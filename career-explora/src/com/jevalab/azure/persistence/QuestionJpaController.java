/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jevalab.azure.persistence;

import java.io.Serializable;
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
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;

/**
 *
 * @author JAIDA DE LAHUYA
 */
public class QuestionJpaController implements Serializable {

    public QuestionJpaController() {
       
    }
    
    private static DatastoreService ds = EMF.getDs();
    private static EntityManagerFactory emf = EMF.get();
    private Transaction txn = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    

    public void create(Question question) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            em.persist(question);
            txn.commit();
        } catch (Exception ex) {
            try {
            	txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Question question) throws RollbackFailureException, NonexistentEntityException {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            question = em.merge(question);
            txn.commit();
        } catch (Exception ex) {
            try {
            	txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = question.getId();
                if (findQuestion(id) == null) {
                   throw new NonexistentEntityException("The question with key " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            Question question;
            try {
                question = em.getReference(Question.class, id);
                question.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The question with key " + id + " no longer exists.", enfe);
            }
            em.remove(question);
            txn.commit();
        } catch (Exception ex) {
            try {
                txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Question> findQuestionEntities() {
        return findQuestionEntities(true, -1, -1);
    }

    public List<Question> findQuestionEntities(int maxResults, int firstResult) {
        return findQuestionEntities(false, maxResults, firstResult);
    }

    private List<Question> findQuestionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Question.class));
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

    public Question findQuestion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Question.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Question> rt = cq.from(Question.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Question> findQuestionEntities(String subjectName) {
    	subjectName = subjectName.toUpperCase();
		EntityManager em = getEntityManager();
    	Query q = em.createQuery("SELECT q FROM  question q where subjectName = :subject");
    	q.setParameter("subject", subjectName);
    	List list = q.getResultList();
		return list;
	}
    
    public List<Long> findQuestionIds(String subjectName) {
    	subjectName = subjectName.toUpperCase();
		EntityManager em = getEntityManager();
    	Query q = em.createQuery("SELECT q.id FROM  question q where subjectName = :subject");
    	q.setParameter("subject", subjectName);
    	List list = q.getResultList();
		return list;
	}
    
}
