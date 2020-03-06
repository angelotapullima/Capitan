package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository.BusquedaEquiposRoomDBRepository;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository.BusquedaEquiposWebServiceRepository;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Model.EquiposGrupo;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Repository.EquiposGrupoRoomDBRepository;

import java.util.List;

public class EquiposGrupoViewModel extends AndroidViewModel {

    private EquiposGrupoRoomDBRepository equiposGrupoRoomDBRepository;
    public LiveData<List<EquiposGrupo>> mAllMiEquipos;





    public  LiveData<List<EquiposGrupo>> retroObservable;
    public  LiveData<List<EquiposGrupo>> MiequiporetroObservable;


    public EquiposGrupoViewModel(Application application ){
        super(application);
        equiposGrupoRoomDBRepository = new EquiposGrupoRoomDBRepository(application);
    }


    /*public LiveData<List<BusquedaEquipos>> getAllEquipo(String id_usuario,String token ) {

        misEquiposWebServiceRepository.providesWebService("busqueda",id_usuario,"",token);
        mAllMiEquipos = misEquiposRoomDBRepository.getAllEquipo();
        return mAllMiEquipos;
    }*/



    public LiveData<List<EquiposGrupo>> getAllEquipoGrupo() {
        mAllMiEquipos=equiposGrupoRoomDBRepository.getAllEquipoGrupo();
        return mAllMiEquipos;
    }


}
