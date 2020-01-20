package com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Views;

import android.app.Activity;
import android.app.Dialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Models.Estadisticas;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.ViewModels.EstadisticasViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;


public class FragmentEstadisticas extends DialogFragment {


    EstadisticasViewModel estadisticasViewModel;
    Activity activity;
    Context context;
    RecyclerView rcv_estadisticas1;
    EstadisticasAdapter1 estadisticasAdapter1;
    Preferences preferences;
    //EstadisticasAdapter2 estadisticasAdapter2;


    public FragmentEstadisticas() {
        // Required empty public constructor
    }

    public static FragmentEstadisticas newInstance(String title) {
        FragmentEstadisticas fragment = new FragmentEstadisticas();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);

        estadisticasViewModel = ViewModelProviders.of(getActivity()).get(EstadisticasViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_estadisticas, container, false);
        context = getContext();
        preferences= new Preferences(context);
        activity = getActivity();
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

        estadisticasViewModel.getAllEstadisticas("12",preferences.getToken()).observe(this, new Observer<List<Estadisticas>>() {
            @Override
            public void onChanged(@Nullable List<Estadisticas> estadisticas) {
                estadisticasAdapter1.setWords(estadisticas);
                //estadisticasAdapter2.setWords(estadisticas);


            }
        });

    }



    private void setAdapter(){

        estadisticasAdapter1 = new EstadisticasAdapter1(context, new EstadisticasAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(Estadisticas comments, int position) {

            }
        });


        rcv_estadisticas1.setAdapter(estadisticasAdapter1);
        rcv_estadisticas1.setLayoutManager(new LinearLayoutManager(getActivity()));


    }



    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }
}
