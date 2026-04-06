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

// Definimos una entidad que representará a un libro correspondiente de ejemplares en la biblioteca.
    /*
     * 1. Primero mapea a la tabla "libros" en la base de datos.
     * 2. Despues define una relación donde/en la que un libro puede corresponder a múltiples ejemplares, es decir, relación uno a muchos, un libro-muchos ejemplares: (1:N).
     */
@Entity
@Table(name = "libros")

// Crea la clase "Libro".
public class Libro{
    // ==================== ATRIBUTOS ====================
        // Declara las variables, atributos.
            // (Sustituye al archivo "Libro.hbm.xml").
                /*
                 * 1. Se genera el identificador, "ID", único (clave primaria) de "libro".
                 * 2. Se genera automáticamente mediante estrategia "IDENTITY".
                 * 3. Se 'ancla'/enlaza a la columna de nombre "id_libro".
                 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long id; /* Utilizo "Long" (clase envolvente) en vez de "long" (tipo primitivo) ya que Hibernate necesita saber si el objeto (libro) ya tiene un valor de (id) asignado o si debe generarlo automáticamente. Al usar tipos primitivos su valor por defecto será (0) confundiéndo a Hibernate y haciéndole pensar que ya tiene un valor (id) asignado. Por el contrario, al usar una clase envolvente, como es el caso, es valor inicial por defecto es (null), de esta forma Hibernate sabrá que si recibe o lee un valor nulo deberá generar uno nuevo antes/al guardar el objeto. */
    
                /*
                 * 1. Atributo para el título del libro.
                 * 2. Se 'ancla'/enlaza a la columna de nombre "titulo", añadiéndole atributos de campo obligatorio ("nullable") y de máximo 200 caracteres ("length").
                 */
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

                /*
                 * Atributo para el código ISBN del libro.
                 * Se 'ancla'/enlaza a la columna de nombre "isbn", añadiéndole atributos de campo único ("unique"), de campo obligatorio ("nullable") y de máximo 20 caracteres ("length").
                 */
    @Column(name = "isbn", unique = true, nullable = false, length = 20)
    private String isbn;

                /*
                 * Atributo para la fecha de publicación del libro.
                 * Se 'ancla'/enlaza a la columna de nombre "fecha_publicacion".
                 * Al no anclar a la columna "fecha_publicacion" los atributos "nullable" y "length" el campo queda opcional, en vez de obligatorio; y sin un límite máximo de caracteres.
                 */
    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

                /*
                 * Atributo para el número de páginas del libro.
                 * Se 'ancla'/enlaza a la columna de nombre "numero_paginas".
                 * Al no anclar a la columna "numero_paginas" los atributos "nullable" y "length" el campo queda opcional, en vez de obligatorio; y sin un límite máximo de caracteres.
                 */
    @Column(name = "numero_paginas")
    private int numeroPaginas;
    
    
    // ==================== RELACIONES ====================
        /*
         * Hacemos una relación para la lista de libros pertenecientes al/escritos por el mismo autor. Es decir, una relación entre la lista de libros, los libros, y el autor al que pertenecen/corresponden.
         * 
         * Hacemos una relación (N:1) bidireccional con "Ejemplar".
         *   El atributo "  fetch = FetchType.LAZY       " hace/aplica una carga perezosa retrasando la carga de contenido hasta que sea necesario, consiguiendo optimizar el rendimiento de recursos y tiempo.
         * 
         * Se 'ancla'/enlaza a la columna de nombre "id_autor" de la tabla "libros".
         */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Autor autor;
    
        /*
         * Hacemos una relación para la lista de ejemplares corresponden al mismo libro. Es decir, una relación entre la lista de ejemplares, los ejemplares, y el libro al que pertenecen/corresponden.
         * 
         * Hacemos una relación (1:N) bidireccional con "Ejemplar".
         *   El atributo "  mappedBy = "libro"           " indica que "Ejemplar" es el propietario de la relación.
         *   El atributo "  cascade = CascadeType.ALL    " refire a que todas las operaciones se propagan a los ejemplares.
         *   El atributo "  orphanRemoval = true         " elimina automáticamente ejemplares huérfanos.
         *   El atributo "  fetch = FetchType.LAZY       " hace/aplica una carga perezosa retrasando la carga de contenido hasta que sea necesario, consiguiendo optimizar el rendimiento de recursos y tiempo.
         * 
         * Definimos la cascada aplicada ("cascade = CascadeType.ALL").
         *   El atributo "  PERSIST " Aplica que al guardar un libro, guarda sus ejemplares.
         *   El atributo "  MERGE   " Aplica que al actualizar un libro, actualiza sus ejemplares.
         *   El atributo "  REMOVE  " Aplica que al eliminar un libro, elimina sus ejemplares.
         *   El atributo "  REFRESH " Aplica que al refrescar un libro, refresca sus ejemplares.
         *   El atributo "  DETACH  " Aplica que al desasociar un libro, desasocia sus ejemplares.
         */
    @OneToMany(mappedBy = "libro", 
           cascade = CascadeType.ALL, 
           orphanRemoval = true, 
           fetch = FetchType.LAZY)
    private List<Ejemplar> ejemplares = new ArrayList<>();
    
    
    // ==================== CONSTRUCTORES ====================
        // Crea el constructor vacío, sin parámetros. Es necesario para que "JPA" pueda crear las instancias.
    public Libro(){}
    
        // Crea el constructor de/para las variables, con todos los campos excepto el "ID" y relaciones.
            /*
             * "@param titulo"              Parámetro: Título del libro.
             * "@param isbn"                Parámetro: ISBN (código) del libro.
             * "@param fechaPublicacion"    Parámetro: Fecha de publicacion del libro.
             * "@param numeroPaginas"       Parámetro: Número de Páginas del libro.
             */
    public Libro(String titulo, String isbn, LocalDate fechaPublicacion, int numeroPaginas){
        this.titulo = titulo;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.numeroPaginas = numeroPaginas;
    }
    
    
    // ==================== MÉTODOS HELPER ====================
    
    /*
     * Añade un ejemplar al libro manteniendo la consistencia bidireccional.
     * 
     * Este método es fundamental para mantener la integridad de la relación:
     *   1. Añade el ejemplar a la lista de ejempalres del libro.
     *   2. Añade el libro al ejemplar (lado opuesto de la relación).
     *
     *      Ejemplo de uso:
     *         Libro libro = new Libro("Cien años de soledad", "978-84-376-0494-7", ...);
     *         Ejemplar ejemplar = new Ejemplar("EJ-001-2024", "EstadoEjemplar.DISPONIBLE", "Estantería A-12");
     *         libro.addEjemplar(ejemplar); → Permite que se mantenga la consistencia entre/en ambos lados.
     * 
     * "@param libro" Parámetro: Libro a añadir al autor.
     */
    public void addEjemplar(Ejemplar ejemplar){
        ejemplares.add(ejemplar);
        ejemplar.setLibro(this);
    }
    
    /*
     * Elimina un ejemplar del libro manteniendo la consistencia bidireccional.
     * 
     * Este método es fundamental para mantener la integridad de la relación:
     *   1. Elimina el ejemplar de la lista de ejemplares del libro.
     *   2. Elimina el libro (su referencia) del ejemplar (lado opuesto).
     * 
     * Si "orphanRemoval = true" (está activado), Hibernate eliminará el ejemplar de la base de datos automáticamente.
     * 
     *      Ejemplo de uso:
     *         Ejemplar ejemplar = libro.getEjemplares().get(0);
     *         libro.removeEjemplar(ejemplar);  → El ejemplar será eliminado de la BD.
     * 
     * "@param libro" Parámetro: Libro a eliminar del autor.
     */
    public void removeEjemplar(Ejemplar ejemplar){
        ejemplares.remove(ejemplar);
        ejemplar.setLibro(null);
    }
    
    
    // ==================== GETTERS ====================
        // Crea los métodos "get".
            /*
             * 1. Obtiene el ID del libro.
             * 2. "@return id" Retorna: ID del libro.
             *
             * 3. Obtiene el título del libro.
             * 4. "@return titulo" Retorna: Título del libro.
             *
             * 5. Obtiene el código ISBN del libro.
             * 6. "@return isbn" Retorna: ISBN (código) del libro.
             *
             * 7. Obtiene la fecha de publicación del libro.
             * 8. "@return fechaPublicacion" Retorna: Fecha de publicación del libro.
             *
             * 9. Obtiene el número de paginas del libro.
             * 10. "@return numeroPaginas" Retorna: Número de paginas del libro.
             *
             * 11. Obtiene el autor del libro.
             * 12. "@return autor" Retorna: Autor del libro.
             *
             * 13. Obtiene la lista de ejemplares del libro.
             *   (Es fundamental saber que si se accede fuera de una sesión de Hibernate y la carga es "LAZY", puede lanzar "LazyInitializationException").
             * 14. "@return ejemplares" Retorna: Lista de ejemplares del libro.
             */
    public Long getId(){ return id; }
    public String getTitulo(){ return titulo; }
    public String getIsbn(){ return isbn; }
    public LocalDate getFechaPublicacion(){ return fechaPublicacion; }
    public int getNumeroPaginas(){ return numeroPaginas; }
    
    public Autor getAutor(){ return autor; }
    
    public List<Ejemplar> getEjemplares(){
        return ejemplares; }
    
    
    
    // ==================== SETTERS ====================
        // Crea los métodos "set".
            /*
             * 1. Establece el ID del libro.
             * 2. "@param id" Parámetro: ID del libro.
             *
             * 3. Establece el título del libro.
             * 4. "@param titulo" Parámetro: Título del libro.
             *
             * 5. Establece el código ISBN del libro.
             * 6. "@param isbn" Parámetro: ISBN (código) del libro.
             *
             * 7. Establece la fecha de publicación del libro.
             * 8. "@param fechaPublicacion" Parámetro: Fecha de publicación del libro.
             *
             * 9. Establece el número de paginas del libro.
             * 10. "@param numeroPaginas" Parámetro: Número de paginas del libro.
             *
             * 11. Establece el autor del libro.
             * 12. "@param autor" Retorna: Autor del libro.
             *
             * 13. Establece la lista de ejempalres del libro.
             *   (Es fundamental usar "addEjemplar()" y "removeEjemplar()" para mantener la consistencia bidireccional automáticamente).
             * 14. "@param Lista" Retorna: Lista de ejemplares del libro.
             */
    public void setId(Long id){ this.id = id; }
    public void setTitulo(String titulo){ this.titulo = titulo; }
    public void setIsbn(String isbn){ this.isbn = isbn; }
    public void setFechaPublicacion(LocalDate fechaPublicacion){ this.fechaPublicacion = fechaPublicacion; }
    public void setNumeroPaginas(int numeroPaginas){ this.numeroPaginas = numeroPaginas; }
    
    public void setAutor(Autor autor) { this.autor = autor; }
    
    public void setEjemplares(List<Ejemplar> ejemplares){ this.ejemplares = ejemplares; }
    
    
    // ==================== TOSTRING ====================
        // Crea el método "toString".
    @Override
    public String toString(){
        return "\n\t\tLibro {ID: (" + id + ")}{"
             + "\n\t\t\tTitulo:" + titulo
             + "\n\t\t\tISBN:" + isbn
             + "\n\t\t\tFecha de Publicación:" + fechaPublicacion
             + "\n\t\t\tNúmero de Páginas: " + numeroPaginas
             + "\n\t\t\tNº Ejemplares:" + (ejemplares != null ? ejemplares.size() : 0)
             + "\n\t\t" + '}';
    }
}