package com.tec.bufeo.capitan.Activity.DetalleEquipo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoUsuarioPorEquipo;
import com.tec.bufeo.capitan.Modelo.Usuario;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class DetalleEquipo extends AppCompatActivity {

    public static ArrayList<Usuario> arrayusuario;
    public static ArrayList<Usuario> arrayusuarioactual;
    public static AdaptadorListadoUsuarioPorEquipo adaptadorListadoUsuarioPorEquipo;
    static ImageView img_fotoEquipo;
    static RecyclerView rcv_ParticipantesEquipo;
    static FloatingActionButton fab_agregarParticipantesEquipo;
    static Toolbar tlb_detalleEquipo;
    static CardView cdv_mensaje;
    static CollapsingToolbarLayout ctbl_detalleEquipo;
    static ProgressBar progressbar;
    static DataConnection dc;
    static DataConnection dc1;
    static DataConnection dc2;
    public static Activity activity;
    Context context;

    public static String id_equipo,nombre_equipo,foto_equipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_equipo);


        id_equipo = getIntent().getStringExtra("id_equipo");
        nombre_equipo = getIntent().getStringExtra("nombre_equipo");
        foto_equipo = getIntent().getStringExtra("foto_equipo");

        img_fotoEquipo = (ImageView) findViewById(R.id.img_fotoEquipo);
        tlb_detalleEquipo = (Toolbar) findViewById(R.id.tlb_detalleEquipo);
        ctbl_detalleEquipo = (CollapsingToolbarLayout) findViewById(R.id.ctbl_detalleEquipo);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        rcv_ParticipantesEquipo =(RecyclerView)findViewById(R.id.rcv_ParticipantesEquipo) ;
        fab_agregarParticipantesEquipo = (FloatingActionButton) findViewById(R.id.fab_agregarParticipantesEquipo);
        cdv_mensaje = (CardView)findViewById(R.id.cdv_mensaje);

        /*setSupportActionBar(tlb_detalleEquipo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);*/
        //tlb_detalleEquipo.setTitle(nombre_equipo);
        ctbl_detalleEquipo.setTitle(nombre_equipo);
        Picasso.with(getApplicationContext()).load(IP+"/"+ foto_equipo).into(img_fotoEquipo);
       // Picasso.with(context).load("http://"+IP+"/"+ obj.getEquipo_foto()).into(holder.img_fotoEquipo);


        fab_agregarParticipantesEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AgregarJugador.class);
               // intent.putExtra("id_empresa",id_empresa);
                startActivity(intent);
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);



       dc = new DataConnection(DetalleEquipo.this,"listarUsuarioPorEquipo",new Usuario(id_equipo),false);
        new DetalleEquipo.GetUsuPorEquipo().execute();
    }

    public static class GetUsuPorEquipo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayusuario = dc.getListadoUsuarioPorEquipo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressbar.setVisibility(ProgressBar.INVISIBLE);


            //Toast.makeText(getActivity(),"Z "+arrayempresas.size(),Toast.LENGTH_SHORT).show();


            GridLayoutManager linearLayoutManager = new GridLayoutManager(activity, 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_ParticipantesEquipo.setLayoutManager(linearLayoutManager);

            adaptadorListadoUsuarioPorEquipo = new AdaptadorListadoUsuarioPorEquipo(activity, arrayusuario, R.layout.rcv_item_list_usuarios_por_equipo, new AdaptadorListadoUsuarioPorEquipo.OnItemClickListener() {
                @Override
                public void onItemClick(Usuario usuario, int position) {

                }
            });
            rcv_ParticipantesEquipo.setAdapter(adaptadorListadoUsuarioPorEquipo);

            if (arrayusuario.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }

    public static class ActualizarUsuPorEquipo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayusuarioactual = dc1.getListadoUsuarioPorEquipo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



            GridLayoutManager linearLayoutManager = new GridLayoutManager(activity, 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_ParticipantesEquipo.setLayoutManager(linearLayoutManager);

            adaptadorListadoUsuarioPorEquipo = new AdaptadorListadoUsuarioPorEquipo(activity, arrayusuarioactual, R.layout.rcv_item_list_usuarios_por_equipo, new AdaptadorListadoUsuarioPorEquipo.OnItemClickListener() {
                @Override
                public void onItemClick(Usuario usuario, int position) {

                }
            });

            progressbar.setVisibility(ProgressBar.INVISIBLE);

            rcv_ParticipantesEquipo.setAdapter(adaptadorListadoUsuarioPorEquipo);


            if( arrayusuarioactual.size()>0){
                cdv_mensaje.setVisibility(View.INVISIBLE);
            }else{
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }

    public static void ActualizarEquipo(){
        dc1 = new DataConnection(activity,"listarUsuarioPorEquipo",new Usuario(id_equipo),false);
        new DetalleEquipo.ActualizarUsuPorEquipo().execute();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                onBackPressed();
                return true;
            case R.id.action_btn_editar:
                Toast.makeText(getApplicationContext(),"Editar",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_btn_buscar items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_btn_editar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
