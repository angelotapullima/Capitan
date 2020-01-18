package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.seleccionados;


import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.Jugadores;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.JugadoresSeleccionados;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SeleccionadosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(JugadoresSeleccionados resultModel);




    /*
    @Query("SELECT * from jugadores WHERE jugador_estado =:estado  ")
    LiveData<List<Jugadores>> getAllJugadores(String estado);


    @Query("Update jugadores set jugador_estado = 'seleccionado' where jugador_id =:id")
    void EstadoSeleccionado(String id);

    @Query("Update jugadores set jugador_estado = null where jugador_id =:id")
    void EstadoVacio(String id);
*/

    @Query("SELECT * from jugadores_seleccionados ")
    LiveData<List<JugadoresSeleccionados>> getAll();

    @Query("DELETE FROM jugadores_seleccionados where jugadors_id=:id")
    void deleteOne(String id);

    @Query("DELETE FROM jugadores_seleccionados")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSeleccionados(List<JugadoresSeleccionados> resultModel);
}
