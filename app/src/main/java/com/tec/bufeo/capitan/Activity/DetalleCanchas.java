package com.tec.bufeo.capitan.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.bufeo.capitan.Fragments.FragmentHoy;
import com.tec.bufeo.capitan.Fragments.FragmentMañana;
import com.tec.bufeo.capitan.Fragments.FragmentPasMañana;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DetalleCanchas extends AppCompatActivity implements View.OnClickListener {

    public  TabLayout tabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;
    public  String cancha_id, saldo,precio_dia,precio_noche,
            cancha_nombre,horario,fecha_actual_mas_uno, fecha_actual_mas_dos ,nombre_empresa,tipo_usuario,fecha_actual,hora_actual;
    public Date date;
    TextView saldo_contable;
    ImageView arrow_back;
    LinearLayout lbufis;


    public boolean existeHoy = true;
    public boolean existeMñn = false;
    public boolean existePasMñn = false;
    public  String[] tituloIds = new String[3];
    private   int[] imgIds = {

            R.drawable.calendar_blank,
            R.drawable.calendar,
            R.drawable.calendar_range
    };

    private   int[] imgBordeIds = {
            R.drawable.calendar_blank_outline,
            R.drawable.calendar_outline,
            R.drawable.calendar_range_outline
    };



    public  DetalleCanchas newInstance() {
        return new DetalleCanchas();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_canchas);



        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        saldo_contable= findViewById(R.id.saldo_contable);
        arrow_back= findViewById(R.id.arrow_back);
        lbufis= findViewById(R.id.lbufis);
        mViewPager = (ViewPager)findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        cancha_id = getIntent().getStringExtra("id_cancha");
        nombre_empresa = getIntent().getStringExtra("nombre_empresa");
        cancha_nombre = getIntent().getStringExtra("nombre_cancha");
        precio_dia = getIntent().getStringExtra("precio_dia");
        precio_noche = getIntent().getStringExtra("precio_noche");
        horario = getIntent().getStringExtra("horario");
        tipo_usuario = getIntent().getStringExtra("tipo_usuario");
        saldo = getIntent().getStringExtra("saldo");
        fecha_actual = getIntent().getStringExtra("fecha_actual");
        hora_actual = getIntent().getStringExtra("hora_actual");


        if (tipo_usuario.equals("admin")){
            lbufis.setVisibility(View.GONE);
        }
        saldo_contable.setText(saldo);
        String dtStart = fecha_actual;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dtStart);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //fecha actual mas 1
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
        //SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        // fecha_actual_mas_uno_insert = format1.format(calendar.getTime());

        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        fecha_actual_mas_uno = format2.format(calendar.getTime());

        //Fecha actual mas 2

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date); // Configuramos la fecha que se recibe
        calendar2.add(Calendar.DAY_OF_YEAR,2);  // numero de días a añadir, o restar en caso de días<0
        //SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
        //fecha_actual_mas_dos_insert = format3.format(calendar2.getTime());
        SimpleDateFormat format4 = new SimpleDateFormat("dd-MM-yyyy");
        fecha_actual_mas_dos = format4.format(calendar2.getTime());

        /*Toast.makeText(context,"f"+ fecha_actual_mas_uno,Toast.LENGTH_LONG).show();
        Toast.makeText(context,"f2"+ fecha_actual_mas_dos,Toast.LENGTH_LONG).show();*/

        tituloIds[0] = "HOY";
        tituloIds[1] = fecha_actual_mas_uno;
        tituloIds[2] = fecha_actual_mas_dos;


        tabLayout = (TabLayout) findViewById(R.id.tabs);


        for(int i=0;i<3;i++) {
            tabLayout.addTab(tabLayout.newTab(). setIcon(imgBordeIds[i]).setText(tituloIds[i]) );
        }


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        tabLayout.getSelectedTabPosition();




        arrow_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.equals(arrow_back)){

            finish();
        }
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
                    existeHoy = true;
                    existeMñn =false;
                    existePasMñn =false;
                    fragment = new FragmentHoy();
                    Bundle bundle =  new Bundle();
                    bundle.putString("cancha_id", cancha_id);
                    bundle.putString("cancha_nombre", cancha_nombre);
                    bundle.putString("precio_dia", precio_dia);
                    bundle.putString("precio_noche", precio_noche);
                    bundle.putString("hora_actual", hora_actual);
                    bundle.putString("fecha_actual", fecha_actual);
                    bundle.putString("horario", horario);
                    bundle.putString("nombre_empresa", nombre_empresa);
                    bundle.putString("tipo_usuario", tipo_usuario);
                    bundle.putString("saldo", saldo);
                    fragment.setArguments(bundle);

                    break;
                case 1:
                    existeHoy = false;
                    existeMñn =true;
                    existePasMñn =false;
                    fragment = new FragmentMañana();
                    Bundle bundle1 =  new Bundle();
                    bundle1.putString("cancha_id", cancha_id);
                    bundle1.putString("cancha_nombre", cancha_nombre);
                    bundle1.putString("precio_dia", precio_dia);
                    bundle1.putString("precio_noche", precio_noche);
                    bundle1.putString("hora_actual", hora_actual);
                    bundle1.putString("fecha_actual", fecha_actual);
                    bundle1.putString("horario", horario);
                    bundle1.putString("nombre_empresa", nombre_empresa);
                    bundle1.putString("tipo_usuario", tipo_usuario);
                    bundle1.putString("saldo", saldo);
                    fragment.setArguments(bundle1);
                    break;

                case 2:
                    existeHoy = false;
                    existeMñn =false;
                    existePasMñn =true;
                    fragment = new FragmentPasMañana();
                    Bundle bundle2 =  new Bundle();
                    bundle2.putString("cancha_id", cancha_id);
                    bundle2.putString("cancha_nombre", cancha_nombre);
                    bundle2.putString("precio_dia", precio_dia);
                    bundle2.putString("precio_noche", precio_noche);
                    bundle2.putString("hora_actual", hora_actual);
                    bundle2.putString("fecha_actual", fecha_actual);
                    bundle2.putString("horario", horario);
                    bundle2.putString("nombre_empresa", nombre_empresa);
                    bundle2.putString("tipo_usuario", tipo_usuario);
                    bundle2.putString("saldo", saldo);
                    fragment.setArguments(bundle2);
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
