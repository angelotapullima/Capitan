package com.tec.bufeo.capitan.Activity;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.tec.bufeo.capitan.Fragments.FragmentHoy;
import com.tec.bufeo.capitan.Fragments.FragmentMañana;
import com.tec.bufeo.capitan.Fragments.FragmentPasMañana;
import com.tec.bufeo.capitan.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.tec.bufeo.capitan.Activity.DetalleNegocio.context;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.fecha_actual;

public class DetalleCanchas extends AppCompatActivity {

    public static  TabLayout tabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;
    public static  String cancha_id, precio_dia,precio_noche, cancha_nombre,fecha_actual_mas_uno, fecha_actual_mas_dos ;
    public Date date;
    private FloatingActionButton fab_verReportes;


    public static boolean existeHoy = true;
    public static boolean existeMñn = false;
    public static boolean existePasMñn = false;
    public  static String[] tituloIds = new String[3];
    private  static int[] imgIds = {

            R.drawable.calendar_blank,
            R.drawable.calendar,
            R.drawable.calendar_range
    };

    private  static int[] imgBordeIds = {
            R.drawable.calendar_blank_outline,
            R.drawable.calendar_outline,
            R.drawable.calendar_range_outline
    };



    public static DetalleCanchas newInstance() {
        return new DetalleCanchas();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_canchas);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager)findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        cancha_id = getIntent().getStringExtra("id_cancha");
        cancha_nombre = getIntent().getStringExtra("nombre_cancha");
        precio_dia = getIntent().getStringExtra("precio_dia");
        precio_noche = getIntent().getStringExtra("precio_noche");

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

        Toast.makeText(context,"f"+ fecha_actual_mas_uno,Toast.LENGTH_LONG).show();
        Toast.makeText(context,"f2"+ fecha_actual_mas_dos,Toast.LENGTH_LONG).show();

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

                    break;
                case 1:
                    existeHoy = false;
                    existeMñn =true;
                    existePasMñn =false;
                    fragment = new FragmentMañana();
                    break;

                case 2:
                    existeHoy = false;
                    existeMñn =false;
                    existePasMñn =true;
                    fragment = new FragmentPasMañana();
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
