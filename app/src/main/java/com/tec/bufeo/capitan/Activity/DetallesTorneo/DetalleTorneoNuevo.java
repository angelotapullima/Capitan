package com.tec.bufeo.capitan.Activity.DetallesTorneo;

import android.app.Activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.EquiposDtorneoFragment;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.InfoDtorneoFragment;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.TablaDtorneo.TablaDtorneoFragment;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

public class DetalleTorneoNuevo extends AppCompatActivity {

    public TabLayout tabs_Dtorneo;
    Button fecha_Dtorneo,unirse_Dtorneo;
    ImageView imagen_Dtorneo;
    public ViewPager container_Dtorneo;
    private SectionsDetalleTorneoAdapter sectionsDetalleTorneoAdapter;
    Activity activity;
    Context context;
    Preferences preferences;




    private   String[] tituloIds = {
            "Info",
            "Equipos",
            "Tabla",
            "Resultados"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_torneo_nuevo);


        sectionsDetalleTorneoAdapter = new SectionsDetalleTorneoAdapter(getSupportFragmentManager());

        container_Dtorneo = findViewById(R.id.container_Dtorneo);
        container_Dtorneo.setAdapter(sectionsDetalleTorneoAdapter);

        tabs_Dtorneo =  findViewById(R.id.tabs_Dtorneo);
        imagen_Dtorneo =  findViewById(R.id.imagen_Dtorneo);
        fecha_Dtorneo =  findViewById(R.id.fecha_Dtorneo);
        unirse_Dtorneo =  findViewById(R.id.unirse_Dtorneo);

        for(int i=0;i<4;i++) {
            tabs_Dtorneo.addTab(tabs_Dtorneo.newTab().setText(tituloIds[i]) );
        }


        container_Dtorneo.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs_Dtorneo));
        //tabLayoutT.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabs_Dtorneo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                container_Dtorneo.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabs_Dtorneo.getSelectedTabPosition();
    }


    public class SectionsDetalleTorneoAdapter extends FragmentPagerAdapter {

        public SectionsDetalleTorneoAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment  = null;

            switch (position){
                case 0:
                    fragment = new InfoDtorneoFragment();
                    break;
                case 1:
                    fragment = new EquiposDtorneoFragment();
                    break;
                case 2:
                    fragment = new TablaDtorneoFragment();
                    break;
                case 3:
                    fragment = new resultadosDtorneoFragment();
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
