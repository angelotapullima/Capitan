<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 02/10/2019
 * Time: 22:59
 */
require 'app/models/Userg.php';
require 'app/models/Person.php';
require 'app/models/User.php';
require 'app/models/Role.php';
class RegistrarController{
    private $crypt;
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

    public function nuevo_usuario(){
        try{
            $model = new User();
            $modelp = new Person();
            if($this->userg->validateUser($_POST['user_nickname'])){
                $result = 3;
                $message = "Code 3: Duplicate Nickname";
            } else {
                if($this->userg->validateDNI($_POST['person_dni'])){
                    $result = 4;
                    $message = "Code 4: Duplicate DNI";
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
                        $model->user_password =  password_hash($_POST['user_password'], PASSWORD_BCRYPT);
                        $model->user_email = $_POST['user_email'];
                        $model->id_role = 4;
                        $model->id_person = $this->person->listByDni($_POST['person_dni']);
                        $result = $this->user->save($model);
                        if($result == 1){
                            $message = "You did it";
                        } else{
                            $this->person->deletedni($_POST['person_dni']);
                            $result = 2;
                            $message = "Code 2: General Error";
                        }
                    } else {
                        $this->person->deletedni($_POST['person_dni']);
                        $result = 2;
                        $message = "Code 2: General Error";
                    }
                }
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
            $message = "Code 2: General Error";
        }
        $response = array("code" => $result,"message" => $message);
        $data = array("result" => $response);
        echo json_encode($data);
    }
}