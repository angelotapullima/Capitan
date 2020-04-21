package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models.Chanchas;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Repository.ChanchasRoomDBRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Repository.ChanchasWebServiceRepository;

import java.util.List;

public class ChanchasViewModel extends AndroidViewModel {

    private ChanchasRoomDBRepository movimientosRoomDBRepository;
    public LiveData<List<Chanchas>> mAllChanchas;


    ChanchasWebServiceRepository chanchasWebServiceRepository;


    public LiveData<List<Chanchas>> retroObservable;
    public LiveData<List<Chanchas>> MiequiporetroObservable;


    public ChanchasViewModel(Application application ){
        super(application);
        movimientosRoomDBRepository = new ChanchasRoomDBRepository(application);
        chanchasWebServiceRepository =  new ChanchasWebServiceRepository(application);

    }

    public LiveData<List<Chanchas>> getChanchas(String id_equipo, String token) {
        chanchasWebServiceRepository.providesWebService(id_equipo,token);
        mAllChanchas= movimientosRoomDBRepository.getChanchas(id_equipo);
        return mAllChanchas;
    }


    public LiveData<List<Chanchas>> loadIDS(List<String> menuModel) {
        mAllChanchas= movimientosRoomDBRepository.loadIDS(menuModel);
        return mAllChanchas;
    }

}
