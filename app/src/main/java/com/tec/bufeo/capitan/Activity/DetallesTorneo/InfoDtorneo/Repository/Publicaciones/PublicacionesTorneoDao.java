package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Publicaciones;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.PublicacionesTorneo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;



import java.util.List;

//import com.andr.mvvm.RetrofitRoom.Models.PublicacionesTorneo;

@Dao
public interface PublicacionesTorneoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PublicacionesTorneo publicacionesTorneo);

    @Query("SELECT * from publicaciones_torneo ORDER BY orden DESC")
    LiveData<List<PublicacionesTorneo>> getAllPosts();

    @Query("SELECT * from publicaciones_torneo where id_torneo =:id_torneo ")
    LiveData<List<PublicacionesTorneo>> getIdTorneo(String id_torneo);

    @Query("DELETE FROM publicaciones_torneo")
    void deleteAll();

    @Query("DELETE FROM publicaciones_torneo WHERE  publicacion_id = :foro_id")
    void deleteOneFeed(String foro_id);

    @Query("SELECT * FROM publicaciones_torneo WHERE foro_titulo LIKE '%' || :query || '%' OR foro_descripcion LIKE '%' || :query || '%'  OR usuario_nombre LIKE '%' || :query || '%'" )
    LiveData<List<PublicacionesTorneo>> searchFeeds(String query);


    /*@Query("SELECT publicacion_id,limite_inf,limite_sup from feedTorneo")
    LiveData<List<FeedTorneo>> getAllIdPosts();*/

    @Query("Update publicaciones_torneo set limite_sup = :sup")
    void actualizarSup(String sup);

    @Query("Update publicaciones_torneo set limite_inf = :sup")
    void actualizarInf(String sup);


    @Query("Update publicaciones_torneo set nuevos_datos = :nuevos")
    void NuevosDatos(String nuevos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<PublicacionesTorneo> feedTorneo);

}
