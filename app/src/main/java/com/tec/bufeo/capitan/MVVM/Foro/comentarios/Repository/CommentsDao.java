package com.tec.bufeo.capitan.MVVM.Foro.comentarios.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
