package com.tec.bufeo.capitan.Activity.DetallesTorneo.TablaDtorneo;

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

import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Models.Estadisticas;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.ViewModels.EstadisticasViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Views.EstadisticasAdapter1;
import com.tec.bufeo.capitan.R;

import java.util.List;


public class TablaDtorneoFragment extends Fragment {

    EstadisticasViewModel estadisticasViewModel;

    RecyclerView rcv_estadisticas1;
    EstadisticasAdapter1 estadisticasAdapter1;
    String id_torneo;
    public TablaDtorneoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        estadisticasViewModel = ViewModelProviders.of(getActivity()).get(EstadisticasViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabla_dtorneo, container, false);

        final Bundle bdl = getArguments();


        id_torneo = bdl.getString("id_torneo");
        initViews(view);
        setAdapter();
        cargarvista();
        return view;
    }

    private void initViews(View view){



        rcv_estadisticas1 = (RecyclerView) view.findViewById(R.id.rcv_estadisticas1);
        //rcv_estadisticas2 = (RecyclerView) view.findViewById(R.id.rcv_estadisticas2);
    }

    public void cargarvista(){

        estadisticasViewModel.getAllEstadisticas("12").observe(this, new Observer<List<Estadisticas>>() {
            @Override
            public void onChanged(@Nullable List<Estadisticas> estadisticas) {
                estadisticasAdapter1.setWords(estadisticas);
                //estadisticasAdapter2.setWords(estadisticas);


            }
        });

    }



    private void setAdapter(){

        estadisticasAdapter1 = new EstadisticasAdapter1(getContext(), new EstadisticasAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(Estadisticas comments, int position) {

            }
        });


        rcv_estadisticas1.setAdapter(estadisticasAdapter1);
        rcv_estadisticas1.setLayoutManager(new LinearLayoutManager(getActivity()));


    }


}
