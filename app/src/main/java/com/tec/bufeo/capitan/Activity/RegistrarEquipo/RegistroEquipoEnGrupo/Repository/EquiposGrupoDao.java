package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Model.EquiposGrupo;

import java.util.List;

@Dao
public interface EquiposGrupoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EquiposGrupo resultModel);



    @Query("SELECT * from equipos_grupo")
    LiveData<List<EquiposGrupo>> getAllEquipoGrupo();




    @Query("DELETE FROM equipos_grupo")
    void deleteAll();

    @Query("DELETE FROM equipos_grupo where equipo_id =:id")
    void deleteID(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<EquiposGrupo> resultModel);

}
