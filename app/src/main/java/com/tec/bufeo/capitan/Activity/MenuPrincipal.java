package com.tec.bufeo.capitan.Activity;

import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Repository.FeedWebServiceRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Models.Mensajes;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.ViewModels.MensajesViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Views.ForoFragment;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.FragmentTorneoPadre;
import com.tec.bufeo.capitan.TabsPrincipales.tabsBuscar.FragmentBuscarPadre;
import com.tec.bufeo.capitan.TabsPrincipales.FragmentInfo;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.Views.FragmentNegocio;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Repository.MisEquiposWebServiceRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class MenuPrincipal extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bnv_menu;
    int id=0;
    //public static String usuario_nombre,usuario_id,usuario_foto,ubigeo_id, usuario_posicion;
    public Fragment fragmentactual;
    FragmentTransaction fragmentTransaction;
    static  String  token = "";
    Preferences preferences;
    SharedPreferences preferencesUser;
    String tokenNuevo;
    BroadcastReceiver BR;
    public static final String registro= "registro";
    private static final String TAG = "FirebaseToken";
    MensajesViewModel mensajesViewModel;
    FeedListViewModel feedListViewModel;
    MisEquiposViewModel misEquiposViewModel;
    String carga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        mensajesViewModel = ViewModelProviders.of(this).get(MensajesViewModel.class);
        feedListViewModel = ViewModelProviders.of(this).get(FeedListViewModel.class);
        misEquiposViewModel = ViewModelProviders.of(this).get(MisEquiposViewModel.class);

        FirebaseApp.initializeApp(this);

        preferences = new Preferences(this);

        carga = getIntent().getExtras().getString("inicio");
        preferencesUser = getSharedPreferences("User", Context.MODE_PRIVATE);

        if (preferences.getCantidadIngreso().equals("1")){
            cargarFeed();
            cargarEquipos();
        }


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener
                ( MenuPrincipal.this,  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        tokenNuevo = instanceIdResult.getToken();
                        Log.d("Token", "onSuccess: " + tokenNuevo);
                        if (tokenNuevo.equals(token)) {
                            Log.d(TAG, "todo esta chevere 1  " + tokenNuevo);
                            Log.d(TAG, "todo esta chevere 2: " + token);
                            //al ser diferente llamamos al asyntask GetActualizar

                            new GetActualizar().execute();
                        } else {
                            Log.d(TAG, "diferente nuevo  " + tokenNuevo);
                            Log.d(TAG, "diferente antiguo: " + token);
                            new GetActualizar().execute();
                        }

                    }
                });
        bnv_menu = (BottomNavigationView) findViewById(R.id.bnv_menu);
        bnv_menu.setOnNavigationItemSelectedListener(this);

        if (carga.equals("mensajes")){
            setMensajesFragment();
        }else{
            setInitialFragment();
        }




        BR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                String to = intent.getStringExtra("tipo");
                String hora_mensaje =intent.getStringExtra("hora");
                String fecha_mensaje =intent.getStringExtra("fecha");
                String idchat_mensaje =intent.getStringExtra("id_chat");
                String idusuario_mensaje =intent.getStringExtra("id_usuario");
                String mensaje_mensaje =intent.getStringExtra("mensaje");

                if (to.equals("mensaje")){
                    registrarMensaje(hora_mensaje,fecha_mensaje,idchat_mensaje,idusuario_mensaje,mensaje_mensaje);

                }
                Toast.makeText(context, "funciona" + to, Toast.LENGTH_SHORT).show();

            }
        };
    }

    private void registrarMensaje(String hora_mensaje, String fecha_mensaje, String idchat_mensaje, String idusuario_mensaje, String mensaje_mensaje) {

        Mensajes mensajes =  new Mensajes();
        mensajes.setChat_id(idchat_mensaje);
        mensajes.setMensaje_contenido(mensaje_mensaje);
        mensajes.setMensajes_id_usuario(idusuario_mensaje);
        mensajes.setMensaje_hora(hora_mensaje);
        mensajes.setMensaje_fecha(fecha_mensaje);
        mensajesViewModel.insertOne(mensajes);

    }

    Application application;
    public void cargarFeed(){
        FeedWebServiceRepository feedTorneoWebServiceRepository = new FeedWebServiceRepository(application);
        feedTorneoWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),"0","0",preferences.getToken(),"","feed");
    }
    public void cargarEquipos(){
        MisEquiposWebServiceRepository misEquiposWebServiceRepository =  new MisEquiposWebServiceRepository(application);
        misEquiposWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),"mi_equipo",preferences.getToken(),"");
        misEquiposWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),"otro_equipo",preferences.getToken(),"");
    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(BR, new IntentFilter(registro));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BR);
        super.onPause();
    }

    Fragment fragment = null;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        int id = item.getItemId();

        if (id == R.id.action_foro) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment= new ForoFragment();;
            fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack("frag").commit();

        }
        if (id == R.id.action_buscar) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment= new FragmentBuscarPadre();;
            fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack("frag").commit();

        }
        if (id == R.id.action_Torneo) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment= new FragmentTorneoPadre();;
            fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack("frag").commit();

        }
        if (id == R.id.action_negocios) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment= new FragmentNegocio();;
            fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack("frag").commit();

        }
        if (id == R.id.action_info) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragment= new FragmentInfo();
            fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack("frag").commit();

        }



        return true;
    }

    private void setInitialFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentactual = new ForoFragment();
        Bundle bundle =  new Bundle();
        bundle.putString("inicio","mensajes");
        fragmentactual.setArguments(bundle);

        fragmentTransaction.add(R.id.content_frame, fragmentactual,"frag");
        fragmentTransaction.commit();
    }

    private void setMensajesFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentactual = new FragmentTorneoPadre();
        fragmentTransaction.add(R.id.content_frame, fragmentactual,"frag");
        fragmentTransaction.commit();
    }



    private  class GetActualizar extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Actualizar();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }

    }

    StringRequest stringRequest;
    //función para actualizar el token
    private void Actualizar(){
        String url =IP2+"/api/User/actualizar_token";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("actualizartoken: ",""+response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //enviamos los parametros id_usuario y el token nuevo asignado al telefono
                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_user",preferences.getIdUsuarioPref());
                parametros.put("token_firebase",tokenNuevo);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                //Log.d("parametros: ",""+parametros);

                return parametros;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);


    }
}