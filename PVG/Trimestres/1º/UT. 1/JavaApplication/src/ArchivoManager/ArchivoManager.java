/**
 *
 * @author Rodrigo
 */
package ArchivoManager;


// Importa de la biblioteca/librer�a todos los paquete de "swing".
import javax.swing.*;
// Importa de la biblioteca/librer�a todos los paquete de "awt".
import java.awt.*;
// Importa de la biblioteca/librer�a todos los paquete de "io".
import java.io.*;
// Importa de la biblioteca/librer�a el paquete "Scanner".
import java.util.Scanner;

// Crea la clase principal del programa que hereda de "JFrame".
public class ArchivoManager extends JFrame{
    // Declara los botones que se usar�n en la interfaz.
    private final JButton btnCrear;
    private final JButton btnModificar;
    
    // Declara un selector de archivos para abrir o guardar archivos. hacemos uso de "JFileChooser", un componente gr�fico de "Java Swing".
    private final JFileChooser fileChooser;
    
    // Crea el constructor de la clase.
    public ArchivoManager(){
        // Llama al constructor de "JFrame" con un t�tulo a?adido para la ventana.
        super("Gestor de Archivos");
        
        // Configura la operaci�n por defecto al cerrar la ventana.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Establece el tama?o inicial de la ventana.
        setSize(400, 150);
        
        // Centra la ventana en la pantalla.
        setLocationRelativeTo(null);
        
        // Establece el layout de la ventana a FlowLayout.
        setLayout(new FlowLayout());
        
        // Crea los botones para crear y modificar archivos.
        btnCrear = new JButton("Crear Archivo");
        btnModificar = new JButton("Modificar Archivo");
        
        // Inicializa el selector de archivos.
        fileChooser = new JFileChooser();
        
        // Asigna la acci�n a realizar cuando se presione el bot�n "Crear Archivo".
        btnCrear.addActionListener(e -> crearArchivo());
        
        // Asigna la acci�n a realizar cuando se presione el bot�n "Modificar Archivo".
        btnModificar.addActionListener(e -> modificarArchivo());
        
        // A?ade los botones a la ventana.
        add(btnCrear);
        add(btnModificar);
    }
    
    // M�todo que permite crear un archivo nuevo.
    private void crearArchivo(){
        // Muestra el di�logo para guardar archivo y obtiene la opci�n seleccionada.
        int seleccion = fileChooser.showSaveDialog(this);
        
        // Verifica si el usuario ha aprobado la selecci�n.
        if (seleccion == JFileChooser.APPROVE_OPTION){
            // Obtiene el archivo seleccionado o a crear.
            File archivo = fileChooser.getSelectedFile();
            
            // Solicita al usuario el contenido que se escribir� en el archivo.
            String contenido = JOptionPane.showInputDialog(this, "Introduce el contenido del archivo:");
            
            // Crea un condicional de tipo "if" para verificar que el contenido no sea nulo.
            if (contenido != null){
                // Crea un bloque "try-catch" que crea un FileWriter para escribir en el archivo.
                try (FileWriter escritor = new FileWriter(archivo)){
                    // Escribe el contenido en el archivo.
                    escritor.write(contenido);
                    
                    // Muestra mensaje de �xito.
                    JOptionPane.showMessageDialog(this, "Archivo creado exitosamente.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                
                // Captura posibles errores de escritura.
                } catch (IOException ex){
                    // Muestreo del mensaje de error.
                    JOptionPane.showMessageDialog(this, "Error al crear el archivo: " + ex.getMessage(), "Error en la ejecuci�n", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // M�todo que permite modificar un archivo existente.
    private void modificarArchivo(){
        // Muestra el di�logo para abrir archivo y obtiene la opci�n seleccionada.
        int seleccion = fileChooser.showOpenDialog(this);
        
        // Crea un condicional de tipo "if" para verificar si el usuario ha aprobado la selecci�n.
        if (seleccion == JFileChooser.APPROVE_OPTION){
            // Obtiene el archivo seleccionado.
            File archivo = fileChooser.getSelectedFile();
            
            // Inicializa un StringBuilder para almacenar el contenido actual del archivo.
            StringBuilder contenidoActual = new StringBuilder();
            
            // Crea un bloque "try-catch" con "BufferedReader" para leer el archivo.
            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))){
                // Declara una variable cadena.
                String linea;
                
                // Crea un bucle de tipo "while" para leee l�nea por l�nea hasta el final del archivo.
                while ((linea = lector.readLine()) != null){
                    // Agrega cada l�nea al StringBuilder.
                    contenidoActual.append(linea).append("\n");
                }
            
            // Captura posibles errores de lectura.
            } catch (IOException ex){
                // Muestreo del mensaje de error.
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage(), "Error en la ejecuci�n", JOptionPane.ERROR_MESSAGE);
                
                // Sale del m�todo si ocurre un error.
                return;
            }
            
            // Solicita al usuario modificar el contenido del archivo, mostrando el contenido actual.
            String nuevoContenido = JOptionPane.showInputDialog(this, "Modifica el contenido:", contenidoActual.toString());
            
            // Crea un condicional de tipo "if" para verificar que el nuevo contenido no sea nulo.
            if (nuevoContenido != null){
                // Crea un bloque "try-catch" para crear un "FileWriter" para sobrescribir el archivo.
                try (FileWriter escritor = new FileWriter(archivo)){
                    // Escribe el nuevo contenido en el archivo.
                    escritor.write(nuevoContenido);
                    
                    // Muestreo del mensaje de error.
                    JOptionPane.showMessageDialog(this, "Archivo modificado exitosamente.", "", JOptionPane.INFORMATION_MESSAGE);
                    
                // Captura posibles errores de escritura.
                } catch (IOException ex){
                    // Muestreo del mensaje de error.
                    JOptionPane.showMessageDialog(this, "Error al modificar el archivo: " + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // Crea el m�todo principal del programa. Funci�n/Bloque de interacci�n por consola.
    public static void main(String[] args){
        // Crea el esc�ner 'user' con el que interactuar con el usuario.
        Scanner user = new Scanner(System.in);
        
        // Declara las variables a usar.
        String nombre, decision, decisionFinal, respuesta;
        
        // Se inicializa la variable "decision" con valor nulo, vac�o.
        decision = null;
        
        // Interaci�n por consola.
        System.out.print("Bienvenido a la aplicaci�n 'JADB', por favor, ingrese su nombre: ");
        nombre = user.nextLine();
        System.out.print("\nBienvenido al programa, {" + nombre + "}, Ahora decida si quiere abrir o no la app. (S� / No): ");
        
        // Normaliza la respuesta (sin espacios, primera letra may�scula y letra segunda min�scula).
        decision = user.nextLine();
        decisionFinal = Character.toUpperCase(decision.charAt(0)) + decision.substring(1).toLowerCase();
        respuesta = decisionFinal.trim();
        
        // Crea un condicional de tipo "if" con la funci�n de comparar el valor de la variable "respuesta" y en funci�n de esta clasificarla y ejecutar uno u otro caso.
        //if (respuesta != "S�" || !respuesta != "Si"){
        //if (respuesta != "No" ){
        //if (!respuesta.equalsIgnoreCase("No")){
        if (!"No".equals(respuesta)){
            // Mensaje informativo.
            System.out.println("\n\n\t\t\t\tLa App se ha abierto.\n");
            
            // Lanza la interfaz gr�fica s�lo si dijo que s�.
            SwingUtilities.invokeLater(() -> new ArchivoManager().setVisible(true));
        } else{
            // Mensaje informativo.
            System.err.println("\n\n\t\t\t\tLa App no se ha abierto.");
        }
        
        // Cierra/Finaliza el esc�ner.
        user.close();
    }
}