package co.uniminuto.gestionestudiantes.core.network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import co.uniminuto.gestionestudiantes.data.models.Estudiante;

/**
 * EstudianteApiService se encarga de hacer una solicitud HTTP GET
 * para obtener datos de estudiantes desde un servicio web,
 * y transformarlos en objetos del modelo Estudiante.
 */
public class EstudianteApiService {

    /**
     * Obtiene una lista de estudiantes desde una API pública.
     *
     * @return Lista de objetos Estudiante construidos a partir de la respuesta JSON.
     * @throws Exception si ocurre un error de conexión o de formato en la respuesta.
     */
    public List<Estudiante> getEstudiantes() throws Exception {
        String urlString = "https://jsonplaceholder.typicode.com/users";

        // Crear y configurar conexión HTTP
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        // Verificar respuesta HTTP
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("Error en respuesta: " + responseCode);
        }

        // Leer respuesta JSON
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        reader.close();

        // Parsear JSON a objetos Estudiante
        JSONArray jsonArray = new JSONArray(responseBuilder.toString());
        List<Estudiante> estudiantes = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonEstudiante = jsonArray.getJSONObject(i);

            Estudiante estudiante = new Estudiante();
            estudiante.setCodigo(jsonEstudiante.getString("id"));         // ID como código
            estudiante.setNombre(jsonEstudiante.getString("name"));       // Nombre
            estudiante.setApellido(jsonEstudiante.getString("username")); // Username como apellido
            estudiante.setEmail(jsonEstudiante.getString("email"));       // Email

            estudiantes.add(estudiante);
        }

        return estudiantes;
    }
}
