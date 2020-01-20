package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Views;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.DetalleTorneoNuevo;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.RegistroTorneo;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository.MisTorneoWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.ViewModels.MisTorneoViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository.OtrosTorneosWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.ViewModels.OtrosTorneosViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;


public class FragmentTorneoRoque extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    Preferences preferences;
    ImageButton imb_ver_equipofav, imb_agregar_torneo;
    RecyclerView rcv_mis_torneos, rcv_torneos;
    Activity activity;
    Context context;
    MisTorneoViewModel misTorneoViewModel;
    OtrosTorneosViewModel otrosTorneosViewModel;
    AdaptadorMisTorneos adaptadorMisTorneos;
    FloatingActionButton reg_torneo;
    SwipeRefreshLayout swipeTorneos;


    AdaptadorOtrosTorneos adaptadorOtrosTorneos;


    public FragmentTorneoRoque() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        misTorneoViewModel = ViewModelProviders.of(getActivity()).get(MisTorneoViewModel.class);
        otrosTorneosViewModel = ViewModelProviders.of(getActivity()).get(OtrosTorneosViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_torneo_roque, container, false);

        context = getContext();
        activity = getActivity();
        preferences = new Preferences(context);
        initViews(view);
        setAdapter();
        cargarvista();
        return view;
    }

    private void initViews(View view){

        rcv_mis_torneos = (RecyclerView) view.findViewById(R.id.rcv_mis_torneos);
        rcv_torneos = (RecyclerView) view.findViewById(R.id.rcv_torneos);
        imb_agregar_torneo = (ImageButton) view.findViewById(R.id.imb_agregar_torneo);
        reg_torneo = (FloatingActionButton) view.findViewById(R.id.reg_torneo);
        swipeTorneos = (SwipeRefreshLayout) view.findViewById(R.id.swipeTorneos);
        swipeTorneos.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeTorneos.setOnRefreshListener(this);

        reg_torneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegistroTorneo.class);
                startActivity(intent);
            }
        });


    }


    public void cargarvista(){

        misTorneoViewModel.getAllRetos("si").observe(this, new Observer<List<Torneo>>() {
            @Override
            public void onChanged(@Nullable List<Torneo> torneos) {
                adaptadorMisTorneos.setWords(torneos);

            }
        });



        otrosTorneosViewModel.getAllOtrosTorneos("no").observe(this, new Observer<List<Torneo>>() {
            @Override
            public void onChanged(@Nullable List<Torneo> torneos) {
                adaptadorOtrosTorneos.setWords(torneos);
            }
        });


    }



    private void setAdapter(){

        adaptadorMisTorneos =  new AdaptadorMisTorneos(context, new AdaptadorMisTorneos.OnItemClickListener() {
            @Override
            public void onItemClick(Torneo torneo, int position) {

                Intent intent = new Intent(getContext(), DetalleTorneoNuevo.class);
                intent.putExtra("id_torneo", torneo.getId_torneo());
                intent.putExtra("id_usuario", torneo.getUsuario_id());
                intent.putExtra("nombre", torneo.getTorneo_nombre());
                intent.putExtra("descripcion", torneo.getTorneo_descripcion());
                intent.putExtra("lugar", torneo.getTorneo_lugar());
                intent.putExtra("fecha", torneo.getTorneo_fecha());
                intent.putExtra("hora", torneo.getTorneo_hora());
                intent.putExtra("foto", torneo.getFoto_torneo());
                intent.putExtra("organizador", torneo.getTorneo_organizador());
                startActivity(intent);

            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        rcv_mis_torneos.setLayoutManager(linearLayoutManager);
        rcv_mis_torneos.setAdapter(adaptadorMisTorneos);




        adaptadorOtrosTorneos = new AdaptadorOtrosTorneos(context, new AdaptadorOtrosTorneos.OnItemClickListener() {
            @Override
            public void onItemClick(Torneo torneo, int position) {

                Intent intent = new Intent(getContext(), DetalleTorneoNuevo.class);
                intent.putExtra("id_torneo", torneo.getId_torneo());
                intent.putExtra("id_usuario", torneo.getUsuario_id());
                intent.putExtra("nombre", torneo.getTorneo_nombre());
                intent.putExtra("descripcion", torneo.getTorneo_descripcion());
                intent.putExtra("lugar", torneo.getTorneo_lugar());
                intent.putExtra("fecha", torneo.getTorneo_fecha());
                intent.putExtra("hora", torneo.getTorneo_hora());
                intent.putExtra("foto", torneo.getFoto_torneo());
                intent.putExtra("organizador", torneo.getTorneo_organizador());
                startActivity(intent);

            }
        });
        GridLayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 1);
        layoutManager2.setOrientation(layoutManager2.HORIZONTAL);
        rcv_torneos.setLayoutManager(layoutManager2);
        rcv_torneos.setAdapter(adaptadorOtrosTorneos);

    }


    Application application;
    @Override
    public void onRefresh() {


        MisTorneoWebServiceRepository misTorneoWebServiceRepository= new MisTorneoWebServiceRepository(application);
        misTorneoWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken());

        OtrosTorneosWebServiceRepository otrosTorneosWebServiceRepository =  new OtrosTorneosWebServiceRepository(application);
        otrosTorneosWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken());

        swipeTorneos.setRefreshing(false);

    }
}
