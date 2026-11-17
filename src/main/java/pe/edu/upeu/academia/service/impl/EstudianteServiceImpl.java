package pe.edu.upeu.academia.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pe.edu.upeu.academia.dto.EstudianteRequestDTO;
import pe.edu.upeu.academia.dto.EstudianteResponseDTO;
import pe.edu.upeu.academia.dto.PageRequestDTO;
import pe.edu.upeu.academia.dto.PageResponseDTO;
import pe.edu.upeu.academia.entity.Carrera;
import pe.edu.upeu.academia.exception.RecursoNoEncontradoException;
import pe.edu.upeu.academia.exception.ReglaDeNegocioException;
import pe.edu.upeu.academia.mapper.EstudianteMapper;
import pe.edu.upeu.academia.repository.CarreraRepository;
import pe.edu.upeu.academia.repository.EstudianteRepository;
import pe.edu.upeu.academia.service.service.EstudianteService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {
    private final EstudianteRepository repository;
    private final CarreraRepository carreraRepository;

    private final Set<String> fields = Set.of("id", "codigo", "nombres", "apellidos", "email");

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<EstudianteResponseDTO> findPage(PageRequestDTO pageRequest) {
        var pageable = pageRequest.toPageable(fields);

        return PageResponseDTO.fromPage(repository.findAll(pageable).map(EstudianteMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(EstudianteMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(EstudianteMapper::toResponse)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado: " + id));
    }

    @Override
    @Transactional
    public EstudianteResponseDTO create(EstudianteRequestDTO request) {
        var carrera = carreraById(request.carreraId());

        var codigo = request.codigo();
        if (repository.existsByCodigo(codigo))
            throw new ReglaDeNegocioException("Codigo de estudiante ya existe: " + codigo);

        var email = request.email();
        if (repository.existsByEmailIgnoreCase(email))
            throw new ReglaDeNegocioException("Email ya existe: " + email);

        var estudiante = EstudianteMapper.fromRequest(request, carrera);
        estudiante = repository.save(estudiante);

        return EstudianteMapper.toResponse(estudiante);
    }

    @Override
    @Transactional
    public EstudianteResponseDTO update(Long id, EstudianteRequestDTO request) {
        var estudiante = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado: " + id));

        var carrera = carreraById(request.carreraId());

        var codigo = request.codigo();
        if (repository.existsByCodigoAndIdNot(codigo, id))
            throw new ReglaDeNegocioException("Codigo de estudiante ya existe: " + codigo);

        var email = request.email();
        if (repository.existsByEmailIgnoreCaseAndIdNot(email, id))
            throw new ReglaDeNegocioException("Email ya existe: " + email);

        EstudianteMapper.fromRequest(request, carrera, estudiante);
        estudiante = repository.saveAndFlush(estudiante);

        return EstudianteMapper.toResponse(estudiante);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var estudiante = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado: " + id));

        if (!estudiante.getEstado())
            throw new ReglaDeNegocioException("No se puede desactivar un estudiante inactivo");

        estudiante.setEstado(false);
        repository.save(estudiante);
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
