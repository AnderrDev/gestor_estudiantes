package co.uniminuto.gestionestudiantes.domain.usecases.materia;

import java.util.List;

import co.uniminuto.gestionestudiantes.domain.repositories.MateriaRepository;
import co.uniminuto.gestionestudiantes.data.models.Materia;

public class GetMateriasUseCase {

    private final MateriaRepository repository;

    public GetMateriasUseCase(MateriaRepository repository) {
        this.repository = repository;
    }

    public List<Materia> execute() {
        return repository.obtenerMaterias();
    }
}
