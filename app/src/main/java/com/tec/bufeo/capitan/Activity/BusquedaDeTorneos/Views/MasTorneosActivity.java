package com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Views;

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

import com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Models.MasTorneos;
import com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Repository.MasTorneosWebServiceRepository;
import com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.ViewModels.MasTorneosViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository.OtrosTorneosRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository.OtrosTorneosWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.ViewModels.OtrosTorneosViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Views.AdaptadorOtrosTorneos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class MasTorneosActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    MasTorneosViewModel masTorneosViewModel;
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

        masTorneosViewModel = ViewModelProviders.of(this).get(MasTorneosViewModel.class);
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
        MasTorneosWebServiceRepository masTorneosWebServiceRepository = new MasTorneosWebServiceRepository(application);
        masTorneosWebServiceRepository.providesWebService("busqueda",preferences.getIdUsuarioPref(),v,preferences.getToken());


    }
    public void cargarvista(){

        masTorneosViewModel.getmAll(preferences.getIdUsuarioPref(),preferences.getToken()).observe(this, new Observer<List<MasTorneos>>() {
            @Override
            public void onChanged(List<MasTorneos> masTorneos) {
                adaptadorBusquedaTorneos.setWords(masTorneos);
            }
        });



    }

    private void setAdapter(){

        adaptadorBusquedaTorneos = new AdaptadorBusquedaTorneos(this, new AdaptadorBusquedaTorneos.OnItemClickListener() {
            @Override
            public void onItemClick(MasTorneos comments, int position) {

            }
        });




        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_otros_torneos.setLayoutManager(linearLayoutManager);
        rcv_otros_torneos.setAdapter(adaptadorBusquedaTorneos);




    }
    Application application;

    public void onRefresh() {

        OtrosTorneosWebServiceRepository otrosTorneosWebServiceRepository =  new OtrosTorneosWebServiceRepository(application);
        otrosTorneosWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken());


        swipeOtrosTorneos.setRefreshing(false);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(finishBusquedaTorneo)){
            finish();
        }
    }
}
