/**
 *
 * @author Rodrigo
 */
package com.mycompany.Hibernate_ej.modelo;


// Crea la clase "Empleado".
public class Empleado{
    // Declara las variables, atributos.
    private long id;
    private String primerNombre;
    private double salario;
    private Departamento departamento;
    
    // Crea el constructor vacío.
    public Empleado(){}
    
    // Crea el constructor de/para las variables.
    public Empleado(long id, String primerNombre, double salario, Departamento departamento){
        this.id = id;
        this.primerNombre = primerNombre;
        this.salario = salario;
        this.departamento = departamento;
    }
    
    // Crea los métodos "get".
    public long getId(){ return id; }
    public String getPrimerNombre(){ return primerNombre; }
    public double getSalario(){ return salario; }
    public Departamento getDepartamento(){ return departamento; }
    
    // Crea los métodos "set".
    public void setId(long id){ this.id = id; }
    public void setPrimerNombre(String primerNombre){ this.primerNombre = primerNombre; }
    public void setSalario(double salario){ this.salario = salario;}
    public void setDepartamento(Departamento departamento){ this.departamento = departamento; }
    
    // Crea el método "toString".
    @Override
    public String toString(){
        return "\tEmpleado{"
             + "\n\t\tID: " + id
             + "\n\t\tPrimer Nombre: " + primerNombre
             + "\n\t\tSalario: " + salario
             + "\n\t\tDepartamento: " + departamento
             + "\n\t"
             + '}';
    }
}