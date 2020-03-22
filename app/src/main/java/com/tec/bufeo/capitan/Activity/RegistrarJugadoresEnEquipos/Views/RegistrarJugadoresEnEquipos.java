package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.Jugadores;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.JugadoresSeleccionados;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores.JugadoresRoomDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores.JugadoresWebServiceRepository;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.seleccionados.SeleccionadosDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel.Jugadores.JugadoresViewModel;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel.Seleccionados.SeleccionadosViewModel;
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

public class RegistrarJugadoresEnEquipos extends AppCompatActivity implements View.OnClickListener {

    JugadoresViewModel jugadoresViewModel;
    SeleccionadosViewModel seleccionadosViewModel;
    Preferences preferences;
    RecyclerView rcv_elegidos,rcv_jugadores;
    EditText txt_busqueda;
    AdapterJugadores adapterJugadores;
    AdapterElegidos adapterElegidos;
    String id_equipo,nombre_equipo;
    TextView name_equipo;
    JugadoresRoomDBRepository jugadoresRoomDBRepository;
    SeleccionadosDBRepository seleccionadosDBRepository;
    Application application;
    JugadoresSeleccionados jugadoresSeleccionados ;
    ImageView finishRegistroEquipo;

    FloatingActionButton fab_terminar_equipos;
    public ArrayList<JugadoresSeleccionados> listaDatos=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_jugadores_en_equipos);

        jugadoresViewModel = ViewModelProviders.of(this).get(JugadoresViewModel.class);
        seleccionadosViewModel = ViewModelProviders.of(this).get(SeleccionadosViewModel.class);
        jugadoresRoomDBRepository = new JugadoresRoomDBRepository(application);
        seleccionadosDBRepository = new SeleccionadosDBRepository(application);
        preferences = new Preferences(this);
        jugadoresSeleccionados =  new JugadoresSeleccionados();
        id_equipo= getIntent().getExtras().getString("id_equipo");
        nombre_equipo= getIntent().getExtras().getString("nombre");



        seleccionadosDBRepository.deleteAllSeleccionados();
        jugadoresRoomDBRepository.deleteAllJugadores();

        initViews();
        setAdapter();
        cargarvista();

    }

    private void initViews() {

        rcv_elegidos =  findViewById(R.id.rcv_elegidos);
        rcv_jugadores =  findViewById(R.id.rcv_jugadores);
        txt_busqueda =  findViewById(R.id.txt_busqueda);
        name_equipo =  findViewById(R.id.name_equipo);
        fab_terminar_equipos =  findViewById(R.id.fab_terminar_equipos);
        finishRegistroEquipo =  findViewById(R.id.finishRegistroEquipo);



        name_equipo.setText(nombre_equipo);


        txt_busqueda.addTextChangedListener(new TextWatcher() {
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
        fab_terminar_equipos.setOnClickListener(this);
        finishRegistroEquipo.setOnClickListener(this);
    }

    private void buscar(String v) {
        JugadoresWebServiceRepository jugadoresWebServiceRepository = new JugadoresWebServiceRepository(application);
        jugadoresWebServiceRepository.providesWebService(id_equipo,preferences.getToken(),v);

        jugadoresViewModel.searchFeeds(v).observe(this, new Observer<List<Jugadores>>() {
            @Override
            public void onChanged(List<Jugadores> jugadores) {
                adapterJugadores.setWords(jugadores);
            }
        });
    }

    public void cargarvista() {
        jugadoresViewModel.getAllJugadores(id_equipo,preferences.getToken(),"vacio").observe(this, new Observer<List<Jugadores>>() {
            @Override
            public void onChanged(List<Jugadores> jugadores) {
                adapterJugadores.setWords(jugadores);
            }
        });

        seleccionadosViewModel.getAll().observe(this, new Observer<List<JugadoresSeleccionados>>() {
            @Override
            public void onChanged(List<JugadoresSeleccionados> jugadoresSeleccionados) {
                adapterElegidos.setWords(jugadoresSeleccionados);
                listaDatos.clear();
                listaDatos.addAll(jugadoresSeleccionados);

                for (int i=0;listaDatos.size()>i;i++) {
                    conteo = conteo + 1;
                    Log.d("lista datos", "onClick: "+conteo +" :" + listaDatos.get(i).getJugadors_nombre() );
                }


            }
        });


    }
    private void setAdapter(){

        adapterJugadores= new AdapterJugadores(this, new AdapterJugadores.OnItemClickListener() {
            @Override
            public void onItemClick(Jugadores mequipos, String tipo, int position) {

                if (tipo.equals("contenedor_jugadores")){

                    jugadoresSeleccionados.setJugadors_id(mequipos.getJugador_id());
                    jugadoresSeleccionados.setJugadors_foto(mequipos.getJugador_foto());
                    jugadoresSeleccionados.setJugadors_habilidad(mequipos.getJugador_habilidad());
                    jugadoresSeleccionados.setJugadors_nombre(mequipos.getJugador_nombre());
                    jugadoresSeleccionados.setJugadors_numero(mequipos.getJugador_numero());
                    jugadoresSeleccionados.setJugadors_posicion(mequipos.getJugador_posicion());

                    seleccionadosDBRepository.insertSeleccionados(jugadoresSeleccionados);;
                }
            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_jugadores.setLayoutManager(linearLayoutManager);
        rcv_jugadores.setAdapter(adapterJugadores);


        adapterElegidos = new AdapterElegidos(this, new AdapterElegidos.OnItemClickListener() {
            @Override
            public void onItemClick(JugadoresSeleccionados jugadores, String tipo, int position) {
                if (tipo.equals("contenedor_elegidos")){
                    seleccionadosDBRepository.DeleteOne(jugadores.getJugadors_id());
                }

            }
        });


        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(this, 1);
        linearLayoutManager2.setOrientation(linearLayoutManager.HORIZONTAL);
        rcv_elegidos.setLayoutManager(linearLayoutManager2);
        rcv_elegidos.setAdapter(adapterElegidos);
    }


    int conteo=0;
    @Override
    public void onClick(View view) {
        if (view.equals(fab_terminar_equipos)){

            if (listaDatos.size()>0){


                ProgressDialog loading;

                loading = ProgressDialog.show(RegistrarJugadoresEnEquipos.this, "", "Por favor espere...", false, false);
                Log.d("tamaño de lista", "onClick: "+listaDatos.size() );

                for (int i=0;listaDatos.size()>i;i++){


                        conteo = conteo+1;

                        Log.d("registro jugadores", "onClick: "+conteo +" :" + listaDatos.get(i).getJugadors_nombre() );
                        //registarJugadores(listaDatos.get(i));
                        registarJugadores(listaDatos.get(i));


                }
                loading.dismiss();
                //registrosViewModel.deleteAllFeed();
            }else{
                Toast.makeText(RegistrarJugadoresEnEquipos.this, "No hay datos para enviar ", Toast.LENGTH_SHORT).show();
            }
        }else if(view.equals(finishRegistroEquipo)){
            finish();
        }
    }

    int conteo_envios=0;
    StringRequest stringRequest;
    private void registarJugadores(final JugadoresSeleccionados jugadoresSeleccionados){
        dialogCarga();


        String url =IP2+"/api/Torneo/registrar_equipo_usuario";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d("respons e", "registrar_equipo_usuario: "+response.toString() );
                JSONObject json_data;
                int code;
                try {

                    json_data = new JSONObject(response);
                    JSONArray result_json = json_data.optJSONArray("results");
                    JSONObject jsonNodev = result_json.getJSONObject(0);
                    code = jsonNodev.optInt("valor");
                    
                    if (code==1){
                        conteo_envios++;
                        
                        if (conteo_envios==listaDatos.size()){
                            Toast.makeText(RegistrarJugadoresEnEquipos.this, "Registro completo", Toast.LENGTH_SHORT).show();
                            dialog_cargando.dismiss();
                            finish();
                        }
                    }
                    //menssage = result_json.optString("message");




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: "+error.toString() );


            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_equipo",id_equipo);
                parametros.put("id_usuario",jugadoresSeleccionados.getJugadors_id());
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                Log.d("parametros", "parametros: "+parametros.toString() );
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(RegistrarJugadoresEnEquipos.this).addToRequestQueue(stringRequest);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(this, android.R.style.Theme_Translucent);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);
        LinearLayout back = dialog_cargando.findViewById(R.id.back);
        LinearLayout layout = dialog_cargando.findViewById(R.id.layout);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cargando.dismiss();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
        });

        dialog_cargando.show();

    }
}
