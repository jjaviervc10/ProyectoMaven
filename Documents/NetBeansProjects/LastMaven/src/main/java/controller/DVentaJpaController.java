/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.DVenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.MVenta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blueweb
 */
public class DVentaJpaController implements Serializable {

    public DVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DVenta DVenta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MVenta idVenta = DVenta.getIdVenta();
            if (idVenta != null) {
                idVenta = em.getReference(idVenta.getClass(), idVenta.getIdVenta());
                DVenta.setIdVenta(idVenta);
            }
            em.persist(DVenta);
            if (idVenta != null) {
                idVenta.getDVentaCollection().add(DVenta);
                idVenta = em.merge(idVenta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDVenta(DVenta.getIdDetalleVenta()) != null) {
                throw new PreexistingEntityException("DVenta " + DVenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DVenta DVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DVenta persistentDVenta = em.find(DVenta.class, DVenta.getIdDetalleVenta());
            MVenta idVentaOld = persistentDVenta.getIdVenta();
            MVenta idVentaNew = DVenta.getIdVenta();
            if (idVentaNew != null) {
                idVentaNew = em.getReference(idVentaNew.getClass(), idVentaNew.getIdVenta());
                DVenta.setIdVenta(idVentaNew);
            }
            DVenta = em.merge(DVenta);
            if (idVentaOld != null && !idVentaOld.equals(idVentaNew)) {
                idVentaOld.getDVentaCollection().remove(DVenta);
                idVentaOld = em.merge(idVentaOld);
            }
            if (idVentaNew != null && !idVentaNew.equals(idVentaOld)) {
                idVentaNew.getDVentaCollection().add(DVenta);
                idVentaNew = em.merge(idVentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = DVenta.getIdDetalleVenta();
                if (findDVenta(id) == null) {
                    throw new NonexistentEntityException("The dVenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DVenta DVenta;
            try {
                DVenta = em.getReference(DVenta.class, id);
                DVenta.getIdDetalleVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The DVenta with id " + id + " no longer exists.", enfe);
            }
            MVenta idVenta = DVenta.getIdVenta();
            if (idVenta != null) {
                idVenta.getDVentaCollection().remove(DVenta);
                idVenta = em.merge(idVenta);
            }
            em.remove(DVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DVenta> findDVentaEntities() {
        return findDVentaEntities(true, -1, -1);
    }

    public List<DVenta> findDVentaEntities(int maxResults, int firstResult) {
        return findDVentaEntities(false, maxResults, firstResult);
    }

    private List<DVenta> findDVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DVenta.class));
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

    public DVenta findDVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getDVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DVenta> rt = cq.from(DVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
