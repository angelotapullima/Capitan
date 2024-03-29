package com.tec.bufeo.capitan.TabsPrincipales.tabsBuscar;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tec.bufeo.capitan.R;


public class FragmentBuscarPadre extends Fragment {


    public ViewPager mViewPager;
    private SectionsAdapter sectionsAdapter;

    private  int[] imgIds = {
            R.drawable.buqueda_disponible_hoy,
            R.drawable.buqueda_avanzada
    };

    private  int[] imgBordeIds = {
            R.drawable.buqueda_disponible_hoy,
            R.drawable.buqueda_avanzada
    };

    private  String[] tituloIds = {
            "DISPONIBLES HOY",
            "BUSQUEDA AVANZADA"
    };


    public FragmentBuscarPadre() {
        // Required empty public constructor
    }


    public TabLayout tabLayoutT;
    Activity activity;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_buscar_padre, container, false);

        sectionsAdapter = new SectionsAdapter(getChildFragmentManager());

        mViewPager = view.findViewById(R.id.container);
        mViewPager.setAdapter(sectionsAdapter);

        tabLayoutT =  view.findViewById(R.id.tabs);

        for(int i=0;i<2;i++) {
            tabLayoutT.addTab(tabLayoutT.newTab(). setIcon(imgBordeIds[i]).setText(tituloIds[i]) );
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





        activity = getActivity();
        context = getContext();



        return view;
    }



    public class SectionsAdapter extends FragmentPagerAdapter {

        public SectionsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment  = null;

            switch (position){
                case 0:
                    fragment = new FragmentBuscar();
                    break;
                case 1:
                    fragment = new FragmentBuscarFechas();
                    break;

            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }






}
