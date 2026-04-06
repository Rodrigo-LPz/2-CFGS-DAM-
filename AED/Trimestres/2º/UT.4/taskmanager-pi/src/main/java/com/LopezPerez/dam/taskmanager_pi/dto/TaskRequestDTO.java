package com.LopezPerez.dam.taskmanager_pi.dto;


// Importa archivo "Priority" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Priority;

// Importa de la biblioteca/librería el paquete "NotBlank".
import jakarta.validation.constraints.NotBlank;
// Importa de la biblioteca/librería el paquete "NotNull".
import jakarta.validation.constraints.NotNull;
// Importa de la biblioteca/librería el paquete "Size".
import jakarta.validation.constraints.Size;

// Importa de la biblioteca/librería el paquete "AllArgsConstructor".
import lombok.AllArgsConstructor;
// Importa de la biblioteca/librería el paquete "Data".
import lombok.Data;
// Importa de la biblioteca/librería el paquete "NoArgsConstructor".
import lombok.NoArgsConstructor;

// Importa de la biblioteca/librería el paquete "LocalDateTime".
import java.time.LocalDateTime;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;

@Data               // Hacemos uso de "Lombok" para generar automáticamente los métodos "getters", "setters", "equals", "hashCode" y "toString" necesarios para el código de la app.
@NoArgsConstructor  // Hacemos uso de "Lombok" para generar un constructor vacío (necesario para deserialización JSON).
@AllArgsConstructor // Hacemos uso de "Lombok" para generar un constructor con todos los atributos como parámetros.
// Crea la clase "TaskRequestDTO".
public class TaskRequestDTO{
    // ==================== ATRIBUTOS ====================
        // Declara las variables, atributos.
            /*
             * 1. Atributo para el título de la tarea.
             * 2. Se 'ancla'/enlaza como campo obligatorio ("NotBlank") y de máximo 100 caracteres ("Size").
             */
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    private String title;

            /*
             * 1. Atributo para el título de la tarea.
             * 2. Se 'ancla'/enlaza como campo de máximo 500 caracteres ("Size").
             */
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String description;

            /*
             * 1. Atributo para el título de la tarea.
             * 2. Se 'ancla'/enlaza como campo obligatorio ("NotNull").
             */
    @NotNull(message = "El estado de completado es obligatorio")
    private Boolean completed;

            /*
             * 1. Atributo para el título de la tarea.
             * 2. Se 'ancla'/enlaza como campo obligatorio ("NotNull").
             */
    @NotNull(message = "La prioridad es obligatoria")
    private Priority priority;

    private List<String> tags;

    private String category;

    private LocalDateTime dueDate;
}