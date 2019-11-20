package com.tec.bufeo.capitan.Modelo;

public class MDistrito {

    private String ubigeo_id;
    private String distrito;


    public MDistrito() {
    }

    public MDistrito(String ubigeo_id, String distrito) {
        this.ubigeo_id = ubigeo_id;
        this.distrito = distrito;
    }

    public String getUbigeo_id() {
        return ubigeo_id;
    }

    public void setUbigeo_id(String ubigeo_id) {
        this.ubigeo_id = ubigeo_id;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
}
