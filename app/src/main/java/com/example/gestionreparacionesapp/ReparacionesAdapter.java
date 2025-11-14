package com.example.gestionreparacionesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionreparacionesapp.data.db.entity.Cliente;
import com.example.gestionreparacionesapp.data.db.entity.Reparacion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class ReparacionesAdapter extends RecyclerView.Adapter<ReparacionesAdapter.ReparacionViewHolder> {

    private List<Reparacion> listaReparaciones;
    private List<Cliente> listaClientes;

    public ReparacionesAdapter(List<Reparacion> listaReparaciones, List<Cliente> listaClientes) {
        this.listaReparaciones = listaReparaciones;
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public ReparacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reparacion, parent, false);
        return new ReparacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReparacionViewHolder holder, int position) {
        Reparacion reparacion = listaReparaciones.get(position);

        // ID de la reparación
        holder.tvReparacionId.setText(String.format("REPARACIÓN #%03d", reparacion.getId()));

        // Fecha
        String fecha = reparacion.getFecha();
        if (fecha.contains(" ")) {
            fecha = fecha.split(" ")[0];
        }
        holder.tvFecha.setText(fecha);

        // Cliente
        String nombreCliente = "Cliente desconocido";
        for (Cliente c : listaClientes) {
            if (c.getId() == reparacion.getClienteId()) {
                nombreCliente = c.getNombre();
                break;
            }
        }
        holder.tvCliente.setText(nombreCliente);

        // Descripción
        holder.tvDescripcion.setText(reparacion.getDescripcion());

        // Productos (parsear JSON)
        String productosTexto = parsearProductos(reparacion.getProductosJson());
        holder.tvProductos.setText(productosTexto);

        // Total
        holder.tvTotal.setText(String.format(Locale.getDefault(), "$%.2f", reparacion.getTotal()));
    }

    @Override
    public int getItemCount() {
        return listaReparaciones.size();
    }

    private String parsearProductos(String productosJson) {
        StringBuilder resultado = new StringBuilder();
        try {
            JSONArray array = new JSONArray(productosJson);
            for (int i = 0; i < array.length(); i++) {
                JSONObject producto = array.getJSONObject(i);
                String nombre = producto.getString("nombre");
                int cantidad = producto.getInt("cantidad");
                resultado.append("• ").append(nombre).append(" x").append(cantidad);
                if (i < array.length() - 1) {
                    resultado.append("\n");
                }
            }
        } catch (Exception e) {
            resultado.append("Error al cargar productos");
        }
        return resultado.toString();
    }

    static class ReparacionViewHolder extends RecyclerView.ViewHolder {
        TextView tvReparacionId, tvFecha, tvCliente, tvDescripcion, tvProductos, tvTotal;

        public ReparacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReparacionId = itemView.findViewById(R.id.tvReparacionId);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvProductos = itemView.findViewById(R.id.tvProductos);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}