package pe.edu.upeu.academia.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.academia.dto.CarreraRequestDTO;
import pe.edu.upeu.academia.dto.CarreraResponseDTO;
import pe.edu.upeu.academia.service.service.CarreraService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carreras")
public class CarreraController {
    private final CarreraService service;

    @GetMapping
    public ResponseEntity<List<CarreraResponseDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> listById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CarreraResponseDTO> create(
            @Valid @RequestBody CarreraRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CarreraRequestDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(
            @PathVariable Long id
    ) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
