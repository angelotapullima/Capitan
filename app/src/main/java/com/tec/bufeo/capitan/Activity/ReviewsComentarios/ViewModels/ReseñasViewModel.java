package com.tec.bufeo.capitan.Activity.ReviewsComentarios.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.ReviewsComentarios.Models.Reseñas;
import com.tec.bufeo.capitan.Activity.ReviewsComentarios.Repository.ReseñasRoomDBRepository;
import com.tec.bufeo.capitan.Activity.ReviewsComentarios.Repository.ReseñasWebServiceRepository;

import java.util.List;

public class ReseñasViewModel extends AndroidViewModel {

    private ReseñasRoomDBRepository reseñasRoomDBRepository;
    public LiveData<List<Reseñas>> mAllReseñas;


    ReseñasWebServiceRepository reseñasWebServiceRepository;


    public LiveData<List<Reseñas>> retroObservable;
    public LiveData<List<Reseñas>> MiequiporetroObservable;


    public ReseñasViewModel(Application application ){
        super(application);
        reseñasRoomDBRepository = new ReseñasRoomDBRepository(application);
        reseñasWebServiceRepository =  new ReseñasWebServiceRepository(application);

    }

    public LiveData<List<Reseñas>> getValor(String valor) {
        mAllReseñas= reseñasRoomDBRepository.getValor(valor);
        return mAllReseñas;
    }

    public LiveData<List<Reseñas>> getAll(String id_empresa, String token) {
        reseñasWebServiceRepository.providesWebService(id_empresa,token);
        mAllReseñas= reseñasRoomDBRepository.getAll();
        return mAllReseñas;
    }


}
