package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Models.TorneosDeEquipos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TequiposDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TorneosDeEquipos resultModel);

    @Query("SELECT * from torneos_equipos ")
    LiveData<List<TorneosDeEquipos>> getAllTorneoEquipos();

    @Query("DELETE FROM torneos_equipos")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTorneosEquipos(List<TorneosDeEquipos> resultModel);
}
