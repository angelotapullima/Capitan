<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 27/08/2019
 * Time: 22:06
 */
//Llamada a modelos necesarios
require 'app/models/User.php';
require 'app/models/Userg.php';
require 'app/models/Person.php';
require 'app/models/Role.php';

class UserController{
    private $crypt;
    private $nav;
    private $user;
    private $userg;
    private $person;
    private $role;
    private $log;
    private $clean;
    private $validate;

    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->user = new User();
        $this->userg = new Userg();
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
        try{
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
        }catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General Error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_pagos_por_id_usuario(){
        try{
            $id_usuario = $_POST['id_user'];
            $model = $this->user->listar_pagos_por_id_usuario($id_usuario);
            $resources=array();
            for ($i=0;$i<count($model);$i++) {
                $fecha = $this->validate->get_date_nominal($model[$i]->pago_date,"DateTime","DateTime","es");
                $resources[$i] = array(
                    "concepto" => $model[$i]->transferencia_u_e_concepto,
                    "monto" => $model[$i]->pago_total,
                    "fecha" => $fecha
                );
            }
        }catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General Error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function crear_chat() {
        try{
            $ok_data = true;
            if(isset($_POST['id_1']) && isset($_POST['id_2'])){
                $_POST['id_1'] = $this->clean->clean_post_int($_POST['id_1']);
                $_POST['id_2'] = $this->clean->clean_post_int($_POST['id_2']);

                $ok_data = $this->clean->validate_post_int($_POST['id_1'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_2'], true, $ok_data, 11);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $id_1 = $_POST['id_1'];
                $id_2 = $_POST['id_2'];
                $fecha = date('Y-m-d H:i:s');
                $hora=date('H:i:s');
                $microtime = microtime(true);
                $result = $this->user->crear_chat($id_1,$id_2,$fecha,$microtime);
                $resources = array();
            }else{
                $result=6;
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_ciudades(){
        try{
            $model = $this->user->listar_ciudades();
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $resources[$i] = array(
                    "ubigeo_ciudad" => $model[$i]->ubigeo_ciudad
                );
            }
        }catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General Error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_distritos_por_ciudad(){
        try{
            $ciudad = $_POST['ciudad'];
            $model = $this->user->listar_distritos_por_ciudad($ciudad);
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $resources[$i] = array(
                    "ubigeo_id" => $model[$i]->ubigeo_id,
                    "distrito" => $model[$i]->ubigeo_distrito
                );
            }
        }catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General Error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function new(){
        try{
            $model = new User();
            $modelp = new Person();
            if(isset($_SESSION['id_usered'])){
                $result = 6;
            } else {
                if($this->userg->validateUser($_POST['user_nickname'])){
                    $result = 3;
                } else {
                    $microtime = microtime(true);
                    $modelp->microtime=$microtime;
                    $modelp->person_name= $_POST['person_name'];
                    $modelp->person_surname = $_POST['person_surname'];
                    $modelp->person_dni = $_POST['person_dni'];
                    $modelp->person_birth = $_POST['person_birth'];
                    $modelp->person_number_phone = $_POST['person_number_phone'];
                    $modelp->person_genre = $_POST['person_genre'];
                    $modelp->person_address = $_POST['person_address'];
                    $resultp = $this->person->save($modelp);
                    if($resultp == 1){
                        $model->user_nickname= $_POST['user_nickname'];
                        //$model->id_auth= $_POST['id_auth'];
                        $model->user_password =  password_hash($_POST['user_password'], PASSWORD_BCRYPT);
                        $model->user_email = $_POST['user_email'];
                        $model->user_posicion = $_POST['user_posicion'];
                        $model->user_habilidad = $_POST['user_habilidad'];
                        $model->user_num = $_POST['user_num'];
                        $model->user_image = 'media/user/user.jpg';
                        $model->ubigeo_id = $_POST['ubigeo_id'];
                        $model->id_role = $_POST['id_role'];
                        $model->id_person = $this->person->listByMicrotime($microtime);
                        $result = $this->user->save($model);
                        if($result != 1){
                            $this->person->deletemicrotime($microtime);
                            $result = 2;
                        }
                    } else {
                        $this->person->deletemicrotime($microtime);
                        $result = 2;
                    }
                }

            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }
    public function fecha_hora_actual(){
        try{
            $resources = array();
            date_default_timezone_set('America/Lima');
            $fecha = date('Y-m-d');
            $hora = date('H:i:s');
            $resources[0] = array("fecha"=>$fecha,"hora"=>$hora);
            $data = array("results" => $resources);
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $data = 2;
        }
        echo json_encode($data);
    }
    public function enviar_mensaje() {
        try{
            $chat_id = $_POST['id_chat'];
            $id_usuario = $_POST['id_usuario'];
            $mensaje = $_POST['mensaje'];
            $fecha_ = date('Y-m-d H:i:s');
            $fecha = date('Y-m-d');
            $hora = date('h:i a');
            $result = $this->user->enviar_mensaje($chat_id,$id_usuario,$mensaje,$fecha_);
            if($result==1){
                $datos = $this->user->listar_chat_por_id($chat_id);
                if($datos->id_usuario_1 == $id_usuario){
                    $user=$this->user->list($datos->id_usuario_2);
                    $user2=$this->user->list($datos->id_usuario_1);
                }else{
                    $user=$this->user->list($datos->id_usuario_1);
                    $user2=$this->user->list($datos->id_usuario_2);
                }
                $this->notificar_chat($user->usuario_token,$mensaje,$user2->usuario_nombre,"Mensaje",$mensaje,$chat_id,$id_usuario,$hora,$fecha);
            }
            $resources = array();
            $resources[0] = array("valor"=>$result);
            $data = array("results" => $resources);
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $data = 2;
        }
        echo json_encode($data);
    }
    public function listar_usuario_por_id(){
        $id = $_POST['id'];
        $model = $this->user->list($id);
        $resources = array();
        $resources[0] = array(
            "usuario_id"=>$model->id_user,
            "nombre"=>$model->person_name. " ".$model->person_surname,
            "dni"=>$model->person_dni,
            "nacimiento"=>$model->person_birth,
            "sexo"=>$model->person_genre,
            "email"=>$model->user_email,
            "telefono"=>$model->person_number_phone,
            "usuario"=>$model->user_nickname,
            "posicion"=>$model->user_posicion,
            "habilidad"=>$model->user_habilidad,
            "num"=>$model->user_num,
            "foto"=>$model->user_image
        );
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function notificar($token,$body,$title,$tipo,$contenido){
        $url = 'https://fcm.googleapis.com/fcm/send';
        $fields = array('to' => $token ,
            'notification' => array('body' => $body, 'title' => $title),
            'data' =>array('tipo'=> $tipo ,
                'Contenido' => $contenido
            ));
        define('GOOGLE_API_KEY', 'AAAAj5-Syog:APA91bFAMP0UmglvTddGLwEqtTJmfEtFTmVkSElOOEcmAI1rW-GaJ6uTfGuUvdzbwcMxyyLswqYUkM3ALdSvcUNM60rb9ryY-MIN2oLUHVIoT9SyKPE6uyo7omdwNQZjaVZtEDkYnxX7');
        $headers = array(
            'Authorization:key='.GOOGLE_API_KEY,
            'Content-Type: application/json');
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        $result = curl_exec($ch);
        if($result === false){
            die('Curl failed' . curl_error());}
        curl_close($ch);
        return $result;
    }
    public function notificar_chat($token,$body,$title,$tipo,$mensaje,$id_chat,$id_usuario,$hora,$fecha){
        $url = 'https://fcm.googleapis.com/fcm/send';
        $fields = array('to' => $token ,
            'notification' => array('body' => $body, 'title' => $title),
            'data' =>array('tipo'=> $tipo ,
                'mensaje' => $mensaje,
                'id_chat' => $id_chat,
                'id_usuario' => $id_usuario,
                'hora' => $hora,
                'fecha' => $fecha
            ));
        define('GOOGLE_API_KEY', 'AAAAj5-Syog:APA91bFAMP0UmglvTddGLwEqtTJmfEtFTmVkSElOOEcmAI1rW-GaJ6uTfGuUvdzbwcMxyyLswqYUkM3ALdSvcUNM60rb9ryY-MIN2oLUHVIoT9SyKPE6uyo7omdwNQZjaVZtEDkYnxX7');
        $headers = array(
            'Authorization:key='.GOOGLE_API_KEY,
            'Content-Type: application/json');
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        $result = curl_exec($ch);
        if($result === false){
            die('Curl failed' . curl_error());}
        curl_close($ch);
        return $result;
    }
    public function listar_mensajes_por_chat(){
        $id_chat = $_POST['id_chat'];
        $model = $this->user->listar_mensajes_por_chat($id_chat);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $fecha = explode(' ',$model[$i]->detalle_chat_fecha);
            $resources[$i] = array(
                "chat_id" => $id_chat,
                "detalle_chat_id" => $model[$i]->detalle_chat_id,
                "id_usuario" => $model[$i]->id_usuario,
                "mensaje" => $model[$i]->detalle_chat_mensaje,
                "fecha" => $fecha[0],
                "hora" => $fecha[1],
                "estado" => $model[$i]->detalle_chat_estado
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
}
/*
 * <!-- drawable/tshirt_crew.xml -->
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:height="24dp"
    android:width="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path android:fillColor="@color/colorPrimary" android:pathData="M16,21H8A1,1 0 0,1 7,20V12.07L5.7,13.07C5.31,13.46 4.68,13.46 4.29,13.07L1.46,10.29C1.07,9.9 1.07,9.27 1.46,8.88L7.34,3H9C9,4.1 10.34,5 12,5C13.66,5 15,4.1 15,3H16.66L22.54,8.88C22.93,9.27 22.93,9.9 22.54,10.29L19.71,13.12C19.32,13.5 18.69,13.5 18.3,13.12L17,12.12V20A1,1 0 0,1 16,21" />
</vector>
 * */