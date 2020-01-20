package com.tec.bufeo.capitan.Activity.DetalleEquipo;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

public class EstadisticasDeEquiposFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    RecyclerView rcv_estadisticas_equipos;
    Preferences preferences;
    String id_equipo;
    SwipeRefreshLayout swipeEstadisticaEquipos;
    public EstadisticasDeEquiposFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_estadisticas_de_equipos, container, false);
        preferences = new Preferences(getActivity());

        final Bundle bdl = getArguments();


        id_equipo = bdl.getString("id_equipo");
        initViews(view);
        setAdapter();
        cargarvista();
        return  view;
    }

    private void initViews(View view) {
        rcv_estadisticas_equipos = view.findViewById(R.id.rcv_juegadores);
        swipeEstadisticaEquipos = view.findViewById(R.id.swipeJuegadores);

        swipeEstadisticaEquipos.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeEstadisticaEquipos.setOnRefreshListener(this);
    }
    private void setAdapter() {

/*
        adapterJugadores= new AdapterJugadores(getActivity(), new AdapterJugadores.OnItemClickListener() {
            @Override
            public void onItemClick(Jugadores mequipos, String tipo, int position) {


            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_estadisticas_equipos.setLayoutManager(linearLayoutManager);
        rcv_estadisticas_equipos.setAdapter(adapterJugadores);*/
    }
    private void cargarvista() {
/*
        jugadoresViewModel.getAllMisJugadores(id_equipo,preferences.getToken(),"si").observe(this, new Observer<List<Jugadores>>() {
            @Override
            public void onChanged(List<Jugadores> jugadores) {
                adapterJugadores.setWords(jugadores);
            }
        });*/
    }


    Application application;
    @Override
    public void onRefresh() {
/*
        JugadoresWebServiceRepository jugadoresWebServiceRepository = new JugadoresWebServiceRepository(application);
        jugadoresWebServiceRepository.providesWebService(id_equipo,preferences.getToken(),"si");
        swipeEstadisticaEquipos.setRefreshing(false);*/
    }

}
