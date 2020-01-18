package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel.Seleccionados;

import android.app.Application;

import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.JugadoresSeleccionados;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.seleccionados.SeleccionadosDBRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SeleccionadosViewModel extends AndroidViewModel {
    private SeleccionadosDBRepository seleccionadosDBRepository;
    public LiveData<List<JugadoresSeleccionados>> mAllJugadores;


    public  LiveData<List<JugadoresSeleccionados>> retroObservable;
    public  LiveData<List<JugadoresSeleccionados>> JugadoresRetroObservable;



    public SeleccionadosViewModel(@NonNull Application application) {
        super(application);
        seleccionadosDBRepository = new SeleccionadosDBRepository(application);
    }

    public LiveData<List<JugadoresSeleccionados>> getAll() {
        mAllJugadores=seleccionadosDBRepository.getAllSeleccionados();
        return mAllJugadores;
    }
}
