package pe.edu.upeu.academia.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.upeu.academia.exception.dto.ErrorResponseDTO;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handle(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        var errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
                e -> e.getField(), e -> e.getDefaultMessage()
        ));

        return ErrorResponseDTO.build(HttpStatus.BAD_REQUEST, "Error de Validación", request, errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handle(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        var errors = ex.getConstraintViolations().stream().collect(Collectors.toMap(
                e -> e.getPropertyPath().toString(), e -> e.getMessage()
        ));

        return ErrorResponseDTO.build(HttpStatus.BAD_REQUEST, "Error de Validación", request, errors);
    }

    // 404
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handle(
            RecursoNoEncontradoException ex,
            HttpServletRequest request
    ) {
        return ErrorResponseDTO.build(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    // 409
    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponseDTO> handle(
            ReglaDeNegocioException ex,
            HttpServletRequest request
    ) {
        return ErrorResponseDTO.build(HttpStatus.CONFLICT, ex.getMessage(), request, null);
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handle(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error(ex.getMessage());
        log.error(Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toString());

        return ErrorResponseDTO.build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request, null);
    }
}
