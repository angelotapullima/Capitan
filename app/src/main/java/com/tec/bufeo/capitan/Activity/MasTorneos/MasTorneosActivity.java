package com.tec.bufeo.capitan.Activity.MasTorneos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Application;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Models.Torneo;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Repository.TorneosWebServiceRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.ViewModels.TorneosViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class MasTorneosActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    TorneosViewModel torneosViewModel;
    RecyclerView rcv_otros_torneos;
    Preferences preferences;
    SwipeRefreshLayout swipeOtrosTorneos;
    AdaptadorBusquedaTorneos adaptadorBusquedaTorneos;
    EditText txt_busqueda_torneos;
    ImageView finishBusquedaTorneo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_torneos);

        torneosViewModel = ViewModelProviders.of(this).get(TorneosViewModel.class);
        preferences =  new Preferences(this);
        initViews();
        cargarvista();
        setAdapter();

    }

    private void initViews(){

        rcv_otros_torneos = findViewById(R.id.rcv_otros_torneos);
        swipeOtrosTorneos =  findViewById(R.id.swipeOtrosTorneos);
        txt_busqueda_torneos =  findViewById(R.id.txt_busqueda_torneos);
        finishBusquedaTorneo =  findViewById(R.id.finishBusquedaTorneo);

        swipeOtrosTorneos.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeOtrosTorneos.setOnRefreshListener(this);


        txt_busqueda_torneos.addTextChangedListener(new TextWatcher() {
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

        finishBusquedaTorneo.setOnClickListener(this);
    }

    private void buscar(String v) {
        TorneosWebServiceRepository torneosWebServiceRepository = new TorneosWebServiceRepository(application);
        torneosWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken(),"busqueda",v);

        torneosViewModel.searchTorneo(v).observe(this, new Observer<List<Torneo>>() {
            @Override
            public void onChanged(List<Torneo> torneos) {
                adaptadorBusquedaTorneos.setWords(torneos);
            }
        });



    }
    public void cargarvista(){

        torneosViewModel.getAllOtrosTorneos("no").observe(this, new Observer<List<Torneo>>() {
            @Override
            public void onChanged(List<Torneo> torneos) {
                adaptadorBusquedaTorneos.setWords(torneos);
            }
        });




    }

    private void setAdapter(){

        adaptadorBusquedaTorneos = new AdaptadorBusquedaTorneos(this, new AdaptadorBusquedaTorneos.OnItemClickListener() {
            @Override
            public void onItemClick(Torneo comments, int position) {

            }
        });




        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_otros_torneos.setLayoutManager(linearLayoutManager);
        rcv_otros_torneos.setAdapter(adaptadorBusquedaTorneos);




    }
    Application application;

    public void onRefresh() {

        TorneosWebServiceRepository torneosWebServiceRepository = new TorneosWebServiceRepository(application);
        torneosWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken(),"torneos","");


        swipeOtrosTorneos.setRefreshing(false);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(finishBusquedaTorneo)){
            finish();
        }
    }
}
