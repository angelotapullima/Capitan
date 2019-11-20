package com.tec.bufeo.capitan.Modelo;
public class Usuario {

    private String usuario_id;
    private String equipo_id;
    private String usuario_nombre;
    private String usuario_foto;
    private String usuario_nacimiento;
    private String usuario_sexo;
    private String usuario_usuario;
    private String usuario_posicion;
    private String usuario_habilidad;
    private String usuario_numFavorito;
    private String usuario_clave;
    private String usuario_email;
    private String usuario_telefono;
    private String rol_id;
    private String usuario_estado;
    private String usuario_dni;
    private String ubigeo_id;
    private String token_firebase;

    public Usuario() {
    }

    public Usuario(String equipo_id) {
        this.equipo_id = equipo_id;
    }

    public Usuario(String usuario_id, String equipo_id) {
        this.usuario_id = usuario_id;
        this.equipo_id = equipo_id;
    }

    public Usuario(String usuario_id, String usuario_usuario, String usuario_nombre, String usuario_email, String usuario_habilidad, String usuario_posicion, String usuario_numFavorito, String usuario_foto, String ubigeo_id , String token_firebase) {
        this.usuario_id = usuario_id;
        this.usuario_nombre = usuario_nombre;
        this.usuario_email = usuario_email;
        this.usuario_email = usuario_habilidad;
        this.usuario_email = getUsuario_posicion();
        this.usuario_email = getUsuario_numFavorito();
        this.usuario_foto = usuario_foto;
        this.usuario_usuario = usuario_usuario;
        this.ubigeo_id = ubigeo_id;
        this.token_firebase = token_firebase;
    }

    public Usuario(String usuario_id, String usuario_nombre, String usuario_foto, String usuario_nacimiento, String usuario_sexo, String usuario_usuario, String usuario_posicion, String usuario_habilidad, String usuario_numFavorito, String usuario_clave, String usuario_email, String usuario_telefono, String rol_id, String usuario_estado, String usuario_dni, String ubigeo_id, String token_firebase ) {
        this.usuario_id = usuario_id;
        this.usuario_nombre = usuario_nombre;
        this.usuario_foto = usuario_foto;
        this.usuario_nacimiento = usuario_nacimiento;
        this.usuario_sexo = usuario_sexo;
        this.usuario_usuario = usuario_usuario;
        this.usuario_posicion = usuario_posicion;
        this.usuario_habilidad = usuario_habilidad;
        this.usuario_numFavorito = usuario_numFavorito;
        this.usuario_clave = usuario_clave;
        this.usuario_email = usuario_email;
        this.usuario_telefono = usuario_telefono;
        this.rol_id = rol_id;
        this.usuario_estado = usuario_estado;
        this.usuario_dni = usuario_dni;
        this.ubigeo_id = ubigeo_id;
        this.token_firebase = token_firebase;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public String getUsuario_email() {
        return usuario_email;
    }

    public void setUsuario_email(String usuario_email) {
        this.usuario_email = usuario_email;
    }

    public String getUsuario_foto() {
        return usuario_foto;
    }

    public void setUsuario_foto(String usuario_foto) {
        this.usuario_foto = usuario_foto;
    }

    public String getUsuario_nacimiento() {
        return usuario_nacimiento;
    }

    public void setUsuario_nacimiento(String usuario_nacimiento) {
        this.usuario_nacimiento = usuario_nacimiento;
    }

    public String getUsuario_sexo() {
        return usuario_sexo;
    }

    public void setUsuario_sexo(String usuario_sexo) {
        this.usuario_sexo = usuario_sexo;
    }

    public String getUsuario_usuario() {
        return usuario_usuario;
    }

    public void setUsuario_usuario(String usuario_usuario) {
        this.usuario_usuario = usuario_usuario;
    }

    public String getUsuario_posicion() {
        return usuario_posicion;
    }

    public void setUsuario_posicion(String usuario_posicion) {
        this.usuario_posicion = usuario_posicion;
    }

    public String getUsuario_habilidad() {
        return usuario_habilidad;
    }

    public void setUsuario_habilidad(String usuario_habilidad) {
        this.usuario_habilidad = usuario_habilidad;
    }

    public String getUsuario_numFavorito() {
        return usuario_numFavorito;
    }

    public void setUsuario_numFavorito(String usuario_numFavorito) {
        this.usuario_numFavorito = usuario_numFavorito;
    }

    public String getUsuario_clave() {
        return usuario_clave;
    }

    public void setUsuario_clave(String usuario_clave) {
        this.usuario_clave = usuario_clave;
    }

    public String getUsuario_telefono() {
        return usuario_telefono;
    }

    public void setUsuario_telefono(String usuario_telefono) {
        this.usuario_telefono = usuario_telefono;
    }

    public String getRol_id() {
        return rol_id;
    }

    public void setRol_id(String rol_id) {
        this.rol_id = rol_id;
    }

    public String getUsuario_estado() {
        return usuario_estado;
    }

    public void setUsuario_estado(String usuario_estado) {
        this.usuario_estado = usuario_estado;
    }

    public String getUsuario_dni() {
        return usuario_dni;
    }

    public void setUsuario_dni(String usuario_dni) {
        this.usuario_dni = usuario_dni;
    }

    public String getUbigeo_id() {
        return ubigeo_id;
    }

    public void setUbigeo_id(String ubigeo_id) {
        this.ubigeo_id = ubigeo_id;
    }

    public String getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(String equipo_id) {
        this.equipo_id = equipo_id;
    }


    public String getToken_firebase() {
        return token_firebase;
    }

    public void setToken_firebase(String token_firebase) {
        this.token_firebase = token_firebase;
    }
}
