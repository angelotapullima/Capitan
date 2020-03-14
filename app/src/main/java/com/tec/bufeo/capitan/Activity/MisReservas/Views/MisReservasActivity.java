package com.tec.bufeo.capitan.Activity.MisReservas.Views;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
        setAdapter();
    }

    private void initViews(){

        rcv_mis_reservas= findViewById(R.id.rcv_mis_reservas);
    }
    public void cargarvista(){




        misReservasViewModel.getAll(preferences.getIdUsuarioPref(),preferences.getToken(),"normal").observe(this, new Observer<List<MisReservas>>() {
            @Override
            public void onChanged(@Nullable List<MisReservas> mequipos) {
                adaptadorMisReservas.setWords(mequipos);

            }
        });

    }

    Application application;
    private void setAdapter(){


        adaptadorMisReservas = new AdaptadorMisReservas(this, new AdaptadorMisReservas.OnItemClickListener() {
            @Override
            public void onItemClick(MisReservas misReservas, String tipo, int position) {

                if (tipo.equals("verMas")){
                    Intent i =  new Intent(getApplicationContext(), ConfirmacionReserva.class);
                    i.putExtra("cancha",misReservas.getCancha_nombre());
                    i.putExtra("lugar",misReservas.getEmpresa_nombre());
                    i.putExtra("hora",misReservas.getReserva_hora());
                    i.putExtra("fecha",misReservas.getReserva_fecha());
                    i.putExtra("nombre",misReservas.getReserva_nombre());
                    i.putExtra("direccion",misReservas.getEmpresa_direccion());
                    i.putExtra("telefono",misReservas.getEmpresa_telefono_1());
                    i.putExtra("telefono2",misReservas.getEmpresa_telefono_2());


                    float totalex;

                    if (Float.parseFloat(misReservas.getReserva_pago2())>0){

                        totalex= Float.parseFloat(misReservas.getReserva_pago1()) + Float.parseFloat(misReservas.getReserva_pago2());

                    }else{
                        totalex = Float.parseFloat(misReservas.getReserva_pago1());
                    }
                    i.putExtra("precio",String.valueOf(totalex));
                    startActivity(i);
                }
            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_mis_reservas.setLayoutManager(linearLayoutManager);
        rcv_mis_reservas.setAdapter(adaptadorMisReservas);

    }
}
