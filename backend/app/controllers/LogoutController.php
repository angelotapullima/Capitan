<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 28/08/2019
 * Time: 10:32
 */
class LogoutController{
    private $log;
    private $crypt;
    public function __construct()
    {
        $this->log = new Log();
        $this->crypt = new Crypt();
    }

    public function singOut(){
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
        if($okey ==1){
            header('Location: ' . _SERVER_ );
        }
    }
}