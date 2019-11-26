package com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Models.EquiposTorneo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Repository.EquiposTorneoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Repository.EquiposTorneoWebServiceRepository;

import java.util.List;

public class EquiposTorneoViewModel extends AndroidViewModel {

    private EquiposTorneoRoomDBRepository equiposTorneoRoomDBRepository;
    public LiveData<List<EquiposTorneo>> mAllMiEquipos;


    EquiposTorneoWebServiceRepository equiposTorneoWebServiceRepository;


    public  LiveData<List<EquiposTorneo>> retroObservable;
    public  LiveData<List<EquiposTorneo>> MiequiporetroObservable;


    public EquiposTorneoViewModel(Application application){
        super(application);
        equiposTorneoRoomDBRepository = new EquiposTorneoRoomDBRepository(application);
        equiposTorneoWebServiceRepository =  new EquiposTorneoWebServiceRepository(application);

    }

    public LiveData<List<EquiposTorneo>> getAllEquiposTorneo(String equipo_torneo ) {
        //retroObservable = equiposTorneoWebServiceRepository.providesWebService(id_usuario);
        mAllMiEquipos = equiposTorneoRoomDBRepository.getAllEquiposTorneo(equipo_torneo);
        return mAllMiEquipos;
    }



   /* public LiveData<List<ListaPlatos>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
