<?php
/**
 * Created by PhpStorm.
 * User: CesarJose39
 * Date: 21/09/2018
 * Time: 0:34
 */
//Nunca dejes que nadie te sorprenda, porque de un momento a otro puedes PUTO EL QUE LEA ESTO :)
//Aqui implementaré la api con control de sessiones y gestion de roles

//Establecer zona horaria
date_default_timezone_set('America/Lima');
//Variables Globales
require 'core/globals.php';
//LLamada a archivo gestor de base de datos
require 'core/Database_Auth.php';
//LLamada a archivo gestor de base de datos
require 'core/Database.php';
//Levantamiento del Log para registro de errores
require 'app/models/Log.php';
//Levantamiento de registro de roles y permisos para acceso a vistas
require 'app/models/Rolei.php';
//Inicio clase para la encriptacion de contenido
require 'app/models/Crypt.php';
//Inicio clase para limpieza de contenido
require 'app/models/Clean.php';
//Inicio clase para la validacion de duplicados
require 'app/models/Validate.php';
//Clase para validación de token
require 'app/models/Token.php';
$tokenizacion = new Token();

//Inicialización de clases
$errores = new Log();
$rolei = new Rolei();

// Manejo de Errores Personalizado de PHP a Try/Catch
function exception_error_handler($severidad, $mensaje, $fichero, $linea) {
    $cadena =  '[LEVEL]: ' . $severidad . ' IN ' . $fichero . ': ' . $linea . '[MESSAGGE]' . $mensaje . "\n";
    $guardar = new Log();
    $guardar->insert($cadena, "Excepcion No Manejada");
    //echo $cadena;
}

//Para manejo de caracteres
header("Content-Type: text/html;charset=utf-8");
//Para Permitir CORS
header('Access-Control-Allow-Origin: *');
header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
header('Access-Control-Allow-Methods: GET, POST');
//Especificar el manejo de errores personalizados
set_error_handler("exception_error_handler");
//Inicio de Sesion
session_start();

//Inicio de Código de Verificación de Permisos

//Captura de Datos para Obtener el Controlador y la Accion

//Inicio de codigo de la api
//Verificar existencia de los archivos
$controlador = $_GET['c'] ?? "none";
$controlador = ucfirst($controlador);
$accion = $_GET['a'] ?? "none";
$function_action = $controlador . "|" . $accion;
$archivo = 'app/controllers/' . $controlador . 'Controller.php';

if(file_exists($archivo)){
    //Variable Para Determinar Si Procede O No La Petición
    $autorizado = false;
    $crypt = new Crypt();
    if(isset($_SESSION['role'])){
        $role = $_SESSION['role'];
        $rol = $crypt->decrypt($role, _FULL_KEY_);
        $autorizado = $rolei->verificar_permisos_rol($rol, $controlador, $accion);
        $permiso = $rolei->verificar_estado_usuario($crypt->decrypt($_SESSION['user_nickname'], _FULL_KEY_));
    } else {
        if(isset($_POST['app']) && $_POST['app'] == true){
            if(isset($_POST['token'])) {
                //Función que verifica si el token proporcionado es válido
                $validacion = $tokenizacion->validate_token($_POST['token']);
                if($validacion){
                    $usuario = $crypt->tripledecrypt($_POST['token']);
                    $rol = $rolei->obtener_rol($usuario[0]);
                    $autorizado = $rolei->verificar_permisos_($rol, $controlador);
                    $permiso = $rolei->verificar_estado_usuario_id($usuario[0]);
                } else {
                    //Si $validacion = false, se responde el json con la respuesta.
                    $response = array("code" => 2,"message" => 'TOKEN INVALIDO');
                    $data = array("result" => $response);
                    echo json_encode($data);
                }
            } else {
                $autorizado = $rolei->verificar_permisos_rol(1, $controlador, $accion);
                $permiso = 1;
            }
        } else {
            $autorizado = $rolei->verificar_permisos_rol(1, $controlador, $accion);
            $permiso = 1;
        }
    }
    //Si $autorizado =  true Entra Aquí, Descomentar La Linea Siguiente Si Sólo Se Quiere Probar Funciones
    //$autorizado = true
    if($autorizado && $permiso == 1){
        try{
            //Entra Aquí Si La Clase Y La Funcion Existen
            require $archivo;
            $clase = sprintf('%sController', $_GET['c'] ?? $controlador);
            $clase = trim(ucfirst($clase));
            $accion = trim(strtolower($accion));
            $controller = new $clase;
            if(method_exists($controller,$accion)){
                $controller->$accion();
            } else {
                $errores->insert('Intento de acceder a '. $function_action . ' fallido. La funcion no existe.', $function_action);
                $response = array("code" => 2,"message" => 'Intento de acceder a '. $function_action . ' fallido. La funcion no existe.');
                $data = array("result" => $response);
                echo json_encode($data);
            }
        } catch (Throwable $e){
            $errores->insert($e->getMessage(), $function_action);
            $response = array("code" => 2,"message" => 'ERROR');
            $data = array("result" => $response);
            echo json_encode($data);
        }
    } else {
        $errores->insert("SIN PERMISOS SUFICIENTES", $function_action);
        if($permiso == 0){
            $rolei->singOut();
        }
        $response = array("code" => 2,"message" => 'SIN PERMISOS');
        $data = array("result" => $response);
        echo json_encode($data);
    }
} else {
    //Acciones si el archivo no existe
    //Automaticamente, notificar error
    $errores->insert("ACCESO A CONTROLADOR NO EXISTENTE", $function_action);
    $response = array("code" => 2,"message" => 'CONTROLADOR DESCONOCIDO');
    $data = array("result" => $response);
    echo json_encode($data);
}