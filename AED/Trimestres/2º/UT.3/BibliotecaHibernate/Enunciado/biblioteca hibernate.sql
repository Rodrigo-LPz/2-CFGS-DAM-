-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS biblioteca_hibernate 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE biblioteca_hibernate;

-- Tabla Autor
CREATE TABLE autores (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(50),
    fecha_nacimiento DATE,
    CONSTRAINT uk_autor_nombre UNIQUE (nombre, apellidos)
);

-- Tabla Libro
CREATE TABLE libros (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    fecha_publicacion DATE,
    numero_paginas INT,
    id_autor INT NOT NULL,
    CONSTRAINT fk_libro_autor FOREIGN KEY (id_autor) 
        REFERENCES autores(id_autor) ON DELETE CASCADE
);

-- Tabla Ejemplar
CREATE TABLE ejemplares (
    id_ejemplar INT AUTO_INCREMENT PRIMARY KEY,
    codigo_ejemplar VARCHAR(50) UNIQUE NOT NULL,
    estado VARCHAR(20) DEFAULT 'DISPONIBLE',
    ubicacion VARCHAR(100),
    id_libro INT NOT NULL,
    CONSTRAINT fk_ejemplar_libro FOREIGN KEY (id_libro) 
        REFERENCES libros(id_libro) ON DELETE CASCADE,
    CONSTRAINT chk_estado CHECK (estado IN ('DISPONIBLE', 'PRESTADO', 'REPARACION', 'BAJA'))
);

-- Datos de ejemplo
INSERT INTO autores (nombre, apellidos, nacionalidad, fecha_nacimiento) VALUES
('Gabriel', 'García Márquez', 'Colombiana', '1927-03-06'),
('Isabel', 'Allende', 'Chilena', '1942-08-02'),
('Miguel', 'de Cervantes', 'Española', '1547-09-29');

INSERT INTO libros (titulo, isbn, fecha_publicacion, numero_paginas, id_autor) VALUES
('Cien años de soledad', '978-84-376-0494-7', '1967-06-05', 471, 1),
('El amor en los tiempos del cólera', '978-84-397-0428-7', '1985-09-05', 464, 1),
('La casa de los espíritus', '978-84-204-2943-7', '1982-10-01', 433, 2),
('Don Quijote de la Mancha', '978-84-667-0814-4', '1605-01-16', 863, 3);

INSERT INTO ejemplares (codigo_ejemplar, estado, ubicacion, id_libro) VALUES
('EJ-001-2024', 'DISPONIBLE', 'Estantería A-12', 1),
('EJ-002-2024', 'DISPONIBLE', 'Estantería A-12', 1),
('EJ-003-2024', 'PRESTADO', 'Estantería A-13', 2),
('EJ-004-2024', 'DISPONIBLE', 'Estantería B-05', 3),
('EJ-005-2024', 'DISPONIBLE', 'Estantería C-20', 4);
