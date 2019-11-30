package com.tec.bufeo.capitan.Activity.RegistrarEquipo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.BuscarEquipo.RegistroEquipoFragment;
import com.tec.bufeo.capitan.R;

public class RegistroEquipoPadre extends AppCompatActivity {

    public TabLayout tabLayoutT;
    private SectionsPagerRegistroEquipo mSectionsPagerAdapter;
    public ViewPager mViewPager;
    String id_grupo;


    private   String[] tituloIds = {
            "Mis Equipos",
            "Torneo"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_equipo_padre);

        id_grupo =getIntent().getExtras().getString("id_grupo");
        mSectionsPagerAdapter = new SectionsPagerRegistroEquipo(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container_registro_equipo);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayoutT =  findViewById(R.id.tabs_registro_equipo);

        for(int i=0;i<2;i++) {
            tabLayoutT.addTab(tabLayoutT.newTab().setText(tituloIds[i]) );
        }


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutT));
        //tabLayoutT.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayoutT.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayoutT.getSelectedTabPosition();
    }

    public class SectionsPagerRegistroEquipo extends FragmentPagerAdapter {

        public SectionsPagerRegistroEquipo(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment  = null;

            switch (position){
                case 0:
                    fragment = new RegistroEquipoFragment();
                    Bundle bundle =  new Bundle();
                    bundle.putString("id_grupo", id_grupo);
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new EquipoBusquedaFragment();
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
