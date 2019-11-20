package com.tec.bufeo.capitan.Activity.DetalleEquipo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tec.bufeo.capitan.R;

public class DetalleEquipoNuevo extends AppCompatActivity {

    public TabLayout tabs_Dequipo;
    Button btn_retarDequipo,unirse_Dequipo;
    ImageView imagen_Dequipo;
    public ViewPager container_Dequipo;
    private SectionsDetalleEquipoAdapter sectionsDetalleEquipoAdapter;

    FloatingActionButton fab_agregarParticipantesEquipo;


    private   String[] tituloIds = {
            "Jugadores",
            "Torneos",
            "Proximos Partidos"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_equipo_nuevo);

        sectionsDetalleEquipoAdapter = new SectionsDetalleEquipoAdapter(getSupportFragmentManager());

        container_Dequipo = findViewById(R.id.container_Dequipo);
        container_Dequipo.setAdapter(sectionsDetalleEquipoAdapter);

        tabs_Dequipo =  findViewById(R.id.tabs_Dequipo);
        imagen_Dequipo =  findViewById(R.id.imagen_Dequipo);
        btn_retarDequipo =  findViewById(R.id.btn_retarDequipo);
        unirse_Dequipo =  findViewById(R.id.unirse_Dequipo);
        fab_agregarParticipantesEquipo =  findViewById(R.id.fab_agregarParticipantesEquipo);

        for(int i=0;i<3;i++) {
            tabs_Dequipo.addTab(tabs_Dequipo.newTab().setText(tituloIds[i]) );
        }


        container_Dequipo.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs_Dequipo));
        //tabLayoutT.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabs_Dequipo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                container_Dequipo.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabs_Dequipo.getSelectedTabPosition();

        fab_agregarParticipantesEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(DetalleEquipoNuevo.this,AgregarJugador.class);
                startActivity(i);
            }
        });
    }

    public class SectionsDetalleEquipoAdapter extends FragmentPagerAdapter {

        public SectionsDetalleEquipoAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment  = null;

            switch (position){
                case 0:
                    fragment = new JugadoresDequiposFragment();
                    break;
                case 1:
                    fragment = new TorneoDequiposFragment();
                    break;
                case 2:
                    fragment = new ProximoPartidosDequiposFragment();
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

