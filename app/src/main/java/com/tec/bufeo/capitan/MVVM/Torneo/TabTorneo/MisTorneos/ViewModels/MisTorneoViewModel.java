package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository.MisTorneoRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository.MisTorneoWebServiceRepository;

import java.util.List;

public class MisTorneoViewModel extends AndroidViewModel {

    private MisTorneoRoomDBRepository misTorneoRoomDBRepository;

    private LiveData<List<Torneo>> mAllTorneo;

    MisTorneoWebServiceRepository misTorneoWebServiceRepository;

    private  LiveData<List<Torneo>> retroObservable;


    public MisTorneoViewModel(Application application){
        super(application);
        misTorneoRoomDBRepository = new MisTorneoRoomDBRepository(application);
        misTorneoWebServiceRepository = new MisTorneoWebServiceRepository(application);
        //retroObservable = retosWebServiceRepository.providesWebService();
        //listPlatosRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllRetos = retosRoomDBRepository.getAllRetos();
    }

    public LiveData<List<Torneo>> getAllRetos( String si) {
        //retroObservable = misTorneoWebServiceRepository.providesWebService(id_usuario,token);
        //listPlatosRoomDBRepository.insertPosts(retroObservable.getValue());
        mAllTorneo = misTorneoRoomDBRepository.getAllMisTorneo(si);
        return mAllTorneo;
    }

    public void insertOneReto(Torneo mRetos){
        misTorneoRoomDBRepository.inserOneReto(mRetos);
    }
   /* public LiveData<List<ListaPlatos>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
