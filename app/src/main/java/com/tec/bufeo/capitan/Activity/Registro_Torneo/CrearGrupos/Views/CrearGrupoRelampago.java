package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearEquipos.RegistrarEquipoEnGrupo;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Models.Grupos;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.ViewModels.GruposListViewModel;
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

public class CrearGrupoRelampago extends AppCompatActivity implements View.OnClickListener {

    GruposListViewModel gruposListViewModel;
    RecyclerView rcv_grupos;
    TextView nombre_torneo_grupo;
    Button btn_agregar_grupos,btnNext;
    EditText nombre_grupo_grupos;
    AdapterGrupos adapterGrupos;
    String id_torneo,nombre_torneo;
    Preferences preferences;
    int cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo_relampago);

        gruposListViewModel = ViewModelProviders.of(this).get(GruposListViewModel.class);


        preferences= new Preferences(this);


        initViews();
        setAdapter();
        cargarvista();
    }

    private void initViews() {
        if(getIntent().getExtras()!=null){
            id_torneo = getIntent().getExtras().getString("id_torneo");

            nombre_torneo = getIntent().getExtras().getString("nombre_torneo");
        }

        rcv_grupos =  findViewById(R.id.rcv_grupos);
        nombre_torneo_grupo =  findViewById(R.id.nombre_torneo_grupo);
        btn_agregar_grupos =  findViewById(R.id.btn_agregar_grupos);
        nombre_grupo_grupos =  findViewById(R.id.nombre_grupo_grupos);
        btnNext =  findViewById(R.id.btnNext);

        nombre_torneo_grupo.setText(nombre_torneo);

        btn_agregar_grupos.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private void setAdapter() {

        adapterGrupos =  new AdapterGrupos(this, new AdapterGrupos.OnItemClickListener() {
            @Override
            public void onItemClick(String dato, Grupos grupos, int position) {


            }
        });

        rcv_grupos.setAdapter(adapterGrupos);
        rcv_grupos.setLayoutManager(new LinearLayoutManager(this));




    }

    private void cargarvista() {

        gruposListViewModel.getIdTorneo(id_torneo,preferences.getToken()).observe(this, new Observer<List<Grupos>>() {
            @Override
            public void onChanged(List<Grupos> grupos) {
                if (grupos.size()>0){
                    adapterGrupos.setWords(grupos);

                }
                cantidad = grupos.size()+1;

            }
        });



    }


    String url = IP2+"/api/Torneo/registrar_grupo";
    StringRequest stringRequest;
    int valor;
    private void crear_grupo() {


        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Torneo","registrar grupo"+response);


                try {
                    JSONObject jsonObject;;

                    jsonObject = new JSONObject(response);
                    JSONArray resultJSON = jsonObject.getJSONArray("results");
                    JSONObject jsonNode = resultJSON.getJSONObject(0);
                    valor = jsonNode.optInt("valor");
                    if (valor==1){
                        gruposListViewModel.getIdTorneo(id_torneo,preferences.getToken());
                        nombre_grupo_grupos.setText("");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nombre_grupo_grupos.setText("");
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();



            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();

                parametros.put("id_torneo", id_torneo);
                parametros.put("grupo_nombre", nombre_grupo_grupos.getText().toString());
                parametros.put("app", "true");
                parametros.put("grupo_nombre", preferences.getToken());


                Log.e("torneo", "getParams: "+parametros.toString() );
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }

    public void agregar_grupo_a_bd(){
        Grupos grupos =  new Grupos();
        grupos.setId_grupo(String.valueOf(cantidad));
        grupos.setNombre_grupo(nombre_grupo_grupos.getText().toString());
        grupos.setId_torneo(id_torneo);
        grupos.setEstado("1");
        gruposListViewModel.insert(grupos);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btn_agregar_grupos)){

            if (!nombre_grupo_grupos.getText().equals("")){

                agregar_grupo_a_bd();
            }
            crear_grupo();

        }if (view.equals(btnNext)) {

            Intent i = new Intent(this, RegistrarEquipoEnGrupo.class);
            i.putExtra("id_torneo",id_torneo);
            startActivity(i);
        }
    }


}
