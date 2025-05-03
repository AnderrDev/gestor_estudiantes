package co.uniminuto.gestionestudiantes.presentation.materia;

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
import co.uniminuto.gestionestudiantes.data.models.Materia;

public class MateriaAdapter extends ArrayAdapter<Materia> {

    public MateriaAdapter(@NonNull Context context, @NonNull List<Materia> materias) {
        super(context, 0, materias);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.item_materia, parent, false);
        }

        Materia materia = getItem(position);

        TextView tvNombreMateria = listItem.findViewById(R.id.tvNombreMateria);
        TextView tvCodigoMateria = listItem.findViewById(R.id.tvCodigoMateria);

        if (materia != null) {
            tvNombreMateria.setText(materia.getNombre());
            tvCodigoMateria.setText("Código: " + materia.getCodigo());
        }

        return listItem;
    }
}
