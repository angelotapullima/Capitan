package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Views;


import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tec.bufeo.capitan.Fragments.tabsBuscar.FragmentBuscar;
import com.tec.bufeo.capitan.Fragments.tabsBuscar.FragmentBuscarFechas;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository.RetosWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.ViewModels.RetosViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Views.AdaptadorRetos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

import static com.tec.bufeo.capitan.Activity.DetalleCanchas.tituloIds;


public class FragmentRetos extends Fragment  {

    public TabLayout tabLayout_retos;

    public  PaginasAdapter paginasAdapter;
    public ViewPager mViewPager;

    public FragmentRetos() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retos, container, false);


        paginasAdapter = new PaginasAdapter(getChildFragmentManager());

        mViewPager = view.findViewById(R.id.contenedor_retos);
        mViewPager.setAdapter(paginasAdapter);

        tabLayout_retos =  view.findViewById(R.id.tabLayout_retos);


        for (int i = 0; i < tabLayout_retos.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout_retos.getTabAt(i);
            if (tab != null) tab.setCustomView(R.layout.custom_tabs);
        }



        tabLayout_retos.addTab(tabLayout_retos.newTab().setText("Aceptados"));
        tabLayout_retos.addTab(tabLayout_retos.newTab().setText("pendientes"));
        tabLayout_retos.addTab(tabLayout_retos.newTab().setText("Rechazados"));

        for(int i=0; i < tabLayout_retos.getTabCount() - 1; i++) {
            View tab = ((ViewGroup) tabLayout_retos.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 30, 0);
            tab.requestLayout();
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_retos));
        //tabLayoutT.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout_retos.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

                //tab.setIcon(imgIds[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab.setIcon(imgBordeIds[tab.getPosition()]);
                //tabLayout_retos.setTabTextColors(R.color.colorAccent,R.color.colorAccent);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout_retos.getSelectedTabPosition();
        return view;
    }




    public class PaginasAdapter extends FragmentPagerAdapter {

        public PaginasAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment  = null;

            switch (position){
                case 0:
                    fragment = new FragmentAceptados();
                    break;

                case 1:
                    fragment = new FragmentPendientes();
                    break;

                case 2:
                    fragment = new FragmentRechazados();
                    break;


            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
