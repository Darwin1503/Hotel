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

public class insertar_habitacion extends AppCompatActivity {

    EditText des,cap,piso,costo;
    String url ="http://192.168.0.7/PHP/PostgreSQL/insertar_habitacion.php";
    habitacion fun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_habitacion);

        des= (EditText)findViewById(R.id.txtdes);
        cap=(EditText)findViewById(R.id.txtcap);
        piso=(EditText)findViewById(R.id.txtpiso);
        costo=(EditText)findViewById(R.id.txtcosto);
        fun=new habitacion();
    }


    public void registrar(View view){

        String i=des.getText().toString();
        String n=cap.getText().toString();
        String p=piso.getText().toString();
        String c=costo.getText().toString();

        boolean paso=true;

        if (i.isEmpty()) {
            des.setError("Campo vacio");
            paso=false;
        }
        if (n.isEmpty()){
            cap.setError("Campo vacio");
            paso=false;
        }
        if (p.isEmpty()){
            piso.setError("Campo vacio");
            paso=false;
        }
        if (c.isEmpty()){
            costo.setError("Campo vacio");
            paso=false;
        }

        if(paso){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("Alum")) {

                                Toast.makeText(insertar_habitacion.this, "COMPLETADO", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(insertar_habitacion.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(insertar_habitacion.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("des",des.getText().toString());
                    params.put("cap", cap.getText().toString());
                    params.put("piso", piso.getText().toString());
                    params.put("costo", costo.getText().toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(insertar_habitacion.this);
            requestQueue.add(stringRequest);
            Toast.makeText(insertar_habitacion.this,"REGISTRADO", Toast.LENGTH_SHORT).show();
        }
        fun.ver();
    }
}