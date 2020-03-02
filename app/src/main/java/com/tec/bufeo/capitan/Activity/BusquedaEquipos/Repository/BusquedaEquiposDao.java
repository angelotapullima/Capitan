package com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Models.BusquedaEquipos;

import java.util.List;

@Dao
public interface BusquedaEquiposDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BusquedaEquipos resultModel);

    /*@Query("SELECT * from equipos WHERE mi_equipo = :mi_equipo")
    LiveData<List<RegistroEquiposTorneo>> getAllEquipos(String mi_equipo);*/

    @Query("SELECT * from equipos")
    LiveData<List<BusquedaEquipos>> getAllEquipo();

    @Query("SELECT * from equipos ")
    LiveData<List<BusquedaEquipos>> getAll();


    @Query("Update equipos set estado_seleccion = '1' where equipo_id =:id")
    void actualizarEstado1(String id);

    @Query("Update equipos set estado_seleccion = '0' where equipo_id =:id")
    void actualizarEstado0(String id);


    @Query("DELETE FROM equipos")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<BusquedaEquipos> resultModel);

}
