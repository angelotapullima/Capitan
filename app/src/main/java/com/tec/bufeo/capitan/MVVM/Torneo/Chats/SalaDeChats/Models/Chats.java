package com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

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


    @ColumnInfo(name = "id_usuario_1")
    @SerializedName("id_usuario_1")
    private String id_usuario_1;


    @ColumnInfo(name = "usuario_1")
    @SerializedName("usuario_1")
    private String usuario_1;


    @ColumnInfo(name = "usuario_1_foto")
    @SerializedName("usuario_1_foto")
    private String usuario_1_foto;

@ColumnInfo(name = "id_usuario_2")
    @SerializedName("id_usuario_2")
    private String id_usuario_2;


    @ColumnInfo(name = "usuario_2")
    @SerializedName("usuario_2")
    private String usuario_2;


    @ColumnInfo(name = "usuario_2_foto")
    @SerializedName("usuario_2_foto")
    private String usuario_2_foto;



    @ColumnInfo(name = "chat_fecha")
    @SerializedName("chat_fecha")
    private String chat_fecha;

   /* @ColumnInfo(name = "chat_mensaje")
    @SerializedName("chat_mensaje")
    private String chat_mensaje;*/

    @ColumnInfo(name = "chat_estado")
    @SerializedName("chat_estado")
    private String chat_estado;

    @ColumnInfo(name = "chat_ultimo_mensaje")
    @SerializedName("chat_ultimo_mensaje")
    private String chat_ultimo_mensaje;

    @ColumnInfo(name = "chat_ultimo_mensaje_id")
    @SerializedName("chat_ultimo_mensaje_id")
    private String chat_ultimo_mensaje_id;


    @ColumnInfo(name = "chat_ultimo_mensaje_fecha")
    @SerializedName("chat_ultimo_mensaje_fecha")
    private String chat_ultimo_mensaje_fecha;

    @ColumnInfo(name = "chat_ultimo_mensaje_hora")
    @SerializedName("chat_ultimo_mensaje_hora")
    private String chat_ultimo_mensaje_hora;


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

    public String getId_usuario_1() {
        return id_usuario_1;
    }

    public void setId_usuario_1(String id_usuario_1) {
        this.id_usuario_1 = id_usuario_1;
    }

    public String getUsuario_1() {
        return usuario_1;
    }

    public void setUsuario_1(String usuario_1) {
        this.usuario_1 = usuario_1;
    }

    public String getUsuario_1_foto() {
        return usuario_1_foto;
    }

    public void setUsuario_1_foto(String usuario_1_foto) {
        this.usuario_1_foto = usuario_1_foto;
    }

    public String getId_usuario_2() {
        return id_usuario_2;
    }

    public void setId_usuario_2(String id_usuario_2) {
        this.id_usuario_2 = id_usuario_2;
    }

    public String getUsuario_2() {
        return usuario_2;
    }

    public void setUsuario_2(String usuario_2) {
        this.usuario_2 = usuario_2;
    }

    public String getUsuario_2_foto() {
        return usuario_2_foto;
    }

    public void setUsuario_2_foto(String usuario_2_foto) {
        this.usuario_2_foto = usuario_2_foto;
    }

    public String getChat_fecha() {
        return chat_fecha;
    }

    public void setChat_fecha(String chat_fecha) {
        this.chat_fecha = chat_fecha;
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

    public String getChat_ultimo_mensaje_id() {
        return chat_ultimo_mensaje_id;
    }

    public void setChat_ultimo_mensaje_id(String chat_ultimo_mensaje_id) {
        this.chat_ultimo_mensaje_id = chat_ultimo_mensaje_id;
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
