package com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Views;

import android.app.Application;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Models.EquiposTorneo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Repository.EquiposTorneoWebServiceRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.ViewModels.EquiposTorneoViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Views.AdaptadorEquipos;
import com.tec.bufeo.capitan.R;

import java.util.List;


public class EquiposDtorneoFragment extends Fragment {


    TextView numero_equiposDtorneo;
    RecyclerView rcv_equipostorneo;
    EquiposTorneoViewModel equiposTorneoViewModel;
    AdapterEquiposTorneo adapterEquiposTorneo;
    String id_torneo;
    public EquiposDtorneoFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        equiposTorneoViewModel = ViewModelProviders.of(this).get(EquiposTorneoViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equipos_dtorneo, container, false);
        final Bundle bdl = getArguments();


        id_torneo = bdl.getString("id_torneo");

        initViews(view);
        setAdapter();
        cargarvista();
        equiposTorneo();

        return  view;
    }

    private void setAdapter() {

        adapterEquiposTorneo = new AdapterEquiposTorneo(getContext(), new AdapterEquiposTorneo.OnItemClickListener() {
            @Override
            public void onItemClick(EquiposTorneo mequipos, int position) {

            }
        });
        rcv_equipostorneo.setAdapter(adapterEquiposTorneo);
        rcv_equipostorneo.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void cargarvista() {
        equiposTorneoViewModel.getAllEquiposTorneo(id_torneo).observe(this, new Observer<List<EquiposTorneo>>() {
            @Override
            public void onChanged(@Nullable List<EquiposTorneo> equiposTorneos) {
                if (equiposTorneos.size()>0){

                    adapterEquiposTorneo.setWords(equiposTorneos);
                    numero_equiposDtorneo.setText(String.valueOf(equiposTorneos.size()));
                }else{
                    numero_equiposDtorneo.setText("0");
                }
            }
        });
    }

    private void initViews(View view) {
        numero_equiposDtorneo = view.findViewById(R.id.numero_equiposDtorneo);
        rcv_equipostorneo = view.findViewById(R.id.rcv_equipostorneo);

    }

    Application application;
    public void equiposTorneo(){

        EquiposTorneoWebServiceRepository equiposTorneoWebServiceRepository =  new EquiposTorneoWebServiceRepository(application);
        equiposTorneoWebServiceRepository.providesWebService(id_torneo);
    }


}
