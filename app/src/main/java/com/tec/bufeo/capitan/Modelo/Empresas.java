package com.tec.bufeo.capitan.Modelo;

public class Empresas {

    private String empresas_id;
    private String usuario_id;
    private String empresas_nombre;
    private String empresas_foto;
    private String empresas_descripcion;
    private String empresas_direccion;
    private String empresas_valoracion;
    private String empresas_promedio;
    private String empresas_conteo;
    private String empresas_estado;
    private String ubigeo_id;
    private String empresas_horario;
    private String empresas_telefono;
    private String empresa_cancha_hora;
    private String empresa_cancha_fecha;
    private String empresa_cancha_fecha_reporte;
    private String cancha_precioD;
    private String cancha_precioN;

    public Empresas() {

    }

    public Empresas( String usuario_id,String empresas_id, String empresas_valoracion) {
        this.usuario_id = usuario_id;
        this.empresas_id = empresas_id;
        this.empresas_valoracion = empresas_valoracion;
    }

   /*  public Empresas(String empresas_id, String empresa_cancha_fecha) {
        this.empresas_id = empresas_id;
        this.empresa_cancha_fecha = empresa_cancha_fecha;
    }*/

    public Empresas(String empresas_id) {
        this.empresas_id = empresas_id;
    }

    public Empresas(String empresas_id, String usuario_id) {
        this.empresas_id = empresas_id;
        this.usuario_id = usuario_id;
    }

    public Empresas(String empresas_id, String usuario_id, String empresas_nombre, String empresas_foto, String empresas_descripcion, String empresas_direccion, String empresas_valoracion, String empresas_estado, String ubigeo_id, String empresas_horario, String empresas_telefono, String empresa_cancha_hora, String empresa_cancha_fecha, String empresa_cancha_fecha_reporte) {
        this.empresas_id = empresas_id;
        this.usuario_id = usuario_id;
        this.empresas_nombre = empresas_nombre;
        this.empresas_foto = empresas_foto;
        this.empresas_descripcion = empresas_descripcion;
        this.empresas_direccion = empresas_direccion;
        this.empresas_valoracion = empresas_valoracion;
        this.empresas_estado = empresas_estado;
        this.ubigeo_id = ubigeo_id;
        this.empresas_horario = empresas_horario;
        this.empresas_telefono = empresas_telefono;
        this.empresa_cancha_hora = empresa_cancha_hora;
        this.empresa_cancha_fecha = empresa_cancha_fecha;
        this.empresa_cancha_fecha_reporte = empresa_cancha_fecha_reporte;
    }


    public String getEmpresas_id() {
        return empresas_id;
    }

    public void setEmpresas_id(String empresas_id) {
        this.empresas_id = empresas_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getEmpresas_nombre() {
        return empresas_nombre;
    }

    public void setEmpresas_nombre(String empresas_nombre) {
        this.empresas_nombre = empresas_nombre;
    }

    public String getEmpresas_foto() {
        return empresas_foto;
    }

    public void setEmpresas_foto(String empresas_foto) {
        this.empresas_foto = empresas_foto;
    }

    public String getEmpresas_descripcion() {
        return empresas_descripcion;
    }

    public void setEmpresas_descripcion(String empresas_descripcion) {
        this.empresas_descripcion = empresas_descripcion;
    }

    public String getEmpresas_direccion() {
        return empresas_direccion;
    }

    public void setEmpresas_direccion(String empresas_direccion) {
        this.empresas_direccion = empresas_direccion;
    }

    public String getEmpresas_valoracion() {
        return empresas_valoracion;
    }

    public void setEmpresas_valoracion(String empresas_valoracion) {
        this.empresas_valoracion = empresas_valoracion;
    }

    public String getEmpresas_estado() {
        return empresas_estado;
    }

    public void setEmpresas_estado(String empresas_estado) {
        this.empresas_estado = empresas_estado;
    }

    public String getUbigeo_id() {
        return ubigeo_id;
    }

    public void setUbigeo_id(String ubigeo_id) {
        this.ubigeo_id = ubigeo_id;
    }

    public String getEmpresas_horario() {
        return empresas_horario;
    }

    public void setEmpresas_horario(String empresas_horario) {
        this.empresas_horario = empresas_horario;
    }

    public String getEmpresas_telefono() {
        return empresas_telefono;
    }

    public void setEmpresas_telefono(String empresas_telefono) {
        this.empresas_telefono = empresas_telefono;
    }

    public String getEmpresa_cancha_hora() {
        return empresa_cancha_hora;
    }

    public void setEmpresa_cancha_hora(String empresa_cancha_hora) {
        this.empresa_cancha_hora = empresa_cancha_hora;
    }

    public String getEmpresa_cancha_fecha() {
        return empresa_cancha_fecha;
    }

    public void setEmpresa_cancha_fecha(String empresa_cancha_fecha) {
        this.empresa_cancha_fecha = empresa_cancha_fecha;
    }

    public String getEmpresa_cancha_fecha_reporte() {
        return empresa_cancha_fecha_reporte;
    }

    public void setEmpresa_cancha_fecha_reporte(String empresa_cancha_fecha_reporte) {
        this.empresa_cancha_fecha_reporte = empresa_cancha_fecha_reporte;
    }

    public String getEmpresas_promedio() {
        return empresas_promedio;
    }

    public void setEmpresas_promedio(String empresas_promedio) {
        this.empresas_promedio = empresas_promedio;
    }

    public String getEmpresas_conteo() {
        return empresas_conteo;
    }

    public void setEmpresas_conteo(String empresas_conteo) {
        this.empresas_conteo = empresas_conteo;
    }

    public String getCancha_precioD() {
        return cancha_precioD;
    }

    public void setCancha_precioD(String cancha_precioD) {
        this.cancha_precioD = cancha_precioD;
    }

    public String getCancha_precioN() {
        return cancha_precioN;
    }

    public void setCancha_precioN(String cancha_precioN) {
        this.cancha_precioN = cancha_precioN;
    }
}


