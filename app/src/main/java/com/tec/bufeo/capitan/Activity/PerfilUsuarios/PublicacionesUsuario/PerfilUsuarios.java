package com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.DetalleEquipoNuevo;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.ViewModels.DatosUsuarioViewModel;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Models.EquiposUsuarios;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.ViewModels.EquiposUsuariosViewModel;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Models.PublicacionesUsuario;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.ViewModels.PublicacionesUsuarioViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class PerfilUsuarios extends AppCompatActivity {

    TextView posicion_usuarios,Ncamiseta_usuarios,habilidadUsuarios,EmailUsuarios,nombre_perfil_Usuarios;
    LinearLayout InvitarEquipo,RetarEquipo;
    ImageButton InvitarEquipoB,RetarEquipoB;
    RecyclerView recycler_equipos_usuarios,recycler_publicaciones_usuarios;
    DatosUsuarioViewModel datosUsuarioViewModel;
    EquiposUsuariosViewModel equiposUsuariosViewModel;
    PublicacionesUsuarioViewModel publicacionesUsuarioViewModel;
    AdapterPublicacionesUsuario adapterPublicacionesUsuario;
    String id_user;
    Preferences preferences;
    ImageView fotodeperfil_Usuarios;
    LinearLayout layout_carga_perfil_Usuarios;
    AdaptadorEquiposUsuario adaptadorEquiposUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuarios);

        datosUsuarioViewModel = ViewModelProviders.of(this).get(DatosUsuarioViewModel.class);
        equiposUsuariosViewModel = ViewModelProviders.of(this).get(EquiposUsuariosViewModel.class);
        publicacionesUsuarioViewModel = ViewModelProviders.of(this).get(PublicacionesUsuarioViewModel.class);


        preferences = new Preferences(this);
        id_user= getIntent().getExtras().getString("id_user");

        initViews();
        cargarvista();
        setAdapter();
        showToolbar("",true);

    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.WHITE);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);  //y habilitamos la flacha hacia atras

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();//definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }
    private void initViews() {
        posicion_usuarios= findViewById(R.id.posicion_usuarios);
        Ncamiseta_usuarios= findViewById(R.id.Ncamiseta_usuarios);
        habilidadUsuarios= findViewById(R.id.habilidadUsuarios);
        EmailUsuarios= findViewById(R.id.EmailUsuarios);
        fotodeperfil_Usuarios= findViewById(R.id.fotodeperfil_Usuarios);
        nombre_perfil_Usuarios= findViewById(R.id.nombre_perfil_Usuarios);

        layout_carga_perfil_Usuarios= findViewById(R.id.layout_carga_perfil_Usuarios);

        InvitarEquipo= findViewById(R.id.InvitarEquipo);
        RetarEquipo= findViewById(R.id.RetarEquipo);
        InvitarEquipoB= findViewById(R.id.InvitarEquipoB);
        RetarEquipoB= findViewById(R.id.RetarEquipoB);

        recycler_equipos_usuarios= findViewById(R.id.recycler_equipos_usuarios);
        recycler_publicaciones_usuarios= findViewById(R.id.recycler_publicaciones_usuarios);

        recycler_publicaciones_usuarios.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {

                    //Haga puedes icorporar la logica que deseas
                }
            }
        });


    }
    private void cargarvista() {

        datosUsuarioViewModel.getAll(id_user,preferences.getToken()).observe(this, new Observer<List<DatosUsuario>>() {
            @Override
            public void onChanged(List<DatosUsuario> datosUsuarios) {

                if (datosUsuarios.size()>0){
                    layout_carga_perfil_Usuarios.setVisibility(View.GONE);
                    posicion_usuarios.setText(datosUsuarios.get(0).getPosicion());
                    Ncamiseta_usuarios.setText(datosUsuarios.get(0).getNum());
                    habilidadUsuarios.setText(datosUsuarios.get(0).getHabilidad());
                    EmailUsuarios.setText(datosUsuarios.get(0).getEmail());
                    nombre_perfil_Usuarios.setText(datosUsuarios.get(0).getNombre());
                    Glide.with(getApplicationContext()).load(IP2+"/"+datosUsuarios.get(0).getImg()).into(fotodeperfil_Usuarios);
                }else{

                }
            }
        });

        equiposUsuariosViewModel.getAll(id_user,preferences.getToken()).observe(this, new Observer<List<EquiposUsuarios>>() {
            @Override
            public void onChanged(List<EquiposUsuarios> equiposUsuarios) {
                if (equiposUsuarios.size()>0){
                    adaptadorEquiposUsuario.setWords(equiposUsuarios);
                }
            }
        });


        publicacionesUsuarioViewModel.getAllPosts(id_user,"0","0",preferences.getToken()).observe(this, new Observer<List<PublicacionesUsuario>>() {
            @Override
            public void onChanged(List<PublicacionesUsuario> publicacionesUsuarios) {
                if (publicacionesUsuarios.size()>0){
                    adapterPublicacionesUsuario.setWords(publicacionesUsuarios);
                }

            }
        });
    }
    private void setAdapter() {

        adaptadorEquiposUsuario= new AdaptadorEquiposUsuario(this, new AdaptadorEquiposUsuario.OnItemClickListener() {
            @Override
            public void onItemClick(EquiposUsuarios mequipos, String tipo, int position) {
                if (tipo.equals("img_fotoEquipoUsuarios")){
                    Intent i = new Intent(PerfilUsuarios.this, DetalleEquipoNuevo.class);
                    i.putExtra("id_equipo", mequipos.getEquipo_id());
                    i.putExtra("nombre_equipo", mequipos.getNombre());
                    i.putExtra("foto_equipo", mequipos.getFoto());
                    i.putExtra("capitan_equipo", mequipos.getCapitan());
                    i.putExtra("capitan_id", mequipos.getCapitan_id());
                    startActivity(i);
                }

            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        recycler_equipos_usuarios.setLayoutManager(linearLayoutManager);
        recycler_equipos_usuarios.setAdapter(adaptadorEquiposUsuario);




        adapterPublicacionesUsuario = new AdapterPublicacionesUsuario(this, new AdapterPublicacionesUsuario.OnItemClickListener() {
            @Override
            public void onItemClick(String dato, PublicacionesUsuario feedTorneo, int position) {

            }
        });

        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(this, 1);
        linearLayoutManager1.setOrientation(linearLayoutManager.VERTICAL);
        recycler_publicaciones_usuarios.setLayoutManager(linearLayoutManager1);
        recycler_publicaciones_usuarios.setAdapter(adapterPublicacionesUsuario);
    }
}
