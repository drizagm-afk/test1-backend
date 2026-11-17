package pe.edu.upeu.academia.service.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import pe.edu.upeu.academia.dto.EstudianteRequestDTO;
import pe.edu.upeu.academia.dto.EstudianteResponseDTO;
import pe.edu.upeu.academia.dto.PageRequestDTO;
import pe.edu.upeu.academia.dto.PageResponseDTO;
import pe.edu.upeu.academia.service.generic.CrudService;

@Validated
public interface EstudianteService extends CrudService<EstudianteRequestDTO, EstudianteResponseDTO, Long> {
    PageResponseDTO<EstudianteResponseDTO> findPage(@Valid PageRequestDTO pageRequest);
}
