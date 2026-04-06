# api.py
"""
Definición de todos los endpoints de la API REST.
Aquí se validan los datos y se controlan los errores.
"""

from fastapi import APIRouter, HTTPException, Query
from pydantic import BaseModel, Field, EmailStr
from typing import Optional, List
from datetime import datetime

# Importar todas las funciones de base de datos
from db import (
    # Empleados
    db_list_employees,
    db_get_employee,
    db_get_employee_by_code,
    db_create_employee,
    db_verify_employee,
    db_delete_employee,
    # Fichajes
    db_create_punch,
    db_list_punches,
    # Productos
    db_list_products,
    db_get_product,
    db_get_product_by_sku,
    db_create_product,
    # Movimientos de stock
    db_create_stock_movement,
    db_list_stock_movements,
    # Estadísticas
    db_get_stats
)

# Crea el router.
router = APIRouter()


# ========================================
# MODELOS DE DATOS (Pydantic)
# ========================================

# --- EMPLEADOS ---

class EmployeeCreate(BaseModel):
    employee_code: str = Field(..., min_length=3, max_length=20, description="Código único del empleado")
    name: str = Field(..., min_length=2, max_length=100)
    email: EmailStr
    pin: str = Field(..., min_length=4, max_length=6, description="PIN numérico de 4-6 dígitos")
    role: str = Field(default="employee", description="Rol: employee, manager, admin")

class EmployeeLogin(BaseModel):
    employee_code: str = Field(..., description="Código del empleado")
    pin: str = Field(..., description="PIN del empleado")

class EmployeeOut(BaseModel):
    id: int
    employee_code: str
    name: str
    email: str
    role: str
    active: int
    created_at: int

# --- FICHAJES ---

class PunchCreate(BaseModel):
    employee_code: str = Field(..., description="Código del empleado")
    pin: str = Field(..., description="PIN para verificar identidad")
    punch_type: str = Field(..., description="Tipo: IN o OUT")
    location: Optional[str] = Field(None, description="Ubicación GPS (opcional)")
    notes: Optional[str] = Field(None, description="Notas adicionales")

class PunchOut(BaseModel):
    id: int
    employee_id: int
    employee_name: str
    employee_code: str
    punch_type: str
    timestamp: int
    location: Optional[str]
    notes: Optional[str]

# --- PRODUCTOS ---

class ProductCreate(BaseModel):
    sku: str = Field(..., min_length=3, max_length=50, description="Código SKU único")
    name: str = Field(..., min_length=2, max_length=200)
    description: Optional[str] = None
    category: str = Field(..., description="Categoría del producto")
    price: float = Field(..., ge=0, description="Precio debe ser mayor o igual a 0")
    stock: int = Field(default=0, ge=0, description="Stock inicial")
    min_stock: int = Field(default=0, ge=0, description="Stock mínimo para alertas")

class ProductOut(BaseModel):
    id: int
    sku: str
    name: str
    description: Optional[str]
    category: str
    price: float
    stock: int
    min_stock: int
    active: int
    created_at: int
    updated_at: int

# --- MOVIMIENTOS DE STOCK ---

class StockMovementCreate(BaseModel):
    product_sku: str = Field(..., description="SKU del producto")
    employee_code: str = Field(..., description="Código del empleado que realiza el movimiento")
    pin: str = Field(..., description="PIN para verificar identidad")
    movement_type: str = Field(..., description="Tipo: IN (entrada) o OUT (salida)")
    quantity: int = Field(..., gt=0, description="Cantidad debe ser mayor que 0")
    reason: str = Field(..., description="Motivo: purchase, sale, adjustment, return, etc.")
    notes: Optional[str] = None

class StockMovementOut(BaseModel):
    id: int
    product_id: int
    product_name: str
    sku: str
    employee_id: int
    employee_name: str
    movement_type: str
    quantity: int
    reason: str
    notes: Optional[str]
    timestamp: int


# ========================================
# ENDPOINTS - EMPLEADOS
# ========================================

@router.post("/auth/login", tags=["Autenticación"])
def login(credentials: EmployeeLogin):
    """
    Verifica las credenciales de un empleado.
    Retorna los datos del empleado si son correctos.
    """
    employee = db_verify_employee(credentials.employee_code, credentials.pin)
    
    if not employee:
        raise HTTPException(
            status_code=401,
            detail="Código de empleado o PIN incorrecto"
        )
    
    # No se devuelve el PIN en la respuesta.
    employee.pop("pin", None)
    
    return {
        "message": "Login exitoso",
        "employee": employee
    }


@router.get("/employees", response_model=List[EmployeeOut], tags=["Empleados"])
def list_employees(active_only: bool = Query(default=True, description="Mostrar solo empleados activos")):
    """Lista todos los empleados del sistema."""
    employees = db_list_employees(active_only=active_only)
    
    # Elimina el PIN de las respuestas por seguridad.
    for emp in employees:
        emp.pop("pin", None)
    
    return employees


@router.get("/employees/{employee_id}", response_model=EmployeeOut, tags=["Empleados"])
def get_employee(employee_id: int):
    """Obtiene los datos de un empleado específico."""
    employee = db_get_employee(employee_id)
    
    if not employee:
        raise HTTPException(status_code=404, detail="Empleado no encontrado")
    
    # No se devuelve el PIN.
    employee.pop("pin", None)
    
    return employee


@router.post("/employees", response_model=EmployeeOut, status_code=201, tags=["Empleados"])
def create_employee(employee_data: EmployeeCreate):
    """
    Crea un nuevo empleado en el sistema.
    Requiere código único, nombre, email y PIN.
    """
    # Valida que el rol sea válido.
    valid_roles = ["employee", "manager", "admin"]
    if employee_data.role not in valid_roles:
        raise HTTPException(
            status_code=400,
            detail=f"Rol inválido. Roles permitidos: {', '.join(valid_roles)}"
        )
    
    # Valida que el PIN sea numérico.
    if not employee_data.pin.isdigit():
        raise HTTPException(status_code=400, detail="El PIN debe contener solo números")
    
    try:
        employee = db_create_employee(
            employee_code=employee_data.employee_code,
            name=employee_data.name,
            email=employee_data.email,
            pin=employee_data.pin,
            role=employee_data.role
        )
        
        # No se devuelve el PIN.
        employee.pop("pin", None)
        
        return employee
        
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))


@router.delete("/employees/{employee_id}", tags=["Empleados"])
def delete_employee(employee_id: int):
    """
    Elimina (desactiva) un empleado del sistema.
    No se borra físicamente, solo se marca como inactivo.
    """
    employee = db_get_employee(employee_id)
    
    if not employee:
        raise HTTPException(status_code=404, detail="Empleado no encontrado")
    
    db_delete_employee(employee_id)
    
    return {
        "message": f"Empleado {employee['name']} desactivado correctamente",
        "employee_id": employee_id
    }


# ========================================
# ENDPOINTS - FICHAJES
# ========================================

@router.post("/punches", response_model=PunchOut, status_code=201, tags=["Fichajes"])
def create_punch(punch_data: PunchCreate):
    """
    Registra un fichaje (entrada o salida).
    Verifica primero las credenciales del empleado.
    """
    # Valida el tipo de fichaje.
    if punch_data.punch_type not in ["IN", "OUT"]:
        raise HTTPException(
            status_code=400,
            detail="Tipo de fichaje inválido. Use 'IN' para entrada o 'OUT' para salida"
        )
    
    # Verifica/Comprueba las credenciales.
    employee = db_verify_employee(punch_data.employee_code, punch_data.pin)
    
    if not employee:
        raise HTTPException(
            status_code=401,
            detail="Código de empleado o PIN incorrecto"
        )
    
    try:
        # Crea el fichaje.
        punch = db_create_punch(
            employee_id=employee["id"],
            punch_type=punch_data.punch_type,
            location=punch_data.location,
            notes=punch_data.notes
        )
        
        # Añade información del empleado a la respuesta.
        punch["employee_name"] = employee["name"]
        punch["employee_code"] = employee["employee_code"]
        
        return punch
        
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))


@router.get("/punches", response_model=List[PunchOut], tags=["Fichajes"])
def list_punches(
    employee_id: Optional[int] = Query(None, description="Filtrar por ID de empleado"),
    limit: int = Query(default=100, ge=1, le=500, description="Cantidad máxima de resultados")
):
    """
    Lista los fichajes registrados.
    Puede filtrar por empleado y limitar resultados.
    """
    punches = db_list_punches(employee_id=employee_id, limit=limit)
    return punches


@router.get("/punches/employee/{employee_code}", response_model=List[PunchOut], tags=["Fichajes"])
def list_punches_by_code(
    employee_code: str,
    limit: int = Query(default=50, ge=1, le=500)
):
    """Lista los fichajes de un empleado específico usando su código."""
    employee = db_get_employee_by_code(employee_code)
    
    if not employee:
        raise HTTPException(status_code=404, detail="Empleado no encontrado")
    
    punches = db_list_punches(employee_id=employee["id"], limit=limit)
    return punches


# ========================================
# ENDPOINTS - PRODUCTOS
# ========================================

@router.get("/products", response_model=List[ProductOut], tags=["Productos"])
def list_products(
    category: Optional[str] = Query(None, description="Filtrar por categoría"),
    active_only: bool = Query(default=True, description="Mostrar solo productos activos")
):
    """Lista todos los productos del inventario."""
    products = db_list_products(category=category, active_only=active_only)
    return products


@router.get("/products/{product_id}", response_model=ProductOut, tags=["Productos"])
def get_product(product_id: int):
    """Obtiene los detalles de un producto específico."""
    product = db_get_product(product_id)
    
    if not product:
        raise HTTPException(status_code=404, detail="Producto no encontrado")
    
    return product


@router.get("/products/sku/{sku}", response_model=ProductOut, tags=["Productos"])
def get_product_by_sku(sku: str):
    """Obtiene un producto usando su código SKU."""
    product = db_get_product_by_sku(sku)
    
    if not product:
        raise HTTPException(status_code=404, detail=f"Producto con SKU '{sku}' no encontrado")
    
    return product


@router.post("/products", response_model=ProductOut, status_code=201, tags=["Productos"])
def create_product(product_data: ProductCreate):
    """
    Crea un nuevo producto en el inventario.
    Requiere SKU único, nombre, categoría y precio.
    """
    try:
        product = db_create_product(
            sku=product_data.sku,
            name=product_data.name,
            category=product_data.category,
            price=product_data.price,
            stock=product_data.stock,
            description=product_data.description,
            min_stock=product_data.min_stock
        )
        
        return product
        
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))


# ========================================
# ENDPOINTS - MOVIMIENTOS DE STOCK
# ========================================

@router.post("/stock-movements", response_model=StockMovementOut, status_code=201, tags=["Inventario"])
def create_stock_movement(movement_data: StockMovementCreate):
    """
    Registra un movimiento de stock (entrada o salida).
    Actualiza automáticamente el stock del producto.
    Requiere autenticación del empleado.
    """
    # Valida el tipo de movimiento.
    if movement_data.movement_type not in ["IN", "OUT"]:
        raise HTTPException(
            status_code=400,
            detail="Tipo de movimiento inválido. Use 'IN' para entrada o 'OUT' para salida"
        )
    
    # Verifican/Comprueban las credenciales del empleado.
    employee = db_verify_employee(movement_data.employee_code, movement_data.pin)
    
    if not employee:
        raise HTTPException(
            status_code=401,
            detail="Código de empleado o PIN incorrecto"
        )
    
    # Obtiene el producto por SKU.
    product = db_get_product_by_sku(movement_data.product_sku)
    
    if not product:
        raise HTTPException(
            status_code=404,
            detail=f"Producto con SKU '{movement_data.product_sku}' no encontrado"
        )
    
    try:
        # Crea el movimiento.
        movement = db_create_stock_movement(
            product_id=product["id"],
            employee_id=employee["id"],
            movement_type=movement_data.movement_type,
            quantity=movement_data.quantity,
            reason=movement_data.reason,
            notes=movement_data.notes
        )
        
        # Añade información adicional a la respuesta.
        movement["product_name"] = product["name"]
        movement["sku"] = product["sku"]
        movement["employee_name"] = employee["name"]
        
        return movement
        
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))


@router.get("/stock-movements", response_model=List[StockMovementOut], tags=["Inventario"])
def list_stock_movements(
    product_id: Optional[int] = Query(None, description="Filtrar por ID de producto"),
    employee_id: Optional[int] = Query(None, description="Filtrar por ID de empleado"),
    limit: int = Query(default=100, ge=1, le=500, description="Cantidad máxima de resultados")
):
    """
    Lista los movimientos de stock registrados.
    Puede filtrar por producto, empleado y limitar resultados.
    """
    movements = db_list_stock_movements(
        product_id=product_id,
        employee_id=employee_id,
        limit=limit
    )
    return movements


@router.get("/stock-movements/product/{sku}", response_model=List[StockMovementOut], tags=["Inventario"])
def list_movements_by_sku(
    sku: str,
    limit: int = Query(default=100, ge=1, le=500)
):
    """Lista los movimientos de un producto específico usando su SKU."""
    product = db_get_product_by_sku(sku)
    
    if not product:
        raise HTTPException(status_code=404, detail=f"Producto con SKU '{sku}' no encontrado")
    
    movements = db_list_stock_movements(product_id=product["id"], limit=limit)
    return movements


# ========================================
# ENDPOINTS - ESTADÍSTICAS
# ========================================

@router.get("/stats", tags=["Estadísticas"])
def get_statistics():
    """
    Obtiene estadísticas generales del sistema:
    - Total de empleados activos.
    - Total de productos.
    - Fichajes de hoy.
    - Productos con stock bajo.
    """
    stats = db_get_stats()
    return stats


@router.get("/products/low-stock", response_model=List[ProductOut], tags=["Estadísticas"])
def get_low_stock_products():
    """Lista productos que están por debajo de su stock mínimo."""
    with get_conn() as conn:
        rows = conn.execute("""
            SELECT * FROM products 
            WHERE stock <= min_stock AND active = 1 
            ORDER BY stock ASC
        """).fetchall()
        return [dict(r) for r in rows]


# Importar get_conn solo para este endpoint específico
from db import get_conn