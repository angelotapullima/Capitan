package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;

import java.util.List;

@Dao
public interface MisTorneoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Torneo resultModel);

    @Query("SELECT * from Torneo WHERE mi_torneo =  :si  ")
    LiveData<List<Torneo>> getAllMisTorneos(String si);

    @Query("DELETE FROM Torneo")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRetos(List<Torneo> resultModel);

}
