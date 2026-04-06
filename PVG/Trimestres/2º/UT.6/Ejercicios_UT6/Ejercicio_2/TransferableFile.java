// Ejercicio 2: Verificación de Integridad con Checksum (Hash)
  // Implementa un sistema de transferencia de archivos simulado. Crea una clase "TransferableFile" que contenga el contenido del archivo y un "código de verificación" (simulando un Hash o Checksum). El programa debe incluir un método que, al recibir el archivo, vuelva a calcular ese código y lo compare con el original. Si no coinciden, debe informar que la integridad se ha visto comprometida (posible ataque de modificación en el canal de comunicación).
package Ejercicios_UT6.Ejercicio_2;

/** 
 * "Checksum" o "Hash" es un valor calculado a partir del contenido de un archivo utilizando un algoritmo específico (como SHA-256). Este valor actúa como una huella digital única para ese contenido. Cuando se transfiere un archivo, se puede calcular su checksum antes de la transferencia y luego recalcularlo después de la transferencia para verificar que el contenido no ha sido alterado. SIMILAR AL PAQUETE DE DATOS ACK (o Acknowledge) VISTO EN EL PROTOCOLO TCP.
 * El algoritmo de hash "SHA-256" es una función criptográfica que toma una entrada (en este caso, el contenido del archivo) y produce un valor de hash de 256 bits (32 bytes) que es único para esa entrada (es como si creease un ID). Este valor de hash se utiliza como un "código de verificación" para garantizar la integridad del archivo durante la transferencia. Si el contenido del archivo se modifica, incluso ligeramente, el hash resultante será completamente diferente, lo que permite detectar cualquier alteración en el archivo. 
 */




// Importa de la biblioteca/librería "java.security" el paquete "MessageDigest".
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


// Clase "TransferableFile", clase principal del programa.
public class TransferableFile {
  // Atributos privados de la clase.
  private String contenido; /* No le ponemos el atributo "final" porque precisamos de que su valor sea modificado en/con "modificarContenido()" para simular un ataque. */
  private final String checksumOriginal;
  private final String nombre; /* Opcional. */

  // Constructor de la clase "TransferableFile", recibe el contenido del archivo, lo almacena y calcula su checksum original para futuras verificaciones de integridad.
  public TransferableFile(String nombre, String contenido) {
    this.nombre = nombre;
    this.contenido = contenido;
    this.checksumOriginal = calcularChecksum(contenido);
  }

  // Métodos/Funciones Getters para acceder a los atributos desde fuera.
  public String getContenido() { return contenido; }
  public String getChecksumOriginal() { return checksumOriginal; }
  public String getNombre() { return nombre; }

  // Método/Función privado "calcularChecksum", calcula el hash "SHA-256" de un texto dado.
  private String calcularChecksum(String texto) {
    // Bloque de código de tipo "try-catch", intenta calcular el hash del texto utilizando el algoritmo "SHA-256". Mientras prepara la captura de posibles excepciones/errores.
    try {
      // Objeto "MessageDigest", representa la instancia del algoritmo de hash "SHA-256" que se utilizará para calcular el checksum del contenido del archivo.
        // Obtiene (importa) el algoritmo "SHA-256" de la librería "MessageDigest".
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      // Array de bytes "hashBytes", almacena el resultado del cálculo del hash del texto utilizando el algoritmo "SHA-256". El método "digest" toma los bytes del texto como entrada y devuelve el hash resultante como un array de bytes.
        // Convierte el texto a bytes, calcula su hash y lo almacena en un array.
      byte[] hashBytes = md.digest(texto.getBytes());

      // Objeto "StringBuilder", sirve para construir la representación hexadecimal del hash de manera eficiente, evitando la creación de múltiples objetos String durante el proceso de concatenación.
        // Convierte los bytes del hash a una cadena hexadecimal legible.
      StringBuilder sb = new StringBuilder();

      // Bucle de tipo "for-each", itera/recorre cada byte del hash calculado, formatea cada byte como un valor hexadecimal de dos dígitos y lo agrega al "StringBuilder" para construir la representación completa del hash en formato hexadecimal.
      for (byte b : hashBytes) { sb.append(String.format("%02x", b)); }

      // Retona/Devuelve la representación hexadecimal del hash calculado.
      return sb.toString();
    
    // Captura de posibles excepciones/errores de tipo "NoSuchAlgorithmException" que ocurran durante el proceso de cálculo del checksum (se lanza cuando el algoritmo de hash especificado no está disponible en la plataforma).
    } catch (NoSuchAlgorithmException nsaex) {
      javax.swing.JOptionPane.showMessageDialog(null, "Error al calcular el checksum: Algoritmo no disponible: " + nsaex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
      System.err.println("Error al calcular el checksum: Algoritmo no disponible: " + nsaex.getMessage());
      nsaex.printStackTrace();
      return null;
    
    // Captura de posibles excepciones/errores de tipo genérico "Exception" que ocurran durante el proceso de cálculo del checksum (se lanza para cualquier error que pueda ocurrir, como problemas con el algoritmo de hash).
    } catch (Exception ex) {
      javax.swing.JOptionPane.showMessageDialog(null, "Error al calcular el checksum: " + ex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
      System.err.println("Error al calcular el checksum: " + ex.getMessage());
      ex.printStackTrace();
      return null;
    }
  }

  // Método/Función "verificarIntegridad", recalcula el checksum del contenido actual del archivo y lo compara con el checksum original almacenado. Si ambos coinciden, se confirma que la integridad del archivo está intacta; de lo contrario, se alerta sobre una posible modificación maliciosa del archivo durante la transferencia.
  public void verificarIntegridad() {
    // Muestreo por consola del inicio del proceso de verificación de integridad.
    System.out.println("\n-- Verificando integridad del archivo --");

    // Variable "checksumActual", almacena el resultado del cálculo del checksum del contenido actual del archivo utilizando el método "calcularChecksum". Este valor se comparará con el checksum original para verificar la integridad del archivo.
    String checksumActual = calcularChecksum(contenido);

    // Muestreo por consola del checksum recalculado, mostrando el valor del checksum actual que se ha calculado a partir del contenido actual del archivo.
    System.out.println("Checksum actual  : " + checksumActual);

    /** 
     * Condicional de tipo "if-else", compara el checksum recalculado, el checksum actual, con el checksum original para determinar si la integridad del archivo se ha mantenido o si ha sido comprometida por una posible modificación maliciosa, por un posible ataque.
     *     1. Si el checksum recalculado (checksumActual) coincide con el checksum original (checksumOriginal), se imprime un mensaje indicando que la integridad del archivo ha sido verificada y que este no ha sufrido modificaciones maliciosaso que no ha sido atacado.
     *     2. Si el checksum recalculado no coincide con el checksum original, se imprime un mensaje de alerta indicando que la integridad del archivo ha sido comprometida, lo que sugiere que el archivo ha sido modificado de manera maliciosa durante la transferencia.
     */
    if (checksumOriginal.equals(checksumActual)) {
      System.out.println("RESULTADO: Integridad verificada. El archivo no ha sufrido modificaciones maliciosas o no ha sido atacado. [checksumOriginal:" + checksumOriginal + " | checksumActual:" + checksumActual + "]");
    } else {
      System.out.println("RESULTADO: ¡ALERTA! Integridad comprometida. Posible ataque de modificación maliciosa detectado durante la transferencia del mismo. [checksumOriginal:" + checksumOriginal + " | checksumActual:" + checksumActual + "]");
    }
  }

  // Método/Función "modificarContenido", recibe un nuevo contenido para el archivo, simula una modificación maliciosa del contenido del archivo en tránsito (en proceso de transferencia), actualizando el atributo "contenido" con el nuevo valor y mostrando por consola el nuevo contenido alterado.
  public void modificarContenido(String nuevoContenido) {
    // Muestreo por consola del inicio de la simulación de modificación del archivo en tránsito.
    System.out.println("\n\n-- Simulando modificación del archivo en tránsito --");
    
    // Actualiza el contenido del archivo con el nuevo valor proporcionado, simulando una modificación maliciosa del archivo durante la transferencia.
    this.contenido = nuevoContenido;

    // Muestreo por consola del nuevo contenido del archivo después de la modificación, mostrando el nuevo contenido alterado que se ha asignado al atributo "contenido".
    System.out.println("Contenido alterado a: " + nuevoContenido);
  }

  // Método/función "main", método principal del programa.
  public static void main(String[] args) {
    // Objeto "TransferableFile", representa el archivo que se va a transferir, con su contenido original y su checksum calculado.
    TransferableFile archivo = new TransferableFile("configuracion.txt", "usuario=admin;puerto=8080");
    
    // Muestreo por consola del resultado del proceso de creación del archivo, mostrando su contenido y su checksum original.
    System.out.println("El archivo \"" + archivo.getNombre() + "\" ha sido creado.\n\t(simulación de un archvio real creado. eEn este caso es solo un \"String\" que simula el nombre del archivo. Se trabaja todo en memoria, no se crea ningún archivo físico en el disco)");
    System.out.println("Su contenido actual: " + archivo.getContenido());
    System.out.println("Checksum original: " + archivo.getChecksumOriginal());

    // Verifica antes de cualquier modificación (debe ser OK).
    archivo.verificarIntegridad();

    // Simula que alguien intercepta y modifica el archivo en el canal.
    archivo.modificarContenido("usuario=hacker;puerto=9999");

    // Verifica tras la modificación (debe detectar la alteración).
    archivo.verificarIntegridad();
  }
}

/*
¿Cómo funciona?

El flujo es el siguiente:
- Al crear el objeto se calcula el **checksum SHA-256** del contenido y se guarda como original.
- `verificarIntegridad()` recalcula el checksum del contenido actual y lo compara con el guardado.
- `modificarContenido()` simula un **ataque de modificación** en el canal de comunicación.

La salida esperada por consola sería algo así:

Archivo creado.
Contenido        : usuario=admin;puerto=8080
Checksum original: 3a5f91...

-- Verificando integridad del archivo --
Checksum actual  : 3a5f91...
RESULTADO: Integridad verificada. El archivo no ha sufrido modificaciones maliciosas o no ha sido atacado.

-- Simulando modificación del archivo en tránsito --
Contenido alterado a: usuario=hacker;puerto=9999

-- Verificando integridad del archivo --
Checksum actual  : 7bc23d...
RESULTADO: ¡ALERTA! Integridad comprometida. Posible ataque de modificación maliciosa detectado durante la transferencia del mismo..
*/