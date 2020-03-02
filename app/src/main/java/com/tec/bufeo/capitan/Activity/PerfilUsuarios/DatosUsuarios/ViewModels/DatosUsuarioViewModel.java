package com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Repository.DatosUsuarioRoomDBRepository;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Repository.DatosUsuarioWebServiceRepository;

import java.util.List;

public class DatosUsuarioViewModel extends AndroidViewModel {

    private DatosUsuarioRoomDBRepository datosUsuarioRoomDBRepository;
    public LiveData<List<DatosUsuario>> mAllDatosUsuario;


    DatosUsuarioWebServiceRepository datosUsuarioWebServiceRepository;


    public LiveData<List<DatosUsuario>> retroObservable;
    public LiveData<List<DatosUsuario>> MiequiporetroObservable;


    public DatosUsuarioViewModel(Application application ){
        super(application);
        datosUsuarioRoomDBRepository = new DatosUsuarioRoomDBRepository(application);
        datosUsuarioWebServiceRepository =  new DatosUsuarioWebServiceRepository(application);

    }

    public LiveData<List<DatosUsuario>> getAll(String id_user, String token) {
        datosUsuarioWebServiceRepository.providesWebService(id_user,token);
        mAllDatosUsuario= datosUsuarioRoomDBRepository.getAllDatosUsuario(id_user);
        return mAllDatosUsuario;
    }


}
