package com.tec.bufeo.capitan.Activity.MisMovimientos.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.Movimientos;

import java.util.List;

@Dao
public interface MovimientosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movimientos resultModel);


    @Query("SELECT * from Movimientos ")
    LiveData<List<Movimientos>> getAll();

    @Query("DELETE FROM Movimientos")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<Movimientos> resultModel);

}
