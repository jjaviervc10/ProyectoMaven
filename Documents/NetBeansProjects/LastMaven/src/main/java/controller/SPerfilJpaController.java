
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.SAcceso;
import entity.SPerfil;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.SUsuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blueweb
 */
public class SPerfilJpaController implements Serializable {

    public SPerfilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SPerfil SPerfil) throws PreexistingEntityException, Exception {
        if (SPerfil.getSUsuarioCollection() == null) {
            SPerfil.setSUsuarioCollection(new ArrayList<SUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            SUsuario idUsuario = SPerfil.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                SPerfil.setIdUsuario(idUsuario);
            }
            
            Collection<SUsuario> attachedSUsuarioCollection = new ArrayList<SUsuario>();
            for (SUsuario SUsuarioCollectionSUsuarioToAttach : SPerfil.getSUsuarioCollection()) {
                SUsuarioCollectionSUsuarioToAttach = em.getReference(SUsuarioCollectionSUsuarioToAttach.getClass(), SUsuarioCollectionSUsuarioToAttach.getIdUsuario());
                attachedSUsuarioCollection.add(SUsuarioCollectionSUsuarioToAttach);
            }
            SPerfil.setSUsuarioCollection(attachedSUsuarioCollection);
            
            em.persist(SPerfil);
            
            if (idUsuario != null) {
                idUsuario.getSPerfilCollection().add(SPerfil);
                idUsuario = em.merge(idUsuario);
            }
            for (SUsuario SUsuarioCollectionSUsuario : SPerfil.getSUsuarioCollection()) {
                SPerfil oldIdPerfilOfSUsuarioCollectionSUsuario = SUsuarioCollectionSUsuario.getIdPerfil();
                SUsuarioCollectionSUsuario.setIdPerfil(SPerfil);
                SUsuarioCollectionSUsuario = em.merge(SUsuarioCollectionSUsuario);
                if (oldIdPerfilOfSUsuarioCollectionSUsuario != null) {
                    oldIdPerfilOfSUsuarioCollectionSUsuario.getSUsuarioCollection().remove(SUsuarioCollectionSUsuario);
                    oldIdPerfilOfSUsuarioCollectionSUsuario = em.merge(oldIdPerfilOfSUsuarioCollectionSUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSPerfil(SPerfil.getIdPerfil()) != null) {
                throw new PreexistingEntityException("SPerfil " + SPerfil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SPerfil SPerfil) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SPerfil persistentSPerfil = em.find(SPerfil.class, SPerfil.getIdPerfil());
            SUsuario idUsuarioOld = persistentSPerfil.getIdUsuario();
            SUsuario idUsuarioNew = SPerfil.getIdUsuario();
            Collection<SUsuario> SUsuarioCollectionOld = persistentSPerfil.getSUsuarioCollection();
            Collection<SUsuario> SUsuarioCollectionNew = SPerfil.getSUsuarioCollection();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                SPerfil.setIdUsuario(idUsuarioNew);
            }
            Collection<SUsuario> attachedSUsuarioCollectionNew = new ArrayList<SUsuario>();
            for (SUsuario SUsuarioCollectionNewSUsuarioToAttach : SUsuarioCollectionNew) {
                SUsuarioCollectionNewSUsuarioToAttach = em.getReference(SUsuarioCollectionNewSUsuarioToAttach.getClass(), SUsuarioCollectionNewSUsuarioToAttach.getIdUsuario());
                attachedSUsuarioCollectionNew.add(SUsuarioCollectionNewSUsuarioToAttach);
            }
            SUsuarioCollectionNew = attachedSUsuarioCollectionNew;
            SPerfil.setSUsuarioCollection(SUsuarioCollectionNew);
            SPerfil = em.merge(SPerfil);
            
            
            // Actualización de la relación con SAcceso
          /* for (SAcceso sAcceso : SPerfil.getSAccesoCollection()) {
                //SPerfil oldPerfilOfSAcceso = sAcceso.getIdPerfil();
              //  sAcceso.setIdPerfil(SPerfil);
                sAcceso = em.merge(sAcceso);
                if (oldPerfilOfSAcceso != null && !oldPerfilOfSAcceso.equals(SPerfil)) {
                    oldPerfilOfSAcceso.getSAccesoCollection().remove(sAcceso);
                    oldPerfilOfSAcceso = em.merge(oldPerfilOfSAcceso);
                }
            }*/
            
            
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getSPerfilCollection().remove(SPerfil);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getSPerfilCollection().add(SPerfil);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (SUsuario SUsuarioCollectionOldSUsuario : SUsuarioCollectionOld) {
                if (!SUsuarioCollectionNew.contains(SUsuarioCollectionOldSUsuario)) {
                    SUsuarioCollectionOldSUsuario.setIdPerfil(null);
                    SUsuarioCollectionOldSUsuario = em.merge(SUsuarioCollectionOldSUsuario);
                }
            }
            for (SUsuario SUsuarioCollectionNewSUsuario : SUsuarioCollectionNew) {
                if (!SUsuarioCollectionOld.contains(SUsuarioCollectionNewSUsuario)) {
                    SPerfil oldIdPerfilOfSUsuarioCollectionNewSUsuario = SUsuarioCollectionNewSUsuario.getIdPerfil();
                    SUsuarioCollectionNewSUsuario.setIdPerfil(SPerfil);
                    SUsuarioCollectionNewSUsuario = em.merge(SUsuarioCollectionNewSUsuario);
                    if (oldIdPerfilOfSUsuarioCollectionNewSUsuario != null && !oldIdPerfilOfSUsuarioCollectionNewSUsuario.equals(SPerfil)) {
                        oldIdPerfilOfSUsuarioCollectionNewSUsuario.getSUsuarioCollection().remove(SUsuarioCollectionNewSUsuario);
                        oldIdPerfilOfSUsuarioCollectionNewSUsuario = em.merge(oldIdPerfilOfSUsuarioCollectionNewSUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = SPerfil.getIdPerfil();
                if (findSPerfil(id) == null) {
                    throw new NonexistentEntityException("The sPerfil with id " + id + " no longer exists.");
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
            SPerfil SPerfil;
            try {
                SPerfil = em.getReference(SPerfil.class, id);
                SPerfil.getIdPerfil();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The SPerfil with id " + id + " no longer exists.", enfe);
            }
            
            SUsuario idUsuario = SPerfil.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getSPerfilCollection().remove(SPerfil);
                idUsuario = em.merge(idUsuario);
            }
            
            // Eliminar relación con SAcceso
           /* Collection<SAcceso> SAccesoCollection = SPerfil.getSAccesoCollection();
            for (SAcceso sAcceso : SAccesoCollection) {
             //   sAcceso.setIdPerfil(null);
                sAcceso = em.merge(sAcceso);
            }
            */
            
            Collection<SUsuario> SUsuarioCollection = SPerfil.getSUsuarioCollection();
            for (SUsuario SUsuarioCollectionSUsuario : SUsuarioCollection) {
                SUsuarioCollectionSUsuario.setIdPerfil(null);
                SUsuarioCollectionSUsuario = em.merge(SUsuarioCollectionSUsuario);
            }
            em.remove(SPerfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SPerfil> findSPerfilEntities() {
        return findSPerfilEntities(true, -1, -1);
    }

    public List<SPerfil> findSPerfilEntities(int maxResults, int firstResult) {
        return findSPerfilEntities(false, maxResults, firstResult);
    }

    private List<SPerfil> findSPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SPerfil.class));
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

    public SPerfil findSPerfil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SPerfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getSPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SPerfil> rt = cq.from(SPerfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
