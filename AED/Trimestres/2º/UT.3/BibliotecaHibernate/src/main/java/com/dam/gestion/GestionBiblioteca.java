/**
 *
 * @author Rodrigo
 */
package com.dam.gestion;


// Importa todos las dependencias creadas en el paquete "modelo".
import com.dam.modelo.*;
// Importa todos las dependencias creadas en el paquete "modelo.Ejemplar".
import com.dam.modelo.Ejemplar.*;
// Importa todos las dependencias creadas en el paquete "modelo".
import com.dam.util.*;
// Importa de todos los paquetes de la biblioteca/librería "Session".
import org.hibernate.Session;
// Importa de todos los paquetes de la biblioteca/librería "Transaction".
import org.hibernate.Transaction;
// Importa de todos los paquetes de la biblioteca/librería "LocalDate".
import java.time.LocalDate;
// Importa de todos los paquetes de la biblioteca/librería "ArrayList".
import java.util.ArrayList;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;
// Importa de todos los paquetes de la biblioteca/librería "JOptionPane".
import javax.swing.JOptionPane;
// Importa de la biblioteca/librería el paquete "HibernateException".
import org.hibernate.HibernateException;
// Importa de la biblioteca/librería el paquete "Query".
import org.hibernate.query.Query;

// Crea la clase "GestionBiblioteca", ésta contendrá la parte funcional del programa, es decir, todos los métodos del programa.
public class GestionBiblioteca{
    // Crea e inicializa el objeto para la sesión.
    Session session = null;
    
    // Crea e inicializa el objeto para las operaciones de transacciones.
    Transaction transaction = null;
    
    // Crea el método "crearAutorConLibros". Su función será ir añadiendo objetos, autores, (asociándole también dos nuevos libros) a la tabla "autores" de nuesta base de datos.
        /*
         * Crea un nuevo autor con dos libros asociados, y cada libro con sus ejemplares. Gracias a las operaciones en cascada "CascadeType.ALL" al guardar el autor, se guardan automáticamente sus libros y ejemplares. Esto demuestra que el mapeo y la cascada funcionan correctamente.
         */
    public void crearAutorConLibros(){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            System.out.println("\n\n\tCreando el autor junto a sus libros y respectivos ejemplares...");
            
            // Bloque de código para crear el autor.
            Autor autor = new Autor(
                    "Javier",
                    "Marías",
                    "Española",
                    LocalDate.of(1951, 9, 20)
            );
            
            // Bloque de código para crear el libro 1.
            Libro libro1 = new Libro(
                    "Corazón tan blanco",
                    "978-84-204-2954-3",
                    LocalDate.of(1992, 3, 1),
                    368
            );
            
            // Bloque de código para crear ejemplares.
            Ejemplar ejemplar1 = new Ejemplar("EJ-009-2024", EstadoEjemplar.DISPONIBLE, "Estantería E-10");
            Ejemplar ejemplar2 = new Ejemplar("EJ-010-2024", EstadoEjemplar.DISPONIBLE, "Estantería E-10");
            
            // Bloque de código para añadir los ejemplares creados al libro 1.
            libro1.addEjemplar(ejemplar1);
            libro1.addEjemplar(ejemplar2);

            // Bloque de código para crear el libro 2.
            Libro libro2 = new Libro(
                    "Mañana en la batalla piensa en mí",
                    "978-84-204-2955-0",
                    LocalDate.of(1994, 5, 5),
                    464
            );
            
            // Bloque de código para crear ejemplares.
            Ejemplar ejemplar3 = new Ejemplar("EJ-011-2024", EstadoEjemplar.DISPONIBLE, "Estantería E-11");
            
            // Bloque de código para añadir los ejemplares creados al libro 1.
            libro2.addEjemplar(ejemplar3);
            
            // Bloque de código para relacionar los libros con el autor (manteniendo la bidireccionalidad). Aquí se emplea el método "Helper" para mantener consistencia bidireccional.
            autor.addLibro(libro1);
            autor.addLibro(libro2);
            
            // Bloque de código para guardar el autor (se guardan en cascada los libros y ejemplares).
            session.persist(autor);
            
            System.out.println("\n\n\tGuardando el autor junto a sus libros y respectivos ejemplares en la base de datos...");
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();
            
            System.out.println("\n\n\tTransacción confirmada.");

            // Muestreo del resultado final.
            System.out.println("\n\n\tTanto el Autor {" + autor + "}\n\tcomo sus correspondientes libros {" + libro1 + "\n\t\ty" + libro2 + "}\n\ty respectivos ejemplares {" + ejemplar1 + ", " + ejemplar2 + " y " + ejemplar3 + "} han sido creados correctamente.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback(); System.out.println("\n\n\tError: Transacción revertida.");
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (creación de libros): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
    // Crea el método "agregarEjemplarALibroExistente". Su función será ir añadiendo objetos, ejemplares, correspondientes a un código ISBN (con un estado y una ubicación del mismo) de nuesta base de datos.
        /*
         * Busca un libro por su código ISBN y le añade un nuevo ejemplar. Este nuevo ejemplar se crea con estado "DISPONIBLE" y ubicación "Almacén".
         */
    public void agregarEjemplarALibroExistente(String isbn, String codigoEjemplar){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            System.out.println("\n\n\tBuscando un ejemplar/es por su código ISBN {" + isbn + "}...");
            
            // Bloque de código para buscar un libro por su ISBN (código) usando "HQL" (Hibernate Query Language).
                // ":isbn" Es el valor real que espera del parametro que pasaremos "setParameter" (" "isbn", " →(=) isbn).
                    // Forma 1.
            //Libro libro = session.createQuery("FROM Libro l WHERE l.isbn = :isbn", Libro.class).setParameter("isbn", isbn).uniqueResult();
                    // Forma 2.
            String hql = "FROM Libro l WHERE l.isbn = :isbn";
            Query<Libro> query = session.createQuery(hql, Libro.class);
            query.setParameter("isbn", isbn);
            
            // Obtiene el único resultado, "uniqueResult", posible (único porque "ISBN" es "UNIQUE").
            Libro libro = query.uniqueResult();
            
            // Si no hay libros, es decir, si la tabla "libro" está vacía se muestra un mensaje informativo.
            if (libro == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún libro con el ISBN {'" + isbn + "'}.");
                return;
            }
            
            System.out.println("\n\n\tLibro encontrado: " + libro.getTitulo() + "\n\t\tAutor: " + libro.getAutor().getNombre() + " " + libro.getAutor().getApellidos() + "\n\n\tExistencias actuales (Cantidad de ejemplares): " + libro.getEjemplares().size());
            
            // Bloque de código para crear un nuevo ejemplar.
            Ejemplar nuevoEjemplar = new Ejemplar(codigoEjemplar, EstadoEjemplar.DISPONIBLE, "Almacén");
            
            System.out.println("\n\n\tNuevo ejemplar creado/añadido: " + libro.getTitulo() + " ('" + nuevoEjemplar.getCodigoEjemplar() + "')\n\t\tAutor: " + libro.getAutor().getNombre() + " " + libro.getAutor().getApellidos() + "\n\n\tNuevas existencias actuales (Cantidad de ejemplares): " + libro.getEjemplares().size() + "\n\t\tEstado: " + nuevoEjemplar.getEstado() + "\n\t\tUbicación: " + nuevoEjemplar.getUbicacion());
            
            // Bloque de código para añadir el nuevo ejemplar creado al libro. Aquí se emplea el método "Helper" para mantener consistencia bidireccional.
            libro.addEjemplar(nuevoEjemplar);
           
            // Bloque de código para guardar el autor (se guardan en cascada los libros y ejemplares).
            //session.persist(libro);
                // Con el método "Merge" actualizamos el libro (en este caso la parte de sus ejemplares correspondientes) y, por cascada, persiste el nuevo ejemplar.
            session.merge(libro);
            
            System.out.println("\n\n\tGuardando actualización. Nuevo ejemplar asociado al libro en la base de datos...");
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();
            
            System.out.println("\n\n\tTransacción confirmada.");
            
            // Muestreo del resultado final.
            System.out.println("\n\n\tEl nuevo ejemplar ('" + nuevoEjemplar.getCodigoEjemplar() +"') ha sido añadido/asociado a su respectivo libro {" + libro.getTitulo() + "}.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (agregado de ejemplares): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
    
    // Crea el método "listarTodosLosAutores". Su función será ir recuperando/leyendo todos los objetos, autores, de nuesta base de datos.
        /*
         * Busca y hace una lista de todos los autores registrados en la base de datos.
         */
    public void listarTodosLosAutores(){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando y listando a todos los autores registrados en la DB...");
        
        try{
            // Bloque de código para buscar todos los autores registrados en la DB mediante consulta HQL (Hibernate Query Language).
                    // Forma 1.
            // List<Autor> autores = session.createQuery("FROM Autor", Autor.class).list();
                    // Forma 2.
            String hql = "FROM Autor";
            Query<Autor> query = session.createQuery(hql, Autor.class);
            List<Autor> autores = query.list();
            
            // Si no hay autores registrados se muestra un mensaje informativo.
            if (autores.isEmpty()){
                System.out.println("\n\n\tNo se encontró ningún autor. Por el momento no hay autores registrados en la base de datos.");
                return;
            }
            
            int contador = 1;
            
            System.out.println("\n\n\t<==================== LISTA de AUTORES ====================>\n\n");
            for (Autor autor : autores){
                System.out.println("\n\n\tAutor número " + contador);
                //System.out.println(autor);
                System.out.println("\n\t\tAutor: " + autor.getNombre() + " " + autor.getApellidos() + "\tCódigo de identificación (ID): " + autor.getId() + "\n\t\t\tNacionalidad: " + autor.getNacionalidad() + "\n\t\t\tFecha de Nacimiento: " + autor.getFechaNacimiento() + "\n\t\t\tNº Libros/Novelas escritas: " + autor.getLibros().size() + " en total.");
                System.out.println("----------------------------------------------------------------------");
                
                contador ++;
            }
        } catch (HibernateException hex){
            JOptionPane.showMessageDialog(null, "Error inesperado durante la lectura de autores registrados en la DB: " + hex.getMessage(), "Error de lectura", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
    
    // Crea el método "buscarLibroPorId". Su función será ir recuperando/leyendo el objeto, libro, corresponciente a su ID de nuesta base de datos.
        /*
         * Busca un libro por su número de identificación o ID registrado en la base de datos.
         */
    public void buscarLibroPorId(Long id){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando un libro por su número de identificació (ID) {" + id + "}...");
        
        try{
            // Bloque de código para buscar un libro por su número de identificación (ID).
            Libro libro = session.get(Libro.class, id);
            
            // Si no hay libros, es decir, si la tabla "libro" está vacía se muestra un mensaje informativo.
            if (libro == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún libro con el número de identificación (ID): {'" + id + "'}.");
                return;
            }
            
            System.out.println("\n\n\t<==================== LIBRO ENCONTRADO ====================>\n\n");
            
            System.out.println("\n\tTítulo: " + libro.getTitulo() + " " + "\tNúmero de identificación (ID): " + id + "\n\t\tCódigo ISBN: " + libro.getIsbn() + "\n\t\tAutor/Escritor: " + libro.getAutor().getNombre() + " " + libro.getAutor().getApellidos() + " (" + libro.getAutor().getId()+ ")" +"\n\t\tFecha de Publicación: " + libro.getFechaPublicacion() + "\n\t\tNúmero de Páginas: " + libro.getNumeroPaginas());
            
            System.out.println("\n\n\t<==================== AUTOR DEL LIBRO ====================>\n\n");
            
            System.out.println("\n\tAutor/Escritor: " + libro.getAutor().getNombre() + " " + libro.getAutor().getApellidos() + " (" + libro.getAutor().getId()+ ")" + "\n\t\tNacionalidad: " + libro.getAutor().getNacionalidad() + "\n\t\tFecha de Nacimiento: " + libro.getAutor().getFechaNacimiento() + "\n\t\tNº Libros/Novelas escritas: " + libro.getAutor().getLibros().size() + " en total.");
            
            System.out.println("\n\n\t<==================== EJEMPLARES DEL AUTOR (Lista) ====================>\n\n");
            
            List<Ejemplar> ejemplares = libro.getEjemplares();
            System.out.println("\n\tNº Libros/Novelas escritas: " + ejemplares.size() + " en total.");
            
            // Si no hay ejemplares registrados se muestra un mensaje informativo.
            if (ejemplares.isEmpty()){
                System.out.println("\n\n\tNo se encontró ningún ejemplar. Por el momento no hay ejemplares registrados para dicho libro en la base de datos.");
                return;
            }
            
            int contador = 1;
            
            //for (Ejemplar ejemplar : libro.getEjemplares()){
            for (Ejemplar ejemplar : libro.getEjemplares()){
                System.out.println("\n\n\tEjemplar número " + contador);
                //System.out.println(autor);
                System.out.println("\n\t\tCódigo: " + ejemplar.getCodigoEjemplar() + " " + "\tNúmero de identificación (ID): " + ejemplar.getId() + "\n\t\t\tEstado: " + ejemplar.getEstado() + "\n\t\t\tUbicación: " + ejemplar.getUbicacion());
                System.out.println("----------------------------------------------------------------------");
                
                contador ++;
            }
        } catch (HibernateException hex){
            JOptionPane.showMessageDialog(null, "Error inesperado durante la búsqueda del libro con número de identificación o ID ('" + id + ")': " + hex.getMessage(), "Error de búsqueda", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
    
    // Crea el método "buscarEjemplaresPorEstado". Su función será ir recuperando/leyendo todos los objetos, ejemplares, corresponciente a su libro (partiendo como filtro de búsqueda el estado de estos) de nuesta base de datos.
        /*
         * Busca todos los ejemplares filtrados por su estado.
         */
    public void buscarEjemplaresPorEstado(EstadoEjemplar estado){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando un ejemplar/es por su estado {" + estado + "}...");
        
        try{
            // Bloque de código para buscar un ejemplar/es por su estado usando "HQL" (Hibernate Query Language).
                // ":estado" Es el valor real que espera del parametro que pasaremos "setParameter" (" "estado", " →(=) estado).
            String hql = "FROM Ejemplar e WHERE e.estado = :estado";
            Query<Ejemplar> query = session.createQuery(hql, Ejemplar.class);
            query.setParameter("estado", estado);
            List<Ejemplar> ejemplares = query.list();
            
            // Si no hay ejemplares registrados se muestra un mensaje informativo.
            if (ejemplares.isEmpty()){
                System.out.println("\n\n\tNo se encontró ningún ejemplar. Por el momento no hay ejemplares registrados con dicho estado en la base de datos.");
                return;
            }
            
            System.out.println("\n\n\tTotal de ejemplares encontrados: " + ejemplares.size());
            
            int contador = 1;
            
            //for (Ejemplar ejemplar : libro.getEjemplares()){
            for (Ejemplar ejemplar : ejemplares){
                System.out.println("\n\n\tEjemplar número " + contador);
                //System.out.println(autor);
                System.out.println("\n\t\tCódigo: " + ejemplar.getCodigoEjemplar() + " " + "\tNúmero de identificación (ID): " + ejemplar.getId() + "\n\t\t\tEstado: " + ejemplar.getEstado() + "\n\t\t\tUbicación: " + ejemplar.getUbicacion() + "\n\n\t\t\tLibro (al que corresponde): " + ejemplar.getLibro().getTitulo() + "\tNúmero de identificación (ID): " + ejemplar.getLibro().getId() + "\n\t\t\t\tCódigo ISBN: " + ejemplar.getLibro().getIsbn() + "\n\t\t\t\tFecha de Publicación: " + ejemplar.getLibro().getFechaPublicacion() + "\n\t\t\t\tNúmero de Páginas: " + ejemplar.getLibro().getNumeroPaginas() + "\n\n\t\t\tAutor (al que pertenece): " + ejemplar.getLibro().getAutor().getNombre() + " " + ejemplar.getLibro().getAutor().getApellidos() + "\tNúmero de identificación (ID): " + ejemplar.getLibro().getAutor().getId() + "\n\t\t\t\tNacionalidad: " + ejemplar.getLibro().getAutor().getNacionalidad() + "\n\t\t\t\tFecha de Nacimiento: " + ejemplar.getLibro().getAutor().getFechaNacimiento() + "\n\t\t\t\tNº Libros/Novelas escritas: " + ejemplar.getLibro().getAutor().getLibros().size() + " en total.");
                System.out.println("----------------------------------------------------------------------");
                
                contador ++;
            }
        } catch (HibernateException hex){
            JOptionPane.showMessageDialog(null, "Error inesperado durante la búsqueda del ejemplar por su estado actla ('"+ estado +")': " + hex.getMessage(), "Error de búsqueda", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
    
    // Crea el método "obtenerEstadisticasBiblioteca." Su función será ir recuperando/leyendo todos los objetos de nuestra base de datos para crear y mostrar unas estadísticas generalizadas/globales a los datos obtenidos.
        /*
         * Busca y trata todos los objetos de nuestra base de datos para crear y mostrar estadísticas en base a ellos.
         */
    public void obtenerEstadisticasBiblioteca(){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tAnalizando datos y generando estadísticas de la biblioteca...");
        
        try{
            String hql;
            System.out.println("\n\n\t<==================== ESTADÍSTICAS DE LA BIBLIOTECA ====================>\n\n");
            
            // Bloque de código para contabilizar el número total de registros de autores en la DB.
                /*
                 * 1. Crea una consulta HQL que selecciona la cantidad total de autores registrados en la entidad "Autor".
                 * 2. Con "COUNT(a)" cuenta cuántos objetos (autores) existen en la base de datos.
                 * 3. Con "Long.class" indica que el resultado devuelto por la consulta será de tipo numérico largo (Long).
                 * 4. Con "uniqueResult()" obtiene un único valor (no una lista) con el total de autores.
                 * 5. Guarda ese número total en la variable "totalAutores".
                 */
            String autores = "SELECT COUNT(a) FROM Autor a";
            hql = autores;
            Long totalAutores = session.createQuery(hql, Long.class).uniqueResult();
                        
            // Bloque de código para contabilizar el número total de registros de libros en la DB.
                /*
                 * 1. Crea una consulta HQL que selecciona la cantidad total de libros registrados en la entidad "Libro".
                 * 2. Con "COUNT(a)" cuenta cuántos objetos (libros) existen en la base de datos.
                 * 3. Con "Long.class" indica que el resultado devuelto por la consulta será de tipo numérico largo (Long).
                 * 4. Con "uniqueResult()" obtiene un único valor (no una lista) con el total de libros.
                 * 5. Guarda ese número total en la variable "totalLibros".
                 */
            String libros = "SELECT COUNT(l) FROM Libro l";
            hql = libros;
            Long totalLibros = session.createQuery(hql, Long.class).uniqueResult();
            
            // Bloque de código para contabilizar el número total de registros de ejemplares en la DB.
                /*
                 * 1. Crea una consulta HQL que selecciona la cantidad total de ejemplares registrados en la entidad "Ejemplar".
                 * 2. Con "COUNT(a)" cuenta cuántos objetos (ejemplares) existen en la base de datos.
                 * 3. Con "Long.class" indica que el resultado devuelto por la consulta será de tipo numérico largo (Long).
                 * 4. Con "uniqueResult()" obtiene un único valor (no una lista) con el total de ejemplares.
                 * 5. Guarda ese número total en la variable "totalEjemplares".
                 */
            String ejemplares = "SELECT COUNT(e) FROM Ejemplar e";
            hql = ejemplares;
            Long totalEjemplares = session.createQuery(hql, Long.class).uniqueResult();
            
            System.out.println("\n\t\t<========== CONTEOS TOTALES ==========>\n\n");
            
            System.out.println("\n\t\t\tNº Total de autores registrados: " + totalAutores + "\n\t\t\tNº Total de libros registrados: " + totalLibros + "\n\t\t\tNº Total de ejemplares registrados: " + totalEjemplares + "\n");
            
            // Bloque de código para contabilizar el número total de registros de ejemplares por estado de disponibilidad en la DB.
            for (EstadoEjemplar estado : EstadoEjemplar.values()){ /* El método ".values()" es un método propio de los "enum" en Java. Devuelve un array con todos los valores que tiene ese "enum". En este caso el array será tal: "[DISPONIBLE, PRESTADO, DETERIORADO, REPARACION]. "*/
                    // ":estado" Es el valor real que espera del parametro que pasaremos "setParameter" (" "estado", " →(=) estado).
                Long cantidad = session.createQuery("SELECT COUNT(e) FROM Ejemplar e WHERE e.estado = :estado", Long.class).setParameter("estado", estado).uniqueResult();
                
                System.out.println("\t- " + estado + ": " + cantidad);
            }
        } catch (HibernateException hex){
            JOptionPane.showMessageDialog(null, "Error inesperado durante las operaciones de cálculo para obtener las estadísticas: " + hex.getMessage(), "Error de operación", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
    
    // Crea el método "actualizarEstadoEjemplar." Su función será ir recuperando el/los ejemplar/es e ir actualizando su/s estado/s.
        /*
         * Busca los ejemplares por su número de identificación (ID) y actualiza su estado.
         */
    public void actualizarEstadoEjemplar(Long id, EstadoEjemplar nuevoEstado){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando un ejemplar por su número de identificación o ID {" + id + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar al ejemplar por su ID.
            Ejemplar ejemplar = session.get(Ejemplar.class, id);
            
            // Si no hay ejemplares, es decir, si la tabla "ejemplar" está vacía se muestra un mensaje informativo.
            if (ejemplar == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún ejemplar con el número de identificación (ID): {'" + id + "'}.");
                return;
            }
            
            System.out.println("\n\n\tEjemplar {" + id + "} encontrado exitosamente.\n\t\tLibro: " + ejemplar.getLibro().getTitulo() + " | " + ejemplar.getLibro().getAutor() + "\n\t\t\tCódigo: " + ejemplar.getCodigoEjemplar() + "\n\t\t\tEstado actual: " + ejemplar.getEstado() + "\n\t\t\tUbicación: " + ejemplar.getUbicacion());
            
            // Bloque de código para actualizar el estado.
            ejemplar.setEstado(nuevoEstado);
            
            // Si el estado del ejemplar encontrado/buscado pasa a "PRESTADO", cambia/actualiza su ubicación.
            if (nuevoEstado == EstadoEjemplar.PRESTADO){
                ejemplar.setUbicacion("En préstamo");
            }
            
            // Bloque de código para guardar los cambios/actualizaciones recientes hechos sobre los campos/atributos del ejemplar.
            session.update(ejemplar);
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tEstado del ejemplar actualizado correctamente.");
            
            System.out.println("\n\n\tEjemplar {" + id + "} encontrado exitosamente.\n\t\tLibro: " + ejemplar.getLibro().getTitulo() + " | " + ejemplar.getLibro().getAutor() + "\n\t\t\tCódigo: " + ejemplar.getCodigoEjemplar() + "\n\t\t\t(Nuevo) Estado actual: " + ejemplar.getEstado() + "\n\t\t\t(Nueva) Ubicación: " + ejemplar.getUbicacion());
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la actualización de la transacción (cambio/actualización del estado del prestamo): " + hex.getMessage(), "Error de actualización", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
    
    // Crea el método "actualizarDatosLibro." Su función será ir recuperando el/los libro/s e ir actualizando sus datos.
        /*
         * Busca los libros por su número de identificación (ID) y actualiza sus datos.
         */
    public void actualizarDatosLibro(Long id, String nuevoTitulo, Integer nuevasPaginas){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando un libro por su número de identificación o ID {" + id + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar al libro por su ID.
            Libro libro = session.get(Libro.class, id);
            
            // Si no hay libros, es decir, si la tabla "libro" está vacía se muestra un mensaje informativo.
            if (libro == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún libro con el número de identificación (ID): {'" + id + "'}.");
                return;
            }
            
            System.out.println("\n\n\tLibro {" + id + "} encontrado exitosamente.\n\t\tCódigo ISBN: " + libro.getIsbn() + "\n\t\t\tTítulo actual: " + libro.getTitulo() + " | " + "Autor: " + libro.getAutor() + "\n\t\t\tFecha de Publicación: " + libro.getFechaPublicacion() + "\n\t\t\tNúmero de Páginas actuales: " + libro.getNumeroPaginas() + "\n\t\t\t\tEjemplares: " + libro.getEjemplares());
            
            // Bloque de código para actualizar los campos/atributos del libro.
            libro.setTitulo(nuevoTitulo);
            libro.setNumeroPaginas(nuevasPaginas);
            
            // Bloque de código para guardar los cambios/actualizaciones recientes hechos sobre los campos/atributos del libro.
            session.update(libro);
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tCampos/Atributos (título y número de páginas) actualizado correctamente.");
            
            System.out.println("\n\n\tLibro {" + id + "} encontrado exitosamente.\n\t\tCódigo ISBN: " + libro.getIsbn() + "\n\t\t\t(Nuevo) Título actual: " + libro.getTitulo() + " | " + "Autor: " + libro.getAutor() + "\n\t\t\tFecha de Publicación: " + libro.getFechaPublicacion() + "\n\t\t\t(Nuevo) Número de Páginas actuales: " + libro.getNumeroPaginas() + "\n\t\t\t\tEjemplares: " + libro.getEjemplares());
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la actualización de la transacción (cambio/actualización de los campos/atributos título y número de páginas del libro): " + hex.getMessage(), "Error de actualización", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
    
    // Crea el método "transferirLibrosEntreAutores." Su función será transferir todos los datos de un autor a otro.
        /*
         * Busca los autores (origen y destino) por su número de identificación (ID) y transfiere sus datos.
         */
    public void transferirLibrosEntreAutores(Long idAutorOrigen, Long idAutorDestino){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando autor (de origen) por su número de identificación o ID {" + idAutorOrigen + "}...");
        System.out.println("\n\n\tBuscando autor (de destino) por su número de identificación o ID {" + idAutorDestino + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar a ambos autores por su ID.
            Autor autorOrigen = session.get(Autor.class, idAutorOrigen);
            Autor autorDestino = session.get(Autor.class, idAutorDestino);
            
            // Si no hay ninguno de los autores, es decir, si la tabla "autor" está vacía se muestra un mensaje informativo.
            if (autorOrigen == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún autor (de origen) con el número de identificación (ID): {'" + idAutorOrigen + "'}.");
                return;
            }
            if (autorDestino == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún autor (de destio) con el número de identificación (ID): {'" + idAutorDestino + "'}.");
                return;
            }
            
            System.out.println("\n\n\tAutor (de origen) {" + idAutorOrigen + "} encontrado exitosamente.\n\t\tNombre: " + autorOrigen.getNombre() + " " + autorOrigen.getApellidos() + "\n\t\t\tFecha de Nacimiento: " + autorOrigen.getFechaNacimiento() + "\n\t\t\tNacionalidad " + autorOrigen.getNacionalidad() + "\n\t\t\tLibros: " + autorOrigen.getLibros());
            System.out.println("\n\n\tAutor (de destino) {" + idAutorDestino + "} encontrado exitosamente.\n\t\tNombre: " + autorDestino.getNombre() + " " + autorDestino.getApellidos() + "\n\t\t\tFecha de Nacimiento: " + autorDestino.getFechaNacimiento() + "\n\t\t\tNacionalidad " + autorDestino.getNacionalidad() + "\n\t\t\tLibros: " + autorDestino.getLibros());
            
            // Bloque de código para obtener una lista con los libros del autor de origen.
            List<Libro> librosAtraspasar = new ArrayList<>(autorOrigen.getLibros());
            
            // Si no hay libros, es decir, si la tabla "libro" está vacía se muestra un mensaje informativo.
            if (librosAtraspasar.isEmpty()){
            System.out.println("\n\n\tEl autor (de origen) no tiene libros para transferir");
                return;
            }
            
            System.out.println("\n\n\tEl autor (de origen) si tiene libros para transferir.\n\t\tLibros que serán transferidos: " + librosAtraspasar.size());
            
            // Bloque de código para transferir libro a libro del autor de origen al autor dedestino directamente, sin usar los métodos helper ya que en este caso concreto causarían conflicto con la cascada de Hibernate.
            for (Libro libro : librosAtraspasar){                
                libro.setAutor(autorDestino);        /* Cambia el autor del libro directamente. */
                
                // Con el método "Merge" actualizamos el/los libro/s (en este caso la parte de sus ejemplares correspondientes) y, por cascada, persiste el nuevo ejemplar.
                session.merge(libro);
            }
            
            //autorOrigen.getLibros().remove(libro);    /* Primero obtiene el/los libro/s y después eliminala relación del lado origen, es decir, remueve de la lista al autor de origen. */
            autorOrigen.getLibros().clear();            /* Limpia y actualiza las colecciones. */
            
            // Fuerza la sincronización con la BD.
            session.flush();
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tTransferencia de libros completada correcta y exitosamente.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (transferencia de datos, libros de autor origen a autor destino): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
    
    // Crea el método "eliminarEjemplar." Su función será recuperar el libro para eliminar su ejemplar.
        /*
         * Busca el libro a partir del código de identificación (ID) del ejemplar para después borrar ese mismo ejemplar mediante uno de los métodos "helper" de la clase "Libro.java".
         */
    public void eliminarEjemplar(Long id){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando ejemplar por su número de identificación o ID {" + id + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar ejemplares por su ID.
            Ejemplar ejemplar = session.get(Ejemplar.class, id);
            
            // Si no hay ejemplares, es decir, si la tabla "ejemplar" está vacía se muestra un mensaje informativo.
            if (ejemplar == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún ejemplar con el número de identificación (ID): {'" + id + "'}.");
                return;
            }
            
            // Bloque de código para obtener el libro a partir del ejemplar.
            Libro libro = ejemplar.getLibro();
            
            // Si no hay ejemplares, es decir, si la tabla "ejemplar" está vacía se muestra un mensaje informativo.
            if (libro == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún libro asociado a dicho ejemplar {'" + id + "'}.");
                return;
            }
            
            System.out.println("\n\n\tEjemplar {" + id + "} encontrado exitosamente.\n\t\tCódigo: " + ejemplar.getCodigoEjemplar() + "\n\t\t\tEstado: " + ejemplar.getEstado()+ "\n\t\t\tUbicación: " + ejemplar.getUbicacion() + "\n\t\t\tLibros: " + libro.getTitulo() + " {" + libro.getId() + " | " + libro.getIsbn()+ "}\tAutor: " + libro.getAutor());
            
            // Bloque de código para mantener consistencia bidireccional entre "Libro" y "Ejemplar".
            libro.removeEjemplar(ejemplar); /* Llamamos al método "helper" → "removeEjemplar" para conseguir eliminar el ejemplar automáticamente. */
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tEjemplar eliminado/borrado de la lista de ejemplares del libro ('" + libro.getTitulo() + "') correctamente.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (eliminación del ejemplar): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
    
    // Crea el método "eliminarLibro." Su función será recuperar el autor para eliminar su libro.
        /*
         * Busca el autor a partir del código de identificación (ID) del libro para después borrar ese mismo libro mediante uno de los métodos "helper" de la clase "Autor.java".
         */
    public void eliminarLibro(Long id){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando libro por su número de identificación o ID {" + id + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar el libro por su ID.
            Libro libro = session.get(Libro.class, id);
            
            // Si no hay libros, es decir, si la tabla "libro" está vacía se muestra un mensaje informativo.
            if (libro == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún libro con el número de identificación (ID): {'" + id + "'}.");
                return;
            }
                        
            // Bloque de código para obtener el autor.
            Autor autor = libro.getAutor();
            
            // Si no hay libros, es decir, si la tabla "libro" está vacía se muestra un mensaje informativo.
            if (autor == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún autor asociado a dicho libro {'" + id + "'}.");
                return;
            }

            System.out.println("\n\n\tLibro {" + id + "} encontrado exitosamente.\n\t\tCódigo ISBN: " + libro.getIsbn()+ "\n\t\t\tTítulo: " + libro.getTitulo()+ "\n\t\t\tAutor: " + autor.getNombre() + " " + autor.getApellidos() + "\n\t\t\tFecha de Publicación: " + libro.getFechaPublicacion() + "\n\t\t\tNúmero de páginas: " + libro.getNumeroPaginas() + "\n\t\t\tEjemplares: " + libro.getEjemplares());
            
            // Bloque de código (opcional) para contabilizar y así saber cúantos ejemplares serán eliminados al eliminar el libro.
            int ejemplaresEliminados = libro.getEjemplares().size();
            
            // Bloque de código para mantener consistencia bidireccional entre "Autor" y "Libro".
            autor.removeLibro(libro); /* Llamamos al método "helper" → "removeLibro" para conseguir eliminar el ejemplar automáticamente. */
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tLibro eliminado/borrado de la lista de libros del autor ('" + autor.getNombre() + " " + autor.getApellidos() + "') correctamente.\n\t\tAl eliminar el libro, esta acción también hizo que se borrasen (borrado en cascada) los ejemplares correspondientes a dicho libro, es decirm un total de: " + ejemplaresEliminados);
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (eliminación del ejemplar): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
    
    // Crea el método "eliminarAutorConCascada." Su función será recuperar y eliminar el autor en cascada.
        /*
         * Busca el autor a partir del código de identificación (ID) para después eliminarlo/borrarlo en casacada, lo que supone también eliminar todos sus libros y los ejemplares correspondientes de la DB ese mismo libro mediante uno de los métodos "helper" de la clase "Autor.java".
         */
    public void eliminarAutorConCascada(Long id){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando libro por su número de identificación o ID {" + id + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar el autor.
            Autor autor = session.get(Autor.class, id);
            
            // Si no hay autores, es decir, si la tabla "autor" está vacía se muestra un mensaje informativo.
            if (autor == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún autor con el número de identificación (ID): {'" + id + "'}.");
                return;
            }
            
            System.out.println("\n\n\tAutor {" + id + "} encontrado exitosamente.\n\t\tNombre: " + autor.getNombre()+ "\n\t\tApellidos: " + autor.getApellidos()+ "\n\t\tNacionalidad: " + autor.getNacionalidad() + "\n\t\tFecha de Nacimiento: " + autor.getFechaNacimiento()+ "\n\t\tLibros: " + autor.getLibros());
            
            // Bloque de código (opcional) para contabilizar y así saber cúantos libros serán eliminados al eliminar el autor.
            int librosEliminados = autor.getLibros().size();
            
            // Bloque de código (opcional) para contabilizar y así saber cúantos ejemplares serán eliminados al eliminar el libro.
            int ejemplaresEliminados = 0;
            for (Libro l : autor.getLibros()){
                ejemplaresEliminados += l.getEjemplares().size();
            }
            
            // Bloque de código para mantener el borrado en cascada entre "Ejemplar", "Libro" y "Autor".
            session.remove(autor);
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tAutor eliminado/borrado de la lista de autores ('" + autor.getNombre() + " " + autor.getApellidos() + "') correctamente.\n\t\tAl eliminar el autor, esta acción también hizo que se borrasen (borrado en cascada) los libros y ejemplares correspondientes a dicho autor, es decirm un total de: " + librosEliminados + " y un total de: " + ejemplaresEliminados);
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (eliminación del autor): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
        } finally{
            session.close();
        }
    }
}