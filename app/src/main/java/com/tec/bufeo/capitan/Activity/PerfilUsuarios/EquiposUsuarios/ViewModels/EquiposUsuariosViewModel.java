package com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Models.EquiposUsuarios;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Repository.DatosUsuarioRoomDBRepository;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Repository.DatosUsuarioWebServiceRepository;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Repository.EquiposUsuariosRoomDBRepository;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Repository.EquiposUsuariosWebServiceRepository;

import java.util.List;

public class EquiposUsuariosViewModel extends AndroidViewModel {

    private EquiposUsuariosRoomDBRepository datosUsuarioRoomDBRepository;
    public LiveData<List<EquiposUsuarios>> mAllDatosUsuario;


    EquiposUsuariosWebServiceRepository datosUsuarioWebServiceRepository;


    public LiveData<List<EquiposUsuarios>> retroObservable;
    public LiveData<List<EquiposUsuarios>> MiequiporetroObservable;


    public EquiposUsuariosViewModel(Application application ){
        super(application);
        datosUsuarioRoomDBRepository = new EquiposUsuariosRoomDBRepository(application);
        datosUsuarioWebServiceRepository =  new EquiposUsuariosWebServiceRepository(application);

    }

    public LiveData<List<EquiposUsuarios>> getAll(String id_user, String token) {
        datosUsuarioWebServiceRepository.providesWebService(id_user,token);
        mAllDatosUsuario= datosUsuarioRoomDBRepository.getmAllEquiposUsuarios(id_user);
        return mAllDatosUsuario;
    }


}
