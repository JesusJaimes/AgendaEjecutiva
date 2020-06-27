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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Usuario;
import Model.AgendaCompartida;
import Model.AgendaPK;
import java.util.ArrayList;
import java.util.List;
import Model.Cita;
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
        if (agenda.getAgendaCompartidaList() == null) {
            agenda.setAgendaCompartidaList(new ArrayList<AgendaCompartida>());
        }
        if (agenda.getCitaList() == null) {
            agenda.setCitaList(new ArrayList<Cita>());
        }
        agenda.getAgendaPK().setUsuario(agenda.getUsuario().getEmail());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = agenda.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getEmail());
                agenda.setUsuario(usuario);
            }
            List<AgendaCompartida> attachedAgendaCompartidaList = new ArrayList<AgendaCompartida>();
            for (AgendaCompartida agendaCompartidaListAgendaCompartidaToAttach : agenda.getAgendaCompartidaList()) {
                agendaCompartidaListAgendaCompartidaToAttach = em.getReference(agendaCompartidaListAgendaCompartidaToAttach.getClass(), agendaCompartidaListAgendaCompartidaToAttach.getAgendaCompartidaPK());
                attachedAgendaCompartidaList.add(agendaCompartidaListAgendaCompartidaToAttach);
            }
            agenda.setAgendaCompartidaList(attachedAgendaCompartidaList);
            List<Cita> attachedCitaList = new ArrayList<Cita>();
            for (Cita citaListCitaToAttach : agenda.getCitaList()) {
                citaListCitaToAttach = em.getReference(citaListCitaToAttach.getClass(), citaListCitaToAttach.getCitaPK());
                attachedCitaList.add(citaListCitaToAttach);
            }
            agenda.setCitaList(attachedCitaList);
            em.persist(agenda);
            if (usuario != null) {
                usuario.getAgendaList().add(agenda);
                usuario = em.merge(usuario);
            }
            for (AgendaCompartida agendaCompartidaListAgendaCompartida : agenda.getAgendaCompartidaList()) {
                Agenda oldAgendaOfAgendaCompartidaListAgendaCompartida = agendaCompartidaListAgendaCompartida.getAgenda();
                agendaCompartidaListAgendaCompartida.setAgenda(agenda);
                agendaCompartidaListAgendaCompartida = em.merge(agendaCompartidaListAgendaCompartida);
                if (oldAgendaOfAgendaCompartidaListAgendaCompartida != null) {
                    oldAgendaOfAgendaCompartidaListAgendaCompartida.getAgendaCompartidaList().remove(agendaCompartidaListAgendaCompartida);
                    oldAgendaOfAgendaCompartidaListAgendaCompartida = em.merge(oldAgendaOfAgendaCompartidaListAgendaCompartida);
                }
            }
            for (Cita citaListCita : agenda.getCitaList()) {
                Agenda oldAgendaOfCitaListCita = citaListCita.getAgenda();
                citaListCita.setAgenda(agenda);
                citaListCita = em.merge(citaListCita);
                if (oldAgendaOfCitaListCita != null) {
                    oldAgendaOfCitaListCita.getCitaList().remove(citaListCita);
                    oldAgendaOfCitaListCita = em.merge(oldAgendaOfCitaListCita);
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
        agenda.getAgendaPK().setUsuario(agenda.getUsuario().getEmail());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agenda persistentAgenda = em.find(Agenda.class, agenda.getAgendaPK());
            Usuario usuarioOld = persistentAgenda.getUsuario();
            Usuario usuarioNew = agenda.getUsuario();
            List<AgendaCompartida> agendaCompartidaListOld = persistentAgenda.getAgendaCompartidaList();
            List<AgendaCompartida> agendaCompartidaListNew = agenda.getAgendaCompartidaList();
            List<Cita> citaListOld = persistentAgenda.getCitaList();
            List<Cita> citaListNew = agenda.getCitaList();
            List<String> illegalOrphanMessages = null;
            for (AgendaCompartida agendaCompartidaListOldAgendaCompartida : agendaCompartidaListOld) {
                if (!agendaCompartidaListNew.contains(agendaCompartidaListOldAgendaCompartida)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AgendaCompartida " + agendaCompartidaListOldAgendaCompartida + " since its agenda field is not nullable.");
                }
            }
            for (Cita citaListOldCita : citaListOld) {
                if (!citaListNew.contains(citaListOldCita)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cita " + citaListOldCita + " since its agenda field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getEmail());
                agenda.setUsuario(usuarioNew);
            }
            List<AgendaCompartida> attachedAgendaCompartidaListNew = new ArrayList<AgendaCompartida>();
            for (AgendaCompartida agendaCompartidaListNewAgendaCompartidaToAttach : agendaCompartidaListNew) {
                agendaCompartidaListNewAgendaCompartidaToAttach = em.getReference(agendaCompartidaListNewAgendaCompartidaToAttach.getClass(), agendaCompartidaListNewAgendaCompartidaToAttach.getAgendaCompartidaPK());
                attachedAgendaCompartidaListNew.add(agendaCompartidaListNewAgendaCompartidaToAttach);
            }
            agendaCompartidaListNew = attachedAgendaCompartidaListNew;
            agenda.setAgendaCompartidaList(agendaCompartidaListNew);
            List<Cita> attachedCitaListNew = new ArrayList<Cita>();
            for (Cita citaListNewCitaToAttach : citaListNew) {
                citaListNewCitaToAttach = em.getReference(citaListNewCitaToAttach.getClass(), citaListNewCitaToAttach.getCitaPK());
                attachedCitaListNew.add(citaListNewCitaToAttach);
            }
            citaListNew = attachedCitaListNew;
            agenda.setCitaList(citaListNew);
            agenda = em.merge(agenda);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getAgendaList().remove(agenda);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getAgendaList().add(agenda);
                usuarioNew = em.merge(usuarioNew);
            }
            for (AgendaCompartida agendaCompartidaListNewAgendaCompartida : agendaCompartidaListNew) {
                if (!agendaCompartidaListOld.contains(agendaCompartidaListNewAgendaCompartida)) {
                    Agenda oldAgendaOfAgendaCompartidaListNewAgendaCompartida = agendaCompartidaListNewAgendaCompartida.getAgenda();
                    agendaCompartidaListNewAgendaCompartida.setAgenda(agenda);
                    agendaCompartidaListNewAgendaCompartida = em.merge(agendaCompartidaListNewAgendaCompartida);
                    if (oldAgendaOfAgendaCompartidaListNewAgendaCompartida != null && !oldAgendaOfAgendaCompartidaListNewAgendaCompartida.equals(agenda)) {
                        oldAgendaOfAgendaCompartidaListNewAgendaCompartida.getAgendaCompartidaList().remove(agendaCompartidaListNewAgendaCompartida);
                        oldAgendaOfAgendaCompartidaListNewAgendaCompartida = em.merge(oldAgendaOfAgendaCompartidaListNewAgendaCompartida);
                    }
                }
            }
            for (Cita citaListNewCita : citaListNew) {
                if (!citaListOld.contains(citaListNewCita)) {
                    Agenda oldAgendaOfCitaListNewCita = citaListNewCita.getAgenda();
                    citaListNewCita.setAgenda(agenda);
                    citaListNewCita = em.merge(citaListNewCita);
                    if (oldAgendaOfCitaListNewCita != null && !oldAgendaOfCitaListNewCita.equals(agenda)) {
                        oldAgendaOfCitaListNewCita.getCitaList().remove(citaListNewCita);
                        oldAgendaOfCitaListNewCita = em.merge(oldAgendaOfCitaListNewCita);
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
            List<AgendaCompartida> agendaCompartidaListOrphanCheck = agenda.getAgendaCompartidaList();
            for (AgendaCompartida agendaCompartidaListOrphanCheckAgendaCompartida : agendaCompartidaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agenda (" + agenda + ") cannot be destroyed since the AgendaCompartida " + agendaCompartidaListOrphanCheckAgendaCompartida + " in its agendaCompartidaList field has a non-nullable agenda field.");
            }
            List<Cita> citaListOrphanCheck = agenda.getCitaList();
            for (Cita citaListOrphanCheckCita : citaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agenda (" + agenda + ") cannot be destroyed since the Cita " + citaListOrphanCheckCita + " in its citaList field has a non-nullable agenda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = agenda.getUsuario();
            if (usuario != null) {
                usuario.getAgendaList().remove(agenda);
                usuario = em.merge(usuario);
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
