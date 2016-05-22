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

public class EnglishCategoryJpaController {

	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public EnglishCategory create(EnglishCategory englishCategory)
			throws PreexistingEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			if (findEnglishCategory(englishCategory.getKey()) != null) {
				throw new PreexistingEntityException("EnglishCategory "
						+ englishCategory + " already exists.");
			}
			em = getEntityManager();
			em.persist(englishCategory);
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
		return englishCategory;
	}

	public void edit(EnglishCategory englishCategory)
			throws NonexistentEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			englishCategory = em.merge(englishCategory);
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
				Key key = englishCategory.getKey();
				if (findEnglishCategory(key) == null) {
					throw new NonexistentEntityException(
							"The englishCategory with key " + key
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
			EnglishCategory englishCategory;
			try {
				englishCategory = em.getReference(EnglishCategory.class, key);
				englishCategory.getKey();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
						"The englishCategory with key " + key
								+ " no longer exists.", enfe);
			}
			em.remove(englishCategory);
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

	public List<EnglishCategory> findEnglishCategoryEntities() {
		return findEnglishCategoryEntities(true, -1, -1);
	}

	public List<EnglishCategory> findEnglishCategoryEntities(int maxResults,
			int firstResult) {
		return findEnglishCategoryEntities(false, maxResults, firstResult);
	}

	private List<EnglishCategory> findEnglishCategoryEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(EnglishCategory.class));
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

	public EnglishCategory findEnglishCategory(Key key) {
		EntityManager em = getEntityManager();
		try {
			EnglishCategory englishCategory = em.find(EnglishCategory.class, key);
			
			return englishCategory;
		} finally {
			em.close();
		}
	}

	public int getEnglishCategoryCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<EnglishCategory> rt = cq.from(EnglishCategory.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
