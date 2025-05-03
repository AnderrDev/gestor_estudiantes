package co.uniminuto.gestionestudiantes.domain.repositories;

import java.util.List;
import co.uniminuto.gestionestudiantes.data.models.Materia;

public interface MateriaRepository {

    boolean insertarMateria(Materia materia);

    List<Materia> obtenerMaterias();
}
