package forohub.forohub.domain.usuario.validaciones;


import forohub.forohub.domain.topico.Topico;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class UsuarioValidationException extends RuntimeException {

    private final FieldError fieldError;

    public UsuarioValidationException(String field, String message) {
        super(message);
        this.fieldError = new FieldError(Topico.class.getName(),field,message);
    }

    public FieldError getFieldError() {
        return fieldError;
    }
}