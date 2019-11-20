package com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Models.Mensajes;

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
