package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Transaction;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;

/**
 *
 * @author JAIDA DE LAHUYA
 */
public class MultipleIntelligenceTestQuestionJpaController implements Serializable {

    public MultipleIntelligenceTestQuestionJpaController() {
        
    }
    private static DatastoreService ds = EMF.getDs();
    private static EntityManagerFactory emf = EMF.get();
    private Transaction txn = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MultipleIntelligenceTestQuestion multipleIntelligenceTestQuestion) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            em.persist(multipleIntelligenceTestQuestion);
            txn.commit();
        } catch (Exception ex) {
            try {
                txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findMultipleIntelligenceTestQuestion(multipleIntelligenceTestQuestion.getIntelligenceType()) != null) {
                throw new PreexistingEntityException("MultipleIntelligenceTestQuestion " + multipleIntelligenceTestQuestion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MultipleIntelligenceTestQuestion multipleIntelligenceTestQuestion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            multipleIntelligenceTestQuestion = em.merge(multipleIntelligenceTestQuestion);
            txn.commit();
        } catch (Exception ex) {
            try {
                txn.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = multipleIntelligenceTestQuestion.getIntelligenceType();
                if (findMultipleIntelligenceTestQuestion(id) == null) {
                    throw new NonexistentEntityException("The multipleIntelligenceTestQuestion with key " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
        	txn = ds.beginTransaction();
            em = getEntityManager();
            MultipleIntelligenceTestQuestion multipleIntelligenceTestQuestion;
            try {
                multipleIntelligenceTestQuestion = em.getReference(MultipleIntelligenceTestQuestion.class, id);
                multipleIntelligenceTestQuestion.getIntelligenceType();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The multipleIntelligenceTestQuestion with key " + id + " no longer exists.", enfe);
            }
            em.remove(multipleIntelligenceTestQuestion);
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

    public List<MultipleIntelligenceTestQuestion> findMultipleIntelligenceTestQuestionEntities() {
        return findMultipleIntelligenceTestQuestionEntities(true, -1, -1);
    }

    public List<MultipleIntelligenceTestQuestion> findMultipleIntelligenceTestQuestionEntities(int maxResults, int firstResult) {
        return findMultipleIntelligenceTestQuestionEntities(false, maxResults, firstResult);
    }

    private List<MultipleIntelligenceTestQuestion> findMultipleIntelligenceTestQuestionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MultipleIntelligenceTestQuestion.class));
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

    public MultipleIntelligenceTestQuestion findMultipleIntelligenceTestQuestion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MultipleIntelligenceTestQuestion.class, id);
        } finally {
            em.close();
        }
    }

    public int getMultipleIntelligenceTestQuestionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MultipleIntelligenceTestQuestion> rt = cq.from(MultipleIntelligenceTestQuestion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<MultipleIntelligenceTestQuestion> findAllMultipleIntelligenceTestQuestionEntities(){
    	EntityManager em = getEntityManager();
    	List<MultipleIntelligenceTestQuestion> list = null;
    	try {
    		Query q = em.createQuery("SELECT m FROM MultipleIntelligenceTestQuestion m");
        	list = ((List<MultipleIntelligenceTestQuestion>)q.getResultList());
        	for (MultipleIntelligenceTestQuestion m : list) {
        		m.getQuestions();
        	}
        	return list;
    	} finally {
    		em.close();
    	}
    	
    
    	
    }
    
}
