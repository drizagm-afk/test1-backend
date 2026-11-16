package pe.edu.upeu.academia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CarreraRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 a 100 caracteres")
        String nombre,
        @Size(max = 250, message = "La descripcion debe tener como maximo 250 caracteres")
        String descripcion,
        @NotNull(message = "El estado es obligatorio")
        Boolean estado
) {
    public CarreraRequestDTO {
        if (nombre != null) nombre = nombre.trim();
    }
}