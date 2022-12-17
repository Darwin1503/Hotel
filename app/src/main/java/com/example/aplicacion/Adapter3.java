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

public class Adapter3 extends ArrayAdapter<reserva> {
    Context context;
    List<reserva> arrayreservas;
    public Adapter3(@NonNull Context context, List<reserva>arrayreservas) {
        super(context, R.layout.activity_habitacion,arrayreservas);
        this.context = context;
        this.arrayreservas = arrayreservas;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lista_clientes,null,true);
        TextView des = view.findViewById(R.id.reserva_descripcion);
        TextView cap= view.findViewById(R.id.reserva_capacidad);
        TextView piso= view.findViewById(R.id.reserva_piso);
        TextView cost= view.findViewById(R.id.reserva_costo);
        des.setText(arrayreservas.get(position).getDesc());
        cap.setText(arrayreservas.get(position).getCap());
        piso.setText(arrayreservas.get(position).getPiso());
        cost.setText(arrayreservas.get(position).getCosto());
        return view;
    }
}