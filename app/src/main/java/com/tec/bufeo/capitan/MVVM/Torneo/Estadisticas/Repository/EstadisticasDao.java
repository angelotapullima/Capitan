package com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Models.Estadisticas;

import java.util.List;

@Dao
public interface EstadisticasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Estadisticas resultModel);

    /*@Query("SELECT * from equipos WHERE mi_equipo = :mi_equipo")
    LiveData<List<EquiposTorneo>> getAllEquipos(String mi_equipo);*/

    @Query("SELECT * from estadisticas  ")
    LiveData<List<Estadisticas>> getAllEstadisticas();


    @Query("DELETE FROM estadisticas")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEstadisticas(List<Estadisticas> resultModel);

}
