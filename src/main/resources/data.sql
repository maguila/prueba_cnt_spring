DROP TABLE IF EXISTS sismos;
 
CREATE TABLE sismos (
  id_sismo INT IDENTITY NOT NULL PRIMARY KEY,
  fecha VARCHAR(500) NOT NULL,
  magnitud VARCHAR(500) NOT NULL,
  titulo VARCHAR(500) DEFAULT NULL,
  lugar VARCHAR(500) DEFAULT NULL
);
 
insert into sismos(fecha, magnitud, titulo, lugar) values('inicio','dato inicial', 'cargado desde data.sql', '');