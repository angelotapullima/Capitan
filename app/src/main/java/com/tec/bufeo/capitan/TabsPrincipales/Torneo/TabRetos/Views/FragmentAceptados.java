package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.Views;

import android.app.Activity;
import android.app.Application;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.Models.Retos;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.Repository.RetosWebServiceRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.ViewModels.RetosViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;


public class FragmentAceptados extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RetosViewModel retosViewModel;
    RecyclerView rcv_duelos;
    AdaptadorRetos adaptadorRetos;
    Preferences preferences;
    Activity activity;
    Context context;

    SwipeRefreshLayout RefreshLayoutAceptados;


    public FragmentAceptados() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retosViewModel = ViewModelProviders.of(getActivity()).get(RetosViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_aceptados, container, false);

        context =  getContext();
        preferences = new Preferences(context);
        initViews(view);
        setAdapter();
        cargarvista();
        return view;
    }

    private void initViews(View view){

        rcv_duelos =(RecyclerView)view.findViewById(R.id.rcv_duelos);

        RefreshLayoutAceptados =  view.findViewById(R.id.RefreshLayoutAceptados);

        RefreshLayoutAceptados.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        RefreshLayoutAceptados.setOnRefreshListener(this);
        activity = getActivity();
        context = getContext();
    }

    public void cargarvista(){

        retosViewModel.getAllRetos("1").observe(this, new Observer<List<Retos>>() {
            @Override
            public void onChanged(@Nullable List<Retos> retos) {
                adaptadorRetos.setWords(retos);
            }
        });

    }

    private void setAdapter() {
        adaptadorRetos =  new AdaptadorRetos(getActivity(), new AdaptadorRetos.OnItemClickListener() {
            @Override
            public void onItemClick(Retos retos, int position) {


            }
        });

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_duelos.setLayoutManager(linearLayoutManager);
        rcv_duelos.setAdapter(adaptadorRetos);


    }

    Application application;
    @Override
    public void onRefresh() {
        //retosViewModel.ElimarRetos();

        RetosWebServiceRepository retosWebServiceRepository = new RetosWebServiceRepository(application);
        retosWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken(),"normal");
        RefreshLayoutAceptados.setRefreshing(false);

    }
}
