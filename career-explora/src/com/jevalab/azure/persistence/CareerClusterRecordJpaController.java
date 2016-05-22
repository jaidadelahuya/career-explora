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

public class CareerClusterRecordJpaController {

	private static DatastoreService ds = EMF.getDs();
    private static EntityManagerFactory emf = EMF.get();
    private Transaction txn = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CareerClusterRecord careerClusterRecord) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            em.persist(careerClusterRecord);
            txn.commit();
        } catch (Exception ex) {
            try {
                txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCareerClusterRecord(careerClusterRecord.getKey()) != null) {
                throw new PreexistingEntityException("Career Cluster " + careerClusterRecord + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CareerClusterRecord careerClusterRecord) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            careerClusterRecord = em.merge(careerClusterRecord);
            txn.commit();
        } catch (Exception ex) {
            try {
                txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
            	Key id = careerClusterRecord.getKey();
                if (findCareerClusterRecord(id) == null) {
                    throw new NonexistentEntityException("The careerClusterRecord with key " + id + " no longer exists.");
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
            CareerClusterRecord careerClusterRecord;
            try {
            	careerClusterRecord = em.getReference(CareerClusterRecord.class, id);
            	careerClusterRecord.getKey();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The careerClusterRecord with key " + id + " no longer exists.", enfe);
            }
            em.remove(careerClusterRecord);
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

    public List<CareerClusterRecord> findCareerClusterRecordEntities() {
        return findCareerClusterRecordEntities(true, -1, -1);
    }

    public List<CareerClusterRecord> findCareerClusterRecordEntities(int maxResults, int firstResult) {
        return findCareerClusterRecordEntities(false, maxResults, firstResult);
    }

    private List<CareerClusterRecord> findCareerClusterRecordEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CareerClusterRecord.class));
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

    public CareerClusterRecord findCareerClusterRecord(Key id) {
        EntityManager em = getEntityManager();
        try {
        	CareerClusterRecord rec = em.find(CareerClusterRecord.class, id);
        	if(rec != null) {
        		rec.getRecord();
        	}
        	
            return rec;
        } finally {
            em.close();
        }
    }

    public int getCareerClusterRecordCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CareerClusterRecord> rt = cq.from(CareerClusterRecord.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<CareerClusterRecord> findAllCareerClusterRecordEntities(){
    	EntityManager em = getEntityManager();
    	Query q = em.createQuery("SELECT c FROM CareerClusterRecord c");
    	List list = q.getResultList();
    
    	return list;
    }

}
