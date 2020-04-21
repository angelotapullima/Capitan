package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Models.Torneo;

import java.util.List;

@Dao
public interface TorneosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Torneo resultModel);

    @Query("SELECT * from Torneo WHERE mi_torneo =  :si  ")
    LiveData<List<Torneo>> getAllMisTorneos(String si);

    @Query("SELECT * from Torneo WHERE mi_torneo =  :no  ")
    LiveData<List<Torneo>> getAllOtrosTorneos(String no);

    @Query("SELECT * from Torneo WHERE id_torneo =  :id  ")
    LiveData<List<Torneo>> getIdTorneo(String id);

    @Query("DELETE FROM Torneo ")
    void deleteAll();


    @Query("SELECT * FROM torneo WHERE torneo_nombre LIKE '%' || :query || '%'  and mi_torneo = 'no'" )
    LiveData<List<Torneo>> searchTorneo(String query);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTorneo(List<Torneo> resultModel);

}
