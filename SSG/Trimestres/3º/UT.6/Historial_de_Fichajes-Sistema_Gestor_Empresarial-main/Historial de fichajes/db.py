# db.py
"""
Gestión de la base de datos SQLite.
Contiene todas las funciones de acceso a datos.
"""

import sqlite3
from contextlib import contextmanager
from typing import Optional, List, Dict, Any
from datetime import datetime

# Ruta del archivo de base de datos
DB_PATH = "empresa.db"

# ========================================
# GESTIÓN DE CONEXIONES
# ========================================

@contextmanager
def get_conn():
    """
    Context manager para gestionar conexiones a la BD.
    Abre la conexión, ejecuta operaciones y cierra automáticamente.
    """
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row  # Permite acceso a columnas por nombre.
    try:
        yield conn
        conn.commit()  # Confirma los cambios
    except Exception as e:
        conn.rollback()  # Revierte los cambios si hay error.
        raise e
    finally:
        conn.close()


# ========================================
# INICIALIZACIÓN
# ========================================

def init_db():
    """Crea todas las tablas si no existen."""
    with get_conn() as conn:
        # Tabla de empleados
        conn.execute("""
            CREATE TABLE IF NOT EXISTS employees (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                employee_code TEXT UNIQUE NOT NULL,
                name TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                pin TEXT NOT NULL,
                role TEXT NOT NULL DEFAULT 'employee',
                active INTEGER DEFAULT 1,
                created_at INTEGER NOT NULL
            )
        """)
        
        # Tabla de fichajes
        conn.execute("""
            CREATE TABLE IF NOT EXISTS punches (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                employee_id INTEGER NOT NULL,
                punch_type TEXT NOT NULL,
                timestamp INTEGER NOT NULL,
                location TEXT,
                notes TEXT,
                FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
            )
        """)
        
        # Tabla de productos
        conn.execute("""
            CREATE TABLE IF NOT EXISTS products (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                sku TEXT UNIQUE NOT NULL,
                name TEXT NOT NULL,
                description TEXT,
                category TEXT NOT NULL,
                price REAL NOT NULL DEFAULT 0.0,
                stock INTEGER NOT NULL DEFAULT 0,
                min_stock INTEGER DEFAULT 0,
                active INTEGER DEFAULT 1,
                created_at INTEGER NOT NULL,
                updated_at INTEGER NOT NULL
            )
        """)
        
        # Tabla de movimientos de stock
        conn.execute("""
            CREATE TABLE IF NOT EXISTS stock_movements (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                product_id INTEGER NOT NULL,
                employee_id INTEGER NOT NULL,
                movement_type TEXT NOT NULL,
                quantity INTEGER NOT NULL,
                reason TEXT NOT NULL,
                notes TEXT,
                timestamp INTEGER NOT NULL,
                FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                FOREIGN KEY (employee_id) REFERENCES employees(id)
            )
        """)
        
        # Tabla de operaciones
        conn.execute("""
            CREATE TABLE IF NOT EXISTS operations (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                operation_code TEXT UNIQUE NOT NULL,
                customer_email TEXT NOT NULL,
                employee_id INTEGER,
                status TEXT NOT NULL DEFAULT 'CREATED',
                total REAL NOT NULL DEFAULT 0.0,
                created_at INTEGER NOT NULL,
                updated_at INTEGER NOT NULL,
                FOREIGN KEY (employee_id) REFERENCES employees(id)
            )
        """)
        
        # Tabla de items de operaciones
        conn.execute("""
            CREATE TABLE IF NOT EXISTS operation_items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                operation_id INTEGER NOT NULL,
                product_id INTEGER NOT NULL,
                quantity INTEGER NOT NULL,
                unit_price REAL NOT NULL,
                subtotal REAL NOT NULL,
                FOREIGN KEY (operation_id) REFERENCES operations(id) ON DELETE CASCADE,
                FOREIGN KEY (product_id) REFERENCES products(id)
            )
        """)
        
        # Crea índices para optimizar búsquedas.
        conn.execute("CREATE INDEX IF NOT EXISTS idx_punches_employee ON punches(employee_id)")
        conn.execute("CREATE INDEX IF NOT EXISTS idx_punches_timestamp ON punches(timestamp)")
        conn.execute("CREATE INDEX IF NOT EXISTS idx_movements_product ON stock_movements(product_id)")
        conn.execute("CREATE INDEX IF NOT EXISTS idx_movements_employee ON stock_movements(employee_id)")


def seed_data():
    """Inserta datos iniciales de prueba si la BD está vacía."""
    with get_conn() as conn:
        # Verifica/Comprueba si ya hay empleados.
        count = conn.execute("SELECT COUNT(*) as c FROM employees").fetchone()["c"]
        if count > 0:
            return  # Si ya hay datos, no se insertara.
        
        now = int(datetime.now().timestamp())
        
        # Insertan empleados de ejemplo.
        conn.executemany(
            "INSERT INTO employees(employee_code, name, email, pin, role, created_at) VALUES(?,?,?,?,?,?)",
            [
                ("EMP001", "Rodrigo López Pérez", "rodrigo.lopez@empresa.com", "1234", "admin", now),
                ("EMP002", "María García", "maria.garcia@empresa.com", "5678", "manager", now),
                ("EMP003", "Carlos López", "carlos.lopez@empresa.com", "9012", "employee", now),
            ]
        )
        
        # Insertan productos de ejemplo.
        conn.executemany(
            "INSERT INTO products(sku, name, description, category, price, stock, min_stock, created_at, updated_at) VALUES(?,?,?,?,?,?,?,?,?)",
            [
                ("PROD-001", "Laptop HP 15\"", "Laptop para oficina", "Electrónica", 599.99, 15, 5, now, now),
                ("PROD-002", "Mouse Inalámbrico", "Mouse ergonómico", "Accesorios", 19.99, 50, 10, now, now),
                ("PROD-003", "Teclado Mecánico", "Teclado gaming RGB", "Accesorios", 79.99, 25, 5, now, now),
                ("PROD-004", "Monitor 24\"", "Monitor Full HD", "Electrónica", 199.99, 10, 3, now, now),
            ]
        )


# ========================================
# FUNCIONES DE EMPLEADOS
# ========================================

def db_list_employees(active_only: bool = True):
    """Lista todos los empleados."""
    sql = "SELECT * FROM employees WHERE 1=1"
    params = []
    
    if active_only:
        sql += " AND active = ?"
        params.append(1)
    
    sql += " ORDER BY name ASC"
    
    with get_conn() as conn:
        rows = conn.execute(sql, params).fetchall()
        return [dict(r) for r in rows]


def db_get_employee(employee_id: int):
    """Obtiene un empleado por ID."""
    with get_conn() as conn:
        row = conn.execute(
            "SELECT * FROM employees WHERE id = ?",
            (employee_id,)
        ).fetchone()
        return dict(row) if row else None


def db_get_employee_by_code(employee_code: str):
    """Obtiene un empleado por código."""
    with get_conn() as conn:
        row = conn.execute(
            "SELECT * FROM employees WHERE employee_code = ?",
            (employee_code,)
        ).fetchone()
        return dict(row) if row else None


def db_create_employee(employee_code: str, name: str, email: str, pin: str, role: str = "employee"):
    """Crea un nuevo empleado."""
    now = int(datetime.now().timestamp())
    
    with get_conn() as conn:
        # Verifica/Comprueba que no exista el código.
        existing = conn.execute(
            "SELECT id FROM employees WHERE employee_code = ?",
            (employee_code,)
        ).fetchone()
        
        if existing:
            raise ValueError(f"El código de empleado '{employee_code}' ya existe")
        
        # Verifica/Comprueba que no exista el email.
        existing_email = conn.execute(
            "SELECT id FROM employees WHERE email = ?",
            (email,)
        ).fetchone()
        
        if existing_email:
            raise ValueError(f"El email '{email}' ya está registrado")
        
        cursor = conn.execute(
            "INSERT INTO employees(employee_code, name, email, pin, role, created_at) VALUES(?,?,?,?,?,?)",
            (employee_code, name, email, pin, role, now)
        )
        
        employee_id = cursor.lastrowid
        return db_get_employee(employee_id)


def db_verify_employee(employee_code: str, pin: str):
    """Verifica las credenciales de un empleado."""
    with get_conn() as conn:
        row = conn.execute(
            "SELECT * FROM employees WHERE employee_code = ? AND pin = ? AND active = 1",
            (employee_code, pin)
        ).fetchone()
        return dict(row) if row else None


def db_delete_employee(employee_id: int):
    """Elimina un empleado (soft delete - se marca como inactivo)."""
    with get_conn() as conn:
        conn.execute(
            "UPDATE employees SET active = 0 WHERE id = ?",
            (employee_id,)
        )
        return True


# ========================================
# FUNCIONES DE FICHAJES
# ========================================

def db_create_punch(employee_id: int, punch_type: str, location: str = None, notes: str = None):
    """Registra un fichaje."""
    now = int(datetime.now().timestamp())
    
    with get_conn() as conn:
        # verifica/Comprueba que el empleado existe.
        employee = conn.execute(
            "SELECT id FROM employees WHERE id = ? AND active = 1",
            (employee_id,)
        ).fetchone()
        
        if not employee:
            raise ValueError("Empleado no encontrado o inactivo")
        
        cursor = conn.execute(
            "INSERT INTO punches(employee_id, punch_type, timestamp, location, notes) VALUES(?,?,?,?,?)",
            (employee_id, punch_type, now, location, notes)
        )
        
        punch_id = cursor.lastrowid
        
        # Retorna el fichaje creado.
        row = conn.execute(
            "SELECT * FROM punches WHERE id = ?",
            (punch_id,)
        ).fetchone()
        
        return dict(row)


def db_list_punches(employee_id: Optional[int] = None, limit: int = 100):
    """Lista fichajes con filtros opcionales."""
    sql = """
        SELECT p.*, e.name as employee_name, e.employee_code
        FROM punches p
        JOIN employees e ON p.employee_id = e.id
        WHERE 1=1
    """
    params = []
    
    if employee_id:
        sql += " AND p.employee_id = ?"
        params.append(employee_id)
    
    sql += " ORDER BY p.timestamp DESC LIMIT ?"
    params.append(limit)
    
    with get_conn() as conn:
        rows = conn.execute(sql, params).fetchall()
        return [dict(r) for r in rows]


# ========================================
# FUNCIONES DE PRODUCTOS
# ========================================

def db_list_products(category: Optional[str] = None, active_only: bool = True):
    """Lista productos con filtros opcionales."""
    sql = "SELECT * FROM products WHERE 1=1"
    params = []
    
    if active_only:
        sql += " AND active = ?"
        params.append(1)
    
    if category:
        sql += " AND category = ?"
        params.append(category)
    
    sql += " ORDER BY name ASC"
    
    with get_conn() as conn:
        rows = conn.execute(sql, params).fetchall()
        return [dict(r) for r in rows]


def db_get_product(product_id: int):
    """Obtiene un producto por ID."""
    with get_conn() as conn:
        row = conn.execute(
            "SELECT * FROM products WHERE id = ?",
            (product_id,)
        ).fetchone()
        return dict(row) if row else None


def db_get_product_by_sku(sku: str):
    """Obtiene un producto por SKU."""
    with get_conn() as conn:
        row = conn.execute(
            "SELECT * FROM products WHERE sku = ?",
            (sku,)
        ).fetchone()
        return dict(row) if row else None


def db_create_product(sku: str, name: str, category: str, price: float, stock: int = 0, description: str = None, min_stock: int = 0):
    """Crea un nuevo producto."""
    now = int(datetime.now().timestamp())
    
    with get_conn() as conn:
        # verifica/Comprueba que no exista el SKU.
        existing = conn.execute(
            "SELECT id FROM products WHERE sku = ?",
            (sku,)
        ).fetchone()
        
        if existing:
            raise ValueError(f"El SKU '{sku}' ya existe")
        
        cursor = conn.execute(
            "INSERT INTO products(sku, name, description, category, price, stock, min_stock, created_at, updated_at) VALUES(?,?,?,?,?,?,?,?,?)",
            (sku, name, description, category, price, stock, min_stock, now, now)
        )
        
        product_id = cursor.lastrowid
        return db_get_product(product_id)


def db_update_product_stock(product_id: int, new_stock: int):
    """Actualiza el stock de un producto"""
    now = int(datetime.now().timestamp())
    
    with get_conn() as conn:
        conn.execute(
            "UPDATE products SET stock = ?, updated_at = ? WHERE id = ?",
            (new_stock, now, product_id)
        )
        return db_get_product(product_id)


# ========================================
# FUNCIONES DE MOVIMIENTOS DE STOCK
# ========================================

def db_create_stock_movement(product_id: int, employee_id: int, movement_type: str, quantity: int, reason: str, notes: str = None):
    """Registra un movimiento de stock y actualiza el producto."""
    now = int(datetime.now().timestamp())
    
    with get_conn() as conn:
        # Obtiene el producto.
        product = conn.execute(
            "SELECT * FROM products WHERE id = ?",
            (product_id,)
        ).fetchone()
        
        if not product:
            raise ValueError("Producto no encontrado")
        
        # Calcula el nuevo stock.
        current_stock = product["stock"]
        
        if movement_type == "IN":
            new_stock = current_stock + quantity
        elif movement_type == "OUT":
            new_stock = current_stock - quantity
            if new_stock < 0:
                raise ValueError(f"Stock insuficiente. Stock actual: {current_stock}, cantidad solicitada: {quantity}")
        else:
            raise ValueError("Tipo de movimiento inválido. Use 'IN' o 'OUT'")
        
        # Registra el movimiento.
        cursor = conn.execute(
            "INSERT INTO stock_movements(product_id, employee_id, movement_type, quantity, reason, notes, timestamp) VALUES(?,?,?,?,?,?,?)",
            (product_id, employee_id, movement_type, quantity, reason, notes, now)
        )
        
        # Actualiza el stock del producto.
        conn.execute(
            "UPDATE products SET stock = ?, updated_at = ? WHERE id = ?",
            (new_stock, now, product_id)
        )
        
        movement_id = cursor.lastrowid
        
        # Retorna el movimiento creado.
        row = conn.execute(
            "SELECT * FROM stock_movements WHERE id = ?",
            (movement_id,)
        ).fetchone()
        
        return dict(row)


def db_list_stock_movements(product_id: Optional[int] = None, employee_id: Optional[int] = None, limit: int = 100):
    """Se listan movimientos de stock con filtros opcionales."""
    sql = """
        SELECT sm.*, p.name as product_name, p.sku, e.name as employee_name
        FROM stock_movements sm
        JOIN products p ON sm.product_id = p.id
        JOIN employees e ON sm.employee_id = e.id
        WHERE 1=1
    """
    params = []
    
    if product_id:
        sql += " AND sm.product_id = ?"
        params.append(product_id)
    
    if employee_id:
        sql += " AND sm.employee_id = ?"
        params.append(employee_id)
    
    sql += " ORDER BY sm.timestamp DESC LIMIT ?"
    params.append(limit)
    
    with get_conn() as conn:
        rows = conn.execute(sql, params).fetchall()
        return [dict(r) for r in rows]


# ========================================
# ESTADÍSTICAS
# ========================================

def db_get_stats():
    """Obtiene estadísticas generales del sistema."""
    with get_conn() as conn:
        # Total empleados activos.
        total_employees = conn.execute(
            "SELECT COUNT(*) as count FROM employees WHERE active = 1"
        ).fetchone()["count"]
        
        # Total productos.
        total_products = conn.execute(
            "SELECT COUNT(*) as count FROM products WHERE active = 1"
        ).fetchone()["count"]
        
        # Total fichajes hoy.
        from datetime import datetime, timedelta
        today_start = int(datetime.now().replace(hour=0, minute=0, second=0, microsecond=0).timestamp())
        
        punches_today = conn.execute(
            "SELECT COUNT(*) as count FROM punches WHERE timestamp >= ?",
            (today_start,)
        ).fetchone()["count"]
        
        # Productos con stock bajo.
        low_stock_products = conn.execute(
            "SELECT COUNT(*) as count FROM products WHERE stock <= min_stock AND active = 1"
        ).fetchone()["count"]
        
        return {
            "total_employees": total_employees,
            "total_products": total_products,
            "punches_today": punches_today,
            "low_stock_products": low_stock_products
        }