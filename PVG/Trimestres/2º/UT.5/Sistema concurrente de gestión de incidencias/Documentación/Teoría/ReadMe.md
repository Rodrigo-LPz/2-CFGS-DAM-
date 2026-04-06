# 🧩 Estructura del programa
### Modelo clásico: Productor (cliente) – Consumidor (servidor)
    Productores → Cola segura → Consumidores

<hr><br><br><br>

# 🧠 Conceptos teóricos demostrando
### Concurrencia
    Executors.newFixedThreadPool(6)
###### Crea 6 hilos trabajando a la vez. Varios productores y consumidores ejecutándose simultáneamente.

<br>

### Sincronización
    Executors.newFixedThreadPool(6)
###### Crea 6 hilos trabajando a la vez. Varios productores y consumidores ejecutándose simultáneamente.

<br>

### Concurrencia
    LinkedBlockingQueue()
###### Cola encargada de:
1. Bloquear automáticamente.
2. Evitar accesos simultáneos peligrosos.
3. Controlar la espera de ejecución sobre los métodos.
###### (No se necesita declarar un "synchronized" manual ya que el uso de hilos es seguro, "thread-safe".)

<br>

### Seguridad
    AtomicInteger
###### Ayuda a evitar:
1. Dos hilos usando el mismo identificador, ID.
2. Corrupción, pérdida y maltrato de datos.
###### (No se necesita declarar un "synchronized" manual ya que el uso de hilos es seguro, "thread-safe".)

<br>

● Métodos bloqueantes:

    put()
    take()
###### Ayuda a evitar:
1. Lecturas vacías.
2. Escrituras conflictivas.
3. Condición de carrera: "Race conditions"
###### (No se necesita declarar un "synchronized" manual ya que el uso de hilos es seguro, "thread-safe".)

<br>

### Modelo cliente-servidor
| Parte        | Representa |
|--------------|------------|
| Productores  | Clientes   |
| Cola         | Canal      |
| Consumidores | Servidor   |
