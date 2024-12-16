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
import entity.SPerfil;
import entity.CEmpleado;
import java.util.ArrayList;
import java.util.Collection;
import entity.CEmpresa;
import entity.CSucursal;
import entity.MVenta;
import entity.SUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blueweb
 */
public class SUsuarioJpaController implements Serializable {

    public SUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SUsuario SUsuario) throws PreexistingEntityException, Exception {
        if (SUsuario.getCEmpleadoCollection() == null) {
            SUsuario.setCEmpleadoCollection(new ArrayList<CEmpleado>());
        }
        if (SUsuario.getCEmpresaCollection() == null) {
            SUsuario.setCEmpresaCollection(new ArrayList<CEmpresa>());
        }
        if (SUsuario.getCSucursalCollection() == null) {
            SUsuario.setCSucursalCollection(new ArrayList<CSucursal>());
        }
        if (SUsuario.getMVentaCollection() == null) {
            SUsuario.setMVentaCollection(new ArrayList<MVenta>());
        }
        if (SUsuario.getSPerfilCollection() == null) {
            SUsuario.setSPerfilCollection(new ArrayList<SPerfil>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SPerfil idPerfil = SUsuario.getIdPerfil();
            if (idPerfil != null) {
                idPerfil = em.getReference(idPerfil.getClass(), idPerfil.getIdPerfil());
                SUsuario.setIdPerfil(idPerfil);
            }
            Collection<CEmpleado> attachedCEmpleadoCollection = new ArrayList<CEmpleado>();
            for (CEmpleado CEmpleadoCollectionCEmpleadoToAttach : SUsuario.getCEmpleadoCollection()) {
                CEmpleadoCollectionCEmpleadoToAttach = em.getReference(CEmpleadoCollectionCEmpleadoToAttach.getClass(), CEmpleadoCollectionCEmpleadoToAttach.getIdEmpleado());
                attachedCEmpleadoCollection.add(CEmpleadoCollectionCEmpleadoToAttach);
            }
            SUsuario.setCEmpleadoCollection(attachedCEmpleadoCollection);
            Collection<CEmpresa> attachedCEmpresaCollection = new ArrayList<CEmpresa>();
            for (CEmpresa CEmpresaCollectionCEmpresaToAttach : SUsuario.getCEmpresaCollection()) {
                CEmpresaCollectionCEmpresaToAttach = em.getReference(CEmpresaCollectionCEmpresaToAttach.getClass(), CEmpresaCollectionCEmpresaToAttach.getIdEmpresa());
                attachedCEmpresaCollection.add(CEmpresaCollectionCEmpresaToAttach);
            }
            SUsuario.setCEmpresaCollection(attachedCEmpresaCollection);
            Collection<CSucursal> attachedCSucursalCollection = new ArrayList<CSucursal>();
            for (CSucursal CSucursalCollectionCSucursalToAttach : SUsuario.getCSucursalCollection()) {
                CSucursalCollectionCSucursalToAttach = em.getReference(CSucursalCollectionCSucursalToAttach.getClass(), CSucursalCollectionCSucursalToAttach.getIdSucursal());
                attachedCSucursalCollection.add(CSucursalCollectionCSucursalToAttach);
            }
            SUsuario.setCSucursalCollection(attachedCSucursalCollection);
            Collection<MVenta> attachedMVentaCollection = new ArrayList<MVenta>();
            for (MVenta MVentaCollectionMVentaToAttach : SUsuario.getMVentaCollection()) {
                MVentaCollectionMVentaToAttach = em.getReference(MVentaCollectionMVentaToAttach.getClass(), MVentaCollectionMVentaToAttach.getIdVenta());
                attachedMVentaCollection.add(MVentaCollectionMVentaToAttach);
            }
            SUsuario.setMVentaCollection(attachedMVentaCollection);
            Collection<SPerfil> attachedSPerfilCollection = new ArrayList<SPerfil>();
            for (SPerfil SPerfilCollectionSPerfilToAttach : SUsuario.getSPerfilCollection()) {
                SPerfilCollectionSPerfilToAttach = em.getReference(SPerfilCollectionSPerfilToAttach.getClass(), SPerfilCollectionSPerfilToAttach.getIdPerfil());
                attachedSPerfilCollection.add(SPerfilCollectionSPerfilToAttach);
            }
            SUsuario.setSPerfilCollection(attachedSPerfilCollection);
            em.persist(SUsuario);
            if (idPerfil != null) {
                SUsuario oldIdUsuarioOfIdPerfil = idPerfil.getIdUsuario();
                if (oldIdUsuarioOfIdPerfil != null) {
                    oldIdUsuarioOfIdPerfil.setIdPerfil(null);
                    oldIdUsuarioOfIdPerfil = em.merge(oldIdUsuarioOfIdPerfil);
                }
                idPerfil.setIdUsuario(SUsuario);
                idPerfil = em.merge(idPerfil);
            }
            for (CEmpleado CEmpleadoCollectionCEmpleado : SUsuario.getCEmpleadoCollection()) {
                SUsuario oldIdUsuarioOfCEmpleadoCollectionCEmpleado = CEmpleadoCollectionCEmpleado.getIdUsuario();
                CEmpleadoCollectionCEmpleado.setIdUsuario(SUsuario);
                CEmpleadoCollectionCEmpleado = em.merge(CEmpleadoCollectionCEmpleado);
                if (oldIdUsuarioOfCEmpleadoCollectionCEmpleado != null) {
                    oldIdUsuarioOfCEmpleadoCollectionCEmpleado.getCEmpleadoCollection().remove(CEmpleadoCollectionCEmpleado);
                    oldIdUsuarioOfCEmpleadoCollectionCEmpleado = em.merge(oldIdUsuarioOfCEmpleadoCollectionCEmpleado);
                }
            }
            for (CEmpresa CEmpresaCollectionCEmpresa : SUsuario.getCEmpresaCollection()) {
                SUsuario oldIdUsuarioOfCEmpresaCollectionCEmpresa = CEmpresaCollectionCEmpresa.getIdUsuario();
                CEmpresaCollectionCEmpresa.setIdUsuario(SUsuario);
                CEmpresaCollectionCEmpresa = em.merge(CEmpresaCollectionCEmpresa);
                if (oldIdUsuarioOfCEmpresaCollectionCEmpresa != null) {
                    oldIdUsuarioOfCEmpresaCollectionCEmpresa.getCEmpresaCollection().remove(CEmpresaCollectionCEmpresa);
                    oldIdUsuarioOfCEmpresaCollectionCEmpresa = em.merge(oldIdUsuarioOfCEmpresaCollectionCEmpresa);
                }
            }
            for (CSucursal CSucursalCollectionCSucursal : SUsuario.getCSucursalCollection()) {
                SUsuario oldIdUsuarioOfCSucursalCollectionCSucursal = CSucursalCollectionCSucursal.getIdUsuario();
                CSucursalCollectionCSucursal.setIdUsuario(SUsuario);
                CSucursalCollectionCSucursal = em.merge(CSucursalCollectionCSucursal);
                if (oldIdUsuarioOfCSucursalCollectionCSucursal != null) {
                    oldIdUsuarioOfCSucursalCollectionCSucursal.getCSucursalCollection().remove(CSucursalCollectionCSucursal);
                    oldIdUsuarioOfCSucursalCollectionCSucursal = em.merge(oldIdUsuarioOfCSucursalCollectionCSucursal);
                }
            }
            for (MVenta MVentaCollectionMVenta : SUsuario.getMVentaCollection()) {
                SUsuario oldIdUsuarioOfMVentaCollectionMVenta = MVentaCollectionMVenta.getIdUsuario();
                MVentaCollectionMVenta.setIdUsuario(SUsuario);
                MVentaCollectionMVenta = em.merge(MVentaCollectionMVenta);
                if (oldIdUsuarioOfMVentaCollectionMVenta != null) {
                    oldIdUsuarioOfMVentaCollectionMVenta.getMVentaCollection().remove(MVentaCollectionMVenta);
                    oldIdUsuarioOfMVentaCollectionMVenta = em.merge(oldIdUsuarioOfMVentaCollectionMVenta);
                }
            }
            for (SPerfil SPerfilCollectionSPerfil : SUsuario.getSPerfilCollection()) {
                SUsuario oldIdUsuarioOfSPerfilCollectionSPerfil = SPerfilCollectionSPerfil.getIdUsuario();
                SPerfilCollectionSPerfil.setIdUsuario(SUsuario);
                SPerfilCollectionSPerfil = em.merge(SPerfilCollectionSPerfil);
                if (oldIdUsuarioOfSPerfilCollectionSPerfil != null) {
                    oldIdUsuarioOfSPerfilCollectionSPerfil.getSPerfilCollection().remove(SPerfilCollectionSPerfil);
                    oldIdUsuarioOfSPerfilCollectionSPerfil = em.merge(oldIdUsuarioOfSPerfilCollectionSPerfil);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSUsuario(SUsuario.getIdUsuario()) != null) {
                throw new PreexistingEntityException("SUsuario " + SUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SUsuario SUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SUsuario persistentSUsuario = em.find(SUsuario.class, SUsuario.getIdUsuario());
            SPerfil idPerfilOld = persistentSUsuario.getIdPerfil();
            SPerfil idPerfilNew = SUsuario.getIdPerfil();
            Collection<CEmpleado> CEmpleadoCollectionOld = persistentSUsuario.getCEmpleadoCollection();
            Collection<CEmpleado> CEmpleadoCollectionNew = SUsuario.getCEmpleadoCollection();
            Collection<CEmpresa> CEmpresaCollectionOld = persistentSUsuario.getCEmpresaCollection();
            Collection<CEmpresa> CEmpresaCollectionNew = SUsuario.getCEmpresaCollection();
            Collection<CSucursal> CSucursalCollectionOld = persistentSUsuario.getCSucursalCollection();
            Collection<CSucursal> CSucursalCollectionNew = SUsuario.getCSucursalCollection();
            Collection<MVenta> MVentaCollectionOld = persistentSUsuario.getMVentaCollection();
            Collection<MVenta> MVentaCollectionNew = SUsuario.getMVentaCollection();
            Collection<SPerfil> SPerfilCollectionOld = persistentSUsuario.getSPerfilCollection();
            Collection<SPerfil> SPerfilCollectionNew = SUsuario.getSPerfilCollection();
            if (idPerfilNew != null) {
                idPerfilNew = em.getReference(idPerfilNew.getClass(), idPerfilNew.getIdPerfil());
                SUsuario.setIdPerfil(idPerfilNew);
            }
            Collection<CEmpleado> attachedCEmpleadoCollectionNew = new ArrayList<CEmpleado>();
            for (CEmpleado CEmpleadoCollectionNewCEmpleadoToAttach : CEmpleadoCollectionNew) {
                CEmpleadoCollectionNewCEmpleadoToAttach = em.getReference(CEmpleadoCollectionNewCEmpleadoToAttach.getClass(), CEmpleadoCollectionNewCEmpleadoToAttach.getIdEmpleado());
                attachedCEmpleadoCollectionNew.add(CEmpleadoCollectionNewCEmpleadoToAttach);
            }
            CEmpleadoCollectionNew = attachedCEmpleadoCollectionNew;
            SUsuario.setCEmpleadoCollection(CEmpleadoCollectionNew);
            Collection<CEmpresa> attachedCEmpresaCollectionNew = new ArrayList<CEmpresa>();
            for (CEmpresa CEmpresaCollectionNewCEmpresaToAttach : CEmpresaCollectionNew) {
                CEmpresaCollectionNewCEmpresaToAttach = em.getReference(CEmpresaCollectionNewCEmpresaToAttach.getClass(), CEmpresaCollectionNewCEmpresaToAttach.getIdEmpresa());
                attachedCEmpresaCollectionNew.add(CEmpresaCollectionNewCEmpresaToAttach);
            }
            CEmpresaCollectionNew = attachedCEmpresaCollectionNew;
            SUsuario.setCEmpresaCollection(CEmpresaCollectionNew);
            Collection<CSucursal> attachedCSucursalCollectionNew = new ArrayList<CSucursal>();
            for (CSucursal CSucursalCollectionNewCSucursalToAttach : CSucursalCollectionNew) {
                CSucursalCollectionNewCSucursalToAttach = em.getReference(CSucursalCollectionNewCSucursalToAttach.getClass(), CSucursalCollectionNewCSucursalToAttach.getIdSucursal());
                attachedCSucursalCollectionNew.add(CSucursalCollectionNewCSucursalToAttach);
            }
            CSucursalCollectionNew = attachedCSucursalCollectionNew;
            SUsuario.setCSucursalCollection(CSucursalCollectionNew);
            Collection<MVenta> attachedMVentaCollectionNew = new ArrayList<MVenta>();
            for (MVenta MVentaCollectionNewMVentaToAttach : MVentaCollectionNew) {
                MVentaCollectionNewMVentaToAttach = em.getReference(MVentaCollectionNewMVentaToAttach.getClass(), MVentaCollectionNewMVentaToAttach.getIdVenta());
                attachedMVentaCollectionNew.add(MVentaCollectionNewMVentaToAttach);
            }
            MVentaCollectionNew = attachedMVentaCollectionNew;
            SUsuario.setMVentaCollection(MVentaCollectionNew);
            Collection<SPerfil> attachedSPerfilCollectionNew = new ArrayList<SPerfil>();
            for (SPerfil SPerfilCollectionNewSPerfilToAttach : SPerfilCollectionNew) {
                SPerfilCollectionNewSPerfilToAttach = em.getReference(SPerfilCollectionNewSPerfilToAttach.getClass(), SPerfilCollectionNewSPerfilToAttach.getIdPerfil());
                attachedSPerfilCollectionNew.add(SPerfilCollectionNewSPerfilToAttach);
            }
            SPerfilCollectionNew = attachedSPerfilCollectionNew;
            SUsuario.setSPerfilCollection(SPerfilCollectionNew);
            SUsuario = em.merge(SUsuario);
            if (idPerfilOld != null && !idPerfilOld.equals(idPerfilNew)) {
                idPerfilOld.setIdUsuario(null);
                idPerfilOld = em.merge(idPerfilOld);
            }
            if (idPerfilNew != null && !idPerfilNew.equals(idPerfilOld)) {
                SUsuario oldIdUsuarioOfIdPerfil = idPerfilNew.getIdUsuario();
                if (oldIdUsuarioOfIdPerfil != null) {
                    oldIdUsuarioOfIdPerfil.setIdPerfil(null);
                    oldIdUsuarioOfIdPerfil = em.merge(oldIdUsuarioOfIdPerfil);
                }
                idPerfilNew.setIdUsuario(SUsuario);
                idPerfilNew = em.merge(idPerfilNew);
            }
            for (CEmpleado CEmpleadoCollectionOldCEmpleado : CEmpleadoCollectionOld) {
                if (!CEmpleadoCollectionNew.contains(CEmpleadoCollectionOldCEmpleado)) {
                    CEmpleadoCollectionOldCEmpleado.setIdUsuario(null);
                    CEmpleadoCollectionOldCEmpleado = em.merge(CEmpleadoCollectionOldCEmpleado);
                }
            }
            for (CEmpleado CEmpleadoCollectionNewCEmpleado : CEmpleadoCollectionNew) {
                if (!CEmpleadoCollectionOld.contains(CEmpleadoCollectionNewCEmpleado)) {
                    SUsuario oldIdUsuarioOfCEmpleadoCollectionNewCEmpleado = CEmpleadoCollectionNewCEmpleado.getIdUsuario();
                    CEmpleadoCollectionNewCEmpleado.setIdUsuario(SUsuario);
                    CEmpleadoCollectionNewCEmpleado = em.merge(CEmpleadoCollectionNewCEmpleado);
                    if (oldIdUsuarioOfCEmpleadoCollectionNewCEmpleado != null && !oldIdUsuarioOfCEmpleadoCollectionNewCEmpleado.equals(SUsuario)) {
                        oldIdUsuarioOfCEmpleadoCollectionNewCEmpleado.getCEmpleadoCollection().remove(CEmpleadoCollectionNewCEmpleado);
                        oldIdUsuarioOfCEmpleadoCollectionNewCEmpleado = em.merge(oldIdUsuarioOfCEmpleadoCollectionNewCEmpleado);
                    }
                }
            }
            for (CEmpresa CEmpresaCollectionOldCEmpresa : CEmpresaCollectionOld) {
                if (!CEmpresaCollectionNew.contains(CEmpresaCollectionOldCEmpresa)) {
                    CEmpresaCollectionOldCEmpresa.setIdUsuario(null);
                    CEmpresaCollectionOldCEmpresa = em.merge(CEmpresaCollectionOldCEmpresa);
                }
            }
            for (CEmpresa CEmpresaCollectionNewCEmpresa : CEmpresaCollectionNew) {
                if (!CEmpresaCollectionOld.contains(CEmpresaCollectionNewCEmpresa)) {
                    SUsuario oldIdUsuarioOfCEmpresaCollectionNewCEmpresa = CEmpresaCollectionNewCEmpresa.getIdUsuario();
                    CEmpresaCollectionNewCEmpresa.setIdUsuario(SUsuario);
                    CEmpresaCollectionNewCEmpresa = em.merge(CEmpresaCollectionNewCEmpresa);
                    if (oldIdUsuarioOfCEmpresaCollectionNewCEmpresa != null && !oldIdUsuarioOfCEmpresaCollectionNewCEmpresa.equals(SUsuario)) {
                        oldIdUsuarioOfCEmpresaCollectionNewCEmpresa.getCEmpresaCollection().remove(CEmpresaCollectionNewCEmpresa);
                        oldIdUsuarioOfCEmpresaCollectionNewCEmpresa = em.merge(oldIdUsuarioOfCEmpresaCollectionNewCEmpresa);
                    }
                }
            }
            for (CSucursal CSucursalCollectionOldCSucursal : CSucursalCollectionOld) {
                if (!CSucursalCollectionNew.contains(CSucursalCollectionOldCSucursal)) {
                    CSucursalCollectionOldCSucursal.setIdUsuario(null);
                    CSucursalCollectionOldCSucursal = em.merge(CSucursalCollectionOldCSucursal);
                }
            }
            for (CSucursal CSucursalCollectionNewCSucursal : CSucursalCollectionNew) {
                if (!CSucursalCollectionOld.contains(CSucursalCollectionNewCSucursal)) {
                    SUsuario oldIdUsuarioOfCSucursalCollectionNewCSucursal = CSucursalCollectionNewCSucursal.getIdUsuario();
                    CSucursalCollectionNewCSucursal.setIdUsuario(SUsuario);
                    CSucursalCollectionNewCSucursal = em.merge(CSucursalCollectionNewCSucursal);
                    if (oldIdUsuarioOfCSucursalCollectionNewCSucursal != null && !oldIdUsuarioOfCSucursalCollectionNewCSucursal.equals(SUsuario)) {
                        oldIdUsuarioOfCSucursalCollectionNewCSucursal.getCSucursalCollection().remove(CSucursalCollectionNewCSucursal);
                        oldIdUsuarioOfCSucursalCollectionNewCSucursal = em.merge(oldIdUsuarioOfCSucursalCollectionNewCSucursal);
                    }
                }
            }
            for (MVenta MVentaCollectionOldMVenta : MVentaCollectionOld) {
                if (!MVentaCollectionNew.contains(MVentaCollectionOldMVenta)) {
                    MVentaCollectionOldMVenta.setIdUsuario(null);
                    MVentaCollectionOldMVenta = em.merge(MVentaCollectionOldMVenta);
                }
            }
            for (MVenta MVentaCollectionNewMVenta : MVentaCollectionNew) {
                if (!MVentaCollectionOld.contains(MVentaCollectionNewMVenta)) {
                    SUsuario oldIdUsuarioOfMVentaCollectionNewMVenta = MVentaCollectionNewMVenta.getIdUsuario();
                    MVentaCollectionNewMVenta.setIdUsuario(SUsuario);
                    MVentaCollectionNewMVenta = em.merge(MVentaCollectionNewMVenta);
                    if (oldIdUsuarioOfMVentaCollectionNewMVenta != null && !oldIdUsuarioOfMVentaCollectionNewMVenta.equals(SUsuario)) {
                        oldIdUsuarioOfMVentaCollectionNewMVenta.getMVentaCollection().remove(MVentaCollectionNewMVenta);
                        oldIdUsuarioOfMVentaCollectionNewMVenta = em.merge(oldIdUsuarioOfMVentaCollectionNewMVenta);
                    }
                }
            }
            for (SPerfil SPerfilCollectionOldSPerfil : SPerfilCollectionOld) {
                if (!SPerfilCollectionNew.contains(SPerfilCollectionOldSPerfil)) {
                    SPerfilCollectionOldSPerfil.setIdUsuario(null);
                    SPerfilCollectionOldSPerfil = em.merge(SPerfilCollectionOldSPerfil);
                }
            }
            for (SPerfil SPerfilCollectionNewSPerfil : SPerfilCollectionNew) {
                if (!SPerfilCollectionOld.contains(SPerfilCollectionNewSPerfil)) {
                    SUsuario oldIdUsuarioOfSPerfilCollectionNewSPerfil = SPerfilCollectionNewSPerfil.getIdUsuario();
                    SPerfilCollectionNewSPerfil.setIdUsuario(SUsuario);
                    SPerfilCollectionNewSPerfil = em.merge(SPerfilCollectionNewSPerfil);
                    if (oldIdUsuarioOfSPerfilCollectionNewSPerfil != null && !oldIdUsuarioOfSPerfilCollectionNewSPerfil.equals(SUsuario)) {
                        oldIdUsuarioOfSPerfilCollectionNewSPerfil.getSPerfilCollection().remove(SPerfilCollectionNewSPerfil);
                        oldIdUsuarioOfSPerfilCollectionNewSPerfil = em.merge(oldIdUsuarioOfSPerfilCollectionNewSPerfil);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = SUsuario.getIdUsuario();
                if (findSUsuario(id) == null) {
                    throw new NonexistentEntityException("The sUsuario with id " + id + " no longer exists.");
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
            SUsuario SUsuario;
            try {
                SUsuario = em.getReference(SUsuario.class, id);
                SUsuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The SUsuario with id " + id + " no longer exists.", enfe);
            }
            SPerfil idPerfil = SUsuario.getIdPerfil();
            if (idPerfil != null) {
                idPerfil.setIdUsuario(null);
                idPerfil = em.merge(idPerfil);
            }
            Collection<CEmpleado> CEmpleadoCollection = SUsuario.getCEmpleadoCollection();
            for (CEmpleado CEmpleadoCollectionCEmpleado : CEmpleadoCollection) {
                CEmpleadoCollectionCEmpleado.setIdUsuario(null);
                CEmpleadoCollectionCEmpleado = em.merge(CEmpleadoCollectionCEmpleado);
            }
            Collection<CEmpresa> CEmpresaCollection = SUsuario.getCEmpresaCollection();
            for (CEmpresa CEmpresaCollectionCEmpresa : CEmpresaCollection) {
                CEmpresaCollectionCEmpresa.setIdUsuario(null);
                CEmpresaCollectionCEmpresa = em.merge(CEmpresaCollectionCEmpresa);
            }
            Collection<CSucursal> CSucursalCollection = SUsuario.getCSucursalCollection();
            for (CSucursal CSucursalCollectionCSucursal : CSucursalCollection) {
                CSucursalCollectionCSucursal.setIdUsuario(null);
                CSucursalCollectionCSucursal = em.merge(CSucursalCollectionCSucursal);
            }
            Collection<MVenta> MVentaCollection = SUsuario.getMVentaCollection();
            for (MVenta MVentaCollectionMVenta : MVentaCollection) {
                MVentaCollectionMVenta.setIdUsuario(null);
                MVentaCollectionMVenta = em.merge(MVentaCollectionMVenta);
            }
            Collection<SPerfil> SPerfilCollection = SUsuario.getSPerfilCollection();
            for (SPerfil SPerfilCollectionSPerfil : SPerfilCollection) {
                SPerfilCollectionSPerfil.setIdUsuario(null);
                SPerfilCollectionSPerfil = em.merge(SPerfilCollectionSPerfil);
            }
            em.remove(SUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SUsuario> findSUsuarioEntities() {
        return findSUsuarioEntities(true, -1, -1);
    }

    public List<SUsuario> findSUsuarioEntities(int maxResults, int firstResult) {
        return findSUsuarioEntities(false, maxResults, firstResult);
    }

    private List<SUsuario> findSUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SUsuario.class));
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

    public SUsuario findSUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getSUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SUsuario> rt = cq.from(SUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
