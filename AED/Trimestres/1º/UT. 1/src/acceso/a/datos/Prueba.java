/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acceso.a.datos;

import java.io.File;

/**
 *
 * @author Alumno
 */
public class Prueba{
    public static void main(String[] args){
        String nombreArchivo;
        
        nombreArchivo = "C:\\Users\\Alumno\\Documents\\NetBeansProjects\\Acceso a Datos\\src\\acceso\\a\\datos\\Prueba.java";
        //nombreArchivo = "C:\\Users\\Alumno\\Pictures\\Screenshots";
        
        File file = new File(nombreArchivo);
        if (!file.exists()){
            System.err.println("\nError. El archivo en cuestión no existe o no lo reconoce nuestro sistema.");
        }else{
            System.out.println("Archivo encontrado.");
            if (file.isDirectory()){
                System.out.println("\n\tLa ruta indicada pertenece a un directorio.\n\n\tEn su interior, su contenido:");
                File[] files = file.listFiles();
                String texto;
                for (File f : files){
                    texto = f.isDirectory() ? "\\" : " ";
                    System.out.println("\t" + texto + f.getName() + "\n");
                    //System.out.println("\t" + f.getName() + "\n");
                }
            } else{
                System.out.println("\n\tLa ruta indicada pertenece a un archivo.");
            }
        }
    }
}