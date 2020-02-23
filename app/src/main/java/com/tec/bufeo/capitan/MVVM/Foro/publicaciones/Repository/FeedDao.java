package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;

import java.util.List;

//import com.andr.mvvm.RetrofitRoom.Models.PublicacionesTorneo;

@Dao
public interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ModelFeed feedTorneo);

    @Query("SELECT * from feed where estado =1  and foro_foto <> 0 ORDER BY orden DESC ")
    LiveData<List<ModelFeed>> getAllPosts();

    @Query("SELECT * from feed where id_torneo =:id_torneo ")
    LiveData<List<ModelFeed>> getIdTorneo(String id_torneo);

    @Query("DELETE FROM feed")
    void deleteAll();

    @Query("DELETE FROM feed WHERE  publicacion_id = :foro_id")
    void deleteOneFeed(String foro_id);

    @Query("SELECT * FROM feed WHERE foro_titulo LIKE '%' || :query || '%' OR foro_descripcion LIKE '%' || :query || '%'  OR usuario_nombre LIKE '%' || :query || '%'" )
    LiveData<List<ModelFeed>> searchFeeds(String query);


   /* @Query("SELECT publicacion_id,limite_inf,limite_sup from feed")
    LiveData<List<ModelFeed>> getAllIdPosts();*/

    @Query("Update feed set limite_sup = :sup")
    void actualizarSup(String sup);

    @Query("Update feed set limite_inf = :sup")
    void actualizarInf(String sup);


    @Query("Update feed set nuevos_datos = :nuevos")
    void NuevosDatos(String nuevos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<ModelFeed> feedTorneo);

}
