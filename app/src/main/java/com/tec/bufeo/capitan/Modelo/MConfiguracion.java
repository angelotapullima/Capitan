package com.tec.bufeo.capitan.Modelo;

public class MConfiguracion {

    public MConfiguracion() {
    }

    public MConfiguracion(String titulo, String descripcion, int icono) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.icono = icono;
    }

    private String titulo;
    private String descripcion;
    private int icono;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }
}
