package co.uniminuto.gestionestudiantes.domain.usecases.materia;

import co.uniminuto.gestionestudiantes.domain.repositories.MateriaRepository;
import co.uniminuto.gestionestudiantes.data.models.Materia;

public class AddMateriaUseCase {

    private final MateriaRepository repository;

    public AddMateriaUseCase(MateriaRepository repository) {
        this.repository = repository;
    }

    public boolean execute(Materia materia) {
        return repository.insertarMateria(materia);
    }
}
