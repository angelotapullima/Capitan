package com.tec.bufeo.capitan.Activity.DetallesTorneo.TablaDtorneo.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


public class TablaTorneoItem {



    private String nombre_grupo;
    private String id_grupo;
    private List<TablaTorneoSubItem> tablaTorneoSubItems;



    public TablaTorneoItem(String nombre_grupo,String id_grupo, List<TablaTorneoSubItem> tablaTorneoSubItems) {
        this.nombre_grupo = nombre_grupo;
        this.id_grupo = id_grupo;
        this.tablaTorneoSubItems = tablaTorneoSubItems;
    }

    public String getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(String id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    public List<TablaTorneoSubItem> getTablaTorneoSubItems() {
        return tablaTorneoSubItems;
    }

    public void setTablaTorneoSubItems(List<TablaTorneoSubItem> tablaTorneoSubItems) {
        this.tablaTorneoSubItems = tablaTorneoSubItems;
    }
}
