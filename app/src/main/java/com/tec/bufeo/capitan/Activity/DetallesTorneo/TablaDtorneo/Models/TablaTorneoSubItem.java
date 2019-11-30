package com.tec.bufeo.capitan.Activity.DetallesTorneo.TablaDtorneo.Models;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;



public class TablaTorneoSubItem {

    private String equipo_id;
    private String equipo_nombre;
    private String part_j;
    private String part_g;
    private String part_e;
    private String part_p;
    private String gf;
    private String gc;
    private String puntos;
    private String diferencia_goles;
    private String posicion_lista;

    public TablaTorneoSubItem() {
    }

    public TablaTorneoSubItem(String equipo_id, String equipo_nombre, String part_j, String part_g, String part_e, String part_p, String gf, String gc, String puntos) {
        this.equipo_id = equipo_id;
        this.equipo_nombre = equipo_nombre;
        this.part_j = part_j;
        this.part_g = part_g;
        this.part_e = part_e;
        this.part_p = part_p;
        this.gf = gf;
        this.gc = gc;
        this.puntos = puntos;
    }

    public TablaTorneoSubItem(String equipo_nombre, String posicion_lista) {
        this.equipo_nombre=equipo_nombre;
        this.posicion_lista=posicion_lista;
    }

    public String getPosicion_lista() {
        return posicion_lista;
    }

    public void setPosicion_lista(String posicion_lista) {
        this.posicion_lista = posicion_lista;
    }
    public String getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(String equipo_id) {
        this.equipo_id = equipo_id;
    }

    public String getEquipo_nombre() {
        return equipo_nombre;
    }

    public void setEquipo_nombre(String equipo_nombre) {
        this.equipo_nombre = equipo_nombre;
    }

    public String getPart_j() {
        return part_j;
    }

    public void setPart_j(String part_j) {
        this.part_j = part_j;
    }

    public String getPart_g() {
        return part_g;
    }

    public void setPart_g(String part_g) {
        this.part_g = part_g;
    }

    public String getPart_e() {
        return part_e;
    }

    public void setPart_e(String part_e) {
        this.part_e = part_e;
    }

    public String getPart_p() {
        return part_p;
    }

    public void setPart_p(String part_p) {
        this.part_p = part_p;
    }

    public String getGf() {
        return gf;
    }

    public void setGf(String gf) {
        this.gf = gf;
    }

    public String getGc() {
        return gc;
    }

    public void setGc(String gc) {
        this.gc = gc;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getDiferencia_goles() {
        return diferencia_goles;
    }

    public void setDiferencia_goles(String diferencia_goles) {
        this.diferencia_goles = diferencia_goles;
    }
}
