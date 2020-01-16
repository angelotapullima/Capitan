<?php
class Database_Auth{
    private static $db;
    //Función que realiza la conexión a base de datos
    public static function getConnection(){
        try{
            if(empty(self::$db)){
                //$pdo = new PDO('mysql:host=guabba.com;dbname=guabba_salubridad;charset=utf8','guabba_saludos','12345678');
                //$pdo = new PDO('mysql:host=localhost;dbname=u871783810_saneamientobd;charset=utf8','u871783810_saneamiento','219r:VdW&[4wA');
                //$pdo = new PDO('mysql:host=localhost;dbname=u871783810_authbd;charset=utf8','u871783810_authroot','b4/Y*T0e4s1][');
                $pdo = new PDO('mysql:host=213.190.6.106;dbname=u871783810_authbd;charset=utf8','u871783810_authroot','b4/Y*T0e4s1][');
                //En caso de trabajar localmente, descomentar la siguiente linea y comentar la anterior
                //$pdo = new PDO('mysql:host=localhost;dbname=zxcvbnm;charset=utf8','root','');

                //Sirve para indicar al PDO que todo lo que retorne sean objetos
                $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_OBJ);
                //Sirve para indicar que si encuentra error, los muestre
                $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                //$pdo->query("SET NAMES 'utf8';");
                //$pdo->execute();

                self::$db = $pdo;
            }
            return self::$db;
        } catch (\Throwable $e){
            $saltodelinea = '\n';
            echo "<script language=\"javascript\">alert(\"Error Critico. Por favor, regrese más tardecito. " . $saltodelinea . "Nuestros mejores técnicos dejaron su juanecito a medio comer y ya van camino a resolver el problema.\");</script>";
            exit;
            //$this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            //echo "<script language=\"javascript\">window.location.href=\"error/error\";</script>";
        }
    }
}
