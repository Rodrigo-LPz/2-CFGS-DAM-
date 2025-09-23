/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acceso.a.datos;

/**/
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Rodrigo
 */
public class Ejercicio_1 {
    public static void main(String[] args){
        String nombreArchivo;
        
        //nombreArchivo = "C:\\Users\\Alumno\\Documents\\2º CFGS";
        nombreArchivo = "C:\\Users\\Alumno\\Downloads\\Apache-NetBeans-27.exe";
        
        File file = new File(nombreArchivo);
        if (!file.exists()){
            System.err.println("\nError. El archivo en cuestión no existe o no lo reconoce nuestro sistema.");
        }else{
            System.out.println("Archivo encontrado.");
            // Variable para especicificar el formato de la fecha.
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            String tipo;
            
            // Caso para directorios.
            if (file.isDirectory()){
                // Muestreo del resultado inicial.
                System.out.println("\n\tLa ruta indicada pertenece a un directorio.\n\n\tEn su interior, su contenido:");
                File[] files = file.listFiles();
                
                // Caso para directorios no vacíos.
                if (files != null){
                    // Bucle que recorre el directorio dividiendo su contenido en archivos y más directorios.
                    for (File f : files){
                        // División entre archivos y directorios.
                        tipo = f.isDirectory() ? "\n\t[DIR] \\" : "[FILE] ";
                        // Variable para la especificación de tamaño.
                        long tamañoBytes = f.length();
                        // Variable para la especificación de última modificación.
                        String lastDateModificated = sdf.format(new Date(f.lastModified()));

                        // Opción váila si no ponemos previamente "if (files != null){}".
                        /*if (tamañoBytes >= 0){
                            System.out.println("\t" + tipo + f.getName() + "El tamaño del directorio es de " +  tamañoBytes + " bytes." + "\n");
                        } else
                            System.out.println("El directorio no existe, no lo reconoce el sistema o no se pudo obtener su tamaño.");
                        */
                        
                        // Muestreo del resultado final.
                        System.out.println("\t" + tipo + f.getName() + "\n\t\t| El tamaño del directorio es de " +  tamañoBytes + " bytes. |" + "\n\t\t| La última modificación realizada fue " + lastDateModificated + ". |");
                    }
                } else{
                // Muestreo del resultado final.
                System.out.println("\n\tEl directorio en cuestión está o se encuentra vacío.");
                }
                
            // Caso para archivos.
            } else{
                // Muestreo del resultado inicial.
                System.out.println("\n\tLa ruta indicada pertenece a un archivo.\n\n\tEn su interior, su contenido:");
                // Variable para la especificación de tamaño.
                long tamañoBytes = file.length();
                // Variable para la especificación de última modificación.
                String lastDateModificated = sdf.format(new Date(file.lastModified()));

                if (tamañoBytes >= 0){
                    System.out.println("\t" + file.getName() + "\n\t\t| El tamaño del directorio es de " +  tamañoBytes + " bytes. |" + "\n\t\t| La última modificación realizada fue " + lastDateModificated + ". |");
                } else{
                    System.out.println("\n\tEl archivo en cuestión está o se encuentra vacío.");
                }
            }
        }
    }
}