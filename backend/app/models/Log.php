<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 23/08/2019
 * Time: 11:53
 */
//La clase "Log" tiene por finalizar recuperar todos los informes de errores que generan los Try/Catch en el sistema.
class Log{
    //Inicializacion de variables a usar
    public function __construct()
    {
        $this->path     = "log/" . date("Y");
        $this->pathfull = $this->path  . "/" . date("m") ;
        $this->filename = "log-";
        $this->date     = date("Y-m-d");
        $this->hour     = date('H:i:s');
        $this->ip       = ($_SERVER['REMOTE_ADDR']) ? $_SERVER['REMOTE_ADDR'] : 0;
    }

    public function insert($text, $location)
    {   //La variable $log genera el informe de error enviado por el manejo de errores del sistema
        $log    = $this->date . " " . $this->hour . "[UTC -5] [ip] " . $this->ip . " [location] " . $location . " [text] " . $text . PHP_EOL;
        //El if valida si el directorio completo existe "log/YYYY/MM"
        if(is_dir($this->pathfull)){
            //Crea o ingresa la informacion en el archivo txt correspondiente
            $result = (file_put_contents($this->pathfull . "/" . $this->filename . $this->date . ".txt", $log, FILE_APPEND)) ? 1 : 0;
        } else {
            //En caso no existe, este if valida si el directorio del año existe "log/YYYY"
            if(is_dir($this->path)){
                //Crea el directorio del mes correspondiente e ingresa la informacion
                mkdir($this->pathfull, 0700);
                $result = (file_put_contents($this->pathfull . "/" . $this->filename . $this->date . ".txt", $log, FILE_APPEND)) ? 1 : 0;
            } else {
                //Si no existe, crea los directorios correspondientes e ingresa el contenido en el txt
                mkdir($this->path, 0700);
                mkdir($this->pathfull, 0700);
                $result = (file_put_contents($this->pathfull . "/" . $this->filename . $this->date . ".txt", $log, FILE_APPEND)) ? 1 : 0;
            }
        }
        return $result;
    }

}