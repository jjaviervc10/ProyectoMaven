package model;

/**
 *
 * @author Blueweb
 */
import data.PoolDB;
import objetos.Sucursales;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.naming.NamingException;
import respuestas.RespuestaSucursal;

public class CatalogSucursal {

    public RespuestaSucursal getListaSucursal() {

        List<Sucursales> listaSucursales = new ArrayList<>();
        RespuestaSucursal respuestaSucursal = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            connection = PoolDB.getConnection("MyDB");

            String query = "SELECT cSucursal.idSucursal, cSucursal.nombreSucursal, cSucursal.ciudad, cSucursal.activo, "
                    + "cSucursal.estado,cSucursal.fechaAlta, cSucursal.fechaServidor,cSucursal.idEmpresa, cEmpresa.nombreEmpresa "
                    + "FROM cSucursal "
                    + "JOIN cEmpresa ON cSucursal.idEmpresa = cEmpresa.idEmpresa;";

            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            boolean tieneResultados = false;

            while (rs.next()) {

                tieneResultados = true;

                Sucursales servicio_s = new Sucursales();

                servicio_s.setIdSucursal(rs.getInt("idSucursal"));
                servicio_s.setIdEmpresa(rs.getInt("idEmpresa")); // Ahora sí se selecciona
                servicio_s.setNombreSucursal(rs.getString("nombreSucursal"));
                servicio_s.setCiudad(rs.getString("ciudad"));
                servicio_s.setStatus(rs.getBoolean("activo"));
                servicio_s.setEstado(rs.getString("estado"));
                servicio_s.setFechaAlta(rs.getDate("fechaAlta"));

                // Convertir el valor de fechaServidor de String a Timestamp
                String fechaServidorStr = rs.getString("fechaServidor");
                if (fechaServidorStr != null) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                        java.util.Date fechaServidor = sdf.parse(fechaServidorStr);
                        servicio_s.setFechaServidor(new Timestamp(fechaServidor.getTime())); // Asignar como Timestamp
                    } catch (ParseException e) {
                        e.printStackTrace();
                        servicio_s.setFechaServidor(null); // Asignar null en caso de error
                    }
                } else {
                    servicio_s.setFechaServidor(null); // Asignar null si la fecha es nula
                }

                servicio_s.setNombreEmpresa(rs.getString("nombreEmpresa"));

                listaSucursales.add(servicio_s);
            }

            if (tieneResultados) {

                respuestaSucursal = new RespuestaSucursal(0, "Exitoso", listaSucursales);
            } else {

                respuestaSucursal = new RespuestaSucursal(1, "Advertencia");
            }

        } catch (SQLException e) {
            Logger.getLogger(CatalogSucursal.class.getName()).log(Level.SEVERE, null, e);

            respuestaSucursal = new RespuestaSucursal(-1, "Error SQL: " + e.getLocalizedMessage());
        } catch (NamingException e) {
            Logger.getLogger(CatalogSucursal.class.getName()).log(Level.SEVERE, null, e);

            respuestaSucursal = new RespuestaSucursal(-2, "Error de Naming: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Logger.getLogger(CatalogSucursal.class.getName()).log(Level.SEVERE, null, e);

            respuestaSucursal = new RespuestaSucursal(-3, "Error inesperado: " + e.getMessage());
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
                Logger.getLogger(CatalogSucursal.class.getName()).log(Level.WARNING, "Error al cerrar recursos", e);
            }
        }

        return respuestaSucursal;
    }

    // Método para actualizar una sucursal en la base de datos
    public RespuestaSucursal updateSucursal(Sucursales sucursal, int idUsuario, int idSucursal) {
        Connection connection = null;
        RespuestaSucursal respuestaSucursal;

        try {
            connection = PoolDB.getConnection("MyDB");
            String query = "UPDATE cSucursal SET nombreSucursal = ?, ciudad = ?, estado = ?, fechaAlta = ? ,fechaServidor = ? ,idUsuario = ? WHERE idSucursal = ?";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, sucursal.getNombreSucursal());
            stmt.setString(2, sucursal.getCiudad());
            stmt.setString(3, sucursal.getEstado());

            if (sucursal.getFechaAlta() != null) {
                stmt.setDate(4, new java.sql.Date(sucursal.getFechaAlta().getTime()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }

            if (sucursal.getFechaServidor() != null) {
                stmt.setTimestamp(5, new java.sql.Timestamp(sucursal.getFechaServidor().getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.TIMESTAMP);
            }

            stmt.setInt(6, idUsuario);
            stmt.setInt(7, idSucursal);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                respuestaSucursal = new RespuestaSucursal(0, "Actualización exitosa");
            } else {
                respuestaSucursal = new RespuestaSucursal(1, "No se encontró la sucursal para actualizar");
            }

        } catch (SQLException e) {
            Logger.getLogger(CatalogSucursal.class.getName()).log(Level.SEVERE, null, e);
            respuestaSucursal = new RespuestaSucursal(-1, "Error SQL: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Logger.getLogger(CatalogSucursal.class.getName()).log(Level.SEVERE, null, e);
            respuestaSucursal = new RespuestaSucursal(-2, "Error inesperado: " + e.getMessage());
        }

        return respuestaSucursal;
    }

    public RespuestaSucursal insertSucursal(Sucursales nuevaSucursal, int idUsuario) {
        RespuestaSucursal respuestaSucursal;
        Connection connection = null;
        String query = "INSERT INTO cSucursal (nombreSucursal, ciudad, estado, fechaAlta, fechaServidor, idUsuario, idEmpresa) VALUES (?, ?, ?, getdate(), ?, ?, ?)";
        String queryEmpresa = "INSERT INTO cEmpresa (nombreEmpresa) VALUES (?)";

        try {
            connection = PoolDB.getConnection("MyDB");
            connection.setAutoCommit(false); // Iniciar transacción

            int idEmpresa;

            if (nuevaSucursal.getIdEmpresa() <= 0) {
                try (PreparedStatement stmtEmpresa = connection.prepareStatement(queryEmpresa, Statement.RETURN_GENERATED_KEYS)) {
                    stmtEmpresa.setString(1, "Nueva Empresa");
                    stmtEmpresa.executeUpdate();

                    try (ResultSet generatedKeys = stmtEmpresa.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            idEmpresa = generatedKeys.getInt(1); // Obtener el nuevo idEmpresa
                        } else {
                            throw new SQLException("Error al obtener el ID de la nueva servicio_s.");
                        }
                    }
                }
            } else {
                idEmpresa = nuevaSucursal.getIdEmpresa();
            }

            try (PreparedStatement stmtSucursal = connection.prepareStatement(query)) {
                stmtSucursal.setString(1, nuevaSucursal.getNombreSucursal());
                stmtSucursal.setString(2, nuevaSucursal.getCiudad());
                stmtSucursal.setString(3, nuevaSucursal.getEstado());
                if (nuevaSucursal.getFechaServidor() != null) {
                    stmtSucursal.setTimestamp(4, new java.sql.Timestamp(nuevaSucursal.getFechaServidor().getTime()));
                } else {
                    stmtSucursal.setNull(4, java.sql.Types.TIMESTAMP); // Si es null, establece como NULL en la base de datos
                }
                stmtSucursal.setInt(5, idUsuario);
                stmtSucursal.setInt(6, idEmpresa);
                int rowsAffected = stmtSucursal.executeUpdate();
                respuestaSucursal = (rowsAffected > 0)
                        ? new RespuestaSucursal(0, "Inserción exitosa")
                        : new RespuestaSucursal(1, "No se pudo insertar la sucursal");
            }

            connection.commit(); // Confirmar transacción

        } catch (SQLException e) {
            respuestaSucursal = new RespuestaSucursal(-1, "Error SQL: " + e.getLocalizedMessage());

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(CatalogSucursal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            respuestaSucursal = new RespuestaSucursal(-2, "Error inesperado: " + e.getMessage());
        } finally {
            // Asegúrando cerrar la conexión en el bloque finally
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(CatalogSucursal.class.getName()).log(Level.WARNING, "Error al cerrar la conexión", e);
            }
        }

        return respuestaSucursal;
    }

    public RespuestaSucursal deleteSucursal(Sucursales SucursalAEliminar) {
        RespuestaSucursal respuestaSucursal = null;
        String query = "DELETE FROM cSucursal WHERE idSucursal = ?";

        try (Connection connection = PoolDB.getConnection("MyDB"); PreparedStatement stmt = connection.prepareStatement(query)) {

            String checkQuery = "SELECT COUNT(*) FROM cSucursal WHERE idSucursal = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, SucursalAEliminar.getIdSucursal());
                ResultSet checkRs = checkStmt.executeQuery();
                if (checkRs.next()) {
                    int count = checkRs.getInt(1);

                    if (count == 0) {
                        return new RespuestaSucursal(1, "No se encontró la sucursal para eliminar en la base de datos");
                    }
                }
            }

            stmt.setInt(1, SucursalAEliminar.getIdSucursal());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Filas afectadas: " + rowsAffected);

            if (rowsAffected > 0) {
                respuestaSucursal = new RespuestaSucursal(0, "Eliminación exitosa");

            } else {
                respuestaSucursal = new RespuestaSucursal(1, "No se encontró la sucursal para eliminar en la base de datos");

            }

        } catch (SQLException e) {
            Logger.getLogger(CatalogSucursal.class.getName()).log(Level.SEVERE, null, e);
            respuestaSucursal = new RespuestaSucursal(-1, "Error SQL referencia (foreign key): " + e.getLocalizedMessage());

        } catch (Exception e) {
            Logger.getLogger(CatalogSucursal.class.getName()).log(Level.SEVERE, null, e);
            respuestaSucursal = new RespuestaSucursal(-2, "Error inesperado: " + e.getMessage());

        }

        return respuestaSucursal;
    }
    
    
    
}
