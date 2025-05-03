package co.uniminuto.gestionestudiantes.presentation.calificacion;

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
import co.uniminuto.gestionestudiantes.data.models.Calificacion;

public class CalificacionAdapter extends ArrayAdapter<Calificacion> {

    public CalificacionAdapter(@NonNull Context context, @NonNull List<Calificacion> calificaciones) {
        super(context, 0, calificaciones);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Calificacion calificacion = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_calificacion, parent, false);
        }

        TextView tvEstudianteId = convertView.findViewById(R.id.tvEstudianteId);
        TextView tvMateriaId = convertView.findViewById(R.id.tvMateriaId);
        TextView tvNota = convertView.findViewById(R.id.tvNota);

        tvEstudianteId.setText("Estudiante ID: " + calificacion.getIdEstudiante());
        tvMateriaId.setText("Materia ID: " + calificacion.getIdMateria());
        tvNota.setText("Nota: " + calificacion.getNota());

        return convertView;
    }
}
