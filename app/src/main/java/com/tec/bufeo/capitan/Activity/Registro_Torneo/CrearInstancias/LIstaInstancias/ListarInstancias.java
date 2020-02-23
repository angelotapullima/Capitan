package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.LIstaInstancias;

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
import com.tec.bufeo.capitan.Activity.Registro_Torneo.RegistroTorneoFinalizado;
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

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class ListarInstancias extends AppCompatActivity implements View.OnClickListener {


    RecyclerView rcv_partidos_instancias;
    String id_torneo;
    Context context;
    Button btn_finish;
    public List<Instancias> listaItem = new ArrayList<>();
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_instancias);
        preferences =new Preferences(this);
        rcv_partidos_instancias= findViewById(R.id.rcv_partidos_instancias);
        btn_finish= findViewById(R.id.btn_finish);
        id_torneo= getIntent().getExtras().getString("id_torneo");

        btn_finish.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        pedir_tabla(id_torneo);
    }

    JSONObject jsonObject,jsonNode,jsonNode2;
    JSONArray jsonArray,resultJSON;
    Instancias Item;
    PartidosInstancias subItem ;

    StringRequest stringRequest;
    private void pedir_tabla(final String id_torneo) {
        String url =IP2+"/api/Torneo/listar_instancias_partidos_por_id_torneo";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("listar partidos : ","en instancias"+response);

                try {
                    jsonObject = new JSONObject(response);
                    resultJSON = jsonObject.getJSONArray("results");

                    listaItem.clear();
                    int count = resultJSON.length();

                    for (int i = 0; i < count; i++) {

                        jsonNode = resultJSON.getJSONObject(i);


                        String nombre_grupo = jsonNode.optString("nombre_instancia");
                        String id_instancias = jsonNode.optString("id_instancia");

                        jsonArray = jsonNode.getJSONArray("partidos");



                        if (jsonArray == null || jsonArray.equals("")) {


                            //tablaTorneoItem = new TablaTorneoItem(nombre_grupo, );
                            //listaItem.add(tablaTorneoItem);
                        } else {
                            Item = new Instancias(id_instancias,nombre_grupo, buildSubItemList(jsonArray));
                            listaItem.add(Item);
                        }


                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                AdapterInstanciasItem itemAdapter = new AdapterInstanciasItem(getApplicationContext(),id_torneo,listaItem);


                rcv_partidos_instancias.setAdapter(itemAdapter);
                rcv_partidos_instancias.setLayoutManager(layoutManager);


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i("RESPUESTA: ",""+error.toString());

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


    private List<PartidosInstancias> buildSubItemList(JSONArray array) {
        List<PartidosInstancias> subItemList = new ArrayList<>();

        String id_torneo_partido,id_equipo_local,nombre_equipo_local,foto_equipo_local,id_equipo_visita,
                nombre_equipo_visita,foto_equipo_visita,partido_fecha,partido_hora,partido_estado;
        for (int i=0; i<array.length(); i++) {
            try {
                jsonNode2 = array.getJSONObject(i);



                id_torneo_partido = jsonNode2.optString("id_torneo_partido");
                id_equipo_local = jsonNode2.optString("id_equipo_local");
                nombre_equipo_local = jsonNode2.optString("nombre_equipo_local");
                foto_equipo_local = jsonNode2.optString("foto_equipo_local");
                id_equipo_visita = jsonNode2.optString("id_equipo_visita");
                nombre_equipo_visita = jsonNode2.optString("nombre_equipo_visita");
                foto_equipo_visita = jsonNode2.optString("foto_equipo_visita");
                partido_fecha = jsonNode2.optString("partido_fecha");
                partido_hora = jsonNode2.optString("partido_hora");
                partido_estado = jsonNode2.optString("partido_estado");
                //posicion_lista = (String.valueOf(i));


                subItem = new PartidosInstancias(id_torneo_partido,id_equipo_local,nombre_equipo_local,
                        foto_equipo_local,id_equipo_visita,nombre_equipo_visita,foto_equipo_visita,partido_fecha,partido_hora,partido_estado);
                subItemList.add(subItem);




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return subItemList;
    }
    @Override
    public void onClick(View view) {

        if(view.equals(btn_finish)){
            Intent i =  new Intent(ListarInstancias.this, RegistroTorneoFinalizado.class);
            startActivity(i);
        }
    }
}
