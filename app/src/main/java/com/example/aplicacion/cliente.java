package com.example.aplicacion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

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

public class cliente extends AppCompatActivity {

    ListView listav;
    Button insertar;
    Adapter adapter;

    public static ArrayList<clientes> lista = new ArrayList<>();
    String url ="http://192.168.0.7/PHP/PostgreSQL/mostrar_clientes.php";
    String url2 ="http://192.168.0.7/PHP/PostgreSQL/eliminar_cliente.php";
    clientes cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        insertar=findViewById(R.id.btninsert);
        listav=findViewById(R.id.listav);
        adapter=new Adapter(this,lista);
        listav.setAdapter(adapter);
        listav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogItem = {"Editar","Eliminar"};
                builder.setTitle(lista.get(position).getNom());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Intent update = new Intent(cliente.this,actualizar_cliente.class);
                                Bundle datos=new Bundle();
                                datos.putString("id",lista.get(position).getId());
                                datos.putString("nombre",lista.get(position).getNom());
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
                                    String nom = object.getString("nombre");

                                    cliente = new clientes(id,nom);
                                    lista.add(cliente);
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
                Toast.makeText(cliente.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void insertar(View view){
        Intent insert = new Intent(cliente.this,insertar_cliente.class);
        startActivity(insert);
    }



    public void borrar(String a){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Alum")) {

                            Toast.makeText(cliente.this, "COMPLETADO", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(cliente.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cliente.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", a);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(cliente.this);
        requestQueue.add(stringRequest);
        Toast.makeText(cliente.this,"REGISTRADO", Toast.LENGTH_SHORT).show();
    }
}