package com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Models.EquiposUsuarios;

import java.util.List;

@Dao
public interface EquiposUsuariosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EquiposUsuarios resultModel);


    @Query("SELECT * from equipos_usuario where usuario_id=:user_id")
    LiveData<List<EquiposUsuarios>> getAllEquiposUsuarios(String user_id);

    @Query("DELETE FROM equipos_usuario")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<EquiposUsuarios> resultModel);

}
