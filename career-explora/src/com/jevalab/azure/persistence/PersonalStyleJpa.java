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
import com.jevalab.exceptions.RollbackFailureException;

public class PersonalStyleJpa {

	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(PersonalStyle personalStyle) throws RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			em.persist(personalStyle);
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

	public void edit(PersonalStyle personalStyle) throws NonexistentEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			personalStyle = em.merge(personalStyle);
			txn.commit();
		} catch (Exception ex) {
			try {
				txn = ds.beginTransaction();
			} catch (Exception re) {
				throw new RollbackFailureException(
						"An error occurred attempting to roll back the transaction.",
						re);
			}
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Long id = personalStyle.getId();
				if (findPersonalStyle(id) == null) {
					throw new NonexistentEntityException("The personalStyle with key "
							+ id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Long id) throws NonexistentEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			PersonalStyle personalStyle;
			try {
				personalStyle = em.getReference(PersonalStyle.class, id);
				personalStyle.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The personalStyle with key "
						+ id + " no longer exists.", enfe);
			}
			em.remove(personalStyle);
			txn = ds.beginTransaction();
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

	public List<PersonalStyle> findPersonalStyleEntities() {
		return findPersonalStyleEntities(true, -1, -1);
	}

	public List<PersonalStyle> findPersonalStyleEntities(int maxResults, int firstResult) {
		return findPersonalStyleEntities(false, maxResults, firstResult);
	}

	private List<PersonalStyle> findPersonalStyleEntities(boolean all, int maxResults,
			int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(PersonalStyle.class));
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

	public PersonalStyle findPersonalStyle(Long id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(PersonalStyle.class, id);
		} finally {
			em.close();
		}
	}


	public int getPersonalStyleCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<PersonalStyle> rt = cq.from(PersonalStyle.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
