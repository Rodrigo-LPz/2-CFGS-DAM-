/**
 *
 * @author Rodrigo
 */
package ejercicio_5;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Ejercicio_5_Escaner {
    public static void main(String[] args){
        final String fileName = "C:\\Users\\Alumno\\Documents\\2º CFGS\\AED\\Trimestres\\1º\\Ejercicio_5\\11_Historico_Fútbol.txt";
        
        String line = "";
        int totalPlayers = 0;
        int totalAge = 0;
        double totalHeight = 0;
        String[] data;
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            Scanner user;
            
            System.out.println("\n<====================  11 Histórico Fútbol(masculino) - según ChatGPT  ====================>\n");
            
            while ((line = br.readLine()) != null){                
                user = new Scanner(line).useLocale(Locale.US);
                user.useDelimiter("\\s*,\\s*");
                
                user.next();
                totalAge += user.nextInt();
                totalHeight += user.nextDouble();
                totalPlayers ++;
                
                System.out.println("\t\t\t\t" + line);
            }            
            System.out.print("\nEl número total de jugadores registrados en la lista es de: " + totalPlayers);
            System.out.printf("\nLa edad media total de entre todos los jugadores registrados en la lista es de: %2.2f años", ((double)totalAge / totalPlayers));
            System.out.printf("\nLa altura media total de entre todos los jugadores registrados en la lista es de: %2.2fm\n\n", (totalHeight / totalPlayers));
            
            //user.close();
        } catch (IOException ioe){}
    }
}