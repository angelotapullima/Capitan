<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 27/08/2019
 * Time: 22:06
 */
//Llamada a modelos necesarios
require 'app/models/User.php';
require 'app/models/Person.php';
require 'app/models/Role.php';

class UserController{
    private $crypt;
    private $nav;
    private $user;
    private $person;
    private $role;
    private $log;
    private $clean;
    private $validate;

    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->user = new User();
        $this->person = new Person();
        $this->role = new Role();
        $this->log = new Log();
        $this->clean = new Clean();
        $this->validate = new Validate();
    }

    //Vistas
    public function all(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $user = $this->user->listAll();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'user/all.php';
        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function add(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $roles = $this->role->listAll2();
            $person = $this->person->listAllFree();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'user/add.php';

        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function edit(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $_SESSION['id_usered'] = $id;
            $user = $this->user->list($id);
            $roles = $this->role->listAll2();
            $person = $this->person->listAll();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'user/edit.php';

        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function changep(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $_SESSION['id_userchg'] = $id;
            $user = $this->user->list($id);

            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'user/changep.php';
        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    //Funciones
    public function save(){
        try{
            $model = new User();
            if(isset($_SESSION['id_usered'])){
                if($this->user->selectNickname($_SESSION['id_usered']) == $_POST['user_nickname']){
                    $model->id_user = $_SESSION['id_usered'];
                    $model->user_nickname = $_POST['user_nickname'];
                    $model->user_status = $_POST['user_status'];
                    $model->user_email = $_POST['user_email'];
                    $model->id_role = $_POST['id_role'];
                    $model->id_person = $_POST['id_person'];
                    $result = $this->user->save($model);
                    $this->user->sessionclose();
                } else {
                    if($this->user->validateUser($_POST['user_nickname'])){
                        $result = 3;
                    } else {
                        $model->id_user = $_SESSION['id_usered'];
                        $model->user_nickname = $_POST['user_nickname'];
                        $model->user_status = $_POST['user_status'];
                        $model->user_email = $_POST['user_email'];
                        $model->id_role = $_POST['id_role'];
                        $model->id_person = $_POST['id_person'];
                        $result = $this->user->save($model);
                        $this->user->sessionclose();
                    }
                }
            } else {
                if($this->user->validateUser($_POST['user_nickname'])){
                    $result = 3;
                } else {
                    $model->user_nickname= $_POST['user_nickname'];
                    $model->user_password =  password_hash($_POST['user_password'], PASSWORD_BCRYPT);
                    $model->user_email = $_POST['user_email'];
                    $model->id_role = $_POST['id_role'];
                    $model->id_person = $_POST['id_person'];
                    $result = $this->user->save($model);
                }

            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function changepass(){
        try{
            $model = new User();
            if(isset($_SESSION['id_userchg'])){
                $model->id_user = $_SESSION['id_user'];
                $model->user_password =  password_hash($_POST['user_password'], PASSWORD_BCRYPT);
                $result = $this->user->changepassword($model);
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }
    public function delete(){
        try{
            $id = $_POST['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $result = $this->user->delete($id);
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function perfil_app(){
        try{
            $id = $_POST['id_user'];
            $r = $this->user->selectuser($id);
            if(isset($r->id_user)){
                $birthday = $this->validate->get_date_nominal($r->person_birth,"Date","Date","es");
                $created_at = $this->validate->get_date_nominal($r->user_created_at,"DateTime","DateTime","es");
                $last_login = $this->validate->get_date_nominal($r->user_last_login,"DateTime","DateTime","es");
                $response = array(
                    "id_user" => $r->id_user,
                    "id_person" => $r->id_person,
                    "nombre" => $r->person_name." ".$r->person_surname,
                    "dni" => $r->person_dni,
                    "celular" => $r->person_number_phone,
                    "sexo" => $r->person_genre,
                    "birthday" => $birthday,
                    "direccion" => $r->person_address,
                    "seguro" => $r->person_seguro,
                    "fecha_contrato" => $r->person_fecha_contrato,
                    "nickname" => $r->user_nickname,
                    "email" => $r->user_email,
                    "img" => $r->user_image,
                    "estado" => $r->user_status,
                    "created_at" => $created_at,
                    "last_login" => $last_login
                );
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        $data = array("result" => $response);
        echo json_encode($data);
    }
    public function actualizar_token(){
        try{
            $message = "We did it. Your awesome... and beautiful";
            $usuario_id = $_POST['id_user'];
            $token = $_POST['token_firebase'];
            $result = $this->user->actualizar_token($token,$usuario_id);
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $message = "Code 2: General Error";
        }
        $response = array("code" => $result,"message" => $message);
        $data = array("result" => $response);
        echo json_encode($data);
    }
    public function listar_chats_por_id_usuario(){
        $id_usuario = $_POST['id_user'];
        $model = $this->user->listar_chats_por_id_usuario($id_usuario);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $mensaje = $this->user->listar_ultimo_mensaje_de_chat($model[$i]->chat_id);
            $datos_u = $this->user->list($model[$i]->usuario_1);
            $datos_ = $this->user->list($model[$i]->usuario_2);
            $fecha = explode(' ',$mensaje->detalle_chat_fecha);
            $resources[$i] = array(
                "chat_id" => $model[$i]->chat_id,
                "usuario_1" => $model[$i]->usuario_1,
                "usuario_1_foto" => $datos_u->usuario_foto,
                "usuario_2" => $model[$i]->usuario_2,
                "usuario_2_foto" => $datos_->usuario_foto,
                "chat_fecha" => $model[$i]->chat_fecha,
                "ultimo_msj" => $mensaje->detalle_chat_mensaje,
                "ultimo_msj_id" => $mensaje->detalle_chat_id,
                "ultimo_msj_fecha" => $fecha[0],
                "ultimo_msj_hora" => $fecha[1],
                "ultimo_msj_usuario" => $mensaje->id_usuario
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
}