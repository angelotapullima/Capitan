package com.tec.bufeo.capitan.Activity.DetalleCanchas.Repository;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.tec.bufeo.capitan.Activity.DetalleCanchas.Models.ReservasCancha;

import java.util.List;

@Dao
public interface ReservasCanchaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ReservasCancha reservasCancha);

    @Query("SELECT * from reservas_cancha where fecha =:fecha and cancha_id=:cancha_id")
    LiveData<List<ReservasCancha>> getReservasDia(String fecha,String cancha_id);


    @Query("DELETE FROM reservas_cancha")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReservas(List<ReservasCancha> reservasCanchas);
}
