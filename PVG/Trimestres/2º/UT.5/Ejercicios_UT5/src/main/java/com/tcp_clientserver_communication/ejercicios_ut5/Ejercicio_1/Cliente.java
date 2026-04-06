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
import java.net.Socket;

// Crea una de las dos clases principales del programa, "ClienteEcho".
public class Cliente{
    /**
     * Define una variable privada de tipo "final String" llamada "SERVER_HOST" y le asigna el valor "localhost".
     * Este sistema informático anfitrión o 'Host' (localhost), será el especificador del tipo de conexión a una red sobre la comunicación Cliente/Servidor.
     * 
     * La palabra clave "final" es un atributo que indica/redefine cómo será el comportamienro de la variable.
     * La variable no puede ser modificada después de su inicialización. 
     */
    private static final String SERVER_HOST = "localhost";
    
    /**
     * Define una variable privada de tipo "final int" llamada "SERVER_PORT" y le asigna el valor "6000".
     * Este puerto de escucha (6000), será la puerta de enlace por la que el cliente se conectará, entrará, y tendrá acceso al servidor.
     * 
     * La palabra clave "final" es un atributo que indica/redefine cómo será el comportamienro de la variable.
     * La variable no puede ser modificada después de su inicialización. 
     */
    private static final int SERVER_PORT = 6000;
    
    // Crea el método principal del programa, "main".
    public static void main(String[] args){
        // Crea el socket, objeto de conexión de comunicación Cliente/Servidor para el intercambio de paquetes.
        Socket sk = null;   /* Socket (cliente). */
        
        /**
         * Crea un bloque "try-with-resources" para manejar el socket del cliente.
         */
        try{
            // Crea el socket del cliente (Socket) y se conecta al servidor utilizando la dirección del host o anfitrión y el puerto de escucha definidos anteriormente.
            sk = new Socket(SERVER_HOST, SERVER_PORT);
            
            // Imprime un mensaje por consola para indicar que el servidor de eco (Echo Server) está inicializado.
            System.out.println("Conectado al servidor con anfitrión/host en \"" + SERVER_HOST + "\" y puerto: " + SERVER_PORT);

            /**
             * Crea un objeto "PrintWriter" para enviar datos al servidor a través del flujo de salida del socket.
             *      El segundo argumento "true" habilita el "auto-flush", lo que significa que los datos se enviarán inmediatamente después de llamar a "println".
             * 
             * Crea el escritor de texto para mensajes.
             */
            PrintWriter writer = new PrintWriter(sk.getOutputStream(), true);
            
            /**
            * Crea un objeto "BufferedReader" para recibir datos del servidor a través del flujo de entrada del socket.
            */
            BufferedReader reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));

            /**
            * Crea un objeto "BufferedReader" para leer datos del teclado a través del flujo de entrada del socket.
            */
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            
            /**
             * // Recibe y lee el message enviado por el teclado.
             * String message = teclado.readLine();
             */
            String message;
            // Crea un bucle de tipo "while" el cuál mantendrá la comunicación activa permitiendo al cliente enviar múltiples mensajes en una misma conversación/comunicación.
            while (true){   // La función "readLine()" devolverá el valor "null" cuando el cliente cierre la conexión.
                // Imprime un mensaje por consola para indicar e interactuar con el usuario.
                System.out.print("\nIntroduzca su mensaje: ");
                
                message = teclado.readLine();
                
                // Declara un condicional de tipo "if" para resevar una palabra con la que salir/finalizar/cerrar el bucle de mensajes del canal de comunicación con el servidor.
                if (message.equalsIgnoreCase("salir")){
                    break;
                }
                
                // Envía el mensaje al servidor.
                writer.println(message);
                
                // Lee la respuesta inicial enviada por el servidor tras recibir el comando (eco en mayúsculas).
                String response = reader.readLine();
                
                // Imprime un mensaje por consola para indicar que el mensaje ha sido enviado al servidor y se ha recibido la respuesta (eco en mayúsculas) por parte del servidor.
                System.out.println("Respuesta por parte del servidor: " + response);
            }
            
        // Captura de posibles excepciones/errores que ocurran durante la ejecución del programa.
        } catch (IOException ioex){
            javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado durante/en el funcionamiento del cliente: " + ioex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.err.println("Error inesperado durante/en el funcionamiento del cliente: " + ioex.getMessage());
            ioex.printStackTrace();
        } finally{
            // Crea un bloque "try-catch" para manejar el cierre/finalización del socket del cliente.
            try{
                if (sk != null) sk.close();
                
            // Captura de posibles excepciones/errores que ocurran durante el cierre/finalización de los sockets.
            } catch (IOException ioex){
                javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado al cerrar/finalizar los sockets: " + ioex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
                System.err.println("Error inesperado al cerrar/finalizar los sockets: " + ioex.getMessage());
                ioex.printStackTrace();
            }
        }
    }
}