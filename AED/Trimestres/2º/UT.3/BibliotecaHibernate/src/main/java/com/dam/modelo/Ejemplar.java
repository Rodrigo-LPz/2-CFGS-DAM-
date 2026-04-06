/**
 *
 * @author Rodrigo
 */
package com.dam.modelo;


// Importa todos los paquetes de la biblioteca/librería "persistence".
import javax.persistence.*;

// Definimos una entidad que representará a un autor de libros en la biblioteca.
    /*
     * 1. Primero mapea a la tabla "ejemplares" en la base de datos.
     * 2. Despues define una relación donde/en la que un ejemplar pertenece a un único libro, es decir, relación muchos a uno, muchos ejemplares-un libro: (N:1).
     */
@Entity
@Table(name = "ejemplares")

// Crea la clase "Libro".
public class Ejemplar{
    // ==================== ATRIBUTOS ====================
        // Declara las variables, atributos.
            // (Sustituye al archivo "Ejemplar.hbm.xml").
                /*
                 * 1. Se genera el identificador, "ID", único (clave primaria) de "ejemplar".
                 * 2. Se genera automáticamente mediante estrategia "IDENTITY".
                 * 3. Se 'ancla'/enlaza a la columna de nombre "id_ejemplar".
                 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejemplar")
    private Long id; /* Utilizo "Long" (clase envolvente) en vez de "long" (tipo primitivo) ya que Hibernate necesita saber si el objeto (ejemplar) ya tiene un valor de (id) asignado o si debe generarlo automáticamente. Al usar tipos primitivos su valor por defecto será (0) confundiéndo a Hibernate y haciéndole pensar que ya tiene un valor (id) asignado. Por el contrario, al usar una clase envolvente, como es el caso, es valor inicial por defecto es (null), de esta forma Hibernate sabrá que si recibe o lee un valor nulo deberá generar uno nuevo antes/al guardar el objeto. */
    
                /*
                 * 1. Atributo para el código del ejemplar.
                 * 2. Se 'ancla'/enlaza a la columna de nombre "codigoEjemplar", añadiéndole atributos de campo único ("unique"), de campo obligatorio ("nullable") y de máximo 50 caracteres ("length").
                 */
    @Column(name = "codigo_ejemplar", unique = true, nullable = false, length = 50)
    private String codigoEjemplar;

                /*
                 * Atributo para el estado de disponibilidad del ejemplar.
                 * Se mapea como un "enum" (tipo enumerado) usando "STRING" y se almacena el nombre del "enum" en la base de datos.
                 * Se 'ancla'/enlaza a la columna de nombre "estado", añadiéndole el atributo de máximo 20 caracteres ("length").
                 * Al no anclar a la columna "estado" el atributo "nullable" el campo queda opcional, en vez de obligatorio.
                 *
                 * Los posibles valores del "enum" a utilizar son:
                 *   El valor "  DISPONIBLE "   Indica que el ejemplar está disponible.
                 *   El valor "  PRESTADO   "   Indica que el ejemplar está prestado.
                 *   El valor "  REPARACION "   Indica que el ejemplar está en reparación.
                 *   El valor "  BAJA       "   Indica que el ejemplar está dado de baja.
                 */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20)
    private EstadoEjemplar estado;

                /*
                 * Atributo para la ubicación del libro.
                 * Se 'ancla'/enlaza a la columna de nombre "ubicacion", añadiéndole el atributo de máximo 100 caracteres ("length").
                 * Al no anclar a la columna "ubicacion" el atributo "nullable" el campo queda opcional, en vez de obligatorio.
                 */
    @Column(name = "ubicacion", length = 100)
    private String ubicacion;
    
    
    // ==================== RELACIONES ====================
        /*
         * Hacemos una relación para la lista de ejemplares corresponden al mismo libro. Es decir, una relación entre la lista de ejemplares, los ejemplares, y el libro al que pertenecen/corresponden.
         * 
         * Hacemos una relación (N:1) bidireccional con "Ejemplar".
         *   El atributo "  fetch = FetchType.LAZY       " hace/aplica una carga perezosa retrasando la carga de contenido hasta que sea necesario, consiguiendo optimizar el rendimiento de recursos y tiempo.
         */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_libro")
    private Libro libro;
    
    
    // ==================== CONSTRUCTORES ====================
        // Crea el constructor vacío, sin parámetros. Es necesario para que "JPA" pueda crear las instancias.
    public Ejemplar(){}
    
        // Crea el constructor de/para las variables, con todos los campos excepto el "ID" y relaciones.
            /*
             * "@param titulo"              Parámetro: Título del libro.
             * "@param isbn"                Parámetro: ISBN (código) del libro.
             * "@param fechaPublicacion"    Parámetro: Fecha de publicacion del libro.
             * "@param numeroPaginas"       Parámetro: Número de Páginas del libro.
             */
    public Ejemplar(String codigoEjemplar, EstadoEjemplar estado, String ubicacion){
        this.codigoEjemplar = codigoEjemplar;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }
    
    
    // ==================== GETTERS ====================
        // Crea los métodos "get".
            /*
             * 1. Obtiene el ID del ejemplar.
             * 2. "@return id" Retorna: ID del ejemplar.
             *
             * 3. Obtiene el código del ejemplar.
             * 4. "@return codigoEjemplar" Retorna: Código del ejemplar.
             *
             * 5. Obtiene el estado del ejemplar.
             * 6. "@return estado" Retorna: Estado del ejemplar.
             *
             * 7. Obtiene la ubicación del ejemplar.
             * 8. "@return ubicacion" Retorna: Ubicación del ejemplar.
             *
             * 9. Obtiene el libro (al que va a corresponder el ejemplar) de "Libro.java" del ejemplar.
             *   (Es fundamental saber que si se accede fuera de una sesión de Hibernate y la carga es "LAZY", puede lanzar "LazyInitializationException").
             * 10. "@return libro" Retorna: Libro correspondiende al/del ejemplar.
             */
    public Long getId(){ return id; }
    public String getCodigoEjemplar(){ return codigoEjemplar; }
    public EstadoEjemplar getEstado(){ return estado; }
    public String getUbicacion(){ return ubicacion; }
    
    public Libro getLibro(){ return libro; }
    
    
    
    // ==================== SETTERS ====================
        // Crea los métodos "set".
            /*
             * 1. Establece el ID del ejemplar.
             * 2. "@param id" Parámetro: ID del ejemplar.
             *
             * 3. Establece el código del ejemplar.
             * 4. "@param codigoEjemplar" Parámetro: Código del ejemplar.
             *
             * 5. Establece el estado del ejemplar.
             * 6. "@param estado" Parámetro: Estado del ejemplar.
             *
             * 7. Establece la ubicación del ejemplar.
             * 8. "@param ubicacion" Parámetro: Ubicación del ejemplar.
             *
             * 9. Establece el libro (al que va a corresponder el ejemplar) de "Libro.java" del ejemplar.
             * 10. "@param libro" Retorna: Libro correspondiende al/del ejemplar.
             */
    public void setId(Long id){ this.id = id; }
    public void setCodigoEjemplar(String codigoEjemplar){ this.codigoEjemplar = codigoEjemplar; }
    public void setEstado(EstadoEjemplar estado){ this.estado = estado; }
    public void setUbicacion(String ubicacion){ this.ubicacion = ubicacion; }
    
    public void setLibro(Libro libro){ this.libro = libro; }
    
    
    // ==================== TOSTRING ====================
        // Crea el método "toString".
    @Override
    public String toString(){
        return "\n\t\tEjemplar {ID: (" + id + ")}{"
             + "\n\t\t\tCódigo del Ejemplar:" + codigoEjemplar
             + "\n\t\t\tEstado:" + estado
             + "\n\t\t\tUbicación:" + ubicacion
             + "\n\t\t\tLibro:" + (libro != null ? libro.getTitulo() : "N/A (Not Available)") /* Si el ejemplar tiene un libro asociado "libro != null", muestra su título. Pero si no tiene "libro == null", muestra "N/A" para evitar errores. */
             + "\n\t\t" + '}';
    }
    
    
    // ==================== ENUM ====================
        // Crea un método de enumeración para definir los posibles estados de un ejemplar (en la biblioteca). Cada valor representa una situación del propio ejemplar.
            /*
             * Atributo para el estado de disponibilidad del ejemplar. Los posibles valores del "enum" a utilizar son:
             *   El valor "  DISPONIBLE "   Indica que el ejemplar está disponible.
             *   El valor "  PRESTADO   "   Indica que el ejemplar está prestado.
             *   El valor "  REPARACION "   Indica que el ejemplar está en reparación.
             *   El valor "  BAJA       "   Indica que el ejemplar está dado de baja.
             */
   public enum EstadoEjemplar{
        DISPONIBLE, /**/
        PRESTADO,   /**/
        REPARACION, /**/
        BAJA        /**/
    }
}
