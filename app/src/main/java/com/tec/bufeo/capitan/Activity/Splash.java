package com.tec.bufeo.capitan.Activity;

import android.app.Application;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.tec.bufeo.capitan.Activity.SliderInicial.MainActivity;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.Notificaciones.Repository.NotificacionesWebServiceRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.Notificaciones.ViewModels.NotificacionesViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Repository.FeedWebServiceRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Repository.MisEquiposWebServiceRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    public TimerTask task;
    private static final long TIEMPO = 1;
    Preferences preferences;
    FeedListViewModel feedListViewModel;
    MisEquiposViewModel misEquiposViewModel;
    NotificacionesViewModel notificacionesViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        preferences =  new Preferences(this);
       //Obtenemos los datos del sharetpreferense, si el usuario se logueo antes, nops manda directo al menu principal, caso contrario denbe loguearse

        feedListViewModel = ViewModelProviders.of(this).get(FeedListViewModel.class);
        misEquiposViewModel = ViewModelProviders.of(this).get(MisEquiposViewModel.class);
        notificacionesViewModel = ViewModelProviders.of(this).get(NotificacionesViewModel.class);


        if(!preferences.getIdUsuarioPref().equals("")){

            inicio();
        }
        else{
            Intent i1= new Intent(getApplicationContext(), Login.class);
            startActivity(i1);
            finish();
        }





    }

    private void inicio() {

        feedListViewModel.getAllPosts().observe(this, new Observer<List<ModelFeed>>() {
            @Override
            public void onChanged(List<ModelFeed> modelFeeds) {
                if (modelFeeds.size()>0){

                    limite_sup = modelFeeds.get(modelFeeds.size()-1).getLimite_sup();
                    limite_inf = modelFeeds.get(modelFeeds.size()-1).getLimite_inf();

                    //Toast.makeText(getContext(), "sup: " + limite_sup + " inf: " + limite_inf, Toast.LENGTH_SHORT).show();
                }else{
                    limite_sup= "0";
                    limite_inf= "0";
                }

                preferences.saveValuePORT("lim_sup",limite_sup);
                preferences.saveValuePORT("lim_inf",limite_inf);

                cargarFeed();
            }
        });

        cargarEquipos();
        cargarNotificaciones();
        Tarea tarea = new Tarea();
        tarea.execute();

        //Especificamos la tarea que debe hacer
        task = new TimerTask() {
            @Override
            public void run() {

                Intent i= new Intent(getApplicationContext(),MenuPrincipal.class);
                i.putExtra("inicio","inicio");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        };
    }

    public class Tarea extends AsyncTask<Void,Integer,Void> {

        int progreso;

        @Override
        protected void onPreExecute() {
            progreso = 0;
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (progreso < 100){

                progreso++;
                publishProgress(progreso);
                SystemClock.sleep(1);
            }
            //Inicia la tarea
            Timer timer = new Timer();
            timer.schedule(task,TIEMPO);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    String limite_sup,limite_inf;
    Application application;
    public void cargarFeed(){
        Log.d("feed", "cargarFeed: " +limite_inf + " - " + limite_sup );
        FeedWebServiceRepository feedTorneoWebServiceRepository = new FeedWebServiceRepository(application);
        feedTorneoWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),limite_sup,limite_inf,preferences.getToken(),"","feed");
    }
    public void cargarEquipos(){
        MisEquiposWebServiceRepository misEquiposWebServiceRepository =  new MisEquiposWebServiceRepository(application);
        misEquiposWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),"mi_equipo",preferences.getToken(),"");
        misEquiposWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),"otro_equipo",preferences.getToken(),"");
    }

    public void cargarNotificaciones() {
        NotificacionesWebServiceRepository notificacionesWebServiceRepository = new NotificacionesWebServiceRepository(application);
        notificacionesWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(), preferences.getToken());
    }

}
