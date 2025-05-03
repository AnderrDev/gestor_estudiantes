package co.uniminuto.gestionestudiantes.data.repositories;

import java.util.List;

import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.data.models.Estudiante;
import co.uniminuto.gestionestudiantes.domain.repositories.EstudianteRepository;

public class EstudianteRepositoryImpl implements EstudianteRepository {

    private final DatabaseHelper dbHelper;

    public EstudianteRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public boolean insertarEstudiante(Estudiante estudiante) {
        return dbHelper.insertarEstudiante(estudiante);
    }

    @Override
    public List<Estudiante> obtenerEstudiantes() {
        return dbHelper.obtenerEstudiantes();
    }
    @Override
    public Estudiante getEstudianteByCodigo(String codigo) {
        return dbHelper.obtenerEstudiantePorCodigo(codigo);
    }
    @Override
    public boolean deleteEstudianteByCodigo(String codigo) {
        return dbHelper.eliminarEstudiantePorCodigo(codigo);
    }

}
