package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Views;

import android.app.Activity;
import android.app.Application;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.DetalleEquipoNuevo;
import com.tec.bufeo.capitan.Activity.RegistroEquipo;
import com.tec.bufeo.capitan.Activity.RegistroReto;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquipos.MisEquiposWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.OtrosEquipos.OtrosEquiposWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.OtrosEquipos.OtrosEquiposViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquipos.MisEquiposViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.ViewModels.RetosViewModel;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;


public class FragmentEquipo extends Fragment implements View.OnClickListener , SwipeRefreshLayout.OnRefreshListener {


    MisEquiposViewModel misEquiposViewModel;
    OtrosEquiposViewModel otrosEquiposViewModel;
    RetosViewModel retosViewModel;
    RecyclerView rcv_equipo, rcv_equipoFav;
    Preferences preferences;
    Activity activity;
    Context context;
    ImageButton imb_agregar_equipo,imb_ver_equipo;
    AdaptadorMiEquipo adaptadorMiEquipo;
    AdaptadorEquipos adaptadorEquipos;
    SwipeRefreshLayout swipeEquipos;

    public FragmentEquipo() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        misEquiposViewModel = ViewModelProviders.of(getActivity()).get(MisEquiposViewModel.class);
        otrosEquiposViewModel = ViewModelProviders.of(getActivity()).get(OtrosEquiposViewModel.class);
        retosViewModel = ViewModelProviders.of(getActivity()).get(RetosViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_equipo_roque, container, false);
        context =  getContext();
        preferences = new Preferences(context);
        initViews(view);
        setAdapter();
        cargarvista();

        return view;
    }

    private void initViews(View view){

        rcv_equipo =(RecyclerView)view.findViewById(R.id.rcv_equipo);
        rcv_equipoFav = (RecyclerView) view.findViewById(R.id.rcv_equipoFav);
        imb_agregar_equipo =(ImageButton) view.findViewById(R.id.imb_agregar_equipo) ;
        imb_ver_equipo =(ImageButton) view.findViewById(R.id.imb_ver_equipo) ;

        swipeEquipos =  view.findViewById(R.id.swipeEquipos);

        swipeEquipos.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeEquipos.setOnRefreshListener(this);


        imb_ver_equipo.setOnClickListener(this);
        imb_agregar_equipo.setOnClickListener(this);
        activity = getActivity();
        context = getContext();
    }

    public void cargarvista(){


        misEquiposViewModel.getAllMiEquipo("si").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(@Nullable List<Mequipos> mequipos) {
                adaptadorMiEquipo.setWords(mequipos);
            }
        });

        otrosEquiposViewModel.getAllOtrosEquipos("no").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(@Nullable List<Mequipos> mequipos) {
                adaptadorEquipos.setWords(mequipos);

            }
        });


    }

    private void setAdapter(){


        adaptadorMiEquipo =  new AdaptadorMiEquipo(getActivity(), new AdaptadorMiEquipo.OnItemClickListener() {
            @Override
            public void onItemClick(Mequipos mequipos, int position) {

                Intent intent = new Intent(getContext(), DetalleEquipoNuevo.class);
                intent.putExtra("id_equipo", mequipos.getEquipo_id());
                intent.putExtra("nombre_equipo", mequipos.getEquipo_nombre());
                intent.putExtra("foto_equipo", mequipos.getEquipo_foto());
                startActivity(intent);
            }
        });



        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        rcv_equipo.setLayoutManager(linearLayoutManager);
        rcv_equipo.setAdapter(adaptadorMiEquipo);



        adaptadorEquipos = new AdaptadorEquipos(context, new AdaptadorEquipos.OnItemClickListener() {
            @Override
            public void onItemClick(Mequipos mequipos, int position) {

            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        layoutManager.setOrientation(layoutManager.HORIZONTAL);
        rcv_equipoFav.setLayoutManager(layoutManager);
        rcv_equipoFav.setAdapter(adaptadorEquipos);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imb_agregar_equipo:
                //Toast.makeText(getActivity(),"foto:"+usuario_foto,Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getContext(), RegistroEquipo.class);
                startActivity(intent2);
                break;

            case R.id.imb_ver_equipo:
                //Toast.makeText(getActivity(),"foto:"+usuario_foto,Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getContext(), RegistroReto.class);
                startActivity(intent1);
                break;
        }
    }

    Application application;
    @Override
    public void onRefresh() {
        //retosViewModel.ElimarRetos();

        MisEquiposWebServiceRepository misEquiposWebServiceRepository= new MisEquiposWebServiceRepository(application);
        misEquiposWebServiceRepository.providesWebService(preferences.getIdUsuarioPref());

        OtrosEquiposWebServiceRepository otrosEquiposWebServiceRepository = new OtrosEquiposWebServiceRepository(application);
        otrosEquiposWebServiceRepository.providesWebService(preferences.getIdUsuarioPref());
        swipeEquipos.setRefreshing(false);

    }
}
