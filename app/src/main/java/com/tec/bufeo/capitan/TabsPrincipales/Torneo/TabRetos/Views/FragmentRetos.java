package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.Views;


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
