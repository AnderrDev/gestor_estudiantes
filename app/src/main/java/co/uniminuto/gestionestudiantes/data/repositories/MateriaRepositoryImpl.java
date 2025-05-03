package co.uniminuto.gestionestudiantes.data.repositories;

import java.util.List;

import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.data.models.Materia;
import co.uniminuto.gestionestudiantes.domain.repositories.MateriaRepository;

public class MateriaRepositoryImpl implements MateriaRepository {

    private final DatabaseHelper dbHelper;

    public MateriaRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean insertarMateria(Materia materia) {
        return dbHelper.insertarMateria(materia);
    }

    @Override
    public List<Materia> obtenerMaterias() {
        return dbHelper.obtenerMaterias();
    }
}
