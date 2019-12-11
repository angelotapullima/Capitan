package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Repository;

import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Models.InstanciasModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface InstanciasDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(InstanciasModel instanciasModel);

    @Query("SELECT * from instancias ")
    LiveData<List<InstanciasModel>> getAllInstancias();

    @Query("SELECT * from instancias where id_torneo =:id_torneo ")
    LiveData<List<InstanciasModel>> getIdTorneo(String id_torneo);

    @Query("DELETE FROM instancias")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<InstanciasModel> instanciasModels);
}