package com.tec.bufeo.capitan.Activity.DetallesTorneo;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tec.bufeo.capitan.Activity.AgregarEquipos;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Views.EquiposDtorneoFragment;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Views.InfoDtorneoFragment;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.TablaDtorneo.Views.TablaDtorneoFragment;
import com.tec.bufeo.capitan.R;

public class DetalleTorneoNuevo extends AppCompatActivity {

    public TabLayout tabs_Dtorneo;
    Button fecha_Dtorneo,unirse_Dtorneo;
    ImageView imagen_Dtorneo;
    public ViewPager container_Dtorneo;
    private SectionsDetalleTorneoAdapter sectionsDetalleTorneoAdapter;
    String id_torneo,nombre,descripcion,lugar,fecha,hora,organizador, id_usuario;


    FloatingActionButton fab_agregarParticipantesTorneo;



    private   String[] tituloIds = {
            "Información",
            "Equipos",
            "Tabla",
            "Resultados"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_torneo_nuevo);

        id_torneo = getIntent().getStringExtra("id_torneo");
        id_usuario = getIntent().getStringExtra("id_usuario");
        nombre = getIntent().getStringExtra("nombre");
        descripcion = getIntent().getStringExtra("descripcion");
        lugar = getIntent().getStringExtra("lugar");
        fecha = getIntent().getStringExtra("fecha");
        hora = getIntent().getStringExtra("hora");
        organizador = getIntent().getStringExtra("organizador");


        sectionsDetalleTorneoAdapter = new SectionsDetalleTorneoAdapter(getSupportFragmentManager());

        container_Dtorneo = findViewById(R.id.container_Dtorneo);
        container_Dtorneo.setAdapter(sectionsDetalleTorneoAdapter);

        tabs_Dtorneo =  findViewById(R.id.tabs_Dtorneo);
        imagen_Dtorneo =  findViewById(R.id.imagen_Dtorneo);
        fecha_Dtorneo =  findViewById(R.id.fecha_Dtorneo);
        unirse_Dtorneo =  findViewById(R.id.unirse_Dtorneo);
        fab_agregarParticipantesTorneo =  findViewById(R.id.fab_agregarParticipantesTorneo);

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

        fab_agregarParticipantesTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetalleTorneoNuevo.this, AgregarEquipos.class);
                startActivity(i);
            }
        });


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
                    Bundle bundle =  new Bundle();
                    bundle.putString("id_torneo", id_torneo);
                    bundle.putString("organizador", organizador);
                    bundle.putString("lugar", lugar);
                    bundle.putString("fecha", fecha);
                    bundle.putString("hora", hora);
                    bundle.putString("titulo", nombre);
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new EquiposDtorneoFragment();
                    Bundle bundle1 =  new Bundle();
                    bundle1.putString("id_torneo", id_torneo);
                    fragment.setArguments(bundle1);
                    break;
                case 2:
                    fragment = new TablaDtorneoFragment();
                    Bundle bundle2 =  new Bundle();
                    bundle2.putString("id_torneo", id_torneo);
                    fragment.setArguments(bundle2);
                    break;
                case 3:
                    fragment = new ResultadosDtorneoFragment();
                    Bundle bundle3 =  new Bundle();
                    bundle3.putString("id_torneo", id_torneo);
                    fragment.setArguments(bundle3);
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
