package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "torneo")
public class Torneo {


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_torneo")
    @SerializedName("id_torneo")
    private String id_torneo;


    @ColumnInfo(name = "usuario_id")
    @SerializedName("usuario_id")
    private String usuario_id;


    @ColumnInfo(name = "torneo_nombre")
    @SerializedName("torneo_nombre")
    private String torneo_nombre;

    @ColumnInfo(name = "torneo_descripcion")
    @SerializedName("torneo_descripcion")
    private String torneo_descripcion;

    @ColumnInfo(name = "torneo_fecha")
    @SerializedName("torneo_fecha")
    private String torneo_fecha;

    @ColumnInfo(name = "torneo_hora")
    @SerializedName("torneo_hora")
    private String torneo_hora;

    @ColumnInfo(name = "torneo_lugar")
    @SerializedName("torneo_lugar")
    private String torneo_lugar;

    @ColumnInfo(name = "torneo_equipos")
    @SerializedName("torneo_equipos")
    private String torneo_equipos;

    @ColumnInfo(name = "torneo_organizador")
    @SerializedName("torneo_organizador")
    private String torneo_organizador;

    @ColumnInfo(name = "mi_torneo")
    @SerializedName("mi_torneo")
    private String mi_torneo;

    @ColumnInfo(name = "foto_torneo")
    @SerializedName("foto_torneo")
    private String foto_torneo;

    @ColumnInfo(name = "torneo_precio")
    @SerializedName("torneo_precio")
    private String torneo_precio;


    @NonNull
    public String getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(@NonNull String id_torneo) {
        this.id_torneo = id_torneo;
    }



    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getTorneo_nombre() {
        return torneo_nombre;
    }

    public void setTorneo_nombre(String torneo_nombre) {
        this.torneo_nombre = torneo_nombre;
    }

    public String getTorneo_descripcion() {
        return torneo_descripcion;
    }

    public void setTorneo_descripcion(String torneo_descripcion) {
        this.torneo_descripcion = torneo_descripcion;
    }

    public String getTorneo_fecha() {
        return torneo_fecha;
    }

    public void setTorneo_fecha(String torneo_fecha) {
        this.torneo_fecha = torneo_fecha;
    }

    public String getTorneo_hora() {
        return torneo_hora;
    }

    public void setTorneo_hora(String torneo_hora) {
        this.torneo_hora = torneo_hora;
    }

    public String getTorneo_lugar() {
        return torneo_lugar;
    }

    public void setTorneo_lugar(String torneo_lugar) {
        this.torneo_lugar = torneo_lugar;
    }

    public String getTorneo_equipos() {
        return torneo_equipos;
    }

    public void setTorneo_equipos(String torneo_equipos) {
        this.torneo_equipos = torneo_equipos;
    }

    public String getTorneo_organizador() {
        return torneo_organizador;
    }

    public void setTorneo_organizador(String torneo_organizador) {
        this.torneo_organizador = torneo_organizador;
    }

    public String getMi_torneo() {
        return mi_torneo;
    }

    public void setMi_torneo(String mi_torneo) {
        this.mi_torneo = mi_torneo;
    }

    public String getFoto_torneo() {
        return foto_torneo;
    }

    public void setFoto_torneo(String foto_torneo) {
        this.foto_torneo = foto_torneo;
    }

    public String getTorneo_precio() {
        return torneo_precio;
    }

    public void setTorneo_precio(String torneo_precio) {
        this.torneo_precio = torneo_precio;
    }
}
