package forohub.forohub.domain.topico.validaciones;

import forohub.forohub.domain.topico.Topico;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class TopicoValidationException extends RuntimeException {

    private final FieldError fieldError;

    public TopicoValidationException(String field, String message) {
        super(message);
        this.fieldError = new FieldError(Topico.class.getName(),field,message);
    }

    public FieldError getFieldError() {
        return fieldError;
    }
}