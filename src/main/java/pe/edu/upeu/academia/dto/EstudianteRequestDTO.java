package pe.edu.upeu.academia.dto;

import jakarta.validation.constraints.*;

public record EstudianteRequestDTO(
        @NotBlank(message = "El codigo es obligatorio")
        @Pattern(regexp = "\\d{8}", message = "El codigo debe ser un numero de 8 digitos")
        String codigo,
        @NotBlank(message = "Los nombres son obligatorios")
        @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres")
        String nombres,
        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres")
        String apellidos,
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe ser valido")
        @Size(max = 150, message = "El email debe tener como maximo 150 caracteres")
        String email,
        @NotNull(message = "El estado es obligatorio")
        Boolean estado,
        @NotNull(message = "La carrera es obligatoria")
        @Positive(message = "El Id de carrera debe ser positivo")
        Long carreraId
) {
    public EstudianteRequestDTO {
        if (nombres != null) nombres = nombres.trim();
        if (apellidos != null) apellidos = apellidos.trim();
    }
}
