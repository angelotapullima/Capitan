package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Models.Mequipos;

import java.util.List;

@Dao
public interface MisEquiposDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Mequipos resultModel);

    /*@Query("SELECT * from equipos WHERE mi_equipo = :mi_equipo")
    LiveData<List<RegistroEquiposTorneo>> getAllEquipos(String mi_equipo);*/

    @Query("SELECT * from equipos WHERE mis_equipos =:mio ")
    LiveData<List<Mequipos>> getAllEquipo(String mio);

    @Query("SELECT * from equipos ")
    LiveData<List<Mequipos>> getAll();


    @Query("Update equipos set estado_seleccion = '1' where equipo_id =:id")
    void actualizarEstado1(String id);

    @Query("Update equipos set estado_seleccion = '0' where equipo_id =:id")
    void actualizarEstado0(String id);


    @Query("DELETE FROM equipos")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<Mequipos> resultModel);

}
