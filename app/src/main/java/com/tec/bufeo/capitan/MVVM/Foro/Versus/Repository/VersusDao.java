package com.tec.bufeo.capitan.MVVM.Foro.Versus.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

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
