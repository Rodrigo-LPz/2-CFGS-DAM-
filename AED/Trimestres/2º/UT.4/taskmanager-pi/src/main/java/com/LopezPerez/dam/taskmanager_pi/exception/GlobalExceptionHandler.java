package com.LopezPerez.dam.taskmanager_pi.exception;


// Importa de la biblioteca/librería el paquete "HttpStatus".
import org.springframework.http.HttpStatus;
// Importa de la biblioteca/librería el paquete "ResponseEntity".
import org.springframework.http.ResponseEntity;
// Importa de la biblioteca/librería el paquete "FieldError".
import org.springframework.validation.FieldError;
// Importa de la biblioteca/librería el paquete "MethodArgumentNotValidException".
import org.springframework.web.bind.MethodArgumentNotValidException;
// Importa de la biblioteca/librería el paquete "ControllerAdvice".
// Importa de la biblioteca/librería el paquete "ExceptionHandler".
import org.springframework.web.bind.annotation.ExceptionHandler;
// Importa de la biblioteca/librería el paquete "ResponseBody".
// Importa de la biblioteca/librería el paquete "RestControllerAdvice".
import org.springframework.web.bind.annotation.RestControllerAdvice;
// Importa de la biblioteca/librería el paquete "WebRequest".
import org.springframework.web.context.request.WebRequest;

// Importa de la biblioteca/librería el paquete "LocalDateTime".
import java.time.LocalDateTime;
// Importa de la biblioteca/librería el paquete "HashMap".
import java.util.HashMap;
// Importa de la biblioteca/librería el paquete "Map".
import java.util.Map;

// Anotación que combina "@ControllerAdvice" y "@ResponseBody". Lo que permite interceptar excepciones lanzadas por cualquier controlador "REST".
@RestControllerAdvice
// Crea la clase "GlobalExceptionHandler".
public class GlobalExceptionHandler{
    // Bloque de código para interceptar y manejar la excepción "TaskNotFoundException" que se traducirá automáticamente en una respuesta HTTP 404 (Not Found).
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request){
        // Construye la respuesta de error estandarizada.
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),                                                                      // Momento exacto del error.
                HttpStatus.NOT_FOUND.value(),                                                             // Código HTTP 404.
                "Not Found",                                                                              // Tipo de error HTTP.
                ex.getMessage(),                                                                          // Mensaje específico de la excepción.
                request.getDescription(false).replace("uri=", "")   // Ruta de la petición.
        );

        // Devuelve la respuesta junto con el estado HTTP correspondiente.
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Bloque de código para interceptar y manejar las excepciones producidas por validaciones ("@Valid", "@NotNull", etc.). Que se traducirá en una respuesta HTTP 400 (Bad Request).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request){
        // Mapa que almacenará los errores por campo.
        Map<String, String> errors = new HashMap<>();

        // Recorre todos los errores de validación detectados.
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            // Obtiene el nombre del campo que ha fallado.
            String fieldName = ((FieldError) error).getField();

            // Obtiene el mensaje de error asociado a la validación.
            String errorMessage = error.getDefaultMessage();

            // Almacena el error en el mapa.
            errors.put(fieldName, errorMessage);
        });

        // Construye la respuesta completa de error.
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());                                                                   // Fecha y hora del error.
        response.put("status", HttpStatus.BAD_REQUEST.value());                                                           // Código HTTP 400.
        response.put("error", "Bad Request");                                                                             // Tipo de error.
        response.put("message", "Error de validación");                                                                   // Mensaje general.
        response.put("errors", errors);                                                                                   // Detalle por campo.
        response.put("path", request.getDescription(false).replace("uri=", ""));    // Ruta solicitada.

        // Devuelve la respuesta con estado HTTP 400.
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Bloque de código para interceptar y manejar excepciones genéricas, cualquier excepción no controlada previamente. Que se traducirá en una respuesta HTTP 500 (Internal Server Error).
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request){
        // Construye una respuesta de error genérica.
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),                                                                      // Momento del error.
                HttpStatus.INTERNAL_SERVER_ERROR.value(),                                                 // Código HTTP 500.
                "Internal Server Error",                                                                  // Tipo de error.
                ex.getMessage(),                                                                          // Mensaje de la excepción.
                request.getDescription(false).replace("uri=", "")   // Ruta de la petición.
        );

        // Devuelve la respuesta con estado HTTP 500.
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}