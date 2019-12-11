package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Models;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RegistroEquiposTorneo")
public class RegistroEquiposTorneo
{


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "equipo_id")
    @SerializedName("equipo_id")
    private String equipo_id;

    @ColumnInfo(name = "equipo_nombre")
    @SerializedName("equipo_nombre")
    private String equipo_nombre;


    @ColumnInfo(name = "equipo_foto")
    @SerializedName("equipo_foto")
    private String equipo_foto;


    @ColumnInfo(name = "capitan_nombre")
    @SerializedName("capitan_nombre")
    private String capitan_nombre;

    @ColumnInfo(name = "estado_equipo")
    @SerializedName("estado_equipo")
    private String estado_equipo;

    @ColumnInfo(name = "local")
    @SerializedName("local")
    private String local;


    @ColumnInfo(name = "visitante")
    @SerializedName("visitante")
    private String visitante;


    @NonNull
    public String getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(@NonNull String equipo_id) {
        this.equipo_id = equipo_id;
    }

    public String getEquipo_nombre() {
        return equipo_nombre;
    }

    public void setEquipo_nombre(String equipo_nombre) {
        this.equipo_nombre = equipo_nombre;
    }

    public String getEquipo_foto() {
        return equipo_foto;
    }

    public void setEquipo_foto(String equipo_foto) {
        this.equipo_foto = equipo_foto;
    }

    public String getCapitan_nombre() {
        return capitan_nombre;
    }

    public void setCapitan_nombre(String capitan_nombre) {
        this.capitan_nombre = capitan_nombre;
    }


    public String getEstado_equipo() {
        return estado_equipo;
    }

    public void setEstado_equipo(String estado_equipo) {
        this.estado_equipo = estado_equipo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }
}