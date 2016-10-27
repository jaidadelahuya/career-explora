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
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class CareerClusterJpaController {

	private static DatastoreService ds = EMF.getDs();
    private static EntityManagerFactory emf = EMF.get();
    private Transaction txn = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CareerCluster careerCluster) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            em.persist(careerCluster);
            txn.commit();
        } catch (Exception ex) {
            try {
                txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCareerCluster(careerCluster.getClusterName()) != null) {
                throw new PreexistingEntityException("Career Cluster " + careerCluster + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CareerCluster careerCluster) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            careerCluster = em.merge(careerCluster);
            txn.commit();
        } catch (Exception ex) {
            try {
                txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = careerCluster.getClusterName();
                if (findCareerCluster(id) == null) {
                    throw new NonexistentEntityException("The careerCluster with key " + id + " no longer exists.");
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
            CareerCluster careerCluster;
            try {
            	careerCluster = em.getReference(CareerCluster.class, id);
            	careerCluster.getClusterName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The careerCluster with key " + id + " no longer exists.", enfe);
            }
            em.remove(careerCluster);
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

    public List<CareerCluster> findCareerClusterEntities() {
        return findCareerClusterEntities(true, -1, -1);
    }

    public List<CareerCluster> findCareerClusterEntities(int maxResults, int firstResult) {
        return findCareerClusterEntities(false, maxResults, firstResult);
    }

    private List<CareerCluster> findCareerClusterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        
        
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CareerCluster.class));
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

    public CareerCluster findCareerCluster(String id) {
        EntityManager em = getEntityManager();
        try {
            CareerCluster c = em.find(CareerCluster.class, id);
            c.getPathways();
            return c;
        } finally {
            em.close();
        }
    }

    public int getCareerClusterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CareerCluster> rt = cq.from(CareerCluster.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<CareerCluster> findAllCareerClusterEntities(){
    	EntityManager em = getEntityManager();
    	List list = null;
    	try {
    		Query q = em.createQuery("SELECT c FROM careerCluster c");
    		list = q.getResultList();
    		return list;
    	} finally {
    		em.close();
    	}
    }

	public List<CareerCluster> findClusterEntities(String clusterList) {
		EntityManager em = getEntityManager();
		List<CareerCluster> list = null;
		try {
			Query q = em.createQuery("SELECT c FROM careerCluster c where clusterName in ("+clusterList+")");
	    	list = q.getResultList();
	    	for(CareerCluster cc : list) {
	    		cc.getQuestions();
	    		cc.getPathways();
	    	}
			return list;
		} finally {
			em.close();
		}
    	
	}

	

}
