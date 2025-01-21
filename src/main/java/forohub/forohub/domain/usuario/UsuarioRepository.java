package forohub.forohub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdNot(String nombre, Long id);

    boolean existsByCorreoelectronico(String correoelectronico);
    boolean existsByCorreoelectronicoAndIdNot(String correoelectronico, Long id);

    Usuario getReferenceByCorreoelectronico(String correoelectronico);

    Page<Usuario> findByOrderByNombre(Pageable pageable);

}