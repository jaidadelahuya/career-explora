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

public class IdSequenceJpaController {

	private static DatastoreService ds = EMF.getDs();
	private static EntityManagerFactory emf = EMF.get();
	private Transaction txn = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(IdSequence idSeq) throws RollbackFailureException,
			Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			em.persist(idSeq);
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

	public void destroy(Long id) throws NonexistentEntityException,
			RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			txn = ds.beginTransaction();
			em = getEntityManager();
			IdSequence idSeq;
			try {
				idSeq = em.getReference(IdSequence.class, id);
				idSeq.getValue();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The idSeq with key " + id
						+ " no longer exists.", enfe);
			}
			em.remove(idSeq);
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

	public IdSequence getSequence(Long id) {
		EntityManager em = getEntityManager();
        try {
            return em.find(IdSequence.class, id);
        } finally {
            em.close();
        }
		
	}
	

}
