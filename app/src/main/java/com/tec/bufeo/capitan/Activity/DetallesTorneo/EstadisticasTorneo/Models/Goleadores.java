package com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "goleadores")
public class Goleadores {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_torneo")
    @SerializedName("id_torneo")
    private String id_torneo;

    @ColumnInfo(name = "user_image")
    @SerializedName("user_image")
    private String user_image;

    @ColumnInfo(name = "user_nickname")
    @SerializedName("user_nickname")
    private String user_nickname;

    @ColumnInfo(name = "equipo_foto")
    @SerializedName("equipo_foto")
    private String equipo_foto;

    @ColumnInfo(name = "equipo_nombre")
    @SerializedName("equipo_nombre")
    private String equipo_nombre;


    @ColumnInfo(name = "goles")
    @SerializedName("goles")
    private String goles;


    @NonNull
    public String getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(@NonNull String id_torneo) {
        this.id_torneo = id_torneo;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getEquipo_foto() {
        return equipo_foto;
    }

    public void setEquipo_foto(String equipo_foto) {
        this.equipo_foto = equipo_foto;
    }

    public String getEquipo_nombre() {
        return equipo_nombre;
    }

    public void setEquipo_nombre(String equipo_nombre) {
        this.equipo_nombre = equipo_nombre;
    }

    public String getGoles() {
        return goles;
    }

    public void setGoles(String goles) {
        this.goles = goles;
    }
}
