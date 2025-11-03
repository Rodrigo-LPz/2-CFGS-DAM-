/**
 *
 * @author Rodrigo
 */
package com.mycompany.db_conecction;


// Importa de la biblioteca/librería el paquete "Connection".
import java.sql.Connection;
// Importa de la biblioteca/librería el paquete "DriverManager".
import java.sql.DriverManager;
// Importa de la biblioteca/librería el paquete "PreparedStatement".
import java.sql.PreparedStatement;
// Importa de la biblioteca/librería el paquete "SQLException".
import java.sql.SQLException;
// Importa de la biblioteca/librería el paquete "Statement".
import java.sql.Statement;

// Crea la clase principal del programa.
public class DB_Conecction_PrepareStatement_INSERT{
    // Configuración para MySQL en Docker.
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    // Crea el método principal del programa.
    public static void main(String[] args){
        /* 
                DIFERENCIA ENTRE STATEMENT Y PREPAREDSTATEMENT:
         ------------------------------------------------------------
            ◉ En el primer código (con Statement), la consulta se creaba concatenando directamente el texto introducido por el usuario: "SELECT * FROM CLIENTE WHERE APELLIDOS LIKE '" + valor + "%';"
                Esto es peligroso porque si el usuario escribe algo malicioso (por ejemplo: A%' OR '1'='1), podría alterar la consulta y acceder a datos no permitidos. A esto se le llama "inyección SQL".
        
            ◉ En cambio, con PreparedStatement (como en este código), usamos un símbolo '?' como "marcador de posición" y luego le asignamos el valor con el método setString().
                Esto hace que el valor se trate de forma segura, evitando que el usuario pueda modificar la estructura del SQL.
        
            ◉ En resumen:
                - Statement → concatena texto (menos seguro)
                - PreparedStatement → usa parámetros (?) (más seguro y profesional)
        */
        try (Connection cn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement pst = cn.prepareStatement("INSERT INTO CLIENTE (DNI, APELLIDOS, CP) VALUES (?, ?, ?)")){
            System.out.println("\n\n\t\tConectado a la base de datos.");
            Statement st = cn.createStatement();

            /*
            // Crea una Base de datos si no existe.
            String sql = "CREATE DATABASE DAM2;";
            st.execute(sql);
            System.out.println("\n\n\tBase de datos Borrada.");

            // Borra la base de datos si existe.
            sql = "DROP DATABASE IF EXISTS DAM2;";
            st.execute(sql);
            System.out.println("\n\n\tBase de datos creada.");
            */

            // Crea una base de datos si no existe.
            String sql = "CREATE DATABASE IF NOT EXISTS DAM2;";
            st.execute(sql);
            System.out.println("\n\n\t\tBase de datos creada.");
            
            // Seleciona la base de datos creada.
            st.execute("USE DAM2");
            
            // Crea una nueva tabla.
            sql = "CREATE TABLE IF NOT EXISTS CLIENTE (" +
                      "DNI CHAR(9) NOT NULL," +
                      "APELLIDOS VARCHAR(32) NOT NULL," +
                      "CP CHAR(5)," +
                      "PRIMARY KEY (DNI)" +
                ")";
            st.execute(sql);
            System.out.println("\n\n\t\tTabla creadda.\n\n");
            
            /*
            // Inserta nuevos datos en la tabla creada.
                // Declara una variable que tendrá la función de contabilizar el número de filas.
            int rowsAffected = 0;
            
            sql = "INSERT INTO CLIENTE (DNI, APELLIDOS, CP) VALUES" +
                      "('123456789', 'MARTINEZ', '35580')," +
                      "('987654321', 'HERNANDEZ', '38520')," +
                      "('147258369', 'GUTIERREZ', '35500')," +
                      "('963852741', 'GARCÍA', '35580')";
            rowsAffected = st.executeUpdate(sql);
            System.out.println("\tSe han insertado un total de " + rowsAffected + " filas.");
            */
            
            // Insertar un nuevo cliente.
            pst.setString(1, "852741963");
            pst.setString(2, "Fernández");
            pst.setString(3, "35580");
            pst.executeUpdate();
            
            // Insertar otro nuevo cliente.
            pst.setString(1, "852963741");
            pst.setString(2, "López");
            pst.setString(3, "35500");
            pst.executeUpdate();
            
      } catch (SQLException sqlex){
            System.getLogger(DB_Conecction_PrepareStatement_INSERT.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println(sqlex.getMessage());
      }
  }
}