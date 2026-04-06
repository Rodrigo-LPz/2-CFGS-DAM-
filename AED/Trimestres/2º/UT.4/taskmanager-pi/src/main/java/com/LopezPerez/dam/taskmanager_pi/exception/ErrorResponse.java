package com.LopezPerez.dam.taskmanager_pi.exception;

// Importa de la biblioteca/librería el paquete "AllArgsConstructor".
import lombok.AllArgsConstructor;
// Importa de la biblioteca/librería el paquete "Data".
import lombok.Data;

// Importa de la biblioteca/librería el paquete "LocalDateTime".
import java.time.LocalDateTime;

@Data               // Indica que Lombok genere métodos comunes y facilite el manejo de la clase.
@AllArgsConstructor // Genera un constructor que recibe todos los campos como parámetros.
// Crea la clase "ErrorResponse".
public class ErrorResponse{
    // Fecha y hora exactas en las que se produjo el error
    private LocalDateTime timestamp;

    // Código de estado HTTP asociado al error (por ejemplo, 400, 404, 500).
    private int status;

    // Nombre o tipo genérico del error HTTP.
    private String error;

    // Mensaje descriptivo del error, pensado para el cliente o desarrollador.
    private String message;

    // Ruta de la petición que provocó el error.
    private String path;
}