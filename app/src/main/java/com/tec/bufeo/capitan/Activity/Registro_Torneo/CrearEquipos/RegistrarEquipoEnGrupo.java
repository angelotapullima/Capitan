package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearEquipos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Models.TablaTorneoItem;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Models.TablaTorneoSubItem;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Views.CrearInstancias;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class RegistrarEquipoEnGrupo extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rcv_equipos_en_grupos;
    String id_torneo;
    Button btnNext_a_instancias;
    public List<TablaTorneoItem> listaItem = new ArrayList<>();
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_equipo_en_grupo);

        preferences= new Preferences(this);
        rcv_equipos_en_grupos= findViewById(R.id.rcv_equipos_en_grupos);
        btnNext_a_instancias= findViewById(R.id.btnNext_a_instancias);
        id_torneo= getIntent().getExtras().getString("id_torneo");
        //id_torneo ="1";

        pedir_tabla(id_torneo);
        btnNext_a_instancias.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pedir_tabla(id_torneo);
    }

    JSONObject jsonObject,jsonNode,jsonNode2;
    JSONArray jsonArray,resultJSON;
    TablaTorneoItem tablaTorneoItem;
    TablaTorneoSubItem tablaTorneoSubItem ;
    String id_grupo;

    StringRequest stringRequest;
    private void pedir_tabla(final String id_torneo) {
        String url =IP2+"/api/Torneo/listar_tabla_por_id_torneo";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("listar grupos : ","y equipos"+response);

                try {
                    jsonObject = new JSONObject(response);
                    resultJSON = jsonObject.getJSONArray("results");

                    listaItem.clear();
                    int count = resultJSON.length();

                    for (int i = 0; i < count; i++) {

                        jsonNode = resultJSON.getJSONObject(i);


                        String nombre_grupo = jsonNode.optString("nombre_grupo");
                        id_grupo = jsonNode.optString("id_grupo");

                        jsonArray = jsonNode.getJSONArray("equipos");



                        if (jsonArray == null || jsonArray.equals("")) {


                            //tablaTorneoItem = new TablaTorneoItem(nombre_grupo, );
                            //listaItem.add(tablaTorneoItem);
                        } else {
                            tablaTorneoItem = new TablaTorneoItem(nombre_grupo,id_grupo, buildSubItemList(jsonArray));
                            listaItem.add(tablaTorneoItem);
                        }


                    }
                }
             catch (JSONException e) {
                e.printStackTrace();
            }

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    AdapterRegistroEquiposGruposItem itemAdapter = new AdapterRegistroEquiposGruposItem(getApplicationContext(),listaItem);


                    rcv_equipos_en_grupos.setAdapter(itemAdapter);
                    rcv_equipos_en_grupos.setLayoutManager(layoutManager);


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.d("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_torneo",id_torneo);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());


                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }


    private List<TablaTorneoSubItem> buildSubItemList(JSONArray array) {
        List<TablaTorneoSubItem> subItemList = new ArrayList<>();

        for (int i=0; i<array.length(); i++) {
            try {
                jsonNode2 = array.getJSONObject(i);

                String equipo_nombre,posicion_lista,equipo_foto;

                equipo_nombre = jsonNode2.optString("equipo_nombre");
                equipo_foto = jsonNode2.optString("equipo_foto");
                posicion_lista = (String.valueOf(i));


                tablaTorneoSubItem = new TablaTorneoSubItem(equipo_nombre,posicion_lista,equipo_foto);
                subItemList.add(tablaTorneoSubItem);




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return subItemList;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btnNext_a_instancias)){
            Intent intent =  new Intent(this, CrearInstancias.class);
            intent.putExtra("id_torneo",id_torneo);
            startActivity(intent);
        }
    }
}
