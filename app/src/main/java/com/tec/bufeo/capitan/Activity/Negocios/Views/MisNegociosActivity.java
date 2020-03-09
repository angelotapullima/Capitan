package com.tec.bufeo.capitan.Activity.Negocios.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tec.bufeo.capitan.Activity.DetalleNegocio;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoEmpresas;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;

public class MisNegociosActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public  String empresa_id;
    AdaptadorListadoEmpresas adaptadorEmpresas;
    public ArrayList<Empresas> arrayempresas;
    DataConnection dc;
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

        preferences= new Preferences(this);
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





        dc = new DataConnection(this,"listarMisEmpresas",false);
        new GetEmpresas().execute();

        showToolbar("Mis Negocios",true);
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
    @Override
    public void onRefresh() {

        dc = new DataConnection(this,"listarMisEmpresas",false);
        new GetEmpresas().execute();
    }

    @Override
    public void onClick(View v) {

    }

    public class GetEmpresas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayempresas = dc.getListadoEmpresas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            layout_carga_misNegocios.setVisibility(ProgressBar.GONE);
            SwipeRefreshLayout_misNegocios.setRefreshing(false);

            //Toast.makeText(getActivity(),"Z "+arrayempresas.size(),Toast.LENGTH_SHORT).show();


            GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_mis_empresas.setLayoutManager(linearLayoutManager);

            adaptadorEmpresas = new AdaptadorListadoEmpresas(getApplicationContext(), arrayempresas, R.layout.rcv_item_card_negocios, new AdaptadorListadoEmpresas.OnItemClickListener() {
                @Override
                public void onItemClick(Empresas empresas, final int position) {

                    //Toast.makeText(getActivity(),"ID "+empresas.getEmpresas_id(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), DetalleNegocio.class);
                    intent.putExtra("id_empresa",empresas.getEmpresas_id());
                    intent.putExtra("tipo_usuario","admin");
                    startActivity(intent);

                }
            });
            rcv_mis_empresas.setAdapter(adaptadorEmpresas);

            if (arrayempresas.size() > 0) {
                cdv_mis_mensaje.setVisibility(View.INVISIBLE);
                layout_carga_misNegocios.setVisibility(View.GONE);
            } else {
                cdv_mis_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }



}
