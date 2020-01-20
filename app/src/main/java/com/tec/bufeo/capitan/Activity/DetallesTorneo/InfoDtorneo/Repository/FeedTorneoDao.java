package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.FeedTorneo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;



import java.util.List;

//import com.andr.mvvm.RetrofitRoom.Models.PublicacionesTorneo;

@Dao
public interface FeedTorneoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FeedTorneo feedTorneo);

    @Query("SELECT * from feedTorneo ORDER BY orden DESC")
    LiveData<List<FeedTorneo>> getAllPosts();

    @Query("SELECT * from feedTorneo where id_torneo =:id_torneo ")
    LiveData<List<FeedTorneo>> getIdTorneo(String id_torneo);

    @Query("DELETE FROM feedTorneo")
    void deleteAll();

    @Query("DELETE FROM feedTorneo WHERE  publicacion_id = :foro_id")
    void deleteOneFeed(String foro_id);

    @Query("SELECT * FROM feedTorneo WHERE foro_titulo LIKE '%' || :query || '%' OR foro_descripcion LIKE '%' || :query || '%'  OR usuario_nombre LIKE '%' || :query || '%'" )
    LiveData<List<FeedTorneo>> searchFeeds(String query);


    /*@Query("SELECT publicacion_id,limite_inf,limite_sup from feedTorneo")
    LiveData<List<FeedTorneo>> getAllIdPosts();*/

    @Query("Update feedTorneo set limite_sup = :sup")
    void actualizarSup(String sup);

    @Query("Update feedTorneo set limite_inf = :sup")
    void actualizarInf(String sup);


    @Query("Update feedTorneo set nuevos_datos = :nuevos")
    void NuevosDatos(String nuevos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<FeedTorneo> feedTorneo);

}
