/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.SAplicacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class SAplicacionJpaController implements Serializable {

    public SAplicacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SAplicacion SAplicacion) throws PreexistingEntityException, Exception {
        if (SAplicacion.getSModuloCollection() == null) {
            SAplicacion.setSModuloCollection(new ArrayList<SModulo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SModulo> attachedSModuloCollection = new ArrayList<SModulo>();
            for (SModulo SModuloCollectionSModuloToAttach : SAplicacion.getSModuloCollection()) {
                SModuloCollectionSModuloToAttach = em.getReference(SModuloCollectionSModuloToAttach.getClass(), SModuloCollectionSModuloToAttach.getIdModulo());
                attachedSModuloCollection.add(SModuloCollectionSModuloToAttach);
            }
            SAplicacion.setSModuloCollection(attachedSModuloCollection);
            em.persist(SAplicacion);
            for (SModulo SModuloCollectionSModulo : SAplicacion.getSModuloCollection()) {
                SAplicacion oldIdAplicacionOfSModuloCollectionSModulo = SModuloCollectionSModulo.getIdAplicacion();
                SModuloCollectionSModulo.setIdAplicacion(SAplicacion);
                SModuloCollectionSModulo = em.merge(SModuloCollectionSModulo);
                if (oldIdAplicacionOfSModuloCollectionSModulo != null) {
                    oldIdAplicacionOfSModuloCollectionSModulo.getSModuloCollection().remove(SModuloCollectionSModulo);
                    oldIdAplicacionOfSModuloCollectionSModulo = em.merge(oldIdAplicacionOfSModuloCollectionSModulo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSAplicacion(SAplicacion.getIdAplicacion()) != null) {
                throw new PreexistingEntityException("SAplicacion " + SAplicacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SAplicacion SAplicacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SAplicacion persistentSAplicacion = em.find(SAplicacion.class, SAplicacion.getIdAplicacion());
            Collection<SModulo> SModuloCollectionOld = persistentSAplicacion.getSModuloCollection();
            Collection<SModulo> SModuloCollectionNew = SAplicacion.getSModuloCollection();
            List<String> illegalOrphanMessages = null;
            for (SModulo SModuloCollectionOldSModulo : SModuloCollectionOld) {
                if (!SModuloCollectionNew.contains(SModuloCollectionOldSModulo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SModulo " + SModuloCollectionOldSModulo + " since its idAplicacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SModulo> attachedSModuloCollectionNew = new ArrayList<SModulo>();
            for (SModulo SModuloCollectionNewSModuloToAttach : SModuloCollectionNew) {
                SModuloCollectionNewSModuloToAttach = em.getReference(SModuloCollectionNewSModuloToAttach.getClass(), SModuloCollectionNewSModuloToAttach.getIdModulo());
                attachedSModuloCollectionNew.add(SModuloCollectionNewSModuloToAttach);
            }
            SModuloCollectionNew = attachedSModuloCollectionNew;
            SAplicacion.setSModuloCollection(SModuloCollectionNew);
            SAplicacion = em.merge(SAplicacion);
            for (SModulo SModuloCollectionNewSModulo : SModuloCollectionNew) {
                if (!SModuloCollectionOld.contains(SModuloCollectionNewSModulo)) {
                    SAplicacion oldIdAplicacionOfSModuloCollectionNewSModulo = SModuloCollectionNewSModulo.getIdAplicacion();
                    SModuloCollectionNewSModulo.setIdAplicacion(SAplicacion);
                    SModuloCollectionNewSModulo = em.merge(SModuloCollectionNewSModulo);
                    if (oldIdAplicacionOfSModuloCollectionNewSModulo != null && !oldIdAplicacionOfSModuloCollectionNewSModulo.equals(SAplicacion)) {
                        oldIdAplicacionOfSModuloCollectionNewSModulo.getSModuloCollection().remove(SModuloCollectionNewSModulo);
                        oldIdAplicacionOfSModuloCollectionNewSModulo = em.merge(oldIdAplicacionOfSModuloCollectionNewSModulo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = SAplicacion.getIdAplicacion();
                if (findSAplicacion(id) == null) {
                    throw new NonexistentEntityException("The sAplicacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SAplicacion SAplicacion;
            try {
                SAplicacion = em.getReference(SAplicacion.class, id);
                SAplicacion.getIdAplicacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The SAplicacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SModulo> SModuloCollectionOrphanCheck = SAplicacion.getSModuloCollection();
            for (SModulo SModuloCollectionOrphanCheckSModulo : SModuloCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SAplicacion (" + SAplicacion + ") cannot be destroyed since the SModulo " + SModuloCollectionOrphanCheckSModulo + " in its SModuloCollection field has a non-nullable idAplicacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(SAplicacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SAplicacion> findSAplicacionEntities() {
        return findSAplicacionEntities(true, -1, -1);
    }

    public List<SAplicacion> findSAplicacionEntities(int maxResults, int firstResult) {
        return findSAplicacionEntities(false, maxResults, firstResult);
    }

    private List<SAplicacion> findSAplicacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SAplicacion.class));
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

    public SAplicacion findSAplicacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SAplicacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSAplicacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SAplicacion> rt = cq.from(SAplicacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
