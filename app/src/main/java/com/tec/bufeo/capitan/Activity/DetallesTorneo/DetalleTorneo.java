package com.tec.bufeo.capitan.Activity.DetallesTorneo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.AgregarEquipos;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoEquipoTorneo;
import com.tec.bufeo.capitan.others.Equipo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;

import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_id;

public class DetalleTorneo extends AppCompatActivity  implements View.OnClickListener{

    static AdaptadorListadoEquipoTorneo adaptadorListadoEquipoTorneo;
    public ArrayList<Equipo> arrayequipo;
    public static ArrayList<Equipo> arrayequipoActual;
    DataConnection dc;
    static DataConnection dc1;
    static Context context;
    static Activity activity;
    private ImageView img_fotoTorneo;
    static CardView cdv_mensaje;
    public static  ProgressBar progressbar;
    private TextView txt_nombreTorneo,txt_nombreOrganizador, txt_descripcionTorneo,txt_lugarTorneo,txt_horaTorneo,txt_fechaTorneo;
    static RecyclerView rcv_EquiposTorneo;
    FloatingActionButton fab_agregarParticipantesTorneo;
    static String id_torneo,nombre,descripcion,lugar,fecha,hora,organizador, id_usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState)


        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detalle_torneo);

            img_fotoTorneo = findViewById(R.id.img_fotoTorneo);
            cdv_mensaje = findViewById(R.id.cdv_mensaje);
            progressbar = findViewById(R.id.progressbar);
            txt_nombreTorneo = findViewById(R.id.txt_nombreTorneo);
            txt_nombreOrganizador = findViewById(R.id.txt_nombreOrganizador);
            txt_descripcionTorneo = findViewById(R.id.txt_descripcionTorneo);
            txt_lugarTorneo = findViewById(R.id.txt_lugarTorneo);
            txt_horaTorneo = findViewById(R.id.txt_horaTorneo);
            txt_fechaTorneo = findViewById(R.id.txt_fechaTorneo);
            rcv_EquiposTorneo = findViewById(R.id.rcv_EquiposTorneo);
            fab_agregarParticipantesTorneo = findViewById(R.id.fab_agregarParticipantesTorneo);

           context = getApplicationContext();


            id_torneo = getIntent().getStringExtra("id_torneo");
            id_usuario = getIntent().getStringExtra("id_usuario");
            nombre = getIntent().getStringExtra("nombre");
            descripcion = getIntent().getStringExtra("descripcion");
            lugar = getIntent().getStringExtra("lugar");
            fecha = getIntent().getStringExtra("fecha");
            hora = getIntent().getStringExtra("hora");
            organizador = getIntent().getStringExtra("organizador");


            txt_nombreTorneo.setText(nombre);
            txt_nombreOrganizador.setText(organizador);
            txt_descripcionTorneo.setText(descripcion);
            txt_lugarTorneo.setText(lugar);
            txt_horaTorneo.setText(hora);
            txt_fechaTorneo.setText(fecha);

            fab_agregarParticipantesTorneo.setOnClickListener(this);

            cdv_mensaje.setVisibility(View.INVISIBLE);

            dc = new DataConnection(this,"listarEquiposEnTorneo",id_torneo,false);
            new DetalleTorneo.GetEquiposTorneo().execute();


            if(!id_usuario.equals(usuario_id)){
                fab_agregarParticipantesTorneo.setVisibility(View.INVISIBLE);
            }
            else{ fab_agregarParticipantesTorneo.setVisibility(View.VISIBLE);
            }

        }



    public class GetEquiposTorneo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayequipo = dc.getlistaEquiposEnTorneo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressbar.setVisibility(ProgressBar.INVISIBLE);


           // Toast.makeText(getApplicationContext(),"Z "+arrayequipo.size(),Toast.LENGTH_SHORT).show();


           GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_EquiposTorneo.setLayoutManager(linearLayoutManager);

            adaptadorListadoEquipoTorneo = new AdaptadorListadoEquipoTorneo(getApplicationContext(), arrayequipo, R.layout.rcv_item_list_equipos_torneo);


            rcv_EquiposTorneo.setAdapter(adaptadorListadoEquipoTorneo);

            if (arrayequipo.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }


    public static class ActualizarEquipos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayequipoActual = dc1.getlistaEquiposEnTorneo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



            GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_EquiposTorneo.setLayoutManager(linearLayoutManager);

            adaptadorListadoEquipoTorneo = new AdaptadorListadoEquipoTorneo(context, arrayequipoActual, R.layout.rcv_item_list_equipos_torneo);

            progressbar.setVisibility(ProgressBar.INVISIBLE);

            rcv_EquiposTorneo.setAdapter(adaptadorListadoEquipoTorneo);


            if( arrayequipoActual.size()>0){
                cdv_mensaje.setVisibility(View.INVISIBLE);
            }else{
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }

   public static void ActualizarEquipoTorneo(){
       dc1 = new DataConnection(activity,"listarEquiposEnTorneo",id_torneo,false);
        new DetalleTorneo.ActualizarEquipos().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.fab_agregarParticipantesTorneo:
                //Toast.makeText(getActivity(),"foto:"+usuario_foto,Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(getApplication(), AgregarEquipos.class);
               intent.putExtra("id_torneo",id_torneo);
               startActivity(intent);
                break;
        }
    }
}
