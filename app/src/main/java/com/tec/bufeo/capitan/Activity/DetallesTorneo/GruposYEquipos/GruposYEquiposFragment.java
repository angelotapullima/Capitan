package com.tec.bufeo.capitan.Activity.DetallesTorneo.GruposYEquipos;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Models.TablaTorneoItem;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Models.TablaTorneoSubItem;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearEquipos.AdapterRegistroEquiposGruposItem;
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


public class GruposYEquiposFragment extends Fragment {


    RecyclerView rcv_equipos_y_grupos;
    String id_torneo;
    Context context;
    public List<TablaTorneoItem> listaItem = new ArrayList<>();
    Preferences preferences;

    public GruposYEquiposFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grupos_y_equipos, container, false);
        preferences= new Preferences(getActivity());
        final Bundle bdl = getArguments();


        id_torneo = bdl.getString("id_torneo");

        initViews(view);
        pedir_tabla(id_torneo);
        return  view;
    }



    private void initViews(View view) {
        rcv_equipos_y_grupos= view.findViewById(R.id.rcv_equipos_y_grupos);

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

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                AdapterRegistroEquiposGruposItem itemAdapter = new AdapterRegistroEquiposGruposItem(getContext(),listaItem);


                rcv_equipos_y_grupos.setAdapter(itemAdapter);
                rcv_equipos_y_grupos.setLayoutManager(layoutManager);


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
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
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


}
