<?php
/**
 * Created by PhpStorm.
 * User: CesarJose39
 * Date: 22/10/2018
 * Time: 17:50
 */

class ErrorController{
    public function __construct()
    {

    }

    public function error(){
        require _VIEW_PATH_ . 'error/error.php';
    }

    public function index(){
        require _VIEW_PATH_ . 'error/error.php';
    }
}