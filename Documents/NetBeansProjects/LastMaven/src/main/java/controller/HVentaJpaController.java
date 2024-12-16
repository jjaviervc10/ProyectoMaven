/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.CEmpleado;
import entity.CEmpresa;
import entity.CSucursal;
import entity.DVenta;
import entity.HVenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.MVenta;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import objetos.Sucursales;
import respuestas.RespuestaReporteHistorico;

/**
 *
 * @author Blueweb
 */
@Named("hVentaController")
@Stateless
public class HVentaJpaController implements Serializable {

    public HVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public HVentaJpaController() {
      
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HVenta HVenta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MVenta idVenta = HVenta.getIdVenta();
            if (idVenta != null) {
                idVenta = em.getReference(idVenta.getClass(), idVenta.getIdVenta());
                HVenta.setIdVenta(idVenta);
            }
            em.persist(HVenta);
            if (idVenta != null) {
                idVenta.getHVentaCollection().add(HVenta);
                idVenta = em.merge(idVenta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHVenta(HVenta.getIdHistVenta()) != null) {
                throw new PreexistingEntityException("HVenta " + HVenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HVenta HVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HVenta persistentHVenta = em.find(HVenta.class, HVenta.getIdHistVenta());
            MVenta idVentaOld = persistentHVenta.getIdVenta();
            MVenta idVentaNew = HVenta.getIdVenta();
            if (idVentaNew != null) {
                idVentaNew = em.getReference(idVentaNew.getClass(), idVentaNew.getIdVenta());
                HVenta.setIdVenta(idVentaNew);
            }
            HVenta = em.merge(HVenta);
            if (idVentaOld != null && !idVentaOld.equals(idVentaNew)) {
                idVentaOld.getHVentaCollection().remove(HVenta);
                idVentaOld = em.merge(idVentaOld);
            }
            if (idVentaNew != null && !idVentaNew.equals(idVentaOld)) {
                idVentaNew.getHVentaCollection().add(HVenta);
                idVentaNew = em.merge(idVentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = HVenta.getIdHistVenta();
                if (findHVenta(id) == null) {
                    throw new NonexistentEntityException("The hVenta with id " + id + " no longer exists.");
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
            HVenta HVenta;
            try {
                HVenta = em.getReference(HVenta.class, id);
                HVenta.getIdHistVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The HVenta with id " + id + " no longer exists.", enfe);
            }
            MVenta idVenta = HVenta.getIdVenta();
            if (idVenta != null) {
                idVenta.getHVentaCollection().remove(HVenta);
                idVenta = em.merge(idVenta);
            }
            em.remove(HVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HVenta> findHVentaEntities() {
        return findHVentaEntities(true, -1, -1);
    }

    public List<HVenta> findHVentaEntities(int maxResults, int firstResult) {
        return findHVentaEntities(false, maxResults, firstResult);
    }

    private List<HVenta> findHVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HVenta.class));
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

    public HVenta findHVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getHVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HVenta> rt = cq.from(HVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

  

    public RespuestaReporteHistorico<List<HVenta>> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin) {
   
    Timestamp fechaInicioTimestamp = new Timestamp(fechaInicio.getTime());
    Timestamp fechaFinTimestamp = new Timestamp(fechaFin.getTime());

    RespuestaReporteHistorico<List<HVenta>> respuestaConsulta = null;
    EntityManager em = null;

    try {
       
        em = getEntityManager();

       
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<MVenta> mVentaRoot = cq.from(MVenta.class);

        
        Join<MVenta, CSucursal> sucursalJoin = mVentaRoot.join("idSucursal", JoinType.INNER);
        Join<CSucursal, CEmpresa> empresaJoin = sucursalJoin.join("idEmpresa", JoinType.INNER);
        Join<MVenta, CEmpleado> empleadoJoin = mVentaRoot.join("idEmpleado", JoinType.INNER);

       
        Join<MVenta, DVenta> ventaDetalleJoin = mVentaRoot.join("dVentaCollection", JoinType.LEFT);

       
        Predicate fechaPredicate = cb.between(mVentaRoot.get("fechaVenta"), fechaInicioTimestamp, fechaFinTimestamp);
        cq.where(fechaPredicate);

       
        Expression<String> nombreCompleto = cb.concat(empleadoJoin.get("nombre"), " ");
        nombreCompleto = cb.concat(nombreCompleto, cb.coalesce(empleadoJoin.get("apellidoPaterno"), ""));
        nombreCompleto = cb.concat(nombreCompleto, " ");
        nombreCompleto = cb.concat(nombreCompleto, cb.coalesce(empleadoJoin.get("apellidoMaterno"), ""));

        Expression<Integer> cantidadProductos = cb.sum(cb.coalesce(ventaDetalleJoin.get("cantidad"), 0));

        
        cq.select(cb.array(
                mVentaRoot.get("idVenta"), // idVenta
                mVentaRoot.get("totalVenta"), // totalVenta
                sucursalJoin.get("nombreSucursal"), // nombreSucursal
                empresaJoin.get("nombreEmpresa"), // nombreEmpresa
                nombreCompleto, // nombreCompletoEmpleado
                cantidadProductos // suma de cantidadProductos
        ));

      
        cq.groupBy(
                mVentaRoot.get("idVenta"),
                mVentaRoot.get("totalVenta"),
                sucursalJoin.get("nombreSucursal"),
                empresaJoin.get("nombreEmpresa"),
                empleadoJoin.get("nombre"),
                empleadoJoin.get("apellidoPaterno"),
                empleadoJoin.get("apellidoMaterno")
        );

        
        TypedQuery<Object[]> query = em.createQuery(cq);

        
        List<Object[]> resultados = query.getResultList();


        List<HVenta> ventas = new ArrayList<>();

       
        for (Object[] resultado : resultados) {
            Integer idVenta = (Integer) resultado[0];  // idVenta
            BigDecimal totalVenta = (BigDecimal) resultado[1];  // totalVenta
            String nombreSucursal = (String) resultado[2];  // nombreSucursal
            String nombreEmpresa = (String) resultado[3];  // nombreEmpresa
            String nombreCompletoEmpleado = (String) resultado[4];  // nombreCompletoEmpleado
            Integer cantidadProductosValor = (Integer) resultado[5];  // cantidadProductos


            HVenta venta = new HVenta();

         
            venta.setTotalVenta(totalVenta);
            venta.setNombreSucursal(nombreSucursal);
            venta.setNombreEmpresa(nombreEmpresa);
            venta.setNombreCompletoEmpleado(nombreCompletoEmpleado);
            venta.setCantidadProductos(cantidadProductosValor);

  
            MVenta mVenta = new MVenta();
            mVenta.setIdVenta(idVenta);
            venta.setIdVenta(mVenta);

     
            ventas.add(venta);
        }

       
        if (ventas.isEmpty()) {
   
            respuestaConsulta = new RespuestaReporteHistorico<>(1, "No se encontraron ventas en el rango de fechas especificado.", ventas);
        } else {
         
            respuestaConsulta = new RespuestaReporteHistorico<>(0, "Consulta realizada con éxito.", ventas);
        }

    } catch (PersistenceException e) {
     
        respuestaConsulta = new RespuestaReporteHistorico<>(-1, "Error en la base de datos: " + e.getMessage(), null);
        System.err.println("Error de persistencia: " + e.getMessage());
    } catch (Exception e) {
       
        respuestaConsulta = new RespuestaReporteHistorico<>(-1, "Error inesperado: " + e.getMessage(), null);
        System.err.println("Error inesperado: " + e.getMessage());
    } finally {
       
        if (em != null) {
            try {
                em.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar el EntityManager: " + e.getMessage());
            }
        }
    }

    
    return respuestaConsulta;
}

   public RespuestaReporteHistorico<List<HVenta>> obtenerVentasPorFiltros(Timestamp fechaInicio, Timestamp fechaFin) {
  
    Timestamp fechaInicioTimestamp = new Timestamp(fechaInicio.getTime());
    Timestamp fechaFinTimestamp = new Timestamp(fechaFin.getTime());

    EntityManager em = null;
    RespuestaReporteHistorico<List<HVenta>> respuestaConsulta;
    List<HVenta> ventas = null;

    try {
        em = getEntityManager();
        String query = "EXEC sp_Javier_MoverVentasAHistorico @Accion = ?, @fechaInicial = ?, @fechaFinal = ?";

        Query nativeQuery = em.createNativeQuery(query, "HVentaMapping"); 
        nativeQuery.setParameter(1, "consultar");
        nativeQuery.setParameter(2, fechaInicioTimestamp);
        nativeQuery.setParameter(3, fechaFinTimestamp);

        ventas = nativeQuery.getResultList();

        if (ventas.isEmpty()) {
            respuestaConsulta = new RespuestaReporteHistorico<>(1, "No se encontraron ventas en el rango especificado.", ventas);
        } else {
            for (HVenta venta : ventas) {
                System.out.println("idHistVenta: " + venta.getIdHistVenta());
                System.out.println("Nombre Empresa: " + venta.getNombreEmpresa());
                System.out.println("Nombre Sucursal: " + venta.getNombreSucursal());
                System.out.println("Nombre Empleado: " + venta.getNombreCompletoEmpleado());
                System.out.println("Cantidad Productos: " + venta.getCantidadProductos());
                System.out.println("Total Venta: " + venta.getTotalVenta());
            }
            respuestaConsulta = new RespuestaReporteHistorico<>(0, "Consulta realizada con éxito en JPA.", ventas);
        }

    } catch (PersistenceException e) {
        System.err.println("Error de persistencia: " + e.getMessage());
        respuestaConsulta = new RespuestaReporteHistorico<>(-1, "Error en la base de datos: " + e.getLocalizedMessage(), null);
    } catch (Exception e) {
        System.err.println("Error inesperado: " + e.getMessage());
        respuestaConsulta = new RespuestaReporteHistorico<>(-2, "Error inesperado: " + e.getMessage(), null);
    } finally {
        if (em != null) {
            try {
                em.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar el EntityManager: " + e.getMessage());
            }
        }
    }

    return respuestaConsulta;
}

   
}
