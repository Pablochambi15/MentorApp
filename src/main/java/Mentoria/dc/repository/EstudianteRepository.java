package Mentoria.dc.repository;

import Mentoria.dc.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // Buscar estudiante por el email del usuario relacionado
    Optional<Estudiante> findByUser_Email(String email);
}
