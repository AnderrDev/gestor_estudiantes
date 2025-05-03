package co.uniminuto.gestionestudiantes.presentation.estudiante;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import co.uniminuto.gestionestudiantes.R;
import co.uniminuto.gestionestudiantes.data.models.Estudiante;

public class EstudianteAdapter extends ArrayAdapter<Estudiante> {

    public EstudianteAdapter(@NonNull Context context, @NonNull List<Estudiante> estudiantes) {
        super(context, 0, estudiantes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Estudiante estudiante = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_estudiante, parent, false);
        }

        TextView tvNombreApellido = convertView.findViewById(R.id.tvNombreApellido);
        TextView tvCodigo = convertView.findViewById(R.id.tvCodigo);
        TextView tvCorreo = convertView.findViewById(R.id.tvCorreo);

        if (estudiante != null) {
            tvNombreApellido.setText(estudiante.getNombre() + " " + estudiante.getApellido());
            tvCodigo.setText("Código: " + estudiante.getCodigo());
            tvCorreo.setText("Email: " + estudiante.getEmail());
        }

        return convertView;
    }
}
