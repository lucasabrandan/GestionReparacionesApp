package com.example.gestionreparacionesapp.ui.productos;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionreparacionesapp.R;
import com.example.gestionreparacionesapp.data.db.entity.Producto;

import java.util.List;
import java.util.Locale;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder> {

    private List<Producto> listaProductos;

    public ProductosAdapter(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);

        holder.tvNombreProducto.setText(producto.getNombre());
        holder.tvPrecio.setText(String.format(Locale.getDefault(), "$%.2f", producto.getPrecio()));

        int stock = producto.getCantidad();
        holder.tvStock.setText(stock + " unidades");

        // Cambiar color según el stock
        if (stock == 0) {
            holder.tvStock.setTextColor(Color.parseColor("#F44336")); // Rojo
            holder.tvStock.setText("SIN STOCK");
        } else if (stock < 5) {
            holder.tvStock.setTextColor(Color.parseColor("#FF9800")); // Naranja
        } else {
            holder.tvStock.setTextColor(Color.parseColor("#4CAF50")); // Verde
        }
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreProducto, tvPrecio, tvStock;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvStock = itemView.findViewById(R.id.tvStock);
        }
    }
}