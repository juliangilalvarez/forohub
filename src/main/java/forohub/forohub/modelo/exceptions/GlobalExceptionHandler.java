package forohub.forohub.modelo.exceptions;

import forohub.forohub.domain.topico.validaciones.TopicoValidationException;
import forohub.forohub.domain.usuario.validaciones.UsuarioValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorDetail(String referencia, String mensaje){
        public ErrorDetail(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<List<ErrorDetail>> handleNotFound(NoHandlerFoundException e) {
        var errorDetails = new ArrayList<ErrorDetail>();
        errorDetails.add(new ErrorDetail("url", "La URL solicitada no existe"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<List<ErrorDetail>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        var errorDetails = new ArrayList<ErrorDetail>();
        if(e.getMessage().contains("Method parameter 'id':")) {
            errorDetails.add(new ErrorDetail("id", "El identificador debe ser un número entro positivo"));
        } else {
            errorDetails.add(new ErrorDetail(MethodArgumentTypeMismatchException.class.getName(), e.getMessage()));
        }
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<List<ErrorDetail>> handleTypeMismatch(HttpMessageNotReadableException e) {
        var errorDetails = new ArrayList<ErrorDetail>();
        if(e.getMessage().contains("domain.topico.Estado")) {
            errorDetails.add(new ErrorDetail("estado", "El enumerador es inválido"));
        } else {
            errorDetails.add(new ErrorDetail(HttpMessageNotReadableException.class.getName(), e.getMessage()));
        }
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDetail>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        var errorDetails = e.getFieldErrors().stream().map(ErrorDetail::new).toList();
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(TopicoValidationException.class)
    public ResponseEntity<List<ErrorDetail>> handleTopicoValidation(TopicoValidationException e) {
        var errorDetails = new ArrayList<ErrorDetail>();
        errorDetails.add(new ErrorDetail(e.getFieldError()));
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(UsuarioValidationException.class)
    public ResponseEntity<List<ErrorDetail>> handleUsuarioValidation(UsuarioValidationException e) {
        var errorDetails = new ArrayList<ErrorDetail>();
        errorDetails.add(new ErrorDetail(e.getFieldError()));
        return ResponseEntity.badRequest().body(errorDetails);
    }

}