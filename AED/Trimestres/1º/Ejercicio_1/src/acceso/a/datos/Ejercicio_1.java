package acceso.a.datos;

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
            System.err.println("\nError. El archivo en cuesti�n no existe o no lo reconoce nuestro sistema.");
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
                
                // Caso para directorios no vac�os.
                if (files != null){
                    // Bucle que recorre el directorio dividiendo su contenido en archivos y m�s directorios.
                    for (File f : files){
                        // Divisi�n entre archivos y directorios.
                        tipo = f.isDirectory() ? "\n\t[DIR] \\" : "[FILE] ";
                        // Variable para la especificaci�n de tama�o.
                        long tama�oBytes = f.length();
                        // Variable para la especificaci�n de �ltima modificaci�n.
                        String lastDateModificated = sdf.format(new Date(f.lastModified()));

                        // Opci�n v�ila si no ponemos previamente "if (files != null){}".
                        /*if (tama�oBytes >= 0){
                            System.out.println("\t" + tipo + f.getName() + "El tama�o del directorio es de " +  tama�oBytes + " bytes." + "\n");
                        } else
                            System.out.println("El directorio no existe, no lo reconoce el sistema o no se pudo obtener su tama�o.");
                        */
                        
                        // Muestreo del resultado final.
                        System.out.println("\t" + tipo + f.getName() + "\n\t\t| El tama�o del directorio es de " +  tama�oBytes + " bytes. |" + "\n\t\t| La �ltima modificaci�n realizada fue " + lastDateModificated + ". |");
                    }
                } else{
                // Muestreo del resultado final.
                System.out.println("\n\tEl directorio en cuesti�n est� o se encuentra vac�o.");
                }
                
            // Caso para archivos.
            } else{
                // Muestreo del resultado inicial.
                System.out.println("\n\tLa ruta indicada pertenece a un archivo.\n\n\tEn su interior, su contenido:");
                // Variable para la especificaci�n de tama�o.
                long tama�oBytes = file.length();
                // Variable para la especificaci�n de �ltima modificaci�n.
                String lastDateModificated = sdf.format(new Date(file.lastModified()));

                if (tama�oBytes >= 0){
                    System.out.println("\t" + file.getName() + "\n\t\t| El tama�o del directorio es de " +  tama�oBytes + " bytes. |" + "\n\t\t| La �ltima modificaci�n realizada fue " + lastDateModificated + ". |");
                } else{
                    System.out.println("\n\tEl archivo en cuesti�n est� o se encuentra vac�o.");
                }
            }
        }
    }
}