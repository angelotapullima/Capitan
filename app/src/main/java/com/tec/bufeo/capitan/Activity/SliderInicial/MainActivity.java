package com.tec.bufeo.capitan.Activity.SliderInicial;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tec.bufeo.capitan.Activity.SliderInicial.adapter.ViewPagerAdapter;
import com.tec.bufeo.capitan.Activity.SliderInicial.model.ItemObj;
import com.tec.bufeo.capitan.Activity.SliderInicial.transformer.SwipeTransform;
import com.tec.bufeo.capitan.Activity.SliderInicial.viewholder.ItemView;
import com.tec.bufeo.capitan.Activity.Splash;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemView.ItemArrowInterface {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ArrayList<ItemObj> arrayList;
    private RelativeLayout parent;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new Preferences(this);
        PrefIntro();
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        parent = findViewById(R.id.parent);
        initData();
        changeBackground(arrayList.get(0).getAccentColor());

        adapter = new ViewPagerAdapter(this, arrayList, MainActivity.this);
        viewPager.setPageTransformer(true, new SwipeTransform());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeBackground(arrayList.get(position).getAccentColor());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }


    boolean Intro;
    void PrefIntro(){

        Intro = Boolean.valueOf(Pref.read(getApplication(),"wow","true"));
        if(Intro){

        }else{
            Intent goIntro=new Intent(this, Splash.class);
            startActivity(goIntro);
            finish();
        }

    }
    private void initData() {
        arrayList = new ArrayList<>();


        arrayList.add(new ItemObj("https://guabba.com/capitan2/media/pantallax.png", "#0ABF53", "Elige tu cancha preferida y comienza a divertirte"));
        arrayList.add(new ItemObj("https://guabba.com/capitan2/media/pantallax.png", "#D54825", "Paga con el método que mas prefieras, Bufis o crea tus chanchas"));
        arrayList.add(new ItemObj("https://guabba.com/capitan2/media/pantallax.png", "#0ABF53", "Crea Equipos y agrega a tus amigos para retar a la comunidad pelotera"));
        arrayList.add(new ItemObj("https://guabba.com/capitan2/media/pantallax.png", "#FFD000", "Recarga tus bufis con el método de más prefieras, BufeoAgentes o Banca Móvil"));
    }

    private void changeBackground(String color) {
        parent.setBackgroundColor(Color.parseColor(color));
        changeStatusBarColor(color);
    }

    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    @Override
    public void scrollNextPosition(int position) {
        viewPager.setCurrentItem(position, true);
    }
}
