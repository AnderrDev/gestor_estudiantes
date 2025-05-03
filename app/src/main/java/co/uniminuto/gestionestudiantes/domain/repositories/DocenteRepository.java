package co.uniminuto.gestionestudiantes.domain.repositories;

import java.util.List;
import co.uniminuto.gestionestudiantes.data.models.Docente;

public interface DocenteRepository {

    boolean insertarDocente(Docente docente);

    List<Docente> obtenerDocentes();
}
