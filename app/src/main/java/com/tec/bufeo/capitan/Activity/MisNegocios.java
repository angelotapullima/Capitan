package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tec.bufeo.capitan.Adapters.AdaptadorListadoEmpresas;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;

public class MisNegocios extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public  String empresa_id;
    AdaptadorListadoEmpresas adaptadorEmpresas;
    public ArrayList<Empresas> arrayempresas;
    DataConnection dc;
     RecyclerView rcv_mis_empresas;
    ProgressBar progressbar_mis_empresas;
     CardView cdv_mis_mensaje;
    Toolbar tlb_mis_negocios;
    SwipeRefreshLayout SwipeRefreshLayout_misNegocios;
    Activity activity;
    Context context;
    Preferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_negocios);

        preferences= new Preferences(this);
        rcv_mis_empresas = (RecyclerView) findViewById(R.id.rcv_mis_empresas);
        progressbar_mis_empresas = (ProgressBar) findViewById(R.id.progressbar_mis_empresas);
        tlb_mis_negocios = (Toolbar) findViewById(R.id.tlb_mis_negocios);
        cdv_mis_mensaje = (CardView) findViewById(R.id.cdv_mis_mensaje);


        empresa_id = getIntent().getStringExtra("empresa_id");
        SwipeRefreshLayout_misNegocios =  findViewById(R.id.SwipeRefreshLayout_misNegocios);

        SwipeRefreshLayout_misNegocios.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        SwipeRefreshLayout_misNegocios.setOnRefreshListener(this);

        rcv_mis_empresas.setHasFixedSize(true);

        tlb_mis_negocios.setTitle("Mis Negocios");


        cdv_mis_mensaje.setVisibility(View.INVISIBLE);

        activity = this;
        context = getApplicationContext();





        dc = new DataConnection(this,"listarMisEmpresas",false);
        new GetEmpresas().execute();
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

            progressbar_mis_empresas.setVisibility(ProgressBar.INVISIBLE);
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
            } else {
                cdv_mis_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }



}
