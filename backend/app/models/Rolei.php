<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 26/08/2019
 * Time: 0:29
 */

class Rolei{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }
    //FUNCION USADA EN API
    //SIRVE PARA VERIFICAR SI EL ACCESO ESTÁ PERMITITDO
    public function verificar_permisos_rol($id_role, $controller, $accionp){
        $validate = false;
        try{
            $sql = "select m.menu_status, p.permit_status from role r inner join rolemenu rl on r.id_role = rl.id_role inner join menu m on rl.id_menu = m.id_menu inner join optionm o on m.id_menu = o.id_menu inner join permit p on o.id_optionm = p.id_optionm where rl.id_role = ? and m.menu_controller = ? and p.permit_action = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_role, $controller, $accionp]);
            $result = $stm->fetchAll();
            if(count($result) > 0){
                if($result[0]->menu_status == 1 && $result[0]->permit_status == 1){
                    $validate = true;
                } else {
                    $validate = false;
                }
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $validate = false;
        }
        return $validate;
    }

    public function verificar_permisos_($id_role, $controller){
        $validate = false;
        try{
            $sql = "select m.menu_status from role r inner join rolemenu rl on r.id_role = rl.id_role inner join menu m on rl.id_menu = m.id_menu where rl.id_role = ? and m.menu_controller = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_role, $controller]);
            $result = $stm->fetchAll();
            if(count($result) > 0){
                if($result[0]->menu_status == 1){
                    $validate = true;
                } else {
                    $validate = false;
                }
            }
        } catch (Exception $e){
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

    public function verificar_estado_usuario_id($id){
        try{
            $sql = 'select user_status from user where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $consulta = $stm->fetch();
            $permiso = $consulta->user_status;
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $permiso = 0;
        }
        return $permiso;
    }
    //Obtener Role Usuario
    public function obtener_rol($id){
        try{
            $sql = 'select id_role from user where id_user = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $consulta = $stm->fetch();
            $id = $consulta->id_role;
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $id = 0;
        }
        return $id;
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