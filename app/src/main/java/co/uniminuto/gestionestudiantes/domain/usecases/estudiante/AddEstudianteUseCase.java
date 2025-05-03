package co.uniminuto.gestionestudiantes.domain.usecases.estudiante;

import co.uniminuto.gestionestudiantes.data.models.Estudiante;
import co.uniminuto.gestionestudiantes.domain.repositories.EstudianteRepository;

public class AddEstudianteUseCase {
    private final EstudianteRepository repository;

    public AddEstudianteUseCase(EstudianteRepository repository) {
        this.repository = repository;
    }

    public boolean execute(Estudiante estudiante) {
        return repository.insertarEstudiante(estudiante);
    }
}
