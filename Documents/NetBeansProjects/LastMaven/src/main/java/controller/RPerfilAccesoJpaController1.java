/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.RPerfilAcceso;
import entity.RPerfilAccesoPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.SUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blueweb
 */
public class RPerfilAccesoJpaController1 implements Serializable {

    public RPerfilAccesoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RPerfilAcceso RPerfilAcceso) throws PreexistingEntityException, Exception {
        if (RPerfilAcceso.getRPerfilAccesoPK() == null) {
            RPerfilAcceso.setRPerfilAccesoPK(new RPerfilAccesoPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SUsuario idUsuario = RPerfilAcceso.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                RPerfilAcceso.setIdUsuario(idUsuario);
            }
            em.persist(RPerfilAcceso);
            if (idUsuario != null) {
                idUsuario.getRPerfilAccesoCollection().add(RPerfilAcceso);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRPerfilAcceso(RPerfilAcceso.getRPerfilAccesoPK()) != null) {
                throw new PreexistingEntityException("RPerfilAcceso " + RPerfilAcceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RPerfilAcceso RPerfilAcceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RPerfilAcceso persistentRPerfilAcceso = em.find(RPerfilAcceso.class, RPerfilAcceso.getRPerfilAccesoPK());
            SUsuario idUsuarioOld = persistentRPerfilAcceso.getIdUsuario();
            SUsuario idUsuarioNew = RPerfilAcceso.getIdUsuario();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                RPerfilAcceso.setIdUsuario(idUsuarioNew);
            }
            RPerfilAcceso = em.merge(RPerfilAcceso);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getRPerfilAccesoCollection().remove(RPerfilAcceso);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getRPerfilAccesoCollection().add(RPerfilAcceso);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RPerfilAccesoPK id = RPerfilAcceso.getRPerfilAccesoPK();
                if (findRPerfilAcceso(id) == null) {
                    throw new NonexistentEntityException("The rPerfilAcceso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RPerfilAccesoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RPerfilAcceso RPerfilAcceso;
            try {
                RPerfilAcceso = em.getReference(RPerfilAcceso.class, id);
                RPerfilAcceso.getRPerfilAccesoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The RPerfilAcceso with id " + id + " no longer exists.", enfe);
            }
            SUsuario idUsuario = RPerfilAcceso.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getRPerfilAccesoCollection().remove(RPerfilAcceso);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(RPerfilAcceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RPerfilAcceso> findRPerfilAccesoEntities() {
        return findRPerfilAccesoEntities(true, -1, -1);
    }

    public List<RPerfilAcceso> findRPerfilAccesoEntities(int maxResults, int firstResult) {
        return findRPerfilAccesoEntities(false, maxResults, firstResult);
    }

    private List<RPerfilAcceso> findRPerfilAccesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RPerfilAcceso.class));
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

    public RPerfilAcceso findRPerfilAcceso(RPerfilAccesoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RPerfilAcceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getRPerfilAccesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RPerfilAcceso> rt = cq.from(RPerfilAcceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
