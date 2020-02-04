package com.tec.bufeo.capitan.Activity.MisMovimientos.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.Movimientos;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Repository.MovimientosRoomDBRepository;
import com.tec.bufeo.capitan.Activity.MisMovimientos.ViewModels.MovimientosViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class MisMovimientos extends AppCompatActivity {

    MovimientosViewModel movimientosViewModel;
    AdaptadorMovimientos adaptadorMovimientos;
    RecyclerView rcv_ver_recargas;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_movimientos);


        movimientosViewModel = ViewModelProviders.of(this).get(MovimientosViewModel.class);
        preferences= new Preferences(this);

        initViews();
        cargarvista();
        setAdapter();
    }

    private void initViews(){

        rcv_ver_recargas= findViewById(R.id.rcv_ver_recargas);
    }
    public void cargarvista(){


        MovimientosRoomDBRepository movimientosRoomDBRepository = new MovimientosRoomDBRepository(application);
        movimientosRoomDBRepository.deleteAllEquipos();

        movimientosViewModel.getAll(preferences.getIdUsuarioPref(),preferences.getToken()).observe(this, new Observer<List<Movimientos>>() {
            @Override
            public void onChanged(@Nullable List<Movimientos> mequipos) {
                adaptadorMovimientos.setWords(mequipos);
                Log.e("mis Equipos", "onChanged: "+mequipos.size() );
            }
        });

    }

    Application application;
    private void setAdapter(){


        adaptadorMovimientos = new AdaptadorMovimientos(this, new AdaptadorMovimientos.OnItemClickListener() {
            @Override
            public void onItemClick(Movimientos mequipos, String tipo, int position) {

            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_ver_recargas.setLayoutManager(linearLayoutManager);
        rcv_ver_recargas.setAdapter(adaptadorMovimientos);

    }
}
