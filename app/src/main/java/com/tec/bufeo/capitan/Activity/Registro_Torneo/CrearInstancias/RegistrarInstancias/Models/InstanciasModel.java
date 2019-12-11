package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Models;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "instancias")
public class InstanciasModel {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_instancias")
    @SerializedName("id_instancias")
    private String id_instancias;

    @ColumnInfo(name = "nombre_instancias")
    @SerializedName("nombre_instancias")
    private String nombre_instancias;

    @ColumnInfo(name = "tipo_instancias")
    @SerializedName("tipo_instancias")
    private String tipo_instancias;

    @ColumnInfo(name = "id_torneo")
    @SerializedName("id_torneo")
    private String id_torneo;

    @ColumnInfo(name = "estado")
    @SerializedName("estado")
    private String estado;

    @NonNull
    public String getId_instancias() {
        return id_instancias;
    }

    public void setId_instancias(@NonNull String id_instancias) {
        this.id_instancias = id_instancias;
    }

    public String getNombre_instancias() {
        return nombre_instancias;
    }

    public void setNombre_instancias(String nombre_instancias) {
        this.nombre_instancias = nombre_instancias;
    }

    public String getTipo_instancias() {
        return tipo_instancias;
    }

    public void setTipo_instancias(String tipo_instancias) {
        this.tipo_instancias = tipo_instancias;
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
