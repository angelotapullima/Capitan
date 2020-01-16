<?php
/**
 * Created by PhpStorm.
 * User: CesarJose39
 * Date: 03/10/2018
 * Time: 12:21
 */
//Clase para crear la conexión a base de datos
class Database{
    private static $db;
    //Función que realiza la conexión a base de datos
    public static function getConnection(){
        try{
            if(empty(self::$db)){
                //$pdo = new PDO('mysql:host=guabba.com;dbname=guabba_salubridad;charset=utf8','guabba_saludos','12345678');
                //$pdo = new PDO('mysql:host=localhost;dbname=capitan;charset=utf8','root','');
                $pdo = new PDO('mysql:host=192.185.25.45;dbname=guabba_capitan2;charset=utf8','guabba_root','Aa12345678');
                //Sirve para indicar al PDO que todo lo que retorne sean objetos
                $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_OBJ);
                //Sirve para indicar que si encuentra error, los muestre
                $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                self::$db = $pdo;
            }
            return self::$db;
        } catch (\Throwable $e){
            $saltodelinea = '\n';
            echo "<script language=\"javascript\">alert(\"Error Critico. Por favor, regrese más tardecito. " . $saltodelinea . "Nuestros mejores técnicos dejaron su juanecito a medio comer y ya van camino a resolver el problema.\");</script>";
            exit;
        }
    }
}