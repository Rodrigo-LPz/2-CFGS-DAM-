// Ejercicio 3: Sanitización de Salidas (Prevención de XSS simplificado)
  // Imagina un foro donde los usuarios escriben comentarios que luego se muestran a otros. Crea un método prepararComentario(String texto) que reciba un comentario y sustituya caracteres potencialmente peligrosos (como < por &lt; y > por &gt;). Esto evita que si alguien escribe una etiqueta de script (ej: <script>alert('hack')</script>), esta se ejecute en el navegador de otros usuarios.
package Ejercicios_UT6.Ejercicio_3;




// Clase "Foro", clase principal del programa.
public class Foro {
  // Método estático "prepararComentario", recibe un comentario y sustituye los caracteres potencialmente peligrosos para prevenir ataques XSS.
  public static String prepararComentario(String texto) {
    return texto
      .replace("<", "&lt;")
      .replace(">", "&gt;");
  }

  // Método/función "main", método principal del programa.
  public static void main(String[] args) {
    // Comentario normal de un usuario.
    String comentarioNormal = "Hola, me parece un tema muy interesante!";

    // Comentario posiblemente malicioso de un usuario con etiqueta script [intento de XSS (o Cross-Site Scripting). Vulnerabilidad de seguridad web donde un atacante inyecta scripts maliciosos en sitios web legítimos].
    String comentarioMalicioso = "<script>alert('hack')</script>";

    // Muestreo del procedimiento y resultados del posteo de un comentario normal.
    System.out.println("-- Comentario normal --");
    System.out.println("\tOriginal  : " + comentarioNormal);
    System.out.println("\tSanitizado: " + prepararComentario(comentarioNormal));

    // Muestreo del procedimiento y resultados del posteo de un comentario malicioso.
    System.out.println("\n-- Comentario malicioso --");
    System.out.println("\tOriginal  : " + comentarioMalicioso);
    System.out.println("\tSanitizado: " + prepararComentario(comentarioMalicioso));
  }
}