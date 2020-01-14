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
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquipos.MisEquiposWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquipos.MisEquiposViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Views.AdaptadorMiEquipo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class MasMisEquipos extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    MisEquiposViewModel misEquiposViewModel;
    AdaptadorMiEquipo adaptadorMiEquipo;
    RecyclerView rcv_mis_equipo;
    Preferences preferences;
    SwipeRefreshLayout swipeMisEquipos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_mis_equipos);

        misEquiposViewModel = ViewModelProviders.of(this).get(MisEquiposViewModel.class);
        preferences =  new Preferences(this);
        initViews();
        cargarvista();
        setAdapter();
    }

    private void initViews(){

        rcv_mis_equipo = findViewById(R.id.rcv_mis_equipo);
        swipeMisEquipos =  findViewById(R.id.swipeMisEquipos);

        swipeMisEquipos.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeMisEquipos.setOnRefreshListener(this);

    }

    public void cargarvista(){

        misEquiposViewModel.getAllEquipo("si").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(@Nullable List<Mequipos> mequipos) {
                adaptadorMiEquipo.setWords(mequipos);
            }
        });


    }

    private void setAdapter(){


        adaptadorMiEquipo =  new AdaptadorMiEquipo(this,"mas", new AdaptadorMiEquipo.OnItemClickListener() {
            @Override
            public void onItemClick(Mequipos mequipos, int position) {

                Intent intent = new Intent(MasMisEquipos.this, DetalleEquipoNuevo.class);
                intent.putExtra("id_equipo", mequipos.getEquipo_id());
                intent.putExtra("nombre_equipo", mequipos.getEquipo_nombre());
                intent.putExtra("foto_equipo", mequipos.getEquipo_foto());
                startActivity(intent);
            }
        });



        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_mis_equipo.setLayoutManager(linearLayoutManager);
        rcv_mis_equipo.setAdapter(adaptadorMiEquipo);



    }
    Application application;

    public void onRefresh() {


        MisEquiposWebServiceRepository misEquiposWebServiceRepository= new MisEquiposWebServiceRepository(application);
        misEquiposWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),"mi_equipo");
        swipeMisEquipos.setRefreshing(false);

    }
}
