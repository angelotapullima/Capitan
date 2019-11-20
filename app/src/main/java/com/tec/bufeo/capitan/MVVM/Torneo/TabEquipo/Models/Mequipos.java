package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "equipos")
public class Mequipos
{



    /*@PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_equipo")
    @SerializedName("id_equipo")
    private int id_equipo;*/
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "equipo_id")
    @SerializedName("equipo_id")
    private String equipo_id;


    @ColumnInfo(name = "torneo_equipo_id")
    @SerializedName("torneo_equipo_id")
    private String torneo_equipo_id;


    @ColumnInfo(name = "torneo_id")
    @SerializedName("torneo_id")
    private String torneo_id;


    @ColumnInfo(name = "equipo_nombre")
    @SerializedName("equipo_nombre")
    private String equipo_nombre;


    @ColumnInfo(name = "equipo_foto")
    @SerializedName("equipo_foto")
    private String equipo_foto;

    @ColumnInfo(name = "equipo_valoracion")
    @SerializedName("equipo_valoracion")
    private String equipo_valoracion;

    @ColumnInfo(name = "equipo_join")
    @SerializedName("equipo_join")
    private String equipo_join;

    @ColumnInfo(name = "equipo_fechaCreacion")
    @SerializedName("equipo_fechaCreacion")
    private String equipo_fechaCreacion;

    @ColumnInfo(name = "equipo_estado")
    @SerializedName("equipo_estado")
    private String equipo_estado;

    @ColumnInfo(name = "usuario_nombre")
    @SerializedName("usuario_nombre")
    private String usuario_nombre;

    @ColumnInfo(name = "capitan_id")
    @SerializedName("capitan_id")
    private String capitan_id;

    @ColumnInfo(name = "mi_equipo")
    @SerializedName("mi_equipo")
    private String mi_equipo;

    /*@NonNull
    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(@NonNull int id_equipo) {
        this.id_equipo = id_equipo;
    }*/

    public String getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(String equipo_id) {
        this.equipo_id = equipo_id;
    }

    public String getTorneo_equipo_id() {
        return torneo_equipo_id;
    }

    public void setTorneo_equipo_id(String torneo_equipo_id) {
        this.torneo_equipo_id = torneo_equipo_id;
    }

    public String getTorneo_id() {
        return torneo_id;
    }

    public void setTorneo_id(String torneo_id) {
        this.torneo_id = torneo_id;
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

    public String getEquipo_valoracion() {
        return equipo_valoracion;
    }

    public void setEquipo_valoracion(String equipo_valoracion) {
        this.equipo_valoracion = equipo_valoracion;
    }

    public String getEquipo_join() {
        return equipo_join;
    }

    public void setEquipo_join(String equipo_join) {
        this.equipo_join = equipo_join;
    }

    public String getEquipo_fechaCreacion() {
        return equipo_fechaCreacion;
    }

    public void setEquipo_fechaCreacion(String equipo_fechaCreacion) {
        this.equipo_fechaCreacion = equipo_fechaCreacion;
    }

    public String getEquipo_estado() {
        return equipo_estado;
    }

    public void setEquipo_estado(String equipo_estado) {
        this.equipo_estado = equipo_estado;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public String getMi_equipo() {
        return mi_equipo;
    }

    public void setMi_equipo(String mi_equipo) {
        this.mi_equipo = mi_equipo;
    }

    public String getCapitan_id() {
        return capitan_id;
    }

    public void setCapitan_id(String capitan_id) {
        this.capitan_id = capitan_id;
    }


    @Override
    public String toString() {
        return "Mequipos{" +
               // "id_equipo=" + id_equipo +
                ", equipo_id='" + equipo_id + '\'' +
                ", torneo_equipo_id='" + torneo_equipo_id + '\'' +
                ", torneo_id='" + torneo_id + '\'' +
                ", equipo_nombre='" + equipo_nombre + '\'' +
                ", equipo_foto='" + equipo_foto + '\'' +
                ", equipo_valoracion='" + equipo_valoracion + '\'' +
                ", equipo_join='" + equipo_join + '\'' +
                ", equipo_fechaCreacion='" + equipo_fechaCreacion + '\'' +
                ", equipo_estado='" + equipo_estado + '\'' +
                ", usuario_nombre='" + usuario_nombre + '\'' +
                ", capitan_id='" + capitan_id + '\'' +
                ", mi_equipo='" + mi_equipo + '\'' +
                '}';
    }
}