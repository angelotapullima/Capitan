package com.tec.bufeo.capitan.MVVM.Foro.comentarios.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Models.Comments;

import java.util.List;

@Dao
public interface CommentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Comments resultModel);



    @Query("SELECT * from Comments WHERE publicacion_id = :id_publicacion ORDER BY comments_id desc ")
    LiveData<List<Comments>> getAllComments(String id_publicacion );



    @Query("DELETE FROM Comments")
    void deleteAll();

    @Query("DELETE FROM Comments WHERE publicacion_id= :id_publicacion")
    void deleteOne(String id_publicacion);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(List<Comments> resultModel);



}
