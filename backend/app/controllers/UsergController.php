<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 28/08/2019
 * Time: 16:14
 */
require 'app/models/Userg.php';
require 'app/models/Person.php';
require 'app/models/User.php';
require 'app/models/Role.php';
class UsergController{
    private $crypt;
    private $nav;
    private $userg;
    private $user;
    private $person;
    private $role;
    private $log;

    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->userg = new Userg();
        $this->user = new User();
        $this->person = new Person();
        $this->log = new Log();
        $this->role = new Role();
    }
    //Vistas
    public function all(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id_role = $this->crypt->decrypt($_SESSION['role'],_PASS_);
            if($id_role == 2){
                $person = $this->userg->listAll_wsu();
            } else {
                $person = $this->userg->listAll();
            }
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'userg/all.php';
        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function addu(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $role = $this->role->readAllrolewoS();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'userg/add.php';
        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function editpu(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $_SESSION['id_persone'] = $id;

            $person = $this->person->list($id);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'userg/editpu.php';
        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function edituu(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $_SESSION['id_usered'] = $id;
            $user = $this->user->list($id);
            $role = $this->role->readAllrolewoS();
            $person = $this->person->listAll();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'userg/edituu.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }


    //Funciones
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
                    if($this->userg->validateDNI($_POST['person_dni'])){
                        $result = 4;
                    } else {
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
                            $model->id_role = $_POST['id_role'];
                            $model->id_person = $this->person->listByDni($_POST['person_dni']);
                            $result = $this->user->save($model);
                            if($result != 1){
                                $this->person->deletedni($_POST['person_dni']);
                                $result = 2;
                            }
                        } else {
                            $this->person->deletedni($_POST['person_dni']);
                            $result = 2;
                        }
                    }

                }

            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function save_edit_personu(){
        try{
            $model = new Person();
            if(isset($_SESSION['id_persone'])){
                $model->id_person = $_SESSION['id_persone'];
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


    public function save_edit_useru(){
        try{
            $model = new User();
            if(isset($_SESSION['id_usered'])){
                if($this->user->selectNickname($_SESSION['id_usered']) == $_POST['user_nickname']){
                    $model->id_user = $_SESSION['id_usered'];
                    $model->id_role = $_POST['id_role'];
                    $model->user_nickname = $_POST['user_nickname'];
                    $model->user_status = $_POST['user_status'];
                    $model->user_email = $_POST['user_email'];
                    $result = $this->user->save($model);
                } else {
                    if($this->user->validateUser($_POST['user_nickname'])){
                        $result = 3;
                    } else {
                        $model->id_user = $_SESSION['id_usered'];
                        $model->id_role = $_POST['id_role'];
                        $model->user_nickname = $_POST['user_nickname'];
                        $model->user_status = $_POST['user_status'];
                        $model->user_email = $_POST['user_email'];
                        $result = $this->user->save($model);
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

    public function reset_pass(){
        try{
            $model = new User();
            $model->id_user = $_POST['id'];
            $dni = $this->user->getDni($_POST['id']);
            $model->user_password =  password_hash($dni, PASSWORD_BCRYPT);
            $result = $this->user->changepassword($model);
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function change_status(){
        try{
            $model = new User();
            $model->id_user = $_POST['id'];
            $model->user_status =  $_POST['user_status'];
            $result = $this->user->changestatus($model);
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

}