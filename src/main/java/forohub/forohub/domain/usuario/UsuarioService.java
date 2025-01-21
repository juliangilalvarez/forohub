package forohub.forohub.domain.usuario;

import forohub.forohub.domain.perfil.PerfilRepository;
import forohub.forohub.domain.usuario.records.UsuarioDatosActualizar;
import forohub.forohub.domain.usuario.validaciones.UsuarioValidationException;
import forohub.forohub.domain.usuario.records.UsuarioDatosCrear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.getReferenceByCorreoelectronico(username);
    }

    public Usuario crear(UsuarioDatosCrear datos){

        if(!perfilRepository.existsById(datos.perfil())){
            throw new UsuarioValidationException("perfil","No existe un perfil con el identificador " + datos.perfil());
        }

        if(usuarioRepository.existsByNombre(datos.nombre())) {
            throw new UsuarioValidationException("nombre","Ya existe un usuario con el mismo nombre");
        }

        if(usuarioRepository.existsByCorreoelectronico(datos.correoelectronico())) {
            throw new UsuarioValidationException("correoelectronico","Ya existe un usuario con el mismo correo electrónico");
        }

        var contrasena = passwordEncoder.encode(datos.contrasena());
        var perfil = perfilRepository.getReferenceById(datos.perfil());
        var usuario = new Usuario(datos.nombre(), datos.correoelectronico(), contrasena, perfil);

        usuarioRepository.save(usuario);

        return usuario;

    }

    public Usuario ver(Long id){
        if(!usuarioRepository.existsById(id)){
            throw new UsuarioValidationException("id","No existe un usuario con el identificador " + id);
        }
        return usuarioRepository.getReferenceById(id);
    }

    public Page<Usuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findByOrderByNombre(pageable);
    }

    public Usuario actualizar(UsuarioDatosActualizar datos){

        if (!usuarioRepository.existsById(datos.id())){
            throw new UsuarioValidationException("id","No existe un usuario con el identificador " + datos.id());
        }

        var usuario = usuarioRepository.getReferenceById(datos.id());
        var contrasena = usuario.getContrasena();
        var igualNombre = usuario.getNombre().equals(datos.nombre());
        var igualCorreo = usuario.getCorreoelectronico().equals(datos.correoelectronico());
        var igualCSena =  passwordEncoder.matches(datos.contrasena(), contrasena);

        if (igualNombre && igualCorreo && igualCSena) {
            throw new UsuarioValidationException("usuario","Los datos suministrados son iguales a los existentes");
        }

        if (!igualNombre) {
            if (usuarioRepository.existsByNombreAndIdNot(datos.nombre(), datos.id())) {
                throw new UsuarioValidationException("nombre","Ya existe un usuario con el mismo nombre");
            }
        }

        if (!igualCorreo) {
            if (usuarioRepository.existsByCorreoelectronicoAndIdNot(datos.correoelectronico(), datos.id())) {
                throw new UsuarioValidationException("correoelectronico","Ya existe un usuario con el mismo correo electrónico");
            }
        }

        if (!igualCSena) {
            contrasena = passwordEncoder.encode(datos.contrasena());
        }

        usuario.actualizar(datos.nombre(), datos.correoelectronico(), contrasena);
        usuarioRepository.save(usuario);

        return usuario;

    }

    public void eliminar(Long id) {
        var resultado = usuarioRepository.findById(id);
        if (resultado.isEmpty()) {
            throw new UsuarioValidationException("id", "No existe un usuario con el identificador " + id);
        } else if (!resultado.get().getTopicos().isEmpty()) {
            throw new UsuarioValidationException("id", "No es posible eliminar un usuario con tópicos");
        }
        usuarioRepository.deleteById(id);
    }

}