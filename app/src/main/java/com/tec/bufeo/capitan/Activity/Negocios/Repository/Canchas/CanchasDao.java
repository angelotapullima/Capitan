package com.tec.bufeo.capitan.Activity.Negocios.Repository.Canchas;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.Activity.Negocios.Model.Canchas;

import java.util.List;

@Dao
public interface CanchasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Canchas resultModel);


    @Query("SELECT * from canchas where id_empresa =:id")
    LiveData<List<Canchas>> getCancha(String id);

    @Query("DELETE FROM canchas")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<Canchas> resultModel);

}
