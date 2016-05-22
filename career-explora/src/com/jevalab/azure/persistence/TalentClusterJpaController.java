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

public class TalentClusterJpaController {

	private static final long serialVersionUID = 1L;

	public TalentClusterJpaController() {

	}

	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public TalentCluster create(TalentCluster talentCluster)
			throws PreexistingEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			if (findTalentCluster(talentCluster.getKey()) != null) {
				throw new PreexistingEntityException("TalentCluster "
						+ talentCluster + " already exists.");
			}
			em = getEntityManager();
			em.persist(talentCluster);
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
		return talentCluster;
	}

	public void edit(TalentCluster talentCluster)
			throws NonexistentEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			talentCluster = em.merge(talentCluster);
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
				Key key = talentCluster.getKey();
				if (findTalentCluster(key) == null) {
					throw new NonexistentEntityException(
							"The talentCluster with key " + key
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
			TalentCluster talentCluster;
			try {
				talentCluster = em.getReference(TalentCluster.class, key);
				talentCluster.getKey();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
						"The talentCluster with key " + key
								+ " no longer exists.", enfe);
			}
			em.remove(talentCluster);
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

	public List<TalentCluster> findTalentClusterEntities() {
		return findTalentClusterEntities(true, -1, -1);
	}

	public List<TalentCluster> findTalentClusterEntities(int maxResults,
			int firstResult) {
		return findTalentClusterEntities(false, maxResults, firstResult);
	}

	private List<TalentCluster> findTalentClusterEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(TalentCluster.class));
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

	public TalentCluster findTalentCluster(Key key) {
		EntityManager em = getEntityManager();
		try {
			TalentCluster talentCluster = em.find(TalentCluster.class, key);
			
			return talentCluster;
		} finally {
			em.close();
		}
	}

	public int getTalentClusterCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<TalentCluster> rt = cq.from(TalentCluster.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
