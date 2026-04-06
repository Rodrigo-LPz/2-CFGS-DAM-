/**
 *
 * @author Rodrigo y Aday
 */
package Client;

// Importa de la librería/biblioteca el paquete "Scanner".
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


// Crea la clase "FileServer".
public class FileClient{
    // Datos del servidor
        // Define el dispositivo o sistema informático anfitrión (host) conectado a una red.
    private static final String SERVER_HOST = "localhost";
    
        // Define el puerto de escucha (5000), el puerto por el que el cliente se conectará, entrará, y tendrá acceso al servidor.
    private static final int SERVER_PORT = 5000;
    
    // Crea la carpeta donde se descargarán y almacenarán los archivos disponibles.
    private static final String DOWNLOADS_DIR = "downloads";
    
    // Crea el objeto escáner para la interación por consola con el usuario.
    private static Scanner scanner = new Scanner(System.in);
    
    // Declara el método/función principal de la clase.
    public static void main(String[] args){
        // Crea el socket, objeto de conexión de comunicación Cliente/Servidor para el intercambio de paquetes.
        Socket sk = null;           /* Socket (cliente). */
        
        // Imprime un mensaje por consola para indicar e interactuar con el usuario.
        System.out.print("Introduzca el nombre del archivo a descargar: ");
        String fileName = scanner.nextLine();
        
        // Crea la carpeta de descargas "downloads" si no existe.
        File downloadsDir = new File(DOWNLOADS_DIR);
        if (!downloadsDir.exists()) downloadsDir.mkdirs();
        
        try{
            sk = new Socket(SERVER_HOST, SERVER_PORT);
            
            // Crea los flujos de entrada y salida de paquetes.
            BufferedOutputStream bos = new BufferedOutputStream(sk.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(sk.getInputStream());
            
            // Crea el lector de texto para comandos.
            BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
            
            // Envía el comando "GET".
            bos.write(("GET " + fileName + "\n").getBytes());
            
            // Fuerza el envío de todos los datos pendientes en el buffer de salida, asegurando así que la totalidad del archivo sea transmitido al cliente.
            bos.flush();
            
            // Lee la respuesta inicial enviada por el servidor tras recibir el comando.
            String response = reader.readLine();
            
            // Si hay un error con el comando enviado, responde con un mensaje confirmándolo ("ERROR").
            if (response.startsWith("ERROR")){
                System.out.println(response);
                return;
            }
            
            // Si el comando enviado está correcto, envía un mensaje confirmándolo ("OK") y obteniendo así el tamaño/peso del archivo obtenido tras la ejecución del comando.
                /*
                 * 1º Crea un array de tipo String donde lo divide en partes, separadas por un espacio (" ").
                 *    Ejemplo:
                 *       String: "Ok 1234"
                 *       Array (proveniente del String):
                 *              parts[0] = "Ok"
                 *              parts[1] = "1234"
                 *       Split (" "):
                 *              "Ok"
                 *              "1234"
                 */
            String[] parts = response.split(" ");
                /*
                 * 1º Partiendo de lo anterior, parts[1] es "1234" (en String). Aunque el tamaño/peso del archivo debe ser un número. Para ello utilizamos la función "Long.parseLong()" con la que convertir el texto en número, en formato/tipo "long". Se usa "long" para poder llegar a ocupar el peso de 2 GB, en vez de "int" ya que éste no sería suficiente para dicho tamaño.
                 */
            long fileSize = Long.parseLong(parts[1]);
            
            //Prepara el archivo de destino.
            File outputFile = new File(DOWNLOADS_DIR, fileName);
            
            // Abre un flujo de salida binario hacia el archivo donde se guardará la descarga solicitada. Para ello se hará uso de un "BufferedOutputStream" con el que mejorar el rendimiento de escritura.
            try (BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(outputFile))){
                // Buffer de 4 KB para leer el total de bytes recibidos hasta el momento.
                byte[] buffer = new byte[4096];
                
                // Declara una variable con la que controlar el total de bytes recibidos hasta el momento.
                long totalRead = 0;
                
                // Almacena cuántos bytes se leen en cada iteración.
                int bytesRead;
                
                // Lee exactamente el tamaño indicado.
                    // Lee el dato entrante del socket hasta recibir exactamente el mismo tamaño del archivo descargado.
                        /*
                         * 1º Comprueba que todavía no se han leído todos los bytes del archivo: ["totalRead < fileSize" → bytes leídos (recibidos) < bytes totales de archivo (envíados y cebidos por el servidor)].
                         * 2º Lee datos desde el socket y comprueba que el flujo no ha finalizado.
                         */
                while (totalRead < fileSize && (bytesRead = bis.read(buffer)) != -1){
                    // Escribe en el archivo únicamente los bytes que se han leído.
                    fileOut.write(buffer, 0, bytesRead);
                    
                    // Actualiza el contador de bytes recibidos.
                    totalRead += bytesRead;
                }
                
                // Fuerza el envío de todos los datos pendientes en el buffer de salida, asegurando así que la totalidad del archivo sea transmitido al cliente.
                fileOut.flush();
                
                // Imprime un mensaje por consola para indicar que el proceso de descarga ha sido concluido correctamente e informar de su tamaño y ruta.
                System.out.println("Archivo descargado correctamente.\n\tTamaño recibido: " + totalRead + " bytes.\n\tGuardado en la ruta: " + outputFile.getAbsolutePath());
            }

        // Captura de posibles excepciones/errores que ocurran durante la ejecución del programa.
        } catch (IOException ioex){
            javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado en el cliente: " + ioex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.err.println("Error inesperado en el cliente: " + ioex.getMessage());
            ioex.printStackTrace();
        }
    }
}