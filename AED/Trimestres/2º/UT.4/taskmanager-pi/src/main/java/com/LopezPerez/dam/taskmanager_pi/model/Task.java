package com.LopezPerez.dam.taskmanager_pi.model;


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

// Importa de la biblioteca/librería el paquete "Id".
import org.springframework.data.annotation.Id;
// Importa de la biblioteca/librería el paquete "Document".
import org.springframework.data.mongodb.core.mapping.Document;

// Importa de la biblioteca/librería el paquete "LocalDateTime".
import java.time.LocalDateTime;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;

// Definimos una entidad que representará a las tareas.
    /*
     * 1. Primero mapea la colección "tasks" en la base de datos para MongoDb.
     * 2. Después define cómo se van a tratar los datos ("@Data", "@NoArgsConstructor" y "@AllArgsConstructor") con la librería "Lombok".
     */
@Document(collection = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
// Crea la clase "Task".
public class Task{
    // ==================== ATRIBUTOS ====================
        // Declara las variables, atributos.
            /*
             * 1. Se genera el identificador, "ID", único (clave primaria) de "ejemplar".
             */
    @Id
    private String id;

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
    private Boolean completed = false;

            /*
             * 1. Atributo para el título de la tarea.
             * 2. Se 'ancla'/enlaza como campo obligatorio ("NotNull").
             */
    @NotNull(message = "La prioridad es obligatoria")
    private Priority priority;

    private List<String> tags;

    private String category;

    private LocalDateTime  createdAt;

    private LocalDateTime  updatedAt;

    private LocalDateTime  dueDate;
}