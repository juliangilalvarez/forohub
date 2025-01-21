CREATE TABLE usuario (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  correoelectronico VARCHAR(100) NOT NULL,
  contrasena VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX ui_usuario_nombre (nombre ASC) VISIBLE,
  UNIQUE INDEX ui_usuario_correoelectronico (correoelectronico ASC) VISIBLE
);