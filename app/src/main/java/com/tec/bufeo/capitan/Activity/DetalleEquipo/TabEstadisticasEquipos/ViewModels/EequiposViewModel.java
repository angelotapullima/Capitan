package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.ViewModels;

import android.app.Application;


import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Models.EstadisticasDeEquipos;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository.EequiposRoomDbRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository.EequiposWebServiceRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EequiposViewModel extends AndroidViewModel {
    private EequiposRoomDbRepository tequiposRoomDbRepository;

    private LiveData<List<EstadisticasDeEquipos>> mAllTorneo;

    EequiposWebServiceRepository tequiposWebServiceRepository;

    private LiveData<List<EstadisticasDeEquipos>> retroObservable;


    public EequiposViewModel(Application application){
        super(application);
        tequiposRoomDbRepository = new EequiposRoomDbRepository(application);
        tequiposWebServiceRepository = new EequiposWebServiceRepository(application);
        //retroObservable = retosWebServiceRepository.providesWebService();
        //listPlatosRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllRetos = retosRoomDBRepository.getAllRetos();
    }

    public LiveData<List<EstadisticasDeEquipos>> getAllOtrosTorneos( String id_equipo,String token ) {
        retroObservable = tequiposWebServiceRepository.providesWebService(id_equipo,token);
        //listPlatosRoomDBRepository.insertPosts(retroObservable.getValue());
        mAllTorneo = tequiposRoomDbRepository.getAllTorneoEquipos();
        return mAllTorneo;
    }


}
