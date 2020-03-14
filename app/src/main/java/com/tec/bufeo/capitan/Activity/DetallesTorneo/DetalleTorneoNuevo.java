package com.tec.bufeo.capitan.Activity.DetallesTorneo;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.GruposYEquipos.GruposYEquiposFragment;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.DetalleTorneo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.ViewModels.DetalleTorneoViewModel;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Views.InfoDtorneoFragment;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Views.PosicionesFragment;
import com.tec.bufeo.capitan.Activity.RegistroForo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class DetalleTorneoNuevo extends AppCompatActivity implements View.OnClickListener {

    public TabLayout tabs_Dtorneo;
    Button unirse_Dtorneo;
    ImageView imagen_Dtorneo;
    TextView nombre_torneo_Detalle;
    public ViewPager container_Dtorneo;
    private SectionsDetalleTorneoAdapter sectionsDetalleTorneoAdapter;
    String id_torneo,foto,nombre,id_usuario;
    Preferences preferences;
    FloatingActionButton reg_torneo_foro;





    private   String[] tituloIds = {
            "Información",
            "Grupos Y equipos",
            "Instancias y partidos",
            "Posiciones",
            "Estadisticas"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_torneo_nuevo);

        preferences = new Preferences(this);


        id_torneo = getIntent().getStringExtra("id_torneo");
        foto = getIntent().getStringExtra("foto");
        nombre = getIntent().getStringExtra("nombre");
        id_usuario = getIntent().getStringExtra("id_usuario");


        sectionsDetalleTorneoAdapter = new SectionsDetalleTorneoAdapter(getSupportFragmentManager());

        container_Dtorneo = findViewById(R.id.container_Dtorneo);
        container_Dtorneo.setAdapter(sectionsDetalleTorneoAdapter);
        tabs_Dtorneo =  findViewById(R.id.tabs_Dtorneo);
        imagen_Dtorneo =  findViewById(R.id.imagen_Dtorneo);
        nombre_torneo_Detalle =  findViewById(R.id.nombre_torneo_Detalle);
        unirse_Dtorneo =  findViewById(R.id.unirse_Dtorneo);
        reg_torneo_foro =  findViewById(R.id.reg_torneo_foro);





        for(int i=0;i<5;i++) {
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

        container_Dtorneo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    reg_torneo_foro.show();
                }else{
                    reg_torneo_foro.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //Glide.with(this).load(IP2+"/"+ foto).into(imagen_Dtorneo);
        nombre_torneo_Detalle.setText(nombre);


        showToolbar("",true);
        reg_torneo_foro.setOnClickListener(this);


    }


    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.GREEN);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);  //y habilitamos la flacha hacia atras

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }

    @Override
    public void onClick(View v) {

        if (v.equals(reg_torneo_foro)){
            Intent i = new Intent(DetalleTorneoNuevo.this, RegistroForo.class);
            i.putExtra("concepto","torneo");
            i.putExtra("id_torneo",id_torneo);
            startActivity(i);
        }
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
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new GruposYEquiposFragment();
                    Bundle bundle1 =  new Bundle();
                    bundle1.putString("id_torneo", id_torneo);
                    fragment.setArguments(bundle1);
                    break;
                case 2:
                    fragment = new InstanciasYPartidosFragment();
                    Bundle bundle3 =  new Bundle();
                    bundle3.putString("id_torneo", id_torneo);
                    fragment.setArguments(bundle3);
                    break;
                case 3:
                    fragment = new PosicionesFragment();
                    Bundle bundle2 =  new Bundle();
                    bundle2.putString("id_torneo", id_torneo);
                    fragment.setArguments(bundle2);


                    break;
                case 4:
                    fragment = new InstanciasYPartidosFragment();
                    Bundle bundle4 =  new Bundle();
                    bundle4.putString("id_torneo", id_torneo);
                    fragment.setArguments(bundle4);
                    break;

            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

    }
}
