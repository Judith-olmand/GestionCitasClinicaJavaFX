DROP DATABASE IF EXISTS CLINICA;
CREATE DATABASE CLINICA ;
Use CLINICA;

CREATE TABLE CLIENTE(
                        id int primary key,
                        DNI varchar(10),
                        Nombre varchar(50),
                        Direccion varchar(25),
                        Telefono int,
                        Email varchar(50),
                        Contrasenia varchar(255)
);

CREATE TABLE ESPECIALIDAD(
                             ide int primary key,
                             nombre varchar(50)
);

CREATE TABLE CITA(
                     idp int,
                     NumeroCita int primary key,
                     Fecha date,
                     idEspecial int,
-- Especialidad enum('Medicina general', 'Pediatría', 'Traumatología', 'Dermatología', 'Cardiología'),
                     foreign key (idp) references Cliente(id),
                     foreign key (idEspecial) references Especialidad(ide)
);



-- 5 especialidades
INSERT INTO ESPECIALIDAD (ide, nombre) VALUES
                                           (1, 'Medicina general'),
                                           (2, 'Pediatría'),
                                           (3, 'Traumatología'),
                                           (4, 'Dermatología'),
                                           (5, 'Cardiología' );

-- 5 clientes
INSERT INTO CLIENTE (id, DNI, Nombre, Direccion, Telefono, Email, Contrasenia) VALUES
                                                                                   (1, '12345678A', 'María García', 'Calle Mayor 12', 612345678, 'maria.garcia@gmail.com', '$argon2id$m=65536,t=3,p=2$y40C2g7fcjZsyUUxEmBR3g$wCDj8BIFHu1KVNaX0w8Tk2JvBU+NpY5gbLjeY5fanx0'),
                                                                                   (2, '87654321B', 'Carlos López', 'Avda. Sol 45', 622334455, 'carlos.lopez@hotmail.com', '$argon2id$m=65536,t=3,p=2$PqgjdV+IsmnW+h1uNLYOGA$NHfVZSiPc9VtY3Ph6K9eZgXOMXk8wY8O8QX00nPDs/0'),
                                                                                   (3, '45678912C', 'Lucía Fernández', 'Calle Luna 8', 633445566, 'lucia.fernandez@gmail.com', '$argon2id$m=65536,t=3,p=2$bNtJ8PAFAPGE5D0iey0A/w$2cqGjczfsZkbQAsNaoSIcrrWk/JLJtXG43M6jq4S+Qo'),
                                                                                   (4, '78912345D', 'Javier Ruiz', 'Plaza España 3', 644556677, 'javier.ruiz@yahoo.es', '$argon2id$m=65536,t=3,p=2$jAmtCuSnWrbQDOn5g0As5g$gJ/LFg9zt0t5IPWXuKj5FRcWF5m2QYsF5Vs88s6ip0g'),
                                                                                   (5, '32165498E', 'Ana Torres', 'Calle Río 27', 655667788, 'ana.torres@gmail.com', '$argon2id$m=65536,t=3,p=2$4BSk0wYVjyflE8NEQ37btg$M4uNbtMu34brVczf3BcDtteCYKpKjDMei/nTHIpvnJc');

-- 10 citas
INSERT INTO CITA (idp, NumeroCita, Fecha, idEspecial) VALUES
                                                          (1, 101, '2026-10-05', 1),
                                                          (1, 102, '2026-10-20', 4),
                                                          (2, 103, '2026-10-06', 5),
                                                          (2, 104, '2026-11-10', 1),
                                                          (3, 105, '2026-10-08', 2),
                                                          (3, 106, '2026-10-15', 2),
                                                          (4, 107, '2026-10-12', 3),
                                                          (4, 108, '2026-11-03', 5),
                                                          (5, 109, '2026-10-18', 4),
                                                          (5, 110, '2026-11-25', 1);
