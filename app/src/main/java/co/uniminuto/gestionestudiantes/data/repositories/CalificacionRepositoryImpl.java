package co.uniminuto.gestionestudiantes.data.repositories;

import java.util.List;

import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.data.models.Calificacion;
import co.uniminuto.gestionestudiantes.domain.repositories.CalificacionRepository;

public class CalificacionRepositoryImpl implements CalificacionRepository {

    private final DatabaseHelper dbHelper;

    public CalificacionRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public boolean insertarCalificacion(Calificacion calificacion) {
        return dbHelper.insertarCalificacion(calificacion);
    }

    @Override
    public List<Calificacion> obtenerCalificaciones() {
        return dbHelper.obtenerCalificaciones();
    }
}
