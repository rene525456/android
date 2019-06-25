package com.example.primerproyecto.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.primerproyecto.R;
import com.example.primerproyecto.modelo.Alumno;

import java.util.List;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.ViewHolderAlumno>
        implements View.OnClickListener{

    List<Alumno> lista;
    private View.OnClickListener listener;

    public AlumnoAdapter(List<Alumno> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public AlumnoAdapter.ViewHolderAlumno onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_alumno,
                null);
        view.setOnClickListener(this);
        return new ViewHolderAlumno(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoAdapter.ViewHolderAlumno viewHolder, int posicion) {
        // se fija los datos en cada fila a través del holder view
        viewHolder.id.setText(lista.get(posicion).getIdAlumno()+"");
        viewHolder.nombre.setText(lista.get(posicion).getNombre());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }



    public static class ViewHolderAlumno extends RecyclerView.ViewHolder{
        TextView id ;
        TextView nombre;

        public ViewHolderAlumno(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.lblIdAlumnoCard);
            nombre = itemView.findViewById(R.id.lblNombreAlumnoCard);
        }
    }

    public void setOnCLickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }
}