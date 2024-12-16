package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.RPerfilAcceso;
import entity.RPerfilAccesoPK;
import entity.SAcceso;
import entity.SPerfil;
import entity.SUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import objetos.Perfil;

/**
 *
 * @author Blueweb
 */
public class RPerfilAccesoJpaController implements Serializable {

    public RPerfilAccesoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Método para verificar si la relación ya existe
    public boolean existeCombinacion(int idPerfil, int idAcceso) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<RPerfilAcceso> root = cq.from(RPerfilAcceso.class);
            cq.select(cb.count(root));
            cq.where(cb.equal(root.get("rPerfilAccesoPK").get("idPerfil"), idPerfil),
                    cb.equal(root.get("rPerfilAccesoPK").get("idAcceso"), idAcceso));
            Query query = em.createQuery(cq);
            return (Long) query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public void create(Integer selectedPerfil,int accesosSeleccionados, Integer idUsuarioSesion) throws Exception {

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            SUsuario usuario = em.find(SUsuario.class, idUsuarioSesion);
            if (usuario == null) {
                throw new Exception("Usuario no encontrado.");
            }

                RPerfilAccesoPK pk = new RPerfilAccesoPK();
                pk.setIdPerfil(selectedPerfil);
                pk.setIdAcceso(accesosSeleccionados);

        
                    RPerfilAcceso rPerfilAcceso = new RPerfilAcceso();
                    rPerfilAcceso.setRPerfilAccesoPK(pk);
                    rPerfilAcceso.setIdUsuario(usuario); // Asociar el usuario
                    rPerfilAcceso.setFechaServidor(new Date()); // Establecer fecha actual

                    // Persistir la nueva relación
                    em.persist(rPerfilAcceso);
                
        

         
            // Confirmar la transacción
            em.getTransaction().commit();
        } catch (Exception ex) {

            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }
    
    

    public void edit(RPerfilAcceso RPerfilAcceso) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            // Actualizar la relación con el usuario
            if (RPerfilAcceso.getIdUsuario() != null) {
                RPerfilAcceso.setIdUsuario(em.getReference(SUsuario.class, RPerfilAcceso.getIdUsuario().getIdUsuario()));
            }

            RPerfilAcceso = em.merge(RPerfilAcceso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RPerfilAccesoPK id = RPerfilAcceso.getRPerfilAccesoPK();
                if (findRPerfilAcceso(id) == null) {
                    throw new Exception("The rPerfilAcceso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
 public void destroy(RPerfilAccesoPK id) throws Exception {
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();

        // Verificar si la entidad existe antes de intentar eliminarla
        RPerfilAcceso RPerfilAcceso = em.find(RPerfilAcceso.class, id);
        
        if (RPerfilAcceso == null) {
            // La entidad no existe, lanzar una excepción o simplemente salir
            throw new Exception("The RPerfilAcceso with id " + id + " no longer exists.");
        }

        // Si existe, eliminar la entidad
        em.remove(RPerfilAcceso);
        em.getTransaction().commit();
    } catch (Exception e) {
        // Manejar excepciones aquí
        if (em != null) {
            em.getTransaction().rollback(); // Rollback si ocurre un error
        }
        throw new Exception("An error occurred while trying to delete the RPerfilAcceso with id " + id, e);
    } finally {
        if (em != null) {
            em.close();
        }
    }
}
  


 


    public List<RPerfilAcceso> findRPerfilAccesoEntities() {
        return findRPerfilAccesoEntities(true, -1, -1);
    }

    public List<RPerfilAcceso> findRPerfilAccesoEntities(int maxResults, int firstResult) {
        return findRPerfilAccesoEntities(false, maxResults, firstResult);
    }

    private List<RPerfilAcceso> findRPerfilAccesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RPerfilAcceso.class));
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

    public RPerfilAcceso findRPerfilAcceso(RPerfilAccesoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RPerfilAcceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getRPerfilAccesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RPerfilAcceso> rt = cq.from(RPerfilAcceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    // Método para obtener accesos disponibles (no asignados)
    public List<SAcceso> findAccesosDisponibles(int idPerfil) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<SAcceso> cq = cb.createQuery(SAcceso.class);
            Root<SAcceso> accesoRoot = cq.from(SAcceso.class);

            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<RPerfilAcceso> perfilAccesoRoot = subquery.from(RPerfilAcceso.class);
            subquery.select(perfilAccesoRoot.get("rPerfilAccesoPK").get("idAcceso"));
            subquery.where(cb.equal(perfilAccesoRoot.get("rPerfilAccesoPK").get("idPerfil"), idPerfil));  // Filtramos por idPerfil

            cq.where(cb.not(accesoRoot.get("idAcceso").in(subquery)));

            Query query = em.createQuery(cq);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

// Método para obtener accesos asignados
    public List<SAcceso> findAccesosAsignados(int idPerfil) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<SAcceso> cq = cb.createQuery(SAcceso.class);
            Root<SAcceso> accesoRoot = cq.from(SAcceso.class);

            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<RPerfilAcceso> perfilAccesoRoot = subquery.from(RPerfilAcceso.class);
            subquery.select(perfilAccesoRoot.get("rPerfilAccesoPK").get("idAcceso"));
            subquery.where(cb.equal(perfilAccesoRoot.get("rPerfilAccesoPK").get("idPerfil"), idPerfil));

            cq.where(accesoRoot.get("idAcceso").in(subquery));

            Query query = em.createQuery(cq);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
  

}
