package com.tec.bufeo.capitan.Activity.BusquedaEquipos.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Models.BusquedaEquipos;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository.BusquedaEquiposRoomDBRepository;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository.BusquedaEquiposWebServiceRepository;

import java.util.List;

public class BusquedaEquiposViewModel extends AndroidViewModel {

    private BusquedaEquiposRoomDBRepository misEquiposRoomDBRepository;
    public LiveData<List<BusquedaEquipos>> mAllMiEquipos;


    BusquedaEquiposWebServiceRepository misEquiposWebServiceRepository;


    public  LiveData<List<BusquedaEquipos>> retroObservable;
    public  LiveData<List<BusquedaEquipos>> MiequiporetroObservable;


    public BusquedaEquiposViewModel(Application application ){
        super(application);
        misEquiposRoomDBRepository = new BusquedaEquiposRoomDBRepository(application);
        misEquiposWebServiceRepository =  new BusquedaEquiposWebServiceRepository(application);

    }

    public LiveData<List<BusquedaEquipos>> getAllEquipo(String id_usuario,String token ) {

        misEquiposWebServiceRepository.providesWebService("busqueda",id_usuario,"",token);
        mAllMiEquipos = misEquiposRoomDBRepository.getAllEquipo();
        return mAllMiEquipos;
    }

    public LiveData<List<BusquedaEquipos>> getAll() {
        mAllMiEquipos=misEquiposRoomDBRepository.getmAll();
        return mAllMiEquipos;
    }


}
