package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Models.Torneo;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Repository.TorneosRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Repository.TorneosWebServiceRepository;

import java.util.List;

public class MisTorneoViewModel extends AndroidViewModel {

    private TorneosRoomDBRepository torneosRoomDBRepository;

    private LiveData<List<Torneo>> mAllTorneo;

    TorneosWebServiceRepository torneosWebServiceRepository;

    private  LiveData<List<Torneo>> retroObservable;


    public MisTorneoViewModel(Application application){
        super(application);
        torneosRoomDBRepository = new TorneosRoomDBRepository(application);
        torneosWebServiceRepository = new TorneosWebServiceRepository(application);
        //retroObservable = retosWebServiceRepository.providesWebService();
        //listPlatosRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllRetos = retosRoomDBRepository.getAllRetos();
    }

    public LiveData<List<Torneo>> getAllOtrosTorneos( String no) {

        mAllTorneo = torneosRoomDBRepository.getAllOtrosTorneo(no);
        return mAllTorneo;
    }
    public LiveData<List<Torneo>> getAllMisTorneo( String si) {
        //retroObservable = misTorneoWebServiceRepository.providesWebService(id_usuario,token);
        //listPlatosRoomDBRepository.insertPosts(retroObservable.getValue());
        mAllTorneo = torneosRoomDBRepository.getAllMisTorneo(si);
        return mAllTorneo;
    }


    public LiveData<List<Torneo>> searchTorneo(String query) {

        mAllTorneo = torneosRoomDBRepository.searchTorneo(query);
        return mAllTorneo;
    }


}
