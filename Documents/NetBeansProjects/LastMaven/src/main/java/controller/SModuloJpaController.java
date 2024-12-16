/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.SAplicacion;
import entity.SAcceso;
import entity.SModulo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blueweb
 */
public class SModuloJpaController implements Serializable {

    public SModuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SModulo SModulo) throws PreexistingEntityException, Exception {
        if (SModulo.getSAccesoCollection() == null) {
            SModulo.setSAccesoCollection(new ArrayList<SAcceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SAplicacion idAplicacion = SModulo.getIdAplicacion();
            if (idAplicacion != null) {
                idAplicacion = em.getReference(idAplicacion.getClass(), idAplicacion.getIdAplicacion());
                SModulo.setIdAplicacion(idAplicacion);
            }
            Collection<SAcceso> attachedSAccesoCollection = new ArrayList<SAcceso>();
            for (SAcceso SAccesoCollectionSAccesoToAttach : SModulo.getSAccesoCollection()) {
                SAccesoCollectionSAccesoToAttach = em.getReference(SAccesoCollectionSAccesoToAttach.getClass(), SAccesoCollectionSAccesoToAttach.getIdAcceso());
                attachedSAccesoCollection.add(SAccesoCollectionSAccesoToAttach);
            }
            SModulo.setSAccesoCollection(attachedSAccesoCollection);
            em.persist(SModulo);
            if (idAplicacion != null) {
                idAplicacion.getSModuloCollection().add(SModulo);
                idAplicacion = em.merge(idAplicacion);
            }
            for (SAcceso SAccesoCollectionSAcceso : SModulo.getSAccesoCollection()) {
                SModulo oldIdModuloOfSAccesoCollectionSAcceso = SAccesoCollectionSAcceso.getIdModulo();
                SAccesoCollectionSAcceso.setIdModulo(SModulo);
                SAccesoCollectionSAcceso = em.merge(SAccesoCollectionSAcceso);
                if (oldIdModuloOfSAccesoCollectionSAcceso != null) {
                    oldIdModuloOfSAccesoCollectionSAcceso.getSAccesoCollection().remove(SAccesoCollectionSAcceso);
                    oldIdModuloOfSAccesoCollectionSAcceso = em.merge(oldIdModuloOfSAccesoCollectionSAcceso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSModulo(SModulo.getIdModulo()) != null) {
                throw new PreexistingEntityException("SModulo " + SModulo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SModulo SModulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SModulo persistentSModulo = em.find(SModulo.class, SModulo.getIdModulo());
            SAplicacion idAplicacionOld = persistentSModulo.getIdAplicacion();
            SAplicacion idAplicacionNew = SModulo.getIdAplicacion();
            Collection<SAcceso> SAccesoCollectionOld = persistentSModulo.getSAccesoCollection();
            Collection<SAcceso> SAccesoCollectionNew = SModulo.getSAccesoCollection();
            if (idAplicacionNew != null) {
                idAplicacionNew = em.getReference(idAplicacionNew.getClass(), idAplicacionNew.getIdAplicacion());
                SModulo.setIdAplicacion(idAplicacionNew);
            }
            Collection<SAcceso> attachedSAccesoCollectionNew = new ArrayList<SAcceso>();
            for (SAcceso SAccesoCollectionNewSAccesoToAttach : SAccesoCollectionNew) {
                SAccesoCollectionNewSAccesoToAttach = em.getReference(SAccesoCollectionNewSAccesoToAttach.getClass(), SAccesoCollectionNewSAccesoToAttach.getIdAcceso());
                attachedSAccesoCollectionNew.add(SAccesoCollectionNewSAccesoToAttach);
            }
            SAccesoCollectionNew = attachedSAccesoCollectionNew;
            SModulo.setSAccesoCollection(SAccesoCollectionNew);
            SModulo = em.merge(SModulo);
            if (idAplicacionOld != null && !idAplicacionOld.equals(idAplicacionNew)) {
                idAplicacionOld.getSModuloCollection().remove(SModulo);
                idAplicacionOld = em.merge(idAplicacionOld);
            }
            if (idAplicacionNew != null && !idAplicacionNew.equals(idAplicacionOld)) {
                idAplicacionNew.getSModuloCollection().add(SModulo);
                idAplicacionNew = em.merge(idAplicacionNew);
            }
            for (SAcceso SAccesoCollectionOldSAcceso : SAccesoCollectionOld) {
                if (!SAccesoCollectionNew.contains(SAccesoCollectionOldSAcceso)) {
                    SAccesoCollectionOldSAcceso.setIdModulo(null);
                    SAccesoCollectionOldSAcceso = em.merge(SAccesoCollectionOldSAcceso);
                }
            }
            for (SAcceso SAccesoCollectionNewSAcceso : SAccesoCollectionNew) {
                if (!SAccesoCollectionOld.contains(SAccesoCollectionNewSAcceso)) {
                    SModulo oldIdModuloOfSAccesoCollectionNewSAcceso = SAccesoCollectionNewSAcceso.getIdModulo();
                    SAccesoCollectionNewSAcceso.setIdModulo(SModulo);
                    SAccesoCollectionNewSAcceso = em.merge(SAccesoCollectionNewSAcceso);
                    if (oldIdModuloOfSAccesoCollectionNewSAcceso != null && !oldIdModuloOfSAccesoCollectionNewSAcceso.equals(SModulo)) {
                        oldIdModuloOfSAccesoCollectionNewSAcceso.getSAccesoCollection().remove(SAccesoCollectionNewSAcceso);
                        oldIdModuloOfSAccesoCollectionNewSAcceso = em.merge(oldIdModuloOfSAccesoCollectionNewSAcceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = SModulo.getIdModulo();
                if (findSModulo(id) == null) {
                    throw new NonexistentEntityException("The sModulo with id " + id + " no longer exists.");
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
            SModulo SModulo;
            try {
                SModulo = em.getReference(SModulo.class, id);
                SModulo.getIdModulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The SModulo with id " + id + " no longer exists.", enfe);
            }
            SAplicacion idAplicacion = SModulo.getIdAplicacion();
            if (idAplicacion != null) {
                idAplicacion.getSModuloCollection().remove(SModulo);
                idAplicacion = em.merge(idAplicacion);
            }
            Collection<SAcceso> SAccesoCollection = SModulo.getSAccesoCollection();
            for (SAcceso SAccesoCollectionSAcceso : SAccesoCollection) {
                SAccesoCollectionSAcceso.setIdModulo(null);
                SAccesoCollectionSAcceso = em.merge(SAccesoCollectionSAcceso);
            }
            em.remove(SModulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SModulo> findSModuloEntities() {
        return findSModuloEntities(true, -1, -1);
    }

    public List<SModulo> findSModuloEntities(int maxResults, int firstResult) {
        return findSModuloEntities(false, maxResults, firstResult);
    }

    private List<SModulo> findSModuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SModulo.class));
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

    public SModulo findSModulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SModulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSModuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SModulo> rt = cq.from(SModulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
