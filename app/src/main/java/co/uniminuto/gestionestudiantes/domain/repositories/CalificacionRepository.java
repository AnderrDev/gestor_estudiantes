package co.uniminuto.gestionestudiantes.domain.repositories;

import java.util.List;
import co.uniminuto.gestionestudiantes.data.models.Calificacion;

public interface CalificacionRepository {

    boolean insertarCalificacion(Calificacion calificacion);

    List<Calificacion> obtenerCalificaciones();
}
