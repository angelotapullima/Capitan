package com.tec.bufeo.capitan.Activity.MisMovimientos.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
        showToolbar("Movimientos",true);
    }
    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.GREEN);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);//y habilitamos la flacha hacia atras

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
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
                Log.d("mis Equipos", "onChanged: "+mequipos.size() );
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
