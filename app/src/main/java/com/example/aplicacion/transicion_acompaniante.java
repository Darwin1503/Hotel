package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class transicion_acompaniante extends AppCompatActivity {
    Button cont;
    String a,b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transicion_acompaniante);
        cont=findViewById(R.id.btnrcompa);
        Bundle datos=getIntent().getExtras();
        a=datos.getString("cantidad");
        b=datos.getString("id_cliente");
        Toast.makeText(transicion_acompaniante.this,"a= "+a+" b="+b, Toast.LENGTH_SHORT).show();
    }

    public void continuar(View view){
        Intent companion = new Intent(transicion_acompaniante.this,companion.class);
        Bundle datos2=new Bundle();
        datos2.putString("cantidad",a);
        datos2.putString("id_cliente",b);
        companion.putExtras(datos2);
        startActivity(companion);
    }
}