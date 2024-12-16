package data;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PoolDB {

//Creates a new instance of PoolDB
    public static Connection getConnection(String nName) throws SQLException, NamingException {
        InitialContext cxt = new InitialContext();

        DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/" + nName);

        if (ds == null) {
            throw new SQLException("Origen de Datos " + nName + " no Encontrado!");

        }

        Connection conn = ds.getConnection();
        conn.setAutoCommit(true);
        System.out.println("Conexión a la base de datos establecida correctamente.");

        return conn;
    }

    public static void main(String[] args) {
        try {
            Connection conn = getConnection("MyDB");
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }
}
