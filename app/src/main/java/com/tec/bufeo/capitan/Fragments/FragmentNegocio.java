package com.tec.bufeo.capitan.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

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
import android.widget.LinearLayout;

import com.tec.bufeo.capitan.Activity.DetalleNegocio;
import com.tec.bufeo.capitan.Activity.Negocios.Views.MisNegociosActivity;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoEmpresas;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;



public class FragmentNegocio extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public  String empresa_id;
    AdaptadorListadoEmpresas adaptadorEmpresas;
    public ArrayList<Empresas> arrayempresas;
    DataConnection dc;
    DataConnection dc1;
    RecyclerView rcv_empresas;
    LinearLayout layout_carga;
    CardView cdv_mensaje;
    Toolbar tlb_negocios;
    SwipeRefreshLayout swipeRefreshLayout;
    Activity activity;
    Context context;
    Preferences preferences;
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
        layout_carga = view.findViewById(R.id.layout_carga);

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

            layout_carga.setVisibility(View.INVISIBLE);
            swipeRefreshLayout.setRefreshing(false);


            GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
            rcv_empresas.setLayoutManager(linearLayoutManager);

            adaptadorEmpresas = new AdaptadorListadoEmpresas(getActivity(), arrayempresas, R.layout.rcv_item_card_negocios, new AdaptadorListadoEmpresas.OnItemClickListener() {
                @Override
                public void onItemClick(Empresas empresas, final int position) {

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





    @Override
    public void onClick(View v) {

        if (v.equals(mis_negocios)){
            Intent i = new Intent(context, MisNegociosActivity.class);
            i.putExtra("empresa_id",empresa_id);
            context.startActivity(i);
        }
    }

}