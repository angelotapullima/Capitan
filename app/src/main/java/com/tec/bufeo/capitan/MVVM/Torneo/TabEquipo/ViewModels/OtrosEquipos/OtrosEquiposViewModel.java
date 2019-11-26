package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.OtrosEquipos;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.OtrosEquipos.OtrosEquiposRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.OtrosEquipos.OtrosEquiposWebServiceRepository;

import java.util.List;

public class OtrosEquiposViewModel extends AndroidViewModel {

    private OtrosEquiposRoomDBRepository otrosEquiposRoomDBRepository;
    public LiveData<List<Mequipos>> mAllMiEquipos;


    OtrosEquiposWebServiceRepository otrosEquiposWebServiceRepository;


    public  LiveData<List<Mequipos>> retroObservable;
    public  LiveData<List<Mequipos>> MiequiporetroObservable;


    public OtrosEquiposViewModel(Application application){
        super(application);
        otrosEquiposRoomDBRepository = new OtrosEquiposRoomDBRepository(application);
        otrosEquiposWebServiceRepository =  new OtrosEquiposWebServiceRepository(application);

    }

    public LiveData<List<Mequipos>> getAllOtrosEquipos( String no ) {
        //retroObservable = otrosEquiposWebServiceRepository.providesWebService(id_usuario);
        mAllMiEquipos = otrosEquiposRoomDBRepository.getAllOtrosEquipos(no);
        return mAllMiEquipos;
    }



   /* public LiveData<List<ListaPlatos>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
