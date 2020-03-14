<?php
require 'app/models/CuentaEmpresa.php';
require 'app/models/User.php';
class CuentaEmpresaController{
    private $crypt;
    private $nav;
    private $log;
    private $cuentaempresa;
    private $user;
    private $validate;
    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->log = new Log();
        $this->cuentaempresa = new CuentaEmpresa();
        $this->user = new User();
        $this->validate = new Validate();
    }
    //Vistas
    public function detalles_cuenta_empresa(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            if(isset($_GET['id'])){
                $cod = $_GET['id'];
                $mostrar=false;
                $empresas = $this->user->listar_cuentas_empresa_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
                if(count($empresas)>0){
                    for($i=0;$i<count($empresas);$i++){
                        if($empresas[$i]->cuentae_codigo==$cod){
                            $mostrar = true;
                        }
                    }
                }
                if($mostrar){
                    $datos_cuenta = $this->cuentaempresa->listar_cuentae_por_codigo($cod);
                    $ultimas_recargas = $this->cuentaempresa->listar_ultimas_recargas_por_codigo($cod);
                    require _VIEW_PATH_ . 'header.php';
                    require _VIEW_PATH_ . 'navbar.php';
                    require _VIEW_PATH_ . 'cuenta_empresa/detalles_cuenta_empresa.php';
                    require _VIEW_PATH_ . 'footer.php';
                }else{
                    $this->log->insert('Id: '.$this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_). ' Intentó ingresar con codigo: '.$cod, get_class($this).'|'.__FUNCTION__);
                    echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
                    echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
                }
            }else{
                $this->log->insert('No ID declarado', get_class($this).'|'.__FUNCTION__);
                echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
                echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
            }
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
}