package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "mis_chanchas")
public class Chanchas {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_chancha")
    @SerializedName("id_chancha")
    private String id_chancha;

    @ColumnInfo(name = "id_equipo")
    @SerializedName("id_equipo")
    private String id_equipo;

    @ColumnInfo(name = "equipo_nombre")
    @SerializedName("equipo_nombre")
    private String equipo_nombre;

    @ColumnInfo(name = "nombre_chancha")
    @SerializedName("nombre_chancha")
    private String nombre_chancha;


    @ColumnInfo(name = "monto_final")
    @SerializedName("monto_final")
    private String monto_final;


    @ColumnInfo(name = "monto_actual")
    @SerializedName("monto_actual")
    private String monto_actual;


    @TypeConverters(TimestampConverter.class)
    @ColumnInfo(name = "chancha_fecha")
    @SerializedName("chancha_fecha")
    private String chancha_fecha;

    @TypeConverters(DataConverterChanchas.class)
    @ColumnInfo(name = "chancha_detalle")
    @SerializedName("chancha_detalle")
    private List<DetalleChancha> chancha_detalle;

    @NonNull
    public String getId_chancha() {
        return id_chancha;
    }

    public void setId_chancha(@NonNull String id_chancha) {
        this.id_chancha = id_chancha;
    }

    public String getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(String id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getEquipo_nombre() {
        return equipo_nombre;
    }

    public void setEquipo_nombre(String equipo_nombre) {
        this.equipo_nombre = equipo_nombre;
    }

    public String getNombre_chancha() {
        return nombre_chancha;
    }

    public void setNombre_chancha(String nombre_chancha) {
        this.nombre_chancha = nombre_chancha;
    }

    public String getMonto_final() {
        return monto_final;
    }

    public void setMonto_final(String monto_final) {
        this.monto_final = monto_final;
    }

    public String getChancha_fecha() {
        return chancha_fecha;
    }

    public void setChancha_fecha(String chancha_fecha) {
        this.chancha_fecha = chancha_fecha;
    }

    public List<DetalleChancha> getChancha_detalle() {
        return chancha_detalle;
    }

    public void setChancha_detalle(List<DetalleChancha> chancha_detalle) {
        this.chancha_detalle = chancha_detalle;
    }

    public String getMonto_actual() {
        return monto_actual;
    }

    public void setMonto_actual(String monto_actual) {
        this.monto_actual = monto_actual;
    }
}