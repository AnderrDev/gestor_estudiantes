package co.uniminuto.gestionestudiantes.models;

public class Calificacion {
    private int id;
    private int idEstudiante;
    private int idMateria;
    private double nota;

    public Calificacion() {}

    public Calificacion(int id, int idEstudiante, int idMateria, double nota) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.idMateria = idMateria;
        this.nota = nota;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public int getIdMateria() { return idMateria; }
    public void setIdMateria(int idMateria) { this.idMateria = idMateria; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

}
