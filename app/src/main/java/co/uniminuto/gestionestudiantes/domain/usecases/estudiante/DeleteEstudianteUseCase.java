package co.uniminuto.gestionestudiantes.domain.usecases.estudiante;

import androidx.annotation.NonNull;

import co.uniminuto.gestionestudiantes.domain.repositories.EstudianteRepository;

/**
 * Caso de uso para eliminar un estudiante por su código.
 */
public class DeleteEstudianteUseCase {

    private final EstudianteRepository estudianteRepository;

    public DeleteEstudianteUseCase(@NonNull EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * Ejecuta la eliminación del estudiante.
     *
     * @param codigo Código único del estudiante a eliminar.
     * @return true si se eliminó correctamente, false si no se encontró.
     */
    public boolean execute(@NonNull String codigo) {
        return estudianteRepository.deleteEstudianteByCodigo(codigo);
    }
}
