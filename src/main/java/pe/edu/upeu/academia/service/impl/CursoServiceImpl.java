package pe.edu.upeu.academia.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pe.edu.upeu.academia.dto.CursoRequestDTO;
import pe.edu.upeu.academia.dto.CursoResponseDTO;
import pe.edu.upeu.academia.dto.PageRequestDTO;
import pe.edu.upeu.academia.dto.PageResponseDTO;
import pe.edu.upeu.academia.entity.Carrera;
import pe.edu.upeu.academia.exception.RecursoNoEncontradoException;
import pe.edu.upeu.academia.exception.ReglaDeNegocioException;
import pe.edu.upeu.academia.mapper.CursoMapper;
import pe.edu.upeu.academia.repository.CarreraRepository;
import pe.edu.upeu.academia.repository.CursoRepository;
import pe.edu.upeu.academia.service.service.CursoService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {
    private final CursoRepository repository;
    private final CarreraRepository carreraRepository;

    private final Set<String> fields = Set.of("id", "nombre", "creditos", "estado");

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<CursoResponseDTO> findPage(PageRequestDTO pageRequest) {
        var pageable = pageRequest.toPageable(fields);

        return PageResponseDTO.fromPage(repository.findAll(pageable).map(CursoMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(CursoMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(CursoMapper::toResponse)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado: " + id));
    }

    @Override
    @Transactional
    public CursoResponseDTO create(CursoRequestDTO request) {
        var carrera = carreraById(request.carreraId());

        var nombre = request.nombre();
        if (repository.existsByNombreIgnoreCaseAndCarrera_Id(nombre, carrera.getId()))
            throw new ReglaDeNegocioException("Nombre de curso ya existe: " + nombre);

        var curso = CursoMapper.fromRequest(request, carrera);
        curso = repository.save(curso);

        return CursoMapper.toResponse(curso);
    }

    @Override
    @Transactional
    public CursoResponseDTO update(Long id, CursoRequestDTO request) {
        var curso = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado: " + id));

        var carrera = carreraById(request.carreraId());

        var nombre = request.nombre().trim();
        if (repository.existsByNombreIgnoreCaseAndCarrera_IdAndIdNot(nombre, carrera.getId(), id))
            throw new ReglaDeNegocioException("Nombre de curso ya existe: " + nombre);

        CursoMapper.fromRequest(request, carrera, curso);
        curso = repository.saveAndFlush(curso);

        return CursoMapper.toResponse(curso);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var curso = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado: " + id));

        if (!curso.getEstado())
            throw new ReglaDeNegocioException("No se puede desactivar un curso inactivo");

        curso.setEstado(false);
        repository.save(curso);
    }

    // UTILS
    private Carrera carreraById(Long id) {
        var carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada: " + id));

        if (!carrera.getEstado())
            throw new ReglaDeNegocioException("Carrera esta desactivada: " + id);

        return carrera;
    }
}
