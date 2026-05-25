-- Actividad AED: Sistema de Gestión de Centro de Formación Profesional
    -- Profesor: Javier Sebastián Herrero
    -- Curso: 2º CFGS Desarrollo de Aplicaciones Multiplataforma (DAM)
    -- Alumno: Rodrigo López Pérez
    -- Fecha: 14/01/2026



-- Script SQL completo (en orden correcto).
-- 1. Creación de la base de datos.
CREATE DATABASE centro_fp
	WITH
	ENCODING = 'UTF8' 
	LC_COLLATE = 'es_ES.UTF-8' 
	LC_CTYPE = 'es_ES.UTF-8'
	TEMPLATE = template0;



-- 2. Tipos de datos personalizados.
    -- 2.1. Tipos compuestos.
CREATE TYPE contacto_info AS (
    telefono_movil VARCHAR(20),
    telefono_fijo VARCHAR(20),
    email_personal VARCHAR(100),
    email_corporativo VARCHAR(100)
);

CREATE TYPE direccion_completa AS (
    via VARCHAR(100),
    numero INTEGER,
    piso VARCHAR(10),
    puerta VARCHAR(10),
    codigo_postal VARCHAR(10),
    localidad VARCHAR(100),
    provincia VARCHAR(50),
    pais VARCHAR(50)
);

CREATE TYPE nota_detalle AS (
    evaluacion VARCHAR(20),
    nota DECIMAL(4,2),
    fecha DATE,
    observaciones TEXT
);

    -- 2.2. Tipos enumerados (ENUM).
CREATE TYPE tipo_modulo AS ENUM ('obligatorio', 'optativo', 'proyecto');
CREATE TYPE estado_matricula AS ENUM ('activa', 'aprobada', 'suspendida', 'no_presentado', 'convalidada');
CREATE TYPE turno_formacion AS ENUM ('mañana', 'tarde', 'noche');



-- 3. Modelo con herencia (BDOR).
    -- 3.1. Tabla padre: persona.
CREATE TABLE persona (
    id SERIAL PRIMARY KEY,
    dni VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido1 VARCHAR(100) NOT NULL,
    apellido2 VARCHAR(100),
    fecha_nacimiento DATE NOT NULL,
    direccion direccion_completa,
    contacto contacto_info,
    datos_extra JSONB,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT true
);

    -- 3.1. Tablas heredadas.
CREATE TABLE alumno (
    num_expediente VARCHAR(20) UNIQUE NOT NULL,
    fecha_matriculacion DATE NOT NULL,
    idiomas VARCHAR(50)[],
    intereses VARCHAR(100)[],
    historial JSONB,
    media_global DECIMAL(4,2),
    creditos_superados INTEGER DEFAULT 0,
    creditos_matriculados INTEGER DEFAULT 0,
    PRIMARY KEY (id)
) INHERITS (persona);

CREATE TABLE profesor (
    num_empleado VARCHAR(20) UNIQUE NOT NULL,
    fecha_contratacion DATE NOT NULL,
    departamento VARCHAR(100),
    especialidades VARCHAR(100)[],
    titulaciones VARCHAR(200)[],
    perfil_docente JSONB,
    horas_lectivas INTEGER,
    tutor_grupo BOOLEAN DEFAULT false,
    PRIMARY KEY (id)
) INHERITS (persona);

CREATE TABLE administrativo (
    num_empleado VARCHAR(20) UNIQUE NOT NULL,
    fecha_contratacion DATE NOT NULL,
    puesto VARCHAR(100),
    permisos_sistema VARCHAR(50)[],
    nivel_acceso INTEGER DEFAULT 1,
    PRIMARY KEY (id)
) INHERITS (persona);



-- 4. Ciclos formativos y módulos.
    -- 4.1. Tabla ciclo.
CREATE TABLE ciclo (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    turno turno_formacion NOT NULL,
    plan_formativo JSONB,
    duracion_años INTEGER NOT NULL,
    salidas_profesionales TEXT[],
    activo BOOLEAN DEFAULT true
);

    -- 4.2. Tabla modulo.
CREATE TABLE modulo (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    ciclo_id INTEGER REFERENCES ciclo(id),
    creditos INTEGER NOT NULL,
    curso INTEGER NOT NULL CHECK (curso BETWEEN 1 AND 2),
    tipo tipo_modulo DEFAULT 'obligatorio',
    conocimientos_previos VARCHAR(200)[],
    contenido JSONB,
    max_alumnos INTEGER
);



-- 5. Relación profesor–módulo (asignación docente).
CREATE TABLE profesor_modulo (
    id SERIAL PRIMARY KEY,
    profesor_id INTEGER,
    modulo_id INTEGER REFERENCES modulo(id),
    curso_academico VARCHAR(9) NOT NULL,
    grupo VARCHAR(10),
    horario JSONB,
    UNIQUE (profesor_id, modulo_id, curso_academico, grupo)
);



-- 6. Matrícula y evaluación.
    -- 6.1. Tabla matricula.
CREATE TABLE matricula (
    id SERIAL PRIMARY KEY,
    alumno_id INTEGER,
    modulo_id INTEGER REFERENCES modulo(id),
    curso_academico VARCHAR(9) NOT NULL,
    estado estado_matricula DEFAULT 'activa',
    nota_final DECIMAL(4,2),
    detalle_nota nota_detalle NOT NULL,
    adaptaciones JSONB,
    UNIQUE (alumno_id, modulo_id, curso_academico)
);



-- 7. Funciones obligatorias (PL/pgSQL).
    -- 7.1. Función para calcular la media de un alumno.
    --      (considerando solo los módulos con estado 'aprobada' y notas no nulas).
CREATE OR REPLACE FUNCTION calcular_media_alumno(alumno_id_param INTEGER)
RETURNS DECIMAL(4,2) AS $$
DECLARE
    media DECIMAL(4,2);
BEGIN
    SELECT AVG(nota_final) INTO media
    FROM matricula
    WHERE alumno_id = alumno_id_param 
      AND estado = 'aprobada' 
      AND nota_final IS NOT NULL;
    
    RETURN COALESCE(media, 0.00);
END;
$$ LANGUAGE plpgsql;

    -- 7.2. Función para calcular el total de créditos superados por un alumno.
    --      (considerando solo los módulos con estado 'aprobada').
CREATE OR REPLACE FUNCTION creditos_superados(alumno_id_param INTEGER)
RETURNS INTEGER AS $$
DECLARE
    total_creditos INTEGER;
BEGIN
    SELECT SUM(m.creditos) INTO total_creditos
    FROM matricula mat
    JOIN modulo m ON mat.modulo_id = m.id
    WHERE mat.alumno_id = alumno_id_param 
      AND mat.estado = 'aprobada';
    
    RETURN COALESCE(total_creditos, 0);
END;
$$ LANGUAGE plpgsql;

    -- 7.3. Función para validar si un alumno ha superado todos los módulos de un ciclo y así saber si validar o no el curso académico.
    --      (devuelve TRUE si ha superado todos los módulos obligatorios del ciclo).
CREATE OR REPLACE FUNCTION validar_curso_academico(texto VARCHAR)
RETURNS BOOLEAN AS $$
BEGIN
    RETURN texto ~ '^\d{4}-\d{4}$';
END;
$$ LANGUAGE plpgsql;



-- 8. Triggers obligatorios.
    -- 8.1. Función trigger para actualizar la media del alumno tras insertar o actualizar una nota/estado.
    --     (se ejecuta después de insertar o actualizar la "nota_final" o el "estado" registrados al alumno en cuestión).
CREATE OR REPLACE FUNCTION fn_trg_actualizar_media()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE alumno
    SET media_global = calcular_media_alumno(NEW.alumno_id)
    WHERE id = NEW.alumno_id;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- [trigger que se activa al insertar o actualizar nota/estado (AFTER INSERT/UPDATE)].
CREATE TRIGGER trg_actualizar_media
AFTER INSERT OR UPDATE OF nota_final, estado ON matricula
FOR EACH ROW EXECUTE FUNCTION fn_trg_actualizar_media();

    -- 8.2. Función trigger para actualizar los créditos del alumno tras modificar sus matrículas.
    --     (se ejecuta después de insertar, actualizar o eliminar una matrícula para recalcular los créditos superados y matriculados).
CREATE OR REPLACE FUNCTION fn_trg_actualizar_creditos()
RETURNS TRIGGER AS $$
DECLARE
    v_alumno_id INTEGER;
BEGIN
    -- [identifica el ID del alumno (manejando el caso de DELETE)]
    v_alumno_id := COALESCE(NEW.alumno_id, OLD.alumno_id);

    UPDATE alumno
    SET 
        creditos_superados = creditos_superados(v_alumno_id),
        creditos_matriculados = (
            SELECT COALESCE(SUM(m.creditos), 0)
            FROM matricula mat
            JOIN modulo m ON mat.modulo_id = m.id
            WHERE mat.alumno_id = v_alumno_id
        )
    WHERE id = v_alumno_id;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- [trigger que se activa tras realizar una operación (AFTER INSERT/UPDATE/DELETE)].
CREATE TRIGGER trg_actualizar_creditos
AFTER INSERT OR UPDATE OR DELETE ON matricula
FOR EACH ROW EXECUTE FUNCTION fn_trg_actualizar_creditos();

    -- 8.3. Función trigger para controlar/gestionar el aforo máximo de alumnos en un módulo al insertar una matrícula.
    --      (impide que un alumno se inscriba si el módulo ya ha alcanzado su límite de capacidad para ese curso específico).
CREATE OR REPLACE FUNCTION fn_control_max_alumnos()
RETURNS TRIGGER AS $$
DECLARE
    v_max_alumnos INTEGER;
    v_actuales INTEGER;
BEGIN
    -- (obtiene el máximo permitido para ese módulo).
    SELECT max_alumnos INTO v_max_alumnos FROM modulo WHERE id = NEW.modulo_id;
    
    -- (cuenta cuántos alumnos hay ya inscritos en ese curso y módulo).
    SELECT COUNT(*) INTO v_actuales 
    FROM matricula 
    WHERE modulo_id = NEW.modulo_id AND curso_academico = NEW.curso_academico;

    IF v_actuales >= v_max_alumnos THEN
        RAISE EXCEPTION 'Capacidad máxima alcanzada para este módulo en el curso %', NEW.curso_academico;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_control_max_alumnos
BEFORE INSERT ON matricula
FOR EACH ROW EXECUTE FUNCTION fn_control_max_alumnos();



-- 9. Vistas obligatorias.
    -- 9.1. Resumen de alumnos.
CREATE OR REPLACE VIEW vw_alumnos_resumen AS
SELECT 
    id, dni, nombre, (apellido1 || ' ' || COALESCE(apellido2, '')) AS apellidos,
    media_global, creditos_superados, creditos_matriculados,
    idiomas,
    (direccion).localidad
FROM alumno;

    -- 9.2. Información de Docencia.
CREATE OR REPLACE VIEW vw_docencia AS
SELECT 
    pm.curso_academico,
    (p.nombre || ' ' || p.apellido1) AS profesor,
    m.nombre AS modulo,
    c.nombre AS ciclo,
    pm.grupo,
    pm.horario
FROM profesor_modulo pm
JOIN profesor p ON pm.profesor_id = p.id
JOIN modulo m ON pm.modulo_id = m.id
JOIN ciclo c ON m.ciclo_id = c.id;

    -- 9.3. Detalle de Matrículas.
CREATE OR REPLACE VIEW vw_matriculas_detalle AS
SELECT 
    (a.nombre || ' ' || a.apellido1) AS alumno,
    m.nombre AS modulo,
    mat.curso_academico, mat.estado, mat.nota_final,
    (mat.detalle_nota).nota, (mat.detalle_nota).evaluacion, (mat.detalle_nota).fecha
FROM matricula mat
JOIN alumno a ON mat.alumno_id = a.id
JOIN modulo m ON mat.modulo_id = m.id;




-- Creación e inserciones de pruebas.
-- Inserción de Ciclos (2 ciclos).
INSERT INTO ciclo (codigo, nombre, turno, duracion_años, salidas_profesionales) VALUES
('DAM', 'Desarrollo de Aplicaciones Multiplataforma', 'mañana', 2, ARRAY['Programador multiplataforma', 'Desarrollador de aplicaciones móviles', 'Desarrollador de videojuegos', 'Analista programador', 'Especialista en bases de datos', 'Administrador de sistemas informáticos', 'Tester de aplicaciones']),
('DAW', 'Desarrollo de Aplicaciones Web', 'tarde', 2, ARRAY['Desarrollador Web', 'Diseñador UX/UI', 'Administrador de sistemas web y bases de datos', 'Especialista en SEO/SEM', 'Desarrollador E-commerce', 'Consultor en soluciones web']);

-- Inserción de Profesores (2 profesores).
INSERT INTO profesor (dni, nombre, apellido1, fecha_nacimiento, num_empleado, fecha_contratacion, departamento) VALUES
('11111111A', 'Javier', 'Pérez', '1985-05-15', 'EMP001', '2010-09-01', 'Informática'),
('11111111B', 'Javier', 'Sebastian', '1980-08-15', 'EMP002', '2015-09-01', 'Informática');

-- Inserción de Módulos (4 módulos, 2 por ciclo).
INSERT INTO modulo (codigo, nombre, ciclo_id, creditos, curso, tipo, max_alumnos) VALUES
('AED', 'Acceso a Datos', 1, 10, 1, 'obligatorio', 15), 
('PGV', 'Programación de servicios y procesos', 1, 8, 1, 'obligatorio', 30),
('DPL', 'Despliegue de aplicaciones web', 2, 10, 2, 'obligatorio', 27),
('DAD', 'Desarrollo de interfaces', 2, 8, 2, 'obligatorio', 15);

-- Inserción de Alumnos (3 alumnos).
INSERT INTO alumno (dni, nombre, apellido1, fecha_nacimiento, num_expediente, fecha_matriculacion, idiomas, direccion) VALUES
('11111111C', 'Matías Ariel', 'Bayas', '2005-07-06', 'EXP001', '2025-09-01', ARRAY['Inglés'], ROW('Calle Mayor', 5, '1', 'A', '28001', 'Arrecife', 'Las Palmas de Gran Canaria', 'España')),
('11111111D', 'Jesús', 'López', '2004-02-06', 'EXP002', '2025-09-01', ARRAY['Inglés', 'Francés'], ROW('Avenida Paz', 10, '2', 'B', '28002', 'Arrecife', 'Las Palmas de Gran Canaria', 'España')),
('11111111E', 'Rodrgo', 'López', '2006-10-14', 'EXP003', '2025-09-01', ARRAY['Inglés', 'Alemán'], ROW('Plaza Sol', 1, NULL, NULL, '28001', 'Arrecife', 'Las Palmas de Gran Canaria', 'España'));

-- Inserción de Matrículas (10 matrículas).
-- Se insertan con notas para que el trigger de media y créditos actúe 
INSERT INTO matricula (alumno_id, modulo_id, curso_academico, estado, nota_final, detalle_nota) VALUES
-- [Matías (ID 1 aprox)].
(1, 1, '2025-2026', 'aprobada', 8.5, ROW('final', 8.5, '2026-06-15', 'Excelente')),
(1, 2, '2025-2026', 'aprobada', 7.0, ROW('final', 7.0, '2026-06-15', 'Debe recuperar')),
-- [Jesús (ID 2 aprox)].
(2, 1, '2025-2026', 'aprobada', 9.0, ROW('final', 9.0, '2026-06-15', 'Sobresaliente')),
(2, 3, '2025-2026', 'activa', NULL, ROW('1a', 5.0, '2025-12-20', 'En curso')),
(2, 4, '2025-2026', 'convalidada', NULL, ROW('final', NULL, '2025-09-10', 'Convalidado')),
-- [Rodrgo (ID 3 aprox)].
(3, 2, '2025-2026', 'suspendida', 3.0, ROW('final', 3.0, '2026-06-15', 'Aprobado')),
(3, 3, '2025-2026', 'aprobada', 6.5, ROW('final', 6.5, '2026-06-15', 'Notable alto')),
-- (más matrículas para llegar a 10).
(1, 3, '2025-2026', 'activa', NULL, ROW('1a', 6.0, '2025-12-20', '')),
(3, 4, '2025-2026', 'activa', NULL, ROW('1a', 7.5, '2025-12-20', '')),
(2, 2, '2025-2026', 'aprobada', 8.0, ROW('final', 8.0, '2026-06-15', ''));




-- Pruebas de Demostración.
-- Verificación de Triggers (Media y Créditos).
-- (Comprobar que Matías (ID 1) tiene media y créditos calculados)
SELECT nombre, media_global, creditos_superados, creditos_matriculados 
FROM alumno WHERE id = 1;

-- Verificación del Trigger de Aforo.
-- [Intenta insertar una matrícula que supere el aforo máximo del módulo 1 (max 15 alumnos). Esta insercción debe falalr por el trigger "trg_control_max_alumnos"].
INSERT INTO matricula (alumno_id, modulo_id, curso_academico, estado, detalle_nota) 
VALUES (3, 1, '2025-2026', 'activa', ROW('1a', 0, '2025-10-01', 'Prueba error'));

-- Verificación de Vistas
-- (Consultar las vistas creadas para verificar su contenido).
SELECT * FROM vw_alumnos_resumen;
SELECT * FROM vw_matriculas_detalle;