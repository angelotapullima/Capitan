package com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Views;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Models.Goleadores;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Repository.GoleadoresWebServiceRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.ViewModels.GoleadoresListViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class EstadisticasTorneoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView rcv_goleadores;
    String id_torneo;
    Application application;
    Preferences preferences;
    GoleadoresListViewModel goleadoresListViewModel;
    AdaptadorGoleadores adaptadorGoleadores;
    SwipeRefreshLayout swipeGoleadores;


    public EstadisticasTorneoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goleadoresListViewModel = ViewModelProviders.of(this).get(GoleadoresListViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estadisticas_torneo, container, false);

        preferences= new Preferences(getContext());

        initViews(view);
        setAdapter();
        cargarvista();
        return view;
    }

    private void initViews(View view) {
        rcv_goleadores = view.findViewById(R.id.rcv_goleadores);
        swipeGoleadores= view.findViewById(R.id.swipeGoleadores);
        swipeGoleadores.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeGoleadores.setOnRefreshListener(this);



    }
    private void setAdapter() {

        adaptadorGoleadores = new AdaptadorGoleadores(getContext(), new AdaptadorGoleadores.OnItemClickListener() {
            @Override
            public void onItemClick(String dato, Goleadores feedTorneo, int position) {

            }
        });

        rcv_goleadores.setAdapter(adaptadorGoleadores);
        rcv_goleadores.setLayoutManager(new LinearLayoutManager(getContext()));




    }

    private void cargarvista() {
        goleadoresListViewModel.getAllGoleadores(id_torneo,preferences.getToken()).observe(this, new Observer<List<Goleadores>>() {
            @Override
            public void onChanged(List<Goleadores> goleadores) {
                adaptadorGoleadores.setWords(goleadores);
            }
        });



    }


    @Override
    public void onRefresh() {
        GoleadoresWebServiceRepository goleadoresWebServiceRepository = new GoleadoresWebServiceRepository(application);
        goleadoresWebServiceRepository.providesWebService(id_torneo,preferences.getToken());
        swipeGoleadores.setRefreshing(false);
    }
}
