package pe.edu.upeu.academia.dto;

import jakarta.validation.constraints.*;

public record CursoRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
        String nombre,
        @NotNull(message = "Los creditos son obligatorios")
        @Min(value = 1, message = "Los creditos deben estar entre 1 y 10")
        @Max(value = 10, message = "Los creditos deben estar entre 1 y 10")
        Integer creditos,
        @NotNull(message = "El estado es obligatorio")
        Boolean estado,
        @NotNull(message = "La carrera es obligatoria")
        @Positive(message = "El Id de carrera debe ser positivo")
        Long carreraId
) {
    public CursoRequestDTO {
        if (nombre != null) nombre = nombre.trim();
    }
}
