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
