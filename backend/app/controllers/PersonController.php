<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 27/08/2019
 * Time: 21:45
 */

require 'app/models/Person.php';
class PersonController{
    private $crypt;
    private $nav;
    private $person;
    private $log;
    private $clean;
    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->person = new Person();
        $this->log = new Log();
        $this->clean = new Clean();
    }

    //Vistas
    public function all(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $person = $this->person->listAll();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'person/all.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function add(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'person/add.php';
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
            $_SESSION['id_persone'] = $id;
            $person = $this->person->list($id);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'person/edit.php';
        } catch (\Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    //Funciones
    public function save(){
        try{
            //If All OK, the message does not change
            $message = "We did it. Your awesome... and beautiful";
            $model = new Person();
            //Start Evaluation Of Data Integrity
            $ok_data = true;
            if(isset($_POST['person_name']) && isset($_POST['person_surname']) && isset($_POST['person_birth']) && isset($_POST['person_number_phone']) && isset($_POST['person_genre']) && isset($_POST['person_address'])){
                //Clean Data
                $_POST['person_name'] = $this->clean->clean_post_str($_POST['person_name']);
                $_POST['person_surname'] = $this->clean->clean_post_str($_POST['person_surname']);
                $_POST['person_birth'] = $this->clean->clean_post_str($_POST['person_birth']);
                $_POST['person_number_phone'] = $this->clean->clean_post_int($_POST['person_number_phone']);
                $_POST['person_genre'] = $this->clean->clean_post_str($_POST['person_genre']);
                $_POST['person_address'] = $this->clean->clean_post_str($_POST['person_address']);
                //Evaluation If All Data is Ok
                //Empty true: Si el campo NO debe estar vacio
                $ok_data = $this->clean->validate_post_str($_POST['person_name'], true, $ok_data, 200);
                $ok_data = $this->clean->validate_post_str($_POST['person_surname'], true, $ok_data,200);
                $ok_data = $this->clean->validate_post_date($_POST['person_birth'], true, $ok_data,20,1);
                $ok_data = $this->clean->validate_post_int($_POST['person_number_phone'], true, $ok_data, 24);
                $ok_data = $this->clean->validate_post_str($_POST['person_genre'], true, $ok_data, 1);
                $ok_data = $this->clean->validate_post_str($_POST['person_address'], true, $ok_data, 200);
                if(isset($_POST['person_dni'])){
                    $_POST['person_dni'] = $this->clean->clean_post_str($_POST['person_dni']);
                    $ok_data = $this->clean->validate_post_str($_POST['person_dni'], true, $ok_data, 8);
                }
            } else {
                $ok_data = false;
            }
            //End Evaluation Of Data Integrity

            if($ok_data){
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
                    if(isset($_POST['person_dni'])){
                        if($this->person->validateDNI($_POST['person_dni'])){
                            $result = 3;
                        } else {
                            $model->person_name= $_POST['person_name'];
                            $model->person_surname = $_POST['person_surname'];
                            $model->person_dni = $_POST['person_dni'];
                            $model->person_birth = $_POST['person_birth'];
                            $model->person_number_phone = $_POST['person_number_phone'];
                            $model->person_genre = $_POST['person_genre'];
                            $model->person_address = $_POST['person_address'];
                            $result = $this->person->save($model);
                        }
                    } else {
                        $result = 2;
                    }
                }
            } else {
                $result = 6;
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
            $result = $this->person->delete($id);
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }
}
