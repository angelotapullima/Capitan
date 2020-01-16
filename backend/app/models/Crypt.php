<?php
/**
 * Created by PhpStorm.
 * User: CesarJose39
 * Date: 15/10/2018
 * Time: 17:32
 */
//Clase para la encriptacion y desencriptacion de datos
class Crypt{
    //Esta función encripta los datos los datos de una forma que sólo Diosito y él que lo hizo saben
    function encrypt($string, $key) {
        $result = '';
        for($i=0; $i<strlen($string); $i++) {
            $char = substr($string, $i, 1);
            $keychar = substr($key, ($i % strlen($key))-1, 1);
            $char = chr(ord($char)+ord($keychar));
            $result.=$char;
        }
        return base64_encode($result);
    }
    //Esta función desencripta los datos los datos de una forma que sólo Diosito y él que lo hizo saben
    function decrypt($string, $key) {
        if ($string == ''){
            $result = null;
        } else {
            $result = '';
            $string = base64_decode($string);
            for($i=0; $i<strlen($string); $i++) {
                $char = substr($string, $i, 1);
                $keychar = substr($key, ($i % strlen($key))-1, 1);
                $char = chr(ord($char)-ord($keychar));
                $result.=$char;
            }
        }
        return $result;
    }
    //Triple encriptacion
    function tripleencrypt($contrasenha, $usuario, $fecha){
        $pass = '';
        $pass = password_hash($contrasenha, PASSWORD_BCRYPT);
        $pass = $this->encrypt($pass, $fecha);
        $pass = $usuario . '|' . $pass;
        $pass = $this->encrypt($pass, _FULL_KEY_);
        return $pass;
    }
    //Triple desencriptacion
    function tripledecrypt($hash){
        $pass = $hash;
        $pass = $this->decrypt($pass, _FULL_KEY_);
        $pass = explode("|", $pass);
        if(is_array($pass)){
            if(isset($pass[0])){
                $int = intval($pass[0]);
                if(!is_int($int) || $int === 0){
                    $pass = false;
                }
            } else {
                $pass = false;
            }
        } else {
            $pass = false;
        }
        return $pass;
    }

}