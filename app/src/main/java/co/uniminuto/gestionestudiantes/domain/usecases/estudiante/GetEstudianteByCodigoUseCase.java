package co.uniminuto.gestionestudiantes.domain.usecases.estudiante;

import co.uniminuto.gestionestudiantes.data.models.Estudiante;
import co.uniminuto.gestionestudiantes.domain.repositories.EstudianteRepository;

public class GetEstudianteByCodigoUseCase {

    private final EstudianteRepository estudianteRepository;

    public GetEstudianteByCodigoUseCase(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public Estudiante execute(String codigo) {
        return estudianteRepository.getEstudianteByCodigo(codigo);
    }
}
