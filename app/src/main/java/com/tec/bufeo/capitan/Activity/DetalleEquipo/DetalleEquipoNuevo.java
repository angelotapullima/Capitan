package com.tec.bufeo.capitan.Activity.DetalleEquipo;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Views.ChanchasFragment;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Views.EstadisticasDeEquiposFragment;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Views.TorneoDequiposFragment;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Views.RegistrarJugadoresEnEquipos;
import com.tec.bufeo.capitan.Activity.RegistroReto;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class DetalleEquipoNuevo extends AppCompatActivity implements View.OnClickListener {

    public TabLayout tabs_Dequipo;
    Button btn_retarDequipo;
    ImageView imagen_Dequipo;
    TextView name_Equipex;
    public ViewPager container_Dequipo;
    private SectionsDetalleEquipoAdapter sectionsDetalleEquipoAdapter;
    String equipo_id,equipo_nombre,equipo_foto,capitan_nombre,capitan_id;
    Preferences preferences;
    FloatingActionButton fab_agregarParticipantesEquipo;
    UniversalImageLoader universalImageLoader;

    private   String[] tituloIds = {
            "Jugadores",
            "Torneos",
            "Chanchas",
            "Estadísticas"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_equipo_nuevo);

        equipo_id=getIntent().getExtras().getString("id_equipo");
        equipo_nombre=getIntent().getExtras().getString("nombre_equipo");
        equipo_foto=getIntent().getExtras().getString("foto_equipo");
        capitan_nombre=getIntent().getExtras().getString("capitan_equipo");
        capitan_id=getIntent().getExtras().getString("capitan_id");


        preferences = new Preferences(this);
        universalImageLoader = new UniversalImageLoader(this);
        sectionsDetalleEquipoAdapter = new SectionsDetalleEquipoAdapter(getSupportFragmentManager());

        container_Dequipo = findViewById(R.id.container_Dequipo);
        container_Dequipo.setAdapter(sectionsDetalleEquipoAdapter);

        tabs_Dequipo =  findViewById(R.id.tabs_Dequipo);
        imagen_Dequipo =  findViewById(R.id.imagen_Dequipo);
        btn_retarDequipo =  findViewById(R.id.btn_retarDequipo);
        name_Equipex =  findViewById(R.id.name_Equipex);
        fab_agregarParticipantesEquipo = findViewById(R.id.fab_agregarParticipantesEquipo);



        if (capitan_id.equals(preferences.getIdUsuarioPref())){
            btn_retarDequipo.setVisibility(View.GONE);

        }

        name_Equipex.setText(equipo_nombre);
        for(int i=0;i<4;i++) {
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

        container_Dequipo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    fab_agregarParticipantesEquipo.show();
                }else{
                    fab_agregarParticipantesEquipo.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabs_Dequipo.getSelectedTabPosition();

        ImageLoader.getInstance().init(universalImageLoader.getConfig());
        UniversalImageLoader.setImage(IP2+"/"+ equipo_foto,imagen_Dequipo,null);

        fab_agregarParticipantesEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetalleEquipoNuevo.this, RegistrarJugadoresEnEquipos.class);
                i.putExtra("id_equipo",equipo_id);
                i.putExtra("nombre",equipo_nombre);
                startActivity(i);
            }
        });

        showToolbar("",true);
        btn_retarDequipo.setOnClickListener(this);
    }
    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.GREEN);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);//y habilitamos la flacha hacia atras

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btn_retarDequipo)){
            Intent intent = new Intent(DetalleEquipoNuevo.this, RegistroReto.class);
            intent.putExtra("nombre_retado", equipo_nombre);
            intent.putExtra("foto_retado", equipo_foto);
            intent.putExtra("id_retado", equipo_id);
            intent.putExtra("capitan_id", capitan_id);
            startActivity(intent);
        }
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
                    Bundle bundle =  new Bundle();
                    bundle.putString("id_equipo", equipo_id);
                    bundle.putString("nombre", equipo_nombre);
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new TorneoDequiposFragment();
                    Bundle bundle1 =  new Bundle();
                    bundle1.putString("id_equipo", equipo_id);
                    bundle1.putString("nombre", equipo_nombre);
                    fragment.setArguments(bundle1);
                    break;
                case 2:
                    fragment = new ChanchasFragment();
                    Bundle bundle4 =  new Bundle();
                    bundle4.putString("id_equipo", equipo_id);
                    bundle4.putString("nombre", equipo_nombre);
                    bundle4.putString("capitan_id", capitan_id);
                    fragment.setArguments(bundle4);
                    break;

                case 3:
                    fragment = new EstadisticasDeEquiposFragment();
                    Bundle bundle3 =  new Bundle();
                    bundle3.putString("id_equipo", equipo_id);
                    bundle3.putString("nombre", equipo_nombre);
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

