package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "retos")
public class Retos {


    /*@PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_reto")
    @SerializedName("id_reto")
    private int id_reto;*/


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "retos_id")
    @SerializedName("retos_id")
    private String retos_id;

    @ColumnInfo(name = "retador_id")
    @SerializedName("retador_id")
    private String retador_id;


    @ColumnInfo(name = "retado_id")
    @SerializedName("retado_id")
    private String retado_id;

    @ColumnInfo(name = "retos_foto_retador")
    @SerializedName("retos_foto_retador")
    private String retos_foto_retador;

    @ColumnInfo(name = "retos_nombre_retador")
    @SerializedName("retos_nombre_retador")
    private String retos_nombre_retador;

    @ColumnInfo(name = "retos_foto_retado")
    @SerializedName("retos_foto_retado")
    private String retos_foto_retado;

    @ColumnInfo(name = "retos_nombre_retado")
    @SerializedName("retos_nombre_retado")
    private String retos_nombre_retado;

    @ColumnInfo(name = "retos_fecha")
    @SerializedName("retos_fecha")
    private String retos_fecha;

    @ColumnInfo(name = "retos_hora")
    @SerializedName("retos_hora")
    private String retos_hora;

    @ColumnInfo(name = "retos_lugar")
    @SerializedName("retos_lugar")
    private String retos_lugar;

    @ColumnInfo(name = "retos_estado")
    @SerializedName("retos_estado")
    private String retos_estado;

    @ColumnInfo(name = "retos_respuesta")
    @SerializedName("retos_respuesta")
    private String retos_respuesta;

    @ColumnInfo(name = "user_respuesta")
    @SerializedName("user_respuesta")
    private String user_respuesta;




    /*public int getId_reto() {
        return id_reto;
    }

    public void setId_reto(int id_reto) {
        this.id_reto = id_reto;
    }*/

    public String getRetos_id() {
        return retos_id;
    }

    public void setRetos_id(String retos_id) {
        this.retos_id = retos_id;
    }

    public String getRetador_id() {
        return retador_id;
    }

    public void setRetador_id(String retador_id) {
        this.retador_id = retador_id;
    }

    public String getRetado_id() {
        return retado_id;
    }

    public void setRetado_id(String retado_id) {
        this.retado_id = retado_id;
    }

    public String getRetos_foto_retador() {
        return retos_foto_retador;
    }

    public void setRetos_foto_retador(String retos_foto_retador) {
        this.retos_foto_retador = retos_foto_retador;
    }

    public String getRetos_nombre_retador() {
        return retos_nombre_retador;
    }

    public void setRetos_nombre_retador(String retos_nombre_retador) {
        this.retos_nombre_retador = retos_nombre_retador;
    }

    public String getRetos_foto_retado() {
        return retos_foto_retado;
    }

    public void setRetos_foto_retado(String retos_foto_retado) {
        this.retos_foto_retado = retos_foto_retado;
    }

    public String getRetos_nombre_retado() {
        return retos_nombre_retado;
    }

    public void setRetos_nombre_retado(String retos_nombre_retado) {
        this.retos_nombre_retado = retos_nombre_retado;
    }

    public String getRetos_fecha() {
        return retos_fecha;
    }

    public void setRetos_fecha(String retos_fecha) {
        this.retos_fecha = retos_fecha;
    }

    public String getRetos_hora() {
        return retos_hora;
    }

    public void setRetos_hora(String retos_hora) {
        this.retos_hora = retos_hora;
    }

    public String getRetos_lugar() {
        return retos_lugar;
    }

    public void setRetos_lugar(String retos_lugar) {
        this.retos_lugar = retos_lugar;
    }

    public String getRetos_estado() {
        return retos_estado;
    }

    public void setRetos_estado(String retos_estado) {
        this.retos_estado = retos_estado;
    }


    public String getRetos_respuesta() {
        return retos_respuesta;
    }

    public void setRetos_respuesta(String retos_respuesta) {
        this.retos_respuesta = retos_respuesta;
    }

    public String getUser_respuesta() {
        return user_respuesta;
    }

    public void setUser_respuesta(String user_respuesta) {
        this.user_respuesta = user_respuesta;
    }

    @Override
    public String toString() {
        return "Retos{" +
                //"id_reto=" + id_reto +
                ", retos_id='" + retos_id + '\'' +
                ", retador_id='" + retador_id + '\'' +
                ", retado_id='" + retado_id + '\'' +
                ", retos_foto_retador='" + retos_foto_retador + '\'' +
                ", retos_nombre_retador='" + retos_nombre_retador + '\'' +
                ", retos_foto_retado='" + retos_foto_retado + '\'' +
                ", retos_nombre_retado='" + retos_nombre_retado + '\'' +
                ", retos_fecha='" + retos_fecha + '\'' +
                ", retos_hora='" + retos_hora + '\'' +
                ", retos_lugar='" + retos_lugar + '\'' +
                ", retos_estado='" + retos_estado + '\'' +
                ", retos_respuesta='" + retos_respuesta + '\'' +
                '}';
    }
}
