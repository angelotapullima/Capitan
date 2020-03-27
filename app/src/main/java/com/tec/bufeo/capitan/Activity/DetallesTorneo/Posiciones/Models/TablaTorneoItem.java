package com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Models;

import java.util.List;


public class TablaTorneoItem {



    private String nombre_grupo;
    private String id_grupo;
    private String condicion;
    private List<TablaTorneoSubItem> tablaTorneoSubItems;



    public TablaTorneoItem(String nombre_grupo,String id_grupo,String condicion, List<TablaTorneoSubItem> tablaTorneoSubItems) {
        this.nombre_grupo = nombre_grupo;
        this.id_grupo = id_grupo;
        this.condicion = condicion;
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



    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
    public List<TablaTorneoSubItem> getTablaTorneoSubItems() {
        return tablaTorneoSubItems;
    }

    public void setTablaTorneoSubItems(List<TablaTorneoSubItem> tablaTorneoSubItems) {
        this.tablaTorneoSubItems = tablaTorneoSubItems;
    }
}
