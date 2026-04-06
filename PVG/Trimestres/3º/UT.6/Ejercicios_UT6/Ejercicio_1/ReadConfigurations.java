// Ejercicio 1: Gestión Robusta de Excepciones
  // Crea un programa que intente leer un archivo de configuración. Debes capturar específicamente la excepción de "archivo no encontrado" y una excepción genérica para cualquier otro error, asegurando que el programa informe al usuario sin bloquearse.
package Ejercicios_UT6.Ejercicio_1;




// Importa todos los paquetes de la biblioteca/librería "java.io".
import java.io.*;
import javax.swing.JOptionPane;


// Clase "ReadConfigurations", clase principal del programa.
public class ReadConfigurations {
  // Método/función "main", método principal del programa.
  public static void main(String[] args) {
    // Variable "archivo" de tipo String, almacena el nombre del archivo a leer.
    //String archivo = "configuracion.txt";
    String archivo = "Ejercicios_UT6/Ejercicio_1/configuracion.txt";

    // Bloque de código de tipo "try-catch", intenta abrir y leer el archivo especificado en la variable "archivo" línea por línea. Mientras prepara la captura de posibles excepciones/errores.
    try {
      // Objeto de tipo "BufferedReader" donde almacenar temporalmente el archivo a leer, mediante el objeto "FileReader", quién recibe el nombre del archivo.
      BufferedReader reader = new BufferedReader(new FileReader(archivo));
      
      // Variable "linea" de tipo String, almacena cada línea del archivo.
      String linea;

      // Bucle de tipo "while", se ejecutará siempre que la variable que está leyendo, "linea", no sea nula, es decir, no esté vacía. 
      while ((linea = reader.readLine()) != null) { System.out.println(linea); }

      // Cierra/Finaliza la ejecución del objeto "reader", y por ende, también se cierra/finaliza la lectura del archivo.
      reader.close();
      
      // Muestreo por consola del resultado del proceso de lectura del archivo, indicando que el archivo fue leído correctamente.
      //javax.swing.JOptionPane.showMessageDialog(null, "El archivo \"" + archivo + "\" ha sido leído correctamente.", "Lectura de archivo", javax.swing.JOptionPane.INFORMATION_MESSAGE);
      JOptionPane.showMessageDialog(null, "El archivo \"" + archivo + "\" ha sido leído correctamente.", "Lectura de archivo", javax.swing.JOptionPane.INFORMATION_MESSAGE);
      System.out.println("El archivo \"" + archivo + "\" ha sido leído correctamente.");
    
    // Captura de posibles excepciones/errores de tipo "FileNotFoundException" que ocurran durante el proceso de lectura del archivo (se lanza cuando el archivo especificado no se encuentra en la ubicación indicada).
    } catch (FileNotFoundException fnfex) {
      JOptionPane.showMessageDialog(null, "Error inesperado durante la lectura, El archivo \"" + archivo + "\" no existe, no fue encontrado o no lo reconoce el sistema operativo: " + fnfex.getMessage(), "ERROR de lectura de archivo", javax.swing.JOptionPane.ERROR_MESSAGE);
      System.err.println("Error inesperado durante la lectura, El archivo \"" + archivo + "\" no existe, no fue encontrado o no lo reconoce el sistema operativo: " + fnfex.getMessage());
      fnfex.printStackTrace();

    // Captura de posibles excepciones/errores de tipo "IOException" que ocurran durante el proceso de lectura del archivo (se lanza cuando ocurre un error de entrada/salida, como problemas al leer el archivo o al cerrar el recurso).
    } catch (IOException ioex) {
      JOptionPane.showMessageDialog(null, "Error inesperado durante la lectura, El archivo \"" + archivo + "\" no pudo ser leído correctamente: " + ioex.getMessage(), "ERROR de lectura de archivo", javax.swing.JOptionPane.ERROR_MESSAGE);
      System.err.println("Error inesperado durante la lectura, El archivo \"" + archivo + "\" no pudo ser leído correctamente: " + ioex.getMessage());
      ioex.printStackTrace();
    
    // Captura de posibles excepciones/errores de tipo genérico "Exception" que ocurran durante el proceso de lectura del archivo (se lanza para cualquier otro error no específicado en las capturas anteriores que pueda ocurrir).
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
      System.err.println("Error inesperado: " + ex.getMessage());
      ex.printStackTrace();
    }
  }
}