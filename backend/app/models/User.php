<?php
class User{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }
    public function readAllrolenotfree(){
        try{
            $sql = 'select * from role where id_role <> 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    public function selectuser($id){
        try{
            $sql = 'select * from user u inner join person p on u.id_person = p.id_person where u.id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function selectuser_id_auth($id){
        try{
            $sql = 'select * from user u inner join person p on u.id_person = p.id_person where u.id_auth = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function actualizar_token($token,$id){
        try {
            $sql = 'update user set user_token = ? where id_user= ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $token,$id
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    public function readAll(){
        try {
            $sql = 'select * from user u inner join person p on u.id_person = p.id_person inner join role r on u.id_role = r.id_role';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(),get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function readAll_active(){
        try {
            $sql = 'select * from user u inner join person p on u.id_person = p.id_person inner join role r on u.id_role = r.id_role where user_status = 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(),get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    //Listar Toda La Info Sobre Usuarios
    public function listAll(){
        try{
            $sql = 'select * from user u inner join role r on u.id_role = r.id_role';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }

    public function getDni($id){
        try{
            $sql = 'select person_dni from user u inner join person p on u.id_person = p.id_person where u.id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 0;
        }
        return $result->person_dni;
    }

    public function validateUser($nickname){
        try{
            $sql = 'select * from user u where user_nickname = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$nickname]);
            $re = $stm->fetchAll();
            if(count($re) > 0){
                $result = true;
            } else {
                $result = false;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }

    public function selectNickname($id){
        try{
            $sql = 'select user_nickname from user u where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $re = $stm->fetch();
            $result = $re->user_nickname;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = "";
        }
        return $result;
    }
    //Listar Un Unico Usuario por ID
    public function list($id){
        try{
            $sql = 'select * from user u inner join role r on u.id_role = r.id_role where u.id_user = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    //Guardar o Editar Informacion de Usuarios
    public function save($model){
        try {
            $fecha = date("Y-m-d H:i:s");
            if(empty($model->id_user)){
                $sql = 'insert into user(
                    id_person, id_role,ubigeo_id, user_nickname, user_password, user_email, user_image,user_posicion,user_habilidad,user_num, user_status, user_created_at, user_modified_at 
                    ) values(?,?,?,?,?,?,?,?,?,?,?,?,?)';
                $stm = $this->pdo->prepare($sql);
                $stm->execute([
                    //$model->id_auth,
                    $model->id_person,
                    $model->id_role,
                    $model->ubigeo_id,
                    $model->user_nickname,
                    $model->user_password,
                    $model->user_email,
                    $model->user_image,
                    $model->user_posicion,
                    $model->user_habilidad,
                    $model->user_num,
                    1,
                    $fecha,
                    $fecha,
                ]);

            } else {
                if(empty($model->id_person)){
                    $sql = "update user 
                set
                user_nickname = ?,
                user_email = ?,
                id_role = ?,
                user_status = ?,
                user_modified_at = ?
                where id_user = ?";
                    $stm = $this->pdo->prepare($sql);
                    $stm->execute([
                        $model->user_nickname,
                        $model->user_email,
                        $model->id_role,
                        $model->user_status,
                        $fecha,
                        $model->id_user
                    ]);
                    unset($_SESSION['id_usered']);
                } elseif(isset($model->user_auth_exists)){
                    $sql = "update user 
                set
                id_role = ?,
                id_person = ?,
                user_nickname = ?,
                user_password = ?,
                user_email = ?,
                user_status = ?,
                user_modified_at = ?
                where id_user = ?";
                    $stm = $this->pdo->prepare($sql);
                    $stm->execute([
                        $model->id_role,
                        $model->id_person,
                        $model->user_nickname,
                        $model->user_password,
                        $model->user_email,
                        $model->user_status,
                        $fecha,
                        $model->id_user
                    ]);
                }else {
                    $sql = "update user 
                set
                id_role = ?,
                id_person = ?,
                user_nickname = ?,
                user_email = ?,
                user_status = ?,
                user_modified_at = ?
                where id_user = ?";
                    $stm = $this->pdo->prepare($sql);
                    $stm->execute([
                        $model->id_role,
                        $model->id_person,
                        $model->user_nickname,
                        $model->user_email,
                        $model->user_status,
                        $fecha,
                        $model->id_user
                    ]);
                    unset($_SESSION['id_usered']);
                }

            }
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    //Cambiar Contraseña de un Usuario
    public function changepassword($model){
        try {
            $sql = 'update user set
                user_password = ?
                where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->user_password,
                $model->id_user
            ]);
            unset($_SESSION['id_userchg']);
            unset($_SESSION['id_userchginfo']);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    public function changestatus($model){
        try {
            $sql = 'update user set
                user_status = ?
                where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->user_status,
                $model->id_user
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    //Borrar un Registro
    public function delete($id){
        try{
            $sql = 'delete from user where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function sessionclose(){
        try{
            unset($_SESSION['id_user']);
            unset($_SESSION['id_person']);
            unset($_SESSION['user_nickname']);
            unset($_SESSION['user_image']);
            unset($_SESSION['person_name']);
            unset($_SESSION['person_surname']);
            unset($_SESSION['person_dni']);
            unset($_SESSION['person_genre']);
            unset($_SESSION['role']);
            unset($_SESSION['role_name']);
            session_destroy();

            setcookie('id_user', '1', time() - 365 * 24 * 60 * 60, "/");
            setcookie('id_person', '1', time() - 365 * 24 * 60 * 60, "/");
            setcookie('user_nickname', '1', time() - 365 * 24 * 60 * 60, "/");
            setcookie('user_image', '1', time() - 365 * 24 * 60 * 60, "/");
            setcookie('person_name', '1', time() - 365 * 24 * 60 * 60, "/");
            setcookie('person_surname', '1', time() - 365 * 24 * 60 * 60, "/");
            setcookie('person_dni', '1', time() - 365 * 24 * 60 * 60, "/");
            setcookie('person_genre', '1', time() - 365 * 24 * 60 * 60, "/");
            setcookie("role", '1', time() - 365 * 30 * 24 * 60 * 60, "/");
            setcookie("role_name", '1', time() - 365 * 24 * 60 * 60, "/");
            $okey = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $okey = 2;
        }
        return $okey;
    }
    public function crear_chat($id_1,$id_2,$fecha){
        try {
            $sql = 'insert into chat(id_usuario_1,id_usuario_2,chat_fecha) values(?,?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $id_1,$id_2,$fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_cuenta_por_id_user($id){
        try{
            $sql = 'select * from user u inner join cuenta c on u.id_user = c.id_user inner join person p on p.id_person=u.id_person where c.id_user = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_mensajes_por_chat($id_chat){
        try {
            $stm = $this->pdo->prepare("SELECT * from detalle_chat where chat_id=?");
            $stm->execute([$id_chat]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_chats_por_id_usuario($id_usuario){
        try {
            $stm = $this->pdo->prepare("SELECT chat_id,(SELECT us.user_nickname from user us where us.id_user = c.id_usuario_1) as usuario_1,(SELECT us.id_user from user us where us.id_user = c.id_usuario_1) as id_usuario_1,(SELECT us.user_nickname from user us where us.id_user = c.id_usuario_2) as usuario_2,(SELECT us.id_user from user us where us.id_user = c.id_usuario_2) as id_usuario_2,chat_fecha from chat c where c.id_usuario_1=? or c.id_usuario_2=?");
            $stm->execute([$id_usuario,$id_usuario]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_pagos_por_id_usuario($id_usuario){
        try {
            $stm = $this->pdo->prepare("SELECT * from pago p inner join transferencia_u_e tue on tue.id_pago=p.pago_id inner join equipo eq on eq.equipo_id=p.equipo_id where p.id_user=?");
            $stm->execute([$id_usuario]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_ultimo_mensaje_de_chat($id_chat){
        try {
            $stm = $this->pdo->prepare('select * from detalle_chat where chat_id = ? order by detalle_chat_id desc limit 1');
            $stm->execute([$id_chat]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_ciudades(){
        try {
            $stm = $this->pdo->prepare("SELECT DISTINCT u.ubigeo_ciudad FROM ubigeo u");
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_distritos_por_ciudad($ciudad){
        try {
            $stm = $this->pdo->prepare("SELECT DISTINCT u.ubigeo_distrito,u.ubigeo_id from ubigeo u where u.ubigeo_ciudad=?");
            $stm->execute([$ciudad]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function enviar_mensaje($chat_id,$id_usuario,$mensaje,$fecha){
        try {
            $sql = 'insert into detalle_chat(
                    chat_id,
                    id_usuario,
                    detalle_chat_mensaje,
                    detalle_chat_fecha,
                    detalle_chat_estado
                    ) values(?,?,?,?,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $chat_id,$id_usuario,$mensaje,$fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_chat_por_id($id){
        try {
            $stm = $this->pdo->prepare('select * from chat where chat_id = ?');
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
}