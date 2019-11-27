package com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "EquiposTorneo")
public class EquiposTorneo
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

    @ColumnInfo(name = "equipo_torneo_id")
    @SerializedName("equipo_torneo_id")
    private String equipo_torneo_id;



    @ColumnInfo(name = "capitan_nombre")
    @SerializedName("capitan_nombre")
    private String capitan_nombre;

    @ColumnInfo(name = "capitan_id")
    @SerializedName("capitan_id")
    private String capitan_id;




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

    public String getCapitan_id() {
        return capitan_id;
    }

    public void setCapitan_id(String capitan_id) {
        this.capitan_id = capitan_id;
    }

    public String getEquipo_torneo_id() {
        return equipo_torneo_id;
    }

    public void setEquipo_torneo_id(String equipo_torneo_id) {
        this.equipo_torneo_id = equipo_torneo_id;
    }
}