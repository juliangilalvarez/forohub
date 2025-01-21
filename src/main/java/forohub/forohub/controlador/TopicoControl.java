package forohub.forohub.controlador;

import forohub.forohub.domain.topico.Estado;
import forohub.forohub.domain.topico.TopicoService;
import forohub.forohub.domain.topico.records.TopicoDatosActualizar;
import forohub.forohub.domain.topico.records.TopicoDatosCrear;
import forohub.forohub.domain.topico.records.TopicoDatosVer;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoControl {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDatosVer> crear(
            @RequestBody @Valid TopicoDatosCrear datosCrear,
            UriComponentsBuilder uriComponentsBuilder) {
        var datosVer = new TopicoDatosVer(topicoService.crear(datosCrear));
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(datosVer.id()).toUri();
        return ResponseEntity.created(url).body(datosVer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDatosVer> ver(@PathVariable() Long id) {
        var datosVer = new TopicoDatosVer(topicoService.ver(id));
        return ResponseEntity.ok(datosVer);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoDatosVer>> listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        var datosListar = topicoService.listarTodos(pageable).map(TopicoDatosVer::new);
        return ResponseEntity.ok(datosListar);
    }

    @GetMapping("/top10")
    public ResponseEntity<Page<TopicoDatosVer>> listarTop10RecientesAbiertos(@PageableDefault(size = 10) Pageable pageable) {
        var datosListar = topicoService.listarTop10Recientes(Estado.ABIERTO,pageable).map(TopicoDatosVer::new);
        return ResponseEntity.ok(datosListar);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TopicoDatosVer> actualizar(
            @RequestBody @Valid TopicoDatosActualizar datosActualizar,
            UriComponentsBuilder uriComponentsBuilder) {
        var datosVer = new TopicoDatosVer(topicoService.actualizar(datosActualizar));
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(datosVer.id()).toUri();
        return ResponseEntity.ok().location(url).body(datosVer);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDatosVer> eliminar(@PathVariable() Long id) {
        topicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}