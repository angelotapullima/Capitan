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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tec.bufeo.capitan.Activity.CrearEquipos;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.DetalleEquipoNuevo;
import com.tec.bufeo.capitan.Activity.MasEquipos;
import com.tec.bufeo.capitan.Activity.MasMisEquipos;
import com.tec.bufeo.capitan.Activity.RegistroReto;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquiposWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;


public class FragmentEquipo extends Fragment implements View.OnClickListener , SwipeRefreshLayout.OnRefreshListener {


    MisEquiposViewModel misEquiposViewModel;
    //RetosViewModel retosViewModel;
    RecyclerView rcv_equipo, rcv_equipoFav;
    Preferences preferences;
    Activity activity;
    Context context;
    ImageButton imb_ver_equipos,imb_ver_misequipos;
    AdaptadorMiEquipo adaptadorMiEquipo;
    AdaptadorEquipos adaptadorEquipos;
    SwipeRefreshLayout swipeEquipos;
    FloatingActionButton reg_equipos;

    public FragmentEquipo() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        misEquiposViewModel = ViewModelProviders.of(getActivity()).get(MisEquiposViewModel.class);
        //otrosEquiposViewModel = ViewModelProviders.of(getActivity()).get(OtrosEquiposViewModel.class);
        //retosViewModel = ViewModelProviders.of(getActivity()).get(RetosViewModel.class);

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
        imb_ver_equipos =(ImageButton) view.findViewById(R.id.imb_ver_equipos) ;
        imb_ver_misequipos =(ImageButton) view.findViewById(R.id.imb_ver_misequipos) ;
        reg_equipos =(FloatingActionButton) view.findViewById(R.id.reg_equipos) ;

        swipeEquipos =  view.findViewById(R.id.swipeEquipos);

        swipeEquipos.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeEquipos.setOnRefreshListener(this);


        imb_ver_misequipos.setOnClickListener(this);
        imb_ver_equipos.setOnClickListener(this);
        reg_equipos.setOnClickListener(this);
        activity = getActivity();
        context = getContext();
    }

    public void cargarvista(){


        misEquiposViewModel.getAllEquipo("si").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(@Nullable List<Mequipos> mequipos) {
                adaptadorMiEquipo.setWords(mequipos);
                Log.e("mis Equipos", "onChanged: "+mequipos.size() );
            }
        });

        misEquiposViewModel.getAllEquipo("no").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(@Nullable List<Mequipos> mequipos) {
                adaptadorEquipos.setWords(mequipos);
            }
        });



    }

    private void setAdapter(){


        adaptadorMiEquipo =  new AdaptadorMiEquipo(getActivity(), "",new AdaptadorMiEquipo.OnItemClickListener() {
            @Override
            public void onItemClick(Mequipos mequipos, int position) {

                Intent intent = new Intent(getContext(), DetalleEquipoNuevo.class);
                intent.putExtra("id_equipo", mequipos.getEquipo_id());
                intent.putExtra("nombre_equipo", mequipos.getEquipo_nombre());
                intent.putExtra("foto_equipo", mequipos.getEquipo_foto());
                intent.putExtra("capitan_equipo", mequipos.getCapitan_nombre());
                intent.putExtra("capitan_id", mequipos.getCapitan_id());
                startActivity(intent);
            }
        });



        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        rcv_equipo.setLayoutManager(linearLayoutManager);
        rcv_equipo.setAdapter(adaptadorMiEquipo);


        adaptadorEquipos = new AdaptadorEquipos(context, new AdaptadorEquipos.OnItemClickListener() {
            @Override
            public void onItemClick(Mequipos mequipos, String tipo, int position) {
                if (tipo.equals("txt_nombreEquipoRetar")){

                    Intent intent = new Intent(getContext(), DetalleEquipoNuevo.class);
                    intent.putExtra("id_equipo", mequipos.getEquipo_id());
                    intent.putExtra("nombre_equipo", mequipos.getEquipo_nombre());
                    intent.putExtra("foto_equipo", mequipos.getEquipo_foto());
                    intent.putExtra("capitan_equipo", mequipos.getCapitan_nombre());
                    intent.putExtra("capitan_id", mequipos.getCapitan_id());
                    startActivity(intent);
                }else if (tipo.equals("civ_fotoEquipoRetar")){

                    Intent intent = new Intent(getContext(), DetalleEquipoNuevo.class);
                    intent.putExtra("id_equipo", mequipos.getEquipo_id());
                    intent.putExtra("nombre_equipo", mequipos.getEquipo_nombre());
                    intent.putExtra("foto_equipo", mequipos.getEquipo_foto());
                    intent.putExtra("capitan_equipo", mequipos.getCapitan_nombre());
                    intent.putExtra("capitan_id", mequipos.getCapitan_id());
                    startActivity(intent);
                }if (tipo.equals("btn_retar")){

                    Intent intent = new Intent(getContext(), RegistroReto.class);
                    intent.putExtra("nombre_retado", mequipos.getEquipo_nombre());
                    intent.putExtra("foto_retado", mequipos.getEquipo_foto());
                    intent.putExtra("id_retado", mequipos.getEquipo_id());
                    intent.putExtra("capitan_id", mequipos.getCapitan_id());
                    startActivity(intent);
                }


            }
        });



        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        layoutManager.setOrientation(layoutManager.HORIZONTAL);
        rcv_equipoFav.setLayoutManager(layoutManager);
        rcv_equipoFav.setAdapter(adaptadorEquipos);

    }

    @Override
    public void onClick(View v) {

        if (v.equals(imb_ver_misequipos)){
            Intent i = new Intent(getContext(), MasMisEquipos.class);
            startActivity(i);

        }if (v.equals(imb_ver_equipos)){

            Intent i = new Intent(getContext(), MasEquipos.class);
            startActivity(i);
        }if (v.equals(reg_equipos)){
            Intent i = new Intent(getContext(), CrearEquipos.class);
            startActivity(i);
        }
    }

    Application application;
    @Override
    public void onRefresh() {

        MisEquiposWebServiceRepository misEquiposWebServiceRepository= new MisEquiposWebServiceRepository(application);
        misEquiposWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),"mi_equipo",preferences.getToken());
        misEquiposWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),"otro_equipo",preferences.getToken());

        swipeEquipos.setRefreshing(false);

    }
}
