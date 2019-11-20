package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquipos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;

import java.util.List;

@Dao
public interface MisEquiposDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Mequipos resultModel);

    /*@Query("SELECT * from equipos WHERE mi_equipo = :mi_equipo")
    LiveData<List<Mequipos>> getAllEquipos(String mi_equipo);*/

    @Query("SELECT * from equipos WHERE mi_equipo =:si ")
    LiveData<List<Mequipos>> getAllMIEquipo(String si);


    @Query("DELETE FROM equipos")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<Mequipos> resultModel);

}
