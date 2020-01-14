package com.tec.bufeo.capitan.Modelo;
public class Usuario {


    private String id_user;
    private String id_person;
    private String user_nickname;
    private String user_password;
    private String user_email;
    private String user_image;
    private String person_name;
    private String person_surname;
    private String person_dni;
    private String person_birth;
    private String person_number_phone;
    private String person_genre;
    private String person_address;
    private String user_num;
    private String user_posicion;
    private String user_habilidad;
    private String ubigeo_id;
    private String token;
    private String token_firebase;




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

    public Usuario(String id_user, String id_person, String user_nickname, String user_email, String user_image, String person_name, String person_surname,
                   String person_dni, String person_birth, String person_number_phone, String person_genre, String person_address, String user_num,
                   String user_posicion, String user_habilidad, String ubigeo_id, String token, String token_firebase) {

        this.id_user=id_user;
        this.id_person=id_person;
        this.user_nickname=user_nickname;
        this.user_email=user_email;
        this.user_image=user_image;
        this.person_name=person_name;
        this.person_surname=person_surname;
        this.person_dni=person_dni;
        this.person_birth=person_birth;
        this.person_number_phone=person_number_phone;
        this.person_genre=person_genre;
        this.person_address=person_address;
        this.user_num=user_num;
        this.user_posicion=user_posicion;
        this.user_habilidad=user_habilidad;
        this.ubigeo_id=ubigeo_id;
        this.token=token;
        this.token_firebase=token_firebase;
    }

    /*public Usuario(String usuario_id, String usuario_nombre, String usuario_foto, String usuario_nacimiento, String usuario_sexo, String usuario_usuario, String usuario_posicion, String usuario_habilidad, String usuario_numFavorito, String usuario_clave, String usuario_email, String usuario_telefono, String rol_id, String usuario_estado, String usuario_dni, String ubigeo_id, String token_firebase ) {
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
    }*/

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



    public String getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(String equipo_id) {
        this.equipo_id = equipo_id;
    }


    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_person() {
        return id_person;
    }

    public void setId_person(String id_person) {
        this.id_person = id_person;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_surname() {
        return person_surname;
    }

    public void setPerson_surname(String person_surname) {
        this.person_surname = person_surname;
    }

    public String getPerson_dni() {
        return person_dni;
    }

    public void setPerson_dni(String person_dni) {
        this.person_dni = person_dni;
    }

    public String getPerson_birth() {
        return person_birth;
    }

    public void setPerson_birth(String person_birth) {
        this.person_birth = person_birth;
    }

    public String getPerson_number_phone() {
        return person_number_phone;
    }

    public void setPerson_number_phone(String person_number_phone) {
        this.person_number_phone = person_number_phone;
    }

    public String getPerson_genre() {
        return person_genre;
    }

    public void setPerson_genre(String person_genre) {
        this.person_genre = person_genre;
    }

    public String getPerson_address() {
        return person_address;
    }

    public void setPerson_address(String person_address) {
        this.person_address = person_address;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public String getUser_posicion() {
        return user_posicion;
    }

    public void setUser_posicion(String user_posicion) {
        this.user_posicion = user_posicion;
    }

    public String getUser_habilidad() {
        return user_habilidad;
    }

    public void setUser_habilidad(String user_habilidad) {
        this.user_habilidad = user_habilidad;
    }

    public String getUbigeo_id() {
        return ubigeo_id;
    }

    public void setUbigeo_id(String ubigeo_id) {
        this.ubigeo_id = ubigeo_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_firebase() {
        return token_firebase;
    }

    public void setToken_firebase(String token_firebase) {
        this.token_firebase = token_firebase;
    }
}
