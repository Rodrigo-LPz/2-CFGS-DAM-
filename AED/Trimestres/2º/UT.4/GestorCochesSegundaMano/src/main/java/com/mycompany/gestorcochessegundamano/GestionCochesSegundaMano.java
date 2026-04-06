/**
 *
 * @author Rodrigo
 */
package com.mycompany.gestorcochessegundamano;


// Importa de la biblioteca/librería el paquete "Scanner".
import java.util.Scanner;
// Importa de la biblioteca/librería el paquete "JOptionPane".
import javax.swing.JOptionPane;
// Importa de la biblioteca/librería el paquete "FindIterable".
import com.mongodb.client.FindIterable;
// Importa de la biblioteca/librería el paquete "MongoClient".
import com.mongodb.client.MongoClient;
// Importa de la biblioteca/librería el paquete "MongoClients".
import com.mongodb.client.MongoClients;
// Importa de la biblioteca/librería el paquete "MongoCollection".
import com.mongodb.client.MongoCollection;
// Importa de la biblioteca/librería el paquete "MongoDatabase".
import com.mongodb.client.MongoDatabase;
// Importa de la biblioteca/librería el paquete "Filters".
import com.mongodb.client.model.Filters;
// Importa de la biblioteca/librería el paquete "Updates".
import com.mongodb.client.model.Updates;
// Importa de la biblioteca/librería el paquete "Document".
import org.bson.Document;
// Importa de la biblioteca/librería el paquete "ObjectId".
import org.bson.types.ObjectId;

// Crea la clase 'main', principal, del programa.
public class GestionCochesSegundaMano{
    // Crea el objeto privado de cliente MongoDB reutilizable.
    private static MongoClient mongoClient;

    // Crea el objeto privado para la base de datos usada.
    private static MongoDatabase database;

    // Crea el objeto privado para la colección "coches".
    private static MongoCollection<Document> coleccionCoches;

    // Crea el objeto privado para el escáner.
    private static Scanner user;
    
    // Crea el método 'main', principal, del programa.
    public static void main(String[] args){
        // Llama/Inicializa el objeto "escaner", escáner con el que crear una interación dinámica usuario-programa.
        user = new Scanner(System.in);
        
        // Crea un objeto instancia de la clase para posteriormente instanciar y hacer uso de los métodos de la misma.
        GestionCochesSegundaMano gestor = new GestionCochesSegundaMano();
        
        // Bloque de código donde llamar/inicializar el/los método/s.
        try{
            // =============== Apartado 1 ===============
            gestor.conexionMongoDB();

            // =============== Apartado 2 ===============
            gestor.menuPrincipal();
            
            // No lanzamos/ejecutamos los demás métodos/acciones de forma directa (desde aquí) para pasar previamente por el menú.
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución: " + ex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally{
            salir();
        }
    }
    
    
    // =============== Apartado 1 ===============
        // Crea el método "conexionMongoDB". Su función será conectarse y abrir la base de datos.
    private static void conexionMongoDB(){
        try{
            System.out.println("\n\n\t<==================== CONECTÁNDOSE/ACCEDIENDO AL SERVIDOR ====================>");
            System.out.println("\n\t\tConectándose/Accediendo a la base de datos...");

            // Conexión a MongoDB en localhost:27017
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            
            // Crea y accede a la base de datos "concesionario".
            database = mongoClient.getDatabase("concesionario");
            
            // Crea y accede a la colección "coches".
            coleccionCoches = database.getCollection("coches");
            
            System.out.println("\n\t\tConexión exitosa al servidor.");

            System.out.println("\n\t\tConexión a la DB (base de datos de Mongo) {" + database + "} establecida.");
            
            System.out.println("\n\t\t\tColección: {" + coleccionCoches + "} abierta");
        } catch (Exception ex){
            System.getLogger(GestionCochesSegundaMano.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            JOptionPane.showMessageDialog(null, "Error inesperado durante/al intentar conectarse a la base de datos (" + database + "): " + ex.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // =============== Apartado 2 ===============
        // Crea el método "menuPrincipal". Su función será crear y mostrar por pantalla un menú interactivo para el usuario.
    private static void menuPrincipal(){
        boolean continuar = true;
        
        int opcion;
        
        // Bucle "do-while" que hará de bucle principal del menú.
        do{
            // Mostramos el menú de opciones disponible para el usuario.
            System.out.println("\n\n\t<==================== SISTEMA GESTIÓN DE CONCESIONARIO ====================>");
            System.out.println("\n\t\t\t\tOPERACIONES CREATE");
            System.out.println("\t\t\t1. Añadir nuevo coche.");
            System.out.println("\n\t\t\t\tOPERACIONES READ");
            System.out.println("\t\t\t2. Listar todos los coches.");
            System.out.println("\t\t\t3. Buscar coche por marca.");
            System.out.println("\n\t\t\t\tOPERACIONES UPDATE");
            System.out.println("\t\t\t4. Actualizar precio de un coche.");
            System.out.println("\t\t\t6. Buscar por rango de precio.");
            System.out.println("\n\t\t\t\tOPERACIONES DELETE");
            System.out.println("\t\t\t5. Eliminar coche.");
            System.out.println("\n\n\t\t\t0. Salir.");
            System.out.println("\t========================================================================");
            System.out.print("\n\tSeleccione una opción: ");
            
            opcion = leerEntero();
            
            try{
                switch (opcion){
                // ========== OPERACIONES CREATE ==========
                case 1 -> anadirNuevoCoche();

                // ========== OPERACIONES READ ==========
                case 2 -> listarTodosLosCoches();
                case 3 -> buscarPorMarca();

                // ========== OPERACIONES UPDATE ==========
                case 4 -> actualizarPrecio();
                case 6 -> buscarPorRangoPrecio();

                // ========== OPERACIONES DELETE ==========
                case 5 -> eliminarCoche();

                // ========== SALIR ==========
                case 0 -> continuar = salir();
                
                default -> System.out.println("\n\n\tOpción introducida no válida. Intente nuevamente...");
                }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución. Se ha detenido/pausado abruptamente: " + ex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
            }
        } while (opcion != 0);
    }
    
    
    // =============== Apartado 3 ===============
        // Crea el método "anadirNuevoCoche". Su función será crear y añadir a la DB un nuevo objeto, un nuevo coche.
    private static void anadirNuevoCoche(){
        System.out.println("\n\n\t<==================== AÑADIR NUEVO COCHE ====================>");
        
        try{
            // Solicitar datos (atributos) del coche.
            System.out.print("\n\t\tMarca: ");
            String marca = user.nextLine();
            
            System.out.print("\n\t\tModelo: ");
            String modelo = user.nextLine();
            
            System.out.print("\n\t\tAño de fabricación: ");
            int anio = leerEntero();
            
            System.out.print("\n\t\tKilometraje (Km recorridos): ");
            int kilometros = leerEntero();
            
            System.out.print("\n\t\tPrecio (€): ");
            double precio = leerDouble();
            
            System.out.print("\n\t\tColor: ");
            String color = user.nextLine();
            
            // Crea (construye e inserta) un documento BSON "coche" con los datos recopilados.
            Document coche = new Document("marca", marca)
                    .append("modelo", modelo)
                    .append("anio", anio)
                    .append("kilometros", kilometros)
                    .append("precio", precio)
                    .append("color", color)
                    .append("vendido", false);
            
            // Inserta el documento en MongoDB.
            coleccionCoches.insertOne(coche);
            
            // Muestra el "ObjectId" generado.
            System.out.println("\n\t\tEl coche {" + modelo + "\t" + marca + "} ha sido creado y añadido correctamente.");
            System.out.println("\n\t\t\tNúmero de identificación (ID) generado: " + coche.getObjectId("_id"));
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución al intentar añadir un nuevo objeto, un nuevo coche, a la base de datos: " + ex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    
    // =============== Apartado 4 ===============
        // Crea el método "listarTodosLosCoches". Su función será listar/crear una lista con todos los objetos, todos los coches, registrados en la DB y mostrarla por pantalla.
    private static void listarTodosLosCoches(){
        System.out.println("\n\n\t<==================== LISTADO DE COCHES ====================>");
        
        try{
            // Obtiene todos los documentos.
            FindIterable<Document> coches = coleccionCoches.find();
            
            int contador = 0;
            
            // Itera y muestra cada documento.
            for (Document coche : coches){
                contador++;
                mostrarCoche(coche);
            }
            
            // Condicional de tipo "if" para validar que existan coches introducidos/registrados en la DB.
            if (contador == 0){
                System.out.println("\n\t\tNo existen o no hay coches registrados en la base de datos.");
            } else{
                System.out.println("\n\t\tSe han encontrado/listado un total de " + contador + " coches.");
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución al intentar listar todos los objetos, todos los coches, de la base de datos: " + ex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // =============== Apartado 5 ===============
        // Crea el método "buscarPorMarca". Su función será hacer una búsqueda filtrada, buscará un objeto, un coche, concreto a partir del parámetro "marca".
    private static void buscarPorMarca(){
        System.out.println("\n\n\t<==================== BUSCAR COCHE POR MARCA ====================>");
        
        try{
            System.out.print("\n\t\tIntroduzca la marca: ");
            String marca = user.nextLine();
            
            // Búsqueda insensible a mayúsculas usando "regex".
            FindIterable<Document> coches = coleccionCoches.find(Filters.regex("marca", marca, "i"));
            
            int contador = 0;
            
            // Itera y muestra cada documento.
            for (Document coche : coches){
                contador++;
                mostrarCoche(coche);
            }
            
            // Condicional de tipo "if" para validar que existan coches introducidos/registrados en la DB.
            if (contador == 0){
                System.out.println("\n\t\tNo existe o no hay ningún coche registrado con/de la marca '" + marca + "'.");
            } else{
                System.out.println("\n\t\tSe han encontrado/listado un total de " + contador + " coches.");
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución al intentar hacer una búsqueda filtrada de todos los objetos, todos los coches, dada un parámetro (la \"marca\") de la base de datos: " + ex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // =============== Apartado 6 ===============
        // Crea el método "actualizarPrecio". Su función será la de actualizar parámetros de los objetos, los coches, de la DB.
    private static void actualizarPrecio(){
        System.out.println("\n\n\t<==================== ACTUALIZAR PRECIO DE VENTA ====================>");
        
        try{
            System.out.print("\n\t\tIntroduzca el \"ObjectId\" del coche al que desea actualizar el precio de venta: ");
            String idString = user.nextLine();
            
            // Condicional de tipo "if" para validar que el "ObjectId" introducido sea válido.
            if (!ObjectId.isValid(idString)){
                System.out.println("\n\t\tEl \"ObjectId\" proporcionado no es válido, no existe o no lo reconoce el sistema.");
                return;
            }
            
            // Crea el objeto "ObjectId id" que estará relacionado con el id solicitado, con "idString".
            ObjectId id = new ObjectId(idString);
            
            // Busca y muestra el coche antes de actualizar su atributo, el precio.
            Document coche = coleccionCoches.find(Filters.eq("_id", id)).first();
            
            // Condicional de tipo "if" para validar que el "id" introducido sea válido, exita/esté registrado en la DB.
            if (coche == null){
                System.out.println("\n\t\tNo se existe o no encontró ningún coche con ese ID ('" + id + "')");
                return;
            }
            
            System.out.println("\n\t\tCoche encontrado:");
            mostrarCoche(coche);
            
            Double precioAntiguo = coche.getDouble("precio");
            
            // Solicita un nuevo precio.
            System.out.print("\n\t\tAhora introduzca el nuevo precio de mercado para el vehículo (€): ");
            double nuevoPrecio = leerDouble();
            
            // Actualiza el precio (precio antiguo → precio actual/nuevo).
            coleccionCoches.updateOne(
                Filters.eq("_id", id),              // Filtro por ID.
                Updates.set("precio", nuevoPrecio)  // Establece nuevo precio.
            );
            
            System.out.println("\n\t\tEl precio del coche con ID ('" + id + "') ha sido actualizado correctamente:n\t\t\tPrecio antiguo: " + String.format("%.2f", precioAntiguo) + "n\t\t\tPrecio actualizado (nuevo): " + String.format("%.2f", nuevoPrecio));
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error inesperado al intentar actualizar/modificar el parámetro 'precio' del coche: " + ex.getMessage(), "Error de actualización", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // =============== Apartado 7 ===============
        // Crea el método "eliminarCoche". Su función será la de eliminar un objeto, un coche, junto a sus parámetros, atributos, correspondientes de la DB.
    private static void eliminarCoche(){
        System.out.println("\n\n\t<==================== ELIMINAR COCHE ====================>");
        
        try{
            System.out.print("\n\t\tIntroduzca el \"ObjectId\" del coche: ");
            String idString = user.nextLine();
            
            // Condicional de tipo "if" para validar que el "ObjectId" introducido sea válido.
            if (!ObjectId.isValid(idString)){
                System.out.println("\n\t\tEl \"ObjectId\" proporcionado no es válido, no existe o no lo reconoce el sistema.");
                return;
            }
            
            // Crea el objeto "ObjectId id" que estará relacionado con el id solicitado, con "idString".
            ObjectId id = new ObjectId(idString);
            
            // Busca y muestra el coche a eliminar.
            Document coche = coleccionCoches.find(Filters.eq("_id", id)).first();
            
            // Condicional de tipo "if" para validar que el "id" introducido sea válido, exita/esté registrado en la DB.
            if (coche == null){
                System.out.println("\n\t\tNo se existe o no encontró ningún coche con ese ID ('" + id + "')");
                return;
            }
            
            System.out.println("\n\t\tCoche a eliminar:");
            mostrarCoche(coche);
            
            // Solicita confirmación previa para asegurarse de realizar la acción de borrado.
            System.out.print("\n\t\t¿Está seguro de que desea eliminar este coche? (Sí/No): ");
            String confirmacion = user.nextLine().trim().toUpperCase(); // // Normaliza entrada.
            
            if (confirmacion.equals("Sí") || confirmacion.equals("Si")){
                coleccionCoches.deleteOne(Filters.eq("_id", id));   // Borra el documento por ID.
                System.out.println("\n\t\tEl proceso de eliminación ha sido efectuado de forma exitosa. El coche ha sido eliminado correctamente.");
            } else{
                System.out.println("\n\t\tEl proceso de eliminación ha sido cancelado. El coche no ha sido eliminado.");
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución al intentar eliminar/borrar el objeto, el coche, junto a sus parámetros, atributos: " + ex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // =============== Apartado 8 ===============
        // Crea el método "buscarPorRangoPrecio". Su función será la de hacer una búsqueda avanzada/compleja sobre los datos registrados en la DB, búsqueda y listado de coches según un rango de precios.
    private static void buscarPorRangoPrecio(){
        System.out.println("\n\n\t<==================== BÚSQUEDA POR RANGO DE PRECIO ====================>");
        
        try{
            System.out.print("\n\t\tPrecio mínimo como parámetro de búsqueda (€): ");
            double precioMin = leerDouble();
            
            System.out.print("\n\t\tPrecio máximo como parámetro de búsqueda (€): ");
            double precioMax = leerDouble();
            
            // Búsqueda con filtros combinados.
            FindIterable<Document> coches = coleccionCoches.find(
                Filters.and(                                          // Filtros combinados ("gte" y "lte").
                    Filters.gte("precio", precioMin),   //
                    Filters.lte("precio", precioMax)    //
                )
            );
            
            int contador = 0;
            
            // Itera y muestra cada documento.
            for (Document coche : coches){
                contador++;
                mostrarCoche(coche);
            }
            
            // Condicional de tipo "if" para validar que existan coches introducidos/registrados en la DB.
            if (contador == 0){
                System.out.println("\n\t\tNo existen o no hay ningún coches registrados para dicho rango de precios (Precio Mínimo-Máximo: " + precioMin + "-" + precioMax + ").");
            } else{
                System.out.println("\n\t\tSe han encontrado/listado un total de " + contador + " coches comprendidos entre el rango de precios (Precio Mínimo-Máximo: " + precioMin + "-" + precioMax + ").");
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución al intentar hacer una búsqueda parametrizada [entre más de un parámetro, entre rangos de precios (Precio Mínimo-Máximo): " + ex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // =============== MÉTODO AUXILIAR (opcional) ===============
        // Crea el método "mostrarCoche". Su función será la de mostrar e imprimir pontalla con mayor exactitud los datos concretos de un coche solicitado (método que simula el hecho de estar imprimiendo o creando una línea de comando de impresión por cada método de apartado realizado).
    private static void mostrarCoche(Document coche){
        System.out.println("\n\n\t<==================== COCHE (solicitado) ====================>");
        
        System.out.print("\n\t┌─────────────────────────────────────────┐");
        System.out.print("\n\t│ ID: " + coche.getObjectId("_id"));
        System.out.print("\n\t├─────────────────────────────────────────┤");
        System.out.print("\n\t│ Marca:              " + coche.getString("marca"));
        System.out.print("\n\t│ Modelo:             " + coche.getString("modelo"));
        System.out.print("\n\t│ Año (XXXX):         " + coche.getInteger("anio"));
        System.out.print("\n\t│ Kilómetros (Km):    " + coche.getInteger("kilometros") + " Km");
        System.out.print("\n\t│ Precio (€):         " + String.format("%.2f", coche.getDouble("precio")) + " €");
        System.out.print("\n\t│ Color:              " + coche.getString("color"));
        System.out.print("\n\t│ Vendido:            " + (coche.getBoolean("vendido") ? "Sí" : "No"));
        System.out.print("\n\t└─────────────────────────────────────────┘");
    }
    
    
    // Crea el método "leerOpcion" con el que la consola leer la interacción hecha (en numérico con decimales, "Double") por el usuario usuario con el menú de opciones.
    private static double leerDouble(){
        while (true){
            try{
                /*
                 * String linea = user.nextLine();          // Lee toda la línea y la parsea.
                 * return Double.parseDouble(linea.trim()); // Parse seguro.
                 */
                double valor = user.nextDouble();   // Parsea a tipo "Doube" el valor leído, toda la línea.
                user.nextLine();                    // Limpia el buffer.
                return valor;                       // Devuelve el valor ya parseado, tratado a "Double".
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Error. Debe introducir un número decimal válido: " + ex.getMessage(), "Error de lectura", JOptionPane.ERROR_MESSAGE);
                user.nextLine();    // Limpia el buffer.
            }
        }
    }
    
    // Crea el método "leerOpcion" con el que la consola leer la interacción hecha (en cadena, "String") por el usuario usuario con el menú de opciones.
    private static int leerEntero(){
        while (true){
            try{
                /*
                 * String linea = user.nextLine();          // Lee toda la línea y la parsea.
                 * return Double.parseDouble(linea.trim()); // Parse seguro.
                 */
                int  valor = user.nextInt();    // Parsea a tipo "Int" el valor leído, toda la línea.
                user.nextLine();                // Limpia el buffer.
                return valor;                   // Devuelve el valor ya parseado, tratado a "Int".
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Error. Debe introducir un número decimal válido: " + ex.getMessage(), "Error de lectura", JOptionPane.ERROR_MESSAGE);
                user.nextLine();    // Limpia el buffer.
            }
        }
    }
    
    // Crea el método "salir" con el que poder salir y cerrar el programa.
    private static boolean salir(){
        System.out.println("\n\n\tCerrando conexiones con la base de datos...");
        
        try{
            // Condicional de tipo "if" para validar/verificar que el escáner es distinto de nulo. Si es el caso, se cierra.
            if (user != null) user.close();
            
            // Condicional de tipo "if" para validar/verificar que el cliente de Mongo, "mongoClient", es distinto de nulo. Si es el caso, se cierra e imprime un mensaje.
            if (mongoClient != null) mongoClient.close(); System.out.println("\n\n\tLa conexión con la base de datos ha sido cerrada correctamente.");
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error inesperado al intentar cerrar los recursos: " + ex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        }
        
        // Devuelve/Retorna el valor de la función como "false".
        return false;
    }
}