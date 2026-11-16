package pe.edu.upeu.academia.mapper;

import pe.edu.upeu.academia.dto.CarreraRequestDTO;
import pe.edu.upeu.academia.dto.CarreraResponseDTO;
import pe.edu.upeu.academia.entity.Carrera;

public class CarreraMapper {
    public static Carrera fromRequest(CarreraRequestDTO request) {
        return fromRequest(request, new Carrera());
    }

    public static Carrera fromRequest(CarreraRequestDTO request, Carrera carrera) {
        carrera.setNombre(request.nombre());
        carrera.setDescripcion(request.descripcion());
        carrera.setEstado(request.estado());
        return carrera;
    }

    public static CarreraResponseDTO toResponse(Carrera carrera) {
        return new CarreraResponseDTO(
                carrera.getId(),
                carrera.getNombre(),
                carrera.getDescripcion(),
                carrera.getEstado(),
                carrera.getFechaCreacion(),
                carrera.getFechaModificacion()
        );
    }
}
