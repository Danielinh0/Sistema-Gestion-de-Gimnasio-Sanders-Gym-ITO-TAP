package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Daniel Eduardo y su intimo amigo Luis
 */

public class Conexion {
    Connection con;
    
    // Constantes para la URL de conexión, el usuario y la contraseña de la base de datos
    public static final String URL = "jdbc:mysql://localhost:3306/gimnasio1";
    public static final String USER = "root";
    public static final String CLAVE = "dominic";

    // Método que intenta establecer una conexión con la base de datos
    public Connection conectar() {
        try {
            con = DriverManager.getConnection(URL, USER, CLAVE);
        } catch (SQLException e) {
            System.out.println("Error :" + e);
        }
        return con;
    }
}
