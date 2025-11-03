/**
 *
 * @author Rodrigo
 */
package com.mycompany.Hibernate_ej.modelo;


// Crea la clase "Departamento".
public class Departamento{
    // Declara las variables, atributos.
    private long id;
    private String nombre;
    
    // Crea el constructor vacío.
    public Departamento(){}
    
    // Crea el constructor de/para las variables.
    public Departamento(long id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
    
    // Crea los métodos "get".
    public long getId(){ return id; }
    public String getNombre(){ return nombre; }
    
    // Crea los métodos "set".
    public void setId(long id){ this.id = id; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    
    // Crea el método "toString".
    @Override
    public String toString(){
        return "\n\t\t\tDepartamento{"
             + "\n\t\t\t\tID: " + id
             + "\n\t\t\t\tNombre: " + nombre
             + "\n\t\t\t" + '}';
    }
}