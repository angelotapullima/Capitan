package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.LIstaInstancias;

public class PartidosInstancias {
    private String id_torneo_partido;
    private String id_equipo_local;
    private String nombre_equipo_local;
    private String foto_equipo_local;
    private String id_equipo_visita;
    private String nombre_equipo_visita;
    private String foto_equipo_visita;
    private String partido_fecha;
    private String partido_hora;
    private String partido_estado;

    public PartidosInstancias(String id_torneo_partido, String id_equipo_local, String nombre_equipo_local, String foto_equipo_local, String id_equipo_visita, String nombre_equipo_visita, String foto_equipo_visita, String partido_fecha, String partido_hora, String partido_estado) {
        this.id_torneo_partido = id_torneo_partido;
        this.id_equipo_local = id_equipo_local;
        this.nombre_equipo_local = nombre_equipo_local;
        this.foto_equipo_local = foto_equipo_local;
        this.id_equipo_visita = id_equipo_visita;
        this.nombre_equipo_visita = nombre_equipo_visita;
        this.foto_equipo_visita = foto_equipo_visita;
        this.partido_fecha = partido_fecha;
        this.partido_hora = partido_hora;
        this.partido_estado = partido_estado;
    }

    public String getId_torneo_partido() {
        return id_torneo_partido;
    }

    public void setId_torneo_partido(String id_torneo_partido) {
        this.id_torneo_partido = id_torneo_partido;
    }

    public String getId_equipo_local() {
        return id_equipo_local;
    }

    public void setId_equipo_local(String id_equipo_local) {
        this.id_equipo_local = id_equipo_local;
    }

    public String getNombre_equipo_local() {
        return nombre_equipo_local;
    }

    public void setNombre_equipo_local(String nombre_equipo_local) {
        this.nombre_equipo_local = nombre_equipo_local;
    }

    public String getFoto_equipo_local() {
        return foto_equipo_local;
    }

    public void setFoto_equipo_local(String foto_equipo_local) {
        this.foto_equipo_local = foto_equipo_local;
    }

    public String getId_equipo_visita() {
        return id_equipo_visita;
    }

    public void setId_equipo_visita(String id_equipo_visita) {
        this.id_equipo_visita = id_equipo_visita;
    }

    public String getNombre_equipo_visita() {
        return nombre_equipo_visita;
    }

    public void setNombre_equipo_visita(String nombre_equipo_visita) {
        this.nombre_equipo_visita = nombre_equipo_visita;
    }

    public String getFoto_equipo_visita() {
        return foto_equipo_visita;
    }

    public void setFoto_equipo_visita(String foto_equipo_visita) {
        this.foto_equipo_visita = foto_equipo_visita;
    }

    public String getPartido_fecha() {
        return partido_fecha;
    }

    public void setPartido_fecha(String partido_fecha) {
        this.partido_fecha = partido_fecha;
    }

    public String getPartido_hora() {
        return partido_hora;
    }

    public void setPartido_hora(String partido_hora) {
        this.partido_hora = partido_hora;
    }

    public String getPartido_estado() {
        return partido_estado;
    }

    public void setPartido_estado(String partido_estado) {
        this.partido_estado = partido_estado;
    }
}