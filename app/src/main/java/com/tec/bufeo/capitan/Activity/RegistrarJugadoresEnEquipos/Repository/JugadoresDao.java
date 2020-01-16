package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository;

import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.Jugadores;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface JugadoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Jugadores resultModel);


    /*@Query("SELECT * from jugadores WHERE mi_equipo =:mio ")
    LiveData<List<Jugadores>> getAllEquipo(String mio);




    @Query("Update equipos set estado_seleccion = '1' where equipo_id =:id")
    void actualizarEstado1(String id);

    @Query("Update equipos set estado_seleccion = '0' where equipo_id =:id")
    void actualizarEstado0(String id);*/

    @Query("SELECT * from jugadores ")
    LiveData<List<Jugadores>> getAll();

    @Query("DELETE FROM jugadores")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJugadores(List<Jugadores> resultModel);
}
