package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
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
