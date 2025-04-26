package co.uniminuto.gestionestudiantes.models;

public class Docente {
    private int id;
    private String documento;
    private String nombre;
    private String correo;

    public Docente() {}

    public Docente(int id, String documento, String nombre, String correo) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}