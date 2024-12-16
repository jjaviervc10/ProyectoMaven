/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.CEmpleado;
import entity.CSucursal;
import entity.SUsuario;
import entity.HVenta;
import java.util.ArrayList;
import java.util.Collection;
import entity.DVenta;
import entity.MVenta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blueweb
 */
public class MVentaJpaController implements Serializable {

    public MVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MVenta MVenta) throws PreexistingEntityException, Exception {
        if (MVenta.getHVentaCollection() == null) {
            MVenta.setHVentaCollection(new ArrayList<HVenta>());
        }
        if (MVenta.getDVentaCollection() == null) {
            MVenta.setDVentaCollection(new ArrayList<DVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CEmpleado idEmpleado = MVenta.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                MVenta.setIdEmpleado(idEmpleado);
            }
            CSucursal idSucursal = MVenta.getIdSucursal();
            if (idSucursal != null) {
                idSucursal = em.getReference(idSucursal.getClass(), idSucursal.getIdSucursal());
                MVenta.setIdSucursal(idSucursal);
            }
            SUsuario idUsuario = MVenta.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                MVenta.setIdUsuario(idUsuario);
            }
            Collection<HVenta> attachedHVentaCollection = new ArrayList<HVenta>();
            for (HVenta HVentaCollectionHVentaToAttach : MVenta.getHVentaCollection()) {
                HVentaCollectionHVentaToAttach = em.getReference(HVentaCollectionHVentaToAttach.getClass(), HVentaCollectionHVentaToAttach.getIdHistVenta());
                attachedHVentaCollection.add(HVentaCollectionHVentaToAttach);
            }
            MVenta.setHVentaCollection(attachedHVentaCollection);
            Collection<DVenta> attachedDVentaCollection = new ArrayList<DVenta>();
            for (DVenta DVentaCollectionDVentaToAttach : MVenta.getDVentaCollection()) {
                DVentaCollectionDVentaToAttach = em.getReference(DVentaCollectionDVentaToAttach.getClass(), DVentaCollectionDVentaToAttach.getIdDetalleVenta());
                attachedDVentaCollection.add(DVentaCollectionDVentaToAttach);
            }
            MVenta.setDVentaCollection(attachedDVentaCollection);
            em.persist(MVenta);
            if (idEmpleado != null) {
                idEmpleado.getMVentaCollection().add(MVenta);
                idEmpleado = em.merge(idEmpleado);
            }
            if (idSucursal != null) {
                idSucursal.getMVentaCollection().add(MVenta);
                idSucursal = em.merge(idSucursal);
            }
            if (idUsuario != null) {
                idUsuario.getMVentaCollection().add(MVenta);
                idUsuario = em.merge(idUsuario);
            }
            for (HVenta HVentaCollectionHVenta : MVenta.getHVentaCollection()) {
                MVenta oldIdVentaOfHVentaCollectionHVenta = HVentaCollectionHVenta.getIdVenta();
                HVentaCollectionHVenta.setIdVenta(MVenta);
                HVentaCollectionHVenta = em.merge(HVentaCollectionHVenta);
                if (oldIdVentaOfHVentaCollectionHVenta != null) {
                    oldIdVentaOfHVentaCollectionHVenta.getHVentaCollection().remove(HVentaCollectionHVenta);
                    oldIdVentaOfHVentaCollectionHVenta = em.merge(oldIdVentaOfHVentaCollectionHVenta);
                }
            }
            for (DVenta DVentaCollectionDVenta : MVenta.getDVentaCollection()) {
                MVenta oldIdVentaOfDVentaCollectionDVenta = DVentaCollectionDVenta.getIdVenta();
                DVentaCollectionDVenta.setIdVenta(MVenta);
                DVentaCollectionDVenta = em.merge(DVentaCollectionDVenta);
                if (oldIdVentaOfDVentaCollectionDVenta != null) {
                    oldIdVentaOfDVentaCollectionDVenta.getDVentaCollection().remove(DVentaCollectionDVenta);
                    oldIdVentaOfDVentaCollectionDVenta = em.merge(oldIdVentaOfDVentaCollectionDVenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMVenta(MVenta.getIdVenta()) != null) {
                throw new PreexistingEntityException("MVenta " + MVenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MVenta MVenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MVenta persistentMVenta = em.find(MVenta.class, MVenta.getIdVenta());
            CEmpleado idEmpleadoOld = persistentMVenta.getIdEmpleado();
            CEmpleado idEmpleadoNew = MVenta.getIdEmpleado();
            CSucursal idSucursalOld = persistentMVenta.getIdSucursal();
            CSucursal idSucursalNew = MVenta.getIdSucursal();
            SUsuario idUsuarioOld = persistentMVenta.getIdUsuario();
            SUsuario idUsuarioNew = MVenta.getIdUsuario();
            Collection<HVenta> HVentaCollectionOld = persistentMVenta.getHVentaCollection();
            Collection<HVenta> HVentaCollectionNew = MVenta.getHVentaCollection();
            Collection<DVenta> DVentaCollectionOld = persistentMVenta.getDVentaCollection();
            Collection<DVenta> DVentaCollectionNew = MVenta.getDVentaCollection();
            List<String> illegalOrphanMessages = null;
            for (HVenta HVentaCollectionOldHVenta : HVentaCollectionOld) {
                if (!HVentaCollectionNew.contains(HVentaCollectionOldHVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HVenta " + HVentaCollectionOldHVenta + " since its idVenta field is not nullable.");
                }
            }
            for (DVenta DVentaCollectionOldDVenta : DVentaCollectionOld) {
                if (!DVentaCollectionNew.contains(DVentaCollectionOldDVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DVenta " + DVentaCollectionOldDVenta + " since its idVenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                MVenta.setIdEmpleado(idEmpleadoNew);
            }
            if (idSucursalNew != null) {
                idSucursalNew = em.getReference(idSucursalNew.getClass(), idSucursalNew.getIdSucursal());
                MVenta.setIdSucursal(idSucursalNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                MVenta.setIdUsuario(idUsuarioNew);
            }
            Collection<HVenta> attachedHVentaCollectionNew = new ArrayList<HVenta>();
            for (HVenta HVentaCollectionNewHVentaToAttach : HVentaCollectionNew) {
                HVentaCollectionNewHVentaToAttach = em.getReference(HVentaCollectionNewHVentaToAttach.getClass(), HVentaCollectionNewHVentaToAttach.getIdHistVenta());
                attachedHVentaCollectionNew.add(HVentaCollectionNewHVentaToAttach);
            }
            HVentaCollectionNew = attachedHVentaCollectionNew;
            MVenta.setHVentaCollection(HVentaCollectionNew);
            Collection<DVenta> attachedDVentaCollectionNew = new ArrayList<DVenta>();
            for (DVenta DVentaCollectionNewDVentaToAttach : DVentaCollectionNew) {
                DVentaCollectionNewDVentaToAttach = em.getReference(DVentaCollectionNewDVentaToAttach.getClass(), DVentaCollectionNewDVentaToAttach.getIdDetalleVenta());
                attachedDVentaCollectionNew.add(DVentaCollectionNewDVentaToAttach);
            }
            DVentaCollectionNew = attachedDVentaCollectionNew;
            MVenta.setDVentaCollection(DVentaCollectionNew);
            MVenta = em.merge(MVenta);
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getMVentaCollection().remove(MVenta);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getMVentaCollection().add(MVenta);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (idSucursalOld != null && !idSucursalOld.equals(idSucursalNew)) {
                idSucursalOld.getMVentaCollection().remove(MVenta);
                idSucursalOld = em.merge(idSucursalOld);
            }
            if (idSucursalNew != null && !idSucursalNew.equals(idSucursalOld)) {
                idSucursalNew.getMVentaCollection().add(MVenta);
                idSucursalNew = em.merge(idSucursalNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getMVentaCollection().remove(MVenta);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getMVentaCollection().add(MVenta);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (HVenta HVentaCollectionNewHVenta : HVentaCollectionNew) {
                if (!HVentaCollectionOld.contains(HVentaCollectionNewHVenta)) {
                    MVenta oldIdVentaOfHVentaCollectionNewHVenta = HVentaCollectionNewHVenta.getIdVenta();
                    HVentaCollectionNewHVenta.setIdVenta(MVenta);
                    HVentaCollectionNewHVenta = em.merge(HVentaCollectionNewHVenta);
                    if (oldIdVentaOfHVentaCollectionNewHVenta != null && !oldIdVentaOfHVentaCollectionNewHVenta.equals(MVenta)) {
                        oldIdVentaOfHVentaCollectionNewHVenta.getHVentaCollection().remove(HVentaCollectionNewHVenta);
                        oldIdVentaOfHVentaCollectionNewHVenta = em.merge(oldIdVentaOfHVentaCollectionNewHVenta);
                    }
                }
            }
            for (DVenta DVentaCollectionNewDVenta : DVentaCollectionNew) {
                if (!DVentaCollectionOld.contains(DVentaCollectionNewDVenta)) {
                    MVenta oldIdVentaOfDVentaCollectionNewDVenta = DVentaCollectionNewDVenta.getIdVenta();
                    DVentaCollectionNewDVenta.setIdVenta(MVenta);
                    DVentaCollectionNewDVenta = em.merge(DVentaCollectionNewDVenta);
                    if (oldIdVentaOfDVentaCollectionNewDVenta != null && !oldIdVentaOfDVentaCollectionNewDVenta.equals(MVenta)) {
                        oldIdVentaOfDVentaCollectionNewDVenta.getDVentaCollection().remove(DVentaCollectionNewDVenta);
                        oldIdVentaOfDVentaCollectionNewDVenta = em.merge(oldIdVentaOfDVentaCollectionNewDVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = MVenta.getIdVenta();
                if (findMVenta(id) == null) {
                    throw new NonexistentEntityException("The mVenta with id " + id + " no longer exists.");
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
            MVenta MVenta;
            try {
                MVenta = em.getReference(MVenta.class, id);
                MVenta.getIdVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The MVenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<HVenta> HVentaCollectionOrphanCheck = MVenta.getHVentaCollection();
            for (HVenta HVentaCollectionOrphanCheckHVenta : HVentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MVenta (" + MVenta + ") cannot be destroyed since the HVenta " + HVentaCollectionOrphanCheckHVenta + " in its HVentaCollection field has a non-nullable idVenta field.");
            }
            Collection<DVenta> DVentaCollectionOrphanCheck = MVenta.getDVentaCollection();
            for (DVenta DVentaCollectionOrphanCheckDVenta : DVentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MVenta (" + MVenta + ") cannot be destroyed since the DVenta " + DVentaCollectionOrphanCheckDVenta + " in its DVentaCollection field has a non-nullable idVenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CEmpleado idEmpleado = MVenta.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getMVentaCollection().remove(MVenta);
                idEmpleado = em.merge(idEmpleado);
            }
            CSucursal idSucursal = MVenta.getIdSucursal();
            if (idSucursal != null) {
                idSucursal.getMVentaCollection().remove(MVenta);
                idSucursal = em.merge(idSucursal);
            }
            SUsuario idUsuario = MVenta.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getMVentaCollection().remove(MVenta);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(MVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MVenta> findMVentaEntities() {
        return findMVentaEntities(true, -1, -1);
    }

    public List<MVenta> findMVentaEntities(int maxResults, int firstResult) {
        return findMVentaEntities(false, maxResults, firstResult);
    }

    private List<MVenta> findMVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MVenta.class));
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

    public MVenta findMVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getMVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MVenta> rt = cq.from(MVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
