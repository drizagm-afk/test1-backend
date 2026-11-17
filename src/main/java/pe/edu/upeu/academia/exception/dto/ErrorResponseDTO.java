package pe.edu.upeu.academia.exception.dto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
    public static ResponseEntity<ErrorResponseDTO> build(
            HttpStatus status, String message, HttpServletRequest request, Map<String, String> validationErrors
    ) {
        return ResponseEntity.status(status).body(new ErrorResponseDTO(
                LocalDateTime.now(), status.value(), status.name(), message, request.getRequestURI(), validationErrors
        ));
    }
}