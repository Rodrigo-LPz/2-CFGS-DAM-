/**
 *
 * @author Rodrigo
 */
package com.mycompany.multas;


// Crea la clase "Multas" que se extiende de la clase "Vehiculo".
public class Multas extends Vehiculo{
    // Declara dos variables privadas.
    private final String descripcion;
    private final double importe;
    
    // Crea el constructor del método para las variables privadas creadas anteriormente y además de llamar/heredar el método "matrícula" de la superclase o clase padre "Vehiculo".
    public Multas(String descripcion, double importe, String matricula){
        super(matricula);
        this.descripcion = descripcion;
        this.importe = importe;
    }
    
    // Crea los métodos "get" para obtener el valor guardado de dichas variables.
    public String getDescripcion(){ return descripcion; }
    public double getImporteAPagar(){ return importe; }
    
    // Crea el método "toString" para sobrescribir dichas variables.
    @Override
    public String toString(){
        return descripcion + " - Importe: " + String.format("%.2f", importe) + "€";
    }
}