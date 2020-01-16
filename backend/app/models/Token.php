<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 26/08/2019
 * Time: 12:40
 */
class Token{
    private $pdo;
    private $log;
    private $crypt;
    public function __construct(){
        $this->log = new Log();
        $this->pdo = Database::getConnection();
        $this->crypt = new Crypt();
    }
    //Funcion para validar si el token es valido
    public function validate_token($token){
        try{
            //Acá se implementará una lógica bien bacán para validar los tokens de usuario
            //pero como ya va a ser la hora de almuerzo, le pondre true para que
            //deje pasar todas las request sin hacer roche
            $simple_token = $this->crypt->tripledecrypt($token);
            if(!$simple_token){
                $result = false;
            } else {
                $date = $this->get_date($simple_token[0]);
                $original_pass = $this->get_pass($simple_token[0]);
                $hash = $this->crypt->decrypt($simple_token[1], $date);
                if(password_verify($original_pass, $hash)){
                    $result = true;
                } else {
                    $result = false;
                }
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }
    //Funcion para obtener fecha usuario
    public function get_date($id){
        try{
            $sql = 'select user_created_at from user where id_user = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = '1995-01-01';
        }
        return $result->user_created_at;
    }
    //Funcion para obtener pass
    public function get_pass($id){
        try{
            $sql = 'select user_password from user where id_user = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = '';
        }
        return $result->user_password;
    }
}