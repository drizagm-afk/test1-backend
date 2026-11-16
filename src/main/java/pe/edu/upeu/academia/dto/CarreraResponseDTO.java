package pe.edu.upeu.academia.dto;

import java.time.LocalDateTime;

public record CarreraResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        Boolean estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {
}