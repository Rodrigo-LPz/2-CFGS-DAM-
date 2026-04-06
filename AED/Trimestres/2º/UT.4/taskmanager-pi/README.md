__# 🕵 Gestor de Tareas - Task Manager API - con Spring Boot y MongoDB
API RESTful completa para gestión de tareas tipo Google Keep, desarrollada con Spring Boot 3 y MongoDB Atlas.


## 📋 Tabla de Contenidos
- [Características](#características)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Ejecución](#ejecución)
- [Documentación de la API](#documentación-de-la-api)
- [Ejemplos de Uso](#ejemplos-de-uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Autor](#autor)


## 🖊️ Descripción del Proyecto
Task Manager API es una aplicación backend desarrollada íntegramente con Spring Boot y MongoDB Atlas que permite gestionar tareas de manera eficiente mediante una API RESTful. El sistema ofrece operaciones CRUD completas, búsquedas avanzadas, filtros combinados, paginación, ordenación y estadísticas en tiempo real, todo implementado siguiendo las mejores prácticas de desarrollo con arquitectura en capas.


## ✨ Características
### Funcionalidades Principales:

* CRUD completo de tareas: Crear, leer, actualizar y eliminar tareas
* Búsquedas avanzadas: Por categoría, etiqueta, prioridad, título y estado
* Sistema de prioridades: BAJA, MEDIA, ALTA
* Organización flexible: Categorías personalizadas y etiquetas múltiples
* Fechas de vencimiento: Control de plazos con detección de tareas vencidas
* Estadísticas en tiempo real: Total de tareas, completadas, pendientes, vencidas y desglose por prioridad/categoría
* Paginación y ordenación: Navegación eficiente de grandes volúmenes de datos
* Filtros combinados: Búsquedas complejas con múltiples criterios
* Validación de datos: Validaciones automáticas con mensajes personalizados
* Manejo global de excepciones: Respuestas de error estructuradas y coherentes
* DTOs: Separación entre capa de presentación y capa de datos para mayor seguridad
* Base de datos en la nube: MongoDB Atlas con alta disponibilidad


## ✨ Estructura del Proyecto
    taskmanager-api/
    ├── Legal Information/
    │   └── LICENSE
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com.LopezPerez.dam.taskmanager_pi/
    │   │   │       ├── controller/
    │   │   │       │   └── TaskController
    │   │   │       ├── dto/
    │   │   │       │   ├── EjercicioOpciona9/
    │   │   │       │   │   └── TaskStatisticsDTO
    │   │   │       │   ├── TaskMapper
    │   │   │       │   ├── TaskRequestDTO
    │   │   │       │   └── TaskResponseDTO
    │   │   │       ├── exception/
    │   │   │       │   ├── ErrorResponse
    │   │   │       │   ├── GlobalExceptionHandler
    │   │   │       │   └── TaskNotFoundException
    │   │   │       ├── model/
    │   │   │       │   ├── Priority
    │   │   │       │   └── Task
    │   │   │       ├── repository/
    │   │   │       ├── service/
    │   │   │       │   ├── TaskService
    │   │   │       │   └── TaskServiceImpl
    │   │   │       └── TaskmanagerPiApplication
    │   │   └── resources/
    │   │       └── application.properties
    │   └── test/
    │       └── java/
    │           └── com.LopezPerezPiApplication/
    │               └── TaskmanagerPiApplicationTest
    ├── pom.xml
    └── README.md


## 🛠️ Tecnologías Utilizadas
Backend
* Java 17+ - Lenguaje de programación principal
* Spring Boot 3.x - Framework de desarrollo
* Spring Data MongoDB - Integración con MongoDB
* Maven - Gestión de dependencias y construcción del proyecto

Base de Datos
* MongoDB Atlas - Base de datos NoSQL en la nube

Herramientas y Librerías
* Lombok - Reducción de código boilerplate (getters, setters, constructores)
* Jakarta Validation - Validación de datos de entrada
* Spring DevTools - Recarga automática durante el desarrollo

Pruebas
* Postman / Thunder Client - Testing de endpoints REST


## 📦 Requisitos Previos
###Software Necesario
#### JDK 17 o superior
* Descarga: Oracle JDK(https://www.oracle.com/java/technologies/downloads/) o OpenJDK(https://adoptium.net/)
* Verificar instalación:
  ```
  java --version

#### Maven 3.6+
* Descarga: Apache Maven(https://maven.apache.org/download.cgi)
* Verificar instalación:
  ```
  mvn --version

#### Git (opcional, para clonar el repositorio)
* Descarga: Git(https://git-scm.com/downloads)

#### IDE recomendado (uno de los siguientes):
* IntelliJ IDEA (Community o Ultimate)
* Eclipse IDE for Java Developers
* Visual Studio Code + Extension Pack for Java

#### Postman o Thunder Client
* Postman: Descargar(https://www.postman.com/downloads/)
* Thunder Client: Extensión para VS Code

#### Cuenta en MongoDB Atlas (v. gratuita)
* Registro: MongoDB Atlas(https://www.mongodb.com/cloud/atlas/register)


## 🚀 Instalación
### Paso 1: Clonar o Descargar el Proyecto
* Si usas Git:
    ```
    git clone https://github.com/tu-usuario/taskmanager-api.git
    cd taskmanager-api
(O también puedes descargar el ZIP y extraer su contenido manualmente)

### Paso 2: Instalar Dependencias
        mvn clean install
(Este comando descargará todas las dependencias necesarias definidas en el archivo `pom.xml`.)


## ⚙️ Configuración
### Paso 1: Configurar MongoDB Atlas
#### Paso 1.1: Crear Cluster
    1. Accede a "MongoDB Atlas"(https://cloud.mongodb.com/)
    2. Inicia sesión o crea una cuenta gratuita
    3. Click en "Create" > "Deploy a database"
    4. Selecciona el plan gratuito ("M0 Sandbox")
    5. Elige un proveedor (AWS, Google Cloud o Azure)
    6. Selecciona la región más cercana (para España: `eu-west-1`)
    7. Dale un nombre al cluster (ej: `TaskManagerCluster`)
    8. Click en "Create Cluster" (tardará 1-3 minutos)

#### Paso 1.2: Configurar Acceso de Red
    1. En el menú lateral, ve a "Network Access"
    2. Click en "Add IP Address"
    3. Selecciona "Allow Access from Anywhere" → añade `0.0.0.0/0`
    4. Click en "Confirm"

#### Paso 1.4: Obtener Cadena de Conexión
    1. Vuelve a "Database" en el menú lateral
    2. En tu cluster, click en "Connect"
    3. Selecciona "Drivers"
    4. Elige Java como driver
    5. Copia la cadena de conexión (se verá así):


## ▶️ Ejecución del Programa
### ◆ Método 1: Desde la Terminal
    mvn spring-boot:run
(Asegúrate de estar en el directorio del proyecto)

### ◆ Método 2: Desde un IDE
#### IntelliJ IDEA:
    1. Abre el proyecto
    2. Busca la clase/archivo "TaskmanagerApiApplication.java" (o "TaskmanagerPiApplication.java")
    3. Click derecho > "Run" 'TaskmanagerApiApplication'

#### Eclipse:
    1. Abre el proyecto
    2. Click derecho en "TaskmanagerApiApplication.java"
    3. Run As > Spring Boot App

#### VS Code:
    1. Abre el proyecto
    2. Abre "TaskmanagerApiApplication.java"
    3. Click en "Run" sobre el método "main()"

### ◆ Método 3: Ejecutable JAR
#### Compilar el proyecto:
    mvn clean package

#### Ejecutar el JAR generado:
    java -jar target/taskmanager-api-0.0.1-SNAPSHOT.jar

### Después de Ejecutar
#### Descripción de Paquetes:
| Paquete      | Descripción                                              |
|--------------|----------------------------------------------------------|
| `controller` | Controladores REST que exponen los endpoints de la API   |
| `dto`        | Data Transfer Objects para comunicación cliente-servidor |
| `exception`  | Excepciones personalizadas y manejador global de errores |
| `model`      | Entidades del dominio (Task, Priority)                   |
| `repository` | Interfaces de acceso a datos con Spring Data MongoDB     |
| `service`    | Lógica de negocio de la aplicación                       |

#### Resumen de Endpoints:
| Método | Endpoint                                             | Descripción                |
|--------|------------------------------------------------------|----------------------------|
| POST   | `/api/tasks`                                         | Crear nueva tarea          |
| GET    | `/api/tasks`                                         | Obtener todas las tareas   |
| GET    | `/api/tasks/{id}`                                    | Obtener tarea por ID       |
| PUT    | `/api/tasks/{id}`                                    | Actualizar tarea completa  |
| DELETE | `/api/tasks/{id}`                                    | Eliminar tarea             |
| PATCH  | `/api/tasks/{id}/complete`                           | Marcar como completada     |
| PATCH  | `/api/tasks/{id}/incomplete`                         | Marcar como no completada  |
| GET    | `/api/tasks/completed`                               | Obtener tareas completadas |
| GET    | `/api/tasks/pending`                                 | Obtener tareas pendientes  |
| GET    | `/api/tasks/category/{category}`                     | Buscar por categoría       |
| GET    | `/api/tasks/tag/{tag}`                               | Buscar por etiqueta        |
| GET    | `/api/tasks/priority/{priority}`                     | Buscar por prioridad       |
| GET    | `/api/tasks/search?title={title}`                    | Buscar por título          |
| GET    | `/api/tasks/overdue`                                 | Obtener tareas vencidas    |
| GET    | `/api/tasks/statistics`                              | Estadísticas generales     |
| GET    | `/api/tasks/paginated`                               | Lista paginada y ordenada  |
| GET    | `/api/tasks/pending/priority/{priority}`             | Pendientes por prioridad   |
| GET    | `/api/tasks/completed/category/{category}`           | Completadas por categoría  |
| GET    | `/api/tasks/priority/{priority}/category/{category}` | Por prioridad y categoría  |

## 📂 Archivo Ejecución del Programa (Modo Manual de Usuario 📖)
🔵 Endpoints CRUD Básicos
### Crear una tarea
    POST `/api/tasks`
        Content-Type: application/json
#### Body (JSON):
    {
        "title": "Comprar víveres",
        "description": "Ir al supermercado y comprar frutas, verduras y lácteos",
        "completed": false,
        "priority": "MEDIA",
        "tags": ["casa", "urgente"],
        "category": "Personal",
        "dueDate": "2025-12-25T18:00:00"
    }
#### Respuesta (201 Created):
    {
        "id": "67890abc123...",
        "title": "Comprar víveres",
        "description": "Ir al supermercado y comprar frutas, verduras y lácteos",
        "completed": false,
        "priority": "MEDIA",
        "tags": ["casa", "urgente"],
        "category": "Personal",
        "createdAt": "2025-12-20T10:30:00",
        "updatedAt": "2025-12-20T10:30:00",
        "dueDate": "2025-12-25T18:00:00"
    }

### Obtener todas las tareas
    GET /api/tasks
        Content-Type: application/json
#### Respuesta (200 OK):
    [
        {
            "id": "67890abc123...",
            "title": "Comprar víveres",
            "completed": false,
            ...
        },
        {
            "id": "12345def456...",
            "title": "Estudiar para examen",
            "completed": true,
            ...
        }
    ]

### Obtener una tarea por ID
    GET `/api/tasks/{id}`
        Content-Type: application/json
    URL de Ejemplo: `GET /api/tasks/67890abc123`
#### Respuesta (200 OK):
    Objeto Task completo

### Actualizar una tarea
    PUT `/api/tasks/{id}`
        Content-Type: application/json
#### Body (JSON):
    {
        "title": "Comprar víveres ACTUALIZADO",
        "description": "Añadir también pan y leche",
        "completed": false,
        "priority": "ALTA",
        "tags": ["casa", "urgente"],
        "category": "Personal",
        "dueDate": "2025-12-24T18:00:00"
    }
#### Respuesta (200 OK):
    Objeto Tarea actualizada

### Eliminar una tarea
    DELETE /api/tasks/{id}
        Content-Type: application/json
#### Respuesta (204 No Content)

🟡 Endpoints de Estado
### Marcar como completada
    PATCH /api/tasks/{id}/complete
        Content-Type: application/json
#### Respuesta (200 OK):
    Tarea con completed: true

### Marcar como no completada
    PATCH /api/tasks/{id}/incomplete
        Content-Type: application/json
#### Respuesta (200 OK):
    Tarea con completed: false

🟢 Endpoints de Búsqueda
### Tareas completadas
    GET /api/tasks/completed
        Content-Type: application/json

### Tareas pendientes
    GET /api/tasks/pending
        Content-Type: application/json

### Buscar por categoría
    GET /api/tasks/category/{category}
        Content-Type: application/json
    URL de Ejemplo: `GET /api/tasks/category/Personal`

### Buscar por etiqueta
    GET /api/tasks/tag/{tag}
        Content-Type: application/json
    URL de Ejemplo: `GET /api/tasks/tag/urgente`

### Buscar por prioridad
    GET /api/tasks/priority/{priority}
        Content-Type: application/json
    Valores válidos: "BAJA", "MEDIA", "ALTA"
    URL de Ejemplo: `GET /api/tasks/priority/ALTA`

### Buscar por título
    GET /api/tasks/search?title={texto}
        Content-Type: application/json
    URL de Ejemplo: `GET /api/tasks/search?title=comprar`

### Tareas vencidas
    GET /api/tasks/overdue
        Content-Type: application/json

🟡 Endpoints Avanzados
### Estadísticas
    GET /api/tasks/statistics
        Content-Type: application/json
#### Respuesta (200 OK):
    {
        "totalTasks": 25,
        "completedTasks": 15,
        "pendingTasks": 10,
        "overdueTasks": 3,
        "tasksByPriority": {
            "ALTA": 8,
            "MEDIA": 12,
            "BAJA": 5
        },
        "tasksByCategory": {
            "Trabajo": 10,
            "Personal": 8,
            "Estudios": 7
        },
        "completionPercentage": 60.0
    }

### Paginación y ordenación
    GET /api/tasks/paginated?page=0&size=10&sortBy=title&sortDirection=asc
        Content-Type: application/json
#### Parámetros de Query:
| Parámetro     | Tipo   | Default   | Default                         |
|---------------|--------|-----------|---------------------------------|
| page          | int    | 0         | Número de página (empieza en 0) |
| size          | int    | 10        | Elementos por página            |
| sortBy        | String | createdAt | Campo de ordenación             |
| sortDirection | String | desc      | Dirección (asc o desc)          |
#### Campos ordenables:
* title
* createdAt
* updatedAt
* priority
* dueDate
* category

#### Respuesta (200 OK):
    {
        "content": [ /* array de tareas */ ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 10,
            "sort": { "sorted": true }
        },
        "totalElements": 25,
        "totalPages": 3,
        "last": false,
        "first": true,
        "numberOfElements": 10
    }

### Filtros combinados
#### Tareas pendientes por prioridad:
    GET /api/tasks/pending/priority/ALTA
        Content-Type: application/json

#### Tareas completadas por categoría:
    GET /api/tasks/completed/category/Trabajo
        Content-Type: application/json

#### Tareas por prioridad y categoría:
    GET /api/tasks/priority/ALTA/category/Trabajo
        Content-Type: application/json_


## 📚 Documentación de la API
### URL Base
    🌐 http://localhost:8080/api/tasks


## 🎨 Modelo de Datos
### Task (Tarea)
| Campo       | Tipo          | Descripción                 | Validación                      |
|-------------|---------------|-----------------------------|---------------------------------|
| id          | String        | ID único (MongoDB)          | Auto-generado                   |
| title       | String        | Título de la tarea          | Obligatorio, max 100 caracteres |
| description | String        | Descripción detallada       | Opcional, max 500 caracteres    |
| completed   | Boolean       | Estado de completado        | Obligatorio, default: false     |
| priority    | Priority      | Prioridad (BAJA/MEDIA/ALTA) | Obligatorio                     |
| tags        | List<String>  | Lista de etiquetas          | Opcional                        |
| category    | String        | Categoría de la tarea       | Opcional                        |
| createdAt   | LocalDateTime | Fecha de creación           | Auto-generado                   |
| updatedAt   | LocalDateTime | Fecha de actualización      | Auto-actualizado                |
| dueDate     | LocalDateTime | Fecha de vencimiento        | Opcional                        |


## 🧪 Pruebas
### Colección de Postman:
Importa la colección `Task_Manager_API.postman_collection.json` en Postman para probar todos los endpoints.

### Datos de prueba:
El proyecto incluye ejemplos de tareas en la sección de [Ejemplos de Uso](#ejemplos-de-uso).


## 🐛 Manejo de Errores
La API devuelve respuestas de error estructuradas:
### Error 404 - Tarea no encontrada:
    {
        "timestamp": "2025-12-20T10:30:00",
        "status": 404,
        "error": "Not Found",
        "message": "No se encontró la tarea con ID: 67890abc123",
        "path": "/api/tasks/67890abc123"
    }

### Error 400 - Validación fallida:
    {
        "timestamp": "2025-12-20T10:30:00",
        "status": 400,
        "error": "Bad Request",
        "message": "Error de validación",
        "errors": {
            "title": "El título es obligatorio",
            "priority": "La prioridad es obligatoria"
        },
        "path": "/api/tasks"
    }


## 🧩 Arquitectura del Sistema (en capas)
    ┌────────────────────────────────────────────────────────────────┐
    │                        CLIENTE                                 │
    │              (Postman, Navegador, App Móvil)                   │
    └───────────────────────────┬────────────────────────────────────┘
                                │ HTTP/REST
                                ▼
    ┌────────────────────────────────────────────────────────────────┐
    │                   CAPA DE PRESENTACIÓN                         │
    │                    (Controller Layer)                          │
    │  • TaskController.java                                         │
    │  • Endpoints REST                                              │
    │  • Validación de entrada                                       │
    │  • Serialización JSON                                          │
    └───────────────────────────┬────────────────────────────────────┘
                                │
                                ▼
    ┌────────────────────────────────────────────────────────────────┐
    │                    CAPA DE NEGOCIO                             │
    │                    (Service Layer)                             │
    │  • TaskService (interfaz)                                      │
    │  • TaskServiceImpl                                             │
    │  • Lógica de negocio                                           │
    │  • Validaciones complejas                                      │
    │  • Gestión de fechas                                           │
    └───────────────────────────┬────────────────────────────────────┘
                                │
                                ▼
    ┌────────────────────────────────────────────────────────────────┐
    │                  CAPA DE PERSISTENCIA                          │
    │                   (Repository Layer)                           │
    │  • TaskRepository                                              │
    │  • Spring Data MongoDB                                         │
    │  • Queries personalizadas                                      │
    └───────────────────────────┬────────────────────────────────────┘
                                │
                                ▼
    ┌────────────────────────────────────────────────────────────────┐
    │                    BASE DE DATOS                               │
    │                    MongoDB Atlas                               │
    │  • Colección: tasks                                            │
    │  • Documentos JSON                                             │
    └────────────────────────────────────────────────────────────────┘

### Componentes Transversales:
    ┌────────────────────────────────────────────────────────────────┐
    │                 COMPONENTES TRANSVERSALES                      │
    ├────────────────────────────────────────────────────────────────┤
    │  • GlobalExceptionHandler (Manejo de errores)                  │
    │  • DTOs (TaskRequestDTO, TaskResponseDTO, TaskStatisticsDTO)   │
    │  • TaskMapper (Conversión entidad ↔ DTO)                       │
    │  • Validaciones (Jakarta Validation)                           │
    └────────────────────────────────────────────────────────────────┘

### Componentes Transversales:
#### Caso 1: Crear una tarea
    1. Cliente → POST /api/tasks + JSON
    2. TaskController.createTask(TaskRequestDTO)
    3. @Valid → Validación de campos
    4. TaskMapper.toEntity(TaskRequestDTO) → Task
    5. TaskService.createTask(Task)
    6. TaskRepository.save(Task) → MongoDB
    7. Task (con ID generado) ← MongoDB
    8. TaskMapper.toResponseDTO(Task) → TaskResponseDTO
    9. Cliente ← 201 Created + JSON

#### Caso 2: Obtener estadísticas
    1. Cliente → GET /api/tasks/statistics
    2. TaskController.getStatistics()
    3. TaskService.getStatistics()
    4. TaskRepository.findAll() → MongoDB
    5. Stream + GroupBy + Counting
    6. TaskStatisticsDTO (con datos calculados)
    7. Cliente ← 200 OK + JSON con estadísticas

#### Caso 3: Buscar con filtros combinados
    1. Cliente → GET /api/tasks/pending/priority/ALTA
    2. TaskController.getPendingTasksByPriority(Priority.ALTA)
    3. TaskService.getPendingTasksByPriority(ALTA)
    4. TaskRepository.findByCompletedAndPriority(false, ALTA) → MongoDB
    5. List<Task> (filtradas)
    6. Stream.map(TaskMapper::toResponseDTO)
    7. Cliente ← 200 OK


## 🚀 Mejoras Futuras

### Funcionalidades Planificadas

- 📧 **Envío de notificaciones por email** - Recordatorios de tareas vencidas
- 📊 **Gráficos estadísticos** - Visualización de productividad con "Chart.js"
- 💾 **Exportación a "CSV"/"Excel"** - Descargar informes de tareas
- 🔐 **Autenticación y autorización** - Spring Security + JWT
- 👥 **Sistema multiusuario** - Gestión de tareas por usuario
- 🔔 **Notificaciones push** - Alertas en tiempo real
- 📱 **API para aplicación móvil** - Endpoints optimizados
- 🌐 **Internacionalización (i18n)** - Soporte multiidioma
- 📈 **Dashboard con métricas** - Panel de control visual
- 🔍 **Búsqueda full-text** - Búsqueda avanzada con "MDBTS" (MongoDB Text Search)
- 📅 **Tareas recurrentes** - Repetición automática de tareas
- 🏷️ **Gestión avanzada de etiquetas** - CRUD de etiquetas personalizadas
- 🎨 **Temas y personalización** - Colores por categoría
- 🔄 **Sincronización con Google Calendar** - Integración con calendarios
- 🧪 **Tests automatizados** - JUnit 5 + Mockito
- 📖 **Documentación con Swagger/OpenAPI** - API docs interactiva
- 🐳 **Dockerización** - Contenedores Docker para deployment
- ☁️ **CI/CD** - Pipeline de integración y despliegue continuo


## 👨🏻‍💻 Desarrollo y Mantenimiento
### Autor
**👤 Rodrigo López Pérez**
- 🎓 Estudiante de DAM (Desarrollo de Aplicaciones Multiplataforma)
- 📆 Fecha de Creación: Diciembre 2025
- 👀 Versión Actual: 1.0.0

### Contacto
- 📧 **Email:** rodrigo.lop.per@gmail.com
- 🐙 **GitHub:** [@Rodrigo-LPz](https://github.com/Rodrigo-LPz)
- 📂 **Repositorio:** [taskmanager-api](https://github.com/Rodrigo-LPz/taskmanager-api.git)


## 📜 Licencia
### Licencia MIT
Este proyecto está bajo la **Licencia MIT**.

### Términos de Uso
* Acciones aprobadas - Permitido: ✅
  * Uso personal y educativo.
  * Modificación del código fuente.
  * Distribución con atribución adecuada.
  * Uso comercial con licencia MIT.

* Acciones terminantemente prohibidas - No permitido: ❌
  * Eliminación de avisos de copyright
  * Representación falsa de autoría.
  * Uso del nombre/marca sin autorización.
  * Distribución sin incluir la licencia.


## 🙏 Agradecimientos
¡Gracias por usar Gestor de Tareas - Task Manager API - con Spring Boot y MongoDB! 🕵🏻‍♂️📄

Si encuentras algún problema o tienes sugerencias, no dudes en abrir un issue en GitHub. 👨🏻‍💻


================================================================================

                    © 2025 Rodrigo López Pérez
                    Todos los derechos reservados
                    
                    Task Manager API | MIT License

================================================================================