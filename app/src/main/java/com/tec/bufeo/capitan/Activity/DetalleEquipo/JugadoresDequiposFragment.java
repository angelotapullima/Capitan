package com.tec.bufeo.capitan.Activity.DetalleEquipo;

import android.app.Application;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.Jugadores;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores.JugadoresWebServiceRepository;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel.Jugadores.JugadoresViewModel;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Views.AdapterJugadores;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class JugadoresDequiposFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {



    JugadoresViewModel jugadoresViewModel;
    RecyclerView rcv_juegadores;
    SwipeRefreshLayout swipeJuegadores;
    AdapterJugadores adapterJugadores;
    String id_equipo;
    Preferences preferences;
    public JugadoresDequiposFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jugadoresViewModel= ViewModelProviders.of(getActivity()).get(JugadoresViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jugadores_dequipos, container, false);
        preferences = new Preferences(getActivity());

        final Bundle bdl = getArguments();


        id_equipo = bdl.getString("id_equipo");
        initViews(view);
        setAdapter();
        cargarvista();
        return view;
    }

    private void initViews(View view) {
        rcv_juegadores = view.findViewById(R.id.rcv_juegadores);
        swipeJuegadores = view.findViewById(R.id.swipeJuegadores);

        swipeJuegadores.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeJuegadores.setOnRefreshListener(this);
    }
    private void setAdapter() {


        adapterJugadores= new AdapterJugadores(getActivity(), new AdapterJugadores.OnItemClickListener() {
            @Override
            public void onItemClick(Jugadores mequipos, String tipo, int position) {


            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_juegadores.setLayoutManager(linearLayoutManager);
        rcv_juegadores.setAdapter(adapterJugadores);
    }
    private void cargarvista() {

        jugadoresViewModel.getAllMisJugadores(id_equipo,preferences.getToken(),"si").observe(this, new Observer<List<Jugadores>>() {
            @Override
            public void onChanged(List<Jugadores> jugadores) {
                adapterJugadores.setWords(jugadores);
            }
        });
    }


    Application application;
    @Override
    public void onRefresh() {

        JugadoresWebServiceRepository jugadoresWebServiceRepository = new JugadoresWebServiceRepository(application);
        jugadoresWebServiceRepository.providesWebService(id_equipo,preferences.getToken(),"si");
        swipeJuegadores.setRefreshing(false);
    }
}
