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
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.DetalleTorneo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.PublicacionesTorneo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Publicaciones.PublicacionesTorneoWebServiceRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.ViewModels.DetalleTorneoViewModel;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.ViewModels.PublicacionesTorneoViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;


public class InfoDtorneoFragment extends Fragment {


    TextView titulo_infotorneo,fecha_infotorneo,hora_infotorneo,organizador_infotorneo,lugar_infotorneo,costo_infotorneo;

    RecyclerView rcv_infotorneo;
    String id_torneo;
    Application application;
    Preferences preferences;
    PublicacionesTorneoViewModel feedTorneoListViewModel;
    String titulo,fecha,hora,organizador,lugar,costo;
    AdaptadorPublicacionesTorneo adaptadorPublicacionesTorneo;
    DetalleTorneoViewModel detalleTorneoViewModel;

    public InfoDtorneoFragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedTorneoListViewModel = ViewModelProviders.of(this).get(PublicacionesTorneoViewModel.class);
        detalleTorneoViewModel = ViewModelProviders.of(this).get(DetalleTorneoViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info_dtorneo, container, false);

        preferences= new Preferences(getContext());

        final Bundle bdl = getArguments();


        id_torneo = bdl.getString("id_torneo");


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



    }
    private void setAdapter() {

        adaptadorPublicacionesTorneo = new AdaptadorPublicacionesTorneo(getContext(), new AdaptadorPublicacionesTorneo.OnItemClickListener() {
            @Override
            public void onItemClick(String dato, PublicacionesTorneo feedTorneo, int position) {

            }
        });

        rcv_infotorneo.setAdapter(adaptadorPublicacionesTorneo);
        rcv_infotorneo.setLayoutManager(new LinearLayoutManager(getContext()));




    }

    private void cargarvista() {
        feedTorneoListViewModel.getIdTorneo(id_torneo).observe(this, new Observer<List<PublicacionesTorneo>>() {
            @Override
            public void onChanged(@Nullable List<PublicacionesTorneo> feedTorneos) {
                adaptadorPublicacionesTorneo.setWords(feedTorneos);

            }
        });

        detalleTorneoViewModel.getIdTorneo(id_torneo,preferences.getToken()).observe(this, new Observer<List<DetalleTorneo>>() {
            @Override
            public void onChanged(List<DetalleTorneo> detalleTorneos) {
                if (detalleTorneos.size()>0){





                    titulo_infotorneo.setText(detalleTorneos.get(0).getNombre_torneo());
                    fecha_infotorneo.setText(detalleTorneos.get(0).getFecha_torneo());
                    hora_infotorneo.setText(detalleTorneos.get(0).getHora_torneo());
                    organizador_infotorneo.setText(detalleTorneos.get(0).getOrganizador_torneo());
                    lugar_infotorneo.setText(detalleTorneos.get(0).getLugar_torneo());
                    String costoso = detalleTorneos.get(0).getCosto_torneo() + " por Equipo Inscrito";
                    costo_infotorneo.setText(costoso);
                    //costo_infotorneo= costoso;
                }
            }
        });



    }

    public void feed(){
        PublicacionesTorneoWebServiceRepository feedTorneoWebServiceRepository = new PublicacionesTorneoWebServiceRepository(application);
        feedTorneoWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),id_torneo,"0","0","datos",preferences.getToken());
    }


}
