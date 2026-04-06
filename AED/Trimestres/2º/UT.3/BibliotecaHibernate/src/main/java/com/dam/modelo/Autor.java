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

// Definimos una entidad que representará a un autor de libros en la biblioteca.
    /*
     * 1. Primero mapea a la tabla "autores" en la base de datos.
     * 2. Despues define una relación donde/en la que un autor puede escribir múltiples libros, es decir, relación uno a muchos, un autor-muchos libros: (1:N).
     */
@Entity
@Table(name = "autores")

// Crea la clase "Autor".
public class Autor{
    // ==================== ATRIBUTOS ====================
        // Declara las variables, atributos.
            // (Sustituye al archivo "Autor.hbm.xml").
                /*
                 * 1. Se genera el identificador, "ID", único (clave primaria) de "autor".
                 * 2. Se genera automáticamente mediante estrategia "IDENTITY".
                 * 3. Se 'ancla'/enlaza a la columna de nombre "id_autor".
                 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Long id; /* Utilizo "Long" (clase envolvente) en vez de "long" (tipo primitivo) ya que Hibernate necesita saber si el objeto (autor) ya tiene un valor de (id) asignado o si debe generarlo automáticamente. Al usar tipos primitivos su valor por defecto será (0) confundiéndo a Hibernate y haciéndole pensar que ya tiene un valor (id) asignado. Por el contrario, al usar una clase envolvente, como es el caso, es valor inicial por defecto es (null), de esta forma Hibernate sabrá que si recibe o lee un valor nulo deberá generar uno nuevo antes/al guardar el objeto. */
    
                /*
                 * 1. Atributo para el nombre del autor.
                 * 2. Se 'ancla'/enlaza a la columna de nombre "nombre", añadiéndole atributos de campo obligatorio ("nullable") y de máximo 100 caracteres ("length").
                 */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

                /*
                 * Atributo para los apellidos del autor.
                 * Se 'ancla'/enlaza a la columna de nombre "apellidos", añadiéndole atributos de campo obligatorio ("nullable") y de máximo 100 caracteres ("length").
                 */
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

                /*
                 * Atributo para la nacionalidad del autor.
                 * Se 'ancla'/enlaza a la columna de nombre "nacionalidad", añadiéndole el atributo de máximo 50 caracteres ("length").
                 * Al no anclar a la columna "nacionalidad" el atributo "nullable" el campo queda opcional, en vez de obligatorio.
                 */
    @Column(name = "nacionalidad", length = 50)
    private String nacionalidad;

                /*
                 * Atributo para la fecha de nacimiento del autor.
                 * Se 'ancla'/enlaza a la columna de nombre "fecha_nacimiento".
                 * Al no anclar a la columna "fecha_nacimiento" los atributos "nullable" y "length" el campo queda opcional, en vez de obligatorio; y sin un límite máximo de caracteres.
                 */
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    
    // ==================== RELACIONES ====================
        /*
         * Hacemos una relación para la lista de libros escritos por el autor. Es decir, una relación entre la lista de libros, los libros, y el autor que los escribió.
         * 
         * Hacemos una relación (1:N) bidireccional con "Libro".
         *   El atributo "  mappedBy = "autor"           " indica que "Libro" es el propietario de la relación.
         *   El atributo "  cascade = CascadeType.ALL    " refire a que todas las operaciones se propagan a los libros.
         *   El atributo "  orphanRemoval = true         " elimina automáticamente libros huérfanos.
         *   El atributo "  fetch = FetchType.LAZY       " hace/aplica una carga perezosa retrasando la carga de contenido hasta que sea necesario, consiguiendo optimizar el rendimiento de recursos y tiempo.
         * 
         * Definimos la cascada aplicada ("cascade = CascadeType.ALL").
         *   El atributo "  PERSIST " Aplica que al guardar un autor, guarda sus libros.
         *   El atributo "  MERGE   " Aplica que al actualizar un autor, actualiza sus libros.
         *   El atributo "  REMOVE  " Aplica que al eliminar un autor, elimina sus libros.
         *   El atributo "  REFRESH " Aplica que al refrescar un autor, refresca sus libros.
         *   El atributo "  DETACH  " Aplica que al desasociar un autor, desasocia sus libros.
         */
    @OneToMany(mappedBy = "autor", 
               cascade = CascadeType.ALL, 
               orphanRemoval = true, 
               fetch = FetchType.LAZY)
    private List<Libro> libros = new ArrayList<>();
    
    
    // ==================== CONSTRUCTORES ====================
        // Crea el constructor vacío, sin parámetros. Es necesario para que "JPA" pueda crear las instancias.
    public Autor(){}
    
        // Crea el constructor de/para las variables, con todos los campos excepto el "ID" y relaciones.
            /*
             * "@param nombre"          Parámetro: Nombre del autor.
             * "@param apellidos"       Parámetro: Apellidos del autor.
             * "@param nacionalidad"    Parámetro: Nacionalidad del autor.
             * "@param fechaNacimiento" Parámetro: Fecha de nacimiento del autor.
             */
    public Autor(String nombre, String apellidos, String nacionalidad, LocalDate fechaNacimiento){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    
    // ==================== MÉTODOS HELPER ====================
    
    /*
     * Añade un libro al autor manteniendo la consistencia bidireccional.
     * 
     * Este método es fundamental para mantener la integridad de la relación:
     *   1. Añade el libro a la lista de libros del autor.
     *   2. Añade el autor al libro (lado opuesto de la relación).
     *
     *      Ejemplo de uso:
     *         Autor autor = new Autor("Gabriel", "García Márquez", "Colombiana", ...);
     *         Libro libro = new Libro("Cien años de soledad", "978-84-376-0494-7", ...);
     *         autor.addLibro(libro);   → Permite que se mantenga la consistencia entre/en ambos lados.
     * 
     * "@param libro" Parámetro: Libro a añadir al autor.
     */
    public void addLibro(Libro libro){
        libros.add(libro);
        libro.setAutor(this);
    }
    
    /*
     * Elimina un libro del autor manteniendo la consistencia bidireccional.
     * 
     * Este método es fundamental para mantener la integridad de la relación:
     *   1. Elimina el libro de la lista de libros del autor.
     *   2. Elimina el autor (su referencia) del libro (lado opuesto).
     * 
     * Si "orphanRemoval = true" (está activado), Hibernate eliminará el libro de la base de datos automáticamente.
     * 
     *      Ejemplo de uso:
     *         Libro libro = autor.getLibros().get(0);
     *         autor.removeLibro(libro);    → El libro será eliminado de la BD.
     * 
     * "@param libro" Parámetro: Libro a eliminar del autor.
     */
    public void removeLibro(Libro libro){
        libros.remove(libro);
        libro.setAutor(null);
    }
    
    
    // ==================== GETTERS ====================
        // Crea los métodos "get".
            /*
             * 1. Obtiene el ID del autor.
             * 2. "@return id" Retorna: ID del autor.
             *
             * 3. Obtiene el nombre del autor.
             * 4. "@return nombre" Retorna: Nombre del autor.
             *
             * 5. Obtiene los apellidos del autor.
             * 6. "@return apellidos" Retorna: Apellidos del autor.
             *
             * 7. Obtiene la nacionalidad del autor.
             * 8. "@return nacionalidad" Retorna: Nacionalidad del autor.
             *
             * 9. Obtiene la fecha de nacimiento del autor.
             * 10. "@return fechaNacimiento" Retorna: Fecha de nacimiento del autor.
             *
             * 11. Obtiene la lista de libros del autor.
             *   (Es fundamental saber que si se accede fuera de una sesión de Hibernate y la carga es "LAZY", puede lanzar "LazyInitializationException").
             * 12. "@return libros" Retorna: Lista de libros del autor.
             */
    public Long getId(){ return id; }
    public String getNombre(){ return nombre; }
    public String getApellidos(){ return apellidos; }
    public String getNacionalidad(){ return nacionalidad; }
    public LocalDate getFechaNacimiento(){ return fechaNacimiento; }
    
    public List<Libro> getLibros(){ return libros; }
    
    
    // ==================== SETTERS ====================
        // Crea los métodos "set".
            /*
             * 1. Establece el ID del autor.
             * 2. "@param id" Parámetro: ID del autor.
             *
             * 3. Establece el nombre del autor.
             * 4. "@param nombre" Parámetro: Nombre del autor.
             *
             * 5. Establece los apellidos del autor.
             * 6. "@param apellidos" Parámetro: Apellidos del autor.
             *
             * 7. Establece la nacionalidad del autor.
             * 8. "@param nacionalidad" Parámetro: Nacionalidad del autor.
             *
             * 9. Establece la fecha de nacimiento del autor.
             * 10. "@param fechaNacimiento" Parámetro: Fecha de nacimiento del autor.
             *
             * 11. Establece la lista de libros del autor.
             *   (Es fundamental usar "addLibro()" y "removeLibro()" para mantener la consistencia bidireccional automáticamente).
             * 12. "@param Lista" Retorna: Lista de libros del autor.
             */
    public void setId(Long id){ this.id = id; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    public void setApellidos(String apellidos){ this.apellidos = apellidos; }
    public void setNacionalidad(String nacionalidad){ this.nacionalidad = nacionalidad; }
    public void setFechaNacimiento(LocalDate fechaNacimiento){ this.fechaNacimiento = fechaNacimiento; }
    
    public void setLibros(List<Libro> libros){ this.libros = libros; }
    
    
    // ==================== TOSTRING ====================
        // Crea el método "toString".
    @Override
    public String toString(){
        return "\n\t\tAutor {ID: (" + id + ")}{"
             + "\n\t\t\tNombre:" + nombre
             + "\n\t\t\tApellidos:" + apellidos
             + "\n\t\t\tNacionalidad:" + nacionalidad
             + "\n\t\t\tFecha de Nacimiento: " + fechaNacimiento
             + "\n\t\t\tNº Libros:" + (libros != null ? libros.size() : 0)
             + "\n\t\t" + '}';
    }
}