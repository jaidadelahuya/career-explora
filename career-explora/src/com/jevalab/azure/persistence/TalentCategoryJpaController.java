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

public class TalentCategoryJpaController {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public TalentCategoryJpaController() {

	}

	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public TalentCategory create(TalentCategory talentCategory)
			throws PreexistingEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			if (findTalentCategory(talentCategory.getKey()) != null) {
				throw new PreexistingEntityException("TalentCategory "
						+ talentCategory + " already exists.");
			}
			em = getEntityManager();
			em.persist(talentCategory);
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
		return talentCategory;
	}

	public void edit(TalentCategory talentCategory)
			throws NonexistentEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			talentCategory = em.merge(talentCategory);
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
				Key key = talentCategory.getKey();
				if (findTalentCategory(key) == null) {
					throw new NonexistentEntityException(
							"The talentCategory with key " + key
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
			TalentCategory talentCategory;
			try {
				talentCategory = em.getReference(TalentCategory.class, key);
				talentCategory.getKey();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
						"The talentCategory with key " + key
								+ " no longer exists.", enfe);
			}
			em.remove(talentCategory);
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

	public List<TalentCategory> findTalentCategoryEntities() {
		return findTalentCategoryEntities(true, -1, -1);
	}

	public List<TalentCategory> findTalentCategoryEntities(int maxResults,
			int firstResult) {
		return findTalentCategoryEntities(false, maxResults, firstResult);
	}

	private List<TalentCategory> findTalentCategoryEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(TalentCategory.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			List<TalentCategory> list = q.getResultList();
			for(TalentCategory tc:list) {
				tc.getTalent();
			}
			return list;
		} finally {
			em.close();
		}
	}

	public TalentCategory findTalentCategory(Key key) {
		EntityManager em = getEntityManager();
		try {
			TalentCategory talentCategory = em.find(TalentCategory.class, key);
			if(talentCategory != null) {
				talentCategory.getTalent();
			}
			return talentCategory;
		} finally {
			em.close();
		}
	}

	public int getTalentCategoryCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<TalentCategory> rt = cq.from(TalentCategory.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
