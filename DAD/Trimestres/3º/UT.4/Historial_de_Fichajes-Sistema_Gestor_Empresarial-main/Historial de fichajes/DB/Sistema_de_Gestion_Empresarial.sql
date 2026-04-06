-- ========================================
-- BASE DE DATOS UNIFICADA
-- Sistema de Gestión Empresarial Integrado
-- ========================================




-- MÓDULO 1: GESTIÓN DE EMPLEADOS Y FICHAJES
-- ========================================

-- Tabla de empleados (usuarios del sistema)
CREATE TABLE IF NOT EXISTS employees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    employee_code TEXT UNIQUE NOT NULL,  -- Código único del empleado (ej: "EMP001")
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    pin TEXT NOT NULL,                   -- PIN para fichaje (hasheado en producción)
    role TEXT NOT NULL DEFAULT 'employee', -- 'employee', 'manager', 'admin'
    active INTEGER DEFAULT 1,            -- 0 = inactivo, 1 = activo
    created_at INTEGER NOT NULL          -- timestamp
);

-- Tabla de fichajes
CREATE TABLE IF NOT EXISTS punches (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    employee_id INTEGER NOT NULL,
    punch_type TEXT NOT NULL,            -- 'IN' (entrada) o 'OUT' (salida)
    timestamp INTEGER NOT NULL,          -- timestamp del fichaje
    location TEXT,                       -- Ubicación opcional (si usas GPS)
    notes TEXT,                          -- Notas opcionales
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- Índice para búsquedas rápidas por empleado
CREATE INDEX IF NOT EXISTS idx_punches_employee 
ON punches(employee_id);

-- Índice para búsquedas por fecha
CREATE INDEX IF NOT EXISTS idx_punches_timestamp 
ON punches(timestamp);


-- MÓDULO 2: GESTIÓN DE INVENTARIO
-- ========================================

-- Tabla de productos
CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    sku TEXT UNIQUE NOT NULL,            -- Código único del producto
    name TEXT NOT NULL,
    description TEXT,
    category TEXT NOT NULL,              -- Categoría del producto
    price REAL NOT NULL DEFAULT 0.0,
    stock INTEGER NOT NULL DEFAULT 0,    -- Stock actual
    min_stock INTEGER DEFAULT 0,         -- Stock mínimo (alerta)
    active INTEGER DEFAULT 1,            -- 0 = inactivo, 1 = activo
    created_at INTEGER NOT NULL,
    updated_at INTEGER NOT NULL
);

-- Tabla de movimientos de stock
CREATE TABLE IF NOT EXISTS stock_movements (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    product_id INTEGER NOT NULL,
    employee_id INTEGER NOT NULL,        -- Quién hizo el movimiento
    movement_type TEXT NOT NULL,         -- 'IN' (entrada) o 'OUT' (salida)
    quantity INTEGER NOT NULL,           -- Cantidad del movimiento
    reason TEXT NOT NULL,                -- Motivo: 'purchase', 'sale', 'adjustment', etc.
    notes TEXT,                          -- Notas adicionales
    timestamp INTEGER NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Índices para optimizar consultas
CREATE INDEX IF NOT EXISTS idx_movements_product 
ON stock_movements(product_id);

CREATE INDEX IF NOT EXISTS idx_movements_employee 
ON stock_movements(employee_id);

CREATE INDEX IF NOT EXISTS idx_movements_timestamp 
ON stock_movements(timestamp);


-- MÓDULO 3: OPERACIONES DE NEGOCIO (OPCIONAL)
-- ========================================

-- Tabla de operaciones (ventas, pedidos, reservas, etc.)
CREATE TABLE IF NOT EXISTS operations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    operation_code TEXT UNIQUE NOT NULL, -- Código único (ej: "OP-2025-001")
    customer_email TEXT NOT NULL,
    employee_id INTEGER,                 -- Empleado que procesó la operación
    status TEXT NOT NULL DEFAULT 'CREATED', -- 'CREATED', 'PROCESSING', 'COMPLETED', 'CANCELLED'
    total REAL NOT NULL DEFAULT 0.0,
    created_at INTEGER NOT NULL,
    updated_at INTEGER NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Tabla de líneas de operación (detalles de productos en cada operación)
CREATE TABLE IF NOT EXISTS operation_items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    operation_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price REAL NOT NULL,            -- Precio unitario en el momento de la venta
    subtotal REAL NOT NULL,              -- quantity * unit_price
    FOREIGN KEY (operation_id) REFERENCES operations(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_operations_employee 
ON operations(employee_id);

CREATE INDEX IF NOT EXISTS idx_operation_items_operation 
ON operation_items(operation_id);


-- ========================================
-- DATOS DE EJEMPLO (SEED DATA)
-- ========================================

-- Empleados de ejemplo
INSERT OR IGNORE INTO employees (employee_code, name, email, pin, role, created_at) VALUES
('EMP001', 'Juan Pérez', 'juan.perez@empresa.com', '1234', 'admin', strftime('%s', 'now')),
('EMP002', 'María García', 'maria.garcia@empresa.com', '5678', 'manager', strftime('%s', 'now')),
('EMP003', 'Carlos López', 'carlos.lopez@empresa.com', '9012', 'employee', strftime('%s', 'now'));

-- Productos de ejemplo
INSERT OR IGNORE INTO products (sku, name, description, category, price, stock, min_stock, created_at, updated_at) VALUES
('PROD-001', 'Laptop HP 15"', 'Laptop para oficina', 'Electrónica', 599.99, 15, 5, strftime('%s', 'now'), strftime('%s', 'now')),
('PROD-002', 'Mouse Inalámbrico', 'Mouse ergonómico', 'Accesorios', 19.99, 50, 10, strftime('%s', 'now'), strftime('%s', 'now')),
('PROD-003', 'Teclado Mecánico', 'Teclado gaming RGB', 'Accesorios', 79.99, 25, 5, strftime('%s', 'now'), strftime('%s', 'now')),
('PROD-004', 'Monitor 24"', 'Monitor Full HD', 'Electrónica', 199.99, 10, 3, strftime('%s', 'now'), strftime('%s', 'now'));

-- Fichajes de ejemplo (últimos 2 días)
INSERT OR IGNORE INTO punches (employee_id, punch_type, timestamp, notes) VALUES
(1, 'IN', strftime('%s', 'now', '-1 day', 'start of day', '+8 hours'), 'Entrada matinal'),
(1, 'OUT', strftime('%s', 'now', '-1 day', 'start of day', '+17 hours'), 'Salida'),
(2, 'IN', strftime('%s', 'now', '-1 day', 'start of day', '+9 hours'), 'Entrada'),
(2, 'OUT', strftime('%s', 'now', '-1 day', 'start of day', '+18 hours'), 'Salida'),
(1, 'IN', strftime('%s', 'now', 'start of day', '+8 hours'), 'Entrada hoy'),
(2, 'IN', strftime('%s', 'now', 'start of day', '+9 hours'), 'Entrada hoy');

-- Movimientos de stock de ejemplo
INSERT OR IGNORE INTO stock_movements (product_id, employee_id, movement_type, quantity, reason, timestamp, notes) VALUES
(1, 1, 'IN', 10, 'purchase', strftime('%s', 'now', '-7 days'), 'Compra inicial'),
(2, 1, 'IN', 50, 'purchase', strftime('%s', 'now', '-7 days'), 'Compra inicial'),
(1, 2, 'OUT', 2, 'sale', strftime('%s', 'now', '-2 days'), 'Venta a cliente'),
(2, 2, 'OUT', 5, 'sale', strftime('%s', 'now', '-1 day'), 'Venta a cliente');