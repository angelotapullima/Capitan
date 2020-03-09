package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Models.BusquedaEquipos;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository.BusquedaEquiposRoomDBRepository;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository.BusquedaEquiposWebServiceRepository;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.ViewModels.BusquedaEquiposViewModel;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Model.EquiposGrupo;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Repository.EquiposGrupoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.ViewModel.EquiposGrupoViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquiposRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
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
import static java.security.AccessController.getContext;

public class RegistroEquipoEnGrupo extends AppCompatActivity implements View.OnClickListener {

    BusquedaEquiposViewModel busquedaEquiposViewModel;/*
    MisEquiposViewModel misEquiposViewModel;*/
    EquiposGrupoViewModel equiposGrupoViewModel;
    public ArrayList<EquiposGrupo> listaDatos=new ArrayList();
    RecyclerView rcv_busqueda_requipos,rcv_equipos_elegidos;



    AdapterEquiposVacio adapterEquiposVacio;
    AdapterEquiposSeleccionado adapterEquiposSeleccionado;


    EquiposGrupo equiposGrupo;
    String id_grupo;
    Preferences preferences;
    ImageView finishRegistroEquipo;
    BusquedaEquiposRoomDBRepository busquedaEquiposRoomDBRepository;
    EquiposGrupoRoomDBRepository equiposGrupoRoomDBRepository;
    ArrayList<BusquedaEquipos> arrayListEquipos = new ArrayList<>();
    Application application;
    TextView cant_Elegidos,cant_disponibles;
    EditText txt_busqueda_equipos;
    FloatingActionButton fab_terminar_equipos_grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_equipo_en_grupo);

        busquedaEquiposViewModel = ViewModelProviders.of(this).get(BusquedaEquiposViewModel.class);
        /*misEquiposViewModel = ViewModelProviders.of(this).get(MisEquiposViewModel.class);*/
        equiposGrupoViewModel = ViewModelProviders.of(this).get(EquiposGrupoViewModel.class);


        busquedaEquiposRoomDBRepository = new BusquedaEquiposRoomDBRepository(application);
        equiposGrupoRoomDBRepository = new EquiposGrupoRoomDBRepository(application);

        equiposGrupoRoomDBRepository.deleteAllEquipos();

        equiposGrupo = new EquiposGrupo();
        preferences= new Preferences(this);
        id_grupo =getIntent().getExtras().getString("id_grupo");

        initViews();
        setAdapter();
        cargarvista();
    }

    private void initViews() {
        rcv_busqueda_requipos = findViewById(R.id.rcv_busqueda_requipos);
        rcv_equipos_elegidos = findViewById(R.id.rcv_equipos_elegidos);
        cant_Elegidos = findViewById(R.id.cant_Elegidos);
        finishRegistroEquipo = findViewById(R.id.finishRegistroEquipo);
        cant_disponibles = findViewById(R.id.cant_disponibles);
        txt_busqueda_equipos = findViewById(R.id.txt_busqueda_equipos);
        fab_terminar_equipos_grupo = findViewById(R.id.fab_terminar_equipos_grupo);

        txt_busqueda_equipos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String v = String.valueOf(s);
                buscar(v);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fab_terminar_equipos_grupo.setOnClickListener(this);
        finishRegistroEquipo.setOnClickListener(this);
    }

    private void buscar(String v) {
        BusquedaEquiposWebServiceRepository busquedaEquiposWebServiceRepository = new BusquedaEquiposWebServiceRepository(application);
        busquedaEquiposWebServiceRepository.providesWebService("busqueda","0","0",v,preferences.getToken());

        busquedaEquiposViewModel.getSearh(v).observe(this, new Observer<List<BusquedaEquipos>>() {
            @Override
            public void onChanged(List<BusquedaEquipos> busquedaEquipos) {
                adapterEquiposVacio.setWords(busquedaEquipos);
            }
        });
    }
    private void setAdapter() {

        adapterEquiposVacio = new AdapterEquiposVacio(this, new AdapterEquiposVacio.OnItemClickListener() {
            @Override
            public void onItemClick(BusquedaEquipos mequipos, String tipo, int position) {
                if (tipo.equals("cardEquipos_vacio")){
                    equiposGrupo.setEquipo_id(mequipos.getEquipo_id());
                    equiposGrupo.setEquipo_nombre(mequipos.getEquipo_nombre());
                    equiposGrupo.setEquipo_foto(mequipos.getEquipo_foto());
                    equiposGrupo.setCapitan_nombre(mequipos.getCapitan_nombre());
                    equiposGrupo.setCapitan_id(mequipos.getCapitan_id());
                    equiposGrupoRoomDBRepository.insertSeleccionados(equiposGrupo);
                }
            }
        });

        rcv_busqueda_requipos.setAdapter(adapterEquiposVacio);
        rcv_busqueda_requipos.setLayoutManager(new LinearLayoutManager(this));


        adapterEquiposSeleccionado = new AdapterEquiposSeleccionado(this, new AdapterEquiposSeleccionado.OnItemClickListener() {
            @Override
            public void onItemClick(EquiposGrupo mequipos, String tipo, int position) {
                if (tipo.equals("contenedor_equipos")){
                    equiposGrupoRoomDBRepository.deleteID(mequipos.getEquipo_id());
                }
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(layoutManager.HORIZONTAL);
        rcv_equipos_elegidos.setLayoutManager(layoutManager);
        rcv_equipos_elegidos.setAdapter(adapterEquiposSeleccionado);


    }


    private void cargarvista() {

        /*misEquiposViewModel.getAll().observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(List<Mequipos> mequipos) {
                if (mequipos.size() > 0) {
                    arrayListEquipos.clear();
                    for (int i = 0; i < mequipos.size(); i++) {
                        BusquedaEquipos busquedaEquipos = new BusquedaEquipos();

                        busquedaEquipos.setEquipo_id(mequipos.get(i).getEquipo_id());
                        busquedaEquipos.setEquipo_nombre(mequipos.get(i).getEquipo_nombre());
                        busquedaEquipos.setEquipo_foto(mequipos.get(i).getEquipo_foto());
                        busquedaEquipos.setCapitan_nombre(mequipos.get(i).getCapitan_nombre());
                        busquedaEquipos.setCapitan_id(mequipos.get(i).getCapitan_id());
                        busquedaEquipos.setEstado_seleccion("vacio");
                        arrayListEquipos.add(busquedaEquipos);
                    }


                    Log.e("Tamaño", "onChanged: " + arrayListEquipos.size());
                    adapterEquiposVacio.setWords(arrayListEquipos);
                }

            }
        });*/
        busquedaEquiposViewModel.getmAllVacio("0", "0", preferences.getToken()).observe(this, new Observer<List<BusquedaEquipos>>() {
            @Override
            public void onChanged(List<BusquedaEquipos> busquedaEquipos) {
                adapterEquiposVacio.setWords(busquedaEquipos);
                cant_disponibles.setText("Equipos Disponibles " + busquedaEquipos.size());
            }
        });

        equiposGrupoViewModel.getAllEquipoGrupo().observe(this, new Observer<List<EquiposGrupo>>() {
            @Override
            public void onChanged(List<EquiposGrupo> equiposGrupos) {
                adapterEquiposSeleccionado.setWords(equiposGrupos);
                cant_Elegidos.setText("Equipos Elegidos " + equiposGrupos.size());
                listaDatos.clear();
                listaDatos.addAll(equiposGrupos);

                for (int i=0;listaDatos.size()>i;i++) {
                    conteo = conteo + 1;
                    Log.e("lista datos", "onClick: "+conteo +" :" + listaDatos.get(i).getEquipo_nombre() );
                }
            }
        });









    }

    int conteo=0;
    int conteo_envios=0;
    StringRequest stringRequest;
    int valor;
    private void agregar_equipo(final EquiposGrupo equiposGrupo) {



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

                    if (valor==1){
                        conteo_envios++;

                        if (conteo_envios==listaDatos.size()){
                            Toast.makeText(RegistroEquipoEnGrupo.this, "Registro completo", Toast.LENGTH_SHORT).show();

                            dialog_cargando.dismiss();
                            finish();
                        }
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
                dialog_cargando.dismiss();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_equipo",equiposGrupo.getEquipo_id());
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

    @Override
    public void onClick(View v) {
        if (v.equals(fab_terminar_equipos_grupo)){
            if (listaDatos.size()>0){
                dialogoCompletado();
            }

        }else if(v.equals(finishRegistroEquipo)){
            onBackPressed();
        }
    }




    Dialog dialog_completado;
    public void dialogoCompletado( ){

        dialog_completado = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_completado.setCancelable(true);
        dialog_completado.setContentView(R.layout.dialogo_confirmacion_equipos_en_grupo);

        dialog_completado.show();



        TextView si = dialog_completado.findViewById(R.id.si);
        TextView texto = dialog_completado.findViewById(R.id.texto);
        TextView no = dialog_completado.findViewById(R.id.no);

        String tex = "Se registraron " + listaDatos.size() + " equipos en el torneo";
        texto.setText(tex);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_completado.dismiss();
            }
        });

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tamaño de lista", "onClick: "+listaDatos.size() );


                    dialog_completado.dismiss();
                    dialogCarga();


                for (int i=0;listaDatos.size()>i;i++){


                    conteo = conteo+1;

                    Log.e("registro jugadores", "onClick: "+conteo +" :" + listaDatos.get(i).getEquipo_nombre() );
                    //registarJugadores(listaDatos.get(i));
                    agregar_equipo(listaDatos.get(i));

                }

            }
        });

    }

    Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);

        dialog_cargando.show();

    }
}
