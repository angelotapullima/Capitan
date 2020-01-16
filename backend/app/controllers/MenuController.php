<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 27/08/2019
 * Time: 20:39
 */
require 'app/models/Menu.php';
class MenuController{
    private $crypt;
    private $nav;
    private $log;
    private $menu;
    public function __construct()
    {
        $this->menu = new Menu();
        $this->crypt = new Crypt();
        //$this->nav = new Navbar();
        $this->log = new Log();
    }
    //Vistas
    public function list(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $menu = $this->menu->list();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'menu/list.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function add(){
        try{
            unset($_SESSION['id_menue']);
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'menu/add.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function edit(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $_SESSION['id_menue'] = $id;
            $menue = $this->menu->listMenu($id);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'menu/edit.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function icons(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'menu/icons.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function role(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));

            $id_menu = $_GET['id'] ?? 0;
            if($id_menu == 0){
                throw new Exception('ID Sin Declarar');
            }
            $menust = $this->menu->listMenuRole($id_menu);
            $menusf = $this->menu->listRole();

            $menuname = $this->menu->listMenuName($id_menu);

            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'menu/role.php';


        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function functions(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id_menu= $_GET['id'] ?? 0;
            if($id_menu == 0){
                throw new Exception('ID Sin Declarar');
            }
            $menuname = $this->menu->listMenuName($id_menu);
            $options = $this->menu->listOptionsPerMenu($id_menu);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'option/list.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function addf(){
        try{
            unset($_SESSION['id_optionme']);
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id_menu= $_GET['id'] ?? 0;
            if($id_menu == 0){
                throw new Exception('ID Sin Declarar');
            }
            $_SESSION['id_menuee'] = $id_menu;
            $menuname = $this->menu->listMenuName($id_menu);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'option/add.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function editf(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id_optionm= $_GET['id'] ?? 0;
            if($id_optionm == 0){
                throw new Exception('ID Sin Declarar');
            }
            $_SESSION['id_optionme'] = $id_optionm;
            $opt = $this->menu->listOption($id_optionm);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'option/edit.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function listp(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id_optionm= $_GET['id'] ?? 0;
            if($id_optionm == 0){
                throw new Exception('ID Sin Declarar');
            }
            $optionname = $this->menu->listOptionName($id_optionm);
            $id_menu= $this->menu->listOptionIdMenu($id_optionm);
            $permits = $this->menu->listPermitPerOption($id_optionm);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'permit/list.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function addp(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id_optionm= $_GET['id'] ?? 0;
            if($id_optionm == 0){
                throw new Exception('ID Sin Declarar');
            }
            $_SESSION['id_optionmee'] = $id_optionm;
            $optionname = $this->menu->listOptionName($id_optionm);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'permit/add.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }

    public function editp(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_PASS_));
            $id_permit= $_GET['id'] ?? 0;
            if($id_permit == 0){
                throw new Exception('ID Sin Declarar');
            }
            $_SESSION['id_permite'] = $id_permit;
            $per = $this->menu->listPermit($id_permit);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';

            require _VIEW_PATH_ . 'permit/edit.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);;
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    //Funciones
    public function save(){
        try{
            $model = new Menu();
            if($this->menu->verificatePassword($this->crypt->decrypt($_SESSION['user_nickname'],_PASS_), $_POST['password'])) {
                if(isset($_SESSION['id_menue'])){
                    $model->id_menu = $_SESSION['id_menue'];
                    $model->menu_name= $_POST['menu_name'];
                    $model->menu_icon= $_POST['menu_icon'];
                    $model->menu_controller= $_POST['menu_controller'];
                    $model->menu_order= $_POST['menu_order'];
                    $model->menu_status= $_POST['menu_status'];
                    $model->menu_show= $_POST['menu_show'];
                    $result = $this->menu->save($model);
                    unset($_SESSION['id_menue']);
                } else {
                    $model->menu_name= $_POST['menu_name'];
                    $model->menu_icon= $_POST['menu_icon'];
                    $model->menu_controller= $_POST['menu_controller'];
                    $model->menu_order= $_POST['menu_order'];
                    $model->menu_status= $_POST['menu_status'];
                    $model->menu_show= $_POST['menu_show'];
                    $result = $this->menu->save($model);
                }
            } else {
                $result = 3;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function deleteMenu(){
        try{
            if($this->menu->verificatePassword($this->crypt->decrypt($_SESSION['user_nickname'],_PASS_), $_POST['password'])) {
                if(isset($_POST['id_menu'])){
                    $result = $this->menu->deleteMenu($_POST['id_menu']);
                }
            } else {
                $result = 3;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function insertRole(){
        try{
            if($this->menu->verificatePassword($this->crypt->decrypt($_SESSION['user_nickname'],_PASS_), $_POST['password'])) {
                if(isset($_POST['id_role']) && isset($_POST['id_menu'])){
                    $result = $this->menu->insertRole($_POST['id_menu'], $_POST['id_role']);
                }
            } else {
                $result = 3;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function deleteRole(){
        try{
            if($this->menu->verificatePassword($this->crypt->decrypt($_SESSION['user_nickname'],_PASS_), $_POST['password'])) {
                if(isset($_POST['id_role']) && isset($_POST['id_menu'])){
                    $result = $this->menu->deleteRole($_POST['id_menu'], $_POST['id_role']);
                }
            } else {
                $result = 3;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function saveOption(){
        try{
            $model = new Menu();
            if($this->menu->verificatePassword($this->crypt->decrypt($_SESSION['user_nickname'],_PASS_), $_POST['password'])) {
                if(isset($_SESSION['id_optionme'])){
                    $model->id_optionm = $_SESSION['id_optionme'];
                    $model->optionm_name= $_POST['optionm_name'];
                    $model->optionm_function= $_POST['optionm_function'];
                    $model->optionm_show= $_POST['optionm_show'];
                    $model->optionm_status= $_POST['optionm_status'];
                    $model->optionm_order= $_POST['optionm_order'];
                    $result = $this->menu->saveOption($model);

                } else {
                    $model->id_menu = $_SESSION['id_menuee'];
                    $model->optionm_name= $_POST['optionm_name'];
                    $model->optionm_function= $_POST['optionm_function'];
                    $model->optionm_show= $_POST['optionm_show'];
                    $model->optionm_status= $_POST['optionm_status'];
                    $model->optionm_order= $_POST['optionm_order'];
                    $result = $this->menu->saveOption($model);
                }
            } else {
                $result = 3;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function deleteOption(){
        try{
            if($this->menu->verificatePassword($this->crypt->decrypt($_SESSION['user_nickname'],_PASS_), $_POST['password'])) {
                if(isset($_POST['id_optionm'])){
                    $result = $this->menu->deleteOption($_POST['id_optionm']);
                }
            } else {
                $result = 3;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function savePermit(){
        try{
            $model = new Menu();
            if($this->menu->verificatePassword($this->crypt->decrypt($_SESSION['user_nickname'],_PASS_), $_POST['password'])) {
                if(isset($_SESSION['id_permite'])){
                    $model->id_permit = $_SESSION['id_permite'];
                    $model->permit_action= $_POST['permit_action'];
                    $model->permit_status= $_POST['permit_status'];
                    $result = $this->menu->savePermit($model);
                } else {
                    $model->id_optionm = $_SESSION['id_optionmee'];
                    $model->permit_action= $_POST['permit_action'];
                    $model->permit_status= $_POST['permit_status'];
                    $result = $this->menu->savePermit($model);
                }
            } else {
                $result = 3;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }

    public function deletePermit(){
        $result = 0;
        try{
            if($this->menu->verificatePassword($this->crypt->decrypt($_SESSION['user_nickname'],_PASS_), $_POST['password'])) {
                if(isset($_POST['id_permit'])){
                    //$this->menu->deletePermit($_POST['id_permit']);
                    $result = $this->menu->deletePermit($_POST['id_permit']);
                }
            } else {
                $result = 3;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }
}