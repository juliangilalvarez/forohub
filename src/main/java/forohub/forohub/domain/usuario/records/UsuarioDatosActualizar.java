package forohub.forohub.domain.usuario.records;

import jakarta.validation.constraints.*;

public record UsuarioDatosActualizar(

        @NotNull(message = "El identificador no puede estar vacío")
        @Positive(message = "El identificador debe ser un número entro positivo")
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min= 5, max = 100, message = "El nombre debe tener entre 5 y 100 caracteres")
        String nombre,

        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Size(min= 5, max = 100, message = "El correo electrónico debe tener entre 5 y 100 caracteres")
        @Email(message = "El correo electrónico es inválido")
        String correoelectronico,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min= 5, max = 20, message = "La contraseña debe tener entre 5 y 20 caracteres")
        String contrasena

) {
}