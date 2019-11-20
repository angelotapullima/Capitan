package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;

import java.util.List;

//import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;

@Dao
public interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ModelFeed modelFeed);

    @Query("SELECT * from feed ORDER BY orden DESC")
    LiveData<List<ModelFeed>> getAllPosts();

    @Query("DELETE FROM feed")
    void deleteAll();

    @Query("DELETE FROM feed WHERE  publicacion_id = :foro_id")
    void deleteOneFeed(String foro_id);

    @Query("SELECT * FROM feed WHERE foro_titulo LIKE '%' || :query || '%' OR foro_descripcion LIKE '%' || :query || '%'  OR usuario_nombre LIKE '%' || :query || '%'" )
    LiveData<List<ModelFeed>> searchFeeds(String query);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<ModelFeed> modelFeed);

}
