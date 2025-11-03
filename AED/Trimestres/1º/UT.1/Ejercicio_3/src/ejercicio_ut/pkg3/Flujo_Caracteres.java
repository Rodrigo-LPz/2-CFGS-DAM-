/**
 *
 * @author Rodrigo
 */
package ejercicio_ut.pkg3;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Crea la clase principal del programa.
public class Flujo_Caracteres{
    // Crea el métodod principal del programa.
    public static void main(String[] args){
        // ...
        // FileReader fr = new FileReader("C:\\Users\\Alumno\\Documents\\NetBeansProjects\\Ejercicio_UT.3\\El_Quijote.txt");
        // FileWriter fw = new FileWriter("C:\\Users\\Alumno\\Documents\\NetBeansProjects\\Ejercicio_UT.3\\El_Quijote - copia.txt");
        
        // ...
        try (FileReader fr = new FileReader("C:\\Users\\Alumno\\Documents\\2º CFGS\\AED\\Trimestres\\1º\\Ejercicio_3\\El_Quijote.txt"); FileWriter fw = new FileWriter("C:\\Users\\Alumno\\Documents\\2º CFGS\\AED\\Trimestres\\1º\\Ejercicio_3\\El_Quijote.txt - copia.txt")){
            // ...
            int charData;
            
            // ...
            //charData = fr.read();
            
            // ...
            int contador = 0;
            
            // Crea un bucle de tipo "while" para ...
            while ((charData = fr.read()) != -1){
                // ...
                fw.write(charData);
                // ...
                contador ++;
            }
            
            // Muestreo del resultado final.
            System.out.println("\n\tDel archivo pasado " + fr + " se han conseguido leer un total de " + contador + " caracteres.");
        } catch (FileNotFoundException ex){
            System.getLogger(Flujo_Caracteres.class.getName()).log(System.Logger.Level.ERROR, /* "Error. Archivo no encontrado." */ (String) null, ex);
        } catch (IOException ex){
            System.getLogger(Flujo_Caracteres.class.getName()).log(System.Logger.Level.ERROR, /* "Error. En el flujo de entrada/salida de datos." */ (String) null, ex);
        }
    }
}