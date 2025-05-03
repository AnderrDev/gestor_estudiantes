package co.uniminuto.gestionestudiantes.domain.repositories;

import java.util.List;
import co.uniminuto.gestionestudiantes.data.models.Estudiante;

public interface EstudianteRepository {
    boolean insertarEstudiante(Estudiante estudiante);
    List<Estudiante> obtenerEstudiantes();
    Estudiante getEstudianteByCodigo(String codigo);
    boolean deleteEstudianteByCodigo(String codigo);

}
