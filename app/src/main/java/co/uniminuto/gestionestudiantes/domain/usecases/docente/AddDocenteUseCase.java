package co.uniminuto.gestionestudiantes.domain.usecases.docente;

import co.uniminuto.gestionestudiantes.domain.repositories.DocenteRepository;
import co.uniminuto.gestionestudiantes.data.models.Docente;

public class AddDocenteUseCase {

    private final DocenteRepository repository;

    public AddDocenteUseCase(DocenteRepository repository) {
        this.repository = repository;
    }

    public boolean execute(Docente docente) {
        return repository.insertarDocente(docente);
    }
}
