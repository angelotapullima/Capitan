package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;

import java.util.List;

@Dao
public interface RetosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Retos resultModel);

    @Query("SELECT * from Retos WHERE retos_respuesta = :respuesta_reto")
    LiveData<List<Retos>> getAllRetos(String respuesta_reto);

    @Query("SELECT * from Retos WHERE retos_id = :id")
    LiveData<List<Retos>> getAllRetoID(String id);

    @Query("SELECT * from Retos WHERE retos_id = :id_reto")
    LiveData<List<Retos>> getRetosResutado(String id_reto);

    @Query("DELETE FROM Retos")
    void deleteAll();

    @Query("SELECT * from Retos  ")
    LiveData<List<Retos>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRetos(List<Retos> resultModel);

}
