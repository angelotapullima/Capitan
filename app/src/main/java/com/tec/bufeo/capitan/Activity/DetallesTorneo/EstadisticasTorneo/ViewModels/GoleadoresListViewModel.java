package com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Models.Goleadores;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Repository.GoleadoresRoomDBRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Repository.GoleadoresWebServiceRepository;

import java.util.List;

public class GoleadoresListViewModel extends AndroidViewModel {

    private GoleadoresRoomDBRepository goleadoresRoomDBRepository;
    private LiveData<List<Goleadores>> mAllGoleadores;
    GoleadoresWebServiceRepository goleadoresWebServiceRepository;
    private LiveData<List<Goleadores>> retroObservable;


    public GoleadoresListViewModel(Application application){
        super(application);
        goleadoresRoomDBRepository = new GoleadoresRoomDBRepository(application);
        goleadoresWebServiceRepository = new GoleadoresWebServiceRepository(application);
        //retroObservable = feedWebServiceRepository.providesWebService();
        //feedTorneoRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllPosts = feedTorneoRoomDBRepository.getAllPosts();

    }

    public LiveData<List<Goleadores>> getAllGoleadores(String id_torneo,String token) {
        retroObservable = goleadoresWebServiceRepository.providesWebService(id_torneo,token);
        mAllGoleadores = goleadoresRoomDBRepository.getAllGoleadores(id_torneo);
        return mAllGoleadores;
    }




    public void insert(Goleadores goleadores){
        goleadoresRoomDBRepository.insert(goleadores);
    }


    public void deleteAllFeed() {
        goleadoresRoomDBRepository.deleteAllGoleadores();
    }


   /*public LiveData<List<PublicacionesTorneo>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
