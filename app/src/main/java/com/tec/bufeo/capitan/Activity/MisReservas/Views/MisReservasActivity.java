package com.tec.bufeo.capitan.Activity.MisReservas.Views;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.bufeo.capitan.Activity.ConfirmacionReserva;
import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.Activity.MisReservas.Repository.MisReservasRoomDBRepository;
import com.tec.bufeo.capitan.Activity.MisReservas.ViewModels.MisReservasViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class MisReservasActivity extends AppCompatActivity {

    MisReservasViewModel misReservasViewModel;
    AdaptadorMisReservas adaptadorMisReservas;
    RecyclerView rcv_mis_reservas;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);


        misReservasViewModel = ViewModelProviders.of(this).get(MisReservasViewModel.class);
        preferences= new Preferences(this);

        initViews();
        cargarvista();

    }

    private void initViews(){

        rcv_mis_reservas= findViewById(R.id.rcv_mis_reservas);
    }
    public void cargarvista(){


        MisReservasRoomDBRepository misReservasRoomDBRepository = new MisReservasRoomDBRepository(application);
        misReservasRoomDBRepository.deleteAllMisReservas();

        misReservasViewModel.getAll(preferences.getIdUsuarioPref(),preferences.getToken()).observe(this, new Observer<List<MisReservas>>() {
            @Override
            public void onChanged(@Nullable List<MisReservas> mequipos) {

                if (mequipos.size()>0){

                    adaptadorMisReservas = new AdaptadorMisReservas(getApplicationContext(), mequipos, new AdaptadorMisReservas.OnItemClickListener() {
                        @Override
                        public void onItemClick(MisReservas mequipos, String tipo, int position) {

                        }
                    });



                    GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
                    rcv_mis_reservas.setLayoutManager(linearLayoutManager);
                    rcv_mis_reservas.setAdapter(adaptadorMisReservas);
                }

            }
        });

    }

    Application application;

}