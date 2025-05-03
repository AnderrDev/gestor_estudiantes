package co.uniminuto.gestionestudiantes.presentation.docente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.uniminuto.gestionestudiantes.R;
import co.uniminuto.gestionestudiantes.data.models.Docente;

public class DocenteAdapter extends BaseAdapter {

    private final Context context;
    private final List<Docente> docentes;

    public DocenteAdapter(Context context, List<Docente> docentes) {
        this.context = context;
        this.docentes = docentes;
    }

    @Override
    public int getCount() {
        return docentes.size();
    }

    @Override
    public Object getItem(int position) {
        return docentes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_docente, parent, false);

        TextView tvNombre = view.findViewById(R.id.tvNombre);
        TextView tvDocumento = view.findViewById(R.id.tvDocumento);
        TextView tvCorreo = view.findViewById(R.id.tvCorreo);

        Docente docente = docentes.get(position);

        tvNombre.setText(docente.getNombre());
        tvDocumento.setText("Documento: " + docente.getDocumento());
        tvCorreo.setText("Correo: " + docente.getCorreo());

        return view;
    }
}
