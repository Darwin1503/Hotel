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

public class Adapter2 extends ArrayAdapter<habitaciones> {
    Context context;
    List<habitaciones> arrayhabitaciones;
    public Adapter2(@NonNull Context context, List<habitaciones>arrayhabitaciones) {
        super(context, R.layout.activity_habitacion,arrayhabitaciones);
        this.context = context;
        this.arrayhabitaciones = arrayhabitaciones;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lista_clientes,null,true);
        TextView tid = view.findViewById(R.id.txtidhabitacion);
        TextView des= view.findViewById(R.id.txtdeshabitacion);
        TextView cost= view.findViewById(R.id.txtcosthabitacion);
        tid.setText(arrayhabitaciones.get(position).getId());
        des.setText(arrayhabitaciones.get(position).getDes());
        cost.setText(arrayhabitaciones.get(position).getCosto());
        return view;
    }
}