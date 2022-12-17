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

public class insert_con extends AppCompatActivity {
    String url ="http://192.168.0.7/PHP/PostgreSQL/act_contraseña.php";
    String a;
    EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_con);
        txt=findViewById(R.id.nueva_contraseña);
        Bundle datos=getIntent().getExtras();
        a=datos.getString("usr");
    }

    public void recordar(View view){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Alum")) {

                            Toast.makeText(insert_con.this, "COMPLETADO", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(insert_con.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(insert_con.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("usr", insert_con.this.a);
                params.put("con", txt.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(insert_con.this);
        requestQueue.add(stringRequest);
        Toast.makeText(insert_con.this,"ACTUALIZADO", Toast.LENGTH_SHORT).show();
    }
}