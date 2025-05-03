package co.uniminuto.gestionestudiantes.data.models;

public class Estudiante {

    private int id;         // ID de la base de datos (autoincremental)
    private String nombre;
    private String apellido;
    private String codigo;  // Código lógico del estudiante
    private String email;

    // Constructor vacío
    public Estudiante() {
    }

    // Constructor completo
    public Estudiante(int id, String nombre, String apellido, String codigo, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigo = codigo;
        this.email = email;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Método de ayuda para convertir desde JSON (útil para API)
    public static Estudiante fromJson(org.json.JSONObject jsonObject) {
        Estudiante estudiante = new Estudiante();
        estudiante.setCodigo(jsonObject.optString("id")); // ¡Aquí importante! id del API = código
        estudiante.setNombre(jsonObject.optString("name"));
        estudiante.setApellido(jsonObject.optString("username"));
        estudiante.setEmail(jsonObject.optString("email"));
        return estudiante;
    }
}
