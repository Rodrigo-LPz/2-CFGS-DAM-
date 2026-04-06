package com.LopezPerez.dam.taskmanager_pi.dto;


// Importa archivo "Priority" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Priority;

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
@NoArgsConstructor  // Hacemos uso de "Lombok" para generar un constructor vacío.
@AllArgsConstructor // Necesario para la serialización/deserialización en respuestas JSON.
// Crea la clase "TaskResponseDTO".
public class TaskResponseDTO{
    // ==================== ATRIBUTOS ====================
        // Declara las variables, atributos.
    private String id;
    private String title;
    private String description;
    private Boolean completed;
    private Priority priority;
    private List<String> tags;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
}
