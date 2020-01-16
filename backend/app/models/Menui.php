<?php
/**
 * Created by PhpStorm.
 * User: Cesar Jose Ruiz
 * Date: 08/04/2019
 * Time: 00:28
 */

class Menui{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }
    //FUNCION USADA EN EL MENU INDEX
    //Sirve para leer los permisos al momento de solicitar index
    public function verificar_rol_usuario($id_role, $view, $option){
        $validate = false;
        try{
            $sql = "select m.menu_status, o.optionm_status from role r inner join rolemenu rl on r.id_role = rl.id_role inner join menu m on rl.id_menu = m.id_menu inner join optionm o on m.id_menu = o.id_menu where rl.id_role = ? and m.menu_controller = ? and o.optionm_function = ? and m.menu_status = 1 limit 1";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_role, $view, $option]);
            $result = $stm->fetch();
            if(isset($result->optionm_status)){
                if($result->optionm_status == 1){
                    $validate = true;
                }
            }
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $validate = false;
        }
        return $validate;
    }

    public function verificar_estado_usuario($user_nickname){
        try{
            $sql = 'select user_status from user where user_nickname = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$user_nickname]);
            $consulta = $stm->fetch();
            $permiso = $consulta->user_status;
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $permiso = 0;
        }
        return $permiso;
    }

    public function singOut(){
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