package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Models.Mensajes;

import java.util.List;

@Dao
public interface MensajesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Mensajes resultModel);



    @Query("SELECT * from Mensajes WHERE chat_id = :chat_id")
    LiveData<List<Mensajes>> getAllMensajes(String chat_id);



    @Query("DELETE FROM Mensajes")
    void deleteAll();

    @Query("DELETE FROM Mensajes WHERE chat_id= :chat_id")
    void deleteOne(String chat_id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(List<Mensajes> resultModel);



}
