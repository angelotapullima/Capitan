package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Views;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Models.EstadisticasDeEquipos;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository.EequiposWebServiceRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.ViewModels.EequiposViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class EstadisticasDeEquiposFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {



    Preferences preferences;
    String id_equipo;
    SwipeRefreshLayout swipeEstadisticaEquipos;
    EequiposViewModel eequiposViewModel;
    TextView  temporada,semana,puntaje_acumulado,puntaje_semanal,
    retos_enviados,retos_recibidos,retos_ganados,retos_empatados,retos_perdidos,torneos_estadisticas_equipos;
    ImageView foto_estadisticas_equipo;
    LinearLayout cargando_layout_estadis;

    public EstadisticasDeEquiposFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eequiposViewModel = ViewModelProviders.of(getActivity()).get(EequiposViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_estadisticas_de_equipos, container, false);
        preferences = new Preferences(getActivity());


        final Bundle bdl = getArguments();


        id_equipo = bdl.getString("id_equipo");
        initViews(view);

        cargarvista();
        return  view;
    }

    private void initViews(View view) {



        foto_estadisticas_equipo = view.findViewById(R.id.foto_estadisticas_equipo);
        temporada = view.findViewById(R.id.temporada);
        semana = view.findViewById(R.id.semana);
        puntaje_acumulado = view.findViewById(R.id.puntaje_acumulado);
        puntaje_semanal = view.findViewById(R.id.puntaje_semanal);
        retos_enviados = view.findViewById(R.id.retos_enviados);
        retos_recibidos = view.findViewById(R.id.retos_recibidos);
        retos_ganados = view.findViewById(R.id.retos_ganados);
        retos_empatados = view.findViewById(R.id.retos_empatados);
        retos_perdidos = view.findViewById(R.id.retos_perdidos);
        cargando_layout_estadis = view.findViewById(R.id.cargando_layout_estadis);
        torneos_estadisticas_equipos = view.findViewById(R.id.torneos_estadisticas_equipos);

        swipeEstadisticaEquipos = view.findViewById(R.id.swipeEstadisticaEquipos);

        swipeEstadisticaEquipos.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeEstadisticaEquipos.setOnRefreshListener(this);
    }

    private void cargarvista() {
        eequiposViewModel.getAllEstadisticas(id_equipo,preferences.getToken()).observe(this, new Observer<List<EstadisticasDeEquipos>>() {
            @Override
            public void onChanged(List<EstadisticasDeEquipos> estadisticasDeEquipos) {

                if (estadisticasDeEquipos.size()>0){


                    Glide.with(getContext()).load(IP2+"/"+ estadisticasDeEquipos.get(0).getFoto()).into(foto_estadisticas_equipo);
                    temporada.setText(estadisticasDeEquipos.get(0).getTemporada());
                    semana.setText(estadisticasDeEquipos.get(0).getSemana());
                    puntaje_acumulado.setText(estadisticasDeEquipos.get(0).getPuntaje_acumulado());
                    puntaje_semanal.setText(estadisticasDeEquipos.get(0).getPuntaje_semanal());
                    retos_enviados.setText(estadisticasDeEquipos.get(0).getRetos_enviados());
                    retos_recibidos.setText(estadisticasDeEquipos.get(0).getRetos_recibidos());
                    retos_ganados.setText(estadisticasDeEquipos.get(0).getRetos_ganados());
                    retos_empatados.setText(estadisticasDeEquipos.get(0).getRetos_empatados());
                    retos_perdidos.setText(estadisticasDeEquipos.get(0).getRetos_perdidos());
                    torneos_estadisticas_equipos.setText(estadisticasDeEquipos.get(0).getTorneos());

                    cargando_layout_estadis.setVisibility(View.GONE);
                }

            }
        });


    }

    Application application;
    @Override
    public void onRefresh() {
        EequiposWebServiceRepository eequiposWebServiceRepository = new EequiposWebServiceRepository(application);
        eequiposWebServiceRepository.providesWebService(id_equipo,preferences.getToken());
        swipeEstadisticaEquipos.setRefreshing(false);
    }
}
