<?php
/**
 * Created by PhpStorm.
 * User: Cesar Jose Ruiz
 * Date: 08/04/2019
 * Time: 00:24
 */
//Permite verificar si existe una sesión iniciada y crear las variables de sesión
//Si existe una sesión iniciada, ya no realiza la verificación
if(!isset($_SESSION['id_user'])){
    //Valida si todas las cookies están creadas
    if(isset($_COOKIE['id_user']) && isset($_COOKIE['id_person']) && isset($_COOKIE['user_nickname']) && isset($_COOKIE['user_nickname']) && isset($_COOKIE['role']) && isset($_COOKIE['role_name'])) {
        //Crea las variables de sesión copiando el contenido de las cookies guardadas
        $_SESSION['id_user'] = $_COOKIE['id_user'];
        $_SESSION['id_person'] = $_COOKIE['id_person'];
        $_SESSION['user_nickname'] = $_COOKIE['user_nickname'];
        $_SESSION['user_image'] = $_COOKIE['user_image'];
        $_SESSION['person_name'] = $_COOKIE['person_name'];
        $_SESSION['person_surname'] = $_COOKIE['person_surname'];
        //$_SESSION['person_dni'] = $_COOKIE['person_dni'];
        //$_SESSION['person_genre'] = $_COOKIE['person_genre'];
        $_SESSION['role'] = $_COOKIE['role'];
        $_SESSION['role_name'] = $_COOKIE['role_name'];
    } else {
        //Si no existen todas las cookies creadas, por precaución destruye todas las variables posiblemente creadas
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
    }
}