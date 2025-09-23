/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package acceso.a.datos;

/**
 *
 * @author Rodrigo
 */
public class AccesoADatos{
    public static void main(String[] args){
        int a = 10;
        int b = 2;
        int c = 0;
        int result;
        
        try{
            result = a / a;
            result = a / b;
            result = a / c;
            result = b / a;
            result = b / b;
            result = b / c;
            result = c / a;
            result = c / b;
            result = c / c;
            
            System.out.println("El resultado de la operación es: " + result);
        } catch (ArithmeticException e){
            System.err.println("\nError. No se puede dividir entre cero. Operación/División con resultyado indefinido");
        } catch (Exception e){
            System.err.println("\nError. Ha surgido un error inesperado distinto del resto de errores.");
        } finally{
            System.out.println("\nAcción ejecutable siempre");
        }
    }
}