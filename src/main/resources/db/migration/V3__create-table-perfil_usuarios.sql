CREATE TABLE perfil_usuario (
  perfil_id BIGINT NOT NULL,
  usuario_id BIGINT NOT NULL,
  PRIMARY KEY (perfil_id, usuario_id),
  CONSTRAINT fk_perfil_usuario_perfil_id
    FOREIGN KEY (perfil_id)
    REFERENCES perfil (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_perfil_usuario_usuario_id
    FOREIGN KEY (usuario_id)
    REFERENCES usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);