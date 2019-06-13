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

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolderProducto>
        implements View.OnClickListener{

    List<Producto> lista;
    private View.OnClickListener listener;

    public ProductoAdapter(List<Producto> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ProductoAdapter.ViewHolderProducto onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_producto,
                null);
        view.setOnClickListener(this);
        return new ViewHolderProducto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolderProducto viewHolder, int posicion) {
        // se fija los datos en cada fila a través del holder view
        viewHolder.nombre.setText(lista.get(posicion).getNombre());
        viewHolder.codigo.setText(lista.get(posicion).getCodigo());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }



    public static class ViewHolderProducto extends RecyclerView.ViewHolder{
        TextView nombre ;
        TextView codigo;

        public ViewHolderProducto(View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.lblCodigoCard);
            nombre = itemView.findViewById(R.id.lblNombreCard);
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