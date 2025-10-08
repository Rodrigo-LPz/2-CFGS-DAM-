/**
 *
 * @author Rodrigo
 */
package com.mycompany.multas;


// Importa de la biblioteca/librería todos los paquetes de "util".
import java.util.*;

// Crea la clase principal del programa
public class Main{
    // Declara una variable privada.
    private static double saldo = 1000.0;
        // Declara un 'Map' privado. Un diccionario que guarda dos valores, una cadena, un "String"; y una lista de valroes procedentes de la clase "Multas", "List<Multas>". 'Multas' Map<String, List<Multas>>.
    private static final Map<String, List<Multas>> multasPendientes = new HashMap<>();
        // Declara un 'Map' privado. Un diccionario que guarda dos valores, una cadena, un "String"; y una lista de valroes procedentes de la clase "Pago", "List<Pago>". 'Multas' Map<String, List<Pago>>.
    private static final Map<String, List<Pago>> multasPagadas = new HashMap<>();
    
    // Crea el método principal del programa.
    public static void main(String[] args){
        // Crea el escáner 'user'.
        Scanner user = new Scanner(System.in);
        
        // Llama, desde el método 'Main', al mérodo "cargarDatosPrueba".
        cargarDatosPrueba();
        
        // Declara una variable numérica.
        int opcion;
        
        // Crea un bucle de tipo "do-while" con el que asegurarse de que al menos el programa ejecute sí o sí lo que hay dentro de este.
        do{
            System.out.println("\n\t<========== SISTEMA DE GESTIÓN DE MULTAS ==========>");
            System.out.printf("\t\t\t\t\t\t\tSaldo actual: %.2f€%n", saldo);
            System.out.println("\t\t\t1. Consultar multas pendientes.\n\t\t\t2. Pagar multa.\n\t\t\t3. Consultar multas anteriores.\n\t\t\t4. Salir.");
            System.out.print("\n\tElige la opción a tramitar: ");
            
            // Crea un bucle de tipo "while" con el que asegurarse de crear una validación simple.
            //while (!user.hasNextInt()){
                //user.nest();
            //}
            //opcion = user.nextInt();
            while (!user.hasNextInt()) user.next();
            opcion = user.nextInt();
            
            // Crea un condicional de tipo "switch" para cubrir todos los casos en los que la variable "opcion" tenga un valor distinto.
            switch (opcion){
                case 1 -> consultarMultas(user);
                case 2 -> pagarMulta(user);
                case 3 -> consultarHistorial(user);
                case 4 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
        
        // Cierra/Finaliza el escáner.
        user.close();
    }
    
    // Crea un método privado "cargarDatosPrueba".
    private static void cargarDatosPrueba(){
        // Agrega iteraciones, objetos, a la clase "Multas".
        agregarMulta(new Multas("Exceso de velocidad", 120.50, "2548DHK"));
        agregarMulta(new Multas("No usar cinturón", 60.0, "2548SSH"));
        agregarMulta(new Multas("Estacionamiento indebido", 80.0, "6503alp"));
        agregarMulta(new Multas("Semáforo en rojo", 200.0, "1789XYZ"));
        agregarMulta(new Multas("No usar cinturón", 90.0, "4566DEF"));
    }
    
    // Crea un método privado "agregarMulta" que se conecta/llama a la clase "Multas".
    private static void agregarMulta(Multas multa){
        // Se llma al 'Map' "multasPendientes" donde busca la clave pasada como "multa.getMatricula()". Al terminar la búsqueda, en caso de que si exista esa clave, devolverá el valor actual existente, "List<Multas>". Pero, en caso contrario, crea un nuevo valor usando la función pasada como "k -> new ArrayList<>()).add(multa)".
        multasPendientes.computeIfAbsent(multa.getMatricula(), k -> new ArrayList<>()).add(multa); /* "k -> new ArrayList<>" es una lambda, una función anónima. */
    }
    
    // Crea un método privado "agregarPago" que se conecta/llama a la clase "Pago".
    private static void agregarPago(Pago pago){
        // Se llma al 'Map' "multasPagadas" donde busca la clave pasada como "pago.getMatricula()". Al terminar la búsqueda, en caso de que si exista esa clave, devolverá el valor actual existente, "List<Pago>". Pero, en caso contrario, crea un nuevo valor usando la función pasada como "k -> new ArrayList<>()).add(pago)".
        multasPagadas.computeIfAbsent(pago.getMatricula(), k -> new ArrayList<>()).add(pago); /* "k -> new ArrayList<>" es una lambda, una función anónima. */
    }
    
    // Crea un método privado "pedirMatricula" por el cual introducir la matrícula del vehículo. Llama al escáner 'user' del método "Main".
    private static String pedirMatricula(Scanner user){
        System.out.print("\n\nIntroduce la matrícula del vehículo: ");
        user.nextLine(); /* Consume el salto de línea sobrante. */
        String input = user.nextLine().trim();
        return input.toUpperCase();
    }
    
    // Crea un método privado "consultarMultas" Llama al escáner 'user' del método "Main".
    private static void consultarMultas(Scanner user){
        // Crea una variable de cadena donde su valor será el último obtenido por el escáner del método "pedirMatricula".
        String matricula = pedirMatricula(user);
        
        // Crea una lista "lista" que se conecta a la clase "Multas". Esta lista busca en el "Map", "multasPendientes", la clave 'matricula'. Si la clave existe devuelve el valor asociado a esta "List<Multas>". Para el caso de que esta clave no exista, se devolverá el valor por defecto ("getOrDefault") que nosotros le pasemos, es decir, "collections.emptyList()".
        List<Multas> lista = multasPendientes.getOrDefault(matricula, Collections.emptyList()); /* "Collections.emptyList()" es un método de Java con la funcionalidad de devolver una lista vacía inmutable, es decir, no se podrá modificar ni con ".add", ni con ".remove", ni con ningún otro. */
        
        // Cres un condicional de tipo "if-else" donde comprobar si la lista "lista" tiene algún valor introducido. Y, en susodicho caso, mostrarlas por pantalla.
        if (lista.isEmpty()){
            System.out.println("No hay multas pendientes para el vehículo " + matricula + ".");
        } else{
            System.out.println("\nMULTAS PENDIENTES:");
            
            // Crea un bucle de tipo "for" dode va leyendo uno a uno los objetos almacenados en la lista "lista" y, a su vez ir imprimiéndolos de a uno.
            for (int i = 0; i < lista.size(); i++){
                System.out.println((i + 1) + ". " + lista.get(i));
            }
        }
    }
    
    // Crea un método privado "pagarMulta" Llama al escáner 'user' del método "Main".
    private static void pagarMulta(Scanner user){
        // Crea una variable de cadena donde su valor será el último obtenido por el escáner del método "pedirMatricula".
        String matricula = pedirMatricula(user);
        
        // Crea una lista "lista" que se conecta a la clase "Multas". Esta lista busca en el "Map", "multasPendientes", la clave 'matricula'. Si la clave existe devuelve el valor asociado a esta "List<Multas>". Para el caso de que esta clave no exista, se devolverá el valor por defecto ("getOrDefault") que nosotros le pasemos, es decir, "collections.emptyList()".
        List<Multas> lista = multasPendientes.getOrDefault(matricula, Collections.emptyList()); /* "Collections.emptyList()" es un método de Java con la funcionalidad de devolver una lista vacía inmutable, es decir, no se podrá modificar ni con ".add", ni con ".remove", ni con ningún otro. */
        
        // Cres un condicional de tipo "if" donde comprobar si la lista "lista" tiene algún valor introducido. En caso de no tener ninguno, mostrarlo por pantalla.
        if (lista.isEmpty()){
            System.out.println("No hay multas pendientes para el vehículo " + matricula + ".");
            return;
        }
        
        System.out.println("\nMULTAS PENDIENTES:");
        
        // Crea un bucle de tipo "for" dode va leyendo uno a uno los objetos almacenados en la lista "lista" y, a su vez ir imprimiéndolos de a uno.
        for (int i = 0; i < lista.size(); i++){
            System.out.println((i + 1) + ". " + lista.get(i));
        }
        
        System.out.print("Selecciona el número de multa a pagar: ");
        
        // Crea un bucle de tipo "while" con el que asegurarse de crear una validación simple.
        while (!user.hasNextInt()) user.next();
        
        // Declara una variable numérica.
        int seleccionado = user.nextInt();
        
        // Crea un condicional de tipo "if" con el que comprobar que el último valor de la variable "seleccionado" está comprendido entre un rango de un mínimo (1), y un máximo [lista.size()]. En caso de no estarlo, mostrarlo por pantalla.
        if (seleccionado < 1 || seleccionado > lista.size()){
            System.out.println("Selección inválida.");
            return;
        }
        
        // Se conecta a la lista de objetos de la clase "Multas", de ella se extraerá gracias a la función ".get()" el objeto guardado en ella. Especificado con el valor de la variable "seleccionado".
        Multas multa = lista.get(seleccionado - 1);
        
        // Declara una variable decimal.
        double importe = multa.getImporteAPagar();
        
        // Crea un condicional de tipo "if" que comprueba si el usuario en cuestión tiene el saldo suficiente como para abonar el cobro de la multa. Para dicho caso se procederá con lo que está dentro del condicional.
        if (saldo >= importe){
            // Se resta el valor del importe al del saldo.
            saldo -= importe;
            
            // Crea un nuevo objeto "Pago" que se conecta al método "agregarPago" y éste a su vez llama al constructor de la clase "Pago".
            agregarPago(new Pago(importe, matricula, multa.getDescripcion()));
            
            // Quita/Elimina dicho registro de multa de la lista.
            lista.remove(multa);
            
            // Crea un condicional de tipo "if" que comprueba si al usuario con 'X' matrícula le queda alguna multa por pagar. En caso de que no se elimina la entrada del 'Map' "multasPendientes".
            if (lista.isEmpty()) multasPendientes.remove(matricula);
            System.out.printf("Multa pagada correctamente. Nuevo saldo: %.2f€%n", saldo);
        } else{
            System.out.printf("Saldo insuficiente. Necesitas %.2f€ para pagar la multa, tu saldo es %.2f€%n", importe, saldo);
        }
    }
    
    // Crea un método privado "consultarHistorial" Llama al escáner 'user' del método "Main".
    private static void consultarHistorial(Scanner user){
        // Crea una variable de cadena donde su valor será el último obtenido por el escáner del método "pedirMatricula".
        String matricula = pedirMatricula(user);
        
        // Crea una lista "lista" que se conecta a la clase "Pago". Esta lista busca en el "Map", "multasPagadas", la clave 'matricula'. Si la clave existe devuelve el valor asociado a esta "List<Pago>". Para el caso de que esta clave no exista, se devolverá el valor por defecto ("getOrDefault") que nosotros le pasemos, es decir, "collections.emptyList()".
        List<Pago> lista = multasPagadas.getOrDefault(matricula, Collections.emptyList()); /* "Collections.emptyList()" es un método de Java con la funcionalidad de devolver una lista vacía inmutable, es decir, no se podrá modificar ni con ".add", ni con ".remove", ni con ningún otro. */
        
        // Cres un condicional de tipo "if-else" donde comprobar si la lista "lista" tiene algún valor introducido. Y, en susodicho caso, mostrarlas por pantalla.
        if (lista.isEmpty()){
            System.out.println("No hay multas abonadas para el vehículo " + matricula + ".");
        } else{
            System.out.println("\nMULTAS ABONADAS:");
            for (int i = 0; i < lista.size(); i++){
                System.out.println((i + 1) + ". " + lista.get(i));
            }
        }
    }
}