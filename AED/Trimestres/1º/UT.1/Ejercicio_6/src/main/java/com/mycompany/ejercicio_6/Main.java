/**
 *
 * @author Rodrigo
 */
package com.mycompany.ejercicio_6;

// Importa de internet el paquete "ObjectMapper".
import com.fasterxml.jackson.core.type.TypeReference;
// Importa de internet el paquete "IOException".
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
// Importa de internet el paquete "IOException".
import java.io.IOException;
// Importa de internet el paquete "List".
import java.util.List;


public class Main{
    public static void main(String[] args){
        ObjectMapper mapeador = new ObjectMapper();
        String fileName = "C:\\Users\\Alumno\\Documents\\2º CFGS\\AED\\Trimestres\\1º\\Ejercicio_6\\11_Historico_Fútbol_JSON.txt";
        
        int totalPlayers = 0;
        int totalAge = 0;
        double totalHeight = 0;
        
        try{
            List<Jugador> jugadores;
            jugadores = mapeador.readValue(new File(fileName), new TypeReference<List<Jugador>>() {});
            for (Jugador j : jugadores){
                totalPlayers ++;
                totalAge += j.getEdad();
                totalHeight += j.getAltura();
            }
            
            System.out.print("\nEl número total de jugadores registrados en la lista es de: " + totalPlayers);
            System.out.printf("\nLa edad media total de entre todos los jugadores registrados en la lista es de: %2.2f años", ((double)totalAge / totalPlayers));
            System.out.printf("\nLa altura media total de entre todos los jugadores registrados en la lista es de: %2.2fm\n\n", (totalHeight / totalPlayers));
        } catch (IOException ioe) { ioe.printStackTrace(); }
    }
}