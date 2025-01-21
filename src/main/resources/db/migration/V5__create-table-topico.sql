CREATE TABLE topico (
  id BIGINT NOT NULL AUTO_INCREMENT,
  titulo VARCHAR(100) NOT NULL,
  mensaje VARCHAR(500) NOT NULL,
  fechacreacion DATETIME NOT NULL,
  estado VARCHAR(50) NOT NULL,
  usuario_id BIGINT NOT NULL,
  curso_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX ui_topico_titulo_mensaje (titulo ASC, mensaje ASC) VISIBLE,
  CONSTRAINT fk_topico_usuario_id
    FOREIGN KEY (usuario_id)
    REFERENCES usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_topico_curso_id
    FOREIGN KEY (curso_id)
    REFERENCES curso (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);