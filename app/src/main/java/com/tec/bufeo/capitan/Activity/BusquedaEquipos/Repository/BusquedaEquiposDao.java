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

    @Query("SELECT * from busqueda_equipos")
    LiveData<List<BusquedaEquipos>> getAllEquipo();

    @Query("SELECT * from busqueda_equipos where estado_seleccion = 'vacio' ")
    LiveData<List<BusquedaEquipos>> getmAllVacio();


    @Query("SELECT * from busqueda_equipos where estado_seleccion = 'seleccionado' ")
    LiveData<List<BusquedaEquipos>> getmAllSeleccionado();

    @Query("SELECT * FROM busqueda_equipos WHERE equipo_nombre LIKE '%' || :query || '%'  " )
    LiveData<List<BusquedaEquipos>> searchFeeds(String query);


    @Query("DELETE FROM busqueda_equipos")
    void deleteAll();



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<BusquedaEquipos> resultModel);

}
