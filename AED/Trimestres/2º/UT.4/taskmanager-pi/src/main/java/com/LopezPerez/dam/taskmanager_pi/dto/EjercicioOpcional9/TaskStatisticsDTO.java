package com.LopezPerez.dam.taskmanager_pi.dto.EjercicioOpcional9;


// Importa de la biblioteca/librería el paquete "AllArgsConstructor".
import lombok.AllArgsConstructor;
// Importa de la biblioteca/librería el paquete "Data".
import lombok.Data;
// Importa de la biblioteca/librería el paquete "NoArgsConstructor".
import lombok.NoArgsConstructor;

// Importa de la biblioteca/librería el paquete "Map".
import java.util.Map;

@Data               // Hacemos uso de "Lombok" para generar automáticamente los métodos "getters", "setters", "equals", "hashCode" y "toString" necesarios para el código de la app.
@NoArgsConstructor  // Hacemos uso de "Lombok" para generar un constructor vacío (necesario para deserialización JSON).
@AllArgsConstructor // Hacemos uso de "Lombok" para generar un constructor con todos los atributos como parámetros.
// Crea la clase "TaskStatisticsDTO".
public class TaskStatisticsDTO{
    // ==================== ATRIBUTOS ====================
        // Declara las variables, atributos.
    private long totalTasks;
    private long completedTasks;
    private long pendingTasks;
    private long overdueTasks;
    private Map<String, Long> tasksByPriority;
    private Map<String, Long> tasksByCategory;
    private double completionPercentage;
}