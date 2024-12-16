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
import entity.CEmpresa;
import entity.SUsuario;
import entity.CEmpleado;
import entity.CSucursal;
import java.util.ArrayList;
import java.util.Collection;
import entity.MVenta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blueweb
 */
public class CSucursalJpaController implements Serializable {

    public CSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CSucursal CSucursal) throws PreexistingEntityException, Exception {
        if (CSucursal.getCEmpleadoCollection() == null) {
            CSucursal.setCEmpleadoCollection(new ArrayList<CEmpleado>());
        }
        if (CSucursal.getMVentaCollection() == null) {
            CSucursal.setMVentaCollection(new ArrayList<MVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CEmpresa idEmpresa = CSucursal.getIdEmpresa();
            if (idEmpresa != null) {
                idEmpresa = em.getReference(idEmpresa.getClass(), idEmpresa.getIdEmpresa());
                CSucursal.setIdEmpresa(idEmpresa);
            }
            SUsuario idUsuario = CSucursal.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                CSucursal.setIdUsuario(idUsuario);
            }
            Collection<CEmpleado> attachedCEmpleadoCollection = new ArrayList<CEmpleado>();
            for (CEmpleado CEmpleadoCollectionCEmpleadoToAttach : CSucursal.getCEmpleadoCollection()) {
                CEmpleadoCollectionCEmpleadoToAttach = em.getReference(CEmpleadoCollectionCEmpleadoToAttach.getClass(), CEmpleadoCollectionCEmpleadoToAttach.getIdEmpleado());
                attachedCEmpleadoCollection.add(CEmpleadoCollectionCEmpleadoToAttach);
            }
            CSucursal.setCEmpleadoCollection(attachedCEmpleadoCollection);
            Collection<MVenta> attachedMVentaCollection = new ArrayList<MVenta>();
            for (MVenta MVentaCollectionMVentaToAttach : CSucursal.getMVentaCollection()) {
                MVentaCollectionMVentaToAttach = em.getReference(MVentaCollectionMVentaToAttach.getClass(), MVentaCollectionMVentaToAttach.getIdVenta());
                attachedMVentaCollection.add(MVentaCollectionMVentaToAttach);
            }
            CSucursal.setMVentaCollection(attachedMVentaCollection);
            em.persist(CSucursal);
            if (idEmpresa != null) {
                idEmpresa.getCSucursalCollection().add(CSucursal);
                idEmpresa = em.merge(idEmpresa);
            }
            if (idUsuario != null) {
                idUsuario.getCSucursalCollection().add(CSucursal);
                idUsuario = em.merge(idUsuario);
            }
            for (CEmpleado CEmpleadoCollectionCEmpleado : CSucursal.getCEmpleadoCollection()) {
                CSucursal oldIdSucursalOfCEmpleadoCollectionCEmpleado = CEmpleadoCollectionCEmpleado.getIdSucursal();
                CEmpleadoCollectionCEmpleado.setIdSucursal(CSucursal);
                CEmpleadoCollectionCEmpleado = em.merge(CEmpleadoCollectionCEmpleado);
                if (oldIdSucursalOfCEmpleadoCollectionCEmpleado != null) {
                    oldIdSucursalOfCEmpleadoCollectionCEmpleado.getCEmpleadoCollection().remove(CEmpleadoCollectionCEmpleado);
                    oldIdSucursalOfCEmpleadoCollectionCEmpleado = em.merge(oldIdSucursalOfCEmpleadoCollectionCEmpleado);
                }
            }
            for (MVenta MVentaCollectionMVenta : CSucursal.getMVentaCollection()) {
                CSucursal oldIdSucursalOfMVentaCollectionMVenta = MVentaCollectionMVenta.getIdSucursal();
                MVentaCollectionMVenta.setIdSucursal(CSucursal);
                MVentaCollectionMVenta = em.merge(MVentaCollectionMVenta);
                if (oldIdSucursalOfMVentaCollectionMVenta != null) {
                    oldIdSucursalOfMVentaCollectionMVenta.getMVentaCollection().remove(MVentaCollectionMVenta);
                    oldIdSucursalOfMVentaCollectionMVenta = em.merge(oldIdSucursalOfMVentaCollectionMVenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCSucursal(CSucursal.getIdSucursal()) != null) {
                throw new PreexistingEntityException("CSucursal " + CSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CSucursal CSucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CSucursal persistentCSucursal = em.find(CSucursal.class, CSucursal.getIdSucursal());
            CEmpresa idEmpresaOld = persistentCSucursal.getIdEmpresa();
            CEmpresa idEmpresaNew = CSucursal.getIdEmpresa();
            SUsuario idUsuarioOld = persistentCSucursal.getIdUsuario();
            SUsuario idUsuarioNew = CSucursal.getIdUsuario();
            Collection<CEmpleado> CEmpleadoCollectionOld = persistentCSucursal.getCEmpleadoCollection();
            Collection<CEmpleado> CEmpleadoCollectionNew = CSucursal.getCEmpleadoCollection();
            Collection<MVenta> MVentaCollectionOld = persistentCSucursal.getMVentaCollection();
            Collection<MVenta> MVentaCollectionNew = CSucursal.getMVentaCollection();
            List<String> illegalOrphanMessages = null;
            for (CEmpleado CEmpleadoCollectionOldCEmpleado : CEmpleadoCollectionOld) {
                if (!CEmpleadoCollectionNew.contains(CEmpleadoCollectionOldCEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CEmpleado " + CEmpleadoCollectionOldCEmpleado + " since its idSucursal field is not nullable.");
                }
            }
            for (MVenta MVentaCollectionOldMVenta : MVentaCollectionOld) {
                if (!MVentaCollectionNew.contains(MVentaCollectionOldMVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MVenta " + MVentaCollectionOldMVenta + " since its idSucursal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEmpresaNew != null) {
                idEmpresaNew = em.getReference(idEmpresaNew.getClass(), idEmpresaNew.getIdEmpresa());
                CSucursal.setIdEmpresa(idEmpresaNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                CSucursal.setIdUsuario(idUsuarioNew);
            }
            Collection<CEmpleado> attachedCEmpleadoCollectionNew = new ArrayList<CEmpleado>();
            for (CEmpleado CEmpleadoCollectionNewCEmpleadoToAttach : CEmpleadoCollectionNew) {
                CEmpleadoCollectionNewCEmpleadoToAttach = em.getReference(CEmpleadoCollectionNewCEmpleadoToAttach.getClass(), CEmpleadoCollectionNewCEmpleadoToAttach.getIdEmpleado());
                attachedCEmpleadoCollectionNew.add(CEmpleadoCollectionNewCEmpleadoToAttach);
            }
            CEmpleadoCollectionNew = attachedCEmpleadoCollectionNew;
            CSucursal.setCEmpleadoCollection(CEmpleadoCollectionNew);
            Collection<MVenta> attachedMVentaCollectionNew = new ArrayList<MVenta>();
            for (MVenta MVentaCollectionNewMVentaToAttach : MVentaCollectionNew) {
                MVentaCollectionNewMVentaToAttach = em.getReference(MVentaCollectionNewMVentaToAttach.getClass(), MVentaCollectionNewMVentaToAttach.getIdVenta());
                attachedMVentaCollectionNew.add(MVentaCollectionNewMVentaToAttach);
            }
            MVentaCollectionNew = attachedMVentaCollectionNew;
            CSucursal.setMVentaCollection(MVentaCollectionNew);
            CSucursal = em.merge(CSucursal);
            if (idEmpresaOld != null && !idEmpresaOld.equals(idEmpresaNew)) {
                idEmpresaOld.getCSucursalCollection().remove(CSucursal);
                idEmpresaOld = em.merge(idEmpresaOld);
            }
            if (idEmpresaNew != null && !idEmpresaNew.equals(idEmpresaOld)) {
                idEmpresaNew.getCSucursalCollection().add(CSucursal);
                idEmpresaNew = em.merge(idEmpresaNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getCSucursalCollection().remove(CSucursal);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getCSucursalCollection().add(CSucursal);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (CEmpleado CEmpleadoCollectionNewCEmpleado : CEmpleadoCollectionNew) {
                if (!CEmpleadoCollectionOld.contains(CEmpleadoCollectionNewCEmpleado)) {
                    CSucursal oldIdSucursalOfCEmpleadoCollectionNewCEmpleado = CEmpleadoCollectionNewCEmpleado.getIdSucursal();
                    CEmpleadoCollectionNewCEmpleado.setIdSucursal(CSucursal);
                    CEmpleadoCollectionNewCEmpleado = em.merge(CEmpleadoCollectionNewCEmpleado);
                    if (oldIdSucursalOfCEmpleadoCollectionNewCEmpleado != null && !oldIdSucursalOfCEmpleadoCollectionNewCEmpleado.equals(CSucursal)) {
                        oldIdSucursalOfCEmpleadoCollectionNewCEmpleado.getCEmpleadoCollection().remove(CEmpleadoCollectionNewCEmpleado);
                        oldIdSucursalOfCEmpleadoCollectionNewCEmpleado = em.merge(oldIdSucursalOfCEmpleadoCollectionNewCEmpleado);
                    }
                }
            }
            for (MVenta MVentaCollectionNewMVenta : MVentaCollectionNew) {
                if (!MVentaCollectionOld.contains(MVentaCollectionNewMVenta)) {
                    CSucursal oldIdSucursalOfMVentaCollectionNewMVenta = MVentaCollectionNewMVenta.getIdSucursal();
                    MVentaCollectionNewMVenta.setIdSucursal(CSucursal);
                    MVentaCollectionNewMVenta = em.merge(MVentaCollectionNewMVenta);
                    if (oldIdSucursalOfMVentaCollectionNewMVenta != null && !oldIdSucursalOfMVentaCollectionNewMVenta.equals(CSucursal)) {
                        oldIdSucursalOfMVentaCollectionNewMVenta.getMVentaCollection().remove(MVentaCollectionNewMVenta);
                        oldIdSucursalOfMVentaCollectionNewMVenta = em.merge(oldIdSucursalOfMVentaCollectionNewMVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = CSucursal.getIdSucursal();
                if (findCSucursal(id) == null) {
                    throw new NonexistentEntityException("The cSucursal with id " + id + " no longer exists.");
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
            CSucursal CSucursal;
            try {
                CSucursal = em.getReference(CSucursal.class, id);
                CSucursal.getIdSucursal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The CSucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CEmpleado> CEmpleadoCollectionOrphanCheck = CSucursal.getCEmpleadoCollection();
            for (CEmpleado CEmpleadoCollectionOrphanCheckCEmpleado : CEmpleadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CSucursal (" + CSucursal + ") cannot be destroyed since the CEmpleado " + CEmpleadoCollectionOrphanCheckCEmpleado + " in its CEmpleadoCollection field has a non-nullable idSucursal field.");
            }
            Collection<MVenta> MVentaCollectionOrphanCheck = CSucursal.getMVentaCollection();
            for (MVenta MVentaCollectionOrphanCheckMVenta : MVentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CSucursal (" + CSucursal + ") cannot be destroyed since the MVenta " + MVentaCollectionOrphanCheckMVenta + " in its MVentaCollection field has a non-nullable idSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CEmpresa idEmpresa = CSucursal.getIdEmpresa();
            if (idEmpresa != null) {
                idEmpresa.getCSucursalCollection().remove(CSucursal);
                idEmpresa = em.merge(idEmpresa);
            }
            SUsuario idUsuario = CSucursal.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getCSucursalCollection().remove(CSucursal);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(CSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CSucursal> findCSucursalEntities() {
        return findCSucursalEntities(true, -1, -1);
    }

    public List<CSucursal> findCSucursalEntities(int maxResults, int firstResult) {
        return findCSucursalEntities(false, maxResults, firstResult);
    }

    private List<CSucursal> findCSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CSucursal.class));
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

    public CSucursal findCSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getCSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CSucursal> rt = cq.from(CSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public String obtenerNombreEmpresaDeSucursal(Integer idSucursal) {
    EntityManager em = getEntityManager();
    try {
        // Buscar la sucursal por su ID
        CSucursal sucursal = em.find(CSucursal.class, idSucursal);

        if (sucursal != null && sucursal.getIdEmpresa() != null) {
            // Obtener el nombre de la empresa asociada
            return sucursal.getIdEmpresa().getNombreEmpresa();
            
        } else {
            return "No hay empresa asociada";
        }
    } finally {
        em.close();
    }
    
 }
}
