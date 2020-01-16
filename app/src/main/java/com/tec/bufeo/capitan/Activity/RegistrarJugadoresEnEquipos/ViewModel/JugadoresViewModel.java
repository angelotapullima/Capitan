package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel;

import android.app.Application;

import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.Jugadores;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.JugadoresRoomDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.JugadoresWebServiceRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class JugadoresViewModel extends AndroidViewModel {

    private JugadoresRoomDBRepository jugadoresRoomDBRepository;
    public LiveData<List<Jugadores>> mAllJugadores;

    JugadoresWebServiceRepository jugadoresWebServiceRepository;

    public  LiveData<List<Jugadores>> retroObservable;
    public  LiveData<List<Jugadores>> JugadoresRetroObservable;


    public JugadoresViewModel( Application application) {
        super(application);
        jugadoresRoomDBRepository = new JugadoresRoomDBRepository(application);
        jugadoresWebServiceRepository =  new JugadoresWebServiceRepository(application);
    }

    public LiveData<List<Jugadores>> getAllJugadores(String id_equipo,String token) {
        retroObservable = jugadoresWebServiceRepository.providesWebService(id_equipo,token);
        mAllJugadores=jugadoresRoomDBRepository.getAllJugadores();
        return mAllJugadores;
    }
}
