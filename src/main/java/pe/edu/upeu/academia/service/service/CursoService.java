package pe.edu.upeu.academia.service.service;

import jakarta.validation.Valid;
import pe.edu.upeu.academia.dto.CursoRequestDTO;
import pe.edu.upeu.academia.dto.CursoResponseDTO;
import pe.edu.upeu.academia.dto.PageRequestDTO;
import pe.edu.upeu.academia.dto.PageResponseDTO;
import pe.edu.upeu.academia.service.generic.CrudService;

import java.util.List;

public interface CursoService extends CrudService<CursoRequestDTO, CursoResponseDTO, Long> {
    PageResponseDTO<CursoResponseDTO> findPage(PageRequestDTO pageRequest);
}
