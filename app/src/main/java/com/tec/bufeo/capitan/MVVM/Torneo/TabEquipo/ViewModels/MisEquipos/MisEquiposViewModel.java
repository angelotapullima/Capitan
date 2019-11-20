package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquipos;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquipos.MisEquiposRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquipos.MisEquiposWebServiceRepository;

import java.util.List;

public class MisEquiposViewModel extends AndroidViewModel {

    private MisEquiposRoomDBRepository misEquiposRoomDBRepository;
    public LiveData<List<Mequipos>> mAllMiEquipos;


    MisEquiposWebServiceRepository misEquiposWebServiceRepository;


    public  LiveData<List<Mequipos>> retroObservable;
    public  LiveData<List<Mequipos>> MiequiporetroObservable;


    public MisEquiposViewModel(Application application ){
        super(application);
        //retroObservable = misEquiposWebServiceRepository.providesWebService(id_usuario);
        misEquiposRoomDBRepository = new MisEquiposRoomDBRepository(application);
        misEquiposWebServiceRepository =  new MisEquiposWebServiceRepository(application);

    }

    public LiveData<List<Mequipos>> getAllMiEquipo(String si ) {
        //retroObservable = misEquiposWebServiceRepository.providesWebService(id_usuario);
        mAllMiEquipos = misEquiposRoomDBRepository.getAllMiEquipo(si);
        return mAllMiEquipos;
    }



   /* public LiveData<List<ListaPlatos>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
