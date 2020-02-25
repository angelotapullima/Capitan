package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;

import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository.OtrosTorneosRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository.OtrosTorneosWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.ViewModels.OtrosTorneosViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Views.AdaptadorOtrosTorneos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class MasTorneos extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    OtrosTorneosViewModel otrosTorneosViewModel;
    RecyclerView rcv_otros_torneos;
    Preferences preferences;
    SwipeRefreshLayout swipeOtrosTorneos;
    AdaptadorOtrosTorneos adaptadorOtrosTorneos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_torneos);

        otrosTorneosViewModel = ViewModelProviders.of(this).get(OtrosTorneosViewModel.class);
        preferences =  new Preferences(this);
        initViews();
        cargarvista();
        setAdapter();
        showToolbar("Torneos",true);
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.rgb(76,175,80));                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.GREEN);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);  //y habilitamos la flacha hacia atras

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }
    private void initViews(){

        rcv_otros_torneos = findViewById(R.id.rcv_otros_torneos);
        swipeOtrosTorneos =  findViewById(R.id.swipeOtrosTorneos);

        swipeOtrosTorneos.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeOtrosTorneos.setOnRefreshListener(this);

    }

    public void cargarvista(){

        otrosTorneosViewModel.getAllOtrosTorneos("no").observe(this, new Observer<List<Torneo>>() {
            @Override
            public void onChanged(List<Torneo> torneos) {
                adaptadorOtrosTorneos.setWords(torneos);
            }
        });


    }

    private void setAdapter(){

        adaptadorOtrosTorneos = new AdaptadorOtrosTorneos(this, new AdaptadorOtrosTorneos.OnItemClickListener() {
            @Override
            public void onItemClick(Torneo comments, int position) {

            }
        });




        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_otros_torneos.setLayoutManager(linearLayoutManager);
        rcv_otros_torneos.setAdapter(adaptadorOtrosTorneos);




    }
    Application application;

    public void onRefresh() {

        OtrosTorneosWebServiceRepository otrosTorneosWebServiceRepository =  new OtrosTorneosWebServiceRepository(application);
        otrosTorneosWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken());


        swipeOtrosTorneos.setRefreshing(false);

    }
}
