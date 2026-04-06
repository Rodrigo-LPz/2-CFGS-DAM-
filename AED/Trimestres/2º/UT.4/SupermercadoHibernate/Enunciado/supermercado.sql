-- Script de creación de base de datos para el sistema de gestión de supermercado
-- Autor: Javier Sebastián Herrero
-- Fecha: Diciembre 2025

-- Eliminar la base de datos si existe y crearla nuevamente
DROP DATABASE IF EXISTS supermercado_hibernate;
CREATE DATABASE supermercado_hibernate CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE supermercado_hibernate;

-- Tabla PROVEEDORES
CREATE TABLE proveedores (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    cif VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    email VARCHAR(100),
    CONSTRAINT chk_email CHECK (email LIKE '%@%')
) ENGINE=InnoDB;

-- Tabla PRODUCTOS
CREATE TABLE productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    codigo_barras VARCHAR(13) NOT NULL UNIQUE,
    precio DECIMAL(10, 2) NOT NULL,
    categoria VARCHAR(100),
    id_proveedor INT NOT NULL,
    CONSTRAINT fk_producto_proveedor FOREIGN KEY (id_proveedor) 
        REFERENCES proveedores(id_proveedor) ON DELETE CASCADE,
    CONSTRAINT chk_precio CHECK (precio >= 0)
) ENGINE=InnoDB;

-- Tabla LOTES
CREATE TABLE lotes (
    id_lote INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    cantidad INT NOT NULL,
    fecha_caducidad DATE NOT NULL,
    estado ENUM('DISPONIBLE', 'VENDIDO', 'CADUCADO', 'RETIRADO') NOT NULL DEFAULT 'DISPONIBLE',
    id_producto INT NOT NULL,
    CONSTRAINT fk_lote_producto FOREIGN KEY (id_producto) 
        REFERENCES productos(id_producto) ON DELETE CASCADE,
    CONSTRAINT chk_cantidad CHECK (cantidad >= 0)
) ENGINE=InnoDB;

-- Índices para mejorar el rendimiento
CREATE INDEX idx_producto_proveedor ON productos(id_proveedor);
CREATE INDEX idx_lote_producto ON lotes(id_producto);
CREATE INDEX idx_lote_estado ON lotes(estado);
CREATE INDEX idx_producto_categoria ON productos(categoria);

-- Inserción de datos de ejemplo

-- Proveedores
INSERT INTO proveedores (nombre, cif, telefono, email) VALUES
('Alimentaria Central S.A.', 'A-11223344', '912345678', 'info@alimentaria.es'),
('Frutas y Verduras del Campo', 'B-22334455', '913456789', 'ventas@frutasdelcampo.es'),
('Lácteos La Granja', 'B-33445566', '914567890', 'contacto@lacteoslagranja.es');

-- Productos para Alimentaria Central S.A.
INSERT INTO productos (nombre, codigo_barras, precio, categoria, id_proveedor) VALUES
('Pasta italiana 500g', '8411111111111', 1.25, 'Alimentación', 1),
('Tomate triturado 400g', '8411111111112', 0.89, 'Alimentación', 1),
('Atún en aceite 3 latas', '8411111111113', 4.50, 'Alimentación', 1);

-- Productos para Frutas y Verduras del Campo
INSERT INTO productos (nombre, codigo_barras, precio, categoria, id_proveedor) VALUES
('Manzanas Golden 1kg', '8422222222221', 2.35, 'Frutería', 2),
('Plátanos de Canarias 1kg', '8422222222222', 1.95, 'Frutería', 2),
('Tomates cherry 250g', '8422222222223', 1.65, 'Verduras', 2);

-- Productos para Lácteos La Granja
INSERT INTO productos (nombre, codigo_barras, precio, categoria, id_proveedor) VALUES
('Leche entera 1L', '8433333333331', 1.10, 'Lácteos', 3),
('Yogur natural pack 8', '8433333333332', 2.25, 'Lácteos', 3),
('Queso manchego curado', '8433333333333', 12.90, 'Lácteos', 3);

-- Lotes para Pasta italiana
INSERT INTO lotes (codigo, cantidad, fecha_caducidad, estado, id_producto) VALUES
('LOT-PAST-001', 150, '2026-03-15', 'DISPONIBLE', 1),
('LOT-PAST-002', 100, '2026-04-20', 'DISPONIBLE', 1);

-- Lotes para Tomate triturado
INSERT INTO lotes (codigo, cantidad, fecha_caducidad, estado, id_producto) VALUES
('LOT-TOM-001', 200, '2025-12-31', 'DISPONIBLE', 2),
('LOT-TOM-002', 50, '2025-10-15', 'CADUCADO', 2);

-- Lotes para Atún en aceite
INSERT INTO lotes (codigo, cantidad, fecha_caducidad, estado, id_producto) VALUES
('LOT-ATUN-001', 80, '2027-06-30', 'DISPONIBLE', 3),
('LOT-ATUN-002', 0, '2027-08-15', 'VENDIDO', 3);

-- Lotes para Manzanas Golden
INSERT INTO lotes (codigo, cantidad, fecha_caducidad, estado, id_producto) VALUES
('LOT-MANZ-001', 30, '2025-01-10', 'DISPONIBLE', 4);

-- Lotes para Plátanos de Canarias
INSERT INTO lotes (codigo, cantidad, fecha_caducidad, estado, id_producto) VALUES
('LOT-PLAT-001', 45, '2025-01-05', 'DISPONIBLE', 5);

-- Lotes para Tomates cherry
INSERT INTO lotes (codigo, cantidad, fecha_caducidad, estado, id_producto) VALUES
('LOT-CHER-001', 25, '2025-01-08', 'DISPONIBLE', 6);

-- Lotes para Leche entera
INSERT INTO lotes (codigo, cantidad, fecha_caducidad, estado, id_producto) VALUES
('LOT-LECH-001', 120, '2025-01-20', 'DISPONIBLE', 7),
('LOT-LECH-002', 80, '2025-01-25', 'DISPONIBLE', 7);

-- Lotes para Yogur natural
INSERT INTO lotes (codigo, cantidad, fecha_caducidad, estado, id_producto) VALUES
('LOT-YOG-001', 60, '2025-01-15', 'DISPONIBLE', 8);

-- Lotes para Queso manchego
INSERT INTO lotes (codigo, cantidad, fecha_caducidad, estado, id_producto) VALUES
('LOT-QUES-001', 15, '2026-09-30', 'DISPONIBLE', 9),
('LOT-QUES-002', 10, '2026-11-15', 'DISPONIBLE', 9);

-- Consultas de verificación
-- SELECT * FROM proveedores;
-- SELECT * FROM productos;
-- SELECT * FROM lotes;

-- Consulta para ver toda la información relacionada
SELECT 
    p.nombre AS proveedor,
    pr.nombre AS producto,
    pr.precio,
    l.codigo AS codigo_lote,
    l.cantidad,
    l.fecha_caducidad,
    l.estado
FROM proveedores p
JOIN productos pr ON p.id_proveedor = pr.id_proveedor
JOIN lotes l ON pr.id_producto = l.id_producto
ORDER BY p.nombre, pr.nombre, l.codigo;