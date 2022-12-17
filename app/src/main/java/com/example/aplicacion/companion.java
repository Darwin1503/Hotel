package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class companion extends AppCompatActivity {
    String a,b;
    EditText id,nom,pais,user,correo,pass;
    TextView ti;
    Button seguir;
    String url ="http://192.168.0.7/PHP/PostgreSQL/insertar_compa.php";
    int num;
    String ini="siguiente registro";
    String fin="Finalizar registro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companion);
        id=(EditText)findViewById(R.id.companion_id);
        nom=(EditText)findViewById(R.id.companion_nombre);
        pais=(EditText)findViewById(R.id.companion_pais);
        user=(EditText)findViewById(R.id.companion_user);
        correo=(EditText)findViewById(R.id.companion_correo);
        pass=(EditText)findViewById(R.id.companion_pass);
        ti=findViewById(R.id.txtcompa);
        seguir=findViewById(R.id.button9);
        Bundle datos2=getIntent().getExtras();
        a=datos2.getString("cantidad");
        b=datos2.getString("id_cliente");
        Toast.makeText(companion.this,"a= "+a+" b="+b, Toast.LENGTH_SHORT).show();
        num=1;
        ti.setText("Acompañante "+num+", ingresa tus datos!");
    }


    public void llenar(View view){

        if(num==Integer.parseInt(a)){
            seguir.setText(fin);
            ti.setText("Acompañante "+num+", ingresa tus datos!");
        }

            String iss=id.getText().toString();
            String n=nom.getText().toString();
            String p=pais.getText().toString();
            String u=user.getText().toString();
            String c=correo.getText().toString();
            String co=pass.getText().toString();

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            boolean paso=true;

            if (iss.isEmpty()) {
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
            if (u.isEmpty()) {
                user.setError("Campo vacio");
                paso=false;
            }
            if (c.isEmpty()){
                correo.setError("Campo vacio");
                paso=false;
            }else{
                Matcher matcher=pattern.matcher(c);
                if(!matcher.find()){
                    correo.setError("Correo inválido");
                    paso=false;
                }
            }
            if (co.isEmpty()){
                pass.setError("Campo vacio");
                paso=false;
            }

            if(paso){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equalsIgnoreCase("Alum")) {

                                    Toast.makeText(companion.this, "Client3", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(companion.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(companion.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", iss);
                        params.put("idc", b);
                        params.put("nombre", n);
                        params.put("pais", p);
                        params.put("user", u);
                        params.put("correo", c);
                        params.put("contra", co);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(companion.this);
                requestQueue.add(stringRequest);
                Toast.makeText(companion.this,"Logrado", Toast.LENGTH_SHORT).show();

                id.setText("");
                nom.setText("");
                pais.setText("");
                user.setText("");
                correo.setText("");
                pass.setText("");
            }
        if(num==Integer.parseInt(a)){
            seguir.setText(fin);
        }else{
            Toast.makeText(companion.this, "Ingresa los datos del siguiente acompañante.", Toast.LENGTH_SHORT).show();
        }
            num+=1;
        ti.setText("Acompañante "+num+", ingresa tus datos!");
        if(num>Integer.parseInt(a)){
            Toast.makeText(companion.this, "Bienvenido, ya puedes iniciar sesión.", Toast.LENGTH_SHORT).show();
            Intent inicio = new Intent(companion.this,MainActivity.class);
            startActivity(inicio);
            seguir.setText(ini);
        }



    }
}