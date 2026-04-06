/**
 *
 * @author Rodrigo
 */
package com.dam;

// Importa del paquete "com.dam.gestion" el archivo "GestionBiblioteca.java".
import com.dam.gestion.GestionSupermercado;
// Importa todos las dependencias creadas en el paquete "modelo.Lote".
import com.dam.modelo.Lote.*;
// Importa de la biblioteca/librería el paquete "HibernateUtil".
import com.dam.util.HibernateUtil;
// Importa de la biblioteca/librería el paquete "InputMismatchException".
import java.util.InputMismatchException;
// Importa de la biblioteca/librería el paquete "Scanner".
import java.util.Scanner;
// Importa de la biblioteca/librería el paquete "JOptionPane".
import javax.swing.JOptionPane;

// Crea la clase 'main', principal del programa.
public class Main{
    // Crea el objeto escáner para leer datos introducidos por teclado, por el usuario.
    private static final Scanner user = new Scanner(System.in);
    
    // Crea el objeto "gestor" que contiene todas las operaciones CRUD importadas del archivo "GestionBiblioteca.java".
    private static final GestionSupermercado gestor = new GestionSupermercado();
    
    // Crea el método 'main', principal del programa.
    public static void main(String[] args){
        boolean continuar = true;
        
        System.out.println("\n\n\t¡Bienvenido al Sistema de Gestión de Supermercado!");
        
        // Bucle "do-while" que hará de bucle principal del menú.
        do{
            try{
                // Llamamos/Inicializamos el método "mostrarMenuPrincipal".
                mostrarMenuPrincipal();
                
                int opcion = leerOpcion();
                
                // Condicional "switch" para las opciones a llamar/inicializar los métodos.
                switch (opcion){
                    // ========== OPERACIONES CREATE ==========
                    /*
                    case 1:
                        gestor.crearProveedorConProductos();
                        break;
                    */
                    case 1 -> crearProveedorConProductos();
                    case 2 -> agregarLoteAProductoExistente();
                    
                    // ========== OPERACIONES READ ==========
                    case 3 -> listarTodosLosProveedores();
                    case 4 -> buscarProductoPorId();
                    case 5 -> buscarLotesPorEstado();
                    case 6 -> obtenerEstadisticasSupermercado();
                    
                    // ========== OPERACIONES UPDATE ==========
                    case 7 -> actualizarEstadoLote();
                    case 8 -> actualizarPrecioProducto();
                    case 9 -> transferirProductosEntreProveedores();
                    
                    // ========== OPERACIONES DELETE ==========
                    case 10 -> eliminarLote();
                    case 11 -> eliminarProducto();
                    case 12 -> eliminarProveedorConCascada();
                    
                    // ========== SALIR ==========
                    case 0 -> continuar = salir();
                    
                    /*
                    default:
                        System.out.println("\n\n\tLa opción introducida no válida. Intente nuevamente... Recuerde que el rango de opciones va desde 0 hasta el 12, ambos incluidos.");
                    */
                    default -> System.out.println("\n\n\tLa opción introducida no es válida. Intente nuevamente... Recuerde que el rango de opciones va desde 0 hasta el 12, ambos incluidos.");
                }
                
                // Salto de línea.
                System.out.println();
                
                // Bloque de código para llamar/inicializar el método "esperarEnter".
                if (continuar && opcion >= 0 && opcion <= 12){ esperarEnter(); }
            } catch (InputMismatchException imex){
                JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución (debe ingresar un número válido): " + imex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
                imex.printStackTrace();
                user.nextLine();    // Limpia el buffer.
                esperarEnter();     // Llamamos/Inicializamos el método "esperarEnter".
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución: " + ex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                esperarEnter(); // Llamamos/Inicializamos el método "esperarEnter".
            }
        } while (continuar);
    }
    
    // Crea el método "mostrarMenuPrincipal" con el que mostrar al usuario el menú de opciones.
    private static void mostrarMenuPrincipal(){
        // Mostramos el menú de opciones disponible para el usuario.
        System.out.println("\n\n\t<==================== SISTEMA GESTIÓN DE SUPERMERCADO ====================>");
        System.out.println("\n\t\t\t\tOPERACIONES CREATE");
        System.out.println("\t\t\t1. Crear nuevo proveedor con productos y lotes.");
        System.out.println("\t\t\t2. Agregar lote a un producto existente.");
        System.out.println("\n\t\t\t\tOPERACIONES READ");
        System.out.println("\t\t\t3. Buscar y listar todos los proveedores registrados.");
        System.out.println("\t\t\t4. Buscar productos por su número de identificación único (ID).");
        System.out.println("\t\t\t5. Buscar lotes por estado.");
        System.out.println("\t\t\t6. Obtener y mostrar las estadísticas del supermercado.");
        System.out.println("\n\t\t\t\tOPERACIONES UPDATE");
        System.out.println("\t\t\t7. Actualizar estado de un lote.");
        System.out.println("\t\t\t8. Actualizar precio de un producto.");
        System.out.println("\t\t\t9. Transferir productos entre proveedores.");
        System.out.println("\n\t\t\t\tOPERACIONES DELETE");
        System.out.println("\t\t\t10. Eliminar lote.");
        System.out.println("\t\t\t11. Eliminar producto.");
        System.out.println("\t\t\t12. Eliminar proveedor.");
        System.out.println("\n\n\t\t\t0. Salir.");
        //System.out.println("====================================================================================================");
        System.out.println("=".repeat(100));
        System.out.print("\n\n\tSeleccione una opción: ");
        }
    
    // Crea el método "leerOpcion" con el que la consola leer la interacción hecha por el usuario usuario con el menú de opciones.
    private static int leerOpcion(){
        return user.nextInt();
    }
    
    // ==================== OPERACIONES CREATE ====================
        // Crea el método "crearProveedorConProductos".
    private static void crearProveedorConProductos(){
        System.out.println("\n\n\tCreando...\n\t\t1 proveedor {Distribuciones García S.L.}.\n\t\tcon 2 productos {Aceite de oliva} y {Arroz integral}.\n\t\tY 3 lotes {LOT-2024-001} y {LOT-2024-002} ambos para el primer producto, asociados a {Aceite de oliva} y {LOT-2024-003} para el segundo producto, asociado a {Arroz integral}\n\n\t...");
        gestor.crearProveedorConProductos();    // Llama/Inicializa al método "crearProveedorConProductos" del archivo "GestionSupermercado.java".
    }
    
        // Crea el método "agregarLoteAProductoExistente".
    private static void agregarLoteAProductoExistente(){
        System.out.println("\n\n\tAgregando un nuevo lote a un producto existente...");
        user.nextLine();    // Limpia el buffer.
        
        System.out.print("\n\t\tIngrese el código de barras del producto a añadir un nuevo lote –> (ej: 8412345678901): ");
        String codigoBarras = user.nextLine();
        
        System.out.print("\n\t\tIngrese el código del nuevo lote a añadir al producto ('" + codigoBarras + "') –> (ej: LOT-2024-XXX): ");
        String codigoLote  = user.nextLine();
        
        gestor.agregarLoteAProductoExistente(codigoBarras, codigoLote );    // Llama/Inicializa al método "agregarLoteAProductoExistente" del archivo "GestionSupermercado.java".
    }
    
    // ==================== OPERACIONES READ ====================
        // Crea el método "listarTodosLosProveedores".
    private static void listarTodosLosProveedores(){
        System.out.println("\n\n\tListando a todos los proveedores registrados en la DB...");
        gestor.listarTodosLosProveedores(); // Llama/Inicializa al método "listarTodosLosProveedores" del archivo "GestionSupermercado.java".
    }
    
        // Crea el método "buscarProductoPorId".
    private static void buscarProductoPorId(){
        System.out.println("\n\n\tBuscando un producto por su id...");
        System.out.print("\n\n\tIngrese el número de identificación (ID) del producto: ");
        Long id = user.nextLong();
        user.nextLine();    // Limpia el buffer.
        
        gestor.buscarProductoPorId(id); // Llama/Inicializa al método "buscarProductoPorId" del archivo "GestionSupermercado.java".
    }
    
        // Crea el método "buscarLotesPorEstado".
    private static void buscarLotesPorEstado(){
        System.out.println("\n\n\tBuscando lotes por su estado...");
        
        System.out.println("\n\n\tEstados disponibles:");
        
        int i = 1;
        
        for (EstadoLote estado : EstadoLote.values()){
            System.out.println("\t\t  " + i + ". " + estado);
            i++;
        }
        
        user.nextLine();    // Limpia el buffer.
        
        System.out.print("\n\n\tSeleccione un estado (1 - " + EstadoLote.values().length + "): ");
        
        int opcion = user.nextInt();
        
        if (opcion >= 1 && opcion <= EstadoLote.values().length){
            EstadoLote estado = EstadoLote.values()[opcion - 1];
            gestor.buscarLotesPorEstado(estado);    // Llama/Inicializa al método "buscarLotesPorEstado" del archivo "GestionSupermercado.java".
        } else{
            System.out.println("\n\n\tError. La opción introducida no es válida. Intente nuevamente...");
            return;
        }
    }
    
        // Crea el método "obtenerEstadisticasSupermercado".
    private static void obtenerEstadisticasSupermercado(){
        System.out.println("\n\n\tObteniendo y mostrando las estadísticas...");
        gestor.obtenerEstadisticasSupermercado(); // Llama/Inicializa al método "obtenerEstadisticasSupermercado" del archivo "GestionSupermercado.java".
    }

    // ==================== OPERACIONES UPDATE ====================
        // Crea el método "actualizarEstadoLote".
    private static void actualizarEstadoLote(){
        System.out.println("\n\n\tActualizando el estado del lote...");
        
        System.out.print("\n\n\tIngrese el ID del lote: ");
        Long id = user.nextLong();
        user.nextLine();    // Limpia el buffer.
        
        System.out.println("\n\n\tEstados disponibles (a cambiar): ");
        
        int i = 1;
        
        for (EstadoLote estado : EstadoLote.values()){
            System.out.println("\t\t  " + i + ". " + estado);
            i++;
        }
        
        System.out.print("\n\n\tSeleccione el nuevo estado (1 - " + EstadoLote.values().length + "): ");
        
        int opcion = user.nextInt();
        
        if (opcion >= 1 && opcion <= EstadoLote.values().length){
            EstadoLote nuevoEstado = EstadoLote.values()[opcion - 1];
            gestor.actualizarEstadoLote(id, nuevoEstado);   // Llama/Inicializa al método "actualizarEstadoLote" del archivo "GestionSupermercado.java".
        } else{
            System.out.println("\n\n\tError. La opción introducida no es válida. Intente nuevamente...");
            return;
        }
    }
    
        // Crea el método "actualizarPrecioProducto".
    private static void actualizarPrecioProducto(){
        System.out.println("\n\n\tActualizando los datos del producto...");
        
        System.out.print("\n\n\tIngrese el ID del producto: ");
        Long id = user.nextLong();
        user.nextLine();    // Limpia el buffer.
        
        System.out.print("\n\n\tIngrese un nuevo precio (€): ");
        Double nuevoPrecio = user.nextDouble();
        user.nextLine();    // Limpia el buffer.
        
        gestor.actualizarPrecioProducto(id, nuevoPrecio);   // Llama/Inicializa al método "actualizarPrecioProducto" del archivo "GestionSupermercado.java".
    }
    
        // Crea el método "transferirProductosEntreProveedores".
    private static void transferirProductosEntreProveedores(){
        System.out.println("\n\n\tTransfiriendo productos entre proveedores...");

        System.out.print("\n\n\tIngrese el ID del proveedor (de origen) (de quien se transferirán los producos): ");
        Long idProveedorOrigen = user.nextLong();

        System.out.print("\n\n\tIngrese el ID del proveedor (de destino) (quien recibirá los producos): ");
        Long idProveedorDestino = user.nextLong();

        user.nextLine();    // Limpia el buffer.

        System.out.print("\n\n\t¿Está seguro de transferir TODOS los productos? (Sí/No): ");
        String confirmacion = user.nextLine().toLowerCase();

        if (confirmacion.equalsIgnoreCase("sí") || confirmacion.equalsIgnoreCase("si")){
            gestor.transferirProductosEntreProveedores(idProveedorOrigen, idProveedorDestino);  // Llama/Inicializa al método "transferirProductosEntreProveedores" del archivo "GestionSupermercado.java".
        } else{
            System.out.println("\n\n\tError. La opción introducida no es válida. Intente nuevamente...");
            return;
        }
    }
    
    // ==================== OPERACIONES DELETE ====================
        // Crea el método "eliminarLote".
    private static void eliminarLote(){
        System.out.println("\n\n\tEliminando lote...");
        
        System.out.print("\n\n\tIngrese el ID del lote a eliminar: ");
        Long idLote = user.nextLong();
        user.nextLine();    // Limpia el buffer.
        
        System.out.print("\n\n\t¿Está seguro de eliminar este lote? (Sí/No): ");
        String confirmacion = user.nextLine().toLowerCase();
        
        if (confirmacion.equalsIgnoreCase("sí") || confirmacion.equalsIgnoreCase("si")){
            gestor.eliminarLote(idLote);    // Llama/Inicializa al método "eliminarLote" del archivo "GestionSupermercado.java".
        } else{
            System.out.println("\n\n\tError. La opción introducida no es válida. Intente nuevamente...");
            return;
        }
    }
    
        // Crea el método "eliminarProducto".
    private static void eliminarProducto(){
        System.out.println("\n\n\tEliminando producto...");
        
        System.out.print("\n\n\tIngrese el ID del producto a eliminar: ");
        Long idProducto = user.nextLong();
        user.nextLine(); // Limpia el buffer.
        
        System.out.println("\n\n\tADVERTENCIA CRÍTICA️");
        System.out.println("\n\t\tSe eliminará el producto Y todos sus lotes (cascada).");
        System.out.print("\n\t\t¿Está seguro? (Sí/No): ");
        String confirmacion = user.nextLine().toLowerCase();
        
        if (confirmacion.equalsIgnoreCase("sí") || confirmacion.equalsIgnoreCase("si")){
            gestor.eliminarProducto(idProducto);    // Llama/Inicializa al método "eliminarProducto" del archivo "GestionSupermercado.java".
        } else{
            System.out.println("\n\n\tError. La opción introducida no es válida. Intente nuevamente...");
            return;
        }
    }
    
        // Crea el método "eliminarProveedorConCascada".
    private static void eliminarProveedorConCascada(){
        System.out.println("\n\n\tEliminando proveedor...");
        
        System.out.print("\n\n\tIngrese el ID del proveedor a eliminar: ");
        Long idProveedor = user.nextLong();
        user.nextLine();    // Limpia el buffer.
        
        System.out.println("\n\n\tADVERTENCIA CRÍTICA️");
        System.out.println("\n\t\tSe eliminará el proveedor junto a todos sus productos (cascada) y junto a todos los lotes de esos productos (cascada).");
        System.out.print("\n\t\t¿Está COMPLETAMENTE seguro? (Sí/No): ");
        String confirmacion = user.nextLine().toLowerCase();
        
        if (confirmacion.equalsIgnoreCase("sí") || confirmacion.equalsIgnoreCase("si")){
            gestor.eliminarProveedorConCascada(idProveedor);    // Llama/Inicializa al método "eliminarProveedorConCascada" del archivo "GestionSupermercado.java".
        } else {
            System.out.println("\n\n\tError. La opción introducida no es válida. Intente nuevamente...");
            return;
        }
    }
    
    // Crea el método "salir".
    private static boolean salir(){
        System.out.println("\n\n\tCerrando conexiones con la base de datos...");
        
        // // Llama/Inicializa al método "shutdown" del archivo "HibernateUtil.java".
        HibernateUtil.shutdown();
        
        // Cierra/Finaliza el objeto escáner.
        user.close();
        
        System.out.println("\n\n\tLa conexión con la base de datos ha sido cerrada correctamente.");
        
        System.out.println("\n\n\t¡Gracias por usar el Sistema de Gestión de Supermercado!");
        
        return false;
    }
    
    // Crea el método "esperarEnter".
    private static void esperarEnter(){
        System.out.print("Presione \"Enter\" (↵) para volver al menú principal...");
        
        try {
            System.in.read();
            user.nextLine();    // Limpia el buffer.
        } catch (Exception ex){ /* Ignorar excepción. */ }
    }
}