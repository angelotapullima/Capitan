<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 28/08/2019
 * Time: 17:59
 */
require 'app/models/User.php';
require 'app/models/Person.php';
require 'app/models/Edit.php';

class EditController{
    private $crypt;
    private $nav;
    private $user;
    private $person;
    private $log;
    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->user = new User();
        $this->person = new Person();
        $this->log = new Log();
        $this->edit = new Edit();
    }
    //Vistas
    public function info(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));

            $_SESSION['id_personeinfo'] = $this->crypt->decrypt($_SESSION['id_person'],_PASS_);
            $person = $this->person->list($this->crypt->decrypt($_SESSION['id_person'],_PASS_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'edit/info.php';

        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function changeUser(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));

            $_SESSION['id_userededit'] = $this->crypt->decrypt($_SESSION['id_user'],_PASS_);
            $user = $this->user->list($this->crypt->decrypt($_SESSION['id_user'],_PASS_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'edit/changeUser.php';

        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function changepass(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));

            $_SESSION['id_userchginfo'] = $this->crypt->decrypt($_SESSION['id_user'],_PASS_);
            $user = $this->user->list($this->crypt->decrypt($_SESSION['id_user'],_PASS_));

            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'edit/changepass.php';
        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    //Funciones
    public function save(){
        try{
            $model = new Person();
            if(isset($_SESSION['id_personeinfo'])){
                $model->id_person = $_SESSION['id_personeinfo'];
                $model->person_name= $_POST['person_name'];
                $model->person_surname = $_POST['person_surname'];
                $model->person_birth = $_POST['person_birth'];
                $model->person_number_phone = $_POST['person_number_phone'];
                $model->person_genre = $_POST['person_genre'];
                $model->person_address = $_POST['person_address'];
                $result = $this->person->save($model);
            } else {
                $result = 2;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }
    public function editar_user(){
        try{
            $model = new Person();
            if(isset($_POST['person_dni']) && isset($_POST['person_address']) &&isset($_POST['user_posicion']) &&isset($_POST['user_habilidad']) &&isset($_POST['user_num'])&&isset($_POST['id_user'])){
                $datos_person = $this->user->list($_POST['id_user']);
                $model->id_person = $datos_person->id_person;
                $model->person_dni= $_POST['person_dni'];
                $model->person_address = $_POST['person_address'];
                $result = $this->person->edit($model);
                if($result==1){
                    $modelU = new User();
                    $modelU->user_posicion = $_POST['user_posicion'];
                    $modelU->user_habilidad = $_POST['user_habilidad'];
                    $modelU->user_num = $_POST['user_num'];
                    $modelU->id_user = $_POST['id_user'];
                    $result = $this->user->edit($modelU);
                }
            } else {
                $result = 2;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function saveNewNick(){
        try{
            $model = new User();
            if(isset($_SESSION['id_userededit'])){
                if($this->user->selectNickname($_SESSION['id_userededit']) == $_POST['user_nickname']){
                    $model->id_user = $_SESSION['id_userededit'];
                    $model->user_nickname = $_POST['user_nickname'];
                    $result = $this->edit->save($model);
                    $this->user->sessionclose();
                } else {
                    if($this->user->validateUser($_POST['user_nickname'])){
                        $result = 3;
                    } else {
                        $model->id_user = $_SESSION['id_userededit'];
                        $model->user_nickname = $_POST['user_nickname'];
                        $result = $this->edit->save($model);
                        $this->user->sessionclose();
                    }
                }
            } else {
                $result = 2;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }
    public function editar_nickname(){
        try{
            $model = new User();
            if($this->user->validateUser($_POST['user_nickname'])){
                $result = 3;
            } else {
                $model->id_user = $_POST['id_user'];
                $model->user_nickname = $_POST['user_nickname'];
                $result = $this->edit->save($model);
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
    }

    public function chgpass(){
        try{
            $model = new User();
            if(isset($_SESSION['id_userchginfo'])){
                $model->id_user = $_SESSION['id_userchginfo'];
                $model->user_password =  password_hash($_POST['user_password'], PASSWORD_BCRYPT);
                $result = $this->user->changepassword($model);
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }
    public function edit_pass(){
        try{
            $model = new User();
            $model->id_user = $_POST['id_user'];
            $model->user_password =  password_hash($_POST['user_password'], PASSWORD_BCRYPT);
            $result = $this->user->changepassword($model);
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
    }
}