package com.jevalab.azure.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Transaction;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class CareerValuesJpaController {

	 private static DatastoreService ds = EMF.getDs();
	    private static EntityManagerFactory emf = EMF.get();
	    private Transaction txn = null;

	    public EntityManager getEntityManager() {
	        return emf.createEntityManager();
	    }
	    

	    public void create(CareerValues careerValues) throws RollbackFailureException, Exception {
	        EntityManager em = null;
	        try {
	        	txn = ds.beginTransaction();
	            em = getEntityManager();
	            em.persist(careerValues);
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

	    public void edit(CareerValues careerValues) throws RollbackFailureException, NonexistentEntityException {
	        EntityManager em = null;
	        try {
	        	txn = ds.beginTransaction();
	            em = getEntityManager();
	            careerValues = em.merge(careerValues);
	            txn.commit();
	        } catch (Exception ex) {
	            try {
	            	txn.rollback();
	            } catch (Exception re) {
	                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
	            }
	            String msg = ex.getLocalizedMessage();
	            if (msg == null || msg.length() == 0) {
	                long id = careerValues.getId();
	                if (findCareerValues(id) == null) {
	                   throw new NonexistentEntityException("The careerValues with key " + id + " no longer exists.");
	                }
	            }
	            throw ex;
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	        }
	    }

	    public void destroy(long id) throws NonexistentEntityException, RollbackFailureException, Exception {
	        EntityManager em = null;
	        try {
	        	txn = ds.beginTransaction();
	            em = getEntityManager();
	            CareerValues careerValues;
	            try {
	                careerValues = em.getReference(CareerValues.class, id);
	                careerValues.getId();
	            } catch (EntityNotFoundException enfe) {
	                throw new NonexistentEntityException("The careerValues with key " + id + " no longer exists.", enfe);
	            }
	            em.remove(careerValues);
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

	    public List<CareerValues> findCareerValuesEntities() {
	        return findCareerValuesEntities(true, -1, -1);
	    }

	    public List<CareerValues> findCareerValuesEntities(int maxResults, int firstResult) {
	        return findCareerValuesEntities(false, maxResults, firstResult);
	    }

	    private List<CareerValues> findCareerValuesEntities(boolean all, int maxResults, int firstResult) {
	        EntityManager em = getEntityManager();
	        try {
	            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	            cq.select(cq.from(CareerValues.class));
	            Query q = em.createQuery(cq);
	            if (!all) {
	                q.setMaxResults(maxResults);
	                q.setFirstResult(firstResult);
	            }
	            List<CareerValues> li = q.getResultList();
	            for(CareerValues c: li) {
	            	c.getValues();
	            }
	            return q.getResultList();
	        } finally {
	            em.close();
	        }
	    }

	    public CareerValues findCareerValues(long id) {
	        EntityManager em = getEntityManager();
	        try {
	            return em.find(CareerValues.class, id);
	        } finally {
	            em.close();
	        }
	    }

	    public int getCareerValuesCount() {
	        EntityManager em = getEntityManager();
	        try {
	            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	            Root<CareerValues> rt = cq.from(CareerValues.class);
	            cq.select(em.getCriteriaBuilder().count(rt));
	            Query q = em.createQuery(cq);
	            return ((Long) q.getSingleResult()).intValue();
	        } finally {
	            em.close();
	        }
	    }

}
