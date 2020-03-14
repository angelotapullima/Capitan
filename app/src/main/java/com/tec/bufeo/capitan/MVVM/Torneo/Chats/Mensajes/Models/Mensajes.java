package com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Mensajes")
public class Mensajes {

    public Mensajes() {
    }


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "mensaje_id")
    @SerializedName("mensaje_id")
    private String mensaje_id;


    @ColumnInfo(name = "chat_id")
    @SerializedName("chat_id")
    private String chat_id;

    @ColumnInfo(name = "mensajes_id_usuario")
    @SerializedName("mensajes_id_usuario")
    private String mensajes_id_usuario;

    @ColumnInfo(name = "mensaje_estado")
    @SerializedName("mensaje_estado")
    private String mensaje_estado;

    @ColumnInfo(name = "mensaje_fecha")
    @SerializedName("mensaje_fecha")
    private String mensaje_fecha;

    @ColumnInfo(name = "mensaje_hora")
    @SerializedName("mensaje_hora")
    private String mensaje_hora;

    @ColumnInfo(name = "mensaje_contenido")
    @SerializedName("mensaje_contenido")
    private String mensaje_contenido;


    @ColumnInfo(name = "mensaje_foto")
    @SerializedName("mensaje_foto")
    private String mensaje_foto;

    @ColumnInfo(name = "mensaje_nombre")
    @SerializedName("mensaje_nombre")
    private String mensaje_nombre;




    @NonNull
    public String getMensaje_id() {
        return mensaje_id;
    }

    public void setMensaje_id(@NonNull String mensaje_id) {
        this.mensaje_id = mensaje_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getMensajes_id_usuario() {
        return mensajes_id_usuario;
    }

    public void setMensajes_id_usuario(String mensajes_id_usuario) {
        this.mensajes_id_usuario = mensajes_id_usuario;
    }

    public String getMensaje_estado() {
        return mensaje_estado;
    }

    public void setMensaje_estado(String mensaje_estado) {
        this.mensaje_estado = mensaje_estado;
    }

    public String getMensaje_fecha() {
        return mensaje_fecha;
    }

    public void setMensaje_fecha(String mensaje_fecha) {
        this.mensaje_fecha = mensaje_fecha;
    }

    public String getMensaje_hora() {
        return mensaje_hora;
    }

    public void setMensaje_hora(String mensaje_hora) {
        this.mensaje_hora = mensaje_hora;
    }

    public String getMensaje_contenido() {
        return mensaje_contenido;
    }

    public void setMensaje_contenido(String mensaje_contenido) {
        this.mensaje_contenido = mensaje_contenido;
    }

    public String getMensaje_foto() {
        return mensaje_foto;
    }

    public void setMensaje_foto(String mensaje_foto) {
        this.mensaje_foto = mensaje_foto;
    }

    public String getMensaje_nombre() {
        return mensaje_nombre;
    }

    public void setMensaje_nombre(String mensaje_nombre) {
        this.mensaje_nombre = mensaje_nombre;
    }
}

