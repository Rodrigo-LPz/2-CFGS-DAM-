/**
 *
 * @author Rodrigo
 */
package com.dam.modelo;


// Importa todos los paquetes de la biblioteca/librería "persistence".
import javax.persistence.*;
// Importa de todos los paquetes de la biblioteca/librería "ArrayList".
import java.util.ArrayList;
// Importa de todos los paquetes de la biblioteca/librería "List".
import java.util.List;

// Crea la clase "Producto".
    // Definimos una entidad que representará a un producto correspondiente de un lote de estos en el supermercado.
        /*
         * 1. Primero mapea a la tabla "productos" en la base de datos.
         * 2. Despues define una relación donde/en la que múltiples productos corresponden a un único proveedor, es decir, relación muchos a uno, un muchos productos-un proveedor: (N:1).
         * 3. Y por último define una relación donde/en la que un producto puede corresponder a múltiples lotes, es decir, relación uno a muchos, un producto-muchos lotes: (1:N).
         */
@Entity
@Table(name = "productos")
public class Producto{
    // ==================== ATRIBUTOS ====================
        // Declara las variables, atributos.
            // (Sustituye al archivo "Producto.hbm.xml").
                /*
                 * 1. Se genera el identificador, "ID", único (clave primaria) de "producto".
                 * 2. Se genera automáticamente mediante estrategia "IDENTITY".
                 * 3. Se 'ancla'/enlaza a la columna de nombre "id_producto".
                 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id; /* Utilizo "Long" (clase envolvente) en vez de "long" (tipo primitivo) ya que Hibernate necesita saber si el objeto (producto) ya tiene un valor de (id) asignado o si debe generarlo automáticamente. Al usar tipos primitivos su valor por defecto será (0) confundiéndo a Hibernate y haciéndole pensar que ya tiene un valor (id) asignado. Por el contrario, al usar una clase envolvente, como es el caso, es valor inicial por defecto es (null), de esta forma Hibernate sabrá que si recibe o lee un valor nulo deberá generar uno nuevo antes/al guardar el objeto. */
    
                /*
                 * 1. Atributo para nombre del producto.
                 * 2. Se 'ancla'/enlaza a la columna de nombre "nombre", añadiéndole atributos de campo obligatorio ("nullable") y de máximo 200 caracteres ("length").
                 */
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

                /*
                 * Atributo para el codigo de barras del producto.
                 * Se 'ancla'/enlaza a la columna de nombre "codigo_barras", añadiéndole atributos de campo único ("unique"), de campo obligatorio ("nullable") y de máximo 13 caracteres ("length").
                 */
    @Column(name = "codigo_barras", unique = true, nullable = false, length = 13)
    private String codigoBarras;

                /*
                 * Atributo para el precio del producto.
                 * Se 'ancla'/enlaza a la columna de nombre "precio", añadiéndole el atributo de campo obligatorio ("nullable").
                 * Al no anclar a la columna "precio" el atributo "length" el campo queda sin un límite máximo de caracteres.
                 */
    @Column(name = "precio", nullable = false)
    private Double precio;

                /*
                 * Atributo para la categoria del producto.
                 * Se 'ancla'/enlaza a la columna de nombre "categoria", añadiéndole el atributo de máximo 100 caracteres ("length").
                 * Al no anclar a la columna "categoria" el atributo "nullable" el campo queda opcional, en vez de obligatorio.
                 */
    @Column(name = "categoria", length = 100)
    private String categoria;
    
    
    // ==================== RELACIONES ====================
        /*
         * Hacemos una relación para la lista de productos pertenecientes al/provistos por el mismo proveedor. Es decir, una relación entre la lista de productos, los productos, y el proveedor al que pertenecen/corresponden.
         * 
         * Hacemos una relación (N:1) bidireccional con "Lote".
         *   El atributo "  fetch = FetchType.LAZY  " hace/aplica una carga perezosa retrasando la carga de contenido hasta que sea necesario, consiguiendo optimizar el rendimiento de recursos y tiempo.
         * 
         * Se 'ancla'/enlaza a la columna de nombre "id_proveedor" de la tabla "productos".
         */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;
    
        /*
         * Hacemos una relación para la lista de lotes corresponden al mismo producto. Es decir, una relación entre la lista de lotes, los lotes, y el producto al que pertenecen/corresponden.
         * 
         * Hacemos una relación (1:N) bidireccional con "Lote".
         *   El atributo "  mappedBy = "producto"        " indica que "Lote" es el propietario de la relación.
         *   El atributo "  cascade = CascadeType.ALL    " refire a que todas las operaciones se propagan a los lotes.
         *   El atributo "  orphanRemoval = true         " elimina automáticamente lotes huérfanos.
         *   El atributo "  fetch = FetchType.LAZY       " hace/aplica una carga perezosa retrasando la carga de contenido hasta que sea necesario, consiguiendo optimizar el rendimiento de recursos y tiempo.
         * 
         * Definimos la cascada aplicada ("cascade = CascadeType.ALL").
         *   El atributo "  PERSIST " Aplica que al guardar un producto, guarda sus lotes.
         *   El atributo "  MERGE   " Aplica que al actualizar un producto, actualiza sus lotes.
         *   El atributo "  REMOVE  " Aplica que al eliminar un producto, elimina sus lotes.
         *   El atributo "  REFRESH " Aplica que al refrescar un producto, refresca sus lotes.
         *   El atributo "  DETACH  " Aplica que al desasociar un producto, desasocia sus lotes.
         */
    @OneToMany(mappedBy = "producto", 
           cascade = CascadeType.ALL, 
           orphanRemoval = true, 
           fetch = FetchType.LAZY)
    private List<Lote> lotes = new ArrayList<>();
    
    
    // ==================== CONSTRUCTORES ====================
        // Crea el constructor vacío, sin parámetros. Es necesario para que "JPA" pueda crear las instancias.
    public Producto(){}
    
        // Crea el constructor de/para las variables, con todos los campos excepto el "ID" y relaciones.
            /*
             * "@param nombre"          Parámetro: Nombre del producto.
             * "@param codigoBarras"    Parámetro: Código de barras del producto.
             * "@param precio"          Parámetro: Precio del producto.
             * "@param categoria"       Parámetro: Categoria del producto.
             */
    public Producto(String nombre, String codigoBarras, Double precio, String categoria){
        this.nombre = nombre;
        this.codigoBarras = codigoBarras;
        this.precio = precio;
        this.categoria = categoria;
    }
    
    
    // ==================== MÉTODOS HELPER ====================
    
    /*
     * Añade un lote al producto manteniendo la consistencia bidireccional.
     * 
     * Este método es fundamental para mantener la integridad de la relación:
     *   1. Añade el lote a la lista de lotes del producto.
     *   2. Añade el producto al lote (lado opuesto de la relación).
     *
     *      Ejemplo de uso:
     *         Producto producto = new Producto("Aceite de oliva", "8412345678901", ...);
     *         Lote lote = new Lote("LOT-2024-001", 50, ...);
     *         producto.addLote(lote);  → Permite que se mantenga la consistencia entre/en ambos lados.
     * 
     * "@param lote" Parámetro: Lote a añadir al producto.
     */
    public void addLote(Lote lote){
        lotes.add(lote);
        lote.setProducto(this);
    }
    
    /*
     * Elimina un lote del producto manteniendo la consistencia bidireccional.
     * 
     * Este método es fundamental para mantener la integridad de la relación:
     *   1. Elimina el lote de la lista de lotes del producto.
     *   2. Elimina el producto (su referencia) del lote (lado opuesto).
     * 
     * Si "orphanRemoval = true" (está activado), Hibernate eliminará el lote de la base de datos automáticamente.
     * 
     *      Ejemplo de uso:
     *         Lote lote = producto.getLotes().get(0);
     *         producto.removeLote(lote);   → El lote será eliminado de la BD.
     * 
     * "@param producto" Parámetro: Producto a eliminar del proveedor.
     */
    public void removeLote(Lote lote){
        lotes.remove(lote);
        lote.setProducto(null);
    }
    
    
    // ==================== GETTERS ====================
        // Crea los métodos "get".
            /*
             * 1. Obtiene el ID del producto.
             * 2. "@return id" Retorna: ID del producto.
             *
             * 3. Obtiene el nombre del producto.
             * 4. "@return nombre" Retorna: Nombre del producto.
             *
             * 5. Obtiene el código de barras del producto.
             * 6. "@return codigoBarras" Retorna: Código de barras del producto.
             *
             * 7. Obtiene el precio del producto.
             * 8. "@return precio" Retorna: Precio del producto.
             *
             * 9. Obtiene la categoría del producto.
             * 10. "@return categoria" Retorna: Categoría del producto.
             *
             * 11. Obtiene el proveedor del producto.
             * 12. "@return proveedor" Retorna: Proveedor del producto.
             *
             * 13. Obtiene la lista de lotes del producto.
             *   (Es fundamental saber que si se accede fuera de una sesión de Hibernate y la carga es "LAZY", puede lanzar "LazyInitializationException").
             * 14. "@return lotes" Retorna: Lista de lotes del producto.
             */
    public Long getId(){ return id; }
    public String getNombre(){ return nombre; }
    public String getCodigoBarras(){ return codigoBarras; }
    public Double getPrecio(){ return precio; }
    public String getCategoria(){ return categoria; }
    
    public Proveedor getProveedor(){ return proveedor; }
    
    public List<Lote> getLotes(){
        return lotes; }
    
    
    // ==================== SETTERS ====================
        // Crea los métodos "set".
            /*
             * 1. Establece el ID del producto.
             * 2. "@param id" Parámetro: ID del producto.
             *
             * 3. Establece el nombre del producto.
             * 4. "@param nombre" Parámetro: Nombre del producto.
             *
             * 5. Establece el código de barras del producto.
             * 6. "@param codigoBarras" Parámetro: Código de barras del producto.
             *
             * 7. Establece el precio del producto.
             * 8. "@param precio" Parámetro: Precio del producto.
             *
             * 9. Establece la categoría del producto.
             * 10. "@param categoria" Parámetro: Categoría del producto.
             *
             * 11. Establece el proveedor del producto.
             * 12. "@param proveedor" Retorna: Proveedor del producto.
             *
             * 13. Establece la lista de ejempalres del producto.
             *   (Es fundamental usar "addLote()" y "removeLote()" para mantener la consistencia bidireccional automáticamente).
             * 14. "@param Lista" Retorna: Lista de lotes del producto.
             */
    public void setId(Long id){ this.id = id; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    public void setCodigoBarras(String codigoBarras){ this.codigoBarras = codigoBarras; }
    public void setPrecio(Double precio){ this.precio = precio; }
    public void setCategoria(String categoria){ this.categoria = categoria; }
    
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    
    public void setLotes(List<Lote> lotes){ this.lotes = lotes; }
    
    
    // ==================== TOSTRING ====================
        // Crea el método "toString".
    @Override
    public String toString(){
        return "\n\t\tProducto {ID: (" + id + ")}{"
             + "\n\t\t\tNombre:" + nombre
             + "\n\t\t\tCódigo de barras:" + codigoBarras
             + "\n\t\t\tPrecio:" + precio
             + "\n\t\t\tCategoría: " + categoria
             + "\n\t\t\tNº de lotes:" + (lotes != null ? lotes.size() : 0)
             + "\n\t\t" + '}';
    }
}