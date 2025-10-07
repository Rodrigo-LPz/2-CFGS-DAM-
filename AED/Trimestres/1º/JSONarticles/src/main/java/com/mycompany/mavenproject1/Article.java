/**
 *
 * @author Rodrigo
 */
package com.mycompany.mavenproject1;


// Crea la clase "Article".
public class Article{
    // Declara las variables privadas.
    private String category;
    private String name;
    private double price;
    
    // Definimos el constructor (para el paquete de la librería JSON).
    /*public Article(){
    }
    */
    public Article(String category, String name, double price){
        this.category = category;
        this.name = name;
        this.price = price;
    }
    
    // Creamos los métodos "get" para obtener el valor de las variables.
    public String getCategory(){ return category; }
    public String getName(){ return name; }
    public double getPrice(){ return price; }
    
    // Creamos los métodos "set" para mostar/poner el valor las variables.
    public void setCategory(String category){ this.category = category; }
    public void setName(String name){ this.name = name; }
    public void setPrice(double price){ this.price = price; }
    
    // Creamos el método "toString" para modificar/rescribir las variables.
    @Override
    public String toString(){
        //return "Article{" + "category=" + category + ", name=" + name + ", price=" + price + '}';
        return "\nArtículo{" + 
               "\n\tCategoría: " + category + "." +
               "\n\t   Nombre: " + name +
               "\n\t   Precio: " + price + "€\n\t" +
               '}';
    }
}