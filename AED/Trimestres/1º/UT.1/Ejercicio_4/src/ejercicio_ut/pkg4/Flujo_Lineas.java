/**
 *
 * @author Rodrigo
 */
package ejercicio_ut.pkg4;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Flujo_Lineas {
    public static void main(String[] args){
        // Variable que recibe la ruta absoluta del archivo.
        String fileName = "C:\\Users\\Alumno\\Documents\\2º CFGS\\AED\\Trimestres\\1º\\Ejercicio_4\\El_Quijote.txt";
        String line = "";
        int contador = 0;
        
        //try (BufferedReader br = new BufferedReader("..."); FileReader fr = new FileReader(br)){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){            
            System.out.println("\n\n========================================================================================================================\n");
            
            /*
            while ((line = br.readLine()) != null){
                System.out.println("\t\t\t" + line);
            }
            */
            do { contador ++; System.out.println("\t\t\t" + line); } while ((line = br.readLine()) != null);
            
            System.out.println("\n========================================================================================================================\n\n");
            
            System.out.println("El archivo de texto, '.txt', (\"" + fileName + "\") analizado/leído tiene un total de " + contador + " lineas.");
        } catch (IOException e){}
    }
}