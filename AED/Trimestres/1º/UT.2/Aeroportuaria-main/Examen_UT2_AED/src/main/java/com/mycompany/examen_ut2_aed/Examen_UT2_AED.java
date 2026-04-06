/**
 *
 * @author Rodrigo
 */
package com.mycompany.examen_ut2_aed;


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
// Importa de la biblioteca/librería el paquete "JOptionPane".
import javax.swing.JOptionPane;

public class Examen_UT2_AED{
    // Configuración previa.
        // Variable de caracter privado para crear la conexión con la DB dado una dirección, url; un usuario y una contraseña.
    private Connection conexion;
    
    String sql;
    
    // Configuración MySQL.
    private static final String URL = "jdbc:mysql://localhost:3306/AEROLINEAS";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    public static void main(String[] args){
        Examen_UT2_AED examen = new Examen_UT2_AED();
        
        examen.Conexion();
        
        // -------------------- APARTADO 1 --------------------
        examen.MostrarDatos();
    }
    // Crea el método "Conexion", se conecta a la base de datos.
    public void Conexion(){
        try{
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            
            System.out.println("\n\tConexión al servidor exitoso.");
            
            System.out.println("\n\n\tConexión a la base de datos 'AEROLINEAS' abierta.");
        } catch (SQLException sqlex){
            System.getLogger(Examen_UT2_AED.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            JOptionPane.showMessageDialog(null, "Error de conexión", "Error inesperado al conectarse a la base de datos: " + sqlex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // -------------------- APARTADO 1 --------------------
    
    // Crea el método "MostrarDatos", muestra toda la información almacenada (vuelos y pasajeros).
    public void MostrarDatos(){
        // Comprobación previa para verificar  si la conexión sigue vigente.
        if (conexion == null){
            JOptionPane.showMessageDialog(null, "Error de conexión", "Error. No hay conexión activa con la base de datos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Mostrar vuelos.
            // Crea la sentencia SQL para realizar la búsqueda.
        sql = "SELECT * FROM VUELOS";
        
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)){
            System.out.println("\n\n\n\n\t<========== Lista/Tabla de VUELOS ==========>\n\n");
            
            while (rs.next()){
                //System.out.println("\n\tCódigo de Vuelo: " + rs.getString("COD_VUELO") + " |\n\tHora de Salida: " + rs.getString("HORA_SALIDA") + "|\n\tDestino: " + rs.getString("DESTINO") + "|\n\t Procedencia: " + rs.getString("PROCEDENCIA") + "|\n\t Plazas Fumador: " + rs.getString("PLAZAS_FUMADOR") + "|\n\t Plazas No Fumador: " + rs.getString("PLAZAS_NO_FUMADOR") + "|\n\t Plazas Turista: " + rs.getString("PLAZAS_TURISTA") + "|\n\t Plazas de Primera: " + rs.getString("PLAZAS_PRIMERA") + "|\n\n");
                /*
                System.out.printf("%-16s %-16s %-16s %-16s %-16s %-16s %-16s %-16s\n",
                        "COD_VUELO", "HORA_SALIDA", "DESTINO", "PROCEDENCIA",
                        "PLAZAS_FUMADOR", "PLAZAS_NO_FUMADOR", "PLAZAS_TURISTA", "PLAZAS_PRIMERA");
                */
                System.out.printf("Código de Vuelo: %-16s | Hora de Salida: %-16s | Destino: %-16s | Procedencia: %-16s | De Fumador: %-16s | De No fumador: %-16s | De Turista: %-16s | De Primera: %-16s\n",
                    rs.getString("COD_VUELO"),
                    rs.getString("HORA_SALIDA"),
                    rs.getString("DESTINO"),
                    rs.getString("PROCEDENCIA"),
                    rs.getInt("PLAZAS_FUMADOR"),
                    rs.getInt("PLAZAS_NO_FUMADOR"),
                    rs.getInt("PLAZAS_TURISTA"),
                    rs.getInt("PLAZAS_PRIMERA"));
            } 
        } catch (SQLException sqlex){
            System.getLogger(Examen_UT2_AED.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            JOptionPane.showMessageDialog(null, "Error SQL", "Error inesperado al mostrar la lista de los datos de los vuelos: " + sqlex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        
        // Mostrar pasajeros.
            // Crea la sentencia SQL para realizar la búsqueda.
        sql = "SELECT * FROM PASAJEROS";
        
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)){
            System.out.println("\n\n\n\n\t<========== Lista de PASAJEROS ==========>\n\n");
            
            while (rs.next()){
                //System.out.println("\n\tCódigo de Vuelo: " + rs.getString("COD_VUELO") + " |\n\tTipo de Plaza : " + rs.getString("TIPO_PLAZA ") + "|\n\tFumador: " + rs.getString("FUMADOR") + "|\n\n");
                System.out.printf("Número: %d | Código de Vuelo: %-16s | Tipo de Plaza: %-16s | Fumador: %-16s\n",
                    rs.getInt("NUM"),
                    rs.getString("COD_VUELO"),
                    rs.getString("TIPO_PLAZA"),
                    rs.getString("FUMADOR"));
            }
        } catch (SQLException sqlex){
            System.getLogger(Examen_UT2_AED.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            JOptionPane.showMessageDialog(null, "Error SQL", "Error inesperado al mostrar la lista de los pasajeros de los vuelos: " + sqlex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        
        // Mostrar vuelos junto con sus pasajeros (relación entre tablas).
            // Crea la sentencia SQL para realizar la búsqueda.
        sql = """
                SELECT v.COD_VUELO, v.HORA_SALIDA, v.DESTINO, v.PROCEDENCIA, v.PLAZAS_FUMADOR, v.PLAZAS_NO_FUMADOR, v.PLAZAS_TURISTA, v.PLAZAS_PRIMERA, p.NUM, p.TIPO_PLAZA, p.FUMADOR
                    FROM VUELOS v
                LEFT JOIN PASAJEROS p ON v.COD_VUELO = p.COD_VUELO
                    ORDER BY v.COD_VUELO, p.NUM
              """;
        
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)){
            System.out.println("\n\n\n\n\t<========== Lista de Vuelos con sus respectivos Pasajeros ==========>\n\n");
            
            while (rs.next()){
                System.out.printf("Código de Vuelo: %-16s | Hora de Salida: %-16s | Destino: %-16s | Procedencia: %-16s | De Fumador: %-16s | De No fumador: %-16s | De Turista: %-16s | De Primera: %-16s\t | Número: %-16d | Tipo de Plaza: %-16s | Fumador: %-16s\n",
                    rs.getString("COD_VUELO"),
                    rs.getString("HORA_SALIDA"),
                    rs.getString("DESTINO"),
                    rs.getString("PROCEDENCIA"),
                    rs.getInt("PLAZAS_FUMADOR"),
                    rs.getInt("PLAZAS_NO_FUMADOR"),
                    rs.getInt("PLAZAS_TURISTA"),
                    rs.getInt("PLAZAS_PRIMERA"),
                    rs.getInt("NUM"),
                    rs.getString("TIPO_PLAZA"),
                    rs.getString("FUMADOR"));
            } 
        } catch (SQLException sqlex){
            System.getLogger(Examen_UT2_AED.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            JOptionPane.showMessageDialog(null, "Error SQL", "Error inesperado al mostrar la lista de los vuelos con sus respectivos pasajeros: " + sqlex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // -------------------- APARTADO 2 --------------------
    
    // Crea el método "mostrarPasajerosPorVuelo", muestra toda la información almacenada (vuelos y pasajeros) por filtración (todos los pasajeros de un vuelo).
    public void mostrarPasajerosPorVuelo(String codVuelo){
        // Comprobación previa para verificar  si la conexión sigue vigente.
        if (conexion == null){
            JOptionPane.showMessageDialog(null, "Error de conexión", "Error. No hay conexión activa con la base de datos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crea la sentencia SQL para realizar la búsqueda.
        sql = "SELECT * FROM PASAJEROS WHERE COD_VUELO = ?";
        
        try (PreparedStatement pst = conexion.prepareStatement(sql)){
            pst.setString(1, codVuelo);
            ResultSet rs = pst.executeQuery();
            
            System.out.println("\n\n\tPASAJEROS DEL VUELO '" + codVuelo + "':");
            
            // Declara una variable booleana inicializada en valor "false" para comprobar si hay o no pasajeros declarados en/para un vuelo.
            boolean hayPasajeros = false;
            
            while (rs.next()){
                hayPasajeros = true;
                System.out.printf("NUM: %d | TIPO_PLAZA: %s | FUMADOR: %s%n",
                    rs.getInt("NUM"),
                    rs.getString("TIPO_PLAZA"),
                    rs.getString("FUMADOR"));
            }
            
            if (!hayPasajeros) System.out.println("\n\n\tNo hay pasajeros registrados para dicho vuelo '" + codVuelo + "'.");
        } catch (SQLException sqlex){
            System.getLogger(Examen_UT2_AED.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            JOptionPane.showMessageDialog(null, "Error SQL", "Error inesperado al mostrar la lista de los vuelos filtrados según el código de vuelo: " + sqlex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // -------------------- APARTADO 3 --------------------
    
    // Crea el método "insertarVuelo", para insertar/registrar un vuelo.
    public void insertarVuelo(String codVuelo, String horaSalida, String destino, String procedencia, int plazasFumador, int plazasNoFumador, int plazasTurista, int plazasPrimera){
        // Comprobación previa para verificar  si la conexión sigue vigente.
        if (conexion == null){
            JOptionPane.showMessageDialog(null, "Error de conexión", "Error. No hay conexión activa con la base de datos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crea la sentencia SQL para realizar la búsqueda.
        sql = "INSERT INTO VUELOS VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int rows;
        
        try (PreparedStatement pst = conexion.prepareStatement(sql)){
            pst.setString(1, codVuelo);
            pst.setString(2, horaSalida);
            pst.setString(3, destino);
            pst.setString(4, procedencia);
            pst.setInt(5, plazasFumador);
            pst.setInt(6, plazasNoFumador);
            pst.setInt(7, plazasTurista);
            pst.setInt(8, plazasPrimera);
            
            rows = pst.executeUpdate();
            
            System.out.println("\n\n\tEl nuevo vuelo ha sido insertado/registrado correctamente (" + rows + " filas nuevas en el registro).");
        } catch (SQLException sqlex){
            System.getLogger(Examen_UT2_AED.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            JOptionPane.showMessageDialog(null, "Error SQL", "Error inesperado al insertar/registrar un nuevo vuelo: " + sqlex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // -------------------- APARTADO 4 --------------------
    
    // Crea el método "borrarVuelo", para borrar/eliminar un vuelo.
    public void borrarVuelo(String codVuelo){
        // Comprobación previa para verificar  si la conexión sigue vigente.
        if (conexion == null){
            JOptionPane.showMessageDialog(null, "Error de conexión", "Error. No hay conexión activa con la base de datos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crea la sentencia SQL para realizar la búsqueda.
        sql = "DELETE FROM VUELOS WHERE COD_VUELO = ?";
        int rows;
        
        try (PreparedStatement pst = conexion.prepareStatement(sql)){
            pst.setString(1, codVuelo);
            
            rows = pst.executeUpdate();
            
            if (rows > 0){
                System.out.println("\n\n\tEl vuelo '" + codVuelo + "' ha sido eliminado correctamente.");
            } else{
                JOptionPane.showMessageDialog(null, "Error de búsqueda", "Error inesperado al borrar/eliminar el vuelo '" + codVuelo + "'", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("\n\n\tEl vuelo ha sido insertado/registrado correctamente (" + rows + " filas nuevas en el registro).");
        } catch (SQLException sqlex){
            System.getLogger(Examen_UT2_AED.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            JOptionPane.showMessageDialog(null, "Error SQL", "Error inesperado al eliminar/borrar el vuelo '" + codVuelo + "': " + sqlex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // -------------------- APARTADO 5 --------------------
    
    // Crea el método "actualizarPlazasFumador", para eliminar las plazas de fumadores y a su vez pasarlas (la cantidad) como plazas no fumadores.
    public void actualizarPlazasFumador(){
        // Comprobación previa para verificar  si la conexión sigue vigente.
        if (conexion == null){
            JOptionPane.showMessageDialog(null, "Error de conexión", "Error. No hay conexión activa con la base de datos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crea la sentencia SQL para realizar la búsqueda.
        sql = "UPDATE VUELOS SET PLAZAS_NO_FUMADOR = PLAZAS_NO_FUMADOR + PLAZAS_FUMADOR, PLAZAS_FUMADOR = 0";
        int rows;
        
        try (Statement st = conexion.createStatement()){
            rows = st.executeUpdate(sql);
            
            System.out.println("\n\n\tLas plazas de fumadores han sido eliminadas correctamente y sus plazas (cantidad) ha sido transferida a plazas de no fumadores ['" + rows + "' (filas) vuelos actualizados].");
            
        } catch (SQLException sqlex){
            System.getLogger(Examen_UT2_AED.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            JOptionPane.showMessageDialog(null, "Error SQL", "Error inesperado al actualizar las plazas de los vuelos: " + sqlex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // -------------------- APARTADO 6 --------------------
    
    // Crea el método "crearTablaPersonas", para crear una nueva tabla donde almacenar/guardar los registros de las personas, pasajeros, con sus datos respectivos (DNI, Nombre y Apellidos).
    public void crearTablaPersonas(){
        // Comprobación previa para verificar  si la conexión sigue vigente.
        if (conexion == null){
            JOptionPane.showMessageDialog(null, "Error de conexión", "Error. No hay conexión activa con la base de datos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crea la sentencia SQL para realizar la búsqueda.
        sql = """
                CREATE TABLE IF NOT EXISTS PERSONAS(
                    DNI VARCHAR(9) PRIMARY KEY,
                    NOMBRE VARCHAR(30),
                    APELLIDO1 VARCHAR(30),
                    APELLIDO2 VARCHAR(30),
                    COD_VUELO VARCHAR(10)
                );
              """;
        
        try (Statement st = conexion.createStatement()){
            st.execute(sql);
            
            System.out.println("\n\n\tLa tabla \"PERSONAS\" se ha creado correctamente.");
            
        } catch (SQLException sqlex){
            System.getLogger(Examen_UT2_AED.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            JOptionPane.showMessageDialog(null, "Error SQL", "Error inesperado al crear la nueva tabla \"PERSONAS\": " + sqlex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Crea el método "insertarPasajeros", para almacenar en esta nueva tabla ('PERSONAS') sus respectivos sus datos (DNI, Nombre y Apellidos).
    public void insertarPasajeros(){
        // Comprobación previa para verificar  si la conexión sigue vigente.
        if (conexion == null){
            JOptionPane.showMessageDialog(null, "Error de conexión", "Error. No hay conexión activa con la base de datos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crea la sentencia SQL para realizar la búsqueda.
        //sql = "INSERT INTO PERSONAS (DNI, NOMBRE, APELLIDO1, APELLIDO2, COD_VUELO) VALUES (?, ?, ?, ?, ?)";
        sql = "INSERT INTO PERSONAS (DNI, NOMBRE, APELLIDO1, APELLIDO2, COD_VUELO) VALUES (?, ?, ?, ?, 'IB-SP-4567')";
        
        try (PreparedStatement pst = conexion.prepareStatement(sql)){
            pst.setString(1, "12345678A");
            pst.setString(2, "Ana");
            pst.setString(3, "García");
            pst.setString(4, "López");
            //pst.setString(5, "IB-SP-4567");
            pst.executeUpdate();

            pst.setString(1, "87654321B");
            pst.setString(2, "Carlos");
            pst.setString(3, "Pérez");
            pst.setString(4, "Ruiz");
            //pst.setString(5, "IB-SP-4567");
            pst.executeUpdate();
            
            pst.setString(1, "14725836C");
            pst.setString(2, "Martín");
            pst.setString(3, "Martínez");
            pst.setString(4, "Martínez");
            //pst.setString(5, "IB-SP-4567");
            pst.executeUpdate();
            
            System.out.println("\n\n\tLos ('nuevos') pasajeros junto cos sus respectivos datos personales (DNI, Nombre y Apellidos), respectivos al vuelo 'IB-SP-4567' han sido añadidos a la tabla \"PERSONAS\" correctamente.");
        } catch (SQLException sqlex){
            System.getLogger(Examen_UT2_AED.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            JOptionPane.showMessageDialog(null, "Error SQL", "Error inesperado al insertar los nuevos ('nuevos')`pasajeros junto cos sus respectivos datos personales (DNI, Nombre y Apellidos), respectivos al vuelo 'IB-SP-4567' a la tabla \"PERSONAS\": " + sqlex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
}