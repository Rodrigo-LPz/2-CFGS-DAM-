/**
 *
 * @author Rodrigo
 */
package com.mycompany.aeropuerto;


// Importa de la biblioteca/librería todos los paquetes pertenecientes de/a "io".
import java.io.*;
/*
*import static java.lang.System.exit;
*/
// Importa de la biblioteca/librería todos los paquetes pertenecientes de/a "util".
import java.util.*;
// Importa de la biblioteca/librería el paquete "JOptionPane".
import javax.swing.JOptionPane;

// Crea la clase principal del programa.
public class Aeropuerto{
    // -------------------------- 'Apartado 1' --------------------------
    // Crea el método que obtiene los aeropuertos de un país dado en el fichero de localizaciones.
    public static List<String> obtenerAeropuerto (String nombreFichero, String pais){
        // Crea la lista donde guardaremos las líneas que cumplan el filtro.
        List<String> aeropuertos = new ArrayList<>();
        
        // Bloque "try-catch" que abre el "BufferedReader" y se asegura de cerrarlo automáticamente al finalizar.
            // "FileReader" se encarga de abrir un archivo de texto y leer su contenido carácter por carácter.
            //"BufferedReader" envuelve a "FileReader" y lo hace mucho más eficiente. En lugar de leer carácter a carácter, carga bloques grandes de texto (un "buffer") en memoria, permitiendo después leer líneas completas o texto más cómodamente.
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))){
            // Declara una variable de tipo cadena para almacenar cada línea leída del fichero.
            String linea;
            
            // Blucle "while" que lee línea a línea hasta que "br.readLine()" devuelva "null" (fin de fichero), es decir, hasta que llegue al final del fichero.
            while ((linea = br.readLine()) != null){
                //Limpia caracteres invisibles (por ejemplo, BOM o símbolos no imprimibles).
                    // El método "replaceAll" reemplaza partes del texto que coinciden con una expresión regular (regex).
                    // La expresión "\p{Print}" en regex de Java significa todos los caracteres imprimibles (letras, números, signos de puntuación, espacios normales, etc.).
                    // Mientras que el símbolo/signo "^" impica/significa, dentro de los corchetes, lo contrario.
                        // Por tanto, "[^\p{Print}]" significa cualquier carácter que no sea imprimible. De esta forma, reemplaza cualquier carácter que no sea imprimible por nada (es decir, lo elimina).
                linea = linea.replaceAll("[^\\p{Print}]", "").trim();

                
                // Divide la línea por comas. Quedando de la siguiente forma: código, país, latitud, longitud.
                String[] partes = linea.split(",");
                
                // Comprobamos que haya al menos 2 campos (código y país) para evitar errores en líneas mal formadas.
                if (partes.length >= 2){
                    // El segundo campo (índice 1) corresponde al país.
                    String paisActual = partes[1].replaceAll("[^\\p{Print}]", "").trim();
                    
                    /* (Para el caso de impresión con formateo)
                    // Formate la cadena.
                    *    // La expresion "String.format" formatea la cadena la cadena "lineaFormateada" personalizando su salida de impresión.
                    *        // El atributo "%" indica que viene un formato.
                    *        // El atributo "-" alinea a la izquierda.
                    *        // Los atributos "10", "15" y "20" establecen un ancho mínimo de/para cada columna (en caracteres).
                    *        // El atributo "%n" es un salto de línea portable.
                    *String lineaFormateada = String.format("%-10s %-10s %-15s %-20s", partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim());
                    */
                    
                    // Si el parámetro 'pais' es "cualquiera" (ignorando mayúsculas y minúsculas), o si el país de la línea coincide con el país solicitado/pedido (ignorando mayúsculas y minúsculas), entencoes añade la línea (aeropuerto) a la lista resultante.
                    if (pais.trim().equalsIgnoreCase("cualquiera") || paisActual.equalsIgnoreCase(pais)){
                        // Añade la línea completa (con espacios externos recortados, es decir, limpia los espacios sobrantes).
                        aeropuertos.add("\t\t\t\t\t\t\t" + linea.trim());
                        /* (Para el caso de impresión con formateo)
                        *aeropuertos.add("\t\t\t\t\t\t\t" + lineaFormateada.trim());
                        */
                    }
                }
            }
        // Captura la excepción. Si ocurre cualquier error de E/S (archivo no existe, permisos, etc.), se captura aquí.
        } catch (IOException ioe){
            JOptionPane.showMessageDialog(null, "Error al leer el fichero: " + ioe.getMessage(), "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }
        
        // Declaramos este bloque después del "try-catch", pues si se da el caso mostramos el mensaje de error una vez, tras haber leído todas y cada una de las lineas; y así no aparecer en cada una de las líneas en que no aparezca el valor del campo 'pais'.
            // Si el parámetro 'pais' es distinto de todo lo anterior (Ejemplo: "Spaain") muestra un mensaje emergente de advertencia de error.
        if (aeropuertos.isEmpty()){
            /*
            *JOptionPane.showMessageDialog(null, "Error en el pais u opción de búsqueda introducido. No existe o no lo reconoce el sistema.", "Error de búsqueda", JOptionPane.ERROR_MESSAGE);
            */
            /*
            * exit();
            */
            exit_cerrarPrograma("Error el pais [" + pais +"] u opción de búsqueda introducido. No existe o no lo reconoce el sistema. O no se encontraron aeropuertos para operativos/activos en dicho país introducido.");
        }
        
        // Devuelve la lista con las líneas (aeropuertos) que cumplen el filtro.
        return aeropuertos;
    }
    
    
    // -------------------------- 'Apartado 2' --------------------------
    // Crea el método que obtiene las rutas entre aeropuertos del listado (del mismo país).
    public static List<String> obtenerRutas (String nombreFichero, List<String> aeropuertos){
        // Crea la lista donde guardaremos las rutas válidas.
        List<String> rutas = new ArrayList<>();
        
        // Bloque "try-catch" que abre el "BufferedReader" y se asegura de cerrarlo automáticamente al finalizar.
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))){
            // Declara una variable de tipo cadena para almacenar cada línea leída del fichero.
            String linea;
            
            // Blucle "while" que lee línea a línea hasta que "br.readLine()" devuelva "null" (fin de fichero), es decir, hasta que llegue al final del fichero.
            while ((linea = br.readLine()) != null){
                //Limpia caracteres invisibles (por ejemplo, BOM o símbolos no imprimibles).
                linea = linea.replaceAll("[^\\p{Print}]", "").trim();

                // Divide la línea por comas. Quedando de la siguiente forma: origen, destino, precio, duración, aerolínea.
                String[] partes = linea.split(",");
                
                // Comprobamos que haya al menos 5 campos (código y país) para evitar errores en líneas mal formadas.
                if (partes.length >= 5){
                    // Declara dos variables para extraer el código del aeropuerto de origen y el de destino.
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();

                    // A modo de comrpobante (para darnos más seguridad). Recorre la lista de aeropuertos y verifica si ambos están incluidos.
                    boolean origenValido = false;
                    boolean destinoValido = false;

                    for (String aeropuerto : aeropuertos){
                        // El primer campo (índice 0) corresponde al código del aeropuerto; usamos trim() para quitar espacios en blanco alrededor.
                        String codigo = aeropuerto.split(",")[0].trim();
                        
                        // Condicional "if" que comprueba si el campo que se está leyendo "codigo" es igual/equivalente a "origen", en susodicho caso, covierte el valor de "origenValido" en verdadero "true".
                        if (codigo.equalsIgnoreCase(origen)) origenValido = true;

                        // Condicional "if" que comprueba si el campo que se está leyendo "codigo" es igual/equivalente a "destino", en susodicho caso, covierte el valor de "destinoValido" en verdadero "true".
                        if (codigo.equalsIgnoreCase(destino)) destinoValido = true;
                        
                        // Condicional "if" donde si ambos campos son válidos, salimos del bucle ("for").
                        if (origenValido && destinoValido) break;
                   }
                    
                    /* (Para el caso de impresión con formateo)
                    *String lineaFormateada = String.format("%-7s %-14s %-7s  %-18s %-7s", partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim(), partes[4].trim());
                    */
                    
                    // Condicional "if" que comprueba si ambos aeropuertos pertenecen a la lista, en dicho caso añade añde la ruta.
                    if (origenValido && destinoValido){
                        // Añade la línea completa (con espacios externos recortados, es decir, limpia los espacios sobrantes).
                        rutas.add("\t\t\t\t\t\t\t" + linea.trim());
                        /* (Para el caso de impresión con formateo)
                        *rutas.add("\t\t\t\t\t\t\t" + lineaFormateada.trim());
                        */
                    }
                }
            }
            
        // Captura la excepción. Si ocurre cualquier error de E/S (archivo no existe, permisos, etc.), se captura aquí.
        } catch (IOException ioe){
            JOptionPane.showMessageDialog(null, "Error al leer el fichero de rutas: " + ioe.getMessage(), "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }

        /*
        // Declaramos este bloque después del "try-catch", pues si se da el caso mostramos el mensaje de error una vez, tras haber leído todas y cada una de las lineas; y así no aparecer en cada una de las líneas en que no aparezca el valor del campo 'ruta'.
            // Si el parámetro 'ruta' .
        if (rutas.isEmpty()){
            /*
            *JOptionPane.showMessageDialog(null, "Error en uno o en los dos de los campos [origen] y/o [destino]. Uno de los destinos o los dos no corresponden al territorio comprendido o declarado como 'Reino de España', por lo que no es un vuelo nacional. Puede ser internacional/intercontinental o continental.", "Error de búsqueda", JOptionPane.ERROR_MESSAGE);
            */
            /*
            * exit();
            *//*
            exit_cerrarPrograma("Error en uno o en los dos de los campos [origen] y/o [destino]. Uno de los destinos o los dos no corresponden al territorio comprendido o declarado como 'Reino de España', por lo que no es un vuelo nacional. Puede ser internacional/intercontinental o continental.");
        }
        */
        
        // Devuelve la lista final de rutas válidas.
        return rutas;
    }
    
    
    // -------------------------- 'Apartado 3' --------------------------
    // Crea el método que filtra las rutas según el aeropuerto de origen y de destino.
    public static List<String> filtrarRutas (List<String> rutas, String origen, String destino){
        // Crea la lista donde guardaremos las rutas que cumplan el filtro.
        List<String> rutasFiltradas = new ArrayList<>();

        // Recorre todas las rutas del listado recibido.
        for (String ruta : rutas){
            // Divide cada línea por comas para separar sus campos: origen, destino, precio, duración, aerolínea.
            String[] partes = ruta.split(",");

            // Comprobamos que haya al menos dos campos válidos (origen y destino).
            if (partes.length >= 2){
                String origenRuta = partes[0].trim();
                String destinoRuta = partes[1].trim();

                // Condición "if" que verifica/comprueba si tanto el origen como el destino coinciden o, por el contrario, el usuario indicó "cualquiera". Posteriormente se añade la ruta.
                if (origenRuta.equalsIgnoreCase(origen) && (destino.equalsIgnoreCase("cualquiera") || destinoRuta.equalsIgnoreCase(destino))){
                    // Añade la ruta, la línea completa, (con espacios externos recortados, es decir, limpia los espacios sobrantes).
                    /* (Para el caso de impresión con formateo)
                    *rutasFiltradas.add(String.format("\t\t\t\t\t\t\t%-5s %-10s %-15s %-20s", partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim()));
                    */
                    // Añade la línea completa (con espacios externos recortados, es decir, limpia los espacios sobrantes).
                    rutasFiltradas.add("\t\t\t\t\t\t\t" + ruta.trim());
                }
            }
        }

        // Si no se encontró ninguna ruta, muestra un mensaje de error y cierra el programa.
        if (rutasFiltradas.isEmpty()){
            exit_cerrarPrograma("Error no se encontraron rutas que cumplan con los criterios de búsqueda/filtrado. Verifique los códigos de aeropuerto de origen y destino introducidos.");
        }

        // Devuelve la lista de rutas filtradas.
        return rutasFiltradas;
    }

    // Crea el método que ordena las rutas según un criterio (precio o duración).
    public static List<String> ordenarRutas (List<String> rutas, String criterio, boolean ascendente){
        // Crea la lista donde guardaremos las rutas que cumplan el filtro.
        List<String> rutasOrdenadas = new ArrayList<>(rutas); /* "new ArrayList<>(rutas)" hace una copia de la lista original de "rutas". De esta forma hacemos que la función ".sort()" no actúe sobre una lista vacía. */

        // Usamos un comparador personalizado.
            // Llama a la lista "rutasOrdenadas", donde comparamos y ordenamos  dada la función ".sort()".
            // "(r1, r2) ->{}" Es una expresión lambda, una función anónima donde "r1" y "r2" son dos rutas (líneas del fichero) que se comparan entre sí.
        rutasOrdenadas.sort((r1, r2) ->{
            // Divide la primera ruta en sus respectivas partes/campos separados por comas.
            String[] p1 = r1.split(",");
            String[] p2 = r2.split(",");

            // Condicional "if" en el que si alguna línea está mal formada (menos de 4 campos), devuelve 0.
            if (p1.length < 4 || p2.length < 4) return 0;

            // Bloque "try-catch" 
            try{
                // Declara dos variables decimales para almacenar el valor numérico que vamos a comparar. Inicializadas en cero.
                double valor1 = 0, valor2 = 0;

                // Condicional "if" en el que si el campo es "precio" (ignora mayúsculas y minúsculas).
                if (criterio.equalsIgnoreCase("precio")){
                    // Convierte cada tercer campo (índice 2) de cada ruta a double.
                    valor1 = Double.parseDouble(p1[2].trim());
                    valor2 = Double.parseDouble(p2[2].trim());
                // Cubre el caso contrario, donde de igual manera para el caso de que el campo sea "duracion" (ignora mayúsculas y minúsculas).
                } else if (criterio.equalsIgnoreCase("duracion")){
                    // Convierte cada tercer campo (índice 3) de cada ruta a double.
                    valor1 = Double.parseDouble(p1[3].trim());
                    valor2 = Double.parseDouble(p2[3].trim());
                }

                // Condicional "if" en el que comparamos los valores numéricos extraídos, y si es 'ascendente' éste devolverá "true", obteniendo en la devolución, en el "return", una comparación natural (menor primero).
                if (ascendente){
                    return Double.compare(valor1, valor2);
                // Cubre el caso contrario, donde de igual manera, si no es 'ascendente' éste devolverá "false", obteniendo un resultado invertido en la devolución, en el "return", una comparación inversa (mayor primero).
                } else{
                    return Double.compare(valor2, valor1);
                }

            // Captura la excepción. Si ocurre cualquier error en la lectura/entrada de caracteresno numéricos (puede ocurrir si los campos que intentamos parsear no son números válidos).
            } catch (NumberFormatException nfe){
                return 0; /* Al devolcer cero lo que hacemos es no cambiar el orden que se llega a dar entre estos dos elementos. */
            }
        });
        
        // Devuelve la lista de rutas filtradas y ordenados por el campo especificado.
        return rutasOrdenadas;
    }
    
    
    // -------------------------- 'Apartado 4' --------------------------
    // Crea el método que guarda en un fichero una o todas las rutas, según la posición indicada.
    public static void guardarRutas(String nombreFichero, List<String> rutas, int posicion){
        // Bloque "try-catch" para asegurar el cierre automático del fichero.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))){

            // Condicional "if" para el "caso 1", donde si la posición indicada coincide con el número de rutas, imprime todas.
            if (posicion == rutas.size()){
                for (String ruta : rutas){
                    escribirRutaFormateada(bw, ruta);
                }
            } 
            // Condicional "if" para el "caso 2", donde si la posición está dentro del rango, escribe solo esa ruta.
            else if (posicion >= 0 && posicion < rutas.size()){
                escribirRutaFormateada(bw, rutas.get(posicion));
            } 
            // Condicional "if" para el "caso 3", donde si la posición no es válida, muestra mensaje de error.
            else{
                exit_cerrarPrograma("Error dada la posición indicada (" + posicion + "). La posición no es válida, es incorrecta o el sisteman no la reconoce. Recuerde que ésta debe estar comprendida entre 0 y " + rutas.size());
            }

            JOptionPane.showMessageDialog(null, "Las rutas se han guardado correctamente en el fichero: " + nombreFichero, "Éxito. Ruta guardada", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ioe){
            exit_cerrarPrograma("Error de escritura al intentar guardar las rutas dadas en el fichero: " + ioe.getMessage());
        }
    }

    // Método privado auxiliar que da formato de escritura a la ruta.
    private static void escribirRutaFormateada(BufferedWriter bw, String ruta) throws IOException{
        // Divide la línea por comas: origen, destino, precio, duración, aerolínea.
        String[] partes = ruta.split(",");

        // Condicional "if" para asegurarse de que se ignoren las líneas que se han formado mal.
        if (partes.length < 5) return;
        
        // Declara las variables correspondiéndoles una posición concreta del índice dentro de la lista "partes".
        String origen = partes[0].trim();
        String destino = partes[1].trim();
        String precio = partes[2].trim();
        String duracion = partes[3].trim();
        String lineaAerea = partes[4].trim();

        // LLama al método "BufferedWriter" 'bx' para ir escribiendo las rutas siguiendo el formato indicado.
        bw.write("---");
        bw.newLine(); /* Salto de línea. */
        bw.write("Flight: " + origen + " to " + destino);
        bw.newLine();
        bw.write("Carrier: " + lineaAerea);
        bw.newLine();
        bw.write("Duration: " + duracion + " minutes");
        bw.newLine();
        bw.write("Total Cost: " + precio + " euros");
        bw.newLine();
        bw.write("---");
        bw.newLine();
    }
    
    
    // -------------------------- 'Main' --------------------------
    // Crea el método principal del programa.
    public static void main(String[] args){
        // Crea el escáner 'user'.
        Scanner user = new Scanner(System.in);
            // Bienvenida al usuario.
        System.out.print("\nBienvenido/a al programa 'Aeropuerto', por favor, introduzca su nombre: ");
        String nombre = user.nextLine();
        
        
        // -------------------------- 'Apartado 1' --------------------------
        /* (Para el caso de ruta directa)
        *String ficheroLocalizacion = "C:\\Users\\rodri\\OneDrive\\Documentos\\NetBeansProjects\\Aeropuerto\\localizacion.txt";
        */
            // Pide el nombre del fichero a buscar.
        System.out.print("\n\tBienvenido/a al programa Sr/Sra " + nombre + ". Por favor, ahora ingrese el nombre del fichero de localizaciones (Ejemplo: localizacion.txt): ");
        String ficheroLocalizacion = user.nextLine();
        
            // Pide el nombre del objeto del campo 'pais' a buscar o filtrar.
        System.out.print("\n\tAhora introduzca el país a filtrar (o bien la palabra clave 'cualquiera' para una búsqueda global/completa): ");
        String pais = user.nextLine();
        
        /* (Para el caso de ruta directa)
        * // Limpia el "buffer" de 'user', del "Scanner", (por si quedó un salto de línea).
        *
        *if (pais.isEmpty()){
        *    pais = user.nextLine().trim();
        *}
        */
        
        // Llama al método "obtenerAeropuerto" con los parámetros que ha introducido el usuario.
        List<String> aeropuertos = obtenerAeropuerto (ficheroLocalizacion.trim(), pais);

        System.out.println("\n\n\n\t\t\t\t<======================== AEROPUERTO/S ENCONTRADO/S ========================>\n");
        
        // Bucle "foreach" que recorre la lista resultante y muestra por pantalla cada aeropuerto (cada línea guardada).
        for (String aeropuerto : aeropuertos){
            System.out.println(aeropuerto);
        }
        
        // Muestreo del resultado final al usuario.
        System.out.println("\n\n\tPara la ubicación (" + pais +") indicado hay un total de " + aeropuertos.size() + " aeropuertos con vuelos operativos (en marcha) hasta el momento.");
        
        
        // -------------------------- 'Apartado 2' --------------------------
        /* (Para el caso de ruta directa)
        *String ficheroRutas = "C:\\Users\\rodri\\OneDrive\\Documentos\\NetBeansProjects\\Aeropuerto\\precios.txt";
        */
            // Pide el nombre del fichero a buscar.
        System.out.print("\n\n\n\n\tAhora introduzca el nombre del fichero de rutas (Ejemplo: precios.txt): ");
        String ficheroRutas = user.nextLine();

        // Llama al método "obtenerRutas" pasando la lista de aeropuertos obtenida antes.
        List<String> rutas = obtenerRutas(ficheroRutas.trim(), aeropuertos);

        System.out.println("\n\n\n\t\t\t\t<======================== RUTAS ENCONTRADAS ========================>\n");
        
        // Bucle "foreach" que recorre la lista resultante y muestra por pantalla cada ruta (cada línea guardada).
        for (String ruta : rutas){
            System.out.println(ruta);
        }
        
        // Muestreo del resultado final al usuario.
        System.out.println("\n\n\tTotal de rutas nacionales activas/operativas (hasta el momento) encontradas: " + rutas.size());
        
        
        // -------------------------- 'Apartado 3' --------------------------
        // Crea la lista "rutasFiltradas" antes para poder partir de esta lista para el "Apartado 3".
        List<String> rutasFiltradas = new ArrayList<>();
        
            // Pregunta al usuario si quiere una búsqueda con filtro.
        String eleccion1 = JOptionPane.showInputDialog(null, "¿Desea filtrar las rutas por aeropuerto de origen y destino? (Sí/No)", "¿Búsqueda filtrada?", JOptionPane.QUESTION_MESSAGE);
        
        // Condicional "if" para verificar si el usuario canceló o dejó vacío la respuesta.
        if (eleccion1 == null || eleccion1.trim().isEmpty()){
            exit_cerrarPrograma("Error en la elección tomada, el sistema no ha reconocido ningún valor o no se ha introducido ninguna respuesta.");
        }
        
        // Condicional "if" para verificar que el usuario haya introducido bien el valor, un valor esperado.
        if (eleccion1 != "Sí" && !eleccion1.equalsIgnoreCase("Si") && !"No".equalsIgnoreCase(eleccion1)){
            exit_cerrarPrograma("Error en la elección tomada. Verifique que el valor debe ser '(Sí)' o '(No)'. O el sistema no ha reconocido el valor introducido.");
        }
        
        // Condicional "if" para verificar la respuesta dada por el usuario.
        if (eleccion1.equalsIgnoreCase("Sí") || eleccion1.equalsIgnoreCase("Si")){
                // Pide al usuario el código del aeropuerto de salida, el aeropuerto 'origen'.
            System.out.print("\n\n\n\n\tPor favor, ingrese el código del aeropuerto de origen (Ejemplo: MAD): ");
            String origen = user.nextLine();
            
            // Condicional "if" para verificar si el usuario canceló o dejó vacío la respuesta.
            if (origen == null || origen.trim().isEmpty()){
                exit_cerrarPrograma("Error no se ha introducido ningún código de aeropuerto de origen.");
            }
            
            // Condicional "if" para verificar que el usuario haya introducido bien el valor, un valor esperado.
            if (origen.trim().length() < 3){
                exit_cerrarPrograma("Error en el código de aeropuerto de origen introducido, éste debe tener al menos 3 caracteres (Ejemplo: MAD, BCN).");
            }
            
                // Pide al usuario el código del aeropuerto de llegada, el aeropuerto 'destino'.
            System.out.print("\n\tAhora introduzca el código del aeropuerto de destino (Si sólo quieres filtrar por vuelos de salida/origen puedes usar la palabra clave: 'cualquiera'): ");
            String destino = user.nextLine();
            
            // Condicional "if" para verificar si el usuario canceló o dejó vacío la respuesta.
            if (destino == null || destino.trim().isEmpty()){
                exit_cerrarPrograma("Error no se ha introducido ningún código de aeropuerto de origen.");
            }
            
            // Condicional "if" para verificar que el usuario haya introducido bien el valor, un valor esperado.
            if (destino.trim().length() < 3){
                exit_cerrarPrograma("Error en el código de aeropuerto de destino introducido, éste debe tener al menos 3 caracteres (Ejemplo: MAD, BCN) (o ser la palabra clave 'cualquiera').");
            }
            
            // Llama al método "rutasFiltradas" pasando la lista de aeropuertos coincidentes con el filtro de búsqueda pasado antes.
            rutasFiltradas = filtrarRutas(rutas, origen.toUpperCase().trim(), destino.toUpperCase().trim());
        
            System.out.println("\n\n\n\t\t\t\t<======================== RUTAS FILTRADAS ========================>\n");
        
            // Bucle "foreach" que recorre la lista resultante y muestra por pantalla cada ruta filtrada (cada línea guardada).
            for (String rutasFiltrada : rutasFiltradas){
                System.out.println(rutasFiltrada);
            }
        
            // Muestreo del resultado final al usuario.
            System.out.println("\n\n\tEl total de rutas filtradas encontradas que parten de [" + origen + "] y terminan en [" + destino + "] es de: " + rutasFiltradas.size());
        }

            // Pregunta al usuario si quiere una búsqueda con ordenada.
        String eleccion2 = JOptionPane.showInputDialog(null, "¿Desea ordenar las rutas? (Sí/No)", "¿Búsqueda ordenada?", JOptionPane.QUESTION_MESSAGE);
        
        // Condicional "if" para verificar si el usuario canceló o dejó vacío la respuesta.
        if (eleccion2 == null || eleccion2.trim().isEmpty()){
            exit_cerrarPrograma("Error en la elección tomada, el sistema no ha reconocido ningún valor o no se ha introducido ninguna respuesta.");
        }
        
        // Condicional "if" para verificar que el usuario haya introducido bien el valor, un valor esperado.
        if (eleccion2 != "Sí" && !eleccion2.equalsIgnoreCase("Si") && !"No".equalsIgnoreCase(eleccion2)){
            exit_cerrarPrograma("Error en la elección tomada. Verifique que el valor debe ser '(Sí)' o '(No)'. O el sistema no ha reconocido el valor introducido.");
        }
        
        // Condicional "if" para verificar la respuesta dada por el usuario.
        if (eleccion2.equalsIgnoreCase("Sí") || eleccion2.equalsIgnoreCase("Si")){
                // Pregunta al usuario por el campo de prefencia a realizar la búsqueda ordenada.
            String criterio = JOptionPane.showInputDialog(null, "¿Por qué criterio desea ordenar? (precio/duracion)", "Criterio de ordenación", JOptionPane.QUESTION_MESSAGE);
            
            // Condicional "if" para verificar si el usuario canceló o dejó vacío la respuesta.
            if (criterio == null || criterio.trim().isEmpty()){
                exit_cerrarPrograma("Error en el criterio de búsqueda ordenada, el sistema no ha reconocido ningún valor o no se ha introducido ninguna respuesta.");
            }
            
            // Condicional "if" para verificar que el usuario haya introducido bien el valor, un valor esperado.
            if (!criterio.equalsIgnoreCase("precio") && !criterio.equalsIgnoreCase("duracion")){
                exit_cerrarPrograma("Error en el criterio de búsqueda ordenada. Verifique que el valor debe ser '(precio)' o '(duracion)'. O el sistema no ha reconocido el valor introducido.");
            }
            
                // Pregunta al usuario por la forma de prefencia a realizar la búsqueda ordenada.
            String orden = JOptionPane.showInputDialog(null, "¿Orden ascendente o descendente?", "Tipo de ordenación", JOptionPane.QUESTION_MESSAGE);
            
            // Condicional "if" para verificar si el usuario canceló o dejó vacío la respuesta.
            if (orden == null || orden.trim().isEmpty()){
                exit_cerrarPrograma("Error en el criterio de ordenación dentro de la búsqueda ordenada por " + criterio + ", el sistema no ha reconocido ningún valor o no se ha introducido ninguna respuesta.");
            }
            
            // Condicional "if" para verificar que el usuario haya introducido bien el valor, un valor esperado.
            if (!orden.equalsIgnoreCase("ascendente") && !orden.equalsIgnoreCase("descendente")){
                exit_cerrarPrograma("Error en el criterio de ordenación dentro de la búsqueda ordenada por " + criterio + ". Verifique que el valor debe ser '(ascendente)' o '(descendente)'. O el sistema no ha reconocido el valor introducido.");
            }
            
            // Declara una variable booleana que servirá como un 'interrumptor lógico'. Espera recibir "true" o "false", en caso de ser lo primero, que el usuario haya puesto "ascendente", devolverá "true" a uno de los dos "if" correspondiente del bloque 'apartado 4', especificándole la forma de ordenar. Si no, y recibe "false" se redirigirá al otro "if".
            boolean ascendente = orden.trim().equalsIgnoreCase("ascendente");
            
            // Crea una lista "rutasAOrdenar" donde si el usuario filtró antes, en el "Apartado 3", hacemos una copia su lista, de las rutas resultantes de la búsqueda filtrada. En caso de que el usuario no hubiese filtrado, usará todas las rutas.
            List<String> rutasAOrdenar = !rutasFiltradas.isEmpty() ? new ArrayList<>(rutasFiltradas) : new ArrayList<>(rutas);

            
            // Llama al método "ordenarRutas" pasando la lista de aeropuertos coincidentes con el filtro de búsqueda pasado antes.
            List<String> rutasOrdenadas = ordenarRutas (rutasAOrdenar, criterio.toLowerCase().trim(), ascendente);

            System.out.println("\n\n\n\t\t\t\t<======================== RUTAS ORDENADAS ========================>\n");
        
            // Bucle "foreach" que recorre la lista resultante y muestra por pantalla cada ruta filtrada según la especificación de orden sobre la búsqueda (cada línea guardada).
            for (String ruta : rutasOrdenadas){
                System.out.println(ruta);
            }

            // Muestreo del resultado final al usuario.
            System.out.println("\n\n\tTEl total de líneas, rutas, ordenadas del campo " + criterio + " de forma/manera " + orden + " es de:  " + rutasOrdenadas.size());
        }
        
        
        // -------------------------- 'Apartado 4' --------------------------
            // Pregunta al usuario si desea guardar/almacenar la lista obtenida tras la búsqueda.
        System.out.print("\n\n¿Desea guardar alguna ruta en fichero? (Sí/No): ");
        String guardar = user.nextLine();
        
        // Condicional "if" para verificar si el usuario canceló o dejó vacío la respuesta.
        if (guardar == null || guardar.trim().isEmpty()){
            exit_cerrarPrograma("Error en la elección tomada, el sistema no ha reconocido ningún valor o no se ha introducido ninguna respuesta.");
        }
        
        // Condicional "if" para verificar que el usuario haya introducido bien el valor, un valor esperado.
        if (guardar != "Sí" && !guardar.equalsIgnoreCase("Si") && !"No".equalsIgnoreCase(guardar)){
            exit_cerrarPrograma("Error en la elección tomada. Verifique que el valor debe ser '(Sí)' o '(No)'. O el sistema no ha reconocido el valor introducido.");
        }
        
        // Condicional "if" para verificar la respuesta dada por el usuario.
        if (guardar.equalsIgnoreCase("Sí") || guardar.equalsIgnoreCase("Si")){
                // Pide al usuario el nombre del fichero de salida a imprimir y guardar la/s ruta/s.
            System.out.print("\n\tPor favor, ingrese el nombre del fichero de salida (Ejemplo: rutas_guardadas.txt): ");
            String ficheroSalida = user.nextLine();
            
                // Pide al usuario la posición de la ruta a guardar dentro del fichero de salida.
            System.out.print("\n\tAhora introduzca la posición de la ruta a guardar (o " + rutas.size() + " para todas): ");
            int posicion = Integer.parseInt(user.nextLine());
            
            // Llama al método "guardarRutas" donde pasa los parametros recibidos 'ficheroSalida', 'rutas' y 'posicion', a fin de que se escriba/imprima en el archivo indicado.
            guardarRutas(ficheroSalida.trim(), rutas, posicion);
        }
        
        // Cierra/Finaliza el escáner.
        user.close();
    }
    
    
    // -------------------------- 'Closing' --------------------------
    /*
    *private static void exit(){
    *    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    *}
    */
    
    // Método para cerrar el programa con código de error.
    private static void exit_cerrarPrograma(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Error durante la ejecución del programa/APP.", JOptionPane.ERROR_MESSAGE);
        /*
        *System.exit(0); /* Cierra el programa correctamente y con salida limpia. *//*
        */
        System.exit(1); /* Cierra el programa indicando error y con salida controlada con error. */
    }
}