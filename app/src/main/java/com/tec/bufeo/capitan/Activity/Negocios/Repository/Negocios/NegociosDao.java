package com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.Activity.Negocios.Model.Negocios;

import java.util.List;

@Dao
public interface NegociosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Negocios resultModel);


    @Query("SELECT * from negocios ")
    LiveData<List<Negocios>> getAll();

    @Query("SELECT * from negocios where soy_admin ='1' ")
    LiveData<List<Negocios>> getAllMisNegocios();

    @Query("SELECT * from negocios where id_empresa =:id ")
    LiveData<List<Negocios>> getAllDetalle(String id);


    @Query("DELETE FROM negocios")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNegocios(List<Negocios> resultModel);

}
