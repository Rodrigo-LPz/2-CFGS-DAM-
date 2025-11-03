/**
 *
 * @author Rodrigo
 */
package com.mycompany.billetesavion;


public class Vuelos{
    // Declara las variables privadas.
    private String aeropuerto_origen;
    private String aeropuerto_destino;
    private double precio_euros;
    private int duracion;
    private String linea_aerea;
    
    // Definimos el constructor.
    public Vuelos(String aeropuerto_origen, String aeropuerto_destino, double precio_euros, int duracion, String linea_aerea) {
        this.aeropuerto_origen = aeropuerto_origen;
        this.aeropuerto_destino = aeropuerto_destino;
        this.precio_euros = precio_euros;
        this.duracion = duracion;
        this.linea_aerea = linea_aerea;
    }
    
    // Creamos los métodos "get" para obtener el valor de las variables.
    public String getAeropuerto_origen(){ return aeropuerto_origen; }
    public String getAeropuerto_destino(){ return aeropuerto_destino; }
    public double getPrecio_euros(){ return precio_euros; }
    public int getDuracion(){ return duracion; }
    public String getLinea_aerea(){ return linea_aerea; }
    
    
    // Creamos los métodos "set" para mostar/poner el valor las variables.
    public void setAeropuerto_origen(String aeropuerto_origen){ this.aeropuerto_origen = aeropuerto_origen; }
    public void setAeropuerto_destino(String aeropuerto_destino){ this.aeropuerto_destino = aeropuerto_destino; }
    public void setPrecio_euros(double precio_euros){ this.precio_euros = precio_euros; }
    public void setDuracion(int duracion){ this.duracion = duracion; }
    public void setLinea_aerea(String linea_aerea){ this.linea_aerea = linea_aerea; }
    
    // Creamos el método "toString" para modificar/rescribir las variables.
    @Override
    public String toString(){
        return "Vuelos{" +
                    "\n\tAeropuerto_origen: " + aeropuerto_origen +
                    "\n\tAeropuerto_destino: " + aeropuerto_destino +
                    "\n\tPrecio_euros: " + precio_euros +
                    "\n\tDuracion: " + duracion +
                    "\n\tLinea_aerea: " + linea_aerea +
               '}';
    }
}