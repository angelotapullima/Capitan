package com.tec.bufeo.capitan.Activity.MisMovimientos.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.Movimientos;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Repository.MovimientosRoomDBRepository;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Repository.MovimientosWebServiceRepository;

import java.util.List;

public class MovimientosViewModel extends AndroidViewModel {

    private MovimientosRoomDBRepository movimientosRoomDBRepository;
    public LiveData<List<Movimientos>> mAllMiEquipos;


    MovimientosWebServiceRepository movimientosWebServiceRepository;


    public LiveData<List<Movimientos>> retroObservable;
    public LiveData<List<Movimientos>> MiequiporetroObservable;


    public MovimientosViewModel(Application application ){
        super(application);
        movimientosRoomDBRepository = new MovimientosRoomDBRepository(application);
        movimientosWebServiceRepository =  new MovimientosWebServiceRepository(application);

    }

    public LiveData<List<Movimientos>> getAll(String id,String token) {
        movimientosWebServiceRepository.providesWebService(id,token);
        mAllMiEquipos= movimientosRoomDBRepository.getmAll();
        return mAllMiEquipos;
    }


}
