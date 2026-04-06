/**
 *
 * @author Rodrigo
 */
package com.tcp_clientserver_communication.ejercicios_ut5.Ejercicio_2;


// Importa de la biblioteca/librería "io" el paquete "IOException".
import java.io.IOException;
// Importa de la biblioteca/librería "net" el paquete "InetAddress".
import java.net.InetAddress;
// Importa de la biblioteca/librería "net" el paquete "UnknownHostException".
import java.net.UnknownHostException;
// Importa de la biblioteca/librería "util" el paquete "Scanner".
import java.util.Scanner;

// Crea la clase principal del programa, "ResolucionDNS".
public class ResolucionDNS{
    /**
     * Define un objeto privado de tipo "final Scnanner" llamado "user".
     * Este objeto se utilizará para realizar la interación consola/cliente (terminal/usuario). Leerá la entrada del usuario desde la consola.
     * 
     * La palabra clave "final" es un atributo que indica/redefine cómo será el comportamienro de la variable.
     * La variable no puede ser modificada después de su inicialización. 
     */
    private static final Scanner user = new Scanner(System.in);
    
    // Crea el método principal del programa, "main".
    public static void main(String[] args){
        // Imprime un mensaje por consola para indicar al usuario que el programa de resolución DNS está inicializado y listo para recibir entradas.
        System.out.println("Programa/Sistema de resolución DNS inicializado y listo para recibir entradas.");
        
        // Imprime un mensaje por consola para indicar e interactuar con el usuario.
        System.out.print("\nIntroduzca un dominio o una dirección IP: ");
        String input = user.nextLine();

        /**
         * Crea un bloque "try-catch" para manejar la resolución DNS.
         * Crea un objeto "InetAddress" utilizando el método "InetAddress.getByName()" para resolver el nombre del host o la dirección IP introducida por el usuario.
         */
        try{
            /**
             * El método o función "InetAddress.getByName()" se utiliza para resolver un nombre de host o una dirección IP en un objeto "InetAddress".
             * Su uncionamiento es el siguiente:
             *     InetAddress.getByName()
             *         - Si recibe un dominio   →   obtiene la IP.
             *         - Si recibe una IP       →   intenta obtener el nombre del host.
            */
            InetAddress address = InetAddress.getByName(input);

            // Obtiene el nombre del host.
            String hostName = address.getHostName();

            // Obtiene la dirección IP.
            String ip = address.getHostAddress();

            // Muestreo de  los resultados.
            System.out.println("Nombre del host (o anfitrión): " + hostName);
            System.out.println("Dirección IP (Protocolo de Internet): " + ip);

        // Captura de posibles excepciones/errores relacionados con la resolución DNS (por ejemplo: dominio no existente o no resolvible) durante la ejecución del bloque "try".
        }catch (UnknownHostException uhe){
            javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado: No se pudo resolver el dominio o IP introducida: " + uhe.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.err.println("Error inesperado: No se pudo resolver el dominio o IP introducida: " + uhe.getMessage());
            uhe.printStackTrace();

        // Captura de posibles excepciones/errores relacionados con la entrada/salida durante la ejecución del bloque "try".
        } catch (IOException ioex){
            javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado al manejar la conexión del cliente: " + ioex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.err.println("Error inesperado al manejar la conexión del cliente: " + ioex.getMessage());
            ioex.printStackTrace();

        // Captura de posibles excepciones/errores relacionados con cualquier otro error inesperado durante la ejecución del bloque "try".
        } catch (Exception ex){
            javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "ERROR de ejecución", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.err.println("Error inesperado: " + ex.getMessage());
            ex.printStackTrace();

        // Finaliza el bloque de ejecución cerrando/finalizando recursos, en caso de ser necesario.
        } finally{
            // Cierra el Scanner ('user') si no es nulo, es decir, si se ha inicializado correctamente.
            if (user != null) user.close();
        }
    }
}