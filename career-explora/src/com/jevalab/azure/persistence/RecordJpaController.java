package com.jevalab.azure.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class RecordJpaController {

	private static DatastoreService ds = EMF.getDs();
    private static EntityManagerFactory emf = EMF.get();
    private Transaction txn = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    

    public void create(Record record) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            em.persist(record);
            txn.commit();
        } catch (Exception ex) {
            try {
            	txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Record record) throws RollbackFailureException, NonexistentEntityException {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            record = em.merge(record);
            txn.commit();
        } catch (Exception ex) {
            try {
            	txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Key id = record.getKey();
                if (findRecord(id) == null) {
                   throw new NonexistentEntityException("The record with key " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Key id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            Record record;
            try {
                record = em.getReference(Record.class, id);
                record.getKey();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The record with key " + id + " no longer exists.", enfe);
            }
            em.remove(record);
            txn.commit();
        } catch (Exception ex) {
            try {
                txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Record> findRecordEntities() {
        return findRecordEntities(true, -1, -1);
    }

    public List<Record> findRecordEntities(int maxResults, int firstResult) {
        return findRecordEntities(false, maxResults, firstResult);
    }

    private List<Record> findRecordEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Record.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Record> li = q.getResultList();
            for(Record c: li) {
            	//c.getValues();
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Record findRecord(Key id) {
        EntityManager em = getEntityManager();
        try {
        	Record rec = em.find(Record.class, id);
            return rec;
        } finally {
            em.close();
        }
    }

    public int getRecordCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Record> rt = cq.from(Record.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }


}
