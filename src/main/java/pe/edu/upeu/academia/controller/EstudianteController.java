package pe.edu.upeu.academia.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.academia.dto.EstudianteRequestDTO;
import pe.edu.upeu.academia.dto.EstudianteResponseDTO;
import pe.edu.upeu.academia.dto.PageRequestDTO;
import pe.edu.upeu.academia.dto.PageResponseDTO;
import pe.edu.upeu.academia.service.service.EstudianteService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/estudiantes")
public class EstudianteController {
    private final EstudianteService service;

    @GetMapping
    public ResponseEntity<PageResponseDTO<EstudianteResponseDTO>> listPage(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio,
            @RequestParam(defaultValue = "nombre") String ordenarPor,
            @RequestParam(defaultValue = "asc") String direccion
    ) {
        return ResponseEntity.ok(service.findPage(new PageRequestDTO(
                pagina, tamanio, ordenarPor, direccion
        )));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> listById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EstudianteResponseDTO> create(
            @Valid @RequestBody EstudianteRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteRequestDTO request
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
