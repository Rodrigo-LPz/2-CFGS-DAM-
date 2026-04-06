/**
 *
 * @author Rodrigo
 */
package com.tcp_clientserver_communication.ejercicios_ut5.Ejercicio_1;


// Importa de la biblioteca/librería "io" el paquete "BufferedReader".
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// Crea una de las dos clases principales del programa, "Servidor".
public class Servidor{
    /**
     * Define una variable privada de tipo "final int" llamada "PORT" y le asigna el valor "6000.
     * Este puerto de escucha (6000), será la puerta de enlace del servidor, el puerto donde escuchará el servidor.
     * 
     * La palabra clave "final" es un atributo que indica/redefine cómo será el comportamienro de la variable.
     * La variable no puede ser modificada después de su inicialización. 
     */
    private static final int PORT = 6000;

    // Crea el método principal del programa, "main".
    public static void main(String[] args){
        // Crea los sockets, objetos de conexión de comunicación Cliente/Servidor para el intercambio de paquetes.
        ServerSocket serSk = null;  /* ServerSocket (servidor). */
        Socket sk = null;           /* Socket (cliente). */
        
        /**
         * Crea un bloque "try-catch" para manejar el socket del servidor.
         * Crea el socket del servidor (ServerSocket) para manejar las conexiones entrantes de los clientes. Quedará a la espera de cliente(s).
         */
        // try (ServerSocket serverSocket = new ServerSocket(PORT)){    // Bloque "try-with-resources".
        try{
            // Crea el socket del servidor (ServerSocket). Quedará a la espera de cliente(s).
            serSk = new ServerSocket(PORT);

            // Imprime un mensaje por consola para indicar que el servidor de eco (Echo Server) está inicializado.
            System.out.println("Servidor de eco (Echo Server) iniciado y conectado al puerto " + PORT + ".");
            
            // Entra en un bucle infinito para aceptar conexiones de clientes.
            while (true){
                /**
                 * Crea un bloque "try-catch" para manejar la conexión del cliente, el flujo de entrada y el flujo de salida.
                 * Crea un socket para aceptar una conexión entrante de un cliente.
                 */
                // try (Socket clientSocket = serverSocket.accept();
                //      BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //      PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)){
                try {
                    // Crea el lector de texto para mensajes.
                    sk = serSk.accept();
                    
                    // Imprime un mensaje por consola para indicar que el cliente se h aconectado exitosamente al servidor por el puerto definido para este.
                    System.out.println("Cliente conectado al Servidor con puerto (" + PORT + ") desde la dirección: " + sk.getInetAddress() + ".\n");

                    /**
                     * Crea un objeto "BufferedReader" para leer datos del cliente a través del flujo de entrada del socket.
                     */
                    BufferedReader reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));

                    /**
                     * Crea un objeto "PrintWriter" para enviar datos al cliente a través del flujo de salida del socket.
                     *      El segundo argumento "true" habilita el "auto-flush", lo que significa que los datos se enviarán inmediatamente después de llamar a "println".
                     * 
                     * Crea el escritor de texto para mensajes.
                     */
                    PrintWriter writer = new PrintWriter(sk.getOutputStream(), true);
                    
                    /**
                     * // Recibe y lee el message enviado por el cliente.
                     * String message = reader.readLine();
                     * 
                     * // Verifica/Comprueba que el message no sea nulo. Si es así, lo convierte a mayúsculas y lo envía de vuelta al cliente.
                     * if (message != null){
                     * 
                     *     String upperCaseMessage = message.toUpperCase();                                                    // Convierte el mensaje recibido a mayúsculas.
                     * 
                     *     writer.println(upperCaseMessage);                                                                 // Envía el mensaje convertido a mayúsculas de vuelta al cliente.
                     * 
                     *     System.out.println("Mensaje recibido (del cliente): " + message);                                   // Imprime por consola el mensaje recibido del cliente.
                     *     System.out.println("Mensaje (convertido a mayúsculas) enviado (al cliente): " + upperCaseMessage);  // Imprime por consola el mensaje enviado al cliente (el mensaje convertido a mayúsculas).
                     * }
                     */
                    // Declara una variable de tipo cadena, "String", donde se almacenará cada mensaje enviado por el cliente..
                    String message;
                     
                    // Crea un bucle de tipo "while" el cuál mantendrá la comunicación activa mientras el cliente siga enviando paquetes de información, datos.
                    while ((message = reader.readLine()) != null){  // La función "readLine()" devolverá el valor "null" cuando el cliente cierre la conexión.
                        String upperCaseMessage = message.toUpperCase();
                        
                        writer.println(upperCaseMessage);
                        
                        System.out.println("Mensaje recibido (del cliente): " + message);
                        System.out.println("Mensaje (convertido a mayúsculas) enviado (al cliente): " + upperCaseMessage + "\n");
                    }
                    
                // Captura de posibles excepciones/errores que ocurran durante la ejecución del programa.
                } catch (IOException ioex){
                    javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado al manejar la conexión del cliente: " + ioex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
                    System.err.println("Error inesperado al manejar la conexión del cliente: " + ioex.getMessage());
                    ioex.printStackTrace();
                }
            }

        // Captura de posibles excepciones/errores que ocurran durante la ejecución del programa.
        } catch (IOException ioex){
            javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado al iniciar el servidor: " + ioex.getMessage(), "ERROR de inicio de sesión", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.err.println("Error inesperado al iniciar el servidor: " + ioex.getMessage());
            ioex.printStackTrace();
        
        // Finaliza el bloque de ejecución cerrando/finalizando recursos, los sockets, en caso de ser necesario.
        } finally{
            // Crea un bloque "try-catch" para manejar el cierre/finalización del socket del cliente.
            try{
                if (sk != null) sk.close();
                if (serSk != null) serSk.close(); 
                
            // Captura de posibles excepciones/errores que ocurran durante el cierre/finalización de los sockets.
            } catch (IOException ioex){
                javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado al cerrar/finalizar los sockets: " + ioex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
                System.err.println("Error inesperado al cerrar/finalizar los sockets: " + ioex.getMessage());
                ioex.printStackTrace();
            }
        }
    }
}