package com.LopezPerez.dam.taskmanager_pi.service;


// Importa archivo "TaskStatisticsDTO" del paquete "EjercicioOpcional9".
import com.LopezPerez.dam.taskmanager_pi.dto.EjercicioOpcional9.TaskStatisticsDTO;
// Importa archivo "TaskNotFoundException" del paquete "exception".
import com.LopezPerez.dam.taskmanager_pi.exception.TaskNotFoundException;
// Importa archivo "Priority" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Priority;
// Importa archivo "Task" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Task;
// Importa archivo "TaskRepository" del paquete "repository".
import com.LopezPerez.dam.taskmanager_pi.repository.TaskRepository;

// Importa de la biblioteca/librería el paquete "Service".
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// Importa de la biblioteca/librería el paquete "LocalDateTime".
import java.time.LocalDateTime;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;
// Importa de la biblioteca/librería el paquete "Map".
import java.util.Map;
// Importa de la biblioteca/librería el paquete "Collectors".
import java.util.stream.Collectors;

@Service
// Crea la clase "TaskServiceImpl".
public class TaskServiceImpl implements TaskService{
    // Repositorio de tareas que permitirá interactuar con la base de datos.
    private final TaskRepository taskRepository;

    // Constructor con inyección de dependencias.
        // Spring inyecta automáticamente una instancia de "TaskRepository".
    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    // Metodo encargado de crear una nueva tarea.
    @Override
    public Task createTask(Task task){
        // Establece la fecha y hora de creación de la tarea.
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        // Si el estado "completed" viene como "null", se inicializa la tarea con valor "false" por defecto.
        if (task.getCompleted() == null){ task.setCompleted(false); }

        // Guarda la tarea en la base de datos y devuelve la entidad persistida.
        return taskRepository.save(task);
    }

    // Obtiene todas las tareas almacenadas.
    @Override
    public List<Task> getAllTasks(){ return taskRepository.findAll(); }

    // Obtiene una tarea a partir de su ID. Si no existe, lanza una excepción con un mensaje descriptivo.
    @Override
    //public Task getTaskById(String id){ return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró la tarea con ID: " + id)); }
    public Task getTaskById(String id){ return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)); }

    // Actualiza una tarea existente.
    @Override
    public Task updateTask(String id, Task task){
        // Recupera la tarea actual desde la base de datos.
        Task existingTask = getTaskById(id);

        // Actualiza los campos editables de la tarea.
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setCompleted(task.getCompleted());
        existingTask.setPriority(task.getPriority());
        existingTask.setTags(task.getTags());
        existingTask.setCategory(task.getCategory());
        existingTask.setDueDate(task.getDueDate());

        // Actualiza la fecha de modificación.
        existingTask.setUpdatedAt(LocalDateTime.now());

        // Guarda los cambios y devuelve la tarea actualizada.
        return taskRepository.save(existingTask);
    }

    // Elimina una tarea por su ID.
    @Override
    public void deleteTask(String id){
        // Obtiene la tarea para asegurar que existe.
        Task task = getTaskById(id);

        // Elimina la tarea de la base de datos.
        taskRepository.delete(task);
    }

    // Marca una tarea como completada.
    @Override
    public Task markAsCompleted(String id){
        // Recupera la tarea por ID.
        Task task = getTaskById(id);

        // Establece el estado de completada.
        task.setCompleted(true);

        // Actualiza la fecha de modificación.
        task.setUpdatedAt(LocalDateTime.now());

        // Guarda y devuelve la tarea modificada.
        return taskRepository.save(task);
    }

    // Marca una tarea como pendiente (no completada).
    @Override
    public Task markAsIncomplete(String id){
        // Recupera la tarea por ID.
        Task task = getTaskById(id);

        // Establece el estado como no completada.
        task.setCompleted(false);

        // Actualiza la fecha de modificación.
        task.setUpdatedAt(LocalDateTime.now());

        // Guarda y devuelve la tarea modificada.
        return taskRepository.save(task);
    }

    // Obtiene todas las tareas que están completadas.
    @Override
    public List<Task> getCompletedTasks(){ return taskRepository.findByCompleted(true); }

    // Obtiene todas las tareas pendientes.
    @Override
    public List<Task> getPendingTasks(){ return taskRepository.findByCompleted(false); }

    // Obtiene tareas filtradas por categoría.
    @Override
    public List<Task> getTasksByCategory(String category){ return taskRepository.findByCategory(category); }

    // Obtiene tareas que contienen una etiqueta específica.
    @Override
    public List<Task> getTasksByTag(String tag){ return taskRepository.findByTagsContaining(tag); }

    // Obtiene tareas según su nivel de prioridad.
    @Override
    public List<Task> getTasksByPriority(Priority priority){ return taskRepository.findByPriority(priority); }

    // Busca tareas cuyo título contenga un texto, ignorando mayúsculas y minúsculas.
    @Override
    public List<Task> searchByTitle(String title){ return taskRepository.findByTitleContainingIgnoreCase(title); }

    // Obtiene todas las tareas cuya fecha límite ya ha expirado.
    @Override
    public List<Task> getOverdueTasks(){ return taskRepository.findByDueDateBefore(LocalDateTime.now()); }

    /*
     *@Override
     *public TaskStatisticsDTO getStatistics(){ return null; }
     */
    // ------------------------------------------------------------
        // Adición paso 9 (Adición del metodo en TaskService)
            @Override
            public TaskStatisticsDTO getStatistics(){
                // Obtiene todas las tareas.
                List<Task> allTasks = taskRepository.findAll();

                // Contabilizadores.
                long totalTasks = allTasks.size();
                long completedTasks = allTasks.stream().filter(Task::getCompleted).count();
                long pendingTasks = allTasks.stream().filter(task -> !task.getCompleted()).count();

                // cuenta las tareas vencidas/caducadas (fecha pasada y no completadas).
                long overdueTasks = allTasks.stream().filter(task -> !task.getCompleted() && task.getDueDate() != null && task.getDueDate().isBefore(LocalDateTime.now())).count();

                // Agrupa las tareas por su prioridad.
                Map<String, Long> tasksByPriority = allTasks.stream().collect(Collectors.groupingBy(task -> task.getPriority().name(), Collectors.counting()));

                // Agrupa las tareas por su categoría.
                Map<String, Long> tasksByCategory = allTasks.stream().filter(task -> task.getCategory() != null && !task.getCategory().isEmpty()).collect(Collectors.groupingBy(Task::getCategory,Collectors.counting()));

                // Calcula el porcentaje de finalización, de tareas finalizadas.
                double completionPercentage = totalTasks > 0 ? (completedTasks * 100.0) / totalTasks : 0.0;

                // Crea y devuelve el DTO
                return new TaskStatisticsDTO(
                        totalTasks,
                        completedTasks,
                        pendingTasks,
                        overdueTasks,
                        tasksByPriority,
                        tasksByCategory,
                        Math.round(completionPercentage * 100.0) / 100.0  // Redondea a 2 decimales.
                );
            }
    // ------------------------------------------------------------

    // ------------------------------------------------------------
        // Adición paso 9 (Implementación en TaskServiceImpl)
            @Override
            public Page<Task> getAllTasksPaginated(Pageable pageable) {
                return taskRepository.findAll(pageable);
            }
    // ------------------------------------------------------------

    // ------------------------------------------------------------
        // Adición paso 9 (Implementación en TaskServiceImpl)
            @Override
            public List<Task> getPendingTasksByPriority(Priority priority){ return taskRepository.findByCompletedAndPriority(false, priority); }

            @Override
            public List<Task> getCompletedTasksByCategory(String category){ return taskRepository.findByCategoryAndCompleted(category, true); }

            @Override
            public List<Task> getTasksByPriorityAndCategory(Priority priority, String category){ return taskRepository.findByPriorityAndCategory(priority, category); }
    // ------------------------------------------------------------
}