package com.tec.bufeo.capitan.Activity.MisReservas.Views;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.Activity.MisReservas.Repository.MisReservasRoomDBRepository;
import com.tec.bufeo.capitan.Activity.MisReservas.ViewModels.MisReservasViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class MisReservasActivity extends AppCompatActivity {

    MisReservasViewModel movimientosViewModel;
    AdaptadorMisReservas adaptadorMisReservas;
    RecyclerView rcv_ver_recargas;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_movimientos);


        movimientosViewModel = ViewModelProviders.of(this).get(MisReservasViewModel.class);
        preferences= new Preferences(this);

        initViews();
        cargarvista();
        setAdapter();
    }

    private void initViews(){

        rcv_ver_recargas= findViewById(R.id.rcv_ver_recargas);
    }
    public void cargarvista(){


        MisReservasRoomDBRepository movimientosRoomDBRepository = new MisReservasRoomDBRepository(application);
        movimientosRoomDBRepository.deleteAllEquipos();

        movimientosViewModel.getAll(preferences.getIdUsuarioPref(),preferences.getToken()).observe(this, new Observer<List<MisReservas>>() {
            @Override
            public void onChanged(@Nullable List<MisReservas> mequipos) {
                adaptadorMisReservas.setWords(mequipos);
                Log.e("mis Equipos", "onChanged: "+mequipos.size() );
            }
        });

    }

    Application application;
    private void setAdapter(){


        adaptadorMisReservas = new AdaptadorMisReservas(this, new AdaptadorMisReservas.OnItemClickListener() {
            @Override
            public void onItemClick(MisReservas mequipos, String tipo, int position) {

            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_ver_recargas.setLayoutManager(linearLayoutManager);
        rcv_ver_recargas.setAdapter(adaptadorMisReservas);

    }
}
