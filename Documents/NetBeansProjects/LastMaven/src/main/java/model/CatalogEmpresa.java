package model;
 
import data.PoolDB;
import objetos.Empresas; 
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import respuestas.RespuestaEmpresa;

public class CatalogEmpresa {

    public RespuestaEmpresa getListaEmpresa() {
        List<Empresas> listaEmpresas = new ArrayList<>();
        RespuestaEmpresa respuestaEmpresa = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            connection = PoolDB.getConnection("MyDB");
            String query = "SELECT cEmpresa.idEmpresa, cEmpresa.claveEmpresa, cEmpresa.nombreEmpresa, cEmpresa.activo, cEmpresa.fechaAlta, cEmpresa.fechaServidor FROM cEmpresa;";
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            boolean tieneResultados = false;

            while (rs.next()) {
                tieneResultados = true;

                Empresas empresa = new Empresas();
                empresa.setIdEmpresa(rs.getInt("idEmpresa"));
                empresa.setClaveEmpresa(rs.getString("claveEmpresa"));
                empresa.setNombreEmpresa(rs.getString("nombreEmpresa"));
                empresa.setStatusEmpresa(rs.getBoolean("activo"));
                empresa.setFechaAltaEmpresa(rs.getDate("fechaAlta"));

                // Convertir el valor de fechaServidor de String a Timestamp
                String fechaServidorStr = rs.getString("fechaServidor");
                if (fechaServidorStr != null) {
                    try {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                        java.util.Date fechaServidor = sdf.parse(fechaServidorStr);
                        empresa.setFechaServidor(new Timestamp(fechaServidor.getTime()));
                        
// Asignar como Timestamp
                    } catch (ParseException e) {
                        e.printStackTrace();
                        empresa.setFechaServidor(null); // Asignar null en caso de error
                    } 
                } else {
                    empresa.setFechaServidor(null); // Asignar null si la fecha es nula
                }

                listaEmpresas.add(empresa);
            }

            if (tieneResultados) {
                respuestaEmpresa = new RespuestaEmpresa(0, "Exitoso", listaEmpresas);
            } else {
                respuestaEmpresa = new RespuestaEmpresa(1, "Advertencia");
            }

        } catch (SQLException e) {
            Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.SEVERE, null, e);
            respuestaEmpresa = new RespuestaEmpresa(-1, "Error SQL: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.SEVERE, null, e);
            respuestaEmpresa = new RespuestaEmpresa(-2, "Error inesperado: " + e.getMessage());
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
                Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.WARNING, "Error al cerrar recursos", e);
            }
        }

        return respuestaEmpresa;
    }

    // Método para actualizar una empresa en la base de datos
    public RespuestaEmpresa updateEmpresa(Empresas empresa, int idUsuario, int idEmpresa) {
        RespuestaEmpresa respuestaEmpresa;
        Connection connection = null;

        try {
            connection = PoolDB.getConnection("MyDB");
        String query = "UPDATE cEmpresa SET claveEmpresa = ?, nombreEmpresa = ?, activo = ?, fechaAlta = ?, fechaServidor = ?, idUsuario = ? WHERE idEmpresa = ?";
        PreparedStatement stmt = connection.prepareStatement(query);

        
        stmt.setString(1, empresa.getClaveEmpresa());
        stmt.setString(2, empresa.getNombreEmpresa());
        stmt.setBoolean(3, empresa.getStatusEmpresa());

      
        if (empresa.getFechaAltaEmpresa() != null) {
            stmt.setDate(4, new java.sql.Date(empresa.getFechaAltaEmpresa().getTime())); 
        } else {
            stmt.setNull(4, java.sql.Types.DATE); // Manejar si la fecha es null
        }

        
        if (empresa.getFechaServidor() != null) {
            stmt.setTimestamp(5, new java.sql.Timestamp(empresa.getFechaServidor().getTime())); 
        } else {
            stmt.setNull(5, java.sql.Types.TIMESTAMP); 
        }

        stmt.setInt(6, idUsuario);
        stmt.setInt(7, idEmpresa); 

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            respuestaEmpresa = new RespuestaEmpresa(0, "Actualización exitosa");
        } else {
            respuestaEmpresa = new RespuestaEmpresa(1, "No se encontró la empresa para actualizar");
        }

    } catch (SQLException e) {
        Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.SEVERE, null, e);
        respuestaEmpresa = new RespuestaEmpresa(-1, "Error SQL: " + e.getLocalizedMessage());
    } catch (Exception e) {
        Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.SEVERE, null, e);
        respuestaEmpresa = new RespuestaEmpresa(-2, "Error inesperado: " + e.getMessage());
    } finally {
        // Cerrar la conexión en el bloque finally
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.WARNING, "Error al cerrar la conexión", e);
        }
    }

    return respuestaEmpresa;
    }
//, int idUsuario

    public RespuestaEmpresa insertEmpresa(Empresas nuevaEmpresa, int idUsuario) {
        RespuestaEmpresa respuestaEmpresa;
        String query = "INSERT INTO cEmpresa (claveEmpresa, nombreEmpresa,activo,fechaAlta, fechaServidor,idUsuario) VALUES (?, ?,1,getdate(),?,?)";
        try {
            Connection connection = PoolDB.getConnection("MyDB");

            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, nuevaEmpresa.getClaveEmpresa());
            stmt.setString(2, nuevaEmpresa.getNombreEmpresa());

            if (nuevaEmpresa.getFechaServidor() != null) {
                stmt.setTimestamp(3, new java.sql.Timestamp(nuevaEmpresa.getFechaServidor().getTime())); // Asumiendo que getFechaServidor devuelve un objeto Date
            } else {
                stmt.setNull(3, java.sql.Types.TIMESTAMP); // Si es null, establece como NULL en la base de datos
            }

            stmt.setInt(4, idUsuario);

            int rowsAffected = stmt.executeUpdate();

            respuestaEmpresa = (rowsAffected > 0)
                    ? new RespuestaEmpresa(0, "Inserción exitosa")
                    : new RespuestaEmpresa(1, "No se pudo insertar la empresa");

        } catch (SQLException e) {
            Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.SEVERE, null, e);
            respuestaEmpresa = new RespuestaEmpresa(-1, "Error SQL: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.SEVERE, null, e);
            respuestaEmpresa = new RespuestaEmpresa(-2, "Error inesperado: " + e.getMessage());
        }

        return respuestaEmpresa;
    }

    public RespuestaEmpresa deleteEmpresa(Empresas EmpresaAEliminar) {
        RespuestaEmpresa respuestaEmpresa = null;
        String query = "DELETE FROM cEmpresa WHERE idEmpresa = ?";

        try (Connection connection = PoolDB.getConnection("MyDB"); PreparedStatement stmt = connection.prepareStatement(query)) {

            String checkQuery = "SELECT COUNT(*) FROM cEmpresa WHERE idEmpresa = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, EmpresaAEliminar.getIdEmpresa());
                ResultSet checkRs = checkStmt.executeQuery();
                if (checkRs.next()) {
                    int count = checkRs.getInt(1);
                    System.out.println("Número de empresas encontradas con ID " + EmpresaAEliminar.getIdEmpresa() + ": " + count);
                    if (count == 0) {
                        return new RespuestaEmpresa(1, "No se encontró la empresa para eliminar en la base de datos");
                    }
                }
            }

            stmt.setInt(1, EmpresaAEliminar.getIdEmpresa());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Filas afectadas: " + rowsAffected);

            if (rowsAffected > 0) {
                respuestaEmpresa = new RespuestaEmpresa(0, "Eliminación exitosa");

            } else {
                respuestaEmpresa = new RespuestaEmpresa(1, "No se encontró la sucursal para eliminar en la base de datos");

            }

        } catch (SQLException e) {
            Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.SEVERE, null, e);
            respuestaEmpresa = new RespuestaEmpresa(-1, "Error SQL referencia (foreign key): " + e.getLocalizedMessage());

        } catch (Exception e) {
            Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.SEVERE, null, e);
            respuestaEmpresa = new RespuestaEmpresa(-2, "Error inesperado: " + e.getMessage());

        }

        return respuestaEmpresa;
    }

}
