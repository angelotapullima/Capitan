package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquiposRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquiposWebServiceRepository;

import java.util.List;

public class MisEquiposViewModel extends AndroidViewModel {

    private MisEquiposRoomDBRepository misEquiposRoomDBRepository;
    public LiveData<List<Mequipos>> mAllMiEquipos;


    MisEquiposWebServiceRepository misEquiposWebServiceRepository;


    public  LiveData<List<Mequipos>> retroObservable;
    public  LiveData<List<Mequipos>> MiequiporetroObservable;


    public MisEquiposViewModel(Application application ){
        super(application);
        misEquiposRoomDBRepository = new MisEquiposRoomDBRepository(application);
        misEquiposWebServiceRepository =  new MisEquiposWebServiceRepository(application);

    }

    public LiveData<List<Mequipos>> getAllEquipo(String mio ) {
        mAllMiEquipos = misEquiposRoomDBRepository.getAllEquipo(mio);
        return mAllMiEquipos;
    }

    public LiveData<List<Mequipos>> getAll() {
        mAllMiEquipos=misEquiposRoomDBRepository.getmAll();
        return mAllMiEquipos;
    }


}
