package com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "equipos_usuario")
public class EquiposUsuarios
{

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "equipo_id")
    @SerializedName("equipo_id")
    private String equipo_id;

        @ColumnInfo(name = "nombre")
    @SerializedName("nombre")
    private String nombre;

    @ColumnInfo(name = "foto")
    @SerializedName("foto")
    private String foto;

    @ColumnInfo(name = "capitan")
    @SerializedName("capitan")
    private String capitan;

    @ColumnInfo(name = "capitan_id")
    @SerializedName("capitan_id")
    private String capitan_id;

    @ColumnInfo(name = "usuario_id")
    @SerializedName("usuario_id")
    private String usuario_id;

    @NonNull
    public String getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(@NonNull String equipo_id) {
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

    public String getCapitan() {
        return capitan;
    }

    public void setCapitan(String capitan) {
        this.capitan = capitan;
    }

    public String getCapitan_id() {
        return capitan_id;
    }

    public void setCapitan_id(String capitan_id) {
        this.capitan_id = capitan_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }
}