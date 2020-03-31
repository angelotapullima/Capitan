package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tec.bufeo.capitan.Activity.DetalleNegocio.DetalleNegocio;
import com.tec.bufeo.capitan.Activity.Negocios.Model.Negocios;
import com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios.NegociosWebServiceRepository;
import com.tec.bufeo.capitan.Activity.Negocios.ViewModels.NegociosViewModel;
import com.tec.bufeo.capitan.Activity.Negocios.Views.AdaptadorListadoEmpresas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class MisNegociosActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public  String empresa_id;
    AdaptadorListadoEmpresas adaptadorEmpresas;
    NegociosViewModel negociosViewModel;
     RecyclerView rcv_mis_empresas;
     CardView cdv_mis_mensaje;
    SwipeRefreshLayout SwipeRefreshLayout_misNegocios;
    Activity activity;
    Context context;
    LinearLayout layout_carga_misNegocios;
    Preferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_negocios_activity);

        negociosViewModel = ViewModelProviders.of(this).get(NegociosViewModel.class);
        preferences= new Preferences(this);

        initViews();
        setAdapter();
        cargarvista();

    }

    private void initViews() {
        rcv_mis_empresas = (RecyclerView) findViewById(R.id.rcv_mis_empresas);

        layout_carga_misNegocios = (LinearLayout) findViewById(R.id.layout_carga_misNegocios);
        cdv_mis_mensaje = (CardView) findViewById(R.id.cdv_mis_mensaje);


        empresa_id = getIntent().getStringExtra("empresa_id");
        SwipeRefreshLayout_misNegocios =  findViewById(R.id.SwipeRefreshLayout_misNegocios);

        SwipeRefreshLayout_misNegocios.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        SwipeRefreshLayout_misNegocios.setOnRefreshListener(this);

        rcv_mis_empresas.setHasFixedSize(true);



        cdv_mis_mensaje.setVisibility(View.INVISIBLE);

        activity = this;
        context = getApplicationContext();

        showToolbar("Mis Negocios",true);
    }

    private void setAdapter() {

        adaptadorEmpresas = new AdaptadorListadoEmpresas(getApplicationContext(), new AdaptadorListadoEmpresas.OnItemClickListener() {
            @Override
            public void onItemClick(Negocios negocios, String tipo, int position) {
                if (tipo.equals("img_fotoEmpresa")){
                    Intent intent = new Intent(getApplicationContext(), DetalleNegocio.class);
                    intent.putExtra("id_empresa",negocios.getId_empresa());
                    intent.putExtra("tipo_usuario","admin");
                    startActivity(intent);
                }

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rcv_mis_empresas.setAdapter(adaptadorEmpresas);
        rcv_mis_empresas.setLayoutManager(layoutManager);
    }

    private void cargarvista() {

        negociosViewModel.getAllMisNegocios().observe(this, new Observer<List<Negocios>>() {
            @Override
            public void onChanged(List<Negocios> negocios) {
                if (negocios.size() > 0) {
                    cdv_mis_mensaje.setVisibility(View.INVISIBLE);
                    layout_carga_misNegocios.setVisibility(View.GONE);
                    adaptadorEmpresas.setWords(negocios);
                } else {
                    cdv_mis_mensaje.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.WHITE);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow); //y habilitamos la flacha hacia atras

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }

    Application application;
    @Override
    public void onRefresh() {
        NegociosWebServiceRepository negociosWebServiceRepository =  new NegociosWebServiceRepository(application);
        negociosWebServiceRepository.providesWebService(preferences.getUbigeoId(),preferences.getIdUsuarioPref(),preferences.getToken());

        SwipeRefreshLayout_misNegocios.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {

    }





}
