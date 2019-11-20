package com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Models.Chats;

import java.util.List;

@Dao
public interface ChatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Chats resultModel);



    @Query("SELECT * from Chats  ")
    LiveData<List<Chats>> getAllChats();



    @Query("DELETE FROM Chats")
    void deleteAll();

    @Query("DELETE FROM Chats WHERE chat_id = :chat_id")
    void deleteOne(String chat_id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(List<Chats> resultModel);



}
