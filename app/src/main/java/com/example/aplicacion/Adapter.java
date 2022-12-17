package com.example.aplicacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter extends ArrayAdapter<clientes> {
    Context context;
    List<clientes> arrayclientes;
    public Adapter(@NonNull Context context, List<clientes>arrayclientes) {
        super(context, R.layout.activity_cliente,arrayclientes);
        this.context = context;
        this.arrayclientes = arrayclientes;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lista_clientes,null,true);
        TextView tid = view.findViewById(R.id.txtid);
        TextView tnombre= view.findViewById(R.id.txtnombre);
        tid.setText(arrayclientes.get(position).getId());
        tnombre.setText(arrayclientes.get(position).getNom());
        return view;
    }
}