INSERT INTO usuario (id, username, password) VALUES (1, 'admin', '{bcrypt}$2a$10$ZJFsSxIuhAE6yhj4zWkBe.hfELM1KA0UXnNBV8l/KknWQPTjvY8BS');

INSERT INTO usuario (id, username, password) VALUES (2, 'user', '{bcrypt}$2a$10$FUDKq1l1PRlxG4B7fnDfoOrxu/ChhiWNtdEPle5hN2JlM/cWbIJlK');

INSERT INTO usuario (id, username, password) VALUES (3, 'alexanderluquehoffrogge@gmail.com', '{bcrypt}$2a$10$FUDKq1l1PRlxG4B7fnDfoOrxu/ChhiWNtdEPle5hN2JlM/cWbIJlK');


INSERT INTO admin (id, nombre, apellidos) VALUES (1, 'Alex', 'Luque');

INSERT INTO socio (id, nombre, apellidos, telefono, fecha_alta) VALUES (2, 'Pepe', 'Fernández', '555-5555', '2022-01-01');

INSERT INTO socio (id, nombre, apellidos, telefono, fecha_alta) VALUES (3, 'Alexander', 'Luque Hoffrogge', '555-5555', '2022-01-01');


INSERT INTO deporte (id, nombre) VALUES (1, 'Tenis');

INSERT INTO deporte (id, nombre) VALUES (2, 'Pádel');

INSERT INTO pista (id, numero, precio, aumento_precio, deporte_id, hora_aumento_precio) VALUES (1, 1, 10.0, 5, 1, '18:00:00');

INSERT INTO pista (id, numero, precio, aumento_precio, deporte_id, hora_aumento_precio) VALUES (2, 2, 10.0, 10, 1, '18:00:00');

INSERT INTO pista (id, numero, precio, aumento_precio, deporte_id, hora_aumento_precio) VALUES (3, 1, 15.0, 15, 2, '18:00:00');

INSERT INTO pista (id, numero, precio, aumento_precio, deporte_id, hora_aumento_precio) VALUES (4, 2, 15.0, 10, 2, '18:00:00');


INSERT INTO reserva (id, fecha_reserva, hora_reserva, precio_reserva, socio_id, pista_id) VALUES (1, '2023-05-15', '10:00:00', 10, 2, 1);

INSERT INTO reserva (id, fecha_reserva, hora_reserva, precio_reserva, socio_id, pista_id) VALUES (2, '2023-05-16', '12:00:00', 10, 2, 2); 

INSERT INTO reserva (id, fecha_reserva, hora_reserva, precio_reserva, socio_id, pista_id) VALUES (3, '2023-05-15', '10:00:00', 15, 2, 3);

INSERT INTO reserva (id, fecha_reserva, hora_reserva, precio_reserva, socio_id, pista_id) VALUES (4, '2023-05-16', '12:00:00', 15, 2, 4); 

ALTER SEQUENCE hibernate_sequence RESTART WITH 1000;
