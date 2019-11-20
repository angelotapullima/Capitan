package com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Chats")
public class Chats {

    public Chats() {
    }


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "chat_id")
    @SerializedName("chat_id")
    private String chat_id;

    @ColumnInfo(name = "chat_usuario")
    @SerializedName("chat_usuario")
    private String chat_usuario;

    @ColumnInfo(name = "chat_fecha")
    @SerializedName("chat_fecha")
    private String chat_fecha;

    @ColumnInfo(name = "chat_mensaje")
    @SerializedName("chat_mensaje")
    private String chat_mensaje;

    @ColumnInfo(name = "chat_estado")
    @SerializedName("chat_estado")
    private String chat_estado;

    @ColumnInfo(name = "chat_ultimo_mensaje")
    @SerializedName("chat_ultimo_mensaje")
    private String chat_ultimo_mensaje;

    @ColumnInfo(name = "chat_ultimo_mensaje_fecha")
    @SerializedName("chat_ultimo_mensaje_fecha")
    private String chat_ultimo_mensaje_fecha;

    @ColumnInfo(name = "chat_ultimo_mensaje_hora")
    @SerializedName("chat_ultimo_mensaje_hora")
    private String chat_ultimo_mensaje_hora;

    @ColumnInfo(name = "chat_ultimo_mensaje_id")
    @SerializedName("chat_ultimo_mensaje_id")
    private String chat_ultimo_mensaje_id;

    @ColumnInfo(name = "chat_ultimo_mensaje_usuario")
    @SerializedName("chat_ultimo_mensaje_usuario")
    private String chat_ultimo_mensaje_usuario;

    @ColumnInfo(name = "mensaje_id")
    @SerializedName("mensaje_id")
    private String mensaje_id;

    @NonNull
    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(@NonNull String chat_id) {
        this.chat_id = chat_id;
    }

    public String getChat_usuario() {
        return chat_usuario;
    }

    public void setChat_usuario(String chat_usuario) {
        this.chat_usuario = chat_usuario;
    }

    public String getChat_fecha() {
        return chat_fecha;
    }

    public void setChat_fecha(String chat_fecha) {
        this.chat_fecha = chat_fecha;
    }

    public String getChat_mensaje() {
        return chat_mensaje;
    }

    public void setChat_mensaje(String chat_mensaje) {
        this.chat_mensaje = chat_mensaje;
    }

    public String getChat_estado() {
        return chat_estado;
    }

    public void setChat_estado(String chat_estado) {
        this.chat_estado = chat_estado;
    }

    public String getChat_ultimo_mensaje() {
        return chat_ultimo_mensaje;
    }

    public void setChat_ultimo_mensaje(String chat_ultimo_mensaje) {
        this.chat_ultimo_mensaje = chat_ultimo_mensaje;
    }

    public String getChat_ultimo_mensaje_fecha() {
        return chat_ultimo_mensaje_fecha;
    }

    public void setChat_ultimo_mensaje_fecha(String chat_ultimo_mensaje_fecha) {
        this.chat_ultimo_mensaje_fecha = chat_ultimo_mensaje_fecha;
    }

    public String getChat_ultimo_mensaje_hora() {
        return chat_ultimo_mensaje_hora;
    }

    public void setChat_ultimo_mensaje_hora(String chat_ultimo_mensaje_hora) {
        this.chat_ultimo_mensaje_hora = chat_ultimo_mensaje_hora;
    }

    public String getChat_ultimo_mensaje_id() {
        return chat_ultimo_mensaje_id;
    }

    public void setChat_ultimo_mensaje_id(String chat_ultimo_mensaje_id) {
        this.chat_ultimo_mensaje_id = chat_ultimo_mensaje_id;
    }

    public String getChat_ultimo_mensaje_usuario() {
        return chat_ultimo_mensaje_usuario;
    }

    public void setChat_ultimo_mensaje_usuario(String chat_ultimo_mensaje_usuario) {
        this.chat_ultimo_mensaje_usuario = chat_ultimo_mensaje_usuario;
    }

    public String getMensaje_id() {
        return mensaje_id;
    }

    public void setMensaje_id(String mensaje_id) {
        this.mensaje_id = mensaje_id;
    }
}
