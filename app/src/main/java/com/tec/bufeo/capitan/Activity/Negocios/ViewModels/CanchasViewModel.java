package com.tec.bufeo.capitan.Activity.Negocios.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.Negocios.Model.Canchas;
import com.tec.bufeo.capitan.Activity.Negocios.Repository.Canchas.CanchasRoomDBRepository;
import com.tec.bufeo.capitan.Activity.Negocios.Repository.Canchas.CanchasWebServiceRepository;

import java.util.List;

public class CanchasViewModel extends AndroidViewModel {

    private CanchasRoomDBRepository movimientosRoomDBRepository;
    public LiveData<List<Canchas>> mAllMiEquipos;


    CanchasWebServiceRepository movimientosWebServiceRepository;


    public LiveData<List<Canchas>> retroObservable;
    public LiveData<List<Canchas>> MiequiporetroObservable;


    public CanchasViewModel(Application application ){
        super(application);
        movimientosRoomDBRepository = new CanchasRoomDBRepository(application);
        movimientosWebServiceRepository =  new CanchasWebServiceRepository(application);

    }

    public LiveData<List<Canchas>> getCanchas(String id, String token) {
        movimientosWebServiceRepository.providesWebService(id,token);
        mAllMiEquipos= movimientosRoomDBRepository.getCancha(id);
        return mAllMiEquipos;
    }




}
