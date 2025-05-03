package co.uniminuto.gestionestudiantes.domain.usecases.estudiante;

import java.util.List;

import co.uniminuto.gestionestudiantes.core.network.EstudianteApiService;
import co.uniminuto.gestionestudiantes.data.models.Estudiante;

public class LoadEstudiantesFromApiUseCase {

    private final EstudianteApiService apiService;

    public LoadEstudiantesFromApiUseCase(EstudianteApiService apiService) {
        this.apiService = apiService;
    }

    public List<Estudiante> execute() throws Exception {
        return apiService.getEstudiantes();
    }
}
