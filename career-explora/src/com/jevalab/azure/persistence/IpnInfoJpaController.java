package com.jevalab.azure.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Transaction;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;
import com.jevalab.helper.classes.IpnInfo;

public class IpnInfoJpaController {
	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public IpnInfo create(IpnInfo ipnInfo) throws PreexistingEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			if (findIpnInfo(ipnInfo.getKey()) != null) {
				throw new PreexistingEntityException("IpnInfo " + ipnInfo
						+ " already exists.");
			}
			em = getEntityManager();
			em.persist(ipnInfo);
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
		return ipnInfo;
	}

	public void edit(IpnInfo ipnInfo) throws NonexistentEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			ipnInfo = em.merge(ipnInfo);
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
				Key key = ipnInfo.getKey();
				if (findIpnInfo(key) == null) {
					throw new NonexistentEntityException(
							"The ipnInfo with key " + key
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
			IpnInfo ipnInfo;
			try {
				ipnInfo = em.getReference(IpnInfo.class, key);
				ipnInfo.getKey();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The ipnInfo with key "
						+ key + " no longer exists.", enfe);
			}
			em.remove(ipnInfo);
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

	public List<IpnInfo> findIpnInfoEntities() {
		return findIpnInfoEntities(true, -1, -1);
	}

	public List<IpnInfo> findIpnInfoEntities(int maxResults, int firstResult) {
		return findIpnInfoEntities(false, maxResults, firstResult);
	}

	private List<IpnInfo> findIpnInfoEntities(boolean all, int maxResults,
			int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(IpnInfo.class));
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

	public IpnInfo findIpnInfo(Key key) {
		EntityManager em = getEntityManager();
		try {
			IpnInfo ipnInfo = em.find(IpnInfo.class, key);

			return ipnInfo;
		} finally {
			em.close();
		}
	}

	public String findIpnInfo(String txnId) {
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query(
				"IpnInfo");

		q.setFilter(new com.google.appengine.api.datastore.Query.FilterPredicate(
				"txnId",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				txnId));
		
		PreparedQuery pq = ds.prepare(q);
		Entity e = null;
		try {
			e = pq.asSingleEntity();
		} catch(TooManyResultsException tmre) {
			tmre.printStackTrace();
		}
		
		if(e == null) {
			return null;
		} else {
			return (String)e.getProperty("txnId");
		}
	}

	public int getIpnInfoCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<IpnInfo> rt = cq.from(IpnInfo.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
