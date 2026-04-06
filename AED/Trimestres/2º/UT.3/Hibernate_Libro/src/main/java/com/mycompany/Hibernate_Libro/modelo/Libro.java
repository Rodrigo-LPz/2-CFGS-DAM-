/**
 *
 * @author Rodrigo
 */
package com.mycompany.Hibernate_Libro.modelo;


// Crea la clase "Empleado".
public class Libro{
    // Variable 'objeto' que sirve para llamar/enlazarse con la clase "Autor.java".
    private Autor autor;
    
    
    // Declara las variables, atributos.
    private long id;
    private String titulo;
    private int anioPublicacion;
    private String isbn;
    
    
    // Crea el constructor vacío.
    public Libro(){}
    
    
    // Crea el constructor de/para las variables.
    public Libro(Autor autor, long id, String titulo, int anioPublicacion, String isbn){
        this.autor = autor;
        
        this.id = id;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.isbn = isbn;
    }
    
    
    // Crea los métodos "get".
    public Autor getAutor(){ return autor; }    
    
    public long getId(){ return id; }
    public String getTitulo(){ return titulo; }
    public int getAnioPublicacion(){ return anioPublicacion; }
    public String getIsbn(){ return isbn; }
    
    
    // Crea los métodos "set".
    public void setAutor(Autor autor) { this.autor = autor; }
    
    public void setId(long id){ this.id = id; }
    public void setTitulo(String titulo){ this.titulo = titulo; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    
    // Crea el método "toString".
    @Override
    public String toString(){
        return "\n\t\tLibro{"
             + "\n\t\t\tID: " + id
             + "\n\t\t\tTitulo: " + titulo
             + "\n\t\t\tAutor : " + autor
             + "\n\t\t\tAño de Publicacion: " + anioPublicacion
             + "\n\t\t\tISBN: " + isbn
             + "\n\t\t" + '}';
    }
}