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
import Model.Cita;
import Model.CitaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class CitaJpaController implements Serializable {
    
    public CitaJpaController() {
        this.emf=Persistence.createEntityManagerFactory("AgendaEjecutivaPU");
    }

    public CitaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cita cita) throws PreexistingEntityException, Exception {
        System.out.println("EN create CITA");
        System.out.println("-----"+cita.getCitaPK().getAgenda());
        System.out.println("-----"+cita.getCitaPK().getUsuario());
        System.out.println("-----"+cita.getCitaPK().getId());
        if (cita.getCitaPK() == null) {
            System.out.println("EN IF CITAPK NULL");
            cita.setCitaPK(new CitaPK());
        }
        System.out.println("despues de if CITAPK NULL");
//        System.out.println(cita.getAgenda1().getAgendaPK().getUsuario()==null);
//        System.out.println(cita.getAgenda1().getAgendaPK().getNombre()==null);
//        cita.getCitaPK().setUsuario(cita.getAgenda1().getAgendaPK().getUsuario());
//        cita.getCitaPK().setAgenda(cita.getAgenda1().getAgendaPK().getNombre());
        System.out.println("despues de inicializar agenda y user");
        EntityManager em = null;
        try {
            System.out.println("inicio try");
            em = getEntityManager();
            em.getTransaction().begin();
            Agenda agenda1 = cita.getAgenda1();
            System.out.println("-------------------1");
            if (agenda1 != null) {
                System.out.println("-------------------2");
                agenda1 = em.getReference(agenda1.getClass(), agenda1.getAgendaPK());
                cita.setAgenda1(agenda1);
            }
            System.out.println("-------------------3");
            System.out.println(cita.getCitaPK().getId());
            System.out.println("-------------------4");
            em.persist(cita);
            System.out.println("-------------------5");
            if (agenda1 != null) {
                agenda1.getCitaList().add(cita);
                agenda1 = em.merge(agenda1);
            }
            System.out.println("-------------------7");
            em.getTransaction().commit();
            System.out.println("fin try");
        } catch (Exception ex) {
            System.out.println("CATCH-------");
            if (findCita(cita.getCitaPK()) != null) {
                System.out.println("-------------------8");
                throw new PreexistingEntityException("Cita " + cita + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cita cita) throws NonexistentEntityException, Exception {
        cita.getCitaPK().setUsuario(cita.getAgenda1().getAgendaPK().getUsuario());
        cita.getCitaPK().setAgenda(cita.getAgenda1().getAgendaPK().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cita persistentCita = em.find(Cita.class, cita.getCitaPK());
            Agenda agenda1Old = persistentCita.getAgenda1();
            Agenda agenda1New = cita.getAgenda1();
            if (agenda1New != null) {
                agenda1New = em.getReference(agenda1New.getClass(), agenda1New.getAgendaPK());
                cita.setAgenda1(agenda1New);
            }
            cita = em.merge(cita);
            if (agenda1Old != null && !agenda1Old.equals(agenda1New)) {
                agenda1Old.getCitaList().remove(cita);
                agenda1Old = em.merge(agenda1Old);
            }
            if (agenda1New != null && !agenda1New.equals(agenda1Old)) {
                agenda1New.getCitaList().add(cita);
                agenda1New = em.merge(agenda1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CitaPK id = cita.getCitaPK();
                if (findCita(id) == null) {
                    throw new NonexistentEntityException("The cita with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CitaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cita cita;
            try {
                cita = em.getReference(Cita.class, id);
                cita.getCitaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cita with id " + id + " no longer exists.", enfe);
            }
            Agenda agenda1 = cita.getAgenda1();
            if (agenda1 != null) {
                agenda1.getCitaList().remove(cita);
                agenda1 = em.merge(agenda1);
            }
            em.remove(cita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cita> findCitaEntities() {
        return findCitaEntities(true, -1, -1);
    }

    public List<Cita> findCitaEntities(int maxResults, int firstResult) {
        return findCitaEntities(false, maxResults, firstResult);
    }

    private List<Cita> findCitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cita.class));
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

    public Cita findCita(CitaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cita.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cita> rt = cq.from(Cita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
