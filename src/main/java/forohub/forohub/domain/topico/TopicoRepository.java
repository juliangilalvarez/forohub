package forohub.forohub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);

    Page<Topico> findByOrderByFechacreacionDesc(Pageable pageable);

    Page<Topico> findTop10ByEstadoOrderByFechacreacionDesc(Estado estado, Pageable pageable);

}