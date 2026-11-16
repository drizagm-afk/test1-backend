package pe.edu.upeu.academia.mapper;

import pe.edu.upeu.academia.dto.EstudianteRequestDTO;
import pe.edu.upeu.academia.dto.EstudianteResponseDTO;
import pe.edu.upeu.academia.entity.Carrera;
import pe.edu.upeu.academia.entity.Estudiante;

public class EstudianteMapper {
    public static Estudiante fromRequest(EstudianteRequestDTO request, Carrera carrera) {
        return fromRequest(request, carrera, new Estudiante());
    }

    public static Estudiante fromRequest(EstudianteRequestDTO request, Carrera carrera, Estudiante estudiante) {
        estudiante.setCodigo(request.codigo());
        estudiante.setNombres(request.nombres());
        estudiante.setApellidos(request.apellidos());
        estudiante.setEmail(request.email());
        estudiante.setEstado(request.estado());
        estudiante.setCarrera(carrera);
        return estudiante;
    }

    public static EstudianteResponseDTO toResponse(Estudiante estudiante) {
        var carrera = estudiante.getCarrera();

        return new EstudianteResponseDTO(
                estudiante.getId(),
                estudiante.getCodigo(),
                estudiante.getNombres(),
                estudiante.getApellidos(),
                estudiante.getEmail(),
                estudiante.getEstado(),
                carrera.getId(),
                carrera.getNombre(),
                estudiante.getFechaCreacion(),
                estudiante.getFechaModificacion()
        );
    }
}