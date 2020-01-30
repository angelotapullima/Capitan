package com.tec.bufeo.capitan.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tec.bufeo.capitan.Activity.DetalleNegocio;
import com.tec.bufeo.capitan.Activity.MisNegocios;
import com.tec.bufeo.capitan.Activity.RegistroNegocio;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoEmpresas;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;



public class FragmentNegocio extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static String empresa_id;
    static AdaptadorListadoEmpresas adaptadorEmpresas;
    public ArrayList<Empresas> arrayempresas;
    public static ArrayList<Empresas> arrayempresaactual;
    DataConnection dc;
    static DataConnection dc1;
    static RecyclerView rcv_empresas;
    static ProgressBar progressBar;
    static CardView cdv_mensaje;
    Toolbar tlb_negocios;
    SwipeRefreshLayout swipeRefreshLayout;
    static Activity activity;
    static Context context;
    static Preferences preferences;
    ImageView mis_negocios;

    public FragmentNegocio() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_negocio, container, false);
        //empresa_id = view.getStringExtra("usuario_nombre");

        preferences= new Preferences(getActivity());
        rcv_empresas = (RecyclerView) view.findViewById(R.id.rcv_empresas);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        cdv_mensaje = (CardView) view.findViewById(R.id.cdv_mensaje);
        mis_negocios = (ImageView) view.findViewById(R.id.mis_negocios);

        empresa_id = getActivity().getIntent().getStringExtra("empresa_id");
        swipeRefreshLayout =  view.findViewById(R.id.SwipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        rcv_empresas.setHasFixedSize(true);




        cdv_mensaje.setVisibility(View.INVISIBLE);

        activity = getActivity();
        context = getContext();

        if (preferences.getTieneNegocio().equals("si")){
            mis_negocios.setOnClickListener(this);
        }else {
            mis_negocios.setVisibility(View.GONE);
        }




        dc = new DataConnection(getActivity(),"listarEmpresas",preferences.getUbigeoId(),false);
        new GetEmpresas().execute();

        return view;

    }

    @Override
    public void onRefresh() {

        dc = new DataConnection(getActivity(),"listarEmpresas",preferences.getUbigeoId(),false);
        new GetEmpresas().execute();
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

            progressBar.setVisibility(ProgressBar.INVISIBLE);
            swipeRefreshLayout.setRefreshing(false);

            //Toast.makeText(getActivity(),"Z "+arrayempresas.size(),Toast.LENGTH_SHORT).show();


            GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_empresas.setLayoutManager(linearLayoutManager);

            adaptadorEmpresas = new AdaptadorListadoEmpresas(getActivity(), arrayempresas, R.layout.rcv_item_card_negocios, new AdaptadorListadoEmpresas.OnItemClickListener() {
                @Override
                public void onItemClick(Empresas empresas, final int position) {

                  //Toast.makeText(getActivity(),"ID "+empresas.getEmpresas_id(), Toast.LENGTH_SHORT).show();

                   Intent intent = new Intent(getContext(), DetalleNegocio.class);
                    intent.putExtra("id_empresa",empresas.getEmpresas_id());
                    intent.putExtra("tipo_usuario","usuario");
                    startActivity(intent);

                }
            });
            rcv_empresas.setAdapter(adaptadorEmpresas);

            if (arrayempresas.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }

    public static class ActualizarEmpresas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayempresaactual = dc1.getListadoEmpresas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



            GridLayoutManager linearLayoutManager = new GridLayoutManager(activity, 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_empresas.setLayoutManager(linearLayoutManager);

            adaptadorEmpresas = new AdaptadorListadoEmpresas(activity, arrayempresaactual, R.layout.rcv_item_card_negocios, new AdaptadorListadoEmpresas.OnItemClickListener() {
                @Override
                public void onItemClick(Empresas empresas, final int position) {

                    Intent intent = new Intent(context,DetalleNegocio.class);
                    intent.putExtra("id_empresa",empresas.getEmpresas_id());
                    intent.putExtra("tipo_usuario","usuario");
                    context.startActivity(intent);
                }
            });

            progressBar.setVisibility(ProgressBar.INVISIBLE);

            rcv_empresas.setAdapter(adaptadorEmpresas);


            if( arrayempresaactual.size()>0){
                cdv_mensaje.setVisibility(View.INVISIBLE);
            }else{
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }

    public static void ActualizarEmpresas(){
        dc1 = new DataConnection(activity,"listarEmpresas",preferences.getUbigeoId(),false);
        new ActualizarEmpresas().execute();
    }

    @Override
    public void onClick(View v) {

        if (v.equals(mis_negocios)){
            Intent i = new Intent(context, MisNegocios.class);
            i.putExtra("empresa_id",empresa_id);
            context.startActivity(i);
        }
    }

}