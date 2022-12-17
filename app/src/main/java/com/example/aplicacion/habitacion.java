package com.example.aplicacion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class habitacion extends AppCompatActivity {

    ListView lhabitaciones;
    Button insertar;
    Adapter2 adapter;


    public static ArrayList<habitaciones> lista = new ArrayList<>();
    String url ="http://192.168.0.7/PHP/PostgreSQL/mostrar_habitaciones.php";
    String url2 ="http://192.168.0.7/PHP/PostgreSQL/eliminar_habitacion.php";
    habitaciones habitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion);
        insertar=findViewById(R.id.btninhabitacion);
        lhabitaciones=findViewById(R.id.lhabitaciones);
        adapter=new Adapter2(this,lista);
        lhabitaciones.setAdapter(adapter);
        lhabitaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogItem = {"Editar","Eliminar"};
                builder.setTitle("habitación "+lista.get(position).getId());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Intent update = new Intent(habitacion.this,actualizar_habitacion.class);
                                Bundle datos=new Bundle();
                                datos.putString("id",lista.get(position).getId());
                                datos.putString("des",lista.get(position).getDes());
                                datos.putString("cost",lista.get(position).getCosto());
                                update.putExtras(datos);
                                startActivity(update);
                                break;
                            case 1:
                                borrar(lista.get(position).getId());
                                ver();
                                break;
                        }

                    }
                });
                builder.create().show();
            }
        });
        ver();
    }

    public void ver(){
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        lista.clear();
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("succes");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");

                            if(succes.equals("1")){

                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String de = object.getString("des");
                                    String cost = object.getString("costo");

                                    habitacion = new habitaciones(id,de,cost);
                                    lista.add(habitacion);
                                    adapter.notifyDataSetChanged();
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
                Toast.makeText(habitacion.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void insertar(View view){
        Intent insert = new Intent(habitacion.this,insertar_habitacion.class);
        startActivity(insert);
    }



    public void borrar(String a){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Alum")) {

                            Toast.makeText(habitacion.this, "COMPLETADO", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(habitacion.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(habitacion.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", a);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(habitacion.this);
        requestQueue.add(stringRequest);
        Toast.makeText(habitacion.this,"REGISTRADO", Toast.LENGTH_SHORT).show();
    }
}