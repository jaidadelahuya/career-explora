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
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class CbtRecordJpaController {

	private static DatastoreService ds = EMF.getDs();
    private static EntityManagerFactory emf = EMF.get();
    private Transaction txn = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CbtRecord cbtRecord) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            em.persist(cbtRecord);
            txn.commit();
        } catch (Exception ex) {
            try {
                txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCbtRecord(cbtRecord.getKey()) != null) {
                throw new PreexistingEntityException("Career Cluster " + cbtRecord + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CbtRecord cbtRecord) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            cbtRecord = em.merge(cbtRecord);
            txn.commit();
        } catch (Exception ex) {
            try {
                txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
            	Key id = cbtRecord.getKey();
                if (findCbtRecord(id) == null) {
                    throw new NonexistentEntityException("The cbtRecord with key " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            CbtRecord cbtRecord;
            try {
            	cbtRecord = em.getReference(CbtRecord.class, id);
            	cbtRecord.getKey();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cbtRecord with key " + id + " no longer exists.", enfe);
            }
            em.remove(cbtRecord);
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

    public List<CbtRecord> findCbtRecordEntities() {
        return findCbtRecordEntities(true, -1, -1);
    }

    public List<CbtRecord> findCbtRecordEntities(int maxResults, int firstResult) {
        return findCbtRecordEntities(false, maxResults, firstResult);
    }

    private List<CbtRecord> findCbtRecordEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CbtRecord.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CbtRecord findCbtRecord(Key id) {
        EntityManager em = getEntityManager();
        try {
        	CbtRecord rec = em.find(CbtRecord.class, id);
        	if(rec != null) {
        		rec.getTests();
        	}
        	
            return rec;
        } finally {
            em.close();
        }
    }

    public int getCbtRecordCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CbtRecord> rt = cq.from(CbtRecord.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<CbtRecord> findAllCbtRecordEntities(){
    	EntityManager em = getEntityManager();
    	Query q = em.createQuery("SELECT c FROM CbtRecord c");
    	List list = q.getResultList();
    
    	return list;
    }

	


}
