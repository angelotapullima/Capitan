package com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Models.Notificaciones;

import java.util.List;

@Dao
public interface NotificacionesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Notificaciones resultModel);


    @Query("SELECT * from notificaciones ")
    LiveData<List<Notificaciones>> getAllNotificaciones();

    @Query("SELECT * from notificaciones where notificacion_estado = '0' ")
    LiveData<List<Notificaciones>> getAllNoVistos();

    @Query("DELETE FROM notificaciones")
    void deleteAll();


    @Query("Update notificaciones set notificacion_estado = '1' where id_notificacion=:id")
    void updateEstado(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<Notificaciones> resultModel);

}
