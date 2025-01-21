package forohub.forohub.controlador;

import forohub.forohub.modelo.seguridad.TokenService;
import forohub.forohub.domain.usuario.UsuarioService;
import forohub.forohub.domain.usuario.Usuario;
import forohub.forohub.domain.usuario.records.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class Usuariocontrol {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioDatosLoginToken> login(@RequestBody @Valid UsuarioDatosLogin datos) {
        var upat = new UsernamePasswordAuthenticationToken(datos.correoelectronico(), datos.contrasena());
        var auth = authenticationManager.authenticate(upat);
        var token = tokenService.crearToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new UsuarioDatosLoginToken(token));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioDatosVer> crear(
            @RequestBody @Valid UsuarioDatosCrear datosCrear,
            UriComponentsBuilder uriComponentsBuilder) {
        var datosVer = new UsuarioDatosVer(usuarioService.crear(datosCrear));
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(datosVer.id()).toUri();
        return ResponseEntity.created(url).body(datosVer);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UsuarioDatosVer> ver(@PathVariable() Long id) {
        var datosVer = new UsuarioDatosVer(usuarioService.ver(id));
        return ResponseEntity.ok(datosVer);
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<UsuarioDatosVer>> listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        var datosListar = usuarioService.listarTodos(pageable).map(UsuarioDatosVer::new);
        return ResponseEntity.ok(datosListar);
    }

    @PutMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UsuarioDatosVer> actualizar(
            @RequestBody @Valid UsuarioDatosActualizar datosActualizar,
            UriComponentsBuilder uriComponentsBuilder) {
        var datosVer = new UsuarioDatosVer(usuarioService.actualizar(datosActualizar));
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(datosVer.id()).toUri();
        return ResponseEntity.ok().location(url).body(datosVer);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UsuarioDatosVer> eliminar(@PathVariable() Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}