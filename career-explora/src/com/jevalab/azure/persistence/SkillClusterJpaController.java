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

public class SkillClusterJpaController {
	private static final long serialVersionUID = 1L;

	public SkillClusterJpaController() {

	}

	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public SkillCluster create(SkillCluster skillCluster)
			throws PreexistingEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			if (findSkillCluster(skillCluster.getKey()) != null) {
				throw new PreexistingEntityException("SkillCluster "
						+ skillCluster + " already exists.");
			}
			em = getEntityManager();
			em.persist(skillCluster);
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
		return skillCluster;
	}

	public void edit(SkillCluster skillCluster)
			throws NonexistentEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			skillCluster = em.merge(skillCluster);
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
				Key key = skillCluster.getKey();
				if (findSkillCluster(key) == null) {
					throw new NonexistentEntityException(
							"The skillCluster with key " + key
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
			SkillCluster skillCluster;
			try {
				skillCluster = em.getReference(SkillCluster.class, key);
				skillCluster.getKey();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
						"The skillCluster with key " + key
								+ " no longer exists.", enfe);
			}
			em.remove(skillCluster);
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

	public List<SkillCluster> findSkillClusterEntities() {
		return findSkillClusterEntities(true, -1, -1);
	}

	public List<SkillCluster> findSkillClusterEntities(int maxResults,
			int firstResult) {
		return findSkillClusterEntities(false, maxResults, firstResult);
	}

	private List<SkillCluster> findSkillClusterEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(SkillCluster.class));
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

	public SkillCluster findSkillCluster(Key key) {
		EntityManager em = getEntityManager();
		try {
			SkillCluster skillCluster = em.find(SkillCluster.class, key);
			
			return skillCluster;
		} finally {
			em.close();
		}
	}

	public int getSkillClusterCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<SkillCluster> rt = cq.from(SkillCluster.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
