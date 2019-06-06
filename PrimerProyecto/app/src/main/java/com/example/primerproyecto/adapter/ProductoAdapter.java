package com.example.primerproyecto.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.primerproyecto.R;
import com.example.primerproyecto.modelo.Producto;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    List<Producto> lista;

    public ProductoAdapter(List<Producto> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_producto,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int posicion) {
        // se fija los datos en cada fila a través del holder view
        viewHolder.nombre.setText(lista.get(posicion).getNombre());
        viewHolder.codigo.setText(lista.get(posicion).getCodigo());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre ;
        TextView codigo;

        public ViewHolder(View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.lblCodigoCard);
            nombre = itemView.findViewById(R.id.lblNombreCard);
        }
    }
}
