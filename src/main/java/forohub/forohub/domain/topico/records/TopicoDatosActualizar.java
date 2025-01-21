package forohub.forohub.domain.topico.records;



import forohub.forohub.domain.topico.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TopicoDatosActualizar(

        @NotNull(message = "El tópico no puede estar vacío")
        @Positive(message = "El tópico debe ser un número entro positivo")
        Long id,

        @NotBlank(message = "El título no puede estar vacío")
        @Size(max = 100, message = "El título debe tener entre 1 y 100 caracteres")
        String titulo,

        @NotBlank(message = "El mensaje no puede estar vacío")
        @Size(max = 500, message = "El mensaje debe tener entre 1 y 500 caracteres")
        String mensaje,

        @NotNull(message = "El estado no puede estar vacío")
        Estado estado

) {
}