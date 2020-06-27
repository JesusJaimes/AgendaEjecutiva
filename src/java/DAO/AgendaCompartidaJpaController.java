/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Agenda;
import Model.AgendaCompartida;
import Model.AgendaCompartidaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class AgendaCompartidaJpaController implements Serializable {
    
    public AgendaCompartidaJpaController() {
        this.emf=Persistence.createEntityManagerFactory("AgendaEjecutivaPU");
    }

    public AgendaCompartidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AgendaCompartida agendaCompartida) throws PreexistingEntityException, Exception {
        if (agendaCompartida.getAgendaCompartidaPK() == null) {
            agendaCompartida.setAgendaCompartidaPK(new AgendaCompartidaPK());
        }
        agendaCompartida.getAgendaCompartidaPK().setOwner(agendaCompartida.getAgenda().getAgendaPK().getUsuario());
        agendaCompartida.getAgendaCompartidaPK().setAgenda(agendaCompartida.getAgenda().getAgendaPK().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agenda agenda = agendaCompartida.getAgenda();
            if (agenda != null) {
                agenda = em.getReference(agenda.getClass(), agenda.getAgendaPK());
                agendaCompartida.setAgenda(agenda);
            }
            em.persist(agendaCompartida);
            if (agenda != null) {
                agenda.getAgendaCompartidaList().add(agendaCompartida);
                agenda = em.merge(agenda);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAgendaCompartida(agendaCompartida.getAgendaCompartidaPK()) != null) {
                throw new PreexistingEntityException("AgendaCompartida " + agendaCompartida + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AgendaCompartida agendaCompartida) throws NonexistentEntityException, Exception {
        agendaCompartida.getAgendaCompartidaPK().setOwner(agendaCompartida.getAgenda().getAgendaPK().getUsuario());
        agendaCompartida.getAgendaCompartidaPK().setAgenda(agendaCompartida.getAgenda().getAgendaPK().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AgendaCompartida persistentAgendaCompartida = em.find(AgendaCompartida.class, agendaCompartida.getAgendaCompartidaPK());
            Agenda agendaOld = persistentAgendaCompartida.getAgenda();
            Agenda agendaNew = agendaCompartida.getAgenda();
            if (agendaNew != null) {
                agendaNew = em.getReference(agendaNew.getClass(), agendaNew.getAgendaPK());
                agendaCompartida.setAgenda(agendaNew);
            }
            agendaCompartida = em.merge(agendaCompartida);
            if (agendaOld != null && !agendaOld.equals(agendaNew)) {
                agendaOld.getAgendaCompartidaList().remove(agendaCompartida);
                agendaOld = em.merge(agendaOld);
            }
            if (agendaNew != null && !agendaNew.equals(agendaOld)) {
                agendaNew.getAgendaCompartidaList().add(agendaCompartida);
                agendaNew = em.merge(agendaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AgendaCompartidaPK id = agendaCompartida.getAgendaCompartidaPK();
                if (findAgendaCompartida(id) == null) {
                    throw new NonexistentEntityException("The agendaCompartida with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AgendaCompartidaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AgendaCompartida agendaCompartida;
            try {
                agendaCompartida = em.getReference(AgendaCompartida.class, id);
                agendaCompartida.getAgendaCompartidaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agendaCompartida with id " + id + " no longer exists.", enfe);
            }
            Agenda agenda = agendaCompartida.getAgenda();
            if (agenda != null) {
                agenda.getAgendaCompartidaList().remove(agendaCompartida);
                agenda = em.merge(agenda);
            }
            em.remove(agendaCompartida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AgendaCompartida> findAgendaCompartidaEntities() {
        return findAgendaCompartidaEntities(true, -1, -1);
    }

    public List<AgendaCompartida> findAgendaCompartidaEntities(int maxResults, int firstResult) {
        return findAgendaCompartidaEntities(false, maxResults, firstResult);
    }

    private List<AgendaCompartida> findAgendaCompartidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AgendaCompartida.class));
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

    public AgendaCompartida findAgendaCompartida(AgendaCompartidaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AgendaCompartida.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgendaCompartidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AgendaCompartida> rt = cq.from(AgendaCompartida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
