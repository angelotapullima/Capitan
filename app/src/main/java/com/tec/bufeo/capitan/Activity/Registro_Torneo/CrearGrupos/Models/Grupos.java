package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Models;


import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grupos")
public class Grupos {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_grupo")
    @SerializedName("id_grupo")
    private String id_grupo;

    @ColumnInfo(name = "nombre_grupo")
    @SerializedName("nombre_grupo")
    private String nombre_grupo;

    @ColumnInfo(name = "id_torneo")
    @SerializedName("id_torneo")
    private String id_torneo;

    @ColumnInfo(name = "estado")
    @SerializedName("estado")
    private String estado;


    @NonNull
    public String getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(@NonNull String id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    public String getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(String id_torneo) {
        this.id_torneo = id_torneo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
