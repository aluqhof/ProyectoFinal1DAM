apache
Copy
INSERT INTO deporte (id, nombre) VALUES (1, 'Tenis');
INSERT INTO deporte (id, nombre) VALUES (2, 'Pádel');
INSERT INTO deporte (id, nombre) VALUES (3, 'Fútbol');

INSERT INTO pista (id, numero, precio, deporte_id) VALUES (1, 1, 5.0, 1);
INSERT INTO pista (id, numero, precio, deporte_id) VALUES (2, 2, 5.0, 1);
INSERT INTO pista (id, numero, precio, deporte_id) VALUES (3, 1, 6.0, 2);
INSERT INTO pista (id, numero, precio, deporte_id) VALUES (4, 2, 7.0, 2);
INSERT INTO pista (id, numero, precio, deporte_id) VALUES (5, 3, 15.0, 2);
INSERT INTO pista (id, numero, precio, deporte_id) VALUES (6, 1, 5.5, 3);

INSERT INTO socio (id, nombre, apellidos, username, password, telefono, fecha_alta, cuota, admin) VALUES (1, 'Juan', 'Pérez', 'admin', '{bcrypt}$2a$10$RlcLSXIvm8VDjEBq19oB7OFEB3sJnxvJETStFrBeHwU1pYc0EGTTa', '123456789', '2023-01-01', 50.0, true);
INSERT INTO socio (id, nombre, apellidos, username, password, telefono, fecha_alta, cuota, admin) VALUES (2, 'María', 'López', 'user', '{bcrypt}$2a$10$bnFKD2JlOON0nuv94.KPqumWUSa9LORejpDtcumz/Vlg/diEjajzi', '987654321', '2023-02-01', 50.0, false);
INSERT INTO socio (id, nombre, apellidos, username, password, telefono, fecha_alta, cuota, admin) VALUES (3, 'Pedro', 'García', 'pedrogarcia', 'contraseña789', '555666777', '2023-03-01', 50.0, false);

INSERT INTO reserva (id, fecha_reserva, hora_reserva, socio_id, pista_id) VALUES (1, '2023-05-10', '10:00:00', 1, 1), (2, '2023-05-10', '11:00:00', 2, 2), (3, '2023-05-10', '12:00:00', 3, 3), (4, '2023-05-11', '10:00:00', 1, 4), (5, '2023-05-11', '11:00:00', 2, 5), (6, '2023-05-11', '12:00:00', 3, 6), (7, '2023-05-12', '10:00:00', 1, 1), (8, '2023-05-12', '11:00:00', 2, 2), (9, '2023-05-12', '12:00:00', 3, 3), (10, '2023-05-13', '10:00:00', 1, 4);

ALTER SEQUENCE hibernate_sequence RESTART WITH 1000;