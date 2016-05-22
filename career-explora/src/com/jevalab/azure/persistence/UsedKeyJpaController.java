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

public class UsedKeyJpaController {
	
	 private static DatastoreService ds = EMF.getDs();
	    private static EntityManagerFactory emf = EMF.get();
	    private Transaction txn = null;

	    public EntityManager getEntityManager() {
	        return emf.createEntityManager();
	    }

	    public UsedKey create(UsedKey usedKey) throws PreexistingEntityException, RollbackFailureException, Exception {
		    EntityManager em = null;
		    try {
		        txn = ds.beginTransaction();
		        UsedKey uk = findUser(usedKey.getKey());
		        if (uk != null) {
		            throw new PreexistingEntityException("User " + usedKey + " already exists.");
		        }
		        em = getEntityManager();
		        em.persist(usedKey);
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
		    return usedKey;
		}

	    public void edit(UsedKey usedKey) throws NonexistentEntityException, RollbackFailureException, Exception {
	        EntityManager em = null;
	        try {
	        	txn = ds.beginTransaction();
	            em = getEntityManager();
	            usedKey = em.merge(usedKey);
	            txn.commit();
	        } catch (Exception ex) {
	            try {
	            	txn.rollback();
	            } catch (Exception re) {
	                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
	            }
	            String msg = ex.getLocalizedMessage();
	            if (msg == null || msg.length() == 0) {
	                String id = usedKey.getKey();
	                if (findUser(id) == null) {
	                    throw new NonexistentEntityException("The usedKey with key " + id + " no longer exists.");
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
	            UsedKey usedKey;
	            try {
	                usedKey = em.getReference(UsedKey.class, id);
	                usedKey.getKey();
	            } catch (EntityNotFoundException enfe) {
	                throw new NonexistentEntityException("The usedKey with key " + id + " no longer exists.", enfe);
	            }
	            em.remove(usedKey);
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

	    public List<UsedKey> findUserEntities() {
	        return findUserEntities(true, -1, -1);
	    }

	    public List<UsedKey> findUserEntities(int maxResults, int firstResult) {
	        return findUserEntities(false, maxResults, firstResult);
	    }

	    private List<UsedKey> findUserEntities(boolean all, int maxResults, int firstResult) {
	        EntityManager em = getEntityManager();
	        try {
	            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	            cq.select(cq.from(UsedKey.class));
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

	    public UsedKey findUser(String id) {
	        EntityManager em = getEntityManager();
	        try {
	        	UsedKey usedKey = em.find(UsedKey.class, id);
	        	
	            return usedKey;
	        } finally {
	            em.close();
	        }
	    }

	    public int getUserCount() {
	        EntityManager em = getEntityManager();
	        try {
	            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	            Root<UsedKey> rt = cq.from(UsedKey.class);
	            cq.select(em.getCriteriaBuilder().count(rt));
	            Query q = em.createQuery(cq);
	            return ((Long) q.getSingleResult()).intValue();
	        } finally {
	            em.close();
	        }
	    }
	    


}
