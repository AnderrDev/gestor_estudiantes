package co.uniminuto.gestionestudiantes.domain.usecases.calificacion;

import co.uniminuto.gestionestudiantes.domain.repositories.CalificacionRepository;
import co.uniminuto.gestionestudiantes.data.models.Calificacion;

public class AddCalificacionUseCase {

    private final CalificacionRepository repository;

    public AddCalificacionUseCase(CalificacionRepository repository) {
        this.repository = repository;
    }

    public boolean execute(Calificacion calificacion) {
        return repository.insertarCalificacion(calificacion);
    }
}
