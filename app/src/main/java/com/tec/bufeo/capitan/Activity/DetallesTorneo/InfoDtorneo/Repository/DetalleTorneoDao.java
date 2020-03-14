package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Detalle;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.DetalleTorneo;

import java.util.List;


@Dao
public interface  DetalleTorneoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DetalleTorneo detalleTorneo);

    @Query("SELECT * from detalle_torneo where id_torneo =:id_torneo ")
    LiveData<List<DetalleTorneo>> getIdTorneo(String id_torneo);

    @Query("DELETE FROM detalle_torneo")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<DetalleTorneo> feedTorneo);

}
