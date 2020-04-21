package com.tec.bufeo.capitan.Activity.ReviewsComentarios.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.ReviewsComentarios.Models.Reseñas;

import java.util.List;

@Dao
public interface ReseñasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Reseñas resultModel);


    @Query("SELECT * from reseñas where rating_empresa_valor =:valor")
    LiveData<List<Reseñas>> getValor(String valor);

    @Query("SELECT * from reseñas")
    LiveData<List<Reseñas>> getAll();

    @Query("DELETE FROM reseñas")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReseñas(List<Reseñas> resultModel);

}
