package com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Models.Goleadores;

import java.util.List;


@Dao
public interface GoleadoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Goleadores goleadores);

    @Query("SELECT * from goleadores where id_torneo =:id_torneo")
    LiveData<List<Goleadores>> getAllGoleadores(String id_torneo);


    @Query("DELETE FROM goleadores")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGoleadores(List<Goleadores> goleadores);
}
