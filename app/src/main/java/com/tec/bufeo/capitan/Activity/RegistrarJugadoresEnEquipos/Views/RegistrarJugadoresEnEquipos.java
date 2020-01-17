package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.Jugadores;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.JugadoresRoomDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel.JugadoresViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class RegistrarJugadoresEnEquipos extends AppCompatActivity {

    JugadoresViewModel jugadoresViewModel;
    Preferences preferences;
    RecyclerView rcv_elegidos,rcv_jugadores;
    EditText txt_busqueda;
    AdapterJugadores adapterJugadores;
    AdapterElegidos adapterElegidos;
    String id_equipo,nombre_equipo;
    TextView name_equipo;
    JugadoresRoomDBRepository jugadoresRoomDBRepository;
    Application application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_jugadores_en_equipos);

        jugadoresViewModel = ViewModelProviders.of(this).get(JugadoresViewModel.class);
        jugadoresRoomDBRepository = new JugadoresRoomDBRepository(application);
        preferences = new Preferences(this);
        /*id_equipo= getIntent().getExtras().getString("id_equipo");
        nombre_equipo= getIntent().getExtras().getString("nombre");*/

        id_equipo="11";
        nombre_equipo="Vamonos de Putas";
        initViews();
        setAdapter();
        cargarvista();

    }

    private void initViews() {

        rcv_elegidos =  findViewById(R.id.rcv_elegidos);
        rcv_jugadores =  findViewById(R.id.rcv_jugadores);
        txt_busqueda =  findViewById(R.id.txt_busqueda);
        name_equipo =  findViewById(R.id.name_equipo);
        name_equipo.setText(nombre_equipo);
    }
    public void cargarvista() {
        jugadoresViewModel.getAllJugadores(id_equipo,preferences.getToken(),"vacio").observe(this, new Observer<List<Jugadores>>() {
            @Override
            public void onChanged(List<Jugadores> jugadores) {
                adapterJugadores.setWords(jugadores);
            }
        });

        jugadoresViewModel.getAllJugadores(id_equipo,preferences.getToken(),"seleccionado").observe(this, new Observer<List<Jugadores>>() {
            @Override
            public void onChanged(List<Jugadores> jugadores) {
                adapterElegidos.setWords(jugadores);
            }
        });

    }
    private void setAdapter(){

        adapterJugadores= new AdapterJugadores(this, new AdapterJugadores.OnItemClickListener() {
            @Override
            public void onItemClick(Jugadores mequipos, String tipo, int position) {

                if (tipo.equals("contenedor_jugadores")){
                    jugadoresRoomDBRepository.EstadoSeleccionado(mequipos.getJugador_id());
                }
            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_jugadores.setLayoutManager(linearLayoutManager);
        rcv_jugadores.setAdapter(adapterJugadores);


        adapterElegidos = new AdapterElegidos(this, new AdapterElegidos.OnItemClickListener() {
            @Override
            public void onItemClick(Jugadores jugadores, String tipo, int position) {
                if (tipo.equals("contenedor_elegidos")){
                    jugadoresRoomDBRepository.EstadoVacio(jugadores.getJugador_id());
                }
            }
        });

        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(this, 1);
        linearLayoutManager2.setOrientation(linearLayoutManager.HORIZONTAL);
        rcv_elegidos.setLayoutManager(linearLayoutManager2);
        rcv_elegidos.setAdapter(adapterElegidos);
    }


}
