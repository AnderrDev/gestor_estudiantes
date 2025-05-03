package co.uniminuto.gestionestudiantes.domain.usecases.docente;

import java.util.List;

import co.uniminuto.gestionestudiantes.domain.repositories.DocenteRepository;
import co.uniminuto.gestionestudiantes.data.models.Docente;

public class GetDocentesUseCase {

    private final DocenteRepository repository;

    public GetDocentesUseCase(DocenteRepository repository) {
        this.repository = repository;
    }

    public List<Docente> execute() {
        return repository.obtenerDocentes();
    }
}
