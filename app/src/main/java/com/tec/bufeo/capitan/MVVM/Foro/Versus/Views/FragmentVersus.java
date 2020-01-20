package com.tec.bufeo.capitan.MVVM.Foro.Versus.Views;

import android.app.Activity;
import android.app.Dialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.tec.bufeo.capitan.MVVM.Foro.Versus.Models.Versus;
import com.tec.bufeo.capitan.MVVM.Foro.Versus.ViewModels.VersusListViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class FragmentVersus extends DialogFragment {

    VersusListViewModel versusListViewModel;
    Activity activity;
    Context context;
    RecyclerView rcv_partidos;
    VersusListAdapter versusListAdapter;
    Preferences preferences;
    public FragmentVersus() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FragmentVersus newInstance(String title) {
        FragmentVersus frag = new FragmentVersus();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        versusListViewModel = ViewModelProviders.of(getActivity()).get(VersusListViewModel.class);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.dialog_partidos_pendientes, container,false);
        preferences= new Preferences(getActivity());
        context = getContext();
        activity = getActivity();
        initViews(view);
        setAdapter();
        cargarvista();
        return view;
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

    private void initViews(View view){



        rcv_partidos = (RecyclerView) view.findViewById(R.id.rcv_partidos);
    }


    public void cargarvista(){
        versusListViewModel.getmAllReviews("14",preferences.getToken()).observe(this, new Observer<List<Versus>>() {
            @Override
            public void onChanged(@Nullable List<Versus> modelFeeds) {
                versusListAdapter.setWords(modelFeeds);
                /*if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }*/

            }
        });
    }



    private void setAdapter(){


        versusListAdapter = new VersusListAdapter(context, new VersusListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Versus comments, int position) {

            }
        }) ;

        rcv_partidos.setAdapter(versusListAdapter);
        rcv_partidos.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
