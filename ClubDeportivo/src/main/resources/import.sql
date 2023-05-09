INSERT INTO deporte (nombre) VALUES ('Tenis');
INSERT INTO deporte (nombre) VALUES ('Pádel');
INSERT INTO deporte (nombre) VALUES ('Fútbol');

INSERT INTO pista (numero, precio, deporte_id) VALUES (1, 5.0, 1);
INSERT INTO pista (numero, precio, deporte_id) VALUES (2, 5.0, 1);
INSERT INTO pista (numero, precio, deporte_id) VALUES (1, 6.0, 2);
INSERT INTO pista (numero, precio, deporte_id) VALUES (2, 7.0, 2);
INSERT INTO pista (numero, precio, deporte_id) VALUES (3, 15.0, 2);
INSERT INTO pista (numero, precio, deporte_id) VALUES (1, 5.5, 3);

INSERT INTO socio (nombre, apellidos, usuario, contrasena, telefono, fecha_alta, cuota) VALUES ('Juan', 'Pérez', 'juanperez', 'contraseña123', '123456789', '2023-01-01', 50.0);
INSERT INTO socio (nombre, apellidos, usuario, contrasena, telefono, fecha_alta, cuota) VALUES ('María', 'López', 'marialopez', 'contraseña456', '987654321', '2023-02-01', 50.0);
INSERT INTO socio (nombre, apellidos, usuario, contrasena, telefono, fecha_alta, cuota) VALUES ('Pedro', 'García', 'pedrogarcia', 'contraseña789', '555666777', '2023-03-01', 50.0);

INSERT INTO reserva (fecha_reserva, hora_reserva, socio_id, pista_id) VALUES ('2023-05-10', '10:00:00', 1, 1), ('2023-05-10', '11:00:00', 2, 2), ('2023-05-10', '12:00:00', 3, 3), ('2023-05-11', '10:00:00', 1, 4), ('2023-05-11', '11:00:00', 2, 5), ('2023-05-11', '12:00:00', 3, 6), ('2023-05-12', '10:00:00', 1, 1), ('2023-05-12', '11:00:00', 2, 2), ('2023-05-12', '12:00:00', 3, 3), ('2023-05-13', '10:00:00', 1, 4);

ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART with 1000
