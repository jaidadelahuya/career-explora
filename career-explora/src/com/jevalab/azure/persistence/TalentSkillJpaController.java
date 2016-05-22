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

public class TalentSkillJpaController {

	private static final long serialVersionUID = 1L;

	public TalentSkillJpaController() {

	}

	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public TalentSkill create(TalentSkill talentSkill)
			throws PreexistingEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			if (findTalentSkill(talentSkill.getKey()) != null) {
				throw new PreexistingEntityException("TalentSkill "
						+ talentSkill + " already exists.");
			}
			em = getEntityManager();
			em.persist(talentSkill);
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
		return talentSkill;
	}

	public void edit(TalentSkill talentSkill)
			throws NonexistentEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			talentSkill = em.merge(talentSkill);
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
				Key key = talentSkill.getKey();
				if (findTalentSkill(key) == null) {
					throw new NonexistentEntityException(
							"The talentSkill with key " + key
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
			TalentSkill talentSkill;
			try {
				talentSkill = em.getReference(TalentSkill.class, key);
				talentSkill.getKey();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
						"The talentSkill with key " + key
								+ " no longer exists.", enfe);
			}
			em.remove(talentSkill);
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

	public List<TalentSkill> findTalentSkillEntities() {
		return findTalentSkillEntities(true, -1, -1);
	}

	public List<TalentSkill> findTalentSkillEntities(int maxResults,
			int firstResult) {
		return findTalentSkillEntities(false, maxResults, firstResult);
	}

	private List<TalentSkill> findTalentSkillEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(TalentSkill.class));
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

	public TalentSkill findTalentSkill(Key key) {
		EntityManager em = getEntityManager();
		try {
			TalentSkill talentSkill = em.find(TalentSkill.class, key);
			
			return talentSkill;
		} finally {
			em.close();
		}
	}

	public int getTalentSkillCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<TalentSkill> rt = cq.from(TalentSkill.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
	public List<String> findSkills(String talentName) {
		EntityManager em = getEntityManager();
		List<String> list = null;
		talentName = talentName.toUpperCase().trim();
		try {
			Query q = em.createQuery("SELECT t.SkillName FROM TalentSkill t where t.talentName = :tname");
			q.setParameter("tname", talentName);
	    	list = q.getResultList();
	    	
			return list;
		} finally {
			em.close();
		}
    	
	}


}
