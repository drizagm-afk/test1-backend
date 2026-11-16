package pe.edu.upeu.academia.dto;

import java.time.LocalDateTime;

public record CursoResponseDTO(
        Long id,
        String nombre,
        Integer creditos,
        Boolean estado,
        Long carreraId,
        String carreraNombre,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {
}