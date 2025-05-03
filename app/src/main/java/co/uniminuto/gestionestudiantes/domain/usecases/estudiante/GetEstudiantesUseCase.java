package co.uniminuto.gestionestudiantes.domain.usecases.estudiante;

import java.util.List;
import co.uniminuto.gestionestudiantes.data.models.Estudiante;
import co.uniminuto.gestionestudiantes.domain.repositories.EstudianteRepository;

public class GetEstudiantesUseCase {
    private final EstudianteRepository repository;

    public GetEstudiantesUseCase(EstudianteRepository repository) {
        this.repository = repository;
    }

    public List<Estudiante> execute() {
        return repository.obtenerEstudiantes();
    }
}
