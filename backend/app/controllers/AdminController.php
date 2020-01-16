<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 26/08/2019
 * Time: 23:22
 */
require 'app/models/User.php';
require 'app/models/Empresa.php';
class AdminController{
    private $crypt;
    private $nav;
    private $log;
    private $user;
    private $empresa;
    private $validate;
    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->log = new Log();
        $this->user = new User();
        $this->empresa = new Empresa();
        $this->validate = new Validate();
    }
    //Vistas
    public function index(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $fecha = date("Y-m-d");
            $hora = date("H");
            $resources = [];
            for ($i=$hora;$i<24;$i++) {
                $model = $this->empresa->listar_canchas_libres_por_hora($fecha,$i);
                $resources[] = array(
                    $i => $model
                );
            }
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'admin/index.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function busqueda_avanzada(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $empresas =$this->empresa->listar_empresas_por_id_ciudad('Iquitos');
            $dia_Actual = date('D');
            $hora_Actual = date('H');
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'admin/busqueda_avanzada.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
}