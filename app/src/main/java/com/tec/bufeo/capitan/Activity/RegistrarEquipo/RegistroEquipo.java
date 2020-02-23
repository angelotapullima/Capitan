package com.tec.bufeo.capitan.Activity.RegistrarEquipo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquiposRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class RegistroEquipo extends AppCompatActivity {


    RecyclerView rcv_busqueda_requipos;
    MisEquiposViewModel misEquiposViewModel;
    AdapterEquipos adaptadorEquipos;
    String id_grupo;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_equipo);

        misEquiposViewModel = ViewModelProviders.of(this).get(MisEquiposViewModel.class);
        preferences= new Preferences(this);
        id_grupo =getIntent().getExtras().getString("id_grupo");
        initViews();
        setAdapter();
        cargarvista();
    }

    private void initViews() {
        rcv_busqueda_requipos = findViewById(R.id.rcv_busqueda_requipos);
    }
    private void setAdapter() {


        adaptadorEquipos =  new AdapterEquipos(this, new AdapterEquipos.OnItemClickListener() {
            @Override
            public void onItemClick(Mequipos mequipos, int position) {

                agregar_equipo(mequipos.getEquipo_id());

            }
        });

        rcv_busqueda_requipos.setAdapter(adaptadorEquipos);
        rcv_busqueda_requipos.setLayoutManager(new LinearLayoutManager(this));




    }


    private void cargarvista() {

        misEquiposViewModel.getAll().observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(List<Mequipos> mequipos) {
                if (mequipos.size()>0){
                    adaptadorEquipos.setWords(mequipos);
                }else{
                    Toast.makeText(RegistroEquipo.this, "no hay datos bateria", Toast.LENGTH_SHORT).show();
                }
                
            }
        });




    }

    StringRequest stringRequest;
    int valor;
    Application application;
    private void agregar_equipo(final String id) {


        String url =IP2+"/api/Torneo/registrar_equipo_en_torneo";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("agregar_equipo: ",""+response);

                try {
                    JSONObject jsonObject;;

                    jsonObject = new JSONObject(response);
                    JSONArray resultJSON = jsonObject.getJSONArray("results");
                    JSONObject jsonNode = resultJSON.getJSONObject(0);
                    valor = jsonNode.optInt("valor");
                    MisEquiposRoomDBRepository misEquiposRoomDBRepository = new MisEquiposRoomDBRepository (application);
                    if (valor==1){


                        misEquiposRoomDBRepository.actualizarEstado1(id);

                    }else {
                        misEquiposRoomDBRepository.actualizarEstado0(id);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                parametros.put("id_equipo",id);
                parametros.put("id_torneo_grupo",id_grupo);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());

                Log.d("reg_equipo", "en grupo : "+parametros);

                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }

}
