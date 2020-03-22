package com.tec.bufeo.capitan.Modelo;



public class Equipo {
    public Equipo() {
    }

    public Equipo(String usuario_id) {
        this.usuario_id = usuario_id;
    }

   private String equipo_id;
    private String usuario_id;
    private String torneo_equipo_id;
    private String torneo_id;
    private String equipo_nombre;
    private String equipo_foto;
    private String equipo_valoracion;
    private String equipo_join;
    private String equipo_fechaCreacion;
    private String equipo_estado;
    private String usuario_nombre;

    public Equipo(String equipo_id, String torneo_id) {
        this.equipo_id = equipo_id;
        this.torneo_id = torneo_id;
    }

    public Equipo(String equipo_id, String usuario_id, String torneo_equipo_id, String torneo_id, String equipo_nombre, String equipo_foto, String equipo_valoracion, String equipo_join, String equipo_fechaCreacion, String equipo_estado, String usuario_nombre) {
        this.equipo_id = equipo_id;
        this.usuario_id = usuario_id;
        this.torneo_equipo_id = torneo_equipo_id;
        this.torneo_id = torneo_id;
        this.equipo_nombre = equipo_nombre;
        this.equipo_foto = equipo_foto;
        this.equipo_valoracion = equipo_valoracion;
        this.equipo_join = equipo_join;
        this.equipo_fechaCreacion = equipo_fechaCreacion;
        this.equipo_estado = equipo_estado;
        this.usuario_nombre = usuario_nombre;
    }

    public String getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(String equipo_id) {
        this.equipo_id = equipo_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getEquipo_nombre() {
        return equipo_nombre;
    }

    public void setEquipo_nombre(String equipo_nombre) {
        this.equipo_nombre = equipo_nombre;
    }

    public String getEquipo_foto() {
        return equipo_foto;
    }

    public void setEquipo_foto(String equipo_foto) {
        this.equipo_foto = equipo_foto;
    }

    public String getEquipo_valoracion() {
        return equipo_valoracion;
    }

    public void setEquipo_valoracion(String equipo_valoracion) {
        this.equipo_valoracion = equipo_valoracion;
    }

    public String getEquipo_fechaCreacion() {
        return equipo_fechaCreacion;
    }

    public void setEquipo_fechaCreacion(String equipo_fechaCreacion) {
        this.equipo_fechaCreacion = equipo_fechaCreacion;
    }

    public String getEquipo_estado() {
        return equipo_estado;
    }

    public void setEquipo_estado(String equipo_estado) {
        this.equipo_estado = equipo_estado;
    }

    public String getTorneo_equipo_id() {
        return torneo_equipo_id;
    }

    public void setTorneo_equipo_id(String torneo_equipo_id) {
        this.torneo_equipo_id = torneo_equipo_id;
    }

    public String getTorneo_id() {
        return torneo_id;
    }

    public void setTorneo_id(String torneo_id) {
        this.torneo_id = torneo_id;
    }

    public String getEquipo_join() {
        return equipo_join;
    }

    public void setEquipo_join(String equipo_join) {
        this.equipo_join = equipo_join;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }
}
