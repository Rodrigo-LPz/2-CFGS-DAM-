/**
 *
 * @author Rodrigo y Aday
 */

package Server;

// Importa las dos librerías junto a los paquetes necesarios para permitir realizar la conexión entre el servidor y cliente(s) a través de sockets que permitirán el envío y recepción de datos e información, de paquetes.
import java.io.*;
import java.net.*;


// Crea la clase "FileServer".
public class FileServer{
    // Define el puerto de escucha (5000), el puerto donde escuchará el servidor.
    private static final int PORT = 5000;
    
    // Crea la carpeta donde se descargarán y almacenarán los archivos disponibles.
    private static final String FILES_DIR = "Server/files";
    
    // Declara el método/función principal de la clase.
    public static void main(String[] args){
        // Crea los sockets, objetos de conexión de comunicación Cliente/Servidor para el intercambio de paquetes.
        ServerSocket serSk = null;  /* ServerSocket (servidor). */
        Socket sk = null;           /* Socket (cliente). */
        
        // Imprime un mensaje por consola para indicar que el servidor está inicializado.
        System.out.println("Servidor iniciado y conectado al puerto " + PORT + ".");
        
        // Crea el socket del servidor (ServerSocket). Quedará a la espera de cliente(s).
        //try (ServerSocket serverSocket = new ServerSocket(PORT)){
        try{
            serSk = new ServerSocket(PORT);
            
            // Crea un bucle "while" para la recepción y atención de múltiples peticiones simultáneas.
            while (true){
                // Acepta una conexión (por parte de un cliente) entrante.
                //Socket clientSocket = serverSocket.accept();
                sk = serSk.accept();
                
                // Imprime un mensaje por consola para indicar que el cliente se h aconectado exitosamente al servidor por el puerto definido para este.
                System.out.println("Cliente conectado al Servidor con puerto (" + PORT + ").");
                
                // Atendemos al cliente
                //handleClient(clientSocket);
                handleClient(sk);
            }
            
        // Captura de posibles excepciones/errores que ocurran durante la ejecución del programa.
        } catch (IOException ioex){
            javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado durante/en el funcionamiento del servidor: " + ioex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.err.println("Error inesperado durante/en el funcionamiento del servidor: " + ioex.getMessage());
            ioex.printStackTrace();
        
        // Finaliza el bloque de ejecución cerrando/finalizando recursos, los sockets, en caso de ser necesario.
        } finally{
            try{
                if (sk != null) sk.close();
                if (serSk != null) serSk.close(); 
                
            // Captura de posibles excepciones/errores que ocurran durante la ejecución del programa.
            } catch (IOException ioex){
                javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado al cerrar/finalizar los sockets: " + ioex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
                System.err.println("Error inesperado al cerrar/finalizar los sockets: " + ioex.getMessage());
                ioex.printStackTrace();
            }
        }
    }
    
    // Declara el método privado "handleClient" que espera/recibe como parámetro "clientSocket" de tipo el objeto "Socket".
    private static void handleClient(Socket clientSocket){
        try (
                // Crea los flujos de entrada y salida de paquetes.
                BufferedInputStream bis = new BufferedInputStream(clientSocket.getInputStream());        /* Flujo de entrada desde el cliente */
                BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());   /* Flujo de salida hacia el cliente. */

                // Crea el lector de texto para comandos.
                BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
            ){
            
            // Recibe y lee el comando enviado por el cliente.
            String command = reader.readLine();
            
            // Imprime un mensaje por consola para indicar que el comando ha sido recibido y leído.
            System.out.println("La petición del cliente, el Comando, ha sido recibido: " + command);
            
            // Verifica/Comprueba que el comando empiece por "GET".
            if (command != null && command.startsWith("GET ")){
                // Obtiene/Extrae el nombre del archivo.
                    /*
                     * 1º Quita los primero 4 carácteres del comando, es decir: ("GET "). Para quedarse únicamente con el nombre del archivo solicitado/requerido por el cliente en la petición.
                     * 2º Con la función ".trim()" elimina posibles espacios sobrantes al inicio y/o al final del mismo.
                     */
                String fileName = command.substring(4).trim();
                    /*
                     * 1º Crea un objeto "File" con el que apuntar al archivo solicitado dentro de la carpeta "FILES_DIR". No abre el archivo, solo representa su ruta en el sistema.
                     */
                File file = new File(FILES_DIR, fileName);
                
                // Si el archivo no existe, responde con un mensaje confirmándolo ("ERROR").
                if (!file.exists() || !file.isFile()){
                    bos.write(("ERROR. El archivo (" + fileName + ") no existe o no se ha podido encontrar dentro del sistema.\n").getBytes());
                    bos.flush();
                    return;
                }
                
                // Si el archivo existe, envía un mensaje confirmándolo ("OK") y el tamaño/peso del mismo.
                long fileSize = file.length();
                bos.write(("OK " + fileSize + "\n").getBytes());
                System.out.println("El archivo (" + fileName + ") ha sido encontrado. Tiene un tamaño/peso de " + fileSize + " bytes.");
                
                // Fuerza el envío de todos los datos pendientes en el buffer de salida, asegurando así que la totalidad del archivo sea transmitido al cliente.
                bos.flush();
                
                // Envía el archivo en binario.
                try (BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(file))){
                        /*
                         * 1º Crea un array de bytes que actuará como buffer (espacio de memoria temporal que almacena datos mientras se transfieren entre dos puntos) de lectura.
                         * 2º Especifica/Ajusta un tamaño máximo de bytes al array. Su tamaño máximo es de 4096 bytes (4 KB), un valor habitual que equilibria eficiencia y consumo de memoria en transferencia de archivos.
                         */
                    byte[] buffer = new byte[4096];
                        /*
                         * 1º Declara una variable que se utilizará para almacenar la cantidad real de bytes leídos en cada iteración. No siempre se llenará el buffer, especialmente en la última lectura.
                         */
                    int bytesRead;
                    
                    // Lee y envía el archivo por bloques.
                        /*
                         * 1º Con "fileIn.read(buffer)" devuelve:
                         *      - El número de bytes leídos (>= 0)
                         *      - Y (-1) cuando se alcanza el final del archivo, EOF (End Of File).
                         *    Mientras no sea (-1) el archivo seguirá teniendo datos.
                         * 2º Escribe en el flujo de salida únicamente los bytes leídos [es importante usar "bytesRead" y no el tamaño completo del buffer (4096) para evitar enviar basura (bytes/datos vacíos) en la última iteración].
                         */
                    while ((bytesRead = fileIn.read(buffer)) != -1){ bos.write(buffer, 0, bytesRead); }
                    
                    // Fuerza el envío de todos los datos pendientes en el buffer de salida, asegurando así que la totalidad del archivo sea transmitido al cliente.
                    bos.flush();
                }
                
                // Imprime un mensaje por consola para indicar que el archivo ha sido encontrado y enviado como resultado d ela petición hecha por el cliente.
                System.out.println("Archivo enviado correctamente al cliente: {" + fileName + "}.");
            
            // Cubre el caso de que el comando no empiece por "GET".
            } else{
                // Imprime un mensaje por consola para indicar que el comando no es válido.
                bos.write("ERROR. Comando no válido\n".getBytes());
                
                // Fuerza el envío de todos los datos pendientes en el buffer de salida, asegurando así que la totalidad del archivo sea transmitido al cliente.
                bos.flush();
            }

        // Captura de posibles excepciones/errores que ocurran durante la ejecución del programa.
        } catch (IOException ioex){
            javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado atendiendo al cliente: " + ioex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.err.println("Error inesperado atendiendo al cliente: " + ioex.getMessage());
            ioex.printStackTrace();
        }
    }
}