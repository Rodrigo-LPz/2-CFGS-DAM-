/**
 *
 * @author Rodrigo
 */
package com.dam.modelo;


// Importa todos los paquetes de la biblioteca/librería "persistence".
import javax.persistence.*;
// Importa de todos los paquetes de la biblioteca/librería "LocalDate".
import java.time.LocalDate;
// Importa de todos los paquetes de la biblioteca/librería "ArrayList".
import java.util.ArrayList;
// Importa de todos los paquetes de la biblioteca/librería "List".
import java.util.List;

// Crea la clase "Proveedor".
    // Definimos una entidad que representará a un proveedor de productos en el supermercado.
        /*
         * 1. Primero mapea a la tabla "proveedores" en la base de datos.
         * 2. Despues define una relación donde/en la que un proveedor puede tener múltiples productos, es decir, relación uno a muchos, un proveedor-muchos productos: (1:N).
         */
@Entity
@Table(name = "proveedores")
public class Proveedor{
    // ==================== ATRIBUTOS ====================
        // Declara las variables, atributos.
            // (Sustituye al archivo "Proveedor.hbm.xml").
                /*
                 * 1. Se genera el identificador, "ID", único (clave primaria) de "proveedor".
                 * 2. Se genera automáticamente mediante estrategia "IDENTITY".
                 * 3. Se 'ancla'/enlaza a la columna de nombre "id_proveedor".
                 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long id; /* Utilizo "Long" (clase envolvente) en vez de "long" (tipo primitivo) ya que Hibernate necesita saber si el objeto (proveedor) ya tiene un valor de (id) asignado o si debe generarlo automáticamente. Al usar tipos primitivos su valor por defecto será (0) confundiéndo a Hibernate y haciéndole pensar que ya tiene un valor (id) asignado. Por el contrario, al usar una clase envolvente, como es el caso, el valor inicial por defecto es (null), de esta forma Hibernate sabrá que si recibe o lee un valor nulo deberá generar uno nuevo antes/al guardar el objeto. */
    
                /*
                 * 1. Atributo para el nombre del proveedor.
                 * 2. Se 'ancla'/enlaza a la columna de nombre "nombre", añadiéndole atributos de campo obligatorio ("nullable") y de máximo 200 caracteres ("length").
                 */
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

                /*
                 * Atributo para el código de identificación fiscal (CIF) del proveedor.
                 * Se 'ancla'/enlaza a la columna de nombre "cif", añadiéndole atributos de campo único ("unique"), de campo obligatorio ("nullable") y de máximo 20 caracteres ("length").
                 */
    @Column(name = "cif", unique = true, nullable = false, length = 20)
    private String cif;

                /*
                 * Atributo para el número de teléfono del proveedor.
                 * Se 'ancla'/enlaza a la columna de nombre "telefono", añadiéndole el atributo de máximo 15 caracteres ("length").
                 * Al no anclar a la columna "telefono" el atributo "nullable" el campo queda opcional, en vez de obligatorio.
                 */
    @Column(name = "telefono", length = 15)
    private String telefono;

                /*
                 * Atributo para el correo electónico (email) del proveedor.
                 * Se 'ancla'/enlaza a la columna de nombre "email", añadiéndole el atributo de máximo 100 caracteres ("length").
                 * Al no anclar a la columna "email" el atributo "nullable" el campo queda opcional, en vez de obligatorio.
                 */
    @Column(name = "email", length = 100)
    private String email;
    
    
    // ==================== RELACIONES ====================
        /*
         * Hacemos una relación para la lista de productos provistos por el proveedor. Es decir, una relación entre la lista de productos, los productos, y el proveedor que los proveyó.
         * 
         * Hacemos una relación (1:N) bidireccional con "Producto".
         *   El atributo "  mappedBy = "proveedor"       " indica que "Producto" es el propietario de la relación.
         *   El atributo "  cascade = CascadeType.ALL    " refire a que todas las operaciones se propagan a los productos.
         *   El atributo "  orphanRemoval = true         " elimina automáticamente productos huérfanos.
         *   El atributo "  fetch = FetchType.LAZY       " hace/aplica una carga perezosa retrasando la carga de contenido hasta que sea necesario, consiguiendo optimizar el rendimiento de recursos y tiempo.
         * 
         * Definimos la cascada aplicada ("cascade = CascadeType.ALL").
         *   El atributo "  PERSIST " Aplica que al guardar un proveedor, guarda sus productos.
         *   El atributo "  MERGE   " Aplica que al actualizar un proveedor, actualiza sus productos.
         *   El atributo "  REMOVE  " Aplica que al eliminar un proveedor, elimina sus productos.
         *   El atributo "  REFRESH " Aplica que al refrescar un proveedor, refresca sus productos.
         *   El atributo "  DETACH  " Aplica que al desasociar un proveedor, desasocia sus productos.
         */
    @OneToMany(mappedBy = "proveedor", 
               cascade = CascadeType.ALL, 
               orphanRemoval = true, 
               fetch = FetchType.LAZY)
    private List<Producto> productos = new ArrayList<>();
    
    
    // ==================== CONSTRUCTORES ====================
        // Crea el constructor vacío, sin parámetros. Es necesario para que "JPA" pueda crear las instancias.
    public Proveedor(){}
    
        // Crea el constructor de/para las variables, con todos los campos excepto el "ID" y relaciones.
            /*
             * "@param nombre"          Parámetro: Nombre del proveedor.
             * "@param apellidos"       Parámetro: Código de identificación fiscal (CIF) del proveedor.
             * "@param nacionalidad"    Parámetro: Teléfono del proveedor.
             * "@param fechaNacimiento" Parámetro: Correo electrónico (email) del proveedor.
             */
    public Proveedor(String nombre, String cif, String telefono, String email){
        this.nombre = nombre;
        this.cif = cif;
        this.telefono = telefono;
        this.email = email;
    }
    
    
    // ==================== MÉTODOS HELPER ====================
    
    /*
     * Añade un producto al proveedor manteniendo la consistencia bidireccional.
     * 
     * Este método es fundamental para mantener la integridad de la relación:
     *   1. Añade el producto a la lista de productos del proveedor.
     *   2. Añade el proveedor al producto (lado opuesto de la relación).
     *
     *      Ejemplo de uso:
     *         Proveedor proveedor = new Proveedor("Distribuciones García", "García Márquez", "B-12345678", ...);
     *         Producto producto = new Producto("Aceite de oliva", "8412345678901", ...);
     *         proveedor.addProducto(producto); → Permite que se mantenga la consistencia entre/en ambos lados.
     * 
     * "@param producto" Parámetro: Producto a añadir al proveedor.
     */
    public void addProducto(Producto producto){
        productos.add(producto);
        producto.setProveedor(this);
    }
    
    /*
     * Elimina un producto del proveedor manteniendo la consistencia bidireccional.
     * 
     * Este método es fundamental para mantener la integridad de la relación:
     *   1. Elimina el producto de la lista de productos del proveedor.
     *   2. Elimina el proveedor(su referencia) del producto (lado opuesto).
     * 
     * Si "orphanRemoval = true" (está activado), Hibernate eliminará el producto de la base de datos automáticamente.
     * 
     *      Ejemplo de uso:
     *         Producto producto = proveedor.getProductos().get(0);
     *         proveedor.removeProducto(producto);  → El producto será eliminado de la BD.
     * 
     * "@param producto" Parámetro: Producto a eliminar del proveedor.
     */
    public void removeProducto(Producto producto){
        productos.remove(producto);
        producto.setProveedor(null);
    }
    
    
    // ==================== GETTERS ====================
        // Crea los métodos "get".
            /*
             * 1. Obtiene el ID del proveedor.
             * 2. "@return id" Retorna: ID del proveedor.
             *
             * 3. Obtiene el nombre del proveedor.
             * 4. "@return nombre" Retorna: Nombre del proveedor.
             *
             * 5. Obtiene el código de identificación fiscal (CIF) del proveedor.
             * 6. "@return cif" Retorna: Código de identificación fiscal (CIF) del proveedor.
             *
             * 7. Obtiene el teléfono del proveedor.
             * 8. "@return telefono" Retorna: Teléfono del proveedor.
             *
             * 9. Obtiene el correo electrónico (email) de nacimiento del proveedor.
             * 10. "@return email" Retorna: Correo electrónico (email) del proveedor.
             *
             * 11. Obtiene la lista de productos del proveedor.
             *   (Es fundamental saber que si se accede fuera de una sesión de Hibernate y la carga es "LAZY", puede lanzar "LazyInitializationException").
             * 12. "@return productos" Retorna: Lista de productos del proveedor.
             */
    public Long getId(){ return id; }
    public String getNombre(){ return nombre; }
    public String getCif(){ return cif; }
    public String getTelefono(){ return telefono; }
    public String getEmail(){ return email; }
    
    public List<Producto> getProductos(){ return productos; }
    
    
    // ==================== SETTERS ====================
        // Crea los métodos "set".
            /*
             * 1. Establece el ID del proveedor.
             * 2. "@param id" Parámetro: ID del proveedor.
             *
             * 3. Establece el nombre del proveedor.
             * 4. "@param nombre" Parámetro: Nombre del proveedor.
             *
             * 5. Establece el código de identificación fiscal (CIF) del proveedor.
             * 6. "@param cif" Parámetro: Código de identificación fiscal (CIF) del proveedor.
             *
             * 7. Establece el teléfono del proveedor.
             * 8. "@param telefono" Parámetro: Teléfono del proveedor.
             *
             * 9. Establece el correo electrónico (email) del proveedor.
             * 10. "@param email" Parámetro: Correo electrónico (email) del proveedor.
             *
             * 11. Establece la lista de productos del proveedor.
             *   (Es fundamental usar "addProducto()" y "removeProducto()" para mantener la consistencia bidireccional automáticamente).
             * 12. "@param Lista" Retorna: Lista de productos del proveedor.
             */
    public void setId(Long id){ this.id = id; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    public void setCif(String cif){ this.cif = cif; }
    public void setTelefono(String telefono){ this.telefono = telefono; }
    public void setEmail(String email){ this.email = email; }
    
    public void setProductos(List<Producto> productos){ this.productos = productos; }
    
    
    // ==================== TOSTRING ====================
        // Crea el método "toString".
    @Override
    public String toString(){
        return "\n\t\tProveedor {ID: (" + id + ")}{"
             + "\n\t\t\tNombre:" + nombre
             + "\n\t\t\tCódigo de identificación fiscal (CIF):" + cif
             + "\n\t\t\tTeléfono:" + telefono
             + "\n\t\t\tCorreo electrónico (email) : " + email
             + "\n\t\t\tNº de productos:" + (productos != null ? productos.size() : 0)
             + "\n\t\t" + '}';
    }
}