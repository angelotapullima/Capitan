package com.tec.bufeo.capitan.Activity.Negocios.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.Negocios.Model.Negocios;
import com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios.NegociosRoomDBRepository;
import com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios.NegociosWebServiceRepository;

import java.util.List;

public class NegociosViewModel extends AndroidViewModel {

    private NegociosRoomDBRepository movimientosRoomDBRepository;
    public LiveData<List<Negocios>> mAllMiEquipos;


    NegociosWebServiceRepository movimientosWebServiceRepository;


    public LiveData<List<Negocios>> retroObservable;
    public LiveData<List<Negocios>> MiequiporetroObservable;


    public NegociosViewModel(Application application ){
        super(application);
        movimientosRoomDBRepository = new NegociosRoomDBRepository(application);
        movimientosWebServiceRepository =  new NegociosWebServiceRepository(application);

    }

    public LiveData<List<Negocios>> getAllNegocios(String id_ciudad ,String id, String token) {
        movimientosWebServiceRepository.providesWebService(id_ciudad,id,token);
        mAllMiEquipos= movimientosRoomDBRepository.getmAll();
        return mAllMiEquipos;
    }

    public LiveData<List<Negocios>> getAllMisNegocios() {
        mAllMiEquipos= movimientosRoomDBRepository.getAllMisNegocios();
        return mAllMiEquipos;
    }

    public LiveData<List<Negocios>> getAllDetalle(String id) {
        mAllMiEquipos= movimientosRoomDBRepository.getAllDetalle(id);
        return mAllMiEquipos;
    }


}
