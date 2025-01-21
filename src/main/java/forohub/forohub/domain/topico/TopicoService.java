package forohub.forohub.domain.topico;


import forohub.forohub.domain.curso.CursoRepository;
import forohub.forohub.domain.topico.records.TopicoDatosActualizar;
import forohub.forohub.domain.topico.records.TopicoDatosCrear;
import forohub.forohub.domain.topico.validaciones.TopicoValidationException;
import forohub.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TopicoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Topico crear(TopicoDatosCrear datos){

        if(!usuarioRepository.existsById(datos.autor())){
            throw new TopicoValidationException("autor","No existe un usuario con el identificador " + datos.autor());
        }

        if(!cursoRepository.existsById(datos.curso())){
            throw new TopicoValidationException("curso","No existe un curso con el identificador " + datos.curso());
        }

        if(topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new TopicoValidationException("topico","Ya existe un tópico con igual título y mensaje");
        }

        var usuario = usuarioRepository.getReferenceById(datos.autor());
        var curso = cursoRepository.getReferenceById(datos.curso());
        var topico = new Topico(datos.titulo(), datos.mensaje(), usuario, curso);

        topicoRepository.save(topico);

        return topico;

    }

    public Topico ver(Long id){
        if(!topicoRepository.existsById(id)){
            throw new TopicoValidationException("id","No existe un tópico con el identificador " + id);
        }
        return topicoRepository.getReferenceById(id);
    }

    public Page<Topico> listarTodos(Pageable pageable) {
        return topicoRepository.findByOrderByFechacreacionDesc(pageable);
    }

    public Page<Topico> listarTop10Recientes(Estado estado, Pageable pageable) {
        return topicoRepository.findTop10ByEstadoOrderByFechacreacionDesc(estado, pageable);
    }

    public Topico actualizar(TopicoDatosActualizar datos){

        if(!topicoRepository.existsById(datos.id())){
            throw new TopicoValidationException("topico","No existe un tópico con el identificador " + datos.id());
        }

        var topico = topicoRepository.getReferenceById(datos.id());
        var igualTitulo = topico.getTitulo().equals(datos.titulo());
        var igualMensaje = topico.getMensaje().equals(datos.mensaje());
        var igualEstado = topico.getEstado() == datos.estado();

        if (igualTitulo && igualMensaje && igualEstado) {
            throw new TopicoValidationException("topico","Los datos suministrados son iguales a los existentes");
        }

        if ((!igualTitulo) || (!igualMensaje)) {
            if (topicoRepository.existsByTituloAndMensajeAndIdNot(datos.titulo(), datos.mensaje(), datos.id())) {
                throw new TopicoValidationException("topico", "Ya existe un tópico con igual título y mensaje");
            }
        }

        topico.actualizar(datos);
        topicoRepository.save(topico);

        return topico;

    }

    public void eliminar(Long id){
        if(!topicoRepository.existsById(id)){
            throw new TopicoValidationException("id","No existe un tópico con el identificador " + id);
        }
        topicoRepository.deleteById(id);
    }

}