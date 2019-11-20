package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.AdaptadorForo;
import com.tec.bufeo.capitan.R;

import java.util.List;


public class InfoDtorneoFragment extends Fragment {


    TextView titulo_infotorneo,fecha_infotorneo,hora_infotorneo,organizador_infotorneo,lugar_infotorneo,costo_infotorneo;
    Button unirme_infotorneo;
    RecyclerView rcv_infotorneo;


    FeedListViewModel feedListViewModel;
    AdaptadorForo adaptadorForo;

    public InfoDtorneoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedListViewModel = ViewModelProviders.of(this).get(FeedListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info_dtorneo, container, false);


        initViews(view);
        setAdapter();
        cargarvista();

        return  view;

    }


    private void setAdapter() {

        adaptadorForo = new AdaptadorForo(getContext(), new AdaptadorForo.OnItemClickListener() {
            @Override
            public void onItemClick(ModelFeed modelFeed, int position) {

            }
        });

        rcv_infotorneo.setAdapter(adaptadorForo);
        rcv_infotorneo.setLayoutManager(new LinearLayoutManager(getContext()));




    }

    private void cargarvista() {
        feedListViewModel.getAllPosts().observe(this, new Observer<List<ModelFeed>>() {
            @Override
            public void onChanged(@Nullable List<ModelFeed> modelFeeds) {
                adaptadorForo.setWords(modelFeeds);
                /*if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }*/
                //a.setVisibility(ProgressBar.INVISIBLE);
                //cdv_mensaje.setVisibility(View.INVISIBLE);
            }
        });



    }

    private void initViews(View view) {
        titulo_infotorneo = view.findViewById(R.id.titulo_infotorneo);
        fecha_infotorneo = view.findViewById(R.id.fecha_infotorneo);
        hora_infotorneo = view.findViewById(R.id.hora_infotorneo);
        organizador_infotorneo = view.findViewById(R.id.organizador_infotorneo);
        lugar_infotorneo = view.findViewById(R.id.lugar_infotorneo);
        costo_infotorneo = view.findViewById(R.id.costo_infotorneo);
        rcv_infotorneo = view.findViewById(R.id.rcv_infotorneo);
    }
}
