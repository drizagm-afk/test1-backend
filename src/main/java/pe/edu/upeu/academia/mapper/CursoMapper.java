package pe.edu.upeu.academia.mapper;

import pe.edu.upeu.academia.dto.CursoRequestDTO;
import pe.edu.upeu.academia.dto.CursoResponseDTO;
import pe.edu.upeu.academia.entity.Carrera;
import pe.edu.upeu.academia.entity.Curso;

public class CursoMapper {
    public static Curso fromRequest(CursoRequestDTO request, Carrera carrera) {
        return fromRequest(request, carrera, new Curso());
    }

    public static Curso fromRequest(CursoRequestDTO request, Carrera carrera, Curso curso) {
        curso.setNombre(request.nombre());
        curso.setCreditos(request.creditos());
        curso.setEstado(request.estado());
        curso.setCarrera(carrera);
        return curso;
    }

    public static CursoResponseDTO toResponse(Curso curso) {
        var carrera = curso.getCarrera();

        return new CursoResponseDTO(
                curso.getId(),
                curso.getNombre(),
                curso.getCreditos(),
                curso.getEstado(),
                carrera.getId(),
                carrera.getNombre(),
                curso.getFechaCreacion(),
                curso.getFechaModificacion()
        );
    }
}
