package com.tec.bufeo.capitan.Activity.DetalleCanchas.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.DetalleCanchas.Models.ReservasCancha;
import com.tec.bufeo.capitan.Activity.DetalleCanchas.Repository.ReservasCanchaRoomDBRepository;
import com.tec.bufeo.capitan.Activity.DetalleCanchas.Repository.ReservasCanchaWebServiceRepository;

import java.util.List;

public class ReservasCanchaListViewModel extends AndroidViewModel {

    private ReservasCanchaRoomDBRepository goleadoresRoomDBRepository;
    private LiveData<List<ReservasCancha>> mAllGoleadores;
    ReservasCanchaWebServiceRepository goleadoresWebServiceRepository;
    private LiveData<List<ReservasCancha>> retroObservable;


    public ReservasCanchaListViewModel(Application application){
        super(application);
        goleadoresRoomDBRepository = new ReservasCanchaRoomDBRepository(application);
        goleadoresWebServiceRepository = new ReservasCanchaWebServiceRepository(application);
        //retroObservable = feedWebServiceRepository.providesWebService();
        //feedTorneoRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllPosts = feedTorneoRoomDBRepository.getAllPosts();

    }

    public LiveData<List<ReservasCancha>> getReservasDia(String fecha,String cancha_id,String token) {
        retroObservable = goleadoresWebServiceRepository.providesWebService(fecha,cancha_id,token);
        mAllGoleadores = goleadoresRoomDBRepository.getReservasDia(fecha,cancha_id);
        return mAllGoleadores;
    }




    public void insert(ReservasCancha goleadores){
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
