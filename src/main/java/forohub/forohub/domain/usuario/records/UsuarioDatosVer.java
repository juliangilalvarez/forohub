package forohub.forohub.domain.usuario.records;

import forohub.forohub.domain.usuario.Usuario;

import java.util.List;
import forohub.forohub.domain.perfil.Perfil;

public record UsuarioDatosVer(
        Long id,
        String nombre,
        String correoelectronico,
        List<Long> pefiles
) {

    public UsuarioDatosVer(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoelectronico(),
                usuario.getPerfiles().stream().map(Perfil::getId).toList()
        );
    }

}