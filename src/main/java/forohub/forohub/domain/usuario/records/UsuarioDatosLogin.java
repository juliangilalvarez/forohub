package forohub.forohub.domain.usuario.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioDatosLogin(

        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Size(max = 100, message = "El correo electrónico debe tener entre 1 y 100 caracteres")
        String correoelectronico,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(max = 100, message = "La contraseña debe tener entre 1 y 100 caracteres")
        String contrasena

) {
}