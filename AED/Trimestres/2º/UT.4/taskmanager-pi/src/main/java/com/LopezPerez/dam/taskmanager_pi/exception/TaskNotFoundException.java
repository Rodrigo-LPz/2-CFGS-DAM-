package com.LopezPerez.dam.taskmanager_pi.exception;

// Crea la excepción "TaskNotFoundException".
public class TaskNotFoundException extends RuntimeException{
    // Constructor que recibe el identificador de la tarea que no ha sido encontrada.
    public TaskNotFoundException(String id){
        // Llama/hereda al constructor de la clase padre ("RuntimeException") y define un mensaje de error claro y descriptivo.
        super("No se encontró la tarea con ID: " + id);
    }
}