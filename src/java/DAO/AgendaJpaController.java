/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Model.Agenda;
import Model.AgendaPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Usuario;
import Model.Cita;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class AgendaJpaController implements Serializable {
    
    public AgendaJpaController() {
        this.emf=Persistence.createEntityManagerFactory("AgendaEjecutivaPU");
    }

    public AgendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Agenda agenda) throws PreexistingEntityException, Exception {
        if (agenda.getAgendaPK() == null) {
            agenda.setAgendaPK(new AgendaPK());
        }
        if (agenda.getCitaList() == null) {
            agenda.setCitaList(new ArrayList<Cita>());
        }
        agenda.getAgendaPK().setUsuario(agenda.getUsuario1().getEmail());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario1 = agenda.getUsuario1();
            if (usuario1 != null) {
                usuario1 = em.getReference(usuario1.getClass(), usuario1.getEmail());
                agenda.setUsuario1(usuario1);
            }
            List<Cita> attachedCitaList = new ArrayList<Cita>();
            for (Cita citaListCitaToAttach : agenda.getCitaList()) {
                citaListCitaToAttach = em.getReference(citaListCitaToAttach.getClass(), citaListCitaToAttach.getCitaPK());
                attachedCitaList.add(citaListCitaToAttach);
            }
            agenda.setCitaList(attachedCitaList);
            em.persist(agenda);
            if (usuario1 != null) {
                usuario1.getAgendaList().add(agenda);
                usuario1 = em.merge(usuario1);
            }
            for (Cita citaListCita : agenda.getCitaList()) {
                Agenda oldAgenda1OfCitaListCita = citaListCita.getAgenda1();
                citaListCita.setAgenda1(agenda);
                citaListCita = em.merge(citaListCita);
                if (oldAgenda1OfCitaListCita != null) {
                    oldAgenda1OfCitaListCita.getCitaList().remove(citaListCita);
                    oldAgenda1OfCitaListCita = em.merge(oldAgenda1OfCitaListCita);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAgenda(agenda.getAgendaPK()) != null) {
                throw new PreexistingEntityException("Agenda " + agenda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Agenda agenda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        agenda.getAgendaPK().setUsuario(agenda.getUsuario1().getEmail());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agenda persistentAgenda = em.find(Agenda.class, agenda.getAgendaPK());
            Usuario usuario1Old = persistentAgenda.getUsuario1();
            Usuario usuario1New = agenda.getUsuario1();
            List<Cita> citaListOld = persistentAgenda.getCitaList();
            List<Cita> citaListNew = agenda.getCitaList();
            List<String> illegalOrphanMessages = null;
            for (Cita citaListOldCita : citaListOld) {
                if (!citaListNew.contains(citaListOldCita)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cita " + citaListOldCita + " since its agenda1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuario1New != null) {
                usuario1New = em.getReference(usuario1New.getClass(), usuario1New.getEmail());
                agenda.setUsuario1(usuario1New);
            }
            List<Cita> attachedCitaListNew = new ArrayList<Cita>();
            for (Cita citaListNewCitaToAttach : citaListNew) {
                citaListNewCitaToAttach = em.getReference(citaListNewCitaToAttach.getClass(), citaListNewCitaToAttach.getCitaPK());
                attachedCitaListNew.add(citaListNewCitaToAttach);
            }
            citaListNew = attachedCitaListNew;
            agenda.setCitaList(citaListNew);
            agenda = em.merge(agenda);
            if (usuario1Old != null && !usuario1Old.equals(usuario1New)) {
                usuario1Old.getAgendaList().remove(agenda);
                usuario1Old = em.merge(usuario1Old);
            }
            if (usuario1New != null && !usuario1New.equals(usuario1Old)) {
                usuario1New.getAgendaList().add(agenda);
                usuario1New = em.merge(usuario1New);
            }
            for (Cita citaListNewCita : citaListNew) {
                if (!citaListOld.contains(citaListNewCita)) {
                    Agenda oldAgenda1OfCitaListNewCita = citaListNewCita.getAgenda1();
                    citaListNewCita.setAgenda1(agenda);
                    citaListNewCita = em.merge(citaListNewCita);
                    if (oldAgenda1OfCitaListNewCita != null && !oldAgenda1OfCitaListNewCita.equals(agenda)) {
                        oldAgenda1OfCitaListNewCita.getCitaList().remove(citaListNewCita);
                        oldAgenda1OfCitaListNewCita = em.merge(oldAgenda1OfCitaListNewCita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AgendaPK id = agenda.getAgendaPK();
                if (findAgenda(id) == null) {
                    throw new NonexistentEntityException("The agenda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AgendaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agenda agenda;
            try {
                agenda = em.getReference(Agenda.class, id);
                agenda.getAgendaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agenda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cita> citaListOrphanCheck = agenda.getCitaList();
            for (Cita citaListOrphanCheckCita : citaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agenda (" + agenda + ") cannot be destroyed since the Cita " + citaListOrphanCheckCita + " in its citaList field has a non-nullable agenda1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario1 = agenda.getUsuario1();
            if (usuario1 != null) {
                usuario1.getAgendaList().remove(agenda);
                usuario1 = em.merge(usuario1);
            }
            em.remove(agenda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Agenda> findAgendaEntities() {
        return findAgendaEntities(true, -1, -1);
    }

    public List<Agenda> findAgendaEntities(int maxResults, int firstResult) {
        return findAgendaEntities(false, maxResults, firstResult);
    }

    private List<Agenda> findAgendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Agenda.class));
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

    public Agenda findAgenda(AgendaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Agenda.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Agenda> rt = cq.from(Agenda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
