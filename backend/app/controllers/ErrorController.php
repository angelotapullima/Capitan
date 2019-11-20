<?php

use Exception;
class ErrorController{

    public function error(){
        require _VIEW_PATH_ . 'error/error.php';
    }
}