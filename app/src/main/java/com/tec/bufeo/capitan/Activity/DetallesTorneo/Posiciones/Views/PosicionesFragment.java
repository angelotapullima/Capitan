package com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Views;



import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Models.TablaTorneoItem;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Models.TablaTorneoSubItem;
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


public class PosicionesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView rcv_item;
    String id_torneo;
    Context context;
    public List<TablaTorneoItem> listaItem = new ArrayList<>();
    Preferences preferences;
    LinearLayout cargando_layoutPosiciones;
    SwipeRefreshLayout swipePosiciones;




    public PosicionesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_posiciones, container, false);

        context = getContext();
        preferences= new Preferences(context);
        final Bundle bdl = getArguments();


        id_torneo = bdl.getString("id_torneo");

        initViews(view);


        pedir_tabla(id_torneo);
        return view;
    }

    private void initViews(View view) {
        rcv_item = (RecyclerView) view.findViewById(R.id.rcv_item);
        cargando_layoutPosiciones = (LinearLayout) view.findViewById(R.id.cargando_layoutPosiciones);
        swipePosiciones= view.findViewById(R.id.swipePosiciones);
        swipePosiciones.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipePosiciones.setOnRefreshListener(this);
    }


    JSONObject jsonObject,jsonNode,jsonNode2;
    JSONArray jsonArray,resultJSON;
    TablaTorneoItem tablaTorneoItem;
    TablaTorneoSubItem tablaTorneoSubItem ;

    StringRequest stringRequest;
    private void pedir_tabla(final String id_torneo) {
        String url =IP2+"/api/Torneo/listar_tabla_por_id_torneo";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("registro reto: ",""+response);

                try {
                    jsonObject = new JSONObject(response);
                    resultJSON = jsonObject.getJSONArray("results");

                    listaItem.clear();
                    int count = resultJSON.length();

                    for (int i = 0; i < count; i++) {

                        jsonNode = resultJSON.getJSONObject(i);


                        String nombre_grupo = jsonNode.optString("nombre_grupo");
                        String id_grupo = jsonNode.optString("id_grupo");
                        jsonArray = jsonNode.getJSONArray("equipos");


                        tablaTorneoItem = new TablaTorneoItem(nombre_grupo,id_grupo,"posiciones",buildSubItemList(jsonArray));
                        listaItem.add(tablaTorneoItem);
                        //listaSubItem.clear();

                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    AdapterItemTablaTorneo itemAdapter = new AdapterItemTablaTorneo(getContext(),listaItem);
                    rcv_item.setAdapter(itemAdapter);
                    rcv_item.setLayoutManager(layoutManager);
                    cargando_layoutPosiciones.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }


    private List<TablaTorneoSubItem> buildSubItemList(JSONArray array) {
        List<TablaTorneoSubItem> subItemList = new ArrayList<>();

        for (int i=0; i<array.length(); i++) {
            try {
                jsonNode2 = array.getJSONObject(i);

                String equipo_id,equipo_nombre,part_j,part_g,part_e,part_p,gf,gc,total,equipo_foto;

                equipo_id = jsonNode2.optString("equipo_id");
                equipo_nombre = jsonNode2.optString("equipo_nombre");
                equipo_foto = jsonNode2.optString("equipo_foto");
                part_j = jsonNode2.optString("part_j");
                part_g = jsonNode2.optString("part_g");
                part_e = jsonNode2.optString("part_e");
                part_p = jsonNode2.optString("part_p");
                gf = jsonNode2.optString("gf");
                gc = jsonNode2.optString("gc");
                total = jsonNode2.optString("total");

                tablaTorneoSubItem = new TablaTorneoSubItem(equipo_id,equipo_nombre,part_j,part_g,part_e,part_p,gf,gc,total,equipo_foto);
                subItemList.add(tablaTorneoSubItem);




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return subItemList;
    }

    @Override
    public void onRefresh() {
        pedir_tabla(id_torneo);
        cargando_layoutPosiciones.setVisibility(View.VISIBLE);
        swipePosiciones.setRefreshing(false);
    }
}
