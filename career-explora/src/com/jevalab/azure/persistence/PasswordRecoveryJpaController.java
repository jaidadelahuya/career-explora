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
import com.jevalab.helper.classes.PasswordRecovery;

public class PasswordRecoveryJpaController {

private static final long serialVersionUID = 1L;

	

	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public PasswordRecovery create(PasswordRecovery passwordRecovery)
			throws PreexistingEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			if (findPasswordRecovery(passwordRecovery.getKey()) != null) {
				throw new PreexistingEntityException("PasswordRecovery "
						+ passwordRecovery + " already exists.");
			}
			em = getEntityManager();
			em.persist(passwordRecovery);
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
		return passwordRecovery;
	}

	public void edit(PasswordRecovery passwordRecovery)
			throws NonexistentEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			passwordRecovery = em.merge(passwordRecovery);
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
				Key key = passwordRecovery.getKey();
				if (findPasswordRecovery(key) == null) {
					throw new NonexistentEntityException(
							"The passwordRecovery with key " + key
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
			PasswordRecovery passwordRecovery;
			try {
				passwordRecovery = em.getReference(PasswordRecovery.class, key);
				passwordRecovery.getKey();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
						"The passwordRecovery with key " + key
								+ " no longer exists.", enfe);
			}
			em.remove(passwordRecovery);
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

	public List<PasswordRecovery> findPasswordRecoveryEntities() {
		return findPasswordRecoveryEntities(true, -1, -1);
	}

	public List<PasswordRecovery> findPasswordRecoveryEntities(int maxResults,
			int firstResult) {
		return findPasswordRecoveryEntities(false, maxResults, firstResult);
	}

	private List<PasswordRecovery> findPasswordRecoveryEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(PasswordRecovery.class));
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

	public PasswordRecovery findPasswordRecovery(Key key) {
		EntityManager em = getEntityManager();
		try {
			PasswordRecovery passwordRecovery = em.find(PasswordRecovery.class, key);
			
			return passwordRecovery;
		} finally {
			em.close();
		}
	}

	public int getPasswordRecoveryCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<PasswordRecovery> rt = cq.from(PasswordRecovery.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
	

}
