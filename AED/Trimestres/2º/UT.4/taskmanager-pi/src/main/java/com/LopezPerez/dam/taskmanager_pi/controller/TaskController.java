package com.LopezPerez.dam.taskmanager_pi.controller;


// Importa archivo "TaskStatisticsDTO" del paquete "EjercicioOpcional9".
import com.LopezPerez.dam.taskmanager_pi.dto.EjercicioOpcional9.TaskStatisticsDTO;
// Importa archivo "Priority" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Priority;
// Importa archivo "Task" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Task;
// Importa archivo "TaskService" del paquete "service".
import com.LopezPerez.dam.taskmanager_pi.service.TaskService;

// Importa de la biblioteca/librería el paquete "Valid".
import jakarta.validation.Valid;

// Importa de la biblioteca/librería el paquete "Page".
import org.springframework.data.domain.Page;
// Importa de la biblioteca/librería el paquete "PageRequest".
import org.springframework.data.domain.PageRequest;
// Importa de la biblioteca/librería el paquete "Pageable".
import org.springframework.data.domain.Pageable;
// Importa de la biblioteca/librería el paquete "Sort".
import org.springframework.data.domain.Sort;
// Importa de la biblioteca/librería el paquete "HttpStatus".
import org.springframework.http.HttpStatus;
// Importa de la biblioteca/librería el paquete "ResponseEntity".
import org.springframework.http.ResponseEntity;
// Importa de la biblioteca/librería los paquetes de mapeo HTTP.
import org.springframework.web.bind.annotation.*;

// Importa de la biblioteca/librería el paquete "List".
import java.util.List;

// ------------------------------------------------------------
    // Adición paso 7
        import com.LopezPerez.dam.taskmanager_pi.dto.TaskMapper;
        import com.LopezPerez.dam.taskmanager_pi.dto.TaskRequestDTO;
        import com.LopezPerez.dam.taskmanager_pi.dto.TaskResponseDTO;

        import java.util.stream.Collectors;
// ------------------------------------------------------------

@RestController                    // Indica que esta clase es un controlador REST.
@RequestMapping("/api/tasks")   // Define la ruta base para todos los "endpoints" de este controlador.
// Crea la clase "TaskController".
public class TaskController{
    // Objeto privado para el servicio de tareas que contendrá la lógica de negocio.
    private final TaskService taskService;

    // ------------------------------------------------------------
        // Adición paso 7
            // Objeto privado para el mapper que convierte entre entidades y DTO's.
            private final TaskMapper taskMapper;
    // ------------------------------------------------------------


    // Constructor con inyección de dependencias. Su función partirá de Spring, este inyectará automáticamente una instancia de "TaskService".
    //public TaskController(TaskService taskService){ this.taskService = taskService; }
    // ------------------------------------------------------------
        // Adición paso 7
            public TaskController(TaskService taskService, TaskMapper taskMapper){
                this.taskService = taskService;
                this.taskMapper = taskMapper;
            }
    // ------------------------------------------------------------


    // ==================== ENDPOINTS con operaciones CRUD básicas ====================
        // Mapeador "post" para crear una nueva tarea.
    /*
     *@PostMapping
     *public ResponseEntity<Task> createTask(@Valid @RequestBody Task task){
     *    Task createdTask = taskService.createTask(task);
     *
     *    return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
     *}
     */
    // ------------------------------------------------------------
        // Adición paso 7
            @PostMapping
            public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO requestDTO){
                Task task = taskMapper.toEntity(requestDTO);                            // Convierte DTO a entidad
                Task createdTask = taskService.createTask(task);
                TaskResponseDTO responseDTO = taskMapper.toResponseDTO(createdTask);    // Convierte entidad a DTO

                return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
            }
    // ------------------------------------------------------------

        // Mapeador "get" para obtener todas las tareas.
    /*
     *@GetMapping
     *public ResponseEntity<List<Task>> getAllTasks(){
     *    List<Task> tasks = taskService.getAllTasks();
     *
     *    return ResponseEntity.ok(tasks);
     *}
    */
    // ------------------------------------------------------------
        // Adición paso 7
            @GetMapping
            public ResponseEntity<List<TaskResponseDTO>> getAllTasks(){
                List<Task> tasks = taskService.getAllTasks();
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());    // Convierte a lista

                return ResponseEntity.ok(responseDTOs);
            }
     // ------------------------------------------------------------

        // Mapeador "get" para obtener una tarea por su número de identificación (ID).
    /*
     *@GetMapping("/{id}")
     *public ResponseEntity<Task> getTaskById(@PathVariable String id){
     *    Task task = taskService.getTaskById(id);
     *
     *    return ResponseEntity.ok(task);
     *}
    */
    // ------------------------------------------------------------
        // Adición paso 7
            @GetMapping("/{id}")
            public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable String id){
                Task task = taskService.getTaskById(id);
                TaskResponseDTO responseDTO = taskMapper.toResponseDTO(task);

                return ResponseEntity.ok(responseDTO);
            }
    // ------------------------------------------------------------

        // Mapeador "put" para actualizar una tarea con estado "completada".
    /*
     *@PutMapping("/{id}")
     *public ResponseEntity<Task> updateTask(@PathVariable String id, @Valid @RequestBody Task task){
     *    Task updatedTask = taskService.updateTask(id, task);
     *
     *    return ResponseEntity.ok(updatedTask);
     *}
     */
    // ------------------------------------------------------------
        // Adición paso 7
            @PutMapping("/{id}")
            public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable String id, @Valid @RequestBody TaskRequestDTO requestDTO){
                Task task = taskMapper.toEntity(requestDTO);
                Task updatedTask = taskService.updateTask(id, task);
                TaskResponseDTO responseDTO = taskMapper.toResponseDTO(updatedTask);

                return ResponseEntity.ok(responseDTO);
            }
    // ------------------------------------------------------------

        // Mapeador "delete" para eliminar una tarea por su número de identificación (ID).
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id){
        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }


    // ==================== ENDPOINTS DE ESTADO ====================
        // Mapeador "patch" para delimitar/marcar una tarea con estado "completada".
    /*
     *@PatchMapping("/{id}/complete")
     *public ResponseEntity<Task> markAsCompleted(@PathVariable String id){
     *    Task task = taskService.markAsCompleted(id);
     *
     *    return ResponseEntity.ok(task);
     *}
     */
    // ------------------------------------------------------------
        // Adición paso 7
            @PatchMapping("/{id}/complete")
            public ResponseEntity<TaskResponseDTO> markAsCompleted(@PathVariable String id){
                Task task = taskService.markAsCompleted(id);
                TaskResponseDTO responseDTO = taskMapper.toResponseDTO(task);

                return ResponseEntity.ok(responseDTO);
            }
    // ------------------------------------------------------------

        // Mapeador "patch" para delimitar/marcar una tarea con estado "no completada".
    /*
     *@PatchMapping("/{id}/incomplete")
     *public ResponseEntity<Task> markAsIncomplete(@PathVariable String id){
     *    Task task = taskService.markAsIncomplete(id);
     *
     *    return ResponseEntity.ok(task);
     *}
     */
    // ------------------------------------------------------------
        // Adición paso 7
            @PatchMapping("/{id}/incomplete")
            public ResponseEntity<TaskResponseDTO> markAsIncomplete(@PathVariable String id){
                Task task = taskService.markAsIncomplete(id);
                TaskResponseDTO responseDTO = taskMapper.toResponseDTO(task);

                return ResponseEntity.ok(responseDTO);
            }
    // ------------------------------------------------------------

        // Mapeador "get" para obtener todas las tareas con estado "completada".
    /*
     *@GetMapping("/completed")
     *public ResponseEntity<List<Task>> getCompletedTasks(){
     *    List<Task> tasks = taskService.getCompletedTasks();
     *
     *    return ResponseEntity.ok(tasks);
     *}
     */
    // ------------------------------------------------------------
        // Adición paso 7
            @GetMapping("/completed")
            public ResponseEntity<List<TaskResponseDTO>> getCompletedTasks(){
                List<Task> tasks = taskService.getCompletedTasks();
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);
            }
    // ------------------------------------------------------------

        // Mapeador "get" para obtener todas las tareas con estado "no completada".
    /*
     *@GetMapping("/pending")
     *public ResponseEntity<List<Task>> getPendingTasks(){
     *    List<Task> tasks = taskService.getPendingTasks();
     *
     *    return ResponseEntity.ok(tasks);
     *}
     */
    // ------------------------------------------------------------
        // Adición paso 7
            @GetMapping("/pending")
            public ResponseEntity<List<TaskResponseDTO>> getPendingTasks(){
                List<Task> tasks = taskService.getPendingTasks();
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);
            }
    // ------------------------------------------------------------


    // ==================== ENDPOINTS DE BÚSQUEDA ====================
        // Mapeador "get" para buscar tareas con el atributo/campo "categoria".
     /*
      *@GetMapping("/category/{category}")
      *public ResponseEntity<List<Task>> getTasksByCategory(@PathVariable String category){
      *    List<Task> tasks = taskService.getTasksByCategory(category);
      *
      *    return ResponseEntity.ok(tasks);
      *}
      */
    // ------------------------------------------------------------
        // Adición paso 7
            @GetMapping("/category/{category}")
            public ResponseEntity<List<TaskResponseDTO>> getTasksByCategory(@PathVariable String category){
                List<Task> tasks = taskService.getTasksByCategory(category);
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);
            }
    // ------------------------------------------------------------

        // Mapeador "get" para buscar tareas con el atributo/campo "etiqueta".
    /*
     *@GetMapping("/tag/{tag}")
     *public ResponseEntity<List<Task>> getTasksByTag(@PathVariable String tag){
     *    List<Task> tasks = taskService.getTasksByTag(tag);
     *
     *    return ResponseEntity.ok(tasks);
     *}
     */
    // ------------------------------------------------------------
        // Adición paso 7
            @GetMapping("/tag/{tag}")
            public ResponseEntity<List<TaskResponseDTO>> getTasksByTag(@PathVariable String tag){
                List<Task> tasks = taskService.getTasksByTag(tag);
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);
            }
    // ------------------------------------------------------------

        // Mapeador "get" para buscar tareas con el atributo/campo "prioridad (BAJA, MEDIA, ALTA)".
    /*
     *@GetMapping("/priority/{priority}")
     *public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Priority priority){
     *    List<Task> tasks = taskService.getTasksByPriority(priority);
     *
     *    return ResponseEntity.ok(tasks);
     *}
     */
    // ------------------------------------------------------------
        // Adición paso 7
            @GetMapping("/priority/{priority}")
            public ResponseEntity<List<TaskResponseDTO>> getTasksByPriority(@PathVariable Priority priority){
                List<Task> tasks = taskService.getTasksByPriority(priority);
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);

            }
    // ------------------------------------------------------------

        // Mapeador "get" para buscar tareas con el atributo/campo "título (búsqueda parcial, ignora mayúsculas/minúsculas)".
    /*
     *@GetMapping("/search")
     *public ResponseEntity<List<Task>> searchByTitle(@RequestParam String title){
     *    List<Task> tasks = taskService.searchByTitle(title);
     *
     *    return ResponseEntity.ok(tasks);
     *}
     */
    // ------------------------------------------------------------
        // Adición paso 7
            @GetMapping("/search")
            public ResponseEntity<List<TaskResponseDTO>> searchByTitle(@RequestParam String title){
                List<Task> tasks = taskService.searchByTitle(title);
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);
            }
    // ------------------------------------------------------------

        // Mapeador "get" para obtener las tareas con el atributo/campo "vencidas (dueDate anterior a la fecha actual)".
    /*
     *@GetMapping("/overdue")
     *public ResponseEntity<List<Task>> getOverdueTasks(){
     *    List<Task> tasks = taskService.getOverdueTasks();
     *
     *    return ResponseEntity.ok(tasks);
     *}
     */
    // ------------------------------------------------------------
        // Adición paso 7
            @GetMapping("/overdue")
            public ResponseEntity<List<TaskResponseDTO>> getOverdueTasks(){
                List<Task> tasks = taskService.getOverdueTasks();
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);
            }
    // ------------------------------------------------------------

    // ------------------------------------------------------------
        // Adición paso 9 (Adición endpoint en TaskController)
            // ==================== ENDPOINT DE ESTADÍSTICAS ====================
            @GetMapping("/statistics")
            public ResponseEntity<TaskStatisticsDTO> getStatistics(){
                TaskStatisticsDTO statistics = taskService.getStatistics();
                return ResponseEntity.ok(statistics);
            }
    // ------------------------------------------------------------

    // ------------------------------------------------------------
        // Adición paso 9 (Adición endpoint en TaskController)
            // ==================== ENDPOINT CON PAGINACIÓN Y ORDENACIÓN ====================
            @GetMapping("/paginated")
            public ResponseEntity<Page<TaskResponseDTO>> getAllTasksPaginated(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "createdAt") String sortBy, @RequestParam(defaultValue = "desc") String sortDirection){
                // Crea el objeto Sort.
                Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

                // Crea el objeto Pageable.
                Pageable pageable = PageRequest.of(page, size, sort);

                // Obtiene la página de tareas.
                Page<Task> taskPage = taskService.getAllTasksPaginated(pageable);

                // Convierte a DTOs.
                Page<TaskResponseDTO> responsePage = taskPage.map(taskMapper::toResponseDTO);

                return ResponseEntity.ok(responsePage);
            }
    // ------------------------------------------------------------

    // ------------------------------------------------------------
        // Adición paso 9 (Adición endpoint en TaskController)
            // ==================== ENDPOINT DE FILTROS COMBINADOS ====================
            @GetMapping("/pending/priority/{priority}")
            public ResponseEntity<List<TaskResponseDTO>> getPendingTasksByPriority(@PathVariable Priority priority){
                List<Task> tasks = taskService.getPendingTasksByPriority(priority);
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);
            }

            @GetMapping("/completed/category/{category}")
            public ResponseEntity<List<TaskResponseDTO>> getCompletedTasksByCategory(@PathVariable String category){
                List<Task> tasks = taskService.getCompletedTasksByCategory(category);
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);
            }

            @GetMapping("/priority/{priority}/category/{category}")
            public ResponseEntity<List<TaskResponseDTO>> getTasksByPriorityAndCategory(@PathVariable Priority priority, @PathVariable String category){
                List<Task> tasks = taskService.getTasksByPriorityAndCategory(priority, category);
                List<TaskResponseDTO> responseDTOs = tasks.stream().map(taskMapper::toResponseDTO).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);
            }
    // ------------------------------------------------------------
}