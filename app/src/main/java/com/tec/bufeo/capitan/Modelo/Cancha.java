package com.tec.bufeo.capitan.Modelo;

public class Cancha {
    public Cancha() {
    }

    private String cancha_id;
    private String cancha_foto;
    private String cancha_nombre;
    private String cancha_dimenciones;
    private String cancha_precioD;
    private String cancha_precioN;
    private String empresas_id;

    public Cancha(String empresas_id) {
        this.empresas_id = empresas_id;
    }

    public String getCancha_id() {
        return cancha_id;
    }

    public String getCancha_foto() {
        return cancha_foto;
    }

    public void setCancha_foto(String cancha_foto) {
        this.cancha_foto = cancha_foto;
    }

    public void setCancha_id(String cancha_id) {
        this.cancha_id = cancha_id;
    }

    public String getCancha_nombre() {
        return cancha_nombre;
    }

    public void setCancha_nombre(String cancha_nombre) {
        this.cancha_nombre = cancha_nombre;
    }

    public String getCancha_dimenciones() {
        return cancha_dimenciones;
    }

    public void setCancha_dimenciones(String cancha_dimenciones) {
        this.cancha_dimenciones = cancha_dimenciones;
    }

    public String getCancha_precioD() {
        return cancha_precioD;
    }

    public String setCancha_precioD(String cancha_precioD) {
        this.cancha_precioD = cancha_precioD;
        return cancha_precioD;
    }

    public String getCancha_precioN() {
        return cancha_precioN;
    }

    public void setCancha_precioN(String cancha_precioN) {
        this.cancha_precioN = cancha_precioN;
    }

    public String getEmpresas_id() {
        return empresas_id;
    }

    public void setEmpresas_id(String empresas_id) {
        this.empresas_id = empresas_id;
    }

    public Cancha(String cancha_id, String cancha_foto,String cancha_nombre, String cancha_dimenciones, String cancha_precioD, String cancha_precioN, String empresas_id) {
        this.cancha_id = cancha_id;
        this.cancha_foto = cancha_foto;
        this.cancha_nombre = cancha_nombre;
        this.cancha_dimenciones = cancha_dimenciones;
        this.cancha_precioD = cancha_precioD;
        this.cancha_precioN = cancha_precioN;
        this.empresas_id = empresas_id;


    }
}
