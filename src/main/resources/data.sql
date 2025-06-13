USE api_test;
-- CREATE DATABASE api_test;
-- DROP DATABASE api_test;

-- Limpiar tablas existentes
DELETE FROM solicitud;
DELETE FROM reserva;
DELETE FROM comision;
DELETE FROM carrera_asignatura;
DELETE FROM asignatura;
DELETE FROM carrera;
DELETE FROM espacio;
DELETE FROM usuario;

-- USUARIOS - Ampliado con más roles y usuarios
INSERT INTO usuario (nombre, apellido, username, password, rol) VALUES 
('Admin', 'Sistema', 'admin', '$2a$10$Pzv7yk8ggNHl.n.4ciTHp.Di8Xve.nFRo0q1jMwvXKpIE3wx9iC3O', 'ADMIN'),
('Carlos', 'Martínez', 'profe', '$2a$10$wPHJ38iZ3bK5Abq50fHyeufyHUm4AgOWTh2a0rBTdxuB3lx0EyLRm', 'PROFESOR'),
('María', 'González', 'maria.gonzalez', '$2a$10$wPHJ38iZ3bK5Abq50fHyeufyHUm4AgOWTh2a0rBTdxuB3lx0EyLRm', 'PROFESOR'),
('Juan', 'Pérez', 'juan.perez', '$2a$10$wPHJ38iZ3bK5Abq50fHyeufyHUm4AgOWTh2a0rBTdxuB3lx0EyLRm', 'PROFESOR'),
('Ana', 'Rodriguez', 'ana.rodriguez', '$2a$10$wPHJ38iZ3bK5Abq50fHyeufyHUm4AgOWTh2a0rBTdxuB3lx0EyLRm', 'PROFESOR'),
('Luis', 'Fernández', 'luis.fernandez', '$2a$10$wPHJ38iZ3bK5Abq50fHyeufyHUm4AgOWTh2a0rBTdxuB3lx0EyLRm', 'PROFESOR'),
('Sofia', 'López', 'sofia.lopez', '$2a$10$wPHJ38iZ3bK5Abq50fHyeufyHUm4AgOWTh2a0rBTdxuB3lx0EyLRm', 'PROFESOR'),
('Diego', 'Ramírez', 'diego.ramirez', '$2a$10$wPHJ38iZ3bK5Abq50fHyeufyHUm4AgOWTh2a0rBTdxuB3lx0EyLRm', 'PROFESOR'),
('Laura', 'Torres', 'laura.torres', '$2a$10$wPHJ38iZ3bK5Abq50fHyeufyHUm4AgOWTh2a0rBTdxuB3lx0EyLRm', 'PROFESOR'),
('Roberto', 'Silva', 'roberto.silva', '$2a$10$wPHJ38iZ3bK5Abq50fHyeufyHUm4AgOWTh2a0rBTdxuB3lx0EyLRm', 'PROFESOR'),
('Patricia', 'Morales', 'patricia.morales', '$2a$10$wPHJ38iZ3bK5Abq50fHyeufyHUm4AgOWTh2a0rBTdxuB3lx0EyLRm', 'PROFESOR');

-- ASIGNATURAS - Expandido con más materias
INSERT INTO asignatura (id, codigo, nombre_asignatura, requiere_laboratorio) VALUES 
(1, 101, 'Programación 1', 1),
(2, 102, 'Bases de Datos', 1),
(3, 103, 'Matemática Discreta', 0),
(4, 201, 'Programación 2', 1),
(5, 202, 'Estructuras de Datos', 1),
(6, 203, 'Algoritmos', 0),
(7, 301, 'Ingeniería de Software', 0),
(8, 302, 'Redes de Computadoras', 1),
(9, 303, 'Sistemas Operativos', 1),
(10, 104, 'Álgebra Lineal', 0),
(11, 105, 'Cálculo 1', 0),
(12, 106, 'Física 1', 1),
(13, 304, 'Inteligencia Artificial', 1),
(14, 305, 'Desarrollo Web', 1),
(15, 306, 'Seguridad Informática', 0);

-- CARRERAS - Expandido
INSERT INTO carrera (id, nombre) VALUES 
(1, 'Tecnicatura en Programación'),
(2, 'Licenciatura en Informática'),
(3, 'Ingeniería en Sistemas'),
(4, 'Tecnicatura en Redes'),
(5, 'Analista de Sistemas');

-- RELACIÓN CARRERA-ASIGNATURA - Más combinaciones
INSERT INTO carrera_asignatura (carrera_id, asignatura_id) VALUES 
-- Tecnicatura en Programación
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 14),
-- Licenciatura en Informática
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7), (2, 10), (2, 11), (2, 13),
-- Ingeniería en Sistemas
(3, 1), (3, 2), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8), (3, 9), (3, 10), (3, 11), (3, 12), (3, 13), (3, 15),
-- Tecnicatura en Redes
(4, 8), (4, 9), (4, 12), (4, 15),
-- Analista de Sistemas
(5, 1), (5, 2), (5, 4), (5, 5), (5, 6), (5, 7);

-- ESPACIOS - Más aulas y laboratorios
INSERT INTO espacio (id, nombre, tipo_espacio, capacidad, tiene_proyector, tienetv, cantidad_computadoras) VALUES 
(1, 'Aula 101', 'AULA', 40, 1, 0, 0),
(2, 'Aula 102', 'AULA', 35, 1, 1, 0),
(3, 'Aula 103', 'AULA', 45, 1, 0, 0),
(4, 'Aula 201', 'AULA', 50, 1, 1, 0),
(5, 'Aula 202', 'AULA', 30, 0, 1, 0),
(6, 'Aula 203', 'AULA', 38, 1, 0, 0),
(7, 'Aula Magna', 'AULA', 120, 1, 1, 0),
(8, 'Lab Informática 1', 'LABORATORIO', 25, 1, 1, 25),
(9, 'Lab Informática 2', 'LABORATORIO', 30, 1, 1, 30),
(10, 'Lab Informática 3', 'LABORATORIO', 20, 1, 0, 20),
(11, 'Lab Redes', 'LABORATORIO', 15, 1, 1, 15),
(12, 'Lab Física', 'LABORATORIO', 20, 0, 0, 0),
(13, 'Sala de Conferencias', 'AULA', 25, 1, 1, 0),
(14, 'Aula Virtual 1', 'AULA', 35, 1, 1, 0),
(15, 'Aula Virtual 2', 'AULA', 40, 1, 1, 0);

-- COMISIONES - Muchas más comisiones
INSERT INTO comision (id, cantidad_alumnos, nombre, asignatura_id, carrera_id, usuario_profesor_id) VALUES 
-- Programación 1
(1, 35, 'Prog1-C1-TM', 1, 1, 2),
(2, 32, 'Prog1-C2-TT', 1, 1, 3),
(3, 28, 'Prog1-C3-TN', 1, 2, 4),
-- Bases de Datos
(4, 25, 'BD-C1-TM', 2, 1, 5),
(5, 30, 'BD-C2-TT', 2, 2, 6),
(6, 22, 'BD-C3-TN', 2, 3, 7),
-- Matemática Discreta
(7, 40, 'MatDisc-C1-TM', 3, 2, 8),
(8, 35, 'MatDisc-C2-TT', 3, 3, 9),
-- Programación 2
(9, 20, 'Prog2-C1-TM', 4, 1, 2),
(10, 18, 'Prog2-C2-TN', 4, 2, 3),
-- Estructuras de Datos
(11, 22, 'EstrDatos-C1-TT', 5, 2, 4),
(12, 25, 'EstrDatos-C2-TN', 5, 3, 5),
-- Algoritmos
(13, 30, 'Algo-C1-TM', 6, 2, 6),
(14, 28, 'Algo-C2-TT', 6, 3, 7),
-- Ingeniería de Software
(15, 24, 'IngSoft-C1-TM', 7, 3, 8),
(16, 26, 'IngSoft-C2-TN', 7, 5, 9),
-- Redes
(17, 15, 'Redes-C1-TT', 8, 3, 10),
(18, 18, 'Redes-C2-TN', 8, 4, 11),
-- Sistemas Operativos
(19, 20, 'SO-C1-TM', 9, 3, 2),
(20, 22, 'SO-C2-TT', 9, 3, 3),
-- Desarrollo Web
(21, 28, 'DevWeb-C1-TM', 14, 1, 4),
(22, 30, 'DevWeb-C2-TN', 14, 2, 5);

-- RESERVAS - Horarios de toda la semana
INSERT INTO reserva (id, fecha_inicio, fecha_fin, hora_inicio, hora_fin, dia, aula_id, comision_id) VALUES 
-- Lunes
(1, '2025-06-16', '2025-12-15', '08:00:00', '10:00:00', 'LUNES', 1, 1),   -- Prog1-C1
(2, '2025-06-16', '2025-12-15', '10:00:00', '12:00:00', 'LUNES', 8, 4),   -- BD-C1 (Lab)
(3, '2025-06-16', '2025-12-15', '14:00:00', '16:00:00', 'LUNES', 2, 7),   -- MatDisc-C1
(4, '2025-06-16', '2025-12-15', '16:00:00', '18:00:00', 'LUNES', 9, 9),   -- Prog2-C1 (Lab)
(5, '2025-06-16', '2025-12-15', '18:00:00', '20:00:00', 'LUNES', 3, 13),  -- Algo-C1

-- Martes
(6, '2025-06-17', '2025-12-16', '08:00:00', '10:00:00', 'MARTES', 4, 2),   -- Prog1-C2
(7, '2025-06-17', '2025-12-16', '10:00:00', '12:00:00', 'MARTES', 8, 11),  -- EstrDatos-C1 (Lab)
(8, '2025-06-17', '2025-12-16', '14:00:00', '16:00:00', 'MARTES', 5, 8),   -- MatDisc-C2
(9, '2025-06-17', '2025-12-16', '16:00:00', '18:00:00', 'MARTES', 9, 17),  -- Redes-C1 (Lab)
(10, '2025-06-17', '2025-12-16', '18:00:00', '20:00:00', 'MARTES', 6, 15), -- IngSoft-C1

-- Miércoles
(11, '2025-06-18', '2025-12-17', '08:00:00', '10:00:00', 'MIERCOLES', 1, 3),   -- Prog1-C3
(12, '2025-06-18', '2025-12-17', '10:00:00', '12:00:00', 'MIERCOLES', 8, 5),   -- BD-C2 (Lab)
(13, '2025-06-18', '2025-12-17', '14:00:00', '16:00:00', 'MIERCOLES', 10, 19), -- SO-C1 (Lab)
(14, '2025-06-18', '2025-12-17', '16:00:00', '18:00:00', 'MIERCOLES', 7, 14),  -- Algo-C2
(15, '2025-06-18', '2025-12-17', '18:00:00', '20:00:00', 'MIERCOLES', 2, 16),  -- IngSoft-C2

-- Jueves
(16, '2025-06-19', '2025-12-18', '08:00:00', '10:00:00', 'JUEVES', 9, 6),   -- BD-C3 (Lab)
(17, '2025-06-19', '2025-12-18', '10:00:00', '12:00:00', 'JUEVES', 3, 10),  -- Prog2-C2
(18, '2025-06-19', '2025-12-18', '14:00:00', '16:00:00', 'JUEVES', 8, 12),  -- EstrDatos-C2 (Lab)
(19, '2025-06-19', '2025-12-18', '16:00:00', '18:00:00', 'JUEVES', 11, 18), -- Redes-C2 (Lab)
(20, '2025-06-19', '2025-12-18', '18:00:00', '20:00:00', 'JUEVES', 4, 20),  -- SO-C2

-- Viernes
(21, '2025-06-20', '2025-12-19', '08:00:00', '10:00:00', 'VIERNES', 9, 21),  -- DevWeb-C1 (Lab)
(22, '2025-06-20', '2025-12-19', '10:00:00', '12:00:00', 'VIERNES', 8, 22),  -- DevWeb-C2 (Lab)
(23, '2025-06-20', '2025-12-19', '14:00:00', '16:00:00', 'VIERNES', 13, 1),  -- Prog1-C1 (Consulta)
(24, '2025-06-20', '2025-12-19', '16:00:00', '18:00:00', 'VIERNES', 14, 7),  -- MatDisc-C1 (Consulta)
(25, '2025-06-20', '2025-12-19', '18:00:00', '20:00:00', 'VIERNES', 15, 15); -- IngSoft-C1 (Consulta)

-- SOLICITUDES - Varias solicitudes en diferentes estados
INSERT INTO solicitud (
  id, fecha_inicio, fecha_fin, hora_inicio, hora_fin, dia_semana, 
  comision_id, usuario_id, fecha_hora_solicitud, estado, nuevo_espacio_id
) VALUES 
-- Solicitudes pendientes
(1, '2025-06-23', '2025-06-23', '14:00:00', '16:00:00', 'LUNES', 
  1, 2, '2025-06-13 09:00:00', 'PENDIENTE', 8),
(2, '2025-06-24', '2025-06-24', '10:00:00', '12:00:00', 'MARTES', 
  4, 5, '2025-06-13 10:30:00', 'PENDIENTE', 9),
(3, '2025-06-25', '2025-06-25', '16:00:00', '18:00:00', 'MIERCOLES', 
  9, 2, '2025-06-13 11:15:00', 'PENDIENTE', 10),

-- Solicitudes aprobadas
(4, '2025-06-26', '2025-06-26', '08:00:00', '10:00:00', 'JUEVES', 
  7, 8, '2025-06-12 14:20:00', 'APROBADA', 7),
(5, '2025-06-27', '2025-06-27', '14:00:00', '16:00:00', 'VIERNES', 
  11, 4, '2025-06-12 15:45:00', 'APROBADA', 8),
(6, '2025-06-30', '2025-06-30', '10:00:00', '12:00:00', 'LUNES', 
  15, 8, '2025-06-11 16:30:00', 'APROBADA', 13),

-- Solicitudes rechazadas
(7, '2025-06-23', '2025-06-23', '08:00:00', '10:00:00', 'LUNES', 
  2, 3, '2025-06-10 12:00:00', 'RECHAZADA', 1),
(8, '2025-06-24', '2025-06-24', '14:00:00', '16:00:00', 'MARTES', 
  5, 6, '2025-06-10 13:30:00', 'RECHAZADA', 2),

-- Solicitudes de cambio de aula por mantenimiento
(9, '2025-06-25', '2025-06-25', '08:00:00', '10:00:00', 'MIERCOLES', 
  3, 4, '2025-06-13 08:00:00', 'PENDIENTE', 14),
(10, '2025-06-26', '2025-06-26', '16:00:00', '18:00:00', 'JUEVES', 
  17, 10, '2025-06-13 09:30:00', 'PENDIENTE', 11),

-- Solicitudes para eventos especiales
(11, '2025-07-01', '2025-07-01', '18:00:00', '20:00:00', 'MARTES', 
  21, 4, '2025-06-13 12:00:00', 'PENDIENTE', 7),
(12, '2025-07-02', '2025-07-02', '14:00:00', '18:00:00', 'MIERCOLES', 
  16, 9, '2025-06-13 13:15:00', 'APROBADA', 7),

-- Solicitudes urgentes
(13, '2025-06-16', '2025-06-16', '20:00:00', '22:00:00', 'LUNES', 
  19, 2, '2025-06-13 16:45:00', 'PENDIENTE', 9),
(14, '2025-06-17', '2025-06-17', '20:00:00', '22:00:00', 'MARTES', 
  20, 3, '2025-06-13 17:20:00', 'APROBADA', 10),

-- Solicitudes para recuperatorios
(15, '2025-07-15', '2025-07-15', '09:00:00', '12:00:00', 'MARTES', 
  1, 2, '2025-06-13 14:30:00', 'PENDIENTE', 7),
(16, '2025-07-16', '2025-07-16', '14:00:00', '17:00:00', 'MIERCOLES', 
  4, 5, '2025-06-13 15:00:00', 'PENDIENTE', 7),
(17, '2025-07-17', '2025-07-17', '09:00:00', '12:00:00', 'JUEVES', 
  7, 8, '2025-06-13 15:30:00', 'APROBADA', 4);

-- Consultas de verificación
SELECT 'USUARIOS' as tabla, COUNT(*) as cantidad FROM usuario
UNION ALL
SELECT 'ASIGNATURAS', COUNT(*) FROM asignatura
UNION ALL
SELECT 'CARRERAS', COUNT(*) FROM carrera
UNION ALL
SELECT 'ESPACIOS', COUNT(*) FROM espacio
UNION ALL
SELECT 'COMISIONES', COUNT(*) FROM comision
UNION ALL
SELECT 'RESERVAS', COUNT(*) FROM reserva
UNION ALL
SELECT 'SOLICITUDES', COUNT(*) FROM solicitud;