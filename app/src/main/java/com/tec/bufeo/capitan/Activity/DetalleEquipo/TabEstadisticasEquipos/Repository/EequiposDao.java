package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Models.EstadisticasDeEquipos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface EequiposDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EstadisticasDeEquipos resultModel);

    @Query("SELECT * from estadisticas_equipos ")
    LiveData<List<EstadisticasDeEquipos>> getAllTorneoEquipos();

    @Query("DELETE FROM estadisticas_equipos")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTorneosEquipos(List<EstadisticasDeEquipos> resultModel);
}
