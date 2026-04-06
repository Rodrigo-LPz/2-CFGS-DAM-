package com.LopezPerez.dam.taskmanager_pi.repository;


// Importa archivo "Priority" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Priority;
// Importa archivo "Task" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Task;

// Importa de la biblioteca/librería el paquete "MongoRepository".
import org.springframework.data.mongodb.repository.MongoRepository;
// Importa de la biblioteca/librería el paquete "Repository".
import org.springframework.stereotype.Repository;

// Importa de la biblioteca/librería el paquete "LocalDateTime".
import java.time.LocalDateTime;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;

@Repository
// Crea la interfaz "TaskRepository".
public interface TaskRepository extends MongoRepository<Task, String>{
    // Buscar y obtiene todas las tareas según su estado de completado.
        /*
         * "True" -> tareas completadas.
         * "False" -> tareas completadas.
         */
    List<Task> findByCompleted(boolean completed);

    // Buscar y obtiene todas las tareas pertenecientes a una categoría concreta.
    List<Task> findByCategory(String category);

    // Buscar y obtiene todas las tareas que contienen una etiqueta específica dentro de la lista de tags.
    List<Task> findByTagsContaining(String tag);

    // Busca y obtiene todas las tareas filtradas por su nivel de prioridad.
    List<Task> findByPriority(Priority priority);

    // Busca tareas cuyo título contenga un texto determinado, ignorando diferencias entre mayúsculas y minúsculas.
    List<Task> findByTitleContainingIgnoreCase(String title);

    // Busca y obtiene todas las tareas cuya fecha límite es anterior a la indicada, permitiendo identificar tareas vencidas.
    List<Task> findByDueDateBefore(LocalDateTime date);

    // ------------------------------------------------------------
        // Adición paso 9 (Adición métodos en TaskRepository)
            // Busca las tareas por estado completado y prioridad.
            List<Task> findByCompletedAndPriority(boolean completed, Priority priority);

            // Busca las tareas por categoría y estado completado.
            List<Task> findByCategoryAndCompleted(String category, boolean completed);

            // Busca las tareas por prioridad y categoría.
            List<Task> findByPriorityAndCategory(Priority priority, String category);
    // ------------------------------------------------------------
}