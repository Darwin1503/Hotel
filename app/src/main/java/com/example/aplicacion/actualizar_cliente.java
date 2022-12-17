package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class actualizar_cliente extends AppCompatActivity {

    String url ="http://192.168.0.7/PHP/PostgreSQL/actualizar_cliente.php";
    String a,b;
    EditText txtnombrecliente;
    cliente fun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_cliente);
        txtnombrecliente=findViewById(R.id.txtnombrecliente);
        Bundle datos=getIntent().getExtras();
        a=datos.getString("id");
        b=datos.getString("nombre");
        fun=new cliente();
    }

    public void actualizar(View view){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Alum")) {

                            Toast.makeText(actualizar_cliente.this, "COMPLETADO", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(actualizar_cliente.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(actualizar_cliente.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", actualizar_cliente.this.a);
                params.put("nom", txtnombrecliente.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(actualizar_cliente.this);
        requestQueue.add(stringRequest);
        Toast.makeText(actualizar_cliente.this,"ACTUALIZADO", Toast.LENGTH_SHORT).show();
        fun.ver();
    }
}