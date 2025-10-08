/**
 *
 * @author Rodrigo
 */
package ArchivoManager;


// Importa de la biblioteca/librería todos los paquete de "swing".
import javax.swing.*;
// Importa de la biblioteca/librería todos los paquete de "awt".
import java.awt.*;
// Importa de la biblioteca/librería todos los paquete de "io".
import java.io.*;
// Importa de la biblioteca/librería el paquete "Scanner".
import java.util.Scanner;

// Crea la clase principal del programa que hereda de "JFrame".
public class ArchivoManager extends JFrame{
    // Declara los botones que se usarán en la interfaz.
    private final JButton btnCrear;
    private final JButton btnModificar;
    
    // Declara un selector de archivos para abrir o guardar archivos. hacemos uso de "JFileChooser", un componente gráfico de "Java Swing".
    private final JFileChooser fileChooser;
    
    // Crea el constructor de la clase.
    public ArchivoManager(){
        // Llama al constructor de "JFrame" con un título a?adido para la ventana.
        super("Gestor de Archivos");
        
        // Configura la operación por defecto al cerrar la ventana.
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
        
        // Asigna la acción a realizar cuando se presione el botón "Crear Archivo".
        btnCrear.addActionListener(e -> crearArchivo());
        
        // Asigna la acción a realizar cuando se presione el botón "Modificar Archivo".
        btnModificar.addActionListener(e -> modificarArchivo());
        
        // A?ade los botones a la ventana.
        add(btnCrear);
        add(btnModificar);
    }
    
    // Método que permite crear un archivo nuevo.
    private void crearArchivo(){
        // Muestra el diálogo para guardar archivo y obtiene la opción seleccionada.
        int seleccion = fileChooser.showSaveDialog(this);
        
        // Verifica si el usuario ha aprobado la selección.
        if (seleccion == JFileChooser.APPROVE_OPTION){
            // Obtiene el archivo seleccionado o a crear.
            File archivo = fileChooser.getSelectedFile();
            
            // Solicita al usuario el contenido que se escribirá en el archivo.
            String contenido = JOptionPane.showInputDialog(this, "Introduce el contenido del archivo:");
            
            // Crea un condicional de tipo "if" para verificar que el contenido no sea nulo.
            if (contenido != null){
                // Crea un bloque "try-catch" que crea un FileWriter para escribir en el archivo.
                try (FileWriter escritor = new FileWriter(archivo)){
                    // Escribe el contenido en el archivo.
                    escritor.write(contenido);
                    
                    // Muestra mensaje de éxito.
                    JOptionPane.showMessageDialog(this, "Archivo creado exitosamente.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                
                // Captura posibles errores de escritura.
                } catch (IOException ex){
                    // Muestreo del mensaje de error.
                    JOptionPane.showMessageDialog(this, "Error al crear el archivo: " + ex.getMessage(), "Error en la ejecución", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // Método que permite modificar un archivo existente.
    private void modificarArchivo(){
        // Muestra el diálogo para abrir archivo y obtiene la opción seleccionada.
        int seleccion = fileChooser.showOpenDialog(this);
        
        // Crea un condicional de tipo "if" para verificar si el usuario ha aprobado la selección.
        if (seleccion == JFileChooser.APPROVE_OPTION){
            // Obtiene el archivo seleccionado.
            File archivo = fileChooser.getSelectedFile();
            
            // Inicializa un StringBuilder para almacenar el contenido actual del archivo.
            StringBuilder contenidoActual = new StringBuilder();
            
            // Crea un bloque "try-catch" con "BufferedReader" para leer el archivo.
            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))){
                // Declara una variable cadena.
                String linea;
                
                // Crea un bucle de tipo "while" para leee línea por línea hasta el final del archivo.
                while ((linea = lector.readLine()) != null){
                    // Agrega cada línea al StringBuilder.
                    contenidoActual.append(linea).append("\n");
                }
            
            // Captura posibles errores de lectura.
            } catch (IOException ex){
                // Muestreo del mensaje de error.
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage(), "Error en la ejecución", JOptionPane.ERROR_MESSAGE);
                
                // Sale del método si ocurre un error.
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
    
    // Crea el método principal del programa. Función/Bloque de interacción por consola.
    public static void main(String[] args){
        // Crea el escáner 'user' con el que interactuar con el usuario.
        Scanner user = new Scanner(System.in);
        
        // Declara las variables a usar.
        String nombre, decision, decisionFinal, respuesta;
        
        // Se inicializa la variable "decision" con valor nulo, vacío.
        decision = null;
        
        // Interación por consola.
        System.out.print("Bienvenido a la aplicación 'JADB', por favor, ingrese su nombre: ");
        nombre = user.nextLine();
        System.out.print("\nBienvenido al programa, {" + nombre + "}, Ahora decida si quiere abrir o no la app. (Sí / No): ");
        
        // Normaliza la respuesta (sin espacios, primera letra mayúscula y letra segunda minúscula).
        decision = user.nextLine();
        decisionFinal = Character.toUpperCase(decision.charAt(0)) + decision.substring(1).toLowerCase();
        respuesta = decisionFinal.trim();
        
        // Crea un condicional de tipo "if" con la función de comparar el valor de la variable "respuesta" y en función de esta clasificarla y ejecutar uno u otro caso.
        //if (respuesta != "Sí" || !respuesta != "Si"){
        //if (respuesta != "No" ){
        //if (!respuesta.equalsIgnoreCase("No")){
        if (!"No".equals(respuesta)){
            // Mensaje informativo.
            System.out.println("\n\n\t\t\t\tLa App se ha abierto.\n");
            
            // Lanza la interfaz gráfica sálo si dijo que sí.
            SwingUtilities.invokeLater(() -> new ArchivoManager().setVisible(true));
        } else{
            // Mensaje informativo.
            System.err.println("\n\n\t\t\t\tLa App no se ha abierto.");
        }
        
        // Cierra/Finaliza el escáner.
        user.close();
    }
}