/**
 *
 * @author Rodrigo
 */
package com.mycompany.ejercicio_6;


// Crea la clase "Juagdor".
public class Jugador {
  // Declara las variables privadas del método.
    private String nombre;
    private int edad;
    private double altura;

    // Crea el constructor del método con sus respectivas variables.
    public Jugador(){

    }

    // Crea las funciones "get".
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public double getAltura() { return altura; }

    // Crea las funciones "set".
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setAltura(double altura) { this.altura = altura; }

    // Crea la función "toString".
    @Override
    public String toString(){
        return "Jugador{" +
                "nombre: " + nombre + "." + 
                "edad: " + edad + "." +
                "altura: " + altura + "." +
                '}';
    }  
}
