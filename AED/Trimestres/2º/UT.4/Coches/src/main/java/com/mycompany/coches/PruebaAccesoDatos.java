/**
 *
 * @author Rodrigo
 */
package com.mycompany.coches;



public class PruebaAccesoDatos{
    // Crea el método principal del programa.
    public static void main(String[] args){
        // Crea un objeto para llamar a los métodos de "AccesoDatos".
        AccesoDatos AD = new AccesoDatos();
        
        // Llama a los distyintos métodos de "AccesoDatos".
            // Crean el entorno automaticamente (prt. 1).
        AD.crearBaseDeDatos();
        AD.abrirConexion();
        AD.crearTablas();
        AD.insertarDatosIniciales();
        
            // Crean y realizan pruebas a las funciones del programa.
        AD.mostrarDatosCoches();
        AD.modificarCoche("BA-3333", 5000);
        AD.borrarCoche("MA-1111");
        AD.insertarCoche("AA-0005", "Ford", 4500, "1A");
        AD.insertarPropietario("X25", "Jose", 54);
            
            // Adicionales (Apartado 4).
        AD.mostrarPropietarioYCoches("1A");
        AD.borrarPropietario("1C");
            
            // Cierra el entorno automaticamente (prt. 2).
        AD.cerrarConexion();
    }
}