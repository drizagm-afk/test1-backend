package pe.edu.upeu.academia.dto;

import jakarta.validation.constraints.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pe.edu.upeu.academia.exception.ReglaDeNegocioException;

import java.util.Set;

public record PageRequestDTO(
        @Min(value = 0, message = "El numero de pagina no puede ser negativo")
        int pageNumber,
        @Min(value = 1, message = "El tamaño de pagina debe estar entre 1 y 100")
        @Max(value = 100, message = "El tamaño de pagina debe estar entre 1 y 100")
        int pageSize,
        @NotBlank(message = "El campo a ordenar es obligatorio")
        String sortByField,
        @NotBlank(message = "La direccion a ordenar es obligatoria")
        @Pattern(regexp = "asc|desc", message = "Solo se puede ordenar de forma ascendente y descendente")
        String sortOrder
) {
    private static final Set<String> orders = Set.of("asc", "desc");

    public Pageable toPageable(Set<String> fields) {
        if (!fields.contains(sortByField))
            throw new ReglaDeNegocioException("Solo se puede ordenar por: " + sortByField);

        return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortOrder), sortByField));
    }
}