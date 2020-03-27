package com.tec.bufeo.capitan.Fragments;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.ProfileActivity;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository.ChatsWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.ViewModels.ChatsListViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Views.FragmentChats;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Views.FragmentEstadisticas;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository.RetosWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.ViewModels.RetosViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Views.FragmentRetos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Repository.TorneosWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.ViewModels.TorneosViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Views.FragmentTorneoRoque;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Views.FragmentEquipo;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class FragmentTorneoPadre extends Fragment  {


    CircleImageView civ_fotoperfil;
    TextView txt_nombreusuario;
    ImageView estadisticas;
    public TabLayout tabLayoutT;
    MisEquiposViewModel misEquiposViewModel;
    RetosViewModel retosViewModel;
    ChatsListViewModel chatsListViewModel;
    TorneosViewModel torneosViewModel;
    UniversalImageLoader universalImageLoader;
    String inicio;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;

    private  int[] imgIds = {
            R.drawable.equipos,
            R.drawable.torneos,
            R.drawable.retos,
            R.drawable.chats
    };

    private  int[] imgBordeIds = {
            R.drawable.equipos,
            R.drawable.torneos,
            R.drawable.retos,
            R.drawable.chats
    };

    private   String[] tituloIds = {
            "Equipos",
            "Torneo",
            "Retos",
            "Chats"
    };

    Activity activity;
    Context context;
    Preferences preferences;

    public FragmentTorneoPadre() {

    }
    Application application;
    RetosWebServiceRepository retosWebServiceRepository;
    ChatsWebServiceRepository chatsWebServiceRepository;
    TorneosWebServiceRepository torneosWebServiceRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        misEquiposViewModel = ViewModelProviders.of(getActivity()).get(MisEquiposViewModel.class);
        retosViewModel = ViewModelProviders.of(getActivity()).get(RetosViewModel.class);
        chatsListViewModel = ViewModelProviders.of(getActivity()).get(ChatsListViewModel.class);
        torneosViewModel = ViewModelProviders.of(getActivity()).get(TorneosViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_torneo_padre, container, false);

        activity = getActivity();
        context = getContext();



        preferences = new Preferences(context);
        universalImageLoader = new UniversalImageLoader(context);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());


        retosWebServiceRepository =  new RetosWebServiceRepository(application);
        retosWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken(),"normal");

        torneosWebServiceRepository =  new TorneosWebServiceRepository(application);
        torneosWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken(),"torneos","");



        chatsWebServiceRepository =  new ChatsWebServiceRepository(application);
        chatsWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken());


        civ_fotoperfil = view.findViewById(R.id.civ_fotoperfil) ;
        estadisticas = view.findViewById(R.id.estadisticas) ;
        txt_nombreusuario = view.findViewById(R.id.txt_nombreusuario) ;
        txt_nombreusuario.setText( preferences.getPersonName() + " " + preferences.getPersonSurname());


        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mViewPager = view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayoutT =  view.findViewById(R.id.tabs);

        for(int i=0;i<4;i++) {
            tabLayoutT.addTab(tabLayoutT.newTab(). setIcon(imgBordeIds[i]).setText(tituloIds[i]) );
        }

        final Bundle bdl = getArguments();



        if (preferences.getInicio().equals("mensajes")){
            mViewPager.setCurrentItem(3);
            preferences.saveValuePORT("inicio","inicio");
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutT));
        //tabLayoutT.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayoutT.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                tab.setIcon(imgIds[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(imgBordeIds[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayoutT.getSelectedTabPosition();

        universalImageLoader = new UniversalImageLoader(context);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());


        UniversalImageLoader.setImage(IP2+"/"+ preferences.getFotoUsuario(),civ_fotoperfil,null);







        civ_fotoperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectImage("Perfil");
                Intent i =  new Intent(context, ProfileActivity.class);
                i.putExtra("id_usuario",preferences.getIdUsuarioPref());
                startActivity(i);


            }
        });

        estadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEstadisticasDialog();
            }
        });
        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void showEstadisticasDialog() {
        FragmentManager fm = getFragmentManager();
        FragmentEstadisticas fragmentEstadisticas = FragmentEstadisticas.newInstance("Some Title");
        fragmentEstadisticas.show(fm, "fragment_edit_name");
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment  = null;

            switch (position){
                case 0:
                    fragment = new FragmentEquipo();
                    break;
                case 1:
                    fragment = new FragmentTorneoRoque();
                    break;
                case 2:
                    fragment = new FragmentRetos();
                    break;
                case 3:
                    fragment = new FragmentChats();
                    break;

            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

    }

}


