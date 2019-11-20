package com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Models.Estadisticas;

import java.util.List;

@Dao
public interface EstadisticasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Estadisticas resultModel);

    /*@Query("SELECT * from equipos WHERE mi_equipo = :mi_equipo")
    LiveData<List<Mequipos>> getAllEquipos(String mi_equipo);*/

    @Query("SELECT * from estadisticas  ")
    LiveData<List<Estadisticas>> getAllEstadisticas();


    @Query("DELETE FROM estadisticas")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEstadisticas(List<Estadisticas> resultModel);

}
