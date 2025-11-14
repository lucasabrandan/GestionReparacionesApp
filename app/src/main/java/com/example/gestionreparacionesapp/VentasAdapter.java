package com.example.gestionreparacionesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionreparacionesapp.data.db.entity.Cliente;
import com.example.gestionreparacionesapp.data.db.entity.Venta;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class VentasAdapter extends RecyclerView.Adapter<VentasAdapter.VentaViewHolder> {

    private List<Venta> listaVentas;
    private List<Cliente> listaClientes;

    public VentasAdapter(List<Venta> listaVentas, List<Cliente> listaClientes) {
        this.listaVentas = listaVentas;
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public VentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venta, parent, false);
        return new VentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VentaViewHolder holder, int position) {
        Venta venta = listaVentas.get(position);

        // ID de la venta
        holder.tvVentaId.setText(String.format("VENTA #%03d", venta.getId()));

        // Fecha
        String fecha = venta.getFecha();
        if (fecha.contains(" ")) {
            fecha = fecha.split(" ")[0]; // Solo la fecha, sin hora
        }
        holder.tvFecha.setText(fecha);

        // Cliente
        String nombreCliente = "Cliente desconocido";
        for (Cliente c : listaClientes) {
            if (c.getId() == venta.getClienteId()) {
                nombreCliente = c.getNombre();
                break;
            }
        }
        holder.tvCliente.setText(nombreCliente);

        // Productos (parsear JSON)
        String productosTexto = parsearProductos(venta.getProductosJson());
        holder.tvProductos.setText(productosTexto);

        // Total
        holder.tvTotal.setText(String.format(Locale.getDefault(), "$%.2f", venta.getTotal()));
    }

    @Override
    public int getItemCount() {
        return listaVentas.size();
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

    static class VentaViewHolder extends RecyclerView.ViewHolder {
        TextView tvVentaId, tvFecha, tvCliente, tvProductos, tvTotal;

        public VentaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVentaId = itemView.findViewById(R.id.tvVentaId);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvProductos = itemView.findViewById(R.id.tvProductos);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}