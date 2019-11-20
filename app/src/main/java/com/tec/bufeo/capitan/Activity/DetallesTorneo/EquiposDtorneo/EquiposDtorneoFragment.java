package com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.OtrosEquipos.OtrosEquiposViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Views.AdaptadorEquipos;
import com.tec.bufeo.capitan.R;

import java.util.List;


public class EquiposDtorneoFragment extends Fragment {


    TextView numero_equiposDtorneo;
    RecyclerView rcv_equipostorneo;
    OtrosEquiposViewModel otrosEquiposViewModel;
    AdaptadorEquipos adaptadorEquipos;
    public EquiposDtorneoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        otrosEquiposViewModel = ViewModelProviders.of(this).get(OtrosEquiposViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equipos_dtorneo, container, false);

        initViews(view);
        setAdapter();
        cargarvista();

        return  view;
    }

    private void setAdapter() {

        adaptadorEquipos = new AdaptadorEquipos(getContext(), new AdaptadorEquipos.OnItemClickListener() {
            @Override
            public void onItemClick(Mequipos mequipos, int position) {

            }
        });
        rcv_equipostorneo.setAdapter(adaptadorEquipos);
        rcv_equipostorneo.setLayoutManager(new LinearLayoutManager(getContext()));

    }








    private void cargarvista() {
        otrosEquiposViewModel.getAllOtrosEquipos("no").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(@Nullable List<Mequipos> mequipos) {
                adaptadorEquipos.setWords(mequipos);
                if (mequipos.size()>0){
                    numero_equiposDtorneo.setText(String.valueOf(mequipos.size()));
                }else {
                    numero_equiposDtorneo.setText("0");
                }


            }
        });



    }

    private void initViews(View view) {
        numero_equiposDtorneo = view.findViewById(R.id.numero_equiposDtorneo);
        rcv_equipostorneo = view.findViewById(R.id.rcv_equipostorneo);

    }


}
