package com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Views;

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

import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Views.ChatsActivity;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Models.Chats;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository.ChatsWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.ViewModels.ChatsListViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;


public class FragmentChats extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    Activity activity;
    Context context;
    Preferences preferences;
    ChatsListViewModel chatsListViewModel;
    RecyclerView recycler_chats;
    ChatsListAdapter chatsListAdapter;
    public FragmentChats() {
        // Required empty public constructor
    }


    SwipeRefreshLayout RefreshLayoutChats;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatsListViewModel = ViewModelProviders.of(getActivity()).get(ChatsListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_chats, container, false);
        context = getContext();
        activity = getActivity();
        preferences = new Preferences(context);
        initViews(view);
        setAdapter();
        cargarvista();
        return view;
    }

    private void initViews(View view){

        recycler_chats = (RecyclerView) view.findViewById(R.id.recycler_chats);
        RefreshLayoutChats =  view.findViewById(R.id.RefreshLayoutChats);

        RefreshLayoutChats.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        RefreshLayoutChats.setOnRefreshListener(this);


    }

    public void cargarvista(){

        chatsListViewModel.getmAllChats("24").observe(this, new Observer<List<Chats>>() {
            @Override
            public void onChanged(@Nullable List<Chats> chats) {
                chatsListAdapter.setWords(chats);


            }
        });




    }



    private void setAdapter(){

        chatsListAdapter =  new ChatsListAdapter(context, new ChatsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Chats chats, int position) {
                Intent i =  new Intent(getContext(), ChatsActivity.class);
                i.putExtra("id_chat",chats.getChat_id());
                i.putExtra("nombre_chat",chats.getChat_usuario());
                startActivity(i);
            }
        });


        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recycler_chats.setLayoutManager(linearLayoutManager);
        recycler_chats.setAdapter(chatsListAdapter);



    }

    Application application;
    @Override
    public void onRefresh() {
        //retosViewModel.ElimarRetos();

        ChatsWebServiceRepository chatsWebServiceRepository= new ChatsWebServiceRepository(application);
        chatsWebServiceRepository.providesWebService(preferences.getIdUsuarioPref());
        RefreshLayoutChats.setRefreshing(false);

    }

}
