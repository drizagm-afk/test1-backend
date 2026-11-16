package pe.edu.upeu.academia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upeu.academia.entity.Carrera;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    @Query("""
    SELECT COUNT(c) > 0 FROM Carrera c
    WHERE c.id = :id AND (
        EXISTS (SELECT 1 FROM Curso cu WHERE cu.carrera = c AND cu.estado = true) OR
        EXISTS (SELECT 1 FROM Estudiante e WHERE e.carrera = c AND e.estado = true)
    )
    """)
    boolean hasActiveCursosOrEstudiantes(Long id);
}