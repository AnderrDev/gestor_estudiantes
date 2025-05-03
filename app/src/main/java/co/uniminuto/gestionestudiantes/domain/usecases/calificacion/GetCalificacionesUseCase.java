package co.uniminuto.gestionestudiantes.domain.usecases.calificacion;

import java.util.List;

import co.uniminuto.gestionestudiantes.domain.repositories.CalificacionRepository;
import co.uniminuto.gestionestudiantes.data.models.Calificacion;

public class GetCalificacionesUseCase {

    private final CalificacionRepository repository;

    public GetCalificacionesUseCase(CalificacionRepository repository) {
        this.repository = repository;
    }

    public List<Calificacion> execute() {
        return repository.obtenerCalificaciones();
    }
}
