/**
 *
 * @author Rodrigo
 */
package com.mycompany.xmlpartner;


@xlmRootElement(name="socio")

public class Partner{
    private int id;
    private String nombre;
    private String direccion;
    private String fecha_alta;

    public Partner(int id, String nombre, String direccion, String fecha_alta) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fecha_alta = fecha_alta;
    }
    
    public int getId(){ return id; }

    public String getNombre(){ return nombre; }
    public String getDireccion(){ return direccion; }
    public String getFecha_alta(){ return fecha_alta; }

    public void setId(int id){ this.id = id; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    public void setDireccion(String direccion){ this.direccion = direccion; }
    public void setFecha_alta(String fecha_alta){ this.fecha_alta = fecha_alta; }

    @Override
    public String toString(){
        return "\nPartner{" + 
                    "\n\tid: " + id +
                        "\n\t\tnombre: " + nombre + "." +
                        "\n\t\tdireccion: " + direccion + "." +
                        "\n\t\tfecha_alta: " + fecha_alta +
               '}';
    }
}