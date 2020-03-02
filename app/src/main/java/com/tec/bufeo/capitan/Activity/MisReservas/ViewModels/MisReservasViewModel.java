package com.tec.bufeo.capitan.Activity.MisReservas.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.Activity.MisReservas.Repository.MisReservasRoomDBRepository;
import com.tec.bufeo.capitan.Activity.MisReservas.Repository.MisReservasWebServiceRepository;

import java.util.List;

public class MisReservasViewModel extends AndroidViewModel {

    private MisReservasRoomDBRepository movimientosRoomDBRepository;
    public LiveData<List<MisReservas>> mAllMiEquipos;


    MisReservasWebServiceRepository movimientosWebServiceRepository;


    public LiveData<List<MisReservas>> retroObservable;
    public LiveData<List<MisReservas>> MiequiporetroObservable;


    public MisReservasViewModel(Application application ){
        super(application);
        movimientosRoomDBRepository = new MisReservasRoomDBRepository(application);
        movimientosWebServiceRepository =  new MisReservasWebServiceRepository(application);

    }

    public LiveData<List<MisReservas>> getAll(String id, String token) {
        movimientosWebServiceRepository.providesWebService(id,token);
        mAllMiEquipos= movimientosRoomDBRepository.getmAll();
        return mAllMiEquipos;
    }


}
