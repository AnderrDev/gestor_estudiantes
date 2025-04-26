package co.uniminuto.gestionestudiantes.api;

import android.os.AsyncTask;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EstudianteAPI {

    public interface EstudianteAPICallback {
        void onSuccess(String response);
        void onError(String error);
    }

    public static void obtenerEstudiantes(String urlString, EstudianteAPICallback callback) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    String urlString = params[0];
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    StringBuilder stringBuilder = new StringBuilder();
                    int charRead;
                    while ((charRead = reader.read()) != -1) {
                        stringBuilder.append((char) charRead);
                    }

                    return stringBuilder.toString();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    callback.onSuccess(result);
                } else {
                    callback.onError("Error al obtener los datos");
                }
            }
        }.execute(urlString);
    }
}