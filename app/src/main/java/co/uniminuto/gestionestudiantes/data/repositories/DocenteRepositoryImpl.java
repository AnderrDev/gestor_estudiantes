package co.uniminuto.gestionestudiantes.data.repositories;

import java.util.List;

import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.data.models.Docente;
import co.uniminuto.gestionestudiantes.domain.repositories.DocenteRepository;

public class DocenteRepositoryImpl implements DocenteRepository {

    private final DatabaseHelper dbHelper;

    public DocenteRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public boolean insertarDocente(Docente docente) {
        return dbHelper.insertarDocente(docente);
    }

    @Override
    public List<Docente> obtenerDocentes() {
        return dbHelper.obtenerDocentes();
    }
}
