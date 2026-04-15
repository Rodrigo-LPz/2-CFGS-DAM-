// "Incidencia.kt" 
    /**
     * Programa escrito en el lenguaje de programación Kotlin que simula un sistema de gestión de incidencias utilizando el patrón productor-consumidor. En este caso, los productores representan a los clientes que generan incidencias, mientras que los consumidores representan a los servidores que procesan y resuelven esas incidencias.
     * A continuación, se explican las partes principales del código:
     *    1. Crear productores (clientes) que generan incidencias de manera concurrente.
     *    2. Crear consumidores (servidores) que procesan las incidencias de manera concurrente.
     *    3. Utilizar una cola bloqueante para gestionar la comunicación entre productores y consumidores de manera segura en un entorno concurrente.
     */




// Importa de la biblioteca/librería "util.concurrent" el paquete "BlockingQueue". Para usar colas bloqueantes (estructuras de datos que permiten la comunicación entre hilos de manera segura).
import java.util.concurrent.BlockingQueue

// Importa de la biblioteca/librería "util.concurrent" el paquete "Executors". Para usar el framework de ejecución de tareas concurrentes, este proporciona una forma de crear y gestionar hilos de ejecución (threads) y tareas de manera eficiente.
import java.util.concurrent.Executors 

// Importa de la biblioteca/librería "util.concurrent" el paquete "LinkedBlockingQueue". Para hacer uso de una implementación de cola bloqueante basada en una lista enlazada, que permite almacenar elementos de manera segura entre hilos de ejecución (threads). Además de ser útil para la comunicación entre productores y consumidores.
import java.util.concurrent.LinkedBlockingQueue

// Importa de la biblioteca/librería "util.concurrent" el paquete "AtomicInteger". Para realizar operaciones atómicas sobre enteros, lo que permite un acceso seguro y eficiente en entornos concurrentes.
import java.util.concurrent.atomic.AtomicInteger

/**
 * Define la clase "Incidencia".
 *    Esta espera como parámetros dos propiedades: "id" de tipo entero y "descripcion" de tipo cadena.
 *    Esta clase representa una incidencia o problema que puede ser gestionado en un sistema CAU (Centro de Atención a Usuarios)/sistema de de atención al cliente o similar.
 */
data class Incidencia(val id: Int, val descripcion: String) /* "data class" es una clase especial en Kotlin que se utiliza para almacenar datos. Proporciona automáticamente métodos como "toString()", "equals()" y "hashCode()" basados en las propiedades definidas en el constructor. */

/**
 * Define la función/método principal ("main()") del programa.
 *    Es el punto de entrada del programa, donde se ejecutará el código para gestionar las incidencias.
 */
fun main(){
    /**
     * Declara una variable inmutable "colaIncidencias" de tipo "BlockingQueue<Incidencia>" y la inicializa con una instancia de "LinkedBlockingQueue()".
     *    Esta cola se utilizará para almacenar las incidencias de manera segura entre hilos de ejecución (threads).
     */
    val colaIncidencias: BlockingQueue<Incidencia> = LinkedBlockingQueue()

    /**
     * Declara una variable inmutable "contador" de tipo "AtomicInteger" y la inicializa con un valor entero de uno (1).
     *    Esta función se utilizará para llevar un registro/conteo del número de incidencias generadas.
     */
    val contador = AtomicInteger(1)

    /**
     * Declara una variable inmutable "ejecutor" de tipo "ExecutorService" y la inicializa con una instancia de "Executors.newFixedThreadPool(6)".
     *    Esta función se utilizará para gestionar la ejecución de tareas concurrentes en un grupo de hilos. En este caso, crea un grupo de hilos con un tamaño fijo de seis (6) hilos, lo que significa que se podrán ejecutar hasta seis tareas concurrentemente, en simultáneo.
     */
    val ejecutor = Executors.newFixedThreadPool(6)


    // ========== PRODUCTORES (Clientes) ==========
        /**
         * 1. Se hace uso de la función "repeat" para repetir un bloque de código un número específico de veces. En este caso, se repetirá tres (3) veces, lo que significa que se crearán tres productores (clientes). Cada vez que se repite el bloque de código, se asigna un identificador único (ID) al productor utilizando la variable "productorID".
         *    2. Se utiliza un "ExecutorService" para gestionar la ejecución de tareas concurrentes en un grupo de hilos. Esto permite que múltiples productores generen incidencias de manera simultánea.
         *        3. Cada productor genera cinco (5) incidencias, cada una con un identificador único (ID) y una descripción que incluye el ID del productor.
         */
    repeat(3) { productorID ->
        ejecutor.execute {
            repeat(5) {
                /**
                 * Declara una variable inmutable "id".
                 *    Genera un identificador único (ID) seguro.
                 */
                val id = contador.getAndIncrement()

                /**
                 * Declara una variable inmutable "incidencia".
                 *    Crea una incidencia.
                 */
                val incidencia = Incidencia(id, "ERROR. fallo detectado por cliente $productorID")

                /**
                 * Utiliza el método "put()".
                 *    Insertamos (put) en la cola (bloqueante "colaIncidencias" y segura).
                 */
                colaIncidencias.put(incidencia)

                // Imprime un mensaje por pantalla indicando que el productor (cliente) ha creado una incidencia, mostrando el ID del productor y el ID de la incidencia creada.
                println("\nProductor\t{$productorID}\tcreó incidencia\t'${incidencia.id}'")

                // Forzamos la espera de medio segundo (0.5s o 500ms) para simular el tiempo que tarda en generar una incidencia.
                Thread.sleep(500)
            }
        }
    }


    // ========== CONSUMIDORES (Servidor) ==========
        /**
         * 1. Se hace uso de la función "repeat" para repetir un bloque de código un número específico de veces. En este caso, se repetirá tres (3) veces, lo que significa que se crearán tres consumidores (servidores). Cada vez que se repite el bloque de código, se asigna un identificador único (ID) al consumidor utilizando la variable "consumidorID".
         *    2. 
         *        3. 
         */
    repeat(3) { consumidorID ->
        ejecutor.execute {
            while (true) {
                /**
                 * Declara una variable inmutable "incidencia".
                 *    Utiliza el método "take()".
                 *        Este método bloquea el hilo si la cola está vacía, esperando hasta que haya una incidencia disponible para ser procesada. 
                 *            Extrae (take) una incidencia de la cola (espera si no hay). 
                 */
                val incidencia = colaIncidencias.take()

                // Imprime un mensaje por pantalla indicando que el consumidor (servidor) está procesando una incidencia, mostrando el ID del consumidor y el ID de la incidencia.
                println("\nConsumidor\t{$consumidorID}\tprocesando incidencia\t'${incidencia.id}'...")

                // Forzamos la espera de un segundo completo(1s o 1000ms) para simular el tiempo que tarda en procesar/resolver una incidencia.
                Thread.sleep(1000)

                // Imprime un mensaje por pantalla indicando que el productor (cliente) ha creado una incidencia, mostrando el ID del productor y el ID de la incidencia creada.
                println("\nIncidencia\t{${incidencia.id}}\tresuelta por el consumidor\t'$consumidorID'")
            }
        }
    }

    // Forzamos la espera de cinco segundos completos(5s o 5000ms) para simular el tiempo que tarda en procesar el apagado del ejecutor.
    Thread.sleep(5000)

    // Utiliza el método "shutdown" del ejecutor para iniciar el proceso de apagado del ejecutor, lo que significa que no se aceptarán nuevas tareas y se finalizarán las tareas en ejecución.
    ejecutor.shutdown()

    // Imprime un mensaje por pantalla indicando que el sistema ha finalizado, se ha cerrado.
    println("\n\n🛑 Sistema finalizado 🛑")
}   