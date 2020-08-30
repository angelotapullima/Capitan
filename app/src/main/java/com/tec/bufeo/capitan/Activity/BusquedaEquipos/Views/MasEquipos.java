package com.tec.bufeo.capitan.Activity.BusquedaEquipos.Views;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Models.BusquedaEquipos;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository.BusquedaEquiposWebServiceRepository;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.ViewModels.BusquedaEquiposViewModel;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.DetalleEquipoNuevo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class MasEquipos extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    BusquedaEquiposViewModel busquedaEquiposViewModel;
    RecyclerView rcv_otros_equipo;
    Preferences preferences;
    EditText txt_busqueda_equipos;
    SwipeRefreshLayout swipeOtrosEquipos;
    AdaptadorBusquedaEquipos adaptadorBusquedaEquipos;
    ImageView finishRegistroEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_equipos);

        busquedaEquiposViewModel = ViewModelProviders.of(this).get(BusquedaEquiposViewModel.class);
        preferences =  new Preferences(this);
        initViews();
        cargarvista();
        setAdapter();
    }

    private void initViews(){

        rcv_otros_equipo = findViewById(R.id.rcv_otros_equipo);
        txt_busqueda_equipos = findViewById(R.id.txt_busqueda_equipos);
        finishRegistroEquipo = findViewById(R.id.finishRegistroEquipo);
        swipeOtrosEquipos =  findViewById(R.id.swipeOtrosEquipos);

        swipeOtrosEquipos.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeOtrosEquipos.setOnRefreshListener(this);
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

        finishRegistroEquipo.setOnClickListener(this);
    }

    private void buscar(String v) {
        BusquedaEquiposWebServiceRepository busquedaEquiposWebServiceRepository = new BusquedaEquiposWebServiceRepository(application);
        busquedaEquiposWebServiceRepository.providesWebService("busqueda","0","0",v,preferences.getToken());
    }
    public void cargarvista(){

        busquedaEquiposViewModel.getAll("0","0",preferences.getToken()).observe(this, new Observer<List<BusquedaEquipos>>() {
            @Override
            public void onChanged(List<BusquedaEquipos> busquedaEquipos) {
                adaptadorBusquedaEquipos.setWords(busquedaEquipos);
            }
        });



    }

    private void setAdapter(){

        adaptadorBusquedaEquipos = new AdaptadorBusquedaEquipos(this,"mas", new AdaptadorBusquedaEquipos.OnItemClickListener() {
            @Override
            public void onItemClick(BusquedaEquipos mequipos, String tipo, int position) {
                Intent intent = new Intent(MasEquipos.this, DetalleEquipoNuevo.class);
                intent.putExtra("id_equipo", mequipos.getEquipo_id());
                intent.putExtra("nombre_equipo", mequipos.getEquipo_nombre());
                intent.putExtra("foto_equipo", mequipos.getEquipo_foto());
                intent.putExtra("capitan_equipo", mequipos.getCapitan_nombre());
                intent.putExtra("capitan_id", mequipos.getCapitan_id());
                startActivity(intent);
            }
        });





        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_otros_equipo.setLayoutManager(linearLayoutManager);
        rcv_otros_equipo.setAdapter(adaptadorBusquedaEquipos);



    }
    Application application;

    public void onRefresh() {


        BusquedaEquiposWebServiceRepository busquedaEquiposWebServiceRepository = new BusquedaEquiposWebServiceRepository(application);
        busquedaEquiposWebServiceRepository.providesWebService("carga","0","0","",preferences.getToken());
        swipeOtrosEquipos.setRefreshing(false);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(finishRegistroEquipo)){
            finish();
        }
    }
}
