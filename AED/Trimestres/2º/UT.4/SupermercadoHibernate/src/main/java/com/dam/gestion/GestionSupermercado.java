/**
 *
 * @author Rodrigo
 */
package com.dam.gestion;


// Importa de la biblioteca/librería el paquete "HibernateUtil".
import com.dam.util.HibernateUtil;
// Importa todos los paquetes de la biblioteca/librería "modelo".
import com.dam.modelo.*;
// Importa todos los paquetes de la biblioteca/librería "Lote".
import com.dam.modelo.Lote.*;
// Importa de la biblioteca/librería el paquete "Session".
import org.hibernate.Session;
// Importa de la biblioteca/librería el paquete "Transaction".
import org.hibernate.Transaction;
// Importa de la biblioteca/librería el paquete "LocalDate".
import java.time.LocalDate;
// Importa de la biblioteca/librería el paquete "ArrayList".
import java.util.ArrayList;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;
// Importa de la biblioteca/librería el paquete "JOptionPane".
import javax.swing.JOptionPane;
// Importa de la biblioteca/librería el paquete "HibernateException".
import org.hibernate.HibernateException;
// Importa de la biblioteca/librería el paquete "Query".
import org.hibernate.query.Query;

// Crea la clase "GestionSupermercado", ésta contendrá la parte funcional del programa, es decir, todos los métodos del programa.
public class GestionSupermercado{
    // Crea e inicializa el objeto para la sesión.
    Session session = null;
    
    // Crea e inicializa el objeto para las operaciones de transacciones.
    Transaction transaction = null;
    
    // ==================== OPERACIONES CREATE ====================
        // Crea el método "crearProveedorConProductos". Su función será ir añadiendo objetos, proveedores, (asociándole también dos nuevos productos) a la tabla "proveedores" de nuesta base de datos.
            // Crea un nuevo proveedor con dos productos asociados, y cada producto con sus lotes. Gracias a las operaciones en cascada "CascadeType.ALL" al guardar el proveedor, se guardan automáticamente sus productos y lotes. Esto demuestra que el mapeo y la cascada funcionan correctamente.
    public void crearProveedorConProductos(){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            System.out.println("\n\n\tCreando el proveedor junto a sus productos y respectivos lotes...");
            
            // Bloque de código para crear el proveedor.
            Proveedor proveedor = new Proveedor(
                    "Distribuciones García S.L.",
                    "B-12345678",
                    "918765432",
                    "contacto@distgarcia.es"
            );
            
            // Bloque de código para crear el producto 1.
            Producto producto1 = new Producto(
                    "Aceite de oliva virgen extra",
                    "8412345678901",
                    8.95,
                    "Alimentación"
            );
            
            // Bloque de código para crear los lotes del producto 1.
            Lote lote1 = new Lote("LOT-2024-001", 50, LocalDate.of(2025, 12, 31), EstadoLote.DISPONIBLE);
            Lote lote2 = new Lote("LOT-2024-002", 30, LocalDate.of(2025, 12, 31), EstadoLote.DISPONIBLE);
            
            // Bloque de código para añadir los lotes creados al producto 1.
            producto1.addLote(lote1);
            producto1.addLote(lote2);

            // Bloque de código para crear el producto 2.
            Producto producto2 = new Producto(
                    "Arroz integral 1kg",
                    "8412345678902",
                    2.45,
                    "Alimentación"
            );
            
            // Bloque de código para crear el lote del producto 2.
            Lote lote3 = new Lote("LOT-2024-003", 100, LocalDate.of(2026, 6, 30), EstadoLote.DISPONIBLE);
            
            // Bloque de código para añadir los lotes creados al producto 1.
            producto2.addLote(lote3);
            
            // Bloque de código para relacionar los productos con el proveedor (manteniendo la bidireccionalidad). Aquí se emplea el método "Helper" para mantener consistencia bidireccional.
            proveedor.addProducto(producto1);
            proveedor.addProducto(producto2);
            
            // Bloque de código para guardar el proveedor (al mismo tiempo se guardan en cascada los productos y lotes).
            session.persist(proveedor);
            
            System.out.println("\n\n\tGuardando el proveedor junto a sus productos y respectivos lotes en la base de datos...");
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();
            
            System.out.println("\n\n\tTransacción confirmada.");

            // Muestreo del resultado final.
            System.out.println("\n\n\tTanto el proveedor {" + proveedor + "}\n\t\tcomo sus correspondientes productos {" + producto1 + " y " + producto2 + "}\n\t\t\ty respectivos lotes {" + lote1 + ", " + lote2 + " y " + lote3 + "} han sido creados correctamente.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback(); System.out.println("\n\n\tError: Transacción revertida.");
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (creación de proveedores con productos y lotes): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
        // Crea el método "agregarLoteAProductoExistente". Su función será ir añadiendo objetos, lotes, correspondientes a un código de barras (con cierta cantidad, un estado y una fecha de caducidad) de nuesta base de datos.
            //Busca un producto por su código de barras y le añade un nuevo lote. Este nuevo lote se crea con una cierta cantidad del mismo, del producto, "25"; un estado "DISPONIBLE" y una fecha de caducidad (un año desde la fecha actual ) "2026/12/06".
    public void agregarLoteAProductoExistente(String codigoBarras, String codigoLote){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            System.out.println("\n\n\tBuscando un producto/s por su código de barras {" + codigoBarras + "}...");
            
            // Bloque de código para buscar un producto por su código de barras (codigo_barras) usando "HQL" (Hibernate Query Language).
                // ":codigoBarras" Es el valor real que espera del parametro que pasaremos "setParameter" (" "codigo", " →(=) codigoBarras).
                    // Forma 1.
            //Producto producto = session.createQuery("FROM Producto l WHERE l.isbn = :isbn", Producto.class).setParameter("isbn", isbn).uniqueResult();
                    // Forma 2.
            String hql = "FROM Producto p WHERE p.codigoBarras= :codigo";
            Query<Producto> query = session.createQuery(hql, Producto.class);
            query.setParameter("codigo", codigoBarras);
            
            // Obtiene el único resultado, "uniqueResult", posible (único porque "codigo_barras" es "UNIQUE").
            Producto producto=  query.uniqueResult();
            
            // Si no hay productos, es decir, si la tabla "productos" está vacía se muestra un mensaje informativo.
            if (producto == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún productos con el siguienet código de barras {'" + codigoBarras + "'}.");
                return;
            }
            
            System.out.println("\n\n\tProducto encontrado: " + producto.getNombre() + "\tNúmero de identificación (ID): " + producto.getId() + "\tCódigo de barras: " + producto.getCodigoBarras() + "\n\t\tCategoría: " + producto.getCategoria() + "\n\t\tExistencias actuales (Cantidad de lotes): " + "\n\t\tPrecio: " + producto.getPrecio() + "\n\n\tProveedor: " + producto.getProveedor().getNombre() + "\tNúmero de identificación (ID): " + producto.getProveedor().getId() + "\tCódigo de identificación fiscal (CIF)" + producto.getProveedor().getCif() + "\n\t\tContacto:\n\t\t\tCorreo electrónico (email):" + producto.getProveedor().getEmail() + "\n\t\t\tTeléfono: " + producto.getProveedor().getTelefono());
            
            // Bloque de código para crear un nuevo lote con fecha de caducidad a 1 año del actual.
            LocalDate fechaCaducidadActual = LocalDate.now().plusYears(1);
            Lote nuevoLote = new Lote(codigoLote, 25, fechaCaducidadActual, EstadoLote.DISPONIBLE);
            
            // Bloque de código para añadir el nuevo lote creado al producto. Aquí se emplea el método "Helper" para mantener consistencia bidireccional.
            producto.addLote(nuevoLote);
           
            // Bloque de código para guardar el proveedor (al mismo tiempo se guardan en cascada los productos y lotes).
            //session.persist(producto);
                // Con el método "Merge" actualizamos el producto (en este caso la parte de sus lotes correspondientes) y, por cascada, persiste el nuevo lote.
            session.merge(producto);
            
            System.out.println("\n\n\tGuardando actualización. Nuevo lote asociado al producto en la base de datos...");
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();
            
            System.out.println("\n\n\tTransacción confirmada.");
            
            // Muestreo del resultado final.
            System.out.println("\n\n\tEl nuevo lote ('" + nuevoLote.getId() + "\t[" + nuevoLote.getCodigo() + "]') ha sido añadido/asociado a su respectivo producto {" + producto.getNombre() + "}. Perteneciente al proveedor {" + producto.getProveedor().getNombre() + "}");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (creación de lotes a productos existentes): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
    // ==================== OPERACIONES READ ====================
        // Crea el método "listarTodosLosProveedores". Su función será ir recuperando, leyendo y mostrando todos los objetos, proveedores, de la tabla "proveedores" de nuesta base de datos.
            // Busca y hace una lista de todos los proveedores registrados en la tabla "proveedores" de nuesta base de datos.
    public void listarTodosLosProveedores(){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando y listando a todos los proveedpres registrados en la tabla \"proveedores\" de nuesta DB...");
        
        try{
            // Bloque de código para buscar todos los proveedores registrados en la DB mediante consulta HQL (Hibernate Query Language).
                    // Forma 1.
            // List<Proveedor> proveedores = session.createQuery("FROM Proveedor", Proveedor.class).list();
                    // Forma 2.
            String hql = "FROM Proveedor";
            Query<Proveedor> query = session.createQuery(hql, Proveedor.class);
            List<Proveedor> proveedores = query.list();
            
            // Si no hay proveedores registrados se muestra un mensaje informativo.
            if (proveedores.isEmpty()){
                System.out.println("\n\n\tNo hay o no se ha encontrado ningún proveedor. Por el momento no hay proveedores registrados en la base de datos.");
                return;
            }
            
            int contador = 1;
            
            System.out.println("\n\n\t<==================== LISTA de PROVEEDORES ====================>");
            
            for (Proveedor proveedor : proveedores){
                System.out.println("\n\tNúmero de proveedores: " + contador);
                //System.out.println(proveedor);
                System.out.println("\n\tProveedor: " + proveedor.getNombre() + "\tCódigo de identificación fiscal (CIF): " + proveedor.getCif() + "\n\t\tContacto:\n\t\t\tCorreo electrónico (email):" + proveedor.getEmail() + "\n\t\t\tTeléfono: " + proveedor.getTelefono() + "\n\t\tNúmero de productos provistos: " + proveedor.getProductos().size() + " en total.");
                //System.out.println("----------------------------------------------------------------------");
                System.out.println("-".repeat(70));
                
                contador ++;
            }
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            JOptionPane.showMessageDialog(null, "Error inesperado durante la lectura y listado de proveedores registrados en la DB: " + hex.getMessage(), "Error de lectura", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
        // Crea el método "buscarProductoPorId". Su función será ir recuperando/leyendo el objeto, producto, corresponciente a su número de identificación (ID) de la tabla "productos" de nuesta base de datos.
            // Busca un producto por su número de identificación o ID registrado en la base de datos.
    public void buscarProductoPorId(Long idProducto){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando un producto por su número de identificació (ID) {" + idProducto + "}...");
        
        try{
            // Bloque de código para buscar un producto por su número de identificación (ID).
            Producto producto = session.get(Producto.class, idProducto);
            
            // Si no hay productos, es decir, si la tabla "productos" está vacía se muestra un mensaje informativo.
            if (producto == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún producto con el número de identificación (ID): {'" + idProducto + "'}.");
                return;
            }
            
            System.out.println("\n\n\t<==================== PRODUCTO ENCONTRADO ====================>");
            
            System.out.println("\n\tNombre: " + producto.getNombre() + " " + "\tNúmero de identificación (ID): " + idProducto + "\tCódigo de barras: " + producto.getCodigoBarras() + "\n\t\tCategoría: " + producto.getCategoria() + "\n\t\tPrecio: " + producto.getPrecio() + "\n\t\tNúmero de lotes asociados: " + producto.getLotes().size() + "\n\t\t\tProveedor: " + producto.getProveedor().getNombre());
            
            System.out.println("\n\n\t<==================== PROVEEDOR DEL PRODUCTO ====================>");
            
            System.out.println("\n\tNombre: " + producto.getProveedor().getNombre() + "\tNúmero de identificación (ID): " + producto.getProveedor().getId() + "\tNúmero de identificación fiscal: " + producto.getProveedor().getCif() + "\n\t\tContacto:\n\t\t\tCorreo electrónico (email):" + producto.getProveedor().getEmail() + "\n\t\t\tTeléfono: " + producto.getProveedor().getTelefono() + "\n\t\tNúmero de productos provistos: " + producto.getProveedor().getProductos().size() + " en total.");
            
            System.out.println("\n\n\t<==================== LOTES DEL PROVEEDOR ====================>");
            
            List<Lote> lotes = producto.getLotes();
            System.out.println("\n\tNúmero de productos provistos: " + lotes.size() + " en total.");
            
            // Si no hay lotes registrados se muestra un mensaje informativo.
            if (lotes.isEmpty()){
                System.out.println("\n\n\tNo hay o no se ha encontrado ningún lote. Por el momento no hay lotes registrados para dicho producto en la base de datos.");
                return;
            }
            
            int contador = 1;
            
            //for (Lote lote : producto.getLotes()){
            for (Lote lote : lotes){
                System.out.println("\n\n\tLote número " + contador);
                //System.out.println(lote);
                System.out.println("\n\t\tCódigo: " + lote.getCodigo() + "\tNúmero de identificación (ID): " + lote.getId() + "\n\t\t\tCantidad: " + lote.getCantidad() + "\n\t\t\tFecha de caducidad: " + lote.getFechaCaducidad() + "\n\t\t\tEstado: " + lote.getEstado() + "\n\t\tProducto al que pertenece: " + lote.getProducto().getNombre());
                //System.out.println("----------------------------------------------------------------------");
                System.out.println("-".repeat(70));
                
                contador ++;
            }
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            JOptionPane.showMessageDialog(null, "Error inesperado durante la búsqueda del producto con número de identificación o ID ('" + idProducto + ")': " + hex.getMessage(), "Error de búsqueda", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
        // Crea el método "buscarLotesPorEstado". Su función será ir recuperando/leyendo todos los objetos, lotes, corresponciente a su producto (partiendo como filtro de búsqueda el estado de estos) de nuesta base de datos.
            // Busca todos los lotes filtrados por su estado.
    public void buscarLotesPorEstado(EstadoLote estado){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando un lote/s por su estado {" + estado + "}...");
        
        try{
            // Bloque de código para buscar un lote/s por su estado usando "HQL" (Hibernate Query Language).
                // ":estado" Es el valor real que espera del parametro que pasaremos "setParameter" (" "estado", " →(=) estado).
            String hql = "FROM Lote l WHERE l.estado = :estado";
            Query<Lote> query = session.createQuery(hql, Lote.class);
            query.setParameter("estado", estado);
            List<Lote> lotes = query.list();
            
            // Si no hay lotes registrados se muestra un mensaje informativo.
            if (lotes.isEmpty()){
                System.out.println("\n\n\tNo hay o no se ha encontrado ningún lote. Por el momento no hay lotes registrados con dicho estado en la base de datos.");
                return;
            }
            
            System.out.println("\n\n\tTotal de lotes encontrados: " + lotes.size());
            
            int contador = 1;
            
            //for (Lote lote : producto.getLotes()){
            for (Lote lote : lotes){
                System.out.println("\n\n\tLote número " + contador);
                //System.out.println(lote);
                System.out.println("\n\t\tCódigo: " + lote.getCodigo() + "\tNúmero de identificación (ID): " + lote.getId() + "\n\t\t\tCantidad: " + lote.getCantidad() + "\n\t\t\tFecha de caducidad: " + lote.getFechaCaducidad() + "\n\t\t\tEstado" + lote.getEstado() + "\n\n\t\t\tProducto al que corresponde: " + lote.getProducto().getNombre() + "\tNúmero de identificación (ID): " + lote.getProducto().getId() + "\tCódigo de barras: " + lote.getProducto().getCodigoBarras() + "\n\t\t\t\tCantidad: " + lote.getProducto().getCategoria() + "\n\t\t\t\tPrecio: " + lote.getProducto().getPrecio() + "\n\t\t\t\tNúmero de lotes asociados: " + lote.getProducto().getLotes().size() + "\n\n\t\t\tProveedor al que pertenece: " + lote.getProducto().getProveedor().getNombre() + "\tNúmero de identificación (ID): " + lote.getProducto().getProveedor().getId() + "\tCódigo de identificación fiscal (CIF): " + lote.getProducto().getProveedor().getCif() + "\n\t\t\t\tContacto\n\t\t\t\t\tCorreo electrónico (email): " + lote.getProducto().getProveedor().getEmail() + "\n\t\t\t\t\tTeléfono: " + lote.getProducto().getProveedor().getTelefono() + "\n\t\t\t\tNúmero de productos provistos: " + lote.getProducto().getProveedor().getProductos().size() + " en total.");
                //System.out.println("----------------------------------------------------------------------");
                System.out.println("-".repeat(70));
                
                contador ++;
            }
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            JOptionPane.showMessageDialog(null, "Error inesperado durante la búsqueda del lote/s por su estado actual ('" + estado + ")': " + hex.getMessage(), "Error de búsqueda", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
        // Crea el método "obtenerEstadisticasSupermercado." Su función será ir recuperando/leyendo todos los objetos de nuestra base de datos para generar y mostrar unas estadísticas generalizadas/globales sobre los datos obtenidos.
            // Busca y trata todos los objetos de nuestra base de datos para crear y mostrar estadísticas en base a ellos.
    public void obtenerEstadisticasSupermercado(){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tAnalizando datos y generando estadísticas del supermercado...");
        
        try{
            String hql;
            System.out.println("\n\n\t<==================== ESTADÍSTICAS DEL SUPERMERCADO ====================>");
            
            // Bloque de código para contabilizar el número total de registros de proveedores en la DB.
                /*
                 * 1. Crea una consulta HQL que selecciona la cantidad total de proveedores registrados en la entidad "Proveedor".
                 * 2. Con "COUNT(p)" cuenta cuántos objetos (proveedores) existen en la base de datos.
                 * 3. Con "Long.class" indica que el resultado devuelto por la consulta será de tipo numérico largo (Long).
                 * 4. Con "uniqueResult()" obtiene un único valor (no una lista) con el total de proveedores.
                 * 5. Guarda ese número total en la variable "totalProveedores".
                 */
            String proveedores = "SELECT COUNT(p) FROM Proveedor p";
            hql = proveedores;
            Long totalProveedores = session.createQuery(hql, Long.class).uniqueResult();
                        
            // Bloque de código para contabilizar el número total de registros de productos en la DB.
                /*
                 * 1. Crea una consulta HQL que selecciona la cantidad total de productos registrados en la entidad "Producto".
                 * 2. Con "COUNT(p)" cuenta cuántos objetos (productos) existen en la base de datos.
                 * 3. Con "Long.class" indica que el resultado devuelto por la consulta será de tipo numérico largo (Long).
                 * 4. Con "uniqueResult()" obtiene un único valor (no una lista) con el total de productos.
                 * 5. Guarda ese número total en la variable "totalProductos".
                 */
            String productos = "SELECT COUNT(p) FROM Producto p";
            hql = productos;
            Long totalProductos = session.createQuery(hql, Long.class).uniqueResult();
            
            // Bloque de código para contabilizar el número total de registros de lotes en la DB.
                /*
                 * 1. Crea una consulta HQL que selecciona la cantidad total de lotes registrados en la entidad "Lote".
                 * 2. Con "COUNT(l)" cuenta cuántos objetos (lotes) existen en la base de datos.
                 * 3. Con "Long.class" indica que el resultado devuelto por la consulta será de tipo numérico largo (Long).
                 * 4. Con "uniqueResult()" obtiene un único valor (no una lista) con el total de lotes.
                 * 5. Guarda ese número total en la variable "totalLotes".
                 */
            String lotes = "SELECT COUNT(l) FROM Lote l";
            hql = lotes;
            Long totalLotes = session.createQuery(hql, Long.class).uniqueResult();
            
            System.out.println("\n\t\t\t\t<========== CONTEOS TOTALES ==========>");
            
            System.out.println("\n\t\t\tNúmero total de proveedores registrados: " + totalProveedores + "\n\t\t\tNúmero total de productos registrados: " + totalProductos + "\n\t\t\tNúmero total de lotes registrados: " + totalLotes + "\n");
            
            // Bloque de código para contabilizar el número total de registros de lotes por estado de disponibilidad en la DB.
                // El método ".values()" es un método propio de los "enum" en Java. Devuelve un array con todos los valores que tiene ese "enum". En este caso el array será tal: "[DISPONIBLE, VENDIDO, CADUCADO, RETIRADO]. 
            for (EstadoLote estado : EstadoLote.values()){
                    // ":estado" Es el valor real que espera del parametro que pasaremos "setParameter" (" "estado", " →(=) estado).
                Long cantidad = session.createQuery("SELECT COUNT(l) FROM Lote l WHERE l.estado = :estado", Long.class).setParameter("estado", estado).uniqueResult();
                
                System.out.println("\t- " + estado + ": " + cantidad);
            }
            
            //System.out.println("----------------------------------------------------------------------");
            System.out.println("-".repeat(70));
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            JOptionPane.showMessageDialog(null, "Error inesperado durante la obtención de datos y/o el cálculo de las operaciones para obtener las estadísticas: " + hex.getMessage(), "Error de operación", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
    // ==================== OPERACIONES UPDATE ====================
        // Crea el método "actualizarEstadoLote." Su función será ir recuperando el lote/s e ir actualizando su estado/s.
            // Busca los lotes por su número de identificación (ID) y actualiza su estado.
    public void actualizarEstadoLote(Long idLote, EstadoLote nuevoEstado){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando un lote por su número de identificación o ID {" + idLote + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar al lote por su ID.
            Lote lote = session.get(Lote.class, idLote);
            
            // Si no hay lotes, es decir, si la tabla "lotes" está vacía se muestra un mensaje informativo.
            if (lote == null){
                System.out.println("\n\n\tNo hay, no se han encontrado o no existe ningún lote con el número de identificación (ID): {'" + idLote + "'}.");
                return;
            }
            
            System.out.println("\n\n\tLote ('" + idLote + "\t[" + lote.getCodigo() + "]') encontrado exitosamente.\n\t\tCantidad: " + lote.getCantidad() + "\n\t\tFecha de caducidad: " + lote.getFechaCaducidad() + "\n\t\tEstado actual: " + lote.getEstado() + "\n\t\tFecha de caducidad: " + lote.getFechaCaducidad() + "\n\t\t\tEstado actual: " + lote.getEstado() + "\n\t\tProducto asociado al que pertenece: " + lote.getProducto().getNombre() + "\tNúmero de identificación (ID): " + lote.getProducto().getId() + "\tCódigo de barras: " + lote.getProducto().getCodigoBarras() + "\n\t\t\tCategoría: " + lote.getProducto().getCategoria() + "\n\t\t\tPrecio: " + lote.getProducto().getPrecio() + "\n\t\t\tNúmero de lotes asociados: " + lote.getProducto().getLotes().size() + " en total.\n\t\tProveedor asociado al que pertenece: " + lote.getProducto().getProveedor().getNombre() + "\tNúmero de identificación (ID): " + lote.getProducto().getProveedor().getId() + "\tNúmero de identificación fiscal (CIF): " + lote.getProducto().getProveedor().getCif() + "\n\t\t\tContacto:\n\t\t\tCorreo electrónico (email): " + lote.getProducto().getProveedor().getEmail() + "\n\t\t\tTeléfono: " + lote.getProducto().getProveedor().getTelefono() + "\n\t\t\tNúmero de productos: " + lote.getProducto().getProveedor().getProductos().size() + " en total.");
            
            EstadoLote estadoAntiguo = lote.getEstado();
            
            // Bloque de código para actualizar el estado.
            lote.setEstado(nuevoEstado);
            
            // Si el estado del lote encontrado/buscado pasa a "VENDIDO", cambia/actualiza su cantidad.
            if (nuevoEstado == EstadoLote.VENDIDO){
                lote.setCantidad(0);
                System.out.println("\n\n\tEl estado del lote ('" + idLote + "') ha cambiado de " + estadoAntiguo + " a " + nuevoEstado + ". [VENDIDO → Cantidad ajustada a 0]");
            }
            
            // Bloque de código para guardar los cambios/actualizaciones recientes hechos sobre los campos/atributos del lote.
            session.update(lote);
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tEstado del lote actualizado correctamente.");
            
            System.out.println("\n\n\tLote ('" + idLote + "\t[" + lote.getCodigo() + "]')\n\t\tCantidad: " + lote.getCantidad() + "\n\t\tFecha de caducidad: " + lote.getFechaCaducidad() + "\n\t\tEstado actual (nuevo): " + /*lote.getEstado()*/nuevoEstado + "\n\t\tFecha de caducidad: " + lote.getFechaCaducidad() + "\n\t\t\tEstado actual: " + lote.getEstado() + "\n\t\tProducto asociado al que pertenece: " + lote.getProducto().getNombre() + "\tNúmero de identificación (ID): " + lote.getProducto().getId() + "\tCódigo de barras: " + lote.getProducto().getCodigoBarras() + "\n\t\t\tCategoría: " + lote.getProducto().getCategoria() + "\n\t\t\tPrecio: " + lote.getProducto().getPrecio() + "\n\t\t\tNúmero de lotes asociados: " + lote.getProducto().getLotes().size() + " en total.\n\t\tProveedor asociado al que pertenece: " + lote.getProducto().getProveedor().getNombre() + "\tNúmero de identificación (ID): " + lote.getProducto().getProveedor().getId() + "\tNúmero de identificación fiscal (CIF): " + lote.getProducto().getProveedor().getCif() + "\n\t\t\tContacto:\n\t\t\tCorreo electrónico (email): " + lote.getProducto().getProveedor().getEmail() + "\n\t\t\tTeléfono: " + lote.getProducto().getProveedor().getTelefono() + "\n\t\t\tNúmero de productos: " + lote.getProducto().getProveedor().getProductos().size() + " en total.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (cambio/actualización del estado del lote): " + hex.getMessage(), "Error de actualización", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
        // Crea el método "actualizarPrecioProducto." Su función será ir recuperando el producto/s e ir actualizando sus datos.
            // Busca los producto por su número de identificación (ID) y actualiza sus datos.
    public void actualizarPrecioProducto(Long idProducto, Double nuevoPrecio){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando un producto por su número de identificación o ID {" + idProducto + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para validar si el precio es positivo.
            if (nuevoPrecio <= 0){
                System.out.println("\n\n\tError: El precio introducido debe corresponderse a uno mayor que 0.");
                return;
            }
            
            // Bloque de código para buscar al producto por su ID.
            Producto producto = session.get(Producto.class, idProducto);
            
            // Si no hay productos, es decir, si la tabla "productos" está vacía se muestra un mensaje informativo.
            if (producto == null){
                System.out.println("\n\n\tNo hay, no se han encontrado o no existe ningún producto con el número de identificación (ID): {'" + idProducto + "'}.");
                return;
            }
            
            System.out.println("\n\n\tProducto {" + idProducto + "} encontrado exitosamente.\tCódigo de barras: " + producto.getCodigoBarras()+ "\n\t\tNombre: " + producto.getNombre() + "\n\t\tCategoría: " + producto.getCategoria() + "\n\t\tPrecio: " + producto.getPrecio() + "\n\t\tNúmero de lotes asociados: " + producto.getLotes().size() + "\n\t\tProveedor asociado al que pertenece: " + producto.getProveedor().getNombre() + "\tNúmero de identificación (ID): " + producto.getProveedor().getId() + "\tNúmero de identificación fiscal (CIF): " + producto.getProveedor().getCif() + "\n\t\t\tContacto:\n\t\t\tCorreo electrónico (email): " + producto.getProveedor().getEmail() + "\n\t\t\tTeléfono: " + producto.getProveedor().getTelefono() + "\n\t\t\tNúmero de productos: " + producto.getProveedor().getProductos().size() + " en total.");
            
            Double precioAnterior = producto.getPrecio();
            
            // Bloque de código para actualizar el campo/atributo del producto.
            producto.setPrecio(nuevoPrecio);
            
            // Bloque de código para guardar el cambio/actualización reciente hecho sobre el campo/atributo del producto.
            session.update(producto);
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();
            
            System.out.println("\n\n\tEl campo/atributo (precio) del produto ha sido actualizado correctamente, de " + precioAnterior + "€ a " + nuevoPrecio + "€.");
            
            System.out.println("\n\n\tProducto {" + idProducto + "} encontrado exitosamente.\tCódigo de barras: " + producto.getCodigoBarras()+ "\n\t\tNombre: " + producto.getNombre() + "\n\t\tCategoría: " + producto.getCategoria() + "\n\t\tPrecio (nuevo): " + /*producto.getPrecio()*/nuevoPrecio + "\n\t\tNúmero de lotes asociados: " + producto.getLotes().size() + "\n\t\tProveedor asociado al que pertenece: " + producto.getProveedor().getNombre() + "\tNúmero de identificación (ID): " + producto.getProveedor().getId() + "\tNúmero de identificación fiscal (CIF): " + producto.getProveedor().getCif() + "\n\t\t\tContacto:\n\t\t\tCorreo electrónico (email): " + producto.getProveedor().getEmail() + "\n\t\t\tTeléfono: " + producto.getProveedor().getTelefono() + "\n\t\t\tNúmero de productos: " + producto.getProveedor().getProductos().size() + " en total.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (cambio/actualización del precio del producto): " + hex.getMessage(), "Error de actualización", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
        // Crea el método "transferirProductosEntreProveedores." Su función será transferir todos los datos de un proveedor a otro.
            // Busca los proveedores (origen y destino) por su número de identificación (ID) y transfiere sus datos.
    public void transferirProductosEntreProveedores(Long idProveedorOrigen, Long idProveedorDestino){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando preoveedor (de origen) por su número de identificación o ID {" + idProveedorOrigen + "}...");
        System.out.println("\n\n\tBuscando preoveedor (de destino) por su número de identificación o ID {" + idProveedorDestino + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar a ambos proveedores por su ID.
            Proveedor proveedorOrigen = session.get(Proveedor.class, idProveedorOrigen);
            Proveedor proveedorDestino = session.get(Proveedor.class, idProveedorDestino);
            
            // Si no hay ninguno de los proveedores, es decir, si la tabla "proveedores" está vacía se muestran dos mensajes informativos, uno para cada uno de los proveedores.
            if (proveedorOrigen == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún proveedor (de origen) con el número de identificación (ID): {'" + idProveedorOrigen + "'}.");
                return;
            }
            
            if (proveedorDestino == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún proveedor (de destio) con el número de identificación (ID): {'" + idProveedorDestino + "'}.");
                return;
            }
            
            System.out.println("\n\n\tProveedor (de origen) ('" + idProveedorOrigen + "\t[" + proveedorOrigen.getCif() + "]') encontrado exitosamente.\n\t\tNombre: " + proveedorOrigen.getNombre() + "\n\t\tContacto\n\t\t\tCorreo electrónico (email): " + proveedorOrigen.getEmail() + "\n\t\t\tTeléfono: " + proveedorOrigen.getTelefono() + "\n\t\t\tNúmero de productos provistos:  " + proveedorOrigen.getProductos().size());
            System.out.println("\n\n\tProveedor (de destino) ('" + idProveedorDestino + "\t[" + proveedorDestino.getCif() + "]') encontrado exitosamente.\n\t\tNombre: " + proveedorDestino.getNombre() + "\n\t\tContacto\n\t\t\tCorreo electrónico (email): " + proveedorDestino.getEmail() + "\n\t\t\tTeléfono: " + proveedorDestino.getTelefono() + "\n\t\t\tNúmero de productos provistos:  " + proveedorDestino.getProductos().size());
            
            // Bloque de código para obtener una lista con los productos del proveedor de origen.
            List<Producto> productosAtraspasar = new ArrayList<>(proveedorOrigen.getProductos());
            
            // Si no hay productos, es decir, si la tabla "productos" está vacía se muestra un mensaje informativo.
            if (productosAtraspasar.isEmpty()){
            System.out.println("\n\n\tEl proveedor (de origen) no tiene productos para transferir.");
                return;
            }
            
            System.out.println("\n\n\tEl proveedor (de origen) tiene productos para transferir.\n\t\tProductos que serán transferidos: " + /*proveedorOrigen.getProductos().size()*/productosAtraspasar.size());
            
            // Bloque de código para transferir producto a producto del proveedor de origen al proveedor de dedestino directamente, sin usar los métodos helper ya que en este caso concreto causarían conflicto con la cascada de Hibernate.
            for (Producto producto : productosAtraspasar){                
                producto.setProveedor(proveedorDestino);    /* Cambia el proveedor del producto directamente. */
                
                // Con el método "Merge" actualizamos el producto/s (en este caso la parte de sus lotes correspondientes) y, por cascada, persiste el nuevo lote.
                session.merge(producto);
            }
            
            //proveedorOrigen.getProductos().remove(producto);  /* Primero obtiene el producto/s y después elimina la relación del lado origen, es decir, remueve de la lista al proveedor de origen. */
            proveedorOrigen.getProductos().clear();             /* Limpia y actualiza las colecciones. */
            
            // Fuerza la sincronización con la BD.
            session.flush();
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tTransferencia de productos completada correcta y exitosamente.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (transferencia de datos, productos de proveedor origen a proveedor destino): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
    // ==================== OPERACIONES DELETE ====================
        // Crea el método "eliminarLote." Su función será recuperar el producto para eliminar su lote.
            // Busca el producto a partir del código de identificación (ID) del lote para después borrar ese mismo lote mediante uno de los métodos "helper" de la clase "Producto.java".
    public void eliminarLote(Long idLote){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando lote por su número de identificación o ID {" + idLote + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar lotes por su ID.
            Lote lote = session.get(Lote.class, idLote);
            
            // Si no hay lotes, es decir, si la tabla "lote" está vacía se muestra un mensaje informativo.
            if (lote == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún lote con el número de identificación (ID): {'" + idLote + "'}.");
                return;
            }
            
            // Bloque de código para obtener el producto a partir del lote.
            Producto producto = lote.getProducto();
            
            // Si no hay lotes, es decir, si la tabla "lote" está vacía se muestra un mensaje informativo.
            if (producto == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún producto asociado a dicho lote {'" + idLote + "'}.");
                return;
            }
            
            System.out.println("\n\n\tLote {" + idLote + "} encontrado exitosamente.\tNúmero de identificación (ID): " + lote.getId() + "\tCódigo: " + lote.getCodigo() + "\n\t\tCantidad: " + lote.getCantidad()+ "\n\t\tFecha de caducidad: " + lote.getFechaCaducidad() + "\n\t\tEstado: " + lote.getEstado() + "\n\t\tProducto asociado al que pertenece: " + lote.getProducto().getNombre());
            
            // Bloque de código para mantener consistencia bidireccional entre "Producto" y "Lote".
            producto.removeLote(lote);  /* Llamamos al método "helper" → "removeLote" para conseguir eliminar el lote automáticamente. */
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tLote eliminado/borrado de la lista de lotes del producto ('" + producto.getNombre() + "') correctamente.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (eliminación del lote): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
        // Crea el método "eliminarProducto." Su función será recuperar el proveedor para eliminar su producto.
            // Busca el proveedor a partir del código de identificación (ID) del producto para después borrar ese mismo producto mediante uno de los métodos "helper" de la clase "Proveedor.java".
    public void eliminarProducto(Long idProducto){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando producto por su número de identificación o ID {" + idProducto + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar el producto por su ID.
            Producto producto = session.get(Producto.class, idProducto);
            
            // Si no hay productos, es decir, si la tabla "productos" está vacía se muestra un mensaje informativo.
            if (producto == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún producto con el número de identificación (ID): {'" + idProducto + "'}.");
                return;
            }
                        
            // Bloque de código para obtener el proveedor.
            Proveedor proveedor = producto.getProveedor();
            
            // Si no hay proveedores, es decir, si la tabla "proveedores" está vacía se muestra un mensaje informativo.
            if (proveedor == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún proveedor asociado a dicho producto {'" + idProducto + "'}.");
                return;
            }

            System.out.println("\n\n\tProducto {" + idProducto + "} encontrado exitosamente.\tCódigo de barras: " + producto.getCodigoBarras() + "\n\t\tNombre: " + producto.getNombre()+ "\n\t\tCategoria: " + producto.getCategoria() + "\n\t\tPrecio: " + producto.getPrecio()+ "\n\t\tNúmero de lotes asociados: " + producto.getLotes().size()+ "\n\t\tProveedor asociado al que pertenece: " + producto.getProveedor().getNombre());
            
            // Bloque de código (opcional) para contabilizar y así saber cúantos lotes serán eliminados al eliminar el producto.
            int lotesEliminados = producto.getLotes().size();
            
            // Bloque de código para mantener consistencia bidireccional entre "Proveedor" y "Producto".
            proveedor.removeProducto(producto); /* Llamamos al método "helper" → "removeProducto" para conseguir eliminar el lote automáticamente. */
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tProducto eliminado/borrado de la lista de productos del proveedor ('" + idProducto + "') correctamente.\n\t\tAl eliminar el producto, esta acción también hizo que se borrasen (borrado en cascada) todos los lotes correspondientes a dicho producto, es decir un total de: " + lotesEliminados + " lotes eliminados.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (eliminación del producto): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
    
        // Crea el método "eliminarProveedorConCascada." Su función será recuperar y eliminar (en cascada) el proveedor.
            // Busca el proveedor a partir del código de identificación (ID) para después eliminarlo/borrarlo en casacada, lo que supone también eliminar todos sus productos y los lotes correspondientes de la DB ese mismo producto mediante uno de los métodos "helper" de la clase "Proveedor.java".
    public void eliminarProveedorConCascada(Long idProveedor){
        // Se abre una sesión de Hibernate para interactuar con la base de datos.
        session = HibernateUtil.getSessionFactory().openSession();
        //transaction = null;
        
        System.out.println("\n\n\tBuscando proveedor por su número de identificación o ID {" + idProveedor + "}...");
        
        try{
            // Se abre/inicia una nueva transacción.
            transaction = session.beginTransaction();
            
            // Bloque de código para buscar el proveedor.
            Proveedor proveedor = session.get(Proveedor.class, idProveedor);
            
            // Si no hay proveedores, es decir, si la tabla "proveedor" está vacía se muestra un mensaje informativo.
            if (proveedor == null){
                System.out.println("\n\n\tNo se encontró o no existe ningún proveedor con el número de identificación (ID): {'" + idProveedor + "'}.");
                return;
            }
            
            System.out.println("\n\n\tProveedor ('" + idProveedor + "\t[" + proveedor.getCif() + "]') encontrado exitosamente.\n\t\tNombre: " + proveedor.getNombre() + "\n\t\tContacto:\n\t\t\tCorreo electrónico (email): " + proveedor.getEmail()+ "\n\t\t\tTeléfono: " + proveedor.getTelefono() + "\n\t\tNúmero de productos asociados: " + proveedor.getProductos().size());
            
            // Bloque de código (opcional) para contabilizar y así saber cúantos productos serán eliminados al eliminar el proveedor.
            int productosEliminados = proveedor.getProductos().size();
            
            // Bloque de código (opcional) para contabilizar y así saber cúantos lotes serán eliminados al eliminar el productos.
            int lotesEliminados = 0;
            for (Producto producto : proveedor.getProductos()){
                lotesEliminados += producto.getLotes().size();
            }
            
            // Bloque de código para mantener el borrado en cascada entre "Lote", "Producto" y "Proveedor".
            session.remove(proveedor);
            
            // Bloque de código para confirmar la transacción.
            transaction.commit();

            System.out.println("\n\n\tProveedor eliminado/borrado de la lista de proveedores ('" + proveedor.getNombre() + "') correctamente.\n\t\tAl eliminar el proveedor, esta acción también hizo que se borrasen (borrado en cascada) todos los productos y lotes correspondientes a dicho proveedor, es decir un total de: " + productosEliminados + " productos eliminados y un total de: " + lotesEliminados + " lotes eliminasos.");
        } catch (HibernateException hex){
            // Si ocurre un error/excepción revierte el proceso de transacción.
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(null, "Error inesperado durante la ejecución de la transacción (eliminación del proveedor): " + hex.getMessage(), "Error de ejecución", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        } finally{
            session.close();
        }
    }
}