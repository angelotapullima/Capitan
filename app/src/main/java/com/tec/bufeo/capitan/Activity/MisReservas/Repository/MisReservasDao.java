package com.tec.bufeo.capitan.Activity.MisReservas.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;

import java.util.List;

@Dao
public interface MisReservasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MisReservas resultModel);


    @Query("SELECT * from mis_reservas ORDER BY  fecha_reserva  DESC ")
    LiveData<List<MisReservas>> getAll();

    @Query("SELECT * from mis_reservas where fecha_reserva = :fecha")
    LiveData<List<MisReservas>> getAllDetalle(String fecha);


    @Query("DELETE FROM mis_reservas")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<MisReservas> resultModel);

}