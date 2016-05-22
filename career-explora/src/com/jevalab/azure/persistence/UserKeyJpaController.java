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

public class UserKeyJpaController {

	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		
	    private static DatastoreService ds = EMF.getDs();
	    private static EntityManagerFactory emf = EMF.get();
	    private Transaction txn = null;

	    public EntityManager getEntityManager() {
	        return emf.createEntityManager();
	    }

	    public UserKey create(UserKey userKey) throws PreexistingEntityException, RollbackFailureException, Exception {
		    EntityManager em = null;
		    try {
		        txn = ds.beginTransaction();
		        UserKey uk = findUser(userKey.getKey());
		        if (uk != null) {
		            throw new PreexistingEntityException("User " + userKey + " already exists.");
		        }
		        em = getEntityManager();
		        em.persist(userKey);
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
		    return userKey;
		}

	    public void edit(UserKey userKey) throws NonexistentEntityException, RollbackFailureException, Exception {
	        EntityManager em = null;
	        try {
	        	txn = ds.beginTransaction();
	            em = getEntityManager();
	            userKey = em.merge(userKey);
	            txn.commit();
	        } catch (Exception ex) {
	            try {
	            	txn.rollback();
	            } catch (Exception re) {
	                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
	            }
	            String msg = ex.getLocalizedMessage();
	            if (msg == null || msg.length() == 0) {
	                String id = userKey.getKey();
	                if (findUser(id) == null) {
	                    throw new NonexistentEntityException("The userKey with key " + id + " no longer exists.");
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
	            UserKey userKey;
	            try {
	                userKey = em.getReference(UserKey.class, id);
	                userKey.getKey();
	            } catch (EntityNotFoundException enfe) {
	                throw new NonexistentEntityException("The userKey with key " + id + " no longer exists.", enfe);
	            }
	            em.remove(userKey);
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

	    public List<UserKey> findUserEntities() {
	        return findUserEntities(true, -1, -1);
	    }

	    public List<UserKey> findUserEntities(int maxResults, int firstResult) {
	        return findUserEntities(false, maxResults, firstResult);
	    }

	    private List<UserKey> findUserEntities(boolean all, int maxResults, int firstResult) {
	        EntityManager em = getEntityManager();
	        try {
	            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	            cq.select(cq.from(UserKey.class));
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

	    public UserKey findUser(String id) {
	        EntityManager em = getEntityManager();
	        try {
	        	UserKey userKey = em.find(UserKey.class, id);
	        	
	            return userKey;
	        } finally {
	            em.close();
	        }
	    }

	    public int getUserCount() {
	        EntityManager em = getEntityManager();
	        try {
	            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	            Root<UserKey> rt = cq.from(UserKey.class);
	            cq.select(em.getCriteriaBuilder().count(rt));
	            Query q = em.createQuery(cq);
	            return ((Long) q.getSingleResult()).intValue();
	        } finally {
	            em.close();
	        }
	    }
	    
	    

}
