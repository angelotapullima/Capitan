package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.OtrosEquipos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;

import java.util.List;

@Dao
public interface OtrosEquiposDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Mequipos resultModel);

    /*@Query("SELECT * from equipos WHERE mi_equipo = :mi_equipo")
    LiveData<List<EquiposTorneo>> getAllEquipos(String mi_equipo);*/

    @Query("SELECT * from equipos WHERE mi_equipo = :no ")
    LiveData<List<Mequipos>> getAllOtrosEquipos(String no);


    @Query("DELETE FROM equipos")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<Mequipos> resultModel);

}
