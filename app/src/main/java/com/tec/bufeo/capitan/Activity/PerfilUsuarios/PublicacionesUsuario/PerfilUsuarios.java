package com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.DetalleEquipoNuevo;
import com.tec.bufeo.capitan.Activity.DetalleFotoUsuario;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.DetalleTorneoNuevo;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.ViewModels.DatosUsuarioViewModel;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Models.EquiposUsuarios;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.ViewModels.EquiposUsuariosViewModel;
import com.tec.bufeo.capitan.Activity.ProfileActivity;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Repository.FeedRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Views.AdaptadorForo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class PerfilUsuarios extends AppCompatActivity implements View.OnClickListener {

    TextView posicion_usuarios,Ncamiseta_usuarios,habilidadUsuarios,EmailUsuarios,nombre_perfil_Usuarios;
    LinearLayout InvitarEquipo,RetarEquipo;
    ImageButton InvitarEquipoB,RetarEquipoB;
    RecyclerView recycler_equipos_usuarios,recycler_publicaciones_usuarios;
    DatosUsuarioViewModel datosUsuarioViewModel;
    EquiposUsuariosViewModel equiposUsuariosViewModel;
    FeedListViewModel feedListViewModel;
    String id_user;
    Preferences preferences;
    ImageView fotodeperfil_Usuarios;
    LinearLayout layout_carga_perfil_Usuarios;
    AdaptadorEquiposUsuario adaptadorEquiposUsuario;
    AdaptadorForo adaptadorForo;
    UniversalImageLoader universalImageLoader;

    String fotexPerfil="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuarios);

        datosUsuarioViewModel = ViewModelProviders.of(this).get(DatosUsuarioViewModel.class);
        equiposUsuariosViewModel = ViewModelProviders.of(this).get(EquiposUsuariosViewModel.class);
        feedListViewModel = ViewModelProviders.of(this).get(FeedListViewModel.class);


        preferences = new Preferences(this);
        universalImageLoader=new UniversalImageLoader(this);
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

        fotodeperfil_Usuarios.setOnClickListener(this);

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
                    UniversalImageLoader.setImage(IP2+"/"+datosUsuarios.get(0).getImg(),fotodeperfil_Usuarios,null);
                    fotexPerfil =datosUsuarios.get(0).getImg();
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

        feedListViewModel.getIdUsuario(id_user,preferences.getToken(),"").observe(this, new Observer<List<ModelFeed>>() {
            @Override
            public void onChanged(List<ModelFeed> modelFeeds) {
                adaptadorForo.setWords(modelFeeds);
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




        adaptadorForo = new AdaptadorForo(this, new AdaptadorForo.OnItemClickListener() {
            @Override
            public void onItemClick(String dato, ModelFeed feedTorneo, int position) {

                /*if (dato.equals("btnAccion")){

                    idpublicacion=feedTorneo.getPublicacion_id();
                    fab_registrarForo.hide();
                    if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }*/
                if (dato.equals("foto_perfil_publicacion")){
                    if (feedTorneo.getId_torneo().equals("0")){
                        if(feedTorneo.getUsuario_id().equals(preferences.getIdUsuarioPref())){
                            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(i);
                        }else{
                            Intent i = new Intent(getApplicationContext(), PerfilUsuarios.class);
                            i.putExtra("id_user",feedTorneo.getUsuario_id());
                            startActivity(i);
                        }
                    }else{
                        Intent i = new Intent(getApplicationContext(), DetalleTorneoNuevo.class);
                        i.putExtra("id_torneo",feedTorneo.getId_torneo());
                        i.putExtra("foto",feedTorneo.getTorneo_foto());
                        i.putExtra("nombre",feedTorneo.getPublicacion_torneo());
                        i.putExtra("id_usuario",feedTorneo.getUsuario_id());
                        startActivity(i);
                    }

                }if (dato.equals("txt_usuarioForo")){
                    if (feedTorneo.getId_torneo().equals("0")){
                        if(feedTorneo.getUsuario_id().equals(preferences.getIdUsuarioPref())){
                            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(i);
                        }else{
                            Intent i = new Intent(getApplicationContext(), PerfilUsuarios.class);
                            i.putExtra("id_user",feedTorneo.getUsuario_id());
                            startActivity(i);
                        }
                    }else{
                        Intent i = new Intent(getApplicationContext(), DetalleTorneoNuevo.class);
                        i.putExtra("id_torneo",feedTorneo.getId_torneo());
                        i.putExtra("foto",feedTorneo.getTorneo_foto());
                        i.putExtra("nombre",feedTorneo.getPublicacion_torneo());
                        i.putExtra("id_usuario",feedTorneo.getUsuario_id());
                        startActivity(i);
                    }


                }if (dato.equals("img_fotoForo")){
                    Intent i = new Intent(getApplicationContext(), DetalleFotoUsuario.class);
                    i.putExtra("foto",feedTorneo.getForo_foto());
                    i.putExtra("descripcion",feedTorneo.getForo_descripcion());
                    i.putExtra("cantidad_comentarios",feedTorneo.getCant_Comentarios());
                    i.putExtra("id_publicacion",feedTorneo.getPublicacion_id());
                    startActivity(i);
                }if(dato.equals("pedir")){
                    //feed();
                    preferences.codeAdvertencia(String.valueOf(position));
                }else if(dato.equals("verMasTorneo")){
                    Intent i = new Intent(getApplicationContext(), DetalleTorneoNuevo.class);
                    i.putExtra("id_torneo",feedTorneo.getId_torneo());
                    i.putExtra("foto",feedTorneo.getTorneo_foto());
                    i.putExtra("nombre",feedTorneo.getPublicacion_torneo());
                    i.putExtra("id_usuario",feedTorneo.getUsuario_id());
                    startActivity(i);
                }else if (dato.equals("imgbt_like")){
                    if (feedTorneo.getDio_like().equals("0")){
                        darlike(feedTorneo.getPublicacion_id());
                    }else{
                        dislike(feedTorneo.getPublicacion_id());
                    }
                }

            }
        });

        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(this, 1);
        linearLayoutManager1.setOrientation(linearLayoutManager.VERTICAL);
        recycler_publicaciones_usuarios.setLayoutManager(linearLayoutManager1);
        recycler_publicaciones_usuarios.setAdapter(adaptadorForo);
    }


    StringRequest stringRequest;
    JSONObject json_data;
    String resultado;
    int totalLikes;
    Application application;
    private void darlike(final String idlike) {
        String url =IP2+"/api/Foro/dar_like";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("darlike: ",""+response);

                try {
                    json_data = new JSONObject(response);
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    resultado = jsonNodev.optString("resultado");
                    totalLikes = Integer.parseInt(jsonNodev.optString("likes"));

                    if (resultado.equals("1")){

                        FeedRoomDBRepository feedRoomDBRepository = new FeedRoomDBRepository(application);
                        feedRoomDBRepository.darlike(idlike);
                        feedRoomDBRepository.cantidadLikes(String.valueOf(totalLikes));
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.d("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("usuario_id",preferences.getIdUsuarioPref());
                parametros.put("publicacion_id",idlike);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());

                return parametros;

            }
        };
        /*requestQueue.add(stringRequest);*/
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    private void dislike(final String iddislike) {
        String url =IP2+"/api/Foro/quitar_like";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dislike: ",""+response);

                try {
                    json_data = new JSONObject(response);
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    resultado = jsonNodev.optString("resultado");
                    totalLikes = Integer.parseInt(jsonNodev.optString("likes"));

                    if (resultado.equals("1")){

                        FeedRoomDBRepository feedRoomDBRepository = new FeedRoomDBRepository(application);
                        feedRoomDBRepository.dislike(iddislike);
                        feedRoomDBRepository.cantidadLikes(String.valueOf(totalLikes));
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.d("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("usuario_id",preferences.getIdUsuarioPref());
                parametros.put("publicacion_id",iddislike);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());

                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(fotodeperfil_Usuarios)){
            if (!fotexPerfil.equals("")){
                Intent i = new Intent(getApplicationContext(), DetalleFotoUsuario.class);
                i.putExtra("foto",fotexPerfil);
                i.putExtra("descripcion","0");
                i.putExtra("cantidad_comentarios","0");
                i.putExtra("id_publicacion","0");
                startActivity(i);
            }

        }
    }
}
