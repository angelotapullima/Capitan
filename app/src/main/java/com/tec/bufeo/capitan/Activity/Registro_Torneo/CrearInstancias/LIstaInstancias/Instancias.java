package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.LIstaInstancias;

import java.util.List;

public class Instancias {

    private String id_instancia;
    private String nombre_instancia;
    private List<PartidosInstancias> listPartidos;

    public Instancias(String id_instancia, String nombre_instancia, List<PartidosInstancias> listPartidos) {
        this.id_instancia = id_instancia;
        this.nombre_instancia = nombre_instancia;
        this.listPartidos = listPartidos;
    }

    public String getId_instancia() {
        return id_instancia;
    }

    public void setId_instancia(String id_instancia) {
        this.id_instancia = id_instancia;
    }

    public String getNombre_instancia() {
        return nombre_instancia;
    }

    public void setNombre_instancia(String nombre_instancia) {
        this.nombre_instancia = nombre_instancia;
    }

    public List<PartidosInstancias> getListPartidos() {
        return listPartidos;
    }

    public void setListPartidos(List<PartidosInstancias> listPartidos) {
        this.listPartidos = listPartidos;
    }
}
