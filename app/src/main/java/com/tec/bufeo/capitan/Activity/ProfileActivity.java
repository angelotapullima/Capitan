package com.tec.bufeo.capitan.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Activity.old.DetalleEquipo;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.AdaptadorForo;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Views.AdaptadorMiEquipo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    RecyclerView recyler_publish,recycler_equipos;
    FeedListViewModel feedListViewModel;
    AdaptadorForo adaptadorForo ;
    MisEquiposViewModel misEquiposViewModel;
    AdaptadorMiEquipo adaptadorMiEquipo;
    Preferences preferences;
    ImageView fotodeperfil;
    TextView nombre_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        feedListViewModel = ViewModelProviders.of(this).get(FeedListViewModel.class);
        misEquiposViewModel = ViewModelProviders.of(this).get(MisEquiposViewModel.class);
        preferences =  new Preferences(this);



        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
        {
          this.getWindow().getDecorView().setSystemUiVisibility( //View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                     View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
          );
        }

        initViews();
        setAdapter();
        cargarvista();
        showToolbar("",true);



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }




    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.WHITE);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);  //y habilitamos la flacha hacia atras

    }

    private void setAdapter() {

        adaptadorForo = new AdaptadorForo(this, new AdaptadorForo.OnItemClickListener() {
            @Override
            public void onItemClick(String dato, ModelFeed feedTorneo, int position) {

            }
        });

        recyler_publish.setAdapter(adaptadorForo);
        recyler_publish.setLayoutManager(new LinearLayoutManager(this));


        adaptadorMiEquipo =  new AdaptadorMiEquipo(this,"", new AdaptadorMiEquipo.OnItemClickListener() {
            @Override
            public void onItemClick(Mequipos mequipos, int position) {

                Intent intent = new Intent(ProfileActivity.this, DetalleEquipo.class);
                intent.putExtra("id_equipo", mequipos.getEquipo_id());
                intent.putExtra("nombre_equipo", mequipos.getEquipo_nombre());
                intent.putExtra("foto_equipo", mequipos.getEquipo_foto());
                startActivity(intent);
            }
        });



        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        recycler_equipos.setLayoutManager(linearLayoutManager);
        recycler_equipos.setAdapter(adaptadorMiEquipo);
    }

    private void cargarvista() {

        feedListViewModel.getAllIdPosts().observe(this, new Observer<List<ModelFeed>>() {
            @Override
            public void onChanged(List<ModelFeed> modelFeeds) {

            }
        });


        misEquiposViewModel.getAllEquipo("si").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(@Nullable List<Mequipos> mequipos) {
                adaptadorMiEquipo.setWords(mequipos);
            }
        });

    }

    private void initViews() {
        recyler_publish = findViewById(R.id.recyler_publish);
        recycler_equipos = findViewById(R.id.recycler_equipos);
        fotodeperfil = findViewById(R.id.fotodeperfil);
        nombre_perfil = findViewById(R.id.nombre_perfil);



        Picasso.with(this).load(preferences.getFotoUsuario()).into(fotodeperfil);
        nombre_perfil.setText(preferences.getPersonName()+" "+preferences.getPersonSurname());
    }
}
