package pe.edu.upeu.academia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.academia.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNombreIgnoreCaseAndCarrera_Id(
            String nombre,
            Long carreraId
    );

    boolean existsByNombreIgnoreCaseAndCarrera_IdAndIdNot(
            String nombre,
            Long carreraId,
            Long id
    );
}
