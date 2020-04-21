package com.tec.bufeo.capitan.TabsPrincipales.Negocios.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "galeria_empresa")
public class Galeria {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_galeria")
    @SerializedName("id_galeria")
    private String id_galeria;


    @ColumnInfo(name = "id_empresas")
    @SerializedName("id_empresas")
    private String id_empresas;


    @ColumnInfo(name = "galeria_foto")
    @SerializedName("galeria_foto")
    private String galeria_foto;

    @NonNull
    public String getId_galeria() {
        return id_galeria;
    }

    public void setId_galeria(@NonNull String id_galeria) {
        this.id_galeria = id_galeria;
    }

    public String getId_empresas() {
        return id_empresas;
    }

    public void setId_empresas(String id_empresas) {
        this.id_empresas = id_empresas;
    }

    public String getGaleria_foto() {
        return galeria_foto;
    }

    public void setGaleria_foto(String galeria_foto) {
        this.galeria_foto = galeria_foto;
    }




    public Galeria(@NonNull String id_galeria, String id_empresas, String galeria_foto) {
        this.id_galeria = id_galeria;
        this.id_empresas = id_empresas;
        this.galeria_foto = galeria_foto;
    }
}
