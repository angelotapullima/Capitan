package com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;

import java.util.List;

@Dao
public interface DatosUsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DatosUsuario resultModel);


    @Query("SELECT * from datos_usuario where id_user=:id_user ")
    LiveData<List<DatosUsuario>> getAllDatosUsuario(String id_user);

    @Query("DELETE FROM datos_usuario")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDatosUsuario(List<DatosUsuario> resultModel);

}
