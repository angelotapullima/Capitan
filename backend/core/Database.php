<?php
//Conexion a la base de datos usando PDO
use PDO;

class Database{
    private static $db;

    public static function getConnection(){
        if(empty(self::$db)){
            //$pdo = new PDO('mysql:host=localhost;dbname=guabba_capitan;charset=utf8','root','');
            $pdo = new PDO('mysql:host=localhost;dbname=guabba_capitan;charset=utf8','guabba_root','Aa12345678');
            //Sirve para indicar al PDO que todo lo que retorne sean objetos
            $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_OBJ);
            //Sirve para indicar que si encuentra error, los muestre
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            self::$db = $pdo;
        }

        return self::$db;
    }
}