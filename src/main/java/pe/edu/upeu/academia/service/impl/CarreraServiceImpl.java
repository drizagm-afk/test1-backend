package pe.edu.upeu.academia.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.academia.dto.CarreraRequestDTO;
import pe.edu.upeu.academia.dto.CarreraResponseDTO;
import pe.edu.upeu.academia.exception.RecursoNoEncontradoException;
import pe.edu.upeu.academia.exception.ReglaDeNegocioException;
import pe.edu.upeu.academia.mapper.CarreraMapper;
import pe.edu.upeu.academia.repository.CarreraRepository;
import pe.edu.upeu.academia.service.service.CarreraService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarreraServiceImpl implements CarreraService {
    private final CarreraRepository repository;

    @Override
    public List<CarreraResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(CarreraMapper::toResponse).toList();
    }

    @Override
    public CarreraResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(CarreraMapper::toResponse)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada: " + id));
    }

    @Override
    @Transactional
    public CarreraResponseDTO create(CarreraRequestDTO request) {
        var nombre = request.nombre();
        if (repository.existsByNombreIgnoreCase(nombre))
            throw new ReglaDeNegocioException("Nombre de Carrera ya existe: " + nombre);

        var carrera = CarreraMapper.fromRequest(request);
        carrera = repository.save(carrera);

        return CarreraMapper.toResponse(carrera);
    }

    @Override
    @Transactional
    public CarreraResponseDTO update(Long id, CarreraRequestDTO request) {
        var carrera = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada: " + id));

        var nombre = request.nombre();
        if (repository.existsByNombreIgnoreCaseAndIdNot(nombre, id))
            throw new ReglaDeNegocioException("Nombre de Carrera ya existe: " + nombre);

        CarreraMapper.fromRequest(request, carrera);
        carrera = repository.saveAndFlush(carrera);

        return CarreraMapper.toResponse(carrera);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var carrera = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada: " + id));

        if (!carrera.getEstado())
            throw new ReglaDeNegocioException("No se puede desactivar una Carrera inactiva");

        if (repository.hasActiveCursosOrEstudiantes(id))
            throw new ReglaDeNegocioException("No se puede desactivar una Carrera con Cursos o Estudiantes activos: " + id);

        carrera.setEstado(false);
        repository.save(carrera);
    }
}
