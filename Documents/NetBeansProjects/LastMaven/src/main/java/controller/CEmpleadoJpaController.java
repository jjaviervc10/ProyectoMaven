/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.CEmpleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.CSucursal;
import entity.SUsuario;
import entity.MVenta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blueweb
 */
public class CEmpleadoJpaController implements Serializable {

    public CEmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CEmpleado CEmpleado) throws PreexistingEntityException, Exception {
        if (CEmpleado.getMVentaCollection() == null) {
            CEmpleado.setMVentaCollection(new ArrayList<MVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CSucursal idSucursal = CEmpleado.getIdSucursal();
            if (idSucursal != null) {
                idSucursal = em.getReference(idSucursal.getClass(), idSucursal.getIdSucursal());
                CEmpleado.setIdSucursal(idSucursal);
            }
            SUsuario idUsuario = CEmpleado.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                CEmpleado.setIdUsuario(idUsuario);
            }
            Collection<MVenta> attachedMVentaCollection = new ArrayList<MVenta>();
            for (MVenta MVentaCollectionMVentaToAttach : CEmpleado.getMVentaCollection()) {
                MVentaCollectionMVentaToAttach = em.getReference(MVentaCollectionMVentaToAttach.getClass(), MVentaCollectionMVentaToAttach.getIdVenta());
                attachedMVentaCollection.add(MVentaCollectionMVentaToAttach);
            }
            CEmpleado.setMVentaCollection(attachedMVentaCollection);
            em.persist(CEmpleado);
            if (idSucursal != null) {
                idSucursal.getCEmpleadoCollection().add(CEmpleado);
                idSucursal = em.merge(idSucursal);
            }
            if (idUsuario != null) {
                idUsuario.getCEmpleadoCollection().add(CEmpleado);
                idUsuario = em.merge(idUsuario);
            }
            for (MVenta MVentaCollectionMVenta : CEmpleado.getMVentaCollection()) {
                CEmpleado oldIdEmpleadoOfMVentaCollectionMVenta = MVentaCollectionMVenta.getIdEmpleado();
                MVentaCollectionMVenta.setIdEmpleado(CEmpleado);
                MVentaCollectionMVenta = em.merge(MVentaCollectionMVenta);
                if (oldIdEmpleadoOfMVentaCollectionMVenta != null) {
                    oldIdEmpleadoOfMVentaCollectionMVenta.getMVentaCollection().remove(MVentaCollectionMVenta);
                    oldIdEmpleadoOfMVentaCollectionMVenta = em.merge(oldIdEmpleadoOfMVentaCollectionMVenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCEmpleado(CEmpleado.getIdEmpleado()) != null) {
                throw new PreexistingEntityException("CEmpleado " + CEmpleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CEmpleado CEmpleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CEmpleado persistentCEmpleado = em.find(CEmpleado.class, CEmpleado.getIdEmpleado());
            CSucursal idSucursalOld = persistentCEmpleado.getIdSucursal();
            CSucursal idSucursalNew = CEmpleado.getIdSucursal();
            SUsuario idUsuarioOld = persistentCEmpleado.getIdUsuario();
            SUsuario idUsuarioNew = CEmpleado.getIdUsuario();
            Collection<MVenta> MVentaCollectionOld = persistentCEmpleado.getMVentaCollection();
            Collection<MVenta> MVentaCollectionNew = CEmpleado.getMVentaCollection();
            if (idSucursalNew != null) {
                idSucursalNew = em.getReference(idSucursalNew.getClass(), idSucursalNew.getIdSucursal());
                CEmpleado.setIdSucursal(idSucursalNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                CEmpleado.setIdUsuario(idUsuarioNew);
            }
            Collection<MVenta> attachedMVentaCollectionNew = new ArrayList<MVenta>();
            for (MVenta MVentaCollectionNewMVentaToAttach : MVentaCollectionNew) {
                MVentaCollectionNewMVentaToAttach = em.getReference(MVentaCollectionNewMVentaToAttach.getClass(), MVentaCollectionNewMVentaToAttach.getIdVenta());
                attachedMVentaCollectionNew.add(MVentaCollectionNewMVentaToAttach);
            }
            MVentaCollectionNew = attachedMVentaCollectionNew;
            CEmpleado.setMVentaCollection(MVentaCollectionNew);
            CEmpleado = em.merge(CEmpleado);
            if (idSucursalOld != null && !idSucursalOld.equals(idSucursalNew)) {
                idSucursalOld.getCEmpleadoCollection().remove(CEmpleado);
                idSucursalOld = em.merge(idSucursalOld);
            }
            if (idSucursalNew != null && !idSucursalNew.equals(idSucursalOld)) {
                idSucursalNew.getCEmpleadoCollection().add(CEmpleado);
                idSucursalNew = em.merge(idSucursalNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getCEmpleadoCollection().remove(CEmpleado);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getCEmpleadoCollection().add(CEmpleado);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (MVenta MVentaCollectionOldMVenta : MVentaCollectionOld) {
                if (!MVentaCollectionNew.contains(MVentaCollectionOldMVenta)) {
                    MVentaCollectionOldMVenta.setIdEmpleado(null);
                    MVentaCollectionOldMVenta = em.merge(MVentaCollectionOldMVenta);
                }
            }
            for (MVenta MVentaCollectionNewMVenta : MVentaCollectionNew) {
                if (!MVentaCollectionOld.contains(MVentaCollectionNewMVenta)) {
                    CEmpleado oldIdEmpleadoOfMVentaCollectionNewMVenta = MVentaCollectionNewMVenta.getIdEmpleado();
                    MVentaCollectionNewMVenta.setIdEmpleado(CEmpleado);
                    MVentaCollectionNewMVenta = em.merge(MVentaCollectionNewMVenta);
                    if (oldIdEmpleadoOfMVentaCollectionNewMVenta != null && !oldIdEmpleadoOfMVentaCollectionNewMVenta.equals(CEmpleado)) {
                        oldIdEmpleadoOfMVentaCollectionNewMVenta.getMVentaCollection().remove(MVentaCollectionNewMVenta);
                        oldIdEmpleadoOfMVentaCollectionNewMVenta = em.merge(oldIdEmpleadoOfMVentaCollectionNewMVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = CEmpleado.getIdEmpleado();
                if (findCEmpleado(id) == null) {
                    throw new NonexistentEntityException("The cEmpleado with id " + id + " no longer exists.");
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
            CEmpleado CEmpleado;
            try {
                CEmpleado = em.getReference(CEmpleado.class, id);
                CEmpleado.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The CEmpleado with id " + id + " no longer exists.", enfe);
            }
            CSucursal idSucursal = CEmpleado.getIdSucursal();
            if (idSucursal != null) {
                idSucursal.getCEmpleadoCollection().remove(CEmpleado);
                idSucursal = em.merge(idSucursal);
            }
            SUsuario idUsuario = CEmpleado.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getCEmpleadoCollection().remove(CEmpleado);
                idUsuario = em.merge(idUsuario);
            }
            Collection<MVenta> MVentaCollection = CEmpleado.getMVentaCollection();
            for (MVenta MVentaCollectionMVenta : MVentaCollection) {
                MVentaCollectionMVenta.setIdEmpleado(null);
                MVentaCollectionMVenta = em.merge(MVentaCollectionMVenta);
            }
            em.remove(CEmpleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CEmpleado> findCEmpleadoEntities() {
        return findCEmpleadoEntities(true, -1, -1);
    }

    public List<CEmpleado> findCEmpleadoEntities(int maxResults, int firstResult) {
        return findCEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<CEmpleado> findCEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CEmpleado.class));
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

    public CEmpleado findCEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getCEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CEmpleado> rt = cq.from(CEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
