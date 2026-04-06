# 🧪 Guía de Pruebas - API Sistema Empresarial

## 📋 Índice
1. [Preparación](#preparación)
2. [Pruebas de Autenticación](#pruebas-de-autenticación)
3. [Pruebas de Empleados](#pruebas-de-empleados)
4. [Pruebas de Fichajes](#pruebas-de-fichajes)
5. [Pruebas de Productos](#pruebas-de-productos)
6. [Pruebas de Movimientos de Stock](#pruebas-de-movimientos-de-stock)
7. [Pruebas de Estadísticas](#pruebas-de-estadísticas)
8. [Casos de Error](#casos-de-error)

---

## 🚀 Preparación

### Paso 1: Iniciar el servidor
```bash
# En la terminal, dentro de la carpeta del proyecto backend
uvicorn main:app --reload
```

Salida esperada:
```
INFO:     Uvicorn running on http://127.0.0.1:8001 (Press CTRL+C to quit)
INFO:     Started reloader process
🚀 Iniciando Sistema de Gestión Empresarial...
✅ Base de datos inicializada correctamente
📚 Documentación disponible en: http://127.0.0.1:8001/docs
```

### Paso 2: Acceder a la documentación
Navegar a: **http://127.0.0.1:8001/docs**

La interfaz Swagger UI presenta la documentación interactiva de la API con todos los endpoints organizados por categorías.

### Paso 3: Conocer los datos iniciales
La base de datos viene con estos datos de prueba:

**Empleados:**
- `EMP001` - Rodrigo López Pérez (Admin) - PIN: `1234`
- `EMP002` - María García (Manager) - PIN: `5678`
- `EMP003` - Carlos López (Employee) - PIN: `9012`

**Productos:**
- `PROD-001` - Laptop HP 15" (Stock: 15)
- `PROD-002` - Mouse Inalámbrico (Stock: 50)
- `PROD-003` - Teclado Mecánico (Stock: 25)
- `PROD-004` - Monitor 24" (Stock: 10)

---

## 🔐 1. Pruebas de Autenticación

### ✅ TEST 1.1: Login exitoso

**Endpoint:** `POST /auth/login`

**Request Body:**
```json
{
  "employee_code": "EMP001",
  "pin": "1234"
}
```

**Respuesta esperada (200):**
```json
{
  "message": "Login exitoso",
  "employee": {
    "id": 1,
    "employee_code": "EMP001",
    "name": "Rodrigo López Pérez",
    "email": "rodrigo.lopez@empresa",
    "role": "admin",
    "active": 1,
    "created_at": 1234567890
  }
}
```

### ❌ TEST 1.2: Login con PIN incorrecto

**Request Body:**
```json
{
  "employee_code": "EMP001",
  "pin": "0000"
}
```

**Respuesta esperada (401):**
```json
{
  "detail": "Código de empleado o PIN incorrecto"
}
```

### ❌ TEST 1.3: Login con empleado inexistente

**Request Body:**
```json
{
  "employee_code": "EMP999",
  "pin": "1234"
}
```

**Respuesta esperada (401):**
```json
{
  "detail": "Código de empleado o PIN incorrecto"
}
```

---

## 👥 2. Pruebas de Empleados

### ✅ TEST 2.1: Listar todos los empleados activos

**Endpoint:** `GET /employees`

**Parámetros:** `active_only=true` [por defecto solo empleados activos (default: true)]

**Respuesta esperada (200):**
```json
[
  {
    "id": 1,
    "employee_code": "EMP001",
    "name": "Rodrigo López Pérez",
    "email": "rodrigo.lopez@empresa",
    "role": "admin",
    "active": 1,
    "created_at": 1234567890
  },
  {
    "id": 2,
    "employee_code": "EMP002",
    "name": "María García",
    "email": "maria.garcia@empresa.com",
    "role": "manager",
    "active": 1,
    "created_at": 1234567890
  }
]
```

### ✅ TEST 2.2: Obtener empleado por ID

**Endpoint:** `GET /employees/1`

**Respuesta esperada (200):**
```json
{
  "id": 1,
  "employee_code": "EMP001",
  "name": "Rodrigo López Pérez",
  "email": "rodrigo.lopez@empresa",
  "role": "admin",
  "active": 1,
  "created_at": 1234567890
}
```

### ✅ TEST 2.3: Crear nuevo empleado

**Endpoint:** `POST /employees`

**Request Body:**
```json
{
  "employee_code": "EMP004",
  "name": "Ana Martínez",
  "email": "ana.martinez@empresa.com",
  "pin": "4567",
  "role": "employee"
}
```

**Respuesta esperada (201):**
```json
{
  "id": 4,
  "employee_code": "EMP004",
  "name": "Ana Martínez",
  "email": "ana.martinez@empresa.com",
  "role": "employee",
  "active": 1,
  "created_at": 1234567890
}
```

### ❌ TEST 2.4: Crear empleado con código duplicado

**Request Body:**
```json
{
  "employee_code": "EMP001",
  "name": "Otro Empleado",
  "email": "otro@empresa.com",
  "pin": "1111",
  "role": "employee"
}
```

**Respuesta esperada (400):**
```json
{
  "detail": "El código de empleado 'EMP001' ya existe"
}
```

### ✅ TEST 2.5: Eliminar (desactivar) empleado

**Endpoint:** `DELETE /employees/4`

**Respuesta esperada (200):**
```json
{
  "message": "Empleado Ana Martínez desactivado correctamente",
  "employee_id": 4
}
```

---

## ⏰ 3. Pruebas de Fichajes

### ✅ TEST 3.1: Registrar entrada (IN)

**Endpoint:** `POST /punches`

**Request Body:**
```json
{
  "employee_code": "EMP001",
  "pin": "1234",
  "punch_type": "IN",
  "location": "Oficina Central",
  "notes": "Entrada matinal"
}
```

**Respuesta esperada (201):**
```json
{
  "id": 1,
  "employee_id": 1,
  "employee_name": "Rodrigo López Pérez",
  "employee_code": "EMP001",
  "punch_type": "IN",
  "timestamp": 1234567890,
  "location": "Oficina Central",
  "notes": "Entrada matinal"
}
```

### ✅ TEST 3.2: Registrar salida (OUT)

**Request Body:**
```json
{
  "employee_code": "EMP001",
  "pin": "1234",
  "punch_type": "OUT",
  "location": "Oficina Central",
  "notes": "Salida fin de jornada"
}
```

**Respuesta esperada (201):** Similar al anterior con `punch_type: "OUT"`

### ❌ TEST 3.3: Fichar con PIN incorrecto

**Request Body:**
```json
{
  "employee_code": "EMP001",
  "pin": "9999",
  "punch_type": "IN",
  "location": null,
  "notes": null
}
```

**Respuesta esperada (401):**
```json
{
  "detail": "Código de empleado o PIN incorrecto"
}
```

### ✅ TEST 3.4: Listar todos los fichajes

**Endpoint:** `GET /punches`

**Parámetros opcionales:**
- `employee_id` - Filtrar por empleado
- `limit` - Limitar resultados (default: 100)

**Respuesta esperada (200):**
```json
[
  {
    "id": 2,
    "employee_id": 1,
    "employee_name": "Rodrigo López Pérez",
    "employee_code": "EMP001",
    "punch_type": "OUT",
    "timestamp": 1234567890,
    "location": "Oficina Central",
    "notes": "Salida"
  },
  {
    "id": 1,
    "employee_id": 1,
    "employee_name": "Rodrigo López Pérez",
    "employee_code": "EMP001",
    "punch_type": "IN",
    "timestamp": 1234567800,
    "location": "Oficina Central",
    "notes": "Entrada"
  }
]
```

### ✅ TEST 3.5: Listar fichajes de un empleado específico

**Endpoint:** `GET /punches/employee/EMP001`

**Parámetros:** `limit=50` (opcional)

**Respuesta esperada (200):** Lista de fichajes solo de EMP001

---

## 📦 4. Pruebas de Productos

### ✅ TEST 4.1: Listar todos los productos

**Endpoint:** `GET /products`

**Parámetros opcionales:**
- `category` - Filtrar por categoría
- `active_only` - Solo activos (default: true)

**Respuesta esperada (200):**
```json
[
  {
    "id": 1,
    "sku": "PROD-001",
    "name": "Laptop HP 15\"",
    "description": "Laptop para oficina",
    "category": "Electrónica",
    "price": 599.99,
    "stock": 15,
    "min_stock": 5,
    "active": 1,
    "created_at": 1234567890,
    "updated_at": 1234567890
  }
]
```

### ✅ TEST 4.2: Obtener producto por SKU

**Endpoint:** `GET /products/sku/PROD-001`

**Respuesta esperada (200):** Objeto del producto

### ✅ TEST 4.3: Crear nuevo producto

**Endpoint:** `POST /products`

**Request Body:**
```json
{
  "sku": "PROD-005",
  "name": "Webcam HD",
  "description": "Webcam 1080p para videoconferencias",
  "category": "Accesorios",
  "price": 49.99,
  "stock": 20,
  "min_stock": 5
}
```

**Respuesta esperada (201):**
```json
{
  "id": 5,
  "sku": "PROD-005",
  "name": "Webcam HD",
  "description": "Webcam 1080p para videoconferencias",
  "category": "Accesorios",
  "price": 49.99,
  "stock": 20,
  "min_stock": 5,
  "active": 1,
  "created_at": 1234567890,
  "updated_at": 1234567890
}
```

### ❌ TEST 4.4: Crear producto con SKU duplicado

**Request Body:**
```json
{
  "sku": "PROD-001",
  "name": "Producto Duplicado",
  "category": "Test",
  "price": 10.0
}
```

**Respuesta esperada (400):**
```json
{
  "detail": "El SKU 'PROD-001' ya existe"
}
```

### ✅ TEST 4.5: Filtrar productos por categoría

**Endpoint:** `GET /products?category=Electrónica`

**Respuesta esperada (200):** Solo productos de categoría "Electrónica"

---

## 📊 5. Pruebas de Movimientos de Stock

### ✅ TEST 5.1: Registrar entrada de stock (IN)

**Endpoint:** `POST /stock-movements`

**Request Body:**
```json
{
  "product_sku": "PROD-001",
  "employee_code": "EMP001",
  "pin": "1234",
  "movement_type": "IN",
  "quantity": 10,
  "reason": "purchase",
  "notes": "Compra de reposición"
}
```

**Respuesta esperada (201):**
```json
{
  "id": 1,
  "product_id": 1,
  "product_name": "Laptop HP 15\"",
  "sku": "PROD-001",
  "employee_id": 1,
  "employee_name": "Rodrigo López Pérez",
  "movement_type": "IN",
  "quantity": 10,
  "reason": "purchase",
  "notes": "Compra de reposición",
  "timestamp": 1234567890
}
```

**Nota:** El stock del producto PROD-001 debe incrementarse de 15 a 25.

### ✅ TEST 5.2: Registrar salida de stock (OUT)

**Request Body:**
```json
{
  "product_sku": "PROD-002",
  "employee_code": "EMP002",
  "pin": "5678",
  "movement_type": "OUT",
  "quantity": 5,
  "reason": "sale",
  "notes": "Venta a cliente corporativo"
}
```

**Respuesta esperada (201):** Similar al anterior

**Nota:** El stock del producto PROD-002 debe decrementarse de 50 a 45.

### ❌ TEST 5.3: Intentar salida con stock insuficiente

**Request Body:**
```json
{
  "product_sku": "PROD-004",
  "employee_code": "EMP001",
  "pin": "1234",
  "movement_type": "OUT",
  "quantity": 100,
  "reason": "sale",
  "notes": null
}
```

**Respuesta esperada (400):**
```json
{
  "detail": "Stock insuficiente. Stock actual: 10, cantidad solicitada: 100"
}
```

### ❌ TEST 5.4: Movimiento con PIN incorrecto

**Request Body:**
```json
{
  "product_sku": "PROD-001",
  "employee_code": "EMP001",
  "pin": "0000",
  "movement_type": "IN",
  "quantity": 5,
  "reason": "adjustment"
}
```

**Respuesta esperada (401):**
```json
{
  "detail": "Código de empleado o PIN incorrecto"
}
```

### ✅ TEST 5.5: Listar todos los movimientos

**Endpoint:** `GET /stock-movements`

**Parámetros opcionales:**
- `product_id` - Filtrar por producto
- `employee_id` - Filtrar por empleado
- `limit` - Limitar resultados

**Respuesta esperada (200):** Lista de movimientos ordenados por fecha descendente

### ✅ TEST 5.6: Listar movimientos de un producto específico

**Endpoint:** `GET /stock-movements/product/PROD-001`

**Respuesta esperada (200):** Solo movimientos del producto PROD-001

---

## 📈 6. Pruebas de Estadísticas

### ✅ TEST 6.1: Obtener estadísticas generales

**Endpoint:** `GET /stats`

**Respuesta esperada (200):**
```json
{
  "total_employees": 3,
  "total_products": 4,
  "punches_today": 2,
  "low_stock_products": 0
}
```

### ✅ TEST 6.2: Listar productos con stock bajo

**Endpoint:** `GET /products/low-stock`

**Respuesta esperada (200):**
```json
[
  {
    "id": 4,
    "sku": "PROD-004",
    "name": "Monitor 24\"",
    "stock": 2,
    "min_stock": 3,
    ...
  }
]
```

---

## ⚠️ 7. Casos de Error Comunes

### ❌ TEST 7.1: Endpoint inexistente

**Endpoint:** `GET /ruta-que-no-existe`

**Respuesta esperada (404):**
```json
{
  "detail": "Not Found"
}
```

### ❌ TEST 7.2: Método HTTP incorrecto

**Endpoint:** `GET /employees` (debería ser POST)

**Request Body:** (cualquiera)

**Respuesta esperada (405):**
```json
{
  "detail": "Method Not Allowed"
}
```

### ❌ TEST 7.3: Datos inválidos (validación Pydantic)

**Endpoint:** `POST /employees`

**Request Body:**
```json
{
  "employee_code": "EM",
  "name": "A",
  "email": "email-invalido",
  "pin": "12",
  "role": "employee"
}
```

**Respuesta esperada (422):** Errores de validación

---

## 🎯 Checklist de Pruebas

### Autenticación ✅
- [ ] Login exitoso
- [ ] Login con PIN incorrecto
- [ ] Login con empleado inexistente

### Empleados ✅
- [ ] Listar empleados
- [ ] Obtener empleado por ID
- [ ] Crear nuevo empleado
- [ ] Intentar crear empleado duplicado
- [ ] Eliminar empleado

### Fichajes ✅
- [ ] Registrar entrada (IN)
- [ ] Registrar salida (OUT)
- [ ] Fichar con PIN incorrecto
- [ ] Listar todos los fichajes
- [ ] Listar fichajes por empleado

### Productos ✅
- [ ] Listar productos
- [ ] Obtener producto por SKU
- [ ] Crear nuevo producto
- [ ] Intentar crear producto duplicado
- [ ] Filtrar por categoría

### Movimientos de Stock ✅
- [ ] Entrada de stock (IN)
- [ ] Salida de stock (OUT)
- [ ] Verificar actualización de stock
- [ ] Intentar salida con stock insuficiente
- [ ] Movimiento con PIN incorrecto
- [ ] Listar movimientos

### Estadísticas ✅
- [ ] Obtener estadísticas generales
- [ ] Productos con stock bajo

---

## 📝 Notas Importantes

1. **Orden de las pruebas:** Algunas pruebas dependen de datos creados en pruebas anteriores
2. **Timestamps:** Los valores de `created_at` y `timestamp` serán diferentes en cada ejecución
3. **IDs:** Los IDs autoincremtentales pueden variar según el orden de las pruebas
4. **Swagger UI:** Las pruebas pueden realizarse directamente desde http://127.0.0.1:8001/docs
5. **Postman:** Las peticiones pueden importarse o crearse manualmente en Postman

---

## 🔧 Troubleshooting

### El servidor no arranca
```bash
# Verificar/Comprobar que las dependencias están instaladas
pip install -r requirements.txt

# Verificar/Comprobar que no hay otro proceso usando el puerto 8000
lsof -i :8000  # En Mac/Linux
netstat -ano | findstr :8000  # En Windows
```

### Error 500 en algún endpoint
- Revisa la terminal donde corre el servidor para ver el error completo
- Verifica que la base de datos se creó correctamente
- Comprueba que los datos de prueba se insertaron

### No veo los datos de ejemplo
- Elimina el archivo `empresa.db`
- Reinicia el servidor
- La función `seed_data()` insertará los datos automáticamente

---

✅ **¡Pruebas completadas!** Una vez completadas todas las pruebas y verificado el funcionamiento correcto de todos los endpoints, la API está lista para ser utilizada por aplicaciones cliente.