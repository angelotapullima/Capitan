package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.ViewModels;

import android.app.Application;

import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Models.RegistroEquiposTorneo;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository.RegistroEquiposTorneoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository.RegistroEquiposTorneoWebServiceRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class RegistroEquiposTorneoViewModel extends AndroidViewModel {

    private RegistroEquiposTorneoRoomDBRepository registroEquiposTorneoRoomDBRepository;
    public LiveData<List<RegistroEquiposTorneo>> mAllMiEquipos;


    RegistroEquiposTorneoWebServiceRepository registroEquiposTorneoWebServiceRepository;


    public  LiveData<List<RegistroEquiposTorneo>> retroObservable;
    public  LiveData<List<RegistroEquiposTorneo>> MiequiporetroObservable;


    public RegistroEquiposTorneoViewModel(Application application){
        super(application);
        registroEquiposTorneoRoomDBRepository = new RegistroEquiposTorneoRoomDBRepository(application);
        registroEquiposTorneoWebServiceRepository =  new RegistroEquiposTorneoWebServiceRepository(application);

    }

    public LiveData<List<RegistroEquiposTorneo>> getAllEquiposTorneo(  ) {
        //retroObservable = equiposTorneoWebServiceRepository.providesWebService(id_usuario);
        mAllMiEquipos = registroEquiposTorneoRoomDBRepository.getAllEquiposTorneo();
        return mAllMiEquipos;
    }


    public LiveData<List<RegistroEquiposTorneo>> getLocal(  ) {
        //retroObservable = equiposTorneoWebServiceRepository.providesWebService(id_usuario);
        mAllMiEquipos = registroEquiposTorneoRoomDBRepository.getLocal();
        return mAllMiEquipos;
    }

    public LiveData<List<RegistroEquiposTorneo>> getVisitante(  ) {
        //retroObservable = equiposTorneoWebServiceRepository.providesWebService(id_usuario);
        mAllMiEquipos = registroEquiposTorneoRoomDBRepository.getVisitante();
        return mAllMiEquipos;
    }



   /* public LiveData<List<ListaPlatos>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
