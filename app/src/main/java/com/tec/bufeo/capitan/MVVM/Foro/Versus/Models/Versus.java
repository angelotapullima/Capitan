package com.tec.bufeo.capitan.MVVM.Foro.Versus.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Versus")
public class Versus {


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "versus_id")
    @SerializedName("versus_id")
    private int versus_id;

    @ColumnInfo(name = "versus_equipo1")
    @SerializedName("versus_equipo1")
    private String versus_equipo1;

    @ColumnInfo(name = "versus_foto_equipo1")
    @SerializedName("versus_foto_equipo1")
    private String versus_foto_equipo1;

    @ColumnInfo(name = "versus_jugador1a")
    @SerializedName("versus_jugador1a")
    private String versus_jugador1a;

    @ColumnInfo(name = "versus_jugador1b")
    @SerializedName("versus_jugador1b")
    private String versus_jugador1b;

    @ColumnInfo(name = "versus_jugador1c")
    @SerializedName("versus_jugador1c")
    private String versus_jugador1c;

    @ColumnInfo(name = "versus_jugador1d")
    @SerializedName("versus_jugador1d")
    private String versus_jugador1d;

    @ColumnInfo(name = "versus_jugador1e")
    @SerializedName("versus_jugador1e")
    private String versus_jugador1e;

    @ColumnInfo(name = "versus_jugador1f")
    @SerializedName("versus_jugador1f")
    private String versus_jugador1f;

    @ColumnInfo(name = "versus_jugador1g")
    @SerializedName("versus_jugador1g")
    private String versus_jugador1g;

    @ColumnInfo(name = "versus_jugador1h")
    @SerializedName("versus_jugador1h")
    private String versus_jugador1h;



    @ColumnInfo(name = "versus_equipo2")
    @SerializedName("versus_equipo2")
    private String versus_equipo2;

    @ColumnInfo(name = "versus_foto_equipo2")
    @SerializedName("versus_foto_equipo2")
    private String versus_foto_equipo2;

    @ColumnInfo(name = "versus_jugador2a")
    @SerializedName("versus_jugador2a")
    private String versus_jugador2a;

    @ColumnInfo(name = "versus_jugador2b")
    @SerializedName("versus_jugador2b")
    private String versus_jugador2b;

    @ColumnInfo(name = "versus_jugador2c")
    @SerializedName("versus_jugador2c")
    private String versus_jugador2c;

    @ColumnInfo(name = "versus_jugador2d")
    @SerializedName("versus_jugador2d")
    private String versus_jugador2d;

    @ColumnInfo(name = "versus_jugador2e")
    @SerializedName("versus_jugador2e")
    private String versus_jugador2e;

    @ColumnInfo(name = "versus_jugador2f")
    @SerializedName("versus_jugador2f")
    private String versus_jugador2f;

    @ColumnInfo(name = "versus_jugador2g")
    @SerializedName("versus_jugador2g")
    private String versus_jugador2g;

    @ColumnInfo(name = "versus_jugador2h")
    @SerializedName("versus_jugador2h")
    private String versus_jugador2h;

    @ColumnInfo(name = "versus_lugar")
    @SerializedName("versus_lugar")
    private String versus_lugar;

    @ColumnInfo(name = "versus_fecha")
    @SerializedName("versus_fecha")
    private String versus_fecha;

    public int getVersus_id() {
        return versus_id;
    }

    public void setVersus_id(int versus_id) {
        this.versus_id = versus_id;
    }

    public String getVersus_equipo1() {
        return versus_equipo1;
    }

    public void setVersus_equipo1(String versus_equipo1) {
        this.versus_equipo1 = versus_equipo1;
    }

    public String getVersus_foto_equipo1() {
        return versus_foto_equipo1;
    }

    public void setVersus_foto_equipo1(String versus_foto_equipo1) {
        this.versus_foto_equipo1 = versus_foto_equipo1;
    }

    public String getVersus_jugador1a() {
        return versus_jugador1a;
    }

    public void setVersus_jugador1a(String versus_jugador1a) {
        this.versus_jugador1a = versus_jugador1a;
    }

    public String getVersus_jugador1b() {
        return versus_jugador1b;
    }

    public void setVersus_jugador1b(String versus_jugador1b) {
        this.versus_jugador1b = versus_jugador1b;
    }

    public String getVersus_jugador1c() {
        return versus_jugador1c;
    }

    public void setVersus_jugador1c(String versus_jugador1c) {
        this.versus_jugador1c = versus_jugador1c;
    }

    public String getVersus_jugador1d() {
        return versus_jugador1d;
    }

    public void setVersus_jugador1d(String versus_jugador1d) {
        this.versus_jugador1d = versus_jugador1d;
    }

    public String getVersus_jugador1e() {
        return versus_jugador1e;
    }

    public void setVersus_jugador1e(String versus_jugador1e) {
        this.versus_jugador1e = versus_jugador1e;
    }

    public String getVersus_jugador1f() {
        return versus_jugador1f;
    }

    public void setVersus_jugador1f(String versus_jugador1f) {
        this.versus_jugador1f = versus_jugador1f;
    }

    public String getVersus_jugador1g() {
        return versus_jugador1g;
    }

    public void setVersus_jugador1g(String versus_jugador1g) {
        this.versus_jugador1g = versus_jugador1g;
    }

    public String getVersus_jugador1h() {
        return versus_jugador1h;
    }

    public void setVersus_jugador1h(String versus_jugador1h) {
        this.versus_jugador1h = versus_jugador1h;
    }

    public String getVersus_equipo2() {
        return versus_equipo2;
    }

    public void setVersus_equipo2(String versus_equipo2) {
        this.versus_equipo2 = versus_equipo2;
    }

    public String getVersus_foto_equipo2() {
        return versus_foto_equipo2;
    }

    public void setVersus_foto_equipo2(String versus_foto_equipo2) {
        this.versus_foto_equipo2 = versus_foto_equipo2;
    }

    public String getVersus_jugador2a() {
        return versus_jugador2a;
    }

    public void setVersus_jugador2a(String versus_jugador2a) {
        this.versus_jugador2a = versus_jugador2a;
    }

    public String getVersus_jugador2b() {
        return versus_jugador2b;
    }

    public void setVersus_jugador2b(String versus_jugador2b) {
        this.versus_jugador2b = versus_jugador2b;
    }

    public String getVersus_jugador2c() {
        return versus_jugador2c;
    }

    public void setVersus_jugador2c(String versus_jugador2c) {
        this.versus_jugador2c = versus_jugador2c;
    }

    public String getVersus_jugador2d() {
        return versus_jugador2d;
    }

    public void setVersus_jugador2d(String versus_jugador2d) {
        this.versus_jugador2d = versus_jugador2d;
    }

    public String getVersus_jugador2e() {
        return versus_jugador2e;
    }

    public void setVersus_jugador2e(String versus_jugador2e) {
        this.versus_jugador2e = versus_jugador2e;
    }

    public String getVersus_jugador2f() {
        return versus_jugador2f;
    }

    public void setVersus_jugador2f(String versus_jugador2f) {
        this.versus_jugador2f = versus_jugador2f;
    }

    public String getVersus_jugador2g() {
        return versus_jugador2g;
    }

    public void setVersus_jugador2g(String versus_jugador2g) {
        this.versus_jugador2g = versus_jugador2g;
    }

    public String getVersus_jugador2h() {
        return versus_jugador2h;
    }

    public void setVersus_jugador2h(String versus_jugador2h) {
        this.versus_jugador2h = versus_jugador2h;
    }

    public String getVersus_lugar() {
        return versus_lugar;
    }

    public void setVersus_lugar(String versus_lugar) {
        this.versus_lugar = versus_lugar;
    }

    public String getVersus_fecha() {
        return versus_fecha;
    }

    public void setVersus_fecha(String versus_fecha) {
        this.versus_fecha = versus_fecha;
    }

    @Override
    public String toString() {
        return "Versus{" +
                "versus_id=" + versus_id +
                ", versus_equipo1='" + versus_equipo1 + '\'' +
                ", versus_foto_equipo1='" + versus_foto_equipo1 + '\'' +
                ", versus_jugador1a='" + versus_jugador1a + '\'' +
                ", versus_jugador1b='" + versus_jugador1b + '\'' +
                ", versus_jugador1c='" + versus_jugador1c + '\'' +
                ", versus_jugador1d='" + versus_jugador1d + '\'' +
                ", versus_jugador1e='" + versus_jugador1e + '\'' +
                ", versus_jugador1f='" + versus_jugador1f + '\'' +
                ", versus_jugador1g='" + versus_jugador1g + '\'' +
                ", versus_jugador1h='" + versus_jugador1h + '\'' +
                ", versus_equipo2='" + versus_equipo2 + '\'' +
                ", versus_foto_equipo2='" + versus_foto_equipo2 + '\'' +
                ", versus_jugador2a='" + versus_jugador2a + '\'' +
                ", versus_jugador2b='" + versus_jugador2b + '\'' +
                ", versus_jugador2c='" + versus_jugador2c + '\'' +
                ", versus_jugador2d='" + versus_jugador2d + '\'' +
                ", versus_jugador2e='" + versus_jugador2e + '\'' +
                ", versus_jugador2f='" + versus_jugador2f + '\'' +
                ", versus_jugador2g='" + versus_jugador2g + '\'' +
                ", versus_jugador2h='" + versus_jugador2h + '\'' +
                ", versus_lugar='" + versus_lugar + '\'' +
                ", versus_fecha='" + versus_fecha + '\'' +
                '}';
    }
}
