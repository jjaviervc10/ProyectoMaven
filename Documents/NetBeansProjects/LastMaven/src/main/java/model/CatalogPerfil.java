package model;

import data.PoolDB;
import objetos.Perfil;
import objetos.Acceso;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.Produces;
import objetos.PerfilAcceso;
import respuestas.RespuestaPerfil;

@ManagedBean(name = "catalogPerfil")  // Nombre del bean para usar en el contexto de JSF con EL
@ApplicationScoped  // Escoge el alcance según tus necesidades

public class CatalogPerfil {

    public RespuestaPerfil getListaPerfil() {
        List<Perfil> listaPerfiles = new ArrayList<>();
        RespuestaPerfil respuestaPerfil = null;
        PreparedStatement stmt = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = PoolDB.getConnection("MyDB");
            String query = "SELECT sPerfil.idPerfil, sPerfil.nombrePerfil, sPerfil.descripcionPerfil, sPerfil.activo, sPerfil.fechaAlta, "
                    + "sPerfil.fechaBaja, sPerfil.fechaServidor, sPerfil.idUsuario, sUsuario.usuario "
                    + "FROM sPerfil " // Asegúrate de que haya un espacio aquí
                    + "JOIN sUsuario ON sPerfil.idUsuario = sUsuario.idUsuario;";
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            boolean tieneResultados = false;

            while (rs.next()) {
                tieneResultados = true;

                Perfil perfil = new Perfil();
                perfil.setIdPerfil(rs.getInt("idPerfil"));
                perfil.setNombrePerfil(rs.getString("nombrePerfil"));
                perfil.setDescripcionPerfil(rs.getString("descripcionPerfil"));
                perfil.setActivo(rs.getBoolean("activo"));
                perfil.setFechaBaja(rs.getDate("fechaBaja"));
                perfil.setFechaServidor(rs.getDate("fechaServidor"));
                perfil.setFechaAlta(rs.getDate("fechaAlta"));
                //perfil.setIdUsuario(rs.getInt("idUsuario"));
                perfil.setNombreUsuario(rs.getString("usuario"));
                listaPerfiles.add(perfil);
                //dualistaPerfilesSource.add(perfil);
            }

            Acceso acceso = new Acceso();

            if (tieneResultados) {

                respuestaPerfil = new RespuestaPerfil(0, "Exitoso", acceso, listaPerfiles, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            } else {

                respuestaPerfil = new RespuestaPerfil(1, "Advertencia");
            }
        } catch (SQLException e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);

            respuestaPerfil = new RespuestaPerfil(-1, "Error SQL: " + e.getLocalizedMessage());
        } catch (NamingException e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);

            respuestaPerfil = new RespuestaPerfil(-2, "Error de Naming: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);

            respuestaPerfil = new RespuestaPerfil(-3, "Error inesperado: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                Logger.getLogger(CatalogPerfil.class.getName()).log(Level.WARNING, "Error al cerrar recursos", e);
            }
        }

        return respuestaPerfil;
    }

    public RespuestaPerfil getListaAccesos(int perfilSelecCatalog) {
        List<Acceso> listaAccesos = new ArrayList<>();
        List<Acceso> dualistaAccesosSource = new ArrayList<>();
        List<Acceso> dualistaAccesosTarget = new ArrayList<>();
        RespuestaPerfil respuestaPerfil = null;
        Acceso acceso = new Acceso();
        PreparedStatement stmt = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = PoolDB.getConnection("MyDB");

            // Obtener todos los accesos disponibles (no asignados)
            String queryAccesosDisponibles = "SELECT sAcceso.idModulo, sAcceso.idAcceso, sAcceso.nombreAcceso, sAcceso.orden, sAcceso.activo, "
                    + "sAcceso.fechaAlta, sAcceso.fechaBaja "
                    + "FROM sAcceso "
                    + "WHERE sAcceso.idAcceso NOT IN (SELECT idAcceso FROM rPerfilAcceso WHERE idPerfil = ?)";
            PreparedStatement stmtDisponibles = connection.prepareStatement(queryAccesosDisponibles);
            stmtDisponibles.setInt(1, perfilSelecCatalog);
            ResultSet rsDisponibles = stmtDisponibles.executeQuery();

            while (rsDisponibles.next()) {
                Acceso acceso1 = new Acceso();
                acceso1.setidModulo(rsDisponibles.getInt("idModulo"));
                acceso1.setidAcceso(rsDisponibles.getInt("idAcceso"));
                acceso1.setNombreAcceso(rsDisponibles.getString("nombreAcceso"));
                acceso1.setOrden(rsDisponibles.getInt("orden"));
                acceso1.setActivo(rsDisponibles.getBoolean("activo"));
                acceso1.setFechaAlta(rsDisponibles.getDate("fechaAlta"));
                acceso1.setFechaBaja(rsDisponibles.getDate("fechaBaja"));
                dualistaAccesosSource.add(acceso1);
            }

              // Después del ciclo, recorres e imprimes todos los elementos de la colección
            for (Acceso accesoImpresion : dualistaAccesosSource) {
                System.out.println("Accesos  disponibles en listSource ");
                System.out.println(accesoImpresion);
            }

            // Obtener los accesos asignados a este perfil
            String queryAccesosAsignados = "SELECT sAcceso.idModulo, sAcceso.idAcceso, sAcceso.nombreAcceso, sAcceso.orden, sAcceso.activo, "
                    + "sAcceso.fechaAlta, sAcceso.fechaBaja "
                    + "FROM sAcceso "
                    + "JOIN rPerfilAcceso ON sAcceso.idAcceso = rPerfilAcceso.idAcceso "
                    + "WHERE rPerfilAcceso.idPerfil = ?";
            PreparedStatement stmtAsignados = connection.prepareStatement(queryAccesosAsignados);
            stmtAsignados.setInt(1, perfilSelecCatalog);
            ResultSet rsAsignados = stmtAsignados.executeQuery();

            while (rsAsignados.next()) {
                Acceso accesoAsignado = new Acceso();
                accesoAsignado.setidModulo(rsAsignados.getInt("idModulo"));
                accesoAsignado.setidAcceso(rsAsignados.getInt("idAcceso"));
                accesoAsignado.setNombreAcceso(rsAsignados.getString("nombreAcceso"));
                accesoAsignado.setOrden(rsAsignados.getInt("orden"));
                accesoAsignado.setActivo(rsAsignados.getBoolean("activo"));
                accesoAsignado.setFechaAlta(rsAsignados.getDate("fechaAlta"));
                accesoAsignado.setFechaBaja(rsAsignados.getDate("fechaBaja"));
                dualistaAccesosTarget.add(accesoAsignado);
            }

            // Después del ciclo, recorres e imprimes todos los elementos de la colección
            for (Acceso accesoImpresion : dualistaAccesosTarget) {
                System.out.println("Accesos asignados obtenido dualistaAccesosTarges mediante consulta: ");
                System.out.println(accesoImpresion);
            }

            if (!dualistaAccesosSource.isEmpty() || !dualistaAccesosTarget.isEmpty()) {
                respuestaPerfil = new RespuestaPerfil(0, "Exitoso", new Acceso(), new ArrayList<>(), listaAccesos, dualistaAccesosSource, dualistaAccesosTarget);
            } else {
                respuestaPerfil = new RespuestaPerfil(1, "No se encontraron accesos.");
            }
        } catch (SQLException e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);
            respuestaPerfil = new RespuestaPerfil(-1, "Error SQL: " + e.getLocalizedMessage());
        } catch (NamingException e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);
            respuestaPerfil = new RespuestaPerfil(-2, "Error de Naming: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);
            respuestaPerfil = new RespuestaPerfil(-3, "Error inesperado: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                } 
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(CatalogPerfil.class.getName()).log(Level.WARNING, "Error al cerrar recursos", e);
            }
        } 

        return respuestaPerfil;
    } 

    public RespuestaPerfil updatePerfil(Perfil perfil, int idUsuario, int idPerfil) {
        Connection connection = null;
        RespuestaPerfil respuestaPerfil;

        try {
            connection = PoolDB.getConnection("MyDB");
            String query = "UPDATE sPerfil SET nombrePerfil = ?, descripcionPerfil = ?, activo = ?, fechaAlta = ? , fechaBaja = ?,fechaServidor = ? ,idUsuario = ? WHERE idPerfil = ?";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, perfil.getNombrePerfil());
            stmt.setString(2, perfil.getDescripcionPerfil());
            stmt.setBoolean(3, perfil.getActivo());

            if (perfil.getFechaAlta() != null) {
                stmt.setDate(4, new java.sql.Date(perfil.getFechaAlta().getTime()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            if (perfil.getFechaBaja() != null) {
                stmt.setDate(5, new java.sql.Date(perfil.getFechaBaja().getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            if (perfil.getFechaServidor() != null) {
                stmt.setTimestamp(6, new java.sql.Timestamp(perfil.getFechaServidor().getTime()));
            } else {
                stmt.setNull(6, java.sql.Types.TIMESTAMP);
            }

            stmt.setInt(7, idUsuario);
            stmt.setInt(8, idPerfil);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                respuestaPerfil = new RespuestaPerfil(0, "Actualización exitosa");
            } else {
                respuestaPerfil = new RespuestaPerfil(1, "No se encontró la perfil para actualizar");
            }

        } catch (SQLException e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);
            respuestaPerfil = new RespuestaPerfil(-1, "Error SQL: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);
            respuestaPerfil = new RespuestaPerfil(-2, "Error inesperado: " + e.getMessage());
        }

        return respuestaPerfil;

    }

    public RespuestaPerfil insertPerfil(Perfil nuevoPerfil, int idUsuario) {
        RespuestaPerfil respuestaPerfil;
        Connection connection = null;                                                                                                                                                                                
        String query = "INSERT INTO sPerfil (nombrePerfil, descripcionPerfil, activo, fechaAlta, fechaBaja, fechaServidor, idUsuario) VALUES (?, ?,1, getdate(),getdate(),getdate(), ?)";

        try {
            connection = PoolDB.getConnection("MyDB");
            PreparedStatement stmtPerfil = connection.prepareStatement(query);

            stmtPerfil.setString(1, nuevoPerfil.getNombrePerfil());
            stmtPerfil.setString(2, nuevoPerfil.getDescripcionPerfil());
            stmtPerfil.setInt(3, idUsuario);

            int rowsAffected = stmtPerfil.executeUpdate();
            respuestaPerfil = (rowsAffected > 0)
                    ? new RespuestaPerfil(0, "Inserción exitosa")
                    : new RespuestaPerfil(1, "No se pudo insertar el perfil");

        } catch (SQLException e) {
            respuestaPerfil = new RespuestaPerfil(-1, "Error SQL: " + e.getLocalizedMessage());

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            respuestaPerfil = new RespuestaPerfil(-2, "Error inesperado: " + e.getMessage());
        } finally {
            // Asegúrando cerrar la conexión en el bloque finally
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(CatalogPerfil.class.getName()).log(Level.WARNING, "Error al cerrar la conexión", e);
            }
        }

        return respuestaPerfil;
    }

    public RespuestaPerfil deletePerfiles(Perfil PerfilAEliminar) {
        RespuestaPerfil respuestaPerfil = null;
        String query = "DELETE FROM sPerfil WHERE idPerfil = ?";

        try (Connection connection = PoolDB.getConnection("MyDB"); PreparedStatement stmt = connection.prepareStatement(query)) {

            String checkQuery = "SELECT COUNT(*) FROM sPerfil WHERE idPerfil = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, PerfilAEliminar.getIdPerfil());
                ResultSet checkRs = checkStmt.executeQuery();
                if (checkRs.next()) {
                    int count = checkRs.getInt(1);

                    if (count == 0) {
                        return new RespuestaPerfil(1, "No se encontró el perfil para eliminar en la base de datos");
                    }
                }
            }

            stmt.setInt(1, PerfilAEliminar.getIdPerfil());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Filas afectadas: " + rowsAffected);

            if (rowsAffected > 0) {
                respuestaPerfil = new RespuestaPerfil(0, "Eliminación exitosa");

            } else {
                respuestaPerfil = new RespuestaPerfil(1, "No se encontró el perfil para eliminar en la base de datos");

            }

        } catch (SQLException e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);
            respuestaPerfil = new RespuestaPerfil(-1, "Error SQL referencia (foreign key): " + e.getLocalizedMessage());

        } catch (Exception e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);
            respuestaPerfil = new RespuestaPerfil(-2, "Error inesperado: " + e.getMessage());

        }

        return respuestaPerfil;
    }

    public RespuestaPerfil guardarAccesosAsignados(int perfilId, List<Acceso> accesosAsignados, int idUsuario) {
        System.out.println("Valor de perfilId recibido guardar acceso: " + perfilId);
        Connection connection = null;
        RespuestaPerfil respuestaPerfil = null;
        String query = "INSERT INTO rPerfilAcceso (idPerfil, idAcceso, idUsuario) VALUES (?, ?,?)";

        // Verifica si la lista de accesos asignados no está vacía
        if (accesosAsignados.isEmpty()) {
            return new RespuestaPerfil(1, "No se seleccionaron accesos para asignar.");
        }
        try {
            connection = PoolDB.getConnection("MyDB");

            // Iniciar transacción para asegurarse de que todo se guarda correctamente
            connection.setAutoCommit(false);

            // Preparar el statement para insertar cada acceso asignado
            PreparedStatement stmt = connection.prepareStatement(query);

            // Insertar cada acceso asignado para el perfil
            for (Acceso acceso : accesosAsignados) {
                System.out.println("Insertando acceso: " + acceso.getidAcceso() + " para perfil: " + perfilId);
                stmt.setInt(1, perfilId);  // El perfil al que se le asigna el acceso
                stmt.setInt(2, acceso.getidAcceso());  // El ID del acceso
                stmt.setInt(3, idUsuario);
                stmt.addBatch();  // Agregar a batch
            }

            // Ejecutar el batch de inserciones
            int[] affectedRows = stmt.executeBatch();

            // Si al menos una fila fue afectada, confirmar la transacción
            if (affectedRows.length > 0) {
                connection.commit();
                respuestaPerfil = new RespuestaPerfil(0, "Accesos asignados correctamente.");
            } else {
                // Si no hubo filas afectadas, revertir los cambios
                connection.rollback();
                respuestaPerfil = new RespuestaPerfil(1, "No se pudieron asignar accesos.");
            }

        } catch (SQLException e) {
            // Si ocurre un error, revertir la transacción
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, ex);
            }
            respuestaPerfil = new RespuestaPerfil(-1, "Error SQL: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);
            respuestaPerfil = new RespuestaPerfil(-2, "Error inesperado: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(CatalogPerfil.class.getName()).log(Level.WARNING, "Error al cerrar la conexión", e);
            }
        }

        return respuestaPerfil;
    }
   
   public List<Acceso> obtenerAccesosDisponibles(int perfilId) {
    System.out.println("Valor de perfilId recibido: " + perfilId);
    List<Acceso> dualistaAccesosSource = new ArrayList<>();
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    String query = "SELECT sAcceso.idModulo, sAcceso.idAcceso, sAcceso.nombreAcceso, sAcceso.orden, sAcceso.activo, "
                 + "sAcceso.fechaAlta, sAcceso.fechaBaja "
                 + "FROM sAcceso "
                 + "WHERE sAcceso.idAcceso NOT IN (SELECT idAcceso FROM rPerfilAcceso WHERE idPerfil = ?)";

    try {
        connection = PoolDB.getConnection("MyDB");
        stmt = connection.prepareStatement(query);
        stmt.setInt(1, perfilId);
        rs = stmt.executeQuery();

        while (rs.next()) {
            Acceso acceso = new Acceso();
            acceso.setidModulo(rs.getInt("idModulo"));
            acceso.setidAcceso(rs.getInt("idAcceso"));
            acceso.setNombreAcceso(rs.getString("nombreAcceso"));
            acceso.setOrden(rs.getInt("orden"));
            acceso.setActivo(rs.getBoolean("activo"));
            acceso.setFechaAlta(rs.getDate("fechaAlta"));
            acceso.setFechaBaja(rs.getDate("fechaBaja"));
            dualistaAccesosSource.add(acceso);
        }
        System.out.println("Accesos source en catalog:" + dualistaAccesosSource);
        
    } catch (SQLException e) {
        Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);
    }   catch (NamingException ex) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.WARNING, "Error al cerrar recursos", e);
        }
    }

    return dualistaAccesosSource;
}
 
    public List<Acceso> obtenerAccesosAsignados(int perfilId) {
    List<Acceso> dualistaAccesosTarget = new ArrayList<>();
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    String query = "SELECT sAcceso.idModulo, sAcceso.idAcceso, sAcceso.nombreAcceso, sAcceso.orden, sAcceso.activo, "
                 + "sAcceso.fechaAlta, sAcceso.fechaBaja "
                 + "FROM sAcceso "
                 + "JOIN rPerfilAcceso ON sAcceso.idAcceso = rPerfilAcceso.idAcceso "
                 + "WHERE rPerfilAcceso.idPerfil = ?";

    try {
        connection = PoolDB.getConnection("MyDB");
        stmt = connection.prepareStatement(query);
        stmt.setInt(1, perfilId);
        rs = stmt.executeQuery();

        while (rs.next()) {
            Acceso acceso = new Acceso();
            acceso.setidModulo(rs.getInt("idModulo"));
            acceso.setidAcceso(rs.getInt("idAcceso"));
            acceso.setNombreAcceso(rs.getString("nombreAcceso"));
            acceso.setOrden(rs.getInt("orden"));
            acceso.setActivo(rs.getBoolean("activo"));
            acceso.setFechaAlta(rs.getDate("fechaAlta"));
            acceso.setFechaBaja(rs.getDate("fechaBaja"));
            dualistaAccesosTarget.add(acceso);
        }
        
        System.out.println("Se consulto la infor de source desde load accesos");
    } catch (SQLException e) {
        Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, e);
    }   catch (NamingException ex) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            Logger.getLogger(CatalogPerfil.class.getName()).log(Level.WARNING, "Error al cerrar recursos", e);
        }
    }

    return dualistaAccesosTarget;
}

    
    
}

   
