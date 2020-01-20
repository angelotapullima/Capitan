package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.ViewModels;

import android.app.Application;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Models.TorneosDeEquipos;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository.TequiposRoomDbRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository.TequiposWebServiceRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TequiposViewModel extends AndroidViewModel {
    private TequiposRoomDbRepository tequiposRoomDbRepository;

    private LiveData<List<TorneosDeEquipos>> mAllTorneo;

    TequiposWebServiceRepository tequiposWebServiceRepository;

    private LiveData<List<TorneosDeEquipos>> retroObservable;


    public TequiposViewModel(Application application){
        super(application);
        tequiposRoomDbRepository = new TequiposRoomDbRepository(application);
        tequiposWebServiceRepository = new TequiposWebServiceRepository(application);
        //retroObservable = retosWebServiceRepository.providesWebService();
        //listPlatosRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllRetos = retosRoomDBRepository.getAllRetos();
    }

    public LiveData<List<TorneosDeEquipos>> getAllOtrosTorneos( String id_equipo,String token ) {
        retroObservable = tequiposWebServiceRepository.providesWebService(id_equipo,token);
        //listPlatosRoomDBRepository.insertPosts(retroObservable.getValue());
        mAllTorneo = tequiposRoomDbRepository.getAllTorneoEquipos();
        return mAllTorneo;
    }


}
