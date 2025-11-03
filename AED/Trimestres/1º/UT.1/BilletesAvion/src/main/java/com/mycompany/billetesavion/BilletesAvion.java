/**
 *
 * @author Rodrigo
 */
package com.mycompany.billetesavion;


// Importa de la biblioteca/librería el paquete "BufferedReader".
import java.io.BufferedReader;
// Importa de la biblioteca/librería el paquete "FileReader".
import java.io.FileReader;
// Importa de la biblioteca/librería el paquete "IOException".
import java.io.IOException;
// Importa de la biblioteca/librería el paquete "ArrayList".
import java.util.ArrayList;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;

// Crea la clase principal del programa.
public class BilletesAvion{
    // Crea el método principal del programa.
    public static void main(String[] args){
        // Crae una lista, una "List", dinámica.
        List<Vuelos> vuelos = new ArrayList<>();
        
        for (Vuelos vuelo : vuelos){
            vuelos.add(new Vuelos()); 
        }
        
        // Variable que recibe la ruta absoluta del archivo.
        String fileName = "\"C:\\Users\\Alumno\\Documents\\2º CFGS\\AED\\Trimestres\\1º\\BilletesAvion\\localizacion.txt\"";
        String line = "";
        String aeropuerto_origen = 0;
        
        // Crea un bloque "try-catch", éste crea y abre un "BufferedReader" (envolviendo un "FileReader") para leer el fichero indicado por "fileName".
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            
            
            // Mostramos el total de líneas leídas
            
        } catch (IOException ioe){}
    }
}