/**
 *
 * @author Rodrigo
 */
package com.mycompany.multas;


// Crea la clase "Pago"
public class Pago{
    // Declara tres variables privadas.
    private final double importePagado;
    private final String matricula;
    private final String descripcion;
    
    // Crea el constructor del método para las variables privadas creadas anteriormente.
    public Pago(double importePagado, String matricula, String descripcion){
        this.importePagado = importePagado;
        this.matricula = matricula.toUpperCase();
        this.descripcion = descripcion;
    }
    
    // Crea los métodos "get" para obtener el valor guardado de dichas variables.
    public double getImportePagado(){ return importePagado; }    public String getMatricula(){ return matricula; }
    public String getDescripcion(){ return descripcion; }
    
    // Crea el método "toString" para sobrescribir dichas variables.
    @Override
    public String toString(){
        return descripcion + " - Importe: " + String.format("%.2f", importePagado) + "€";
    }
}