package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Views;


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
import android.widget.Button;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.FeedTorneo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.FeedTorneoWebServiceRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.ViewModels.FeedTorneoListViewModel;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.AdaptadorForo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;


public class InfoDtorneoFragment extends Fragment {


    TextView titulo_infotorneo,fecha_infotorneo,hora_infotorneo,organizador_infotorneo,lugar_infotorneo,costo_infotorneo;
    Button unirme_infotorneo;
    RecyclerView rcv_infotorneo;
    String id_torneo,organizador,lugar,fecha,hora,titulo;
    Application application;
    Preferences preferences;
    FeedTorneoListViewModel feedTorneoListViewModel;
    AdaptadorFeedTorneo adaptadorFeedTorneo;

    public InfoDtorneoFragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedTorneoListViewModel = ViewModelProviders.of(this).get(FeedTorneoListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info_dtorneo, container, false);

        preferences= new Preferences(getContext());
        final Bundle bdl = getArguments();


        id_torneo = bdl.getString("id_torneo");
        organizador = bdl.getString("organizador");
        lugar = bdl.getString("lugar");
        fecha = bdl.getString("fecha");
        hora = bdl.getString("hora");
        titulo = bdl.getString("titulo");

        initViews(view);
        setAdapter();
        cargarvista();


        feed();
        return  view;

    }

    private void initViews(View view) {
        titulo_infotorneo = view.findViewById(R.id.titulo_infotorneo);
        fecha_infotorneo = view.findViewById(R.id.fecha_infotorneo);
        hora_infotorneo = view.findViewById(R.id.hora_infotorneo);
        organizador_infotorneo = view.findViewById(R.id.organizador_infotorneo);
        lugar_infotorneo = view.findViewById(R.id.lugar_infotorneo);
        costo_infotorneo = view.findViewById(R.id.costo_infotorneo);
        rcv_infotorneo = view.findViewById(R.id.rcv_infotorneo);


        titulo_infotorneo.setText(titulo);
        fecha_infotorneo.setText(fecha);
        hora_infotorneo.setText(hora);
        organizador_infotorneo.setText(organizador);
        lugar_infotorneo.setText(lugar);
    }
    public void feed(){
        FeedTorneoWebServiceRepository feedTorneoWebServiceRepository = new FeedTorneoWebServiceRepository(application);
        feedTorneoWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),id_torneo,"0","0","datos");
    }

    private void setAdapter() {

        adaptadorFeedTorneo = new AdaptadorFeedTorneo(getContext(), new AdaptadorFeedTorneo.OnItemClickListener() {
            @Override
            public void onItemClick(String dato, FeedTorneo feedTorneo, int position) {

            }
        });

        rcv_infotorneo.setAdapter(adaptadorFeedTorneo);
        rcv_infotorneo.setLayoutManager(new LinearLayoutManager(getContext()));




    }

    private void cargarvista() {
        feedTorneoListViewModel.getIdTorneo(id_torneo).observe(this, new Observer<List<FeedTorneo>>() {
            @Override
            public void onChanged(@Nullable List<FeedTorneo> feedTorneos) {
                adaptadorFeedTorneo.setWords(feedTorneos);
                /*if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }*/
                //a.setVisibility(ProgressBar.INVISIBLE);
                //cdv_mensaje.setVisibility(View.INVISIBLE);
            }
        });



    }


}
