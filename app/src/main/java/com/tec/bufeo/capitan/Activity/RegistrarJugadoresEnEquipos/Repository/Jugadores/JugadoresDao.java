package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores;

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




    @Query("SELECT * from jugadores WHERE jugador_estado =:estado  ")
    LiveData<List<Jugadores>> getAllJugadores(String estado);

    @Query("SELECT * FROM jugadores WHERE jugador_nombre LIKE '%' || :query || '%'  " )
    LiveData<List<Jugadores>> searchFeeds(String query);

    @Query("SELECT * from jugadores WHERE jugador_mi_equipo =:mio  ")
    LiveData<List<Jugadores>> getAllMisJugadores(String mio);

    @Query("Update jugadores set jugador_estado = 'seleccionado' where jugador_id =:id")
    void EstadoSeleccionado(String id);

    @Query("Update jugadores set jugador_estado = null where jugador_id =:id")
    void EstadoVacio(String id);

    @Query("SELECT * from jugadores ")
    LiveData<List<Jugadores>> getAll();

    @Query("DELETE FROM jugadores")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJugadores(List<Jugadores> resultModel);
}
