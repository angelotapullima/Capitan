package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Activity.ProfileActivity;
import com.tec.bufeo.capitan.Activity.RegistroForo;
import com.tec.bufeo.capitan.MVVM.Foro.Versus.Views.FragmentVersus;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository.FeedWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;


public class ForoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Preferences preferences;
    RecyclerView rcv_foro;
    FloatingActionButton fab_registrarForo;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    ImageView partidos;
    CardView cdv_mensaje;
    AdaptadorForo adapter = null;
    FeedListViewModel feedListViewModel;
    Activity activity;
    static Context context;
    View view = null;
    ImageView fotoPerfil;
    EditText floating_search_view;


    public ForoFragment() {
    }


    public static boolean isOnLine(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return  true;
        }else {
            return false;
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //    progressDialog = new ProgressDialog(getActivity());
        feedListViewModel = ViewModelProviders.of(getActivity()).get(FeedListViewModel.class);
        //commentsListViewModel =  ViewModelProviders.of(getActivity()).get(VersusListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_foro, container, false);
        context = getContext();
        activity = getActivity();

        preferences = new Preferences(context);
        initViews(view);
        setAdapter();
        cargarvista();


        return view;
    }






    private void initViews(View view){


        fotoPerfil = view.findViewById(R.id.fotoPerfil);
        partidos = view.findViewById(R.id.partidos);
        rcv_foro = (RecyclerView) view.findViewById(R.id.rcv_foro);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        floating_search_view =  view.findViewById(R.id.floating_search_view);
        cdv_mensaje = (CardView) view.findViewById(R.id.cdv_mensaje);
        swipeRefreshLayout =  view.findViewById(R.id.SwipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        fab_registrarForo = view.findViewById(R.id.fab_registrarForo);

        fab_registrarForo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), RegistroForo.class);
                startActivity(i);
            }
        });

        partidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        //tlb_foro.setTitle("Foro..");

        Picasso.with(context).load(IP+"/"+preferences.getFotoUsuario()).into(fotoPerfil);

        floating_search_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String v = String.valueOf(s);
                buscar(v);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(context, ProfileActivity.class);
                startActivity(i);
            }
        });


    }



    private void showEditDialog() {
        FragmentManager fm = getFragmentManager();
        FragmentVersus editNameDialogFragment = FragmentVersus.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }


    public void buscar(String s){
        feedListViewModel.getSearh(s).observe(this, new Observer<List<ModelFeed>>() {
            @Override
            public void onChanged(@Nullable List<ModelFeed> modelFeeds) {
                adapter.setWords(modelFeeds);

            }
        });
    }


    public void cargarvista(){
        feedListViewModel.getAllPosts().observe(this, new Observer<List<ModelFeed>>() {
            @Override
            public void onChanged(@Nullable List<ModelFeed> modelFeeds) {
                adapter.setWords(modelFeeds);
                /*if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }*/
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                cdv_mensaje.setVisibility(View.INVISIBLE);
            }
        });
    }



    private void setAdapter(){


        adapter = new AdaptadorForo(getActivity(), new AdaptadorForo.OnItemClickListener() {
            @Override
            public void onItemClick(ModelFeed modelFeed,  int position) {


            }
        });

        rcv_foro.setAdapter(adapter);
        rcv_foro.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    Application application;
    @Override
    public void onRefresh() {

        //feedListViewModel.deleteAllFeed();
        FeedWebServiceRepository feedWebServiceRepository = new FeedWebServiceRepository(application);
        feedWebServiceRepository.providesWebService();
        setAdapter();
        cargarvista();
        Log.e("prueba", "onRefresh: funciona" );
        swipeRefreshLayout.setRefreshing(false);
    }







}
