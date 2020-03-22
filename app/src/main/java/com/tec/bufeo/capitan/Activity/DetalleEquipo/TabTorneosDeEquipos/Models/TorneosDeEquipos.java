package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Models;


import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "torneos_equipos")
public class TorneosDeEquipos {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_torneo")
    @SerializedName("id_torneo")
    private String  id_torneo;

    @ColumnInfo(name = "equipo_id")
    @SerializedName("equipo_id")
    private String equipo_id;

    @ColumnInfo(name = "nombre")
    @SerializedName("nombre")
    private String nombre;

    @ColumnInfo(name = "foto")
    @SerializedName("foto")
    private String foto;

    @ColumnInfo(name = "lugar")
    @SerializedName("lugar")
    private String lugar;

    @ColumnInfo(name = "fecha")
    @SerializedName("fecha")
    private String fecha;

    @ColumnInfo(name = "organizador")
    @SerializedName("organizador")
    private String organizador;


    @NonNull
    public String getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(@NonNull String id_torneo) {
        this.id_torneo = id_torneo;
    }

    public String getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(String equipo_id) {
        this.equipo_id = equipo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }
}
