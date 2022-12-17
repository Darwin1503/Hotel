package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity {

    VideoView w_fondo3;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    EditText id,nom,pais,user,correo,pass,compa;
    String url ="http://192.168.0.7/PHP/PostgreSQL/insertar_cliente.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        id=(EditText)findViewById(R.id.id_registro);
        nom=(EditText)findViewById(R.id.nom_registro);
        pais=(EditText)findViewById(R.id.pais_registro);
        user=(EditText)findViewById(R.id.txt_registro_username);
        correo=(EditText)findViewById(R.id.txt_registro_correo);
        pass=(EditText)findViewById(R.id.txt_registro_pass);
        compa=(EditText)findViewById(R.id.txt_registro_acompa);
        w_fondo3 = findViewById(R.id.w_fondo3);
        Uri uri = Uri.parse("android.resource://"+ getPackageName()+ "/"+ R.raw.video_login);

        w_fondo3.setVideoURI(uri);

        w_fondo3.start();

        w_fondo3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;

                mMediaPlayer.setLooping(true);

                if(mCurrentVideoPosition !=0){
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });
    }


    public void compas(){
        if(compa.getText().toString().isEmpty()||compa.getText().toString().compareTo("0")==0){
            Toast.makeText(Registrar.this, "Bienvenido, ya puedes iniciar sesión.", Toast.LENGTH_SHORT).show();
            Intent inicio = new Intent(Registrar.this,MainActivity.class);
            startActivity(inicio);
        }else{
            Intent companion = new Intent(Registrar.this,transicion_acompaniante.class);
            Bundle datos=new Bundle();
            datos.putString("cantidad",compa.getText().toString());
            datos.putString("id_cliente",id.getText().toString());
            companion.putExtras(datos);
            startActivity(companion);
        }
    }
    public void registrar(View view){

        String i=id.getText().toString();
        String n=nom.getText().toString();
        String p=pais.getText().toString();
        String u=user.getText().toString();
        String c=correo.getText().toString();
        String co=pass.getText().toString();
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
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
        if (u.isEmpty()) {
            user.setError("Campo vacio");
            paso=false;
        }
        if(compa.getText().toString().equals("")){
        }else{
            if(Integer.parseInt(compa.getText().toString())<0){
                compa.setError("Valor inválido");
                paso=false;
            }
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

                                Toast.makeText(Registrar.this, "Client3", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Registrar.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registrar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("id", id.getText().toString());
                    params.put("nombre", nom.getText().toString());
                    params.put("pais", pais.getText().toString());
                    params.put("user", user.getText().toString());
                    params.put("correo", correo.getText().toString());
                    params.put("contra", pass.getText().toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Registrar.this);
            requestQueue.add(stringRequest);
            Toast.makeText(Registrar.this,"Logrado", Toast.LENGTH_SHORT).show();
            compas();
        }

    }


    @Override
    protected void onPause(){
        super.onPause();

        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        w_fondo3.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();

        w_fondo3.start();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}