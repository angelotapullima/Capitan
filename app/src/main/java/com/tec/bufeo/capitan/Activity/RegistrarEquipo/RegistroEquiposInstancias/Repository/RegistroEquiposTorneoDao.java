package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository;


import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Models.RegistroEquiposTorneo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface RegistroEquiposTorneoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RegistroEquiposTorneo resultModel);

    /*@Query("SELECT * from equipos WHERE mi_equipo = :mi_equipo")
    LiveData<List<RegistroEquiposTorneo>> getAllEquipos(String mi_equipo);*/

    @Query("SELECT * from RegistroEquiposTorneo ")
    LiveData<List<RegistroEquiposTorneo>> getAllEquiposTorneo();

    @Query("SELECT * from RegistroEquiposTorneo where  local = '1'")
    LiveData<List<RegistroEquiposTorneo>> getLocal();

    @Query("SELECT * from RegistroEquiposTorneo where  visitante = '1'")
    LiveData<List<RegistroEquiposTorneo>> getVisitante();

    @Query("DELETE FROM RegistroEquiposTorneo")
    void deleteAll();

    @Query("Update RegistroEquiposTorneo set estado_equipo = '1' where equipo_id =:id")
    void actualizarEstado1(String id);

    @Query("Update RegistroEquiposTorneo set estado_equipo = '0' where equipo_id =:id")
    void actualizarEstado0(String id);


    @Query("Update RegistroEquiposTorneo set local = '1' where equipo_id =:id")
    void actualizarlocal1(String id);
    @Query("Update RegistroEquiposTorneo set local = '0' where equipo_id =:id")
    void actualizarlocal0(String id);

    @Query("Update RegistroEquiposTorneo set visitante = '1' where equipo_id =:id")
    void actualizarvisita1(String id);
    @Query("Update RegistroEquiposTorneo set visitante = '0' where equipo_id =:id")
    void actualizarvisita0(String id);


    @Query("Update RegistroEquiposTorneo set local = '0'")
    void local0();
    @Query("Update RegistroEquiposTorneo set visitante = '0'")
    void visitante0();
    @Query("Update RegistroEquiposTorneo set estado_equipo = '0'")
    void estado0();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipo(List<RegistroEquiposTorneo> resultModel);

}
