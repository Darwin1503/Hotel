package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class insertar_cliente extends AppCompatActivity {

    EditText id,nom,pais;
    String url ="http://192.168.0.7/PHP/PostgreSQL/insertar_cliente.php";
    cliente fun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_cliente);

        id= (EditText)findViewById(R.id.txtidcliente);
        nom=(EditText)findViewById(R.id.txtnomcliente);
        pais=(EditText)findViewById(R.id.txtpaiscliente);
        fun=new cliente();
    }


    public void registrar(View view){

        String i=id.getText().toString();
        String n=nom.getText().toString();
        String p=pais.getText().toString();

        boolean paso=true;

        if (i.isEmpty()) {
            id.setError("Campo vacio");
            paso=false;
        }
        if (n.isEmpty()){
            nom.setError("Campo vacio");
            paso=false;
        }
        if (p.isEmpty()){
            pais.setError("Campo vacio");
            paso=false;
        }

        if(paso){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("Alum")) {

                                Toast.makeText(insertar_cliente.this, "COMPLETADO", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(insertar_cliente.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(insertar_cliente.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("id", id.getText().toString());
                    params.put("nombre", nom.getText().toString());
                    params.put("pais", pais.getText().toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(insertar_cliente.this);
            requestQueue.add(stringRequest);
            Toast.makeText(insertar_cliente.this,"REGISTRADO", Toast.LENGTH_SHORT).show();
            fun.ver();
        }
    }
}