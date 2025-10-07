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
        final String fileName = "C:\\Users\\Alumno\\Documents\\2� CFGS\\AED\\Trimestres\\1�\\Ejercicio_5\\11_Historico_F�tbol.txt";
        
        String line = "";
        int totalPlayers = 0;
        int totalAge = 0;
        double totalHeight = 0;
        String[] data;
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            Scanner user;
            
            System.out.println("\n<====================  11 Hist�rico F�tbol(masculino) - seg�n ChatGPT  ====================>\n");
            
            while ((line = br.readLine()) != null){                
                user = new Scanner(line).useLocale(Locale.US);
                user.useDelimiter("\\s*,\\s*");
                
                user.next();
                totalAge += user.nextInt();
                totalHeight += user.nextDouble();
                totalPlayers ++;
                
                System.out.println("\t\t\t\t" + line);
            }            
            System.out.print("\nEl n�mero total de jugadores registrados en la lista es de: " + totalPlayers);
            System.out.printf("\nLa edad media total de entre todos los jugadores registrados en la lista es de: %2.2f a�os", ((double)totalAge / totalPlayers));
            System.out.printf("\nLa altura media total de entre todos los jugadores registrados en la lista es de: %2.2fm\n\n", (totalHeight / totalPlayers));
            
            //user.close();
        } catch (IOException ioe){}
    }
}