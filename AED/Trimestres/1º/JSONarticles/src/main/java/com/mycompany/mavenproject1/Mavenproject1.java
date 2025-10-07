/**
 *
 * @author Rodrigo
 */
package com.mycompany.mavenproject1;

// Importa de la biblioteca/librería el paquete "ObjectMapper".
import com.fasterxml.jackson.databind.ObjectMapper;
// Importa de la biblioteca/librería el paquete "File".
import java.io.File;
// Importa de la biblioteca/librería el paquete "IOException".
import java.io.IOException;
// Importa de la biblioteca/librería el paquete "ArrayList".
import java.util.ArrayList;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;

public class Mavenproject1{
    public static void main(String[] args){
        // Crae una lista, una "List", dinámica.
        List<Article> articles = new ArrayList<>();
        
        // Agrega artículos, objetos a la lista "articles".
        /*
        Articles article;
        article = new Article("Impresoras", "HP Laserjet 2500", 219.0);
        */
        articles.add(new Article("Impresoras", "HP Laserjet 2500", 219.0));
        articles.add(new Article("Portátiles", "Google Chromebook", 3228.0));
        articles.add(new Article("Impresoras", "HP Inkjet 1234", 69.0));
        articles.add(new Article("Portátiles", "Lenovo Thinkpad 5643", 1219.0));
        articles.add(new Article("Cables", "Cable USB-C a USB 3.0", 19.55));
        
        // Muestreo por pantalla del resultado final al usuario.
        for (Article article : articles){
            System.out.println(article);
        }
        
        // Guarda en JSON.
        ObjectMapper om = new ObjectMapper();
        
        try{
            om.writeValue(new File ("articles.json"), articles);
        } catch (IOException ioe){
            System.err.println(ioe.getMessage());
        }
    }
}