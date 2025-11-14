package com.example.gestionreparacionesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionreparacionesapp.data.db.entity.Cliente;

import java.util.List;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClienteViewHolder> {

    private List<Cliente> listaClientes;

    public ClientesAdapter(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = listaClientes.get(position);

        holder.tvNombreCliente.setText(cliente.getNombre());
        holder.tvDireccion.setText(cliente.getDireccion());
        holder.tvLocalidad.setText(cliente.getLocalidad());
        holder.tvCodigoPostal.setText(cliente.getCodigoPostal());
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCliente, tvDireccion, tvLocalidad, tvCodigoPostal;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvLocalidad = itemView.findViewById(R.id.tvLocalidad);
            tvCodigoPostal = itemView.findViewById(R.id.tvCodigoPostal);
        }
    }
}