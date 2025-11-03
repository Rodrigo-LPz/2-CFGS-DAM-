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
// Importa de la biblioteca/librería el paquete "ResultSet".
import java.sql.ResultSet;
// Importa de la biblioteca/librería el paquete "SQLException".
import java.sql.SQLException;
// Importa de la biblioteca/librería el paquete "Statement".
import java.sql.Statement;
// Importa de la biblioteca/librería el paquete "Scanner".
import java.util.Scanner;

// Crea la clase principal del programa.
public class DB_Conecction_PrepareStatement{
    // Configuración para MySQL en Docker.
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    // Crea el método principal del programa.
    public static void main(String[] args){
        try (Connection cn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement pst = cn.prepareStatement("SELECT * FROM CLIENTE WHERE APELLIDOS LIKE ?;")){
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
            // Selecciona datos de la tabla.
            sql = "SELECT * FROM CLIENTE";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                System.out.println("\t" + rs.getString("DNI") + ",\t" + rs.getString("APELLIDOS") + ",\t" + rs.getString("CP") + ",\n");
            }
            */
            // Consulta a petición del usuario.
            Scanner user = new Scanner(System.in);
            System.out.print("Por favor, introduzca la inial del apellido del cliente a buscar: ");
            String firstLetter = user.nextLine();
                // Petición de búsqueda de personas cuyo apellido empiece por la letra indicada.
            pst.setString(1, firstLetter.toUpperCase() + "%");
            
            try (ResultSet rs = pst.executeQuery()){
                while (rs.next()){
                    System.out.println("\t" + rs.getString("DNI") + ",\t" + rs.getString("APELLIDOS") + ",\t" + rs.getString("CP") + ",\n");
                }
            }
      } catch (SQLException sqlex){
            System.getLogger(DB_Conecction_PrepareStatement.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println(sqlex.getMessage());
      }
  }
}