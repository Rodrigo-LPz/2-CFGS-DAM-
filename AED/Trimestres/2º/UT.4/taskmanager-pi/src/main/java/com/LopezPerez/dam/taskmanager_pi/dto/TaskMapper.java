package com.LopezPerez.dam.taskmanager_pi.dto;


// Importa archivo "Task" del paquete "model".
import com.LopezPerez.dam.taskmanager_pi.model.Task;

// Importa de la biblioteca/librería el paquete "Component".
import org.springframework.stereotype.Component;

@Component
// Crea la clase "TaskMapper".
public class TaskMapper{
    // Crea el metodo/función "toEntity(TaskRequestDTO requestDTO)". Su funcionamiento consiste en convertir un "TaskRequestDTO" a una entidad "Task". Se usará cuando el cliente envía datos para crear/actualizar.
        /*
         * @param requestDTO DTO con los datos recibidos.
         * @return Entidad Task lista para persistir.
         */
    public Task toEntity(TaskRequestDTO requestDTO){
        Task task = new Task();
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setCompleted(requestDTO.getCompleted());
        task.setPriority(requestDTO.getPriority());
        task.setTags(requestDTO.getTags());
        task.setCategory(requestDTO.getCategory());
        task.setDueDate(requestDTO.getDueDate());
        return task;
    }

    // Crea el metodo/función "toResponseDTO (Task task)". Su funcionamiento consiste en convertir una entidad "Task" a "TaskResponseDTO". Se usará cuando enviemos datos al cliente.
        /*
         * @param task Entidad Task desde la base de datos
         * @return DTO con todos los campos para el cliente
         */
    public TaskResponseDTO toResponseDTO(Task task){
        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(task.getId());
        responseDTO.setTitle(task.getTitle());
        responseDTO.setDescription(task.getDescription());
        responseDTO.setCompleted(task.getCompleted());
        responseDTO.setPriority(task.getPriority());
        responseDTO.setTags(task.getTags());
        responseDTO.setCategory(task.getCategory());
        responseDTO.setCreatedAt(task.getCreatedAt());
        responseDTO.setUpdatedAt(task.getUpdatedAt());
        responseDTO.setDueDate(task.getDueDate());
        return responseDTO;
    }

    // Crea el metodo/función "toResponseDTO (Task task)". Su funcionamiento consiste en actualizar una entidad "Task" existente con datos de un "RequestDTO". Se usará en operaciones "PUT" para actualizar solo los campos editables.
        /*
         * @param task Entidad existente a actualizar
         * @param requestDTO DTO con los nuevos datos
         */
    public void updateEntityFromDTO(Task task, TaskRequestDTO requestDTO){
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setCompleted(requestDTO.getCompleted());
        task.setPriority(requestDTO.getPriority());
        task.setTags(requestDTO.getTags());
        task.setCategory(requestDTO.getCategory());
        task.setDueDate(requestDTO.getDueDate());
    }
}
