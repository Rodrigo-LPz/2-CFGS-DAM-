/**
 *
 * @author Rodrigo
 */
package com.dam.modelo;


// Importa de todos los paquetes de la biblioteca/librería "LocalDate".
import java.time.LocalDate;
// Importa todos los paquetes de la biblioteca/librería "persistence".
import javax.persistence.*;

// Definimos una entidad que representará a un lote de productos en el supermercado.
    /*
     * 1. Primero mapea a la tabla "lotes" en la base de datos.
     * 2. Despues define una relación donde/en la que un lote pertenece a un único producto, es decir, relación muchos a uno, muchos lotes-un producto: (N:1).
     * 3. Y por último se incluye "enum EstadoLote" para controlar el estado del lote.
     */
@Entity
@Table(name = "lotes")

// Crea la clase "Producto".
public class Lote{
    // ==================== ATRIBUTOS ====================
        // Declara las variables, atributos.
            // (Sustituye al archivo "Lote.hbm.xml").
                /*
                 * 1. Se genera el identificador, "ID", único (clave primaria) de "lote".
                 * 2. Se genera automáticamente mediante estrategia "IDENTITY".
                 * 3. Se 'ancla'/enlaza a la columna de nombre "id_lote".
                 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Long id; /* Utilizo "Long" (clase envolvente) en vez de "long" (tipo primitivo) ya que Hibernate necesita saber si el objeto (lote) ya tiene un valor de (id) asignado o si debe generarlo automáticamente. Al usar tipos primitivos su valor por defecto será (0) confundiéndo a Hibernate y haciéndole pensar que ya tiene un valor (id) asignado. Por el contrario, al usar una clase envolvente, como es el caso, es valor inicial por defecto es (null), de esta forma Hibernate sabrá que si recibe o lee un valor nulo deberá generar uno nuevo antes/al guardar el objeto. */
    
                /*
                 * 1. Atributo para el código del lote.
                 * 2. Se 'ancla'/enlaza a la columna de nombre "codigo", añadiéndole atributos de campo único ("unique"), de campo obligatorio ("nullable") y de máximo 50 caracteres ("length").
                 */
    @Column(name = "codigo", unique = true, nullable = false, length = 50)
    private String codigo;

                /*
                 * Atributo para la ubicación del lote.
                 * Se 'ancla'/enlaza a la columna de nombre "cantidad", añadiéndole el atributo de campo obligatorio ("nullable").
                 * Al no anclar a la columna "cantidad" el atributo "length" el campo queda sin un límite máximo de caracteres.
                 */
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

                /*
                 * Atributo para la fecha de publicación del lote.
                 * Se 'ancla'/enlaza a la columna de nombre "fecha_caducidad", añadiéndole el atributo de campo obligatorio ("nullable").
                 * Al no anclar a la columna "fecha_caducidad" el atributo "length" el campo queda sin un límite máximo de caracteres.
                 */
    @Column(name = "fecha_caducidad", nullable = false)
    private LocalDate fechaCaducidad;
    
                /*
                 * Atributo para el estado de disponibilidad del lote.
                 * Se mapea como un "enum" (tipo enumerado) usando "STRING" y se almacena el nombre del "enum" en la base de datos.
                 * Se 'ancla'/enlaza a la columna de nombre "estado", añadiéndole atributos de campo obligatorio ("nullable") y de máximo 20 caracteres ("length").
                 *
                 * Los posibles valores del "enum" a utilizar son:
                 *   El valor "  DISPONIBLE "   Indica que el lote está disponible.
                 *   El valor "  VENDIDO    "   Indica que el lote no está disponible, vendido.
                 *   El valor "  CADUCADO   "   Indica que el lote está inservible, caducado.
                 *   El valor "  RETIRADO   "   Indica que el lote está dado de baja, retirado del mercado.
                 */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoLote estado;
    
    
    // ==================== RELACIONES ====================
        /*
         * Hacemos una relación para la lista de lotes que corresponden al mismo producto. Es decir, una relación entre la lista de lotes, los lotes, y el producto al que pertenecen/corresponden.
         * 
         * Hacemos una relación (N:1) bidireccional con "Lote".
         *   El atributo "  fetch = FetchType.LAZY  " hace/aplica una carga perezosa retrasando la carga de contenido hasta que sea necesario, consiguiendo optimizar el rendimiento de recursos y tiempo.
         */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;
    
    
    // ==================== CONSTRUCTORES ====================
        // Crea el constructor vacío, sin parámetros. Es necesario para que "JPA" pueda crear las instancias.
    public Lote(){}
    
        // Crea el constructor de/para las variables, con todos los campos excepto el "ID" y relaciones.
            /*
             * "@param titulo"              Parámetro: Título del lote.
             * "@param isbn"                Parámetro: ISBN (código) del lote.
             * "@param fechaPublicacion"    Parámetro: Fecha de publicacion del lote.
             * "@param numeroPaginas"       Parámetro: Número de Páginas del lote.
             */
    public Lote(String codigo, Integer cantidad, LocalDate fechaCaducidad, EstadoLote estado){
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.fechaCaducidad = fechaCaducidad;
        this.estado = estado;
    }
    
    
    // ==================== GETTERS ====================
        // Crea los métodos "get".
            /*
             * 1. Obtiene el ID del lote.
             * 2. "@return id" Retorna: ID del lote.
             *
             * 3. Obtiene el código del lote.
             * 4. "@return codigo" Retorna: Código del lote.
             *
             * 5. Obtiene la cantidad del lote.
             * 6. "@return cantidad" Retorna: Cantidad del lote.
             *
             * 7. Obtiene la ubicación del lote.
             * 8. "@return ubicacion" Retorna: Ubicación del lote.
             *
             * 9. Obtiene el producto (al que va a corresponder el lote) de "Producto.java" del lote.
             *   (Es fundamental saber que si se accede fuera de una sesión de Hibernate y la carga es "LAZY", puede lanzar "LazyInitializationException").
             * 10. "@return producto" Retorna: Producto correspondiende al/del lote.
             */
    public Long getId(){ return id; }
    public String getCodigo(){ return codigo; }
    public Integer getCantidad(){ return cantidad; }
    public LocalDate getFechaCaducidad(){ return fechaCaducidad; }
    public EstadoLote getEstado(){ return estado; }
    
    public Producto getProducto(){ return producto; }
    
    
    
    // ==================== SETTERS ====================
        // Crea los métodos "set".
            /*
             * 1. Establece el ID del lote.
             * 2. "@param id" Parámetro: ID del lote.
             *
             * 3. Establece el código del lote.
             * 4. "@param codigo" Parámetro: Código del lote.
             *
             * 5. Establece el estado del lote.
             * 6. "@param estado" Parámetro: Estado del lote.
             *
             * 7. Establece la ubicación del lote.
             * 8. "@param ubicacion" Parámetro: Ubicación del lote.
             *
             * 9. Establece el producto (al que va a corresponder el lote) de "Producto.java" del lote.
             * 10. "@param producto" Retorna: Producto correspondiende al/del lote.
             */
    public void setId(Long id){ this.id = id; }
    public void setCodigo(String codigo){ this.codigo = codigo; }
    public void setCantidad(Integer cantidad){ this.cantidad = cantidad; }
    public void setFechaCaducidad(LocalDate fechaCaducidad){ this.fechaCaducidad = fechaCaducidad; }
    public void setEstado(EstadoLote estado){ this.estado = estado; }
    
    public void setProducto(Producto producto){ this.producto = producto; }
    
    
    // ==================== TOSTRING ====================
        // Crea el método "toString".
    @Override
    public String toString(){
        return "\n\t\tLote {ID: (" + id + ")}{"
             + "\n\t\t\tCódigo del lote:" + codigo
             + "\n\t\t\tCantidad:" + cantidad
             + "\n\t\t\tFecha de caducidad:" + fechaCaducidad
             + "\n\t\t\tEstado:" + estado
             + "\n\t\t\tNº de productos:" + (producto != null ? producto.getNombre() : "N/A (Not Available)") /* Si el lote tiene un producto asociado "producto != null", muestra su título. Pero si no tiene "producto == null", muestra "N/A" para evitar errores. */
             + "\n\t\t" + '}';
    }
    
    
    // ==================== ENUM ====================
        // Crea un método de enumeración para definir los posibles estados de un lote (en la biblioteca). Cada valor representa una situación del propio lote.
            /*
             * Atributo para el estado de disponibilidad del lote. Los posibles valores del "enum" a utilizar son:
             *   El valor "  DISPONIBLE "   Indica que el lote está disponible.
             *   El valor "  VENDIDO    "   Indica que el lote no está disponible, vendido.
             *   El valor "  CADUCADO   "   Indica que el lote está inservible, caducado.
             *   El valor "  RETIRADO   "   Indica que el lote está dado de baja, retirado del mercado.
             */
   public enum EstadoLote{
        DISPONIBLE,
        VENDIDO,
        CADUCADO,
        RETIRADO
    }
}
