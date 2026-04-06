/**
 *
 * @author Rodrigo
 */
package com.tcp_clientserver_communication.ejercicios_ut5.Ejercicio_3;


// Importa de la biblioteca/librería "io" el paquete "IOException".
import java.io.IOException;
// Importa de la biblioteca/librería "ftp" el paquete "FTPClient".
import org.apache.commons.net.ftp.FTPClient;
// Importa de la biblioteca/librería "ftp" el paquete "FTPFile".
import org.apache.commons.net.ftp.FTPFile;
// Importa de la biblioteca/librería "ftp" el paquete "FTPReply".
import org.apache.commons.net.ftp.FTPReply;

// Crea la clase principal del programa, "ClienteFTP".
public class ClienteFTP{
    // Crea el método principal del programa, "main".
    public static void main(String[] args){
        // Crea el objeto cliente FTP.
        FTPClient ftp = new FTPClient();
        
        // Servidor FTP público de prueba.
        /**
         * String servidor = "ftp.dlptest.com";ftp.gnu.org
         */
        String servidor = "ftp.gnu.org";
        int puerto = 21;
        
        /**
         * Crea un bloque "try-catch" para manejar la conexión al servidor FTP, el inicio de sesión, la obtención de la lista de archivos y el cierre de sesión.
         *     1. Conexión al servidor FTP.
         *     2. Comprobación/Verificación del código de respuesta del servidor.
         *     3. Login anónimo.
         *     4. Listar archivos del directorio raíz.
         *     5. Cerrar sesión.
         */
        try{
            // Imprime un mensaje por consola para indicar al usuario que se está conectando al servidor FTP.
            System.out.println("Conectando al servidor FTP...");

            // Conexión al servidor FTP.
            ftp.connect(servidor, puerto);
            
            // Comprobación/Verificación del código de respuesta del servidor.
            int respuesta = ftp.getReplyCode();
            
            // Imprime un mensaje por consola para mostrar el código de respuesta del servidor FTP.
            System.out.println("Código de respuesta del servidor: " + respuesta);

            // Crea un condicional de tipo "if" para verificar si el código de respuesta del servidor no es positivo. Si es el caso, se imprime un mensaje por consola indicando que el servidor FTP rechazó la conexión, se desconecta del servidor y se finaliza la ejecución del programa. En caso contrario, Si la conexión fue exitosa, se imprime un mensaje por consola indicando que la conexión se estableció correctamente. 
            /**
             * if (!org.apache.commons.net.ftp.FTPReply.isPositiveCompletion(respuesta)){
             */
            if (!FTPReply.isPositiveCompletion(respuesta)){
                System.out.println("El servidor FTP rechazó la conexión.");
                ftp.disconnect();   // Desconexión del servidor FTP.
                return;
            }
            
            System.out.println("Conexión establecida correctamente.");
            
            // Login anónimo.
            // Crea una variable de tipo "boolean" llamada "login" y le asigna como valor el resultado del método "ftp.login()" con los parámetros "anonymous" para el nombre de usuario y "anonymous" para la contraseña. Este método intenta iniciar sesión en el servidor FTP utilizando credenciales anónimas.
            boolean login = ftp.login("anonymous", "");
            
            // Crea un condicional de tipo "if" para verificar si el inicio de sesión no fue exitoso. Si es el caso, se imprime un mensaje por consola indicando que no se pudo iniciar sesión, se cierra la sesión en el servidor FTP y se finaliza la ejecución del programa. En caso contrario, Si el inicio de sesión fue exitoso, se imprime un mensaje por consola indicando que la sesión se inició como usuario anónimo.
            if (!login){
                System.out.println("No se pudo iniciar sesión.");
                ftp.logout();   // Cierra la sesión en el servidor FTP.
                return;
            }

            // Establece el modo pasivo para la transferencia de archivos.
            ftp.enterLocalPassiveMode();    // El modo pasivo es un modo de conexión FTP en el que el cliente inicia la conexión de datos al servidor, lo que puede ser útil para superar problemas de firewall o NAT (Network Address Translation) o en español, Traducción de Direcciones de Red).  
            
            System.out.println("Sesión iniciada como usuario anónimo.");

            // Listar archivos del directorio raíz.
            System.out.println("\nArchivos en el servidor:\n");

            // Crea un array de objetos "FTPFile" llamado "archivos" y le asigna como valor el resultado del método "ftp.listFiles()". Este método obtiene una lista de archivos y directorios en el directorio actual del servidor FTP.
            FTPFile[] archivos = ftp.listFiles();
            
            // Crea un bucle "for-each" para iterar sobre cada archivo en el array "archivos". Para cada archivo, se determina si es un directorio o un archivo regular y se imprime su nombre por consola con un prefijo que indica su tipo.
            for (FTPFile archivo : archivos){
                // Crea una variable de tipo "String" llamada "tipo" para almacenar el tipo de archivo (directorio o archivo regular).
                String tipo;

                // Crea un condicional de tipo "if" para verificar si el archivo es un directorio utilizando el método "archivo.isDirectory()". Si es el caso, se asigna el valor "[DIRECTORIO]" a la variable "tipo". En caso contrario, se asigna el valor "[ARCHIVO]" a la variable "tipo".
                if (archivo.isDirectory()){
                    tipo = "[DIRECTORIO]";
                } else{
                    tipo = "[ARCHIVO]";
                }

                // Imprime por consola el tipo de archivo y su nombre utilizando la variable "tipo" y el método "archivo.getName()".
                System.out.println(tipo + " " + archivo.getName());
            }

            // Cerrar sesión
            ftp.logout();

            // Imprime un mensaje por consola para indicar que la sesión se ha cerrado exitosamente.
            System.out.println("\nSesión cerrada.");

        // Captura de posibles excepciones/errores relacionados con la entrada/salida durante la ejecución del bloque "try".
        } catch (IOException ioex){
            javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado durante la conexión FTP: " + ioex.getMessage(), "ERROR de conexión FTP", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.err.println("Error inesperado durante la conexión FTP: " + ioex.getMessage());
            ioex.printStackTrace();

        
        // Finaliza el bloque de ejecución cerrando/finalizando recursos, en caso de ser necesario.
        } finally{
            // Crea un bloque "try-catch" para manejar el cierre/finalización de la conexión FTP.
            try{
                // Crea un condicional de tipo "if" para verificar si el cliente FTP está conectado utilizando el método "ftp.isConnected()". Si es el caso, se desconecta del servidor FTP utilizando el método "ftp.disconnect()" y se imprime un mensaje por consola indicando que se ha desconectado del servidor.
                if (ftp.isConnected()){
                    ftp.disconnect();   // Desconexión del servidor FTP.

                    // Imprime un mensaje por consola para indicar que se ha desconectado del servidor.
                    System.out.println("Desconectado del servidor.");
                }
            
            // Captura de posibles excepciones/errores que ocurran durante el cierre/finalización de la conexión FTP.
            } catch (IOException ioex){
            javax.swing.JOptionPane.showMessageDialog(null, "Error inesperado al cerrar la conexión: " + ioex.getMessage(), "ERROR al cerrar conexión FTP", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.err.println("Error inesperado al cerrar la conexión: " + ioex.getMessage());
            ioex.printStackTrace();
            }
        }
    }
}