/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.CEmpresa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.SUsuario;
import entity.CSucursal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blueweb
 */
public class CEmpresaJpaController implements Serializable {

    public CEmpresaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CEmpresa CEmpresa) throws PreexistingEntityException, Exception {
        if (CEmpresa.getCSucursalCollection() == null) {
            CEmpresa.setCSucursalCollection(new ArrayList<CSucursal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SUsuario idUsuario = CEmpresa.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                CEmpresa.setIdUsuario(idUsuario);
            }
            Collection<CSucursal> attachedCSucursalCollection = new ArrayList<CSucursal>();
            for (CSucursal CSucursalCollectionCSucursalToAttach : CEmpresa.getCSucursalCollection()) {
                CSucursalCollectionCSucursalToAttach = em.getReference(CSucursalCollectionCSucursalToAttach.getClass(), CSucursalCollectionCSucursalToAttach.getIdSucursal());
                attachedCSucursalCollection.add(CSucursalCollectionCSucursalToAttach);
            }
            CEmpresa.setCSucursalCollection(attachedCSucursalCollection);
            em.persist(CEmpresa);
            if (idUsuario != null) {
                idUsuario.getCEmpresaCollection().add(CEmpresa);
                idUsuario = em.merge(idUsuario);
            }
            for (CSucursal CSucursalCollectionCSucursal : CEmpresa.getCSucursalCollection()) {
                CEmpresa oldIdEmpresaOfCSucursalCollectionCSucursal = CSucursalCollectionCSucursal.getIdEmpresa();
                CSucursalCollectionCSucursal.setIdEmpresa(CEmpresa);
                CSucursalCollectionCSucursal = em.merge(CSucursalCollectionCSucursal);
                if (oldIdEmpresaOfCSucursalCollectionCSucursal != null) {
                    oldIdEmpresaOfCSucursalCollectionCSucursal.getCSucursalCollection().remove(CSucursalCollectionCSucursal);
                    oldIdEmpresaOfCSucursalCollectionCSucursal = em.merge(oldIdEmpresaOfCSucursalCollectionCSucursal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCEmpresa(CEmpresa.getIdEmpresa()) != null) {
                throw new PreexistingEntityException("CEmpresa " + CEmpresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CEmpresa CEmpresa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CEmpresa persistentCEmpresa = em.find(CEmpresa.class, CEmpresa.getIdEmpresa());
            SUsuario idUsuarioOld = persistentCEmpresa.getIdUsuario();
            SUsuario idUsuarioNew = CEmpresa.getIdUsuario();
            Collection<CSucursal> CSucursalCollectionOld = persistentCEmpresa.getCSucursalCollection();
            Collection<CSucursal> CSucursalCollectionNew = CEmpresa.getCSucursalCollection();
            List<String> illegalOrphanMessages = null;
            for (CSucursal CSucursalCollectionOldCSucursal : CSucursalCollectionOld) {
                if (!CSucursalCollectionNew.contains(CSucursalCollectionOldCSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CSucursal " + CSucursalCollectionOldCSucursal + " since its idEmpresa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                CEmpresa.setIdUsuario(idUsuarioNew);
            }
            Collection<CSucursal> attachedCSucursalCollectionNew = new ArrayList<CSucursal>();
            for (CSucursal CSucursalCollectionNewCSucursalToAttach : CSucursalCollectionNew) {
                CSucursalCollectionNewCSucursalToAttach = em.getReference(CSucursalCollectionNewCSucursalToAttach.getClass(), CSucursalCollectionNewCSucursalToAttach.getIdSucursal());
                attachedCSucursalCollectionNew.add(CSucursalCollectionNewCSucursalToAttach);
            }
            CSucursalCollectionNew = attachedCSucursalCollectionNew;
            CEmpresa.setCSucursalCollection(CSucursalCollectionNew);
            CEmpresa = em.merge(CEmpresa);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getCEmpresaCollection().remove(CEmpresa);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getCEmpresaCollection().add(CEmpresa);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (CSucursal CSucursalCollectionNewCSucursal : CSucursalCollectionNew) {
                if (!CSucursalCollectionOld.contains(CSucursalCollectionNewCSucursal)) {
                    CEmpresa oldIdEmpresaOfCSucursalCollectionNewCSucursal = CSucursalCollectionNewCSucursal.getIdEmpresa();
                    CSucursalCollectionNewCSucursal.setIdEmpresa(CEmpresa);
                    CSucursalCollectionNewCSucursal = em.merge(CSucursalCollectionNewCSucursal);
                    if (oldIdEmpresaOfCSucursalCollectionNewCSucursal != null && !oldIdEmpresaOfCSucursalCollectionNewCSucursal.equals(CEmpresa)) {
                        oldIdEmpresaOfCSucursalCollectionNewCSucursal.getCSucursalCollection().remove(CSucursalCollectionNewCSucursal);
                        oldIdEmpresaOfCSucursalCollectionNewCSucursal = em.merge(oldIdEmpresaOfCSucursalCollectionNewCSucursal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = CEmpresa.getIdEmpresa();
                if (findCEmpresa(id) == null) {
                    throw new NonexistentEntityException("The cEmpresa with id " + id + " no longer exists.");
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
            CEmpresa CEmpresa;
            try {
                CEmpresa = em.getReference(CEmpresa.class, id);
                CEmpresa.getIdEmpresa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The CEmpresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CSucursal> CSucursalCollectionOrphanCheck = CEmpresa.getCSucursalCollection();
            for (CSucursal CSucursalCollectionOrphanCheckCSucursal : CSucursalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CEmpresa (" + CEmpresa + ") cannot be destroyed since the CSucursal " + CSucursalCollectionOrphanCheckCSucursal + " in its CSucursalCollection field has a non-nullable idEmpresa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SUsuario idUsuario = CEmpresa.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getCEmpresaCollection().remove(CEmpresa);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(CEmpresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CEmpresa> findCEmpresaEntities() {
        return findCEmpresaEntities(true, -1, -1);
    }

    public List<CEmpresa> findCEmpresaEntities(int maxResults, int firstResult) {
        return findCEmpresaEntities(false, maxResults, firstResult);
    }

    private List<CEmpresa> findCEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CEmpresa.class));
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

    public CEmpresa findCEmpresa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CEmpresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getCEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CEmpresa> rt = cq.from(CEmpresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
