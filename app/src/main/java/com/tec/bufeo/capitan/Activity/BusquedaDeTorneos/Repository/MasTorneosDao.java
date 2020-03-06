package com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Models.MasTorneos;

import java.util.List;

@Dao
public interface MasTorneosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MasTorneos resultModel);

    /*@Query("SELECT * from equipos WHERE mi_equipo = :mi_equipo")
    LiveData<List<RegistroEquiposTorneo>> getAllEquipos(String mi_equipo);*/

    /*@Query("SELECT * from mas_torneos")
    LiveData<List<MasTorneos>> getAllEquipo();*/

    @Query("SELECT * from mas_torneos ")
    LiveData<List<MasTorneos>> getAll();





    @Query("DELETE FROM mas_torneos")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<MasTorneos> resultModel);

}
