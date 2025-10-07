/**
 *
 * @author Rodrigo
 */
package ejercicio_ut.pkg2;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

// Crea la clase principal del programa.
public class Flujo_Bytes{
    // Crea el método principal del programa.
    public static void main(String[] args){
        // Declara las variables para el flujo de entrada y salida de datos.
        //FileInputStream fis = null;
        //FileOutputStream fos = null;
        
        int byteData;
        int contador = 0;
        
        // Crea un bloque "try-catch" para la lectura y conteo del archivo byte a byte.
        //try{
        try (FileInputStream fis = new FileInputStream("Horario - 2ºCFGS.png"); FileOutputStream fos = new FileOutputStream("Horario - 2ºCFGS - copia.png")){
            // Abre los dos flujos, el de entrada (archivo original) y el de salida (archivo copia).
            //fis = new FileInputStream("Horario - 2ºCFGS.png");
            //fos = new FileOutputStream("Horario - 2ºCFGS - copia.png");
            //FileInputStream fis = new FileInputStream("Horario - 2ºCFGS.png");
            //FileOutputStream fos = new FileOutputStream("Horario - 2ºCFGS - copia.png");
            
            // Comienza leyendo el primer byte del archivo de entrada.
            byteData = fis.read();
                        
            // Crea un bucle de tipo "while", para que mientras no se llegue al final del archivo (-1 indica EOF) se sigue leyendo.
            while (byteData != -1){
                // Aumenta el valor del contador por cada byte leído.
                contador ++;
                
                // Escribe en el archivo de salida el byte leído del archivo de entrada.
                fos.write(byteData);
                // Lee el siguiente byte.
                byteData = fis.read();
            }
            
            // Muestreo del resultado final al usuario.
            System.out.println("\n\tEl número de lectura de bytes que posee el archivo/fichero " + fis + " es de: " + contador);
            
        } catch (FileNotFoundException e){
            System.err.println("\nError. El archivo solicitado no se encuentra o no lo reconoce el sistema.");
        } catch (IOException ioe){
            System.err.println("\nError. Fallo de entrada/salida de datos.");
        } /*finally{
            try{
                // Cierra ambos flujos de datos si no son nulos para liberar recursos.
                if (fis != null){ fis.close(); }
                if (fos != null){ fos.close(); }
                
            } catch (IOException ioe){
                System.err.println("\nError. Fallo de entrada/salida de datos.");
            }
        }*/
    }
}