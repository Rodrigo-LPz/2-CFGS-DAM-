/**
 *
 * @author Rodrigo
 */
package com.dam;

// Importa del paquete "com.dam.gestion" el archivo "GestionBiblioteca.java".
import com.dam.gestion.GestionBiblioteca;
// Importa todos las dependencias creadas en el paquete "modelo.Ejemplar".
import com.dam.modelo.Ejemplar.*;
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
    // Crea el escáner para leer datos introducidos por teclado, por el usuario.
    private static final Scanner user = new Scanner(System.in);
    
    // Crea el objeto "gestor" que contiene todas las operaciones CRUD importadas del archivo "GestionBiblioteca.java".
    private static final GestionBiblioteca gestor = new GestionBiblioteca();
    
    // Crea el método 'main', principal del programa.
    public static void main(String[] args){
        boolean continuar = true;
        
        // Bucle "do-while" que hará de bucle principal del menú.
        do{
            try{
                // Llamamos/Inicializamos el método "mostrarMenuPrincipal".
                mostrarMenuPrincipal();
                
                int opcion = leerOpcion();
                
                // Condicional "switch" para las opciones.
                switch (opcion){
                    // ========== OPERACIONES CREATE ==========
                    /*
                    case 1:
                        // Inserta autores, libros y ejemplares de prueba (apartado 3).
                        gestor.insertarDatosIniciales();
                        break;
                    */
                    case 1 -> crearAutorConLibros();
                    case 2 -> agregarEjemplarALibroExistente();
                    
                    // ========== OPERACIONES READ ==========
                    case 3 -> listarTodosLosAutores();
                    case 4 -> buscarLibroPorId();
                    case 5 -> buscarEjemplaresPorEstado();
                    case 6 -> obtenerEstadisticasBiblioteca();
                    
                    // ========== OPERACIONES UPDATE ==========
                    case 7 -> actualizarEstadoEjemplar();
                    case 8 -> actualizarDatosLibro();
                    case 9 -> transferirLibrosEntreAutores();
                    
                    // ========== OPERACIONES DELETE ==========
                    case 10 -> eliminarEjemplar();
                    case 11 -> eliminarLibro();
                    case 12 -> eliminarAutorConCascada();
                    
                    // ========== SALIR ==========
                    case 0 -> continuar = salir();
                    
                    /*
                    default:
                        System.out.println("⚠️ Opción no válida. Intenta de nuevo.");
                    */
                    default -> System.out.println("\n\n\tOpción introducida no válida. Intente nuevamente...");
                }
                
                // Salto de línea.
                System.out.println();
                
                if (continuar && opcion >= 0 && opcion <= 11){
                    esperarEnter(); // Llamamos/Inicializamos el método "esperarEnter".
                }
            } catch (InputMismatchException imex){
                JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución (debe ingresar un número válido): " + imex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
                imex.printStackTrace();
                user.nextLine(); // Limpia el buffer.
                esperarEnter(); // Llamamos/Inicializamos el método "esperarEnter".
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
        System.out.println("\n\n\t<==================== SISTEMA GESTIÓN DE BIBLIOTECA ====================>");
        System.out.println("\n\t\t\t\tOPERACIONES CREATE");
        System.out.println("\t\t\t1. Crear nuevo autor con libros.");
        System.out.println("\t\t\t2. Agregar ejemplar a libro existente.");
        System.out.println("\n\t\t\t\tOPERACIONES READ");
        System.out.println("\t\t\t3. Buscar/Listar todos los autores registrados.");
        System.out.println("\t\t\t4. Buscar libro por número de identificación único (ID).");
        System.out.println("\t\t\t5. Buscar ejemplares por estado.");
        System.out.println("\t\t\t6. Mostrar/Obtener estadísticas.");
        System.out.println("\n\t\t\t\tOPERACIONES UPDATE");
        System.out.println("\t\t\t7. Actualizar estado de ejemplar.");
        System.out.println("\t\t\t8. Actualizar datos de libro.");
        System.out.println("\t\t\t9. Transferir los libros de un autor a otro autor.");
        System.out.println("\n\t\t\t\tOPERACIONES DELETE");
        System.out.println("\t\t\t10. Eliminar ejemplar.");
        System.out.println("\t\t\t11. Eliminar libro.");
        System.out.println("\t\t\t12. Eliminar autor.");
        System.out.println("\n\n\t\t\t0. Salir.");
        System.out.println("\t========================================================================");
        System.out.print("\n\tSeleccione una opción: ");
        }
    
    // Crea el método "leerOpcion" con el que la consola leer la interacción hecha por el usuario usuario con el menú de opciones.
    private static int leerOpcion(){
        return user.nextInt();
    }
    
    // ==================== OPERACIONES CREATE ====================
        // Crea el método "crearAutorConLibros".
    private static void crearAutorConLibros(){
        System.out.println("\n\n\tCreando 1 autor {Javier Marías} con 2 libros {Corazón tan blanco} y {Mañana en la batalla piensa en mí} y 3 ejemplares {EJ-009-2024} y {EJ-010-2024} ambos para el primer libro y {EJ-011-2024} para el segundo libro...");
        gestor.crearAutorConLibros(); // Llama/Inicializa al método "crearAutorConLibros" del archivo "GestionBiblioteca".
    }
    
        // Crea el método "agregarEjemplarALibroExistente".
    private static void agregarEjemplarALibroExistente(){
        System.out.println("\n\n\tAgregar ejemplar a libro...");
        user.nextLine(); // Limpia el buffer.
        
        System.out.print("\n\t\tIngrese el ISBN del libro: ");
        String isbn = user.nextLine();
        
        System.out.print("\n\t\tIngrese el código del nuevo ejemplar (ej: EJ-XXX-2024): ");
        String codigoEjemplar = user.nextLine();
        
        gestor.agregarEjemplarALibroExistente(isbn, codigoEjemplar); // Llama/Inicializa al método "agregarEjemplarALibroExistente" del archivo "GestionBiblioteca".
    }
    
    // ==================== OPERACIONES READ ====================
        // Crea el método "listarTodosLosAutores".
    private static void listarTodosLosAutores(){
        System.out.println("\n\n\tListando a los autores...");
        gestor.listarTodosLosAutores(); // Llama/Inicializa al método "listarTodosLosAutores" del archivo "GestionBiblioteca".
    }
    
        // Crea el método "buscarLibroPorId".
    private static void buscarLibroPorId(){
        System.out.println("\n\n\tBuscando un libro por su id...");
        System.out.print("\n\n\tIngrese el ID del libro: ");
        Long id = user.nextLong();
        user.nextLine(); // Limpia el buffer.
        
        gestor.buscarLibroPorId(id); // Llama/Inicializa al método "buscarLibroPorId" del archivo "GestionBiblioteca".
    }
    
        // Crea el método "buscarEjemplaresPorEstado".
    private static void buscarEjemplaresPorEstado(){
        System.out.println("\n\n\tBuscando ejemplares por su estado...");
        
        System.out.println("\n\n\tEstados disponibles:");
        int i = 1;
        
        for (EstadoEjemplar estado : EstadoEjemplar.values()){
            System.out.println("\t\t  " + i + ". " + estado);
            i++;
        }
        
        user.nextLine(); // Limpia el buffer.
        
        System.out.print("\n\n\tSeleccione un estado (1 - " + EstadoEjemplar.values().length + "): ");
        
        int opcion = user.nextInt();
        
        if (opcion >= 1 && opcion <= EstadoEjemplar.values().length){
            EstadoEjemplar estado = EstadoEjemplar.values()[opcion - 1];
            gestor.buscarEjemplaresPorEstado(estado);  // Llama/Inicializa al método "buscarEjemplaresPorEstado" del archivo "GestionBiblioteca".
        } else{
            System.out.println("\n\n\tError. La opción introducida no es válida.");
            return;
        }
    }
    
    private static void obtenerEstadisticasBiblioteca(){
        System.out.println("\n\n\tObteniendo/Mostrando las estadísticas...");
        gestor.obtenerEstadisticasBiblioteca(); // Llama/Inicializa al método "obtenerEstadisticasBiblioteca" del archivo "GestionBiblioteca".
    }

    // ==================== OPERACIONES UPDATE ====================
        // Crea el método "actualizarEstadoEjemplar".
    private static void actualizarEstadoEjemplar(){
        System.out.println("\n\n\tActualizando el estado del ejemplar...");
        
        System.out.print("\n\n\tIngrese el ID del ejemplar: ");
        Long id = user.nextLong();
        user.nextLine(); // Limpia el buffer.
        
        System.out.println("\n\n\tEstados disponibles (a cambiar): ");
        
        int i = 1;
        
        for (EstadoEjemplar estado : EstadoEjemplar.values()){
            System.out.println("\t\t  " + i + ". " + estado);
            i++;
        }
        
        System.out.print("\n\n\tSeleccione el nuevo estado (1 - " + EstadoEjemplar.values().length + "): ");
        
        int opcion = user.nextInt();
        
        if (opcion >= 1 && opcion <= EstadoEjemplar.values().length){
            EstadoEjemplar nuevoEstado = EstadoEjemplar.values()[opcion - 1];
            gestor.actualizarEstadoEjemplar(id, nuevoEstado); // Llama/Inicializa al método "actualizarEstadoEjemplar" del archivo "GestionBiblioteca".
        } else{
            System.out.println("\n\n\tError. La opción introducida no es válida.");
        }
    }
    
        // Crea el método "actualizarDatosLibro".
    private static void actualizarDatosLibro(){
        System.out.println("\n\n\tActualizando los datos del libro...");
        
        System.out.print("\n\n\tIngrese el ID del libro: ");
        Long id = user.nextLong();
        user.nextLine(); // Limpia el buffer.
        
        System.out.print("\n\n\tIngrese el nuevo título: ");
        String nuevoTitulo = user.nextLine();
        
        System.out.print("\n\n\tIngrese el nuevo número de páginas: ");
        Integer nuevasPaginas = user.nextInt();
        user.nextLine(); // Limpia el buffer.
        
        gestor.actualizarDatosLibro(id, nuevoTitulo, nuevasPaginas); // Llama/Inicializa al método "actualizarDatosLibro" del archivo "GestionBiblioteca".
    }
    
        // Crea el método "actualizarDatosLibro".
    private static void transferirLibrosEntreAutores(){
        System.out.println("\n\n\tTransfiriendo libros entre autores...");

        System.out.print("\n\n\tIngrese el ID del autor (de origen) (de quien se transferirán los libros): ");
        Long idAutorOrigen = user.nextLong();

        System.out.print("\n\n\tIngrese el ID del autor (de destino) (quien recibirá los libros): ");
        Long idAutorDestino = user.nextLong();

        user.nextLine(); // Limpia el buffer.

        System.out.print("\n\n\t¿Está seguro de transferir TODOS los libros? (Sí/No): ");
        String confirmacion = user.nextLine().toLowerCase();

        if (confirmacion.equalsIgnoreCase("sí") || confirmacion.equalsIgnoreCase("si")){
            gestor.transferirLibrosEntreAutores(idAutorOrigen, idAutorDestino);  // Llama/Inicializa al método "actualizarDatosLibro" del archivo "GestionBiblioteca".
        } else{
            System.out.println("\n\n\tError. La opción introducida no es válida.");
        }
    }
    
    // ==================== OPERACIONES DELETE ====================
        // Crea el método "eliminarEjemplar".
    private static void eliminarEjemplar(){
        System.out.println("\n\n\tEliminando ejemplar...");
        
        System.out.print("\n\n\tIngrese el ID del ejemplar a eliminar: ");
        Long idEjemplar = user.nextLong();
        user.nextLine(); // Limpia el buffer.
        
        System.out.print("\n\n\t¿Está seguro de eliminar este ejemplar? (Sí/No): ");
        String confirmacion = user.nextLine().toLowerCase();
        
        if (confirmacion.equalsIgnoreCase("sí") || confirmacion.equalsIgnoreCase("si")){
            gestor.eliminarEjemplar(idEjemplar); // Llama/Inicializa al método "eliminarEjemplar" del archivo "GestionBiblioteca".
        } else{
            System.out.println("\n\n\tError. La opción introducida no es válida.");
        }
    }
    
        // Crea el método "eliminarLibro".
    private static void eliminarLibro(){
        System.out.println("\n\n\tEliminando libro...");
        
        System.out.print("\n\n\tIngrese el ID del libro a eliminar: ");
        Long idLibro = user.nextLong();
        user.nextLine(); // Limpia el buffer.
        
        System.out.println("\n\n\tADVERTENCIA CRÍTICA️");
        System.out.println("\n\t\tSe eliminará el libro Y todos sus ejemplares (cascada)");
        System.out.print("\n\t\t¿Está seguro? (Sí/No): ");
        String confirmacion = user.nextLine().toLowerCase();
        
        if (confirmacion.equalsIgnoreCase("sí") || confirmacion.equalsIgnoreCase("si")){
            gestor.eliminarLibro(idLibro); // Llama/Inicializa al método "eliminarEjemplar" del archivo "GestionBiblioteca".
        } else{
            System.out.println("\n\n\tError. La opción introducida no es válida.");
        }
    }
    
        // Crea el método "eliminarAutorConCascada".
    private static void eliminarAutorConCascada(){
        System.out.println("\n\n\tEliminando autor...");
        
        System.out.print("Ingrese el ID del autor a eliminar: ");
        Long idAutor = user.nextLong();
        user.nextLine(); // Limpia el buffer.
        
        System.out.println("\n\n\tADVERTENCIA CRÍTICA️");
        System.out.println("\n\t\tSe eliminará:");
        System.out.println("\n\t\t\t- El AUTOR.");
        System.out.println("\n\t\t\t- TODOS sus LIBROS (cascada).");
        System.out.println("\n\t\t\t- TODOS los EJEMPLARES de esos libros (cascada).");
        System.out.print("\n¿Está COMPLETAMENTE seguro? (Sí/No): ");
        String confirmacion = user.nextLine().toLowerCase();
        
        if (confirmacion.equalsIgnoreCase("sí") || confirmacion.equalsIgnoreCase("si")){
            gestor.eliminarAutorConCascada(idAutor); // Llama/Inicializa al método "eliminarLibro" del archivo "GestionBiblioteca".
        } else {
            System.out.println("\n\n\tError. La opción introducida no es válida.");
        }
    }
    
    // Crea el método "salir".
    private static boolean salir(){
        System.out.println("\n\n\tCerrando conexiones con la base de datos...");
        
        HibernateUtil.shutdown();
        user.close();
        
        System.out.println("\n\n\tLa conexión con la base de datos ha sido cerrada correctamente.");
        
        return false;
    }
    
    // Crea el método "esperarEnter".
    private static void esperarEnter(){
        System.out.print("Presione \"Enter\" (↵) para volver al menú principal...");
        
        try {
            System.in.read();
            user.nextLine();        // Limpia el buffer.
        } catch (Exception ex){}    // Ignorar excepción.
    }
}