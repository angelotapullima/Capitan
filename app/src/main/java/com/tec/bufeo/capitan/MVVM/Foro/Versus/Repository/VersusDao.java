package com.tec.bufeo.capitan.MVVM.Foro.Versus.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.MVVM.Foro.Versus.Models.Versus;

import java.util.List;

@Dao
public interface VersusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Versus resultModel);



    @Query("SELECT * from Versus")
    LiveData<List<Versus>> getAllVersus();



    @Query("DELETE FROM Versus")
    void deleteAll();

    @Query("DELETE FROM Versus ")
    void deleteOne();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(List<Versus> resultModel);



}
