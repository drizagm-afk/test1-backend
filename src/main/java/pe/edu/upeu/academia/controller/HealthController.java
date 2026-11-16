package pe.edu.upeu.academia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthController {
    record HealthResponseDTO(String status, String application) {
    }

    @GetMapping("/health")
    public ResponseEntity<HealthResponseDTO> health() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new HealthResponseDTO("UP", "AcademiaBackend"));
    }
}
