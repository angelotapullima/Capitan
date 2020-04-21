package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.Models.Chats;

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
