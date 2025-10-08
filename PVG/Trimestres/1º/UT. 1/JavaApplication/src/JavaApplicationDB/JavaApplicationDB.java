/**
 *
 * @author Rodrigo
 */
package JavaApplicationDB;


// Importa de la biblioteca/librer�a el paquete "Scanner".
import java.util.Scanner;
// Importa de la biblioteca/librer�a el paquete "JFrame".
import javax.swing.JFrame;

// Crea la clase principal del programa.
public class JavaApplicationDB{
    // Crea el m�todo pricipal del programa.
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
            
            // Crea y muestra la interfaz Swing en el Event Dispatch Thread.
            javax.swing.SwingUtilities.invokeLater(new Runnable(){ // Asegura que toda la UI se cree en el hilo correcto.
                // Crea un m�todo vac�o.
                public void run(){ // Contiene el c�digo a ejecutar en el EDT.
                    // Crea un JFrame que contendr� el panel dise?ado.
                    JFrame frame = new JFrame("JADB");
                    
                    // Cierra la aplicaci�n al cerrar la ventana.
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
                    // A?ade el JPanel creado, "ventanaEmergente", al frame.
                    frame.getContentPane().add(new ventanaEmergente());
                    
                    // Ajusta el tama?o del frame a las preferencias de los componentes.
                    frame.pack();
                    
                    // Centra el frame en pantalla.
                    frame.setLocationRelativeTo(null);
                    
                    // Muestra el frame por pantalla.
                    frame.setVisible(true);
                }
            });
        } else{
            // Mensaje informativo.
            System.err.println("\n\n\t\t\t\tLa App no se ha abierto.");
        }
        
    // Cierra/Finaliza el esc�ner.
    user.close();
    }
}