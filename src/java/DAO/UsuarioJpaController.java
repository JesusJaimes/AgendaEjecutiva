/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Agenda;
import Model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class UsuarioJpaController implements Serializable {
    
    public UsuarioJpaController() {
        this.emf=Persistence.createEntityManagerFactory("AgendaEjecutivaPU");
    }

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getAgendaList() == null) {
            usuario.setAgendaList(new ArrayList<Agenda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Agenda> attachedAgendaList = new ArrayList<Agenda>();
            for (Agenda agendaListAgendaToAttach : usuario.getAgendaList()) {
                agendaListAgendaToAttach = em.getReference(agendaListAgendaToAttach.getClass(), agendaListAgendaToAttach.getAgendaPK());
                attachedAgendaList.add(agendaListAgendaToAttach);
            }
            usuario.setAgendaList(attachedAgendaList);
            em.persist(usuario);
            for (Agenda agendaListAgenda : usuario.getAgendaList()) {
                Usuario oldUsuarioOfAgendaListAgenda = agendaListAgenda.getUsuario();
                agendaListAgenda.setUsuario(usuario);
                agendaListAgenda = em.merge(agendaListAgenda);
                if (oldUsuarioOfAgendaListAgenda != null) {
                    oldUsuarioOfAgendaListAgenda.getAgendaList().remove(agendaListAgenda);
                    oldUsuarioOfAgendaListAgenda = em.merge(oldUsuarioOfAgendaListAgenda);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getEmail()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getEmail());
            List<Agenda> agendaListOld = persistentUsuario.getAgendaList();
            List<Agenda> agendaListNew = usuario.getAgendaList();
            List<String> illegalOrphanMessages = null;
            for (Agenda agendaListOldAgenda : agendaListOld) {
                if (!agendaListNew.contains(agendaListOldAgenda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Agenda " + agendaListOldAgenda + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Agenda> attachedAgendaListNew = new ArrayList<Agenda>();
            for (Agenda agendaListNewAgendaToAttach : agendaListNew) {
                agendaListNewAgendaToAttach = em.getReference(agendaListNewAgendaToAttach.getClass(), agendaListNewAgendaToAttach.getAgendaPK());
                attachedAgendaListNew.add(agendaListNewAgendaToAttach);
            }
            agendaListNew = attachedAgendaListNew;
            usuario.setAgendaList(agendaListNew);
            usuario = em.merge(usuario);
            for (Agenda agendaListNewAgenda : agendaListNew) {
                if (!agendaListOld.contains(agendaListNewAgenda)) {
                    Usuario oldUsuarioOfAgendaListNewAgenda = agendaListNewAgenda.getUsuario();
                    agendaListNewAgenda.setUsuario(usuario);
                    agendaListNewAgenda = em.merge(agendaListNewAgenda);
                    if (oldUsuarioOfAgendaListNewAgenda != null && !oldUsuarioOfAgendaListNewAgenda.equals(usuario)) {
                        oldUsuarioOfAgendaListNewAgenda.getAgendaList().remove(agendaListNewAgenda);
                        oldUsuarioOfAgendaListNewAgenda = em.merge(oldUsuarioOfAgendaListNewAgenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getEmail();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getEmail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Agenda> agendaListOrphanCheck = usuario.getAgendaList();
            for (Agenda agendaListOrphanCheckAgenda : agendaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Agenda " + agendaListOrphanCheckAgenda + " in its agendaList field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
