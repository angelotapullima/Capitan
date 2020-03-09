package com.tec.bufeo.capitan.Activity.Negocios.Views;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tec.bufeo.capitan.Activity.DetalleNegocio;
import com.tec.bufeo.capitan.Activity.MisNegociosActivity;
import com.tec.bufeo.capitan.Activity.Negocios.Model.Negocios;
import com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios.NegociosWebServiceRepository;
import com.tec.bufeo.capitan.Activity.Negocios.ViewModels.NegociosViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;


public class FragmentNegocio extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public  String empresa_id;
    AdaptadorListadoEmpresas adaptadorEmpresas;
    NegociosViewModel negociosViewModel;
    RecyclerView rcv_empresas;
    LinearLayout layout_carga;
    SwipeRefreshLayout swipeRefreshLayout;
    Activity activity;
    Context context;
    Preferences preferences;
    ImageView mis_negocios;

    public FragmentNegocio() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //    progressDialog = new ProgressDialog(getActivity());
        negociosViewModel = ViewModelProviders.of(getActivity()).get(NegociosViewModel.class);
        //commentsListViewModel =  ViewModelProviders.of(getActivity()).get(VersusListViewModel.class);
    }
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_negocio, container, false);



        initViews(view);
        setAdapter();
        cargarvista();
        return view;

    }

    private void initViews(View view) {
        preferences= new Preferences(getActivity());
        rcv_empresas = (RecyclerView) view.findViewById(R.id.rcv_empresas);
        layout_carga = view.findViewById(R.id.layout_carga);


        mis_negocios = (ImageView) view.findViewById(R.id.mis_negocios);


        swipeRefreshLayout =  view.findViewById(R.id.SwipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        rcv_empresas.setHasFixedSize(true);



        activity = getActivity();
        context = getContext();

        if (preferences.getTieneNegocio().equals("si")){
            mis_negocios.setOnClickListener(this);
        }else {
            mis_negocios.setVisibility(View.GONE);
        }
    }
    private void setAdapter() {

        negociosViewModel.getAllNegocios(preferences.getUbigeoId(),preferences.getIdUsuarioPref(),preferences.getToken()).observe(this, new Observer<List<Negocios>>() {
            @Override
            public void onChanged(List<Negocios> negocios) {

                if (negocios.size() > 0) {

                    layout_carga.setVisibility(View.GONE);
                    adaptadorEmpresas.setWords(negocios);
                } else {

                }


            }
        });
    }
    private void cargarvista() {
        adaptadorEmpresas = new AdaptadorListadoEmpresas(getContext(), new AdaptadorListadoEmpresas.OnItemClickListener() {
            @Override
            public void onItemClick(Negocios negocios, String tipo, int position) {
                if (tipo.equals("img_fotoEmpresa")){
                    Intent intent = new Intent(getContext(), DetalleNegocio.class);
                    intent.putExtra("id_empresa",negocios.getId_empresa());
                    intent.putExtra("tipo_usuario","usuario");
                    startActivity(intent);
                }

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rcv_empresas.setAdapter(adaptadorEmpresas);
        rcv_empresas.setLayoutManager(layoutManager);

    }

    Application application;
    @Override
    public void onRefresh() {

        NegociosWebServiceRepository negociosWebServiceRepository =  new NegociosWebServiceRepository(application);
        negociosWebServiceRepository.providesWebService(preferences.getUbigeoId(),preferences.getIdUsuarioPref(),preferences.getToken());

        swipeRefreshLayout.setRefreshing(false);

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