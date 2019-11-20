<?php
/*
 * AppVet, proyecto desarrollado con la arquitectura MVC, PHP 7 y Mysql
 * Index.php es el archivo principal, el cual administra las vistas. Todas las vistas pasan por este archivo, de la siguiente manera:
 * index.php?c=Controlador&a=accion
 * donde Controlador es el nombre de la Clase (ubicado en la carpeta app/controllers)
 * y accion es el nombre del método al cual se hace referencia(ubicado dentro de la clase definida en Controlador)
 * Ambos son parametros que se envian a este archivo a traves del método GET
*/
// Errores de PHP a Try/Catch
function exception_error_handler($severidad, $mensaje, $fichero, $línea) {
    if (!(error_reporting() & $severidad)) {
        // Este código de error no está incluido en error_reporting
        return;
    }
}
set_error_handler("exception_error_handler");
session_start();
// path
define('_VIEW_PATH_', 'app/views/');
//Llamado a todos los controladores, si no se encuentra aqui algun controlador, no podra ser utilizado en ninguna parte del sistema
require_once 'app/controllers/UsuarioController.php';
require_once 'app/controllers/TorneoController.php';
require_once 'app/controllers/EmpresaController.php';
require_once 'app/controllers/ErrorController.php';
require_once 'app/controllers/ForoController.php';
//$_GET['key_mobile'] fue creado para que la app pueda acceder a las clases y metodos definidos en la parte web
//si ya inicio sesion:
if(isset($_SESSION['contr_inv_id']) || $_GET['key_mobile']=='123456asdfgh'){
    //recibo la accion, en caso de estar vacio le envio index
    $accion = $_GET['a'] ?? 'index';
    if ($accion == 'loguearse'){
        $c = sprintf(
            '%sController',
            'Usuario'
        );
        $a = 'loguearse';
        $c = trim(ucfirst($c));
        $a = trim(strtolower($a));
        $controller = new $c;
        $controller->$a();
    } else {
        $c = sprintf(
            '%sController',
            $_GET['c'] ?? 'Usuario'
        );
        $a = $accion;
        $c = trim(ucfirst($c));
        $a = trim(strtolower($a));
        try{
            $controller = new $c;
            $controller->$a();
        } catch (\Throwable $e){
            $c = sprintf(
                '%sController',
                'Error'
            );
            $a = 'error';
            $c = trim(ucfirst($c));
            $a = trim(strtolower($a));
            $controller = new $c;
            $controller->$a();
        }
    }
}else{
    $accion = $_GET['a'] ?? '';
        switch ($accion){
            case 'login':
                $c = sprintf(
                    '%sController',
                    'Usuario'
                );
                $a = 'login';
                $c = trim(ucfirst($c));
                $a = trim(strtolower($a));
                $controller = new $c;
                $controller->$a();
                break;
            case 'loguearse':
                $c = sprintf(
                    '%sController',
                    'Usuario'
                );
                $a = 'loguearse';
                $c = trim(ucfirst($c));
                $a = trim(strtolower($a));
                $controller = new $c;
                $controller->$a();
                break;
            default:
                $c = sprintf(
                    '%sController',
                    'Usuario'
                );
                $a = 'loguearse';
                $c = trim(ucfirst($c));
                $a = trim(strtolower($a));
                $controller = new $c;
                $controller->$a();
                break;
    }
}