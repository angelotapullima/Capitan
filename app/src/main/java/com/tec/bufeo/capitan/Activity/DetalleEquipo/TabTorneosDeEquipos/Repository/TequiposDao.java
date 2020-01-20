package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Models.TorneosDeEquipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

@Dao
public interface TequiposDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TorneosDeEquipos resultModel);

    @Query("SELECT * from torneos_equipos ")
    LiveData<List<TorneosDeEquipos>> getAllTorneoEquipos();

    @Query("DELETE FROM torneos_equipos")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTorneosEquipos(List<TorneosDeEquipos> resultModel);
}
