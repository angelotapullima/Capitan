package com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Models.Estadisticas;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository.EstadisticasRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository.EstadisticasWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquipos.MisEquiposRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquipos.MisEquiposWebServiceRepository;

import java.util.List;

public class EstadisticasViewModel extends AndroidViewModel {

    private EstadisticasRoomDBRepository misEquiposRoomDBRepository;
    public LiveData<List<Estadisticas>> mAllMiEquipos;


    EstadisticasWebServiceRepository misEquiposWebServiceRepository;


    public  LiveData<List<Estadisticas>> retroObservable;
    public  LiveData<List<Estadisticas>> MiequiporetroObservable;


    public EstadisticasViewModel(Application application){
        super(application);
        misEquiposRoomDBRepository = new EstadisticasRoomDBRepository(application);
        misEquiposWebServiceRepository =  new EstadisticasWebServiceRepository(application);

    }

    public LiveData<List<Estadisticas>> getAllEstadisticas(String id_usuario ) {
        retroObservable = misEquiposWebServiceRepository.providesWebService(id_usuario);
        mAllMiEquipos = misEquiposRoomDBRepository.getAllEstadisticas();
        return mAllMiEquipos;
    }



   /* public LiveData<List<ListaPlatos>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
