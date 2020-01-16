package com.tec.bufeo.capitan.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.DetalleEquipoNuevo;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquiposWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Views.AdaptadorEquipos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class MasEquipos extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    MisEquiposViewModel misEquiposViewModel;
    RecyclerView rcv_otros_equipo;
    Preferences preferences;
    SwipeRefreshLayout swipeOtrosEquipos;
    AdaptadorEquipos adaptadorEquipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_equipos);

        misEquiposViewModel = ViewModelProviders.of(this).get(MisEquiposViewModel.class);
        preferences =  new Preferences(this);
        initViews();
        cargarvista();
        setAdapter();
    }

    private void initViews(){

        rcv_otros_equipo = findViewById(R.id.rcv_otros_equipo);
        swipeOtrosEquipos =  findViewById(R.id.swipeOtrosEquipos);

        swipeOtrosEquipos.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeOtrosEquipos.setOnRefreshListener(this);

    }

    public void cargarvista(){

        misEquiposViewModel.getAllEquipo("no").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(@Nullable List<Mequipos> mequipos) {
                adaptadorEquipos.setWords(mequipos);
            }
        });


    }

    private void setAdapter(){


        adaptadorEquipos = new AdaptadorEquipos(this, new AdaptadorEquipos.OnItemClickListener() {
            @Override
            public void onItemClick(Mequipos mequipos, int position) {

                Intent intent = new Intent(MasEquipos.this, DetalleEquipoNuevo.class);
                intent.putExtra("id_equipo", mequipos.getEquipo_id());
                intent.putExtra("nombre_equipo", mequipos.getEquipo_nombre());
                intent.putExtra("foto_equipo", mequipos.getEquipo_foto());
                startActivity(intent);
            }
        });




        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_otros_equipo.setLayoutManager(linearLayoutManager);
        rcv_otros_equipo.setAdapter(adaptadorEquipos);



    }
    Application application;

    public void onRefresh() {


        MisEquiposWebServiceRepository misEquiposWebServiceRepository= new MisEquiposWebServiceRepository(application);
        misEquiposWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),"otro_equipo",preferences.getToken());
        swipeOtrosEquipos.setRefreshing(false);

    }
}
