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

public class TalentJpaController {
	private static final long serialVersionUID = 1L;

	public TalentJpaController() {

	}

	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public Talent create(Talent talent)
			throws PreexistingEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			if (findTalent(talent.getKey()) != null) {
				throw new PreexistingEntityException("Talent "
						+ talent + " already exists.");
			}
			em = getEntityManager();
			em.persist(talent);
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
		return talent;
	}

	public void edit(Talent talent)
			throws NonexistentEntityException, RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			talent = em.merge(talent);
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
				Key key = talent.getKey();
				if (findTalent(key) == null) {
					throw new NonexistentEntityException(
							"The talent with key " + key
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
			Talent talent;
			try {
				talent = em.getReference(Talent.class, key);
				talent.getKey();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
						"The talent with key " + key
								+ " no longer exists.", enfe);
			}
			em.remove(talent);
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

	public List<Talent> findTalentEntities() {
		return findTalentEntities(true, -1, -1);
	}

	public List<Talent> findTalentEntities(int maxResults,
			int firstResult) {
		return findTalentEntities(false, maxResults, firstResult);
	}

	private List<Talent> findTalentEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Talent.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			List<Talent> list = q.getResultList();
	    	for(Talent t: list) {
	    		t.getQuestions();
	    	}
			return list;
		} finally {
			em.close();
		}
	}
	
	public List<Talent> findTalentEntities(String category) {
		EntityManager em = getEntityManager();
		try {
	    	Query q = em.createQuery("SELECT t FROM Talent t where t.category = '"+category.toUpperCase()+"'");
	    	List<Talent> list = q.getResultList();
	    	for(Talent t: list) {
	    		t.getQuestions();
	    	}
			return list;
		} finally {
			em.close();
		}
	}

	public Talent findTalent(Key key) {
		EntityManager em = getEntityManager();
		try {
			Talent talent = em.find(Talent.class, key);
			
			return talent;
		} finally {
			em.close();
		}
	}
	
	

	public int getTalentCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Talent> rt = cq.from(Talent.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
