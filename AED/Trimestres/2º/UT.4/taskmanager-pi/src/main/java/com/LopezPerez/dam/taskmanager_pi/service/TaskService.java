package com.LopezPerez.dam.taskmanager_pi.service;


// Importa archivo "TaskStatisticsDTO" del paquete "EjercicioOpcional9".
import com.LopezPerez.dam.taskmanager_pi.dto.EjercicioOpcional9.TaskStatisticsDTO;
// Importa archivo "TaskMapper" del paquete "dto".
import com.LopezPerez.dam.taskmanager_pi.dto.TaskMapper;
// Importa archivo "Priority" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Priority;
// Importa archivo "Task" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Task;
// Importa archivo "Page" del paquete "domain".
import org.springframework.data.domain.Page;
// Importa archivo "Pageable" del paquete "domain".
import org.springframework.data.domain.Pageable;

// Importa de la biblioteca/librería el paquete "LocalDateTime".
import java.time.LocalDateTime;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;

// Crea la interfaz "TaskService".
public interface TaskService{
    // Crea loos métodos para interactuar con la aplicación.
        // Crea una tarea.
    Task createTask(Task task);

        // Obtiene todas las tareas.
    List<Task> getAllTasks();

        // Obtiene una tarea por número de identificación, por su ID.
    Task getTaskById(String id);

        // Actualiza una tarea.
    Task updateTask(String id, Task task);

        // Elimina una tarea.
    void deleteTask(String id);

        // Marca la tarea como completada.
    Task markAsCompleted(String id);

        // Marca la tarea como no completada.
    Task markAsIncomplete(String id);

        // Busca las tareas con estado completadas.
    List<Task> getCompletedTasks();

        // Busca lsa tareas con estado pendientes.
    List<Task> getPendingTasks();

        // Busca por categoría.
    List<Task> getTasksByCategory(String category);

        // Busca las tareas por etiqueta.
    List<Task> getTasksByTag(String tag);

        // Busca las tareas por prioridad.
    List<Task> getTasksByPriority(Priority priority);

        // Busca las tareas por título.
    List<Task> searchByTitle(String title);

        // Busca las tareas con estado vencidas.
    List<Task> getOverdueTasks();

    // ------------------------------------------------------------
        // Adición paso 9 (Implementación en TaskServiceImpl)
            // Obtiene las estadísticas generales de las tareas.
            TaskStatisticsDTO getStatistics();
    // ------------------------------------------------------------

    // ------------------------------------------------------------
        // Adición paso 9 (Adición metodo en TaskService)
            // Obtiene las tareas con paginación.
            Page<Task> getAllTasksPaginated(Pageable pageable);
    // ------------------------------------------------------------

    // ------------------------------------------------------------
        // Adición paso 9 (Adición metodo en TaskService)
            // Busca las tareas pendientes por prioridad.
            List<Task> getPendingTasksByPriority(Priority priority);

            // Busca las tareas completadas por categoría.
            List<Task> getCompletedTasksByCategory(String category);

            // Busca las tareas por prioridad y categoría.
            List<Task> getTasksByPriorityAndCategory(Priority priority, String category);
    // ------------------------------------------------------------

}