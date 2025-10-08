/**
 *
 * @author Rodrigo
 */
package com.mycompany.multas;


// Crea la clase "Multas" que se extiende de la clase "Vehiculo".
public class Vehiculo{
    // Declara una variable protegida.
    protected String matricula;
    
    // Crea el constructor del método para la variable protegida creada anteriormente.
    public Vehiculo(String matricula){
        this.matricula = matricula;
    }
    
    // Crea el método "get" para obtener el valor guardado de dicha variable.
    public String getMatricula(){ return matricula; }
}