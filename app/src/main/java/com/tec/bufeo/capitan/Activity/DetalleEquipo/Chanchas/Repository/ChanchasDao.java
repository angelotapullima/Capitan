package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models.Chanchas;
import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;

import java.util.List;

@Dao
public interface ChanchasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Chanchas resultModel);


    @Query("SELECT * from mis_chanchas where id_equipo =:id")
    LiveData<List<Chanchas>> getChanchas(String id);


    @Query("DELETE FROM mis_chanchas")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<Chanchas> resultModel);


    @Query("SELECT id_chancha FROM mis_chanchas WHERE id_chancha IN (:regions)")
    LiveData<List<Chanchas>> loadIDS(List<String> regions);
}
