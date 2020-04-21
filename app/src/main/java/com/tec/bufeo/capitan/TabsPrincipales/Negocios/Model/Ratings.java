package com.tec.bufeo.capitan.TabsPrincipales.Negocios.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "detalle_ratings")
public class Ratings {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_empresa")
    @SerializedName("id_empresa")
    private String id_empresa;


    @ColumnInfo(name = "conteo1")
    @SerializedName("conteo1")
    private String conteo1;

    @ColumnInfo(name = "conteo2")
    @SerializedName("conteo2")
    private String conteo2;

    @ColumnInfo(name = "conteo3")
    @SerializedName("conteo3")
    private String conteo3;

    @ColumnInfo(name = "conteo4")
    @SerializedName("conteo4")
    private String conteo4;

    @ColumnInfo(name = "conteo5")
    @SerializedName("conteo5")
    private String conteo5;

    @NonNull
    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(@NonNull String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getConteo1() {
        return conteo1;
    }

    public void setConteo1(String conteo1) {
        this.conteo1 = conteo1;
    }

    public String getConteo2() {
        return conteo2;
    }

    public void setConteo2(String conteo2) {
        this.conteo2 = conteo2;
    }

    public String getConteo3() {
        return conteo3;
    }

    public void setConteo3(String conteo3) {
        this.conteo3 = conteo3;
    }

    public String getConteo4() {
        return conteo4;
    }

    public void setConteo4(String conteo4) {
        this.conteo4 = conteo4;
    }

    public String getConteo5() {
        return conteo5;
    }

    public void setConteo5(String conteo5) {
        this.conteo5 = conteo5;
    }

    public Ratings(@NonNull String id_empresa, String conteo1, String conteo2, String conteo3, String conteo4, String conteo5) {
        this.id_empresa = id_empresa;
        this.conteo1 = conteo1;
        this.conteo2 = conteo2;
        this.conteo3 = conteo3;
        this.conteo4 = conteo4;
        this.conteo5 = conteo5;
    }
}
