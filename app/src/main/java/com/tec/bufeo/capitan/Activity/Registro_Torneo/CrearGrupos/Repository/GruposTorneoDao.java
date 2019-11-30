package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Repository;

import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Models.Grupos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface GruposTorneoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Grupos grupos);

    @Query("SELECT * from grupos ")
    LiveData<List<Grupos>> getAllGrupos();

    @Query("SELECT * from grupos where id_torneo =:id_torneo ")
    LiveData<List<Grupos>> getIdTorneo(String id_torneo);

    @Query("DELETE FROM grupos")
    void deleteAll();




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<Grupos> grupos);

}
