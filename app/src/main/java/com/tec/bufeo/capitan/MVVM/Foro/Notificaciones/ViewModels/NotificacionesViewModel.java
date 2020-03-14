package com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Models.Notificaciones;
import com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Repository.NotificacionesRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Repository.NotificacionesWebServiceRepository;

import java.util.List;

public class NotificacionesViewModel extends AndroidViewModel {

    private NotificacionesRoomDBRepository notificacionesRoomDBRepository;
    public LiveData<List<Notificaciones>> mAllMiEquipos;


    NotificacionesWebServiceRepository notificacionesWebServiceRepository;


    public LiveData<List<Notificaciones>> retroObservable;
    public LiveData<List<Notificaciones>> MiequiporetroObservable;


    public NotificacionesViewModel(Application application ){
        super(application);
        notificacionesRoomDBRepository = new NotificacionesRoomDBRepository(application);
        notificacionesWebServiceRepository =  new NotificacionesWebServiceRepository(application);

    }

    public LiveData<List<Notificaciones>> getAllNotificaciones( String id,String token) {
        notificacionesWebServiceRepository.providesWebService(id,token);
        mAllMiEquipos= notificacionesRoomDBRepository.getmAllNotificaciones();
        return mAllMiEquipos;
    }


}
