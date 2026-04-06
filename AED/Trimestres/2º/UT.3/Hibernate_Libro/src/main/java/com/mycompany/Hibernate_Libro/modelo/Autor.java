/**
 *
 * @author Rodrigo
 */
package com.mycompany.Hibernate_Libro.modelo;


// Crea la clase "Departamento".
public class Autor{
    // Declara las variables, atributos.
    private long id;
    private String nombre;
    private String nacionalidad;
    
    
    // Crea el constructor vacío.
    public Autor(){}
    
    
    // Crea el constructor de/para las variables.
    public Autor(long id, String nombre, String nacionalidad){
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }
    
    
    // Crea los métodos "get".
    public long getId(){ return id; }
    public String getNombre(){ return nombre; }
    public String getNacionalidad(){ return nacionalidad; }
    
    
    // Crea los métodos "set".
    public void setId(long id){ this.id = id; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    public void setNacionalidad(String nacionalidad){ this.nacionalidad = nacionalidad; }
    
    
    // Crea el método "toString".
    @Override
    public String toString(){
        return "\n\t\tAutor{"
             + "\n\t\t\t\tID: " + id
             + "\n\t\t\t\tNombre: " + nombre
             + "\n\t\t\t\tNacionalidad: " + nacionalidad
             + "\n\t\t" + '}';
    }
}