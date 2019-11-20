package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RetosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Retos resultModel);

    @Query("SELECT * from Retos WHERE retos_respuesta = :respuesta_reto")
    LiveData<List<Retos>> getAllRetos(String respuesta_reto);

    @Query("SELECT * from Retos WHERE retos_id = :id_reto")
    LiveData<List<Retos>> getRetosResutado(String id_reto);

    @Query("DELETE FROM Retos")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRetos(List<Retos> resultModel);

}
