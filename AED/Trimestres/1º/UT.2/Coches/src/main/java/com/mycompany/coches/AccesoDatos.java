/**
 *
 * @author Rodrigo
 */
package com.mycompany.coches;


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

// Crea la clase principal del programa.
public class AccesoDatos{
    // Método para crear la conexión con la DB dado una direción, url; un usuario y una contraseña.
    private Connection conexion;
    
    // Configuración para MySQL en Docker.
    private static final String URL_ROOT = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "DatosCoches";
    //private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String URL = URL_ROOT + DB_NAME;
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    // Crea el método "crearBaseDeDatos".
    public void crearBaseDeDatos(){
        try (Connection cnTemp = DriverManager.getConnection(URL_ROOT, USER, PASSWORD); Statement st = cnTemp.createStatement()){
            // Crea una base de datos si no existe.
            String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + ";";
            st.execute(sql);
            System.out.println("\n\n\t\tBase de datos, '" + DB_NAME + "', ha sido creada exitosamente.");
            
            // Conexión a la base de datos creada.
            conexion = DriverManager.getConnection(URL_ROOT + DB_NAME, USER, PASSWORD);
            System.out.println("\n\n\tConexión con la/a la base de datos abierta " + DB_NAME + ".\n");
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al crear la base de datos: " + sqlex.getMessage());
        }
    }
    
    // Crea el método "abrirConexion".
    public void abrirConexion(){
            // Conexión a la base de datos creada.
        try{
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("\n\n\tConectado al servidor MySQL.");
            
            System.out.println("\n\n\tConexión con la/a la base de datos abierta " + DB_NAME + ".\n");
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al crear la base de datos: " + sqlex.getMessage());
        }
    }
    
    // Crea un método para crear las tablas, en caso de que no existan.
    public void crearTablas(){
        try (Statement st = conexion.createStatement()){
            String sql;
            
            // Crea la tabla "Propietarios".
            sql = "CREATE TABLE IF NOT EXISTS Propietarios (" +
                    "DNI VARCHAR(10)," +
                    "Nombre VARCHAR(40)," +
                    "Edad INTEGER," +
                    "UNIQUE KEY(DNI)" +
               ")";
            st.execute(sql);
            System.out.println("\n\n\t\tTabla 'Propietarios' ha sido creadda exitosamente.");
            
            // Crea la tabla "Coches".
            sql = "CREATE TABLE IF NOT EXISTS Coches (" +
                        "Matricula VARCHAR(10) ," +
                        "Marca VARCHAR(20)," +
                        "Precio INTEGER," +
                        "DNI VARCHAR (10)," +
                        "UNIQUE KEY(Matricula)," +
                        "FOREIGN KEY (DNI) References Propietarios(DNI)" +
                ")";
            st.execute(sql);
            System.out.println("\n\tTabla 'Coches' ha sido creadda exitosamente.\n\n");
            
            System.out.println("\n\n\tTabla verificadas y creadas correctamente.\n\n");
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al crear las tablas: " + sqlex.getMessage());
        }
    }            
    
    
    // Crea un método para crear las tablas, en caso de que no existan.
    public void insertarDatosIniciales(){
        try (Statement st = conexion.createStatement()){
            // Verifica si ya hay propietarios, si las tablas ya tienen datos iniciales.
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM Propietarios");
            rs.next();
            int count = rs.getInt(1);
            
            /*
            if (count > 0){
                System.out.println("\n\n\tLa base de datos ya contiene datos, no se han insertado los datos iniciales.");
                return;
            }
            */
            if (count == 0){
                System.out.println("\n\n\tInsertando datos iniciales...");
                
                // Inserta datos en la tabla "Propietarios"
                /*
                st.executeUpdate("INSERT INTO Propietarios VALUES ('1A','Pepe',30), ('1B','Ana',40), ('1C','Maria',50)");
                st.executeUpdate("INSERT INTO Coches VALUES ('MA-1111','Opel',1000,'1A'), ('MA-2222','Renault',2000,'1A'), ('BA-3333','Seat',3000,'1B')");
                */
                st.executeUpdate("INSERT INTO Propietarios values('1A','Pepe',30)");
                st.executeUpdate("INSERT INTO Propietarios values('1B','Ana',40)");
                st.executeUpdate("INSERT INTO Propietarios values('1C','Maria',50)");
                
                // Inserta datos en la tabla "Coches"
                st.executeUpdate("INSERT INTO Coches VALUES('MA-1111','Opel',1000,'1A')");
                st.executeUpdate("INSERT INTO Coches VALUES('MA-2222','Renault',2000,'1A')");
                st.executeUpdate("INSERT INTO Coches VALUES('BA-3333','Seat',3000,'1B')");
                
                System.out.println("\n\n\tDatos iniciales insertados correctamente.");
            } else{
                System.out.println("\n\n\tLa base de datos ya contiene datos, no se han insertado los datos iniciales.");
            }
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al insertar los datos iniciales en las tablas: " + sqlex.getMessage());
        }
    }
    
    // Crea el método "cerrarConexion".
    public void cerrarConexion(){
        try{
            // Condicional "if" para verificar previo a cerrar la conexión si el objeto "Conecction" no está vacío o cerrado.
            if (conexion != null && !conexion.isClosed()){
                conexion.close();
                
                System.out.println("\n\n\tLa conexión se ha cerrado correctamente.");
            }
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al cerrar la conexión: " + sqlex.getMessage());
        }
    }
    
    // Crea un método para mostrar los coches ordenados por precio descendiente (del más caro al más más barato).
    public void mostrarDatosCoches(){
        String sql = "SELECT * FROM Coches ORDER BY Precio DESC";
        
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)){
            System.out.println("\n\n\t<========== Lista de coches ==========>");
            
            while (rs.next()){
                System.out.printf("%s - %s - %d€ - Propietario: %s%n", /* La expresión "%s" convierte la matrícula, la marca y el DNI (enteros) a String's, cadenas. La expresión "%d" permite imprimir dentro de una cadena (string) enteros (int) ya que así es como los lee. Y, la expresión "%n" equivale a "\n", es un salto de línea. */
                        rs.getString("Matricula"),
                        rs.getString("Marca"),
                        rs.getInt("Precio"),
                        rs.getString("DNI"));
            }
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al mostrar los datos: " + sqlex.getMessage());
        }
    }
    
    // Crea un método para modificar el precio de un coche según su matrícula.
    public void modificarCoche(String matricula, int precio){
        String sql = "UPDATE Coches SET Precio = ? WHERE Matricula = ?"; /* Los "?" son parámetros. */
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, precio); /*Parámetro (1). */
            ps.setString(2, matricula); /*Parámetro (2). */
            
            int filas = ps.executeUpdate();
            
            System.out.println("\n\n\tSe han modificado/actualizado: " + filas + " fila/s del Coche.");
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al mostrar los datos del coche: " + sqlex.getMessage());
        }
    }
    
    // Crea un método para borra un registro, un coche, por su matrícula.
    public void borrarCoche(String matricula){
        String sql = "DELETE FROM Coches WHERE Matricula LIKE ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, matricula + "%"); /* El "%" es un parámetro que funciona como si estuviese dentro de la cláusula "LIKE" ("como comodín"), y significa “cualquier secuencia de caracteres”, cumpliendo así con el parámetro faltante de la matrícula, las letras. */
            int filas = ps.executeUpdate();
            
            System.out.println("\n\n\tEl registro/s del Coche ha/n sido eliminado/s: " + filas + " fila/s.");
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al borrar el registro entero, el coche: " + sqlex.getMessage());
        }
    }
    
    // Crea un método para insertar un coche, pero sólo si el propietario existe.
    public void insertarCoche(String matricula, String marca, int precio, String dni){
        try{
            String check = "SELECT * FROM Propietarios WHERE DNI = ?"; /* A diferencia del anterior método, donde en su sentencia sql utilizamos "LIKE" para que durante la consulta sql ésta busque coincidencias parciales, en esta no podemos utilizarla, pues no podemos buscar DNI parciales, debe ser justo el que coincida, el que sea exacto respecto a la búsqueda. Pasamos de una búsqueda con coincidencias parciales a una búsqueda de coincidencias exactas. */
            
            // Bloque "try" que acompaña englobando al "if" para verificar si el valor por el que se filtra la búsqueda de propietarios existe, es correcto.
            try (PreparedStatement pst = conexion.prepareStatement(check)){
                pst.setString(1, dni); /* De nuevo, a diferencia del anterior método, puesto que estamo utilizando una consulta de coincidencias exactas y no de coincidencias parciales, no es necesario, sobra, utilizar el parámetro de cláusula "LIKE". */
                ResultSet rs = pst.executeQuery();
                
                if (!rs.next()){
                    System.out.println("\n\n\tError. No se puede insertar el coche: el propietario con el DNI (" + dni + ") solicitado no existe.");
                    return;
                }
            }
            
            String sql = "INSERT INTO Coches (Matricula, Marca, Precio, DNI) VALUES (?, ?, ?, ?)";
            
            // Bloque "try" que dará lugar, se ejecutará si el valor introducido como "DNI" para la búsqueda filtrada en la tabla "Propietarios" existe.
            try (PreparedStatement pst = conexion.prepareStatement(sql)){
                pst.setString(1, matricula);
                pst.setString(2, marca);
                pst.setInt(3, precio);
                pst.setString(4, dni);
                int filas = pst.executeUpdate();
                
                System.out.println("\n\n\t\tEl registro/s del Coche ha/n sido añadido/s: " + filas + " fila(s).");
            }
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al insertar el coche: " + sqlex.getMessage());
        }
    }
    
    // Crea un método para insertar un propietario.
    public void insertarPropietario(String dni, String nombre, int edad){
        String sql = "INSERT INTO Propietarios (DNI, Nombre, Edad) VALUES (?, ?, ?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setInt(3, edad);
            ps.executeUpdate();
            
            System.out.println("\n\n\t\tPropietario y sus datos han sido insertados correctamente.");
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al insertar un nuevo propietario: " + sqlex.getMessage());
        }
    }
    
    // Crea un método para mostrar los datos de un propietario y los coches que tiene.
    public void mostrarPropietarioYCoches(String dni){
        String sql = """
                        SELECT p.DNI, p.Nombre, p.Edad, c.Matricula, c.Marca, c.Precio
                        FROM Propietarios p
                        LEFT JOIN Coches c ON p.DNI = c.DNI
                        WHERE p.DNI = ?
                     """;
        
        try (PreparedStatement pst = conexion.prepareStatement(sql)){
            pst.setString(1, dni);
            ResultSet rs = pst.executeQuery();
            
            boolean propietarioExiste = false;
            boolean tieneCoches = false;
            
            // Bucle "while" para mostrar los resultados siempre y cuando "rs" se ejecute y "tieneCoches" reciba un valor "true".
            while (rs.next()){
                // Si se ejecuta la consulta implica que existe el propietario, por tanto el booleano "propietarioExiste" pasa a ser verdadero, "true".
                propietarioExiste = true;

                // Si existe el propietario y el valor de la variable "tieneCoches" es "true" pasa al condicional "if" como verdadero, "true".
                if (!tieneCoches){ /* "!tienecoche" = si no se encontró registro. */
                System.out.println("\n\n\tPropietario:\n\t\t\tDNI: " + rs.getString("DNI") + "\n\t\t\tNombre: " + rs.getString("Nombre") + "\n\t\t\tEdad: " + rs.getInt("Edad"));
                System.out.println("\n\tCoches del propietario:");
                    tieneCoches = true;
                } else if (propietarioExiste && tieneCoches){
                    System.out.println("\n\n\tEste propietario no tiene vehículo, no tiene ningún coche.");
                }
                
                System.out.printf("     %s - %s - %d€%n",
                        rs.getString("Matricula"),
                        rs.getString("Marca"),
                        rs.getInt("Precio"));
            }
            if (!tieneCoches){
                System.out.println("\n\n\tNo existe propietario en nuestra DB con dicho DNI (" + dni + ").");
            }
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al mostrar el propietario: " + sqlex.getMessage());
        }
    }
    
    // Crea un método para borrar un propietario y sus coches.
    public void borrarPropietario(String dni){
        try{
            String sql1 = "DELETE FROM Coches WHERE DNI = ?";
            String sql2 = "DELETE FROM Propietarios WHERE DNI = ?";

            try (PreparedStatement pst1 = conexion.prepareStatement(sql1);
                 PreparedStatement pst2 = conexion.prepareStatement(sql2)){
                pst1.setString(1, dni);
                pst2.setString(1, dni);
                int coches = pst1.executeUpdate();
                int prop = pst2.executeUpdate();

                System.out.println("\n\n\tPropietario (" + prop + ") y coche/s (" + coches + ") borrados/eliminados de la DB.");
            }
        } catch (SQLException sqlex){
            System.getLogger(AccesoDatos.class.getName()).log(System.Logger.Level.ERROR, (String) null, sqlex);
            System.out.println("\n\n\tError inesperado al borrar el propietario: " + sqlex.getMessage());
        }
    }
}