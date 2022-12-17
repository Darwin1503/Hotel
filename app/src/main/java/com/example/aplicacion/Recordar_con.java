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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recordar_con extends AppCompatActivity {

    VideoView w_fondo2;
    EditText usr_re;
    EditText txtcorreo;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    String url ="http://192.168.0.7/PHP/PostgreSQL/recordar.php";
    int confirm=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordar_con);
        txtcorreo=(EditText)findViewById(R.id.txtcorreo);
        usr_re= (EditText)findViewById(R.id.username_recu);
        w_fondo2 = findViewById(R.id.w_fondo2);
        Uri uri = Uri.parse("android.resource://"+ getPackageName()+ "/"+ R.raw.video_login);

        w_fondo2.setVideoURI(uri);

        w_fondo2.start();

        w_fondo2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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

    public void validar_recuperar_contraseña(){

        boolean paso=true;
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


        String usr=usr_re.getText().toString();
        if (usr.isEmpty()) {
            usr_re.setError("Campo vacio");
            paso=false;
        }
        String cor=txtcorreo.getText().toString();
        if (cor.isEmpty()) {
            txtcorreo.setError("Campo vacio");
            paso=false;
        }else{
            Matcher matcher=pattern.matcher(txtcorreo.getText().toString());
            if(!matcher.find()){
                txtcorreo.setError("Correo inválido");
                paso=false;
            }
        }
        if(paso){
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{

                                JSONObject jsonObject = new JSONObject(response);
                                String succes = jsonObject.getString("succes");
                                JSONArray jsonArray = jsonObject.getJSONArray("datos");

                                if(succes.equals("1")){

                                    for(int i=0;i<jsonArray.length();i++){

                                        JSONObject object = jsonArray.getJSONObject(i);
                                        if(usr_re.getText().toString().equals(object.getString("usuario"))){
                                            if(txtcorreo.getText().toString().equals(object.getString("correo"))){
                                                Intent update = new Intent(Recordar_con.this,insert_con.class);
                                                Bundle datos=new Bundle();
                                                datos.putString("usr",usr_re.getText().toString());
                                                update.putExtras(datos);
                                                startActivity(update);
                                                confirm=1;
                                                break;
                                            }else{
                                                confirm=0;
                                            }
                                        }else{
                                            confirm=0;
                                        }
                                    }
                                    if (confirm==0){
                                        Toast.makeText(Recordar_con.this,"ERROR EN LOS DATOS",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }

                        }


                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Recordar_con.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }



    }

    public void Recuperar (View view){
        validar_recuperar_contraseña();
    }


    @Override
    protected void onPause(){
        super.onPause();

        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        w_fondo2.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();

        w_fondo2.start();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        mMediaPlayer.release();
        mMediaPlayer = null;
    }


}