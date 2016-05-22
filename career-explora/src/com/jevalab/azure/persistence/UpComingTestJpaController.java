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

public class UpComingTestJpaController {

	private static final long serialVersionUID = 1L;

	

	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public UpComingTest create(UpComingTest upComingTest)
			throws PreexistingEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			if (findUpComingTest(upComingTest.getKey()) != null) {
				throw new PreexistingEntityException("UpComingTest "
						+ upComingTest + " already exists.");
			}
			em = getEntityManager();
			em.persist(upComingTest);
			txn.commit();

		} catch (Exception ex) {
			try {
				txn.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}

			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return upComingTest;
	}

	public void edit(UpComingTest upComingTest)
			throws NonexistentEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			upComingTest = em.merge(upComingTest);
			txn.commit();
		} catch (Exception ex) {
			try {
				txn.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Key key = upComingTest.getKey();
				if (findUpComingTest(key) == null) {
					throw new NonexistentEntityException(
							"The upComingTest with key " + key
									+ " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Key key) throws NonexistentEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			UpComingTest upComingTest;
			try {
				upComingTest = em.getReference(UpComingTest.class, key);
				upComingTest.getKey();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
						"The upComingTest with key " + key
								+ " no longer exists.", enfe);
			}
			em.remove(upComingTest);
			txn.commit();
		} catch (Exception ex) {
			try {
				txn.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<UpComingTest> findUpComingTestEntities() {
		return findUpComingTestEntities(true, -1, -1);
	}

	public List<UpComingTest> findUpComingTestEntities(int maxResults,
			int firstResult) {
		return findUpComingTestEntities(false, maxResults, firstResult);
	}

	private List<UpComingTest> findUpComingTestEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(UpComingTest.class));
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

	public UpComingTest findUpComingTest(Key key) {
		EntityManager em = getEntityManager();
		try {
			UpComingTest upComingTest = em.find(UpComingTest.class, key);
			
			return upComingTest;
		} finally {
			em.close();
		}
	}

	public int getUpComingTestCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<UpComingTest> rt = cq.from(UpComingTest.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
	public UpComingTest findUpComingTest(String testName) {
		EntityManager em = getEntityManager();
		UpComingTest uc = null;
		
		try {
			Query q = em.createQuery("SELECT t FROM UPCOMINGTEST t where t.testName = :tname");
			q.setParameter("tname", testName.trim());
	    	uc = (UpComingTest)q.getSingleResult();
	    	
			return uc;
		} finally {
			em.close();
		}
    	
	}

}
