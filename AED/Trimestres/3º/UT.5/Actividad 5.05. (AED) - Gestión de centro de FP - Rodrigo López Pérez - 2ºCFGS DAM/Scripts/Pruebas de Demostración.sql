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