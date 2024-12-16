
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.SAcceso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.SModulo;
import entity.SPerfil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Blueweb
 */
public class SAccesoJpaController implements Serializable {

    public SAccesoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SAcceso SAcceso) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
             // Verificar y asociar SModulo
            SModulo idModulo = SAcceso.getIdModulo();
            if (idModulo != null) {
                idModulo = em.getReference(idModulo.getClass(), idModulo.getIdModulo());
                SAcceso.setIdModulo(idModulo);
            }
       
            
            em.persist(SAcceso);
            // Actualizar colecciones de SModulo y SPerfil
            if (idModulo != null) {
                idModulo.getSAccesoCollection().add(SAcceso);
                idModulo = em.merge(idModulo);
            }
           
            em.getTransaction().commit();
            
        } catch (Exception ex) {
            if (findSAcceso(SAcceso.getIdAcceso()) != null) {
                throw new PreexistingEntityException("SAcceso " + SAcceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SAcceso SAcceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SAcceso persistentSAcceso = em.find(SAcceso.class, SAcceso.getIdAcceso());
            SModulo idModuloOld = persistentSAcceso.getIdModulo();
            SModulo idModuloNew = SAcceso.getIdModulo();
            
         // Actualizar la relación con SModulo
            if (idModuloNew != null) {
                idModuloNew = em.getReference(idModuloNew.getClass(), idModuloNew.getIdModulo());
                SAcceso.setIdModulo(idModuloNew);
            }
           
            SAcceso = em.merge(SAcceso);
            
            // Actualizar las colecciones en SModulo y SPerfil
            if (idModuloOld != null && !idModuloOld.equals(idModuloNew)) {
                idModuloOld.getSAccesoCollection().remove(SAcceso);
                idModuloOld = em.merge(idModuloOld);
            }
            if (idModuloNew != null && !idModuloNew.equals(idModuloOld)) {
                idModuloNew.getSAccesoCollection().add(SAcceso);
                idModuloNew = em.merge(idModuloNew);
            }
         
            
            em.getTransaction().commit();
            
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = SAcceso.getIdAcceso();
                if (findSAcceso(id) == null) {
                    throw new NonexistentEntityException("The sAcceso with id " + id + " no longer exists.");
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
            SAcceso SAcceso;
            try {
                SAcceso = em.getReference(SAcceso.class, id);
                SAcceso.getIdAcceso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The SAcceso with id " + id + " no longer exists.", enfe);
            }
            
            // Eliminar de las colecciones correspondientes
            SModulo idModulo = SAcceso.getIdModulo();
            if (idModulo != null) {
                idModulo.getSAccesoCollection().remove(SAcceso);
                idModulo = em.merge(idModulo);
            }
            
            em.remove(SAcceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SAcceso> findSAccesoEntities() {
        return findSAccesoEntities(true, -1, -1);
    }

    public List<SAcceso> findSAccesoEntities(int maxResults, int firstResult) {
        return findSAccesoEntities(false, maxResults, firstResult);
    }

    private List<SAcceso> findSAccesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SAcceso.class));
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

    public SAcceso findSAcceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SAcceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getSAccesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SAcceso> rt = cq.from(SAcceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    
}
