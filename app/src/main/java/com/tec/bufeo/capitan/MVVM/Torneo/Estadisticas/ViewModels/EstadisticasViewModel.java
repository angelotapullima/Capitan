package com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Models.Estadisticas;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository.EstadisticasRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository.EstadisticasWebServiceRepository;

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

    public LiveData<List<Estadisticas>> getAllEstadisticas(String id_usuario,String token ) {
        retroObservable = misEquiposWebServiceRepository.providesWebService(id_usuario,token);
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
