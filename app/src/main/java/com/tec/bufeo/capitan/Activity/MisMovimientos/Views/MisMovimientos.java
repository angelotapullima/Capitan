package com.tec.bufeo.capitan.Activity.MisMovimientos.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    TextView saldo_contable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_movimientos);


        movimientosViewModel = ViewModelProviders.of(this).get(MovimientosViewModel.class);
        preferences= new Preferences(this);

        initViews();
        dialogCarga();
        cargarvista();

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
        saldo_contable= findViewById(R.id.saldo_contable);
        saldo_contable.setText(preferences.getSaldo());
    }
    public void cargarvista(){




        movimientosViewModel.getAll(preferences.getIdUsuarioPref(),preferences.getToken()).observe(this, new Observer<List<Movimientos>>() {
            @Override
            public void onChanged(@Nullable List<Movimientos> movimientos) {

               /* MovimientosRoomDBRepository movimientosRoomDBRepository = new MovimientosRoomDBRepository(application);
                movimientosRoomDBRepository.deleteAllEquipos();*/
                if (movimientos.size()>0){

                    dialog_cargando.dismiss();
                    adaptadorMovimientos = new AdaptadorMovimientos(getApplicationContext(), movimientos, new AdaptadorMovimientos.OnItemClickListener() {
                        @Override
                        public void onItemClick(Movimientos mequipos, String tipo, int position) {

                        }
                    });


                    GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
                    rcv_ver_recargas.setLayoutManager(linearLayoutManager);
                    rcv_ver_recargas.setAdapter(adaptadorMovimientos);
                }

                Log.d("mis Equipos", "onChanged: "+movimientos.size() );
            }
        });

    }

    Application application;


    Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(this, android.R.style.Theme_Translucent);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);
        LinearLayout back = dialog_cargando.findViewById(R.id.back);
        LinearLayout layout = dialog_cargando.findViewById(R.id.layout);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cargando.dismiss();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog_cargando.show();

    }
}
