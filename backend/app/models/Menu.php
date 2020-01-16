<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 27/08/2019
 * Time: 16:23
 */
class Menu{
    private $pdo;
    private $log;
    public function __construct()
    {
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }

    public function list(){
        try{
            $sql = "select * from menu";
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;

    }

    public function verificatePassword($user, $pass){
        $result = false;
        try{
            $sql = "Select user_password from user where user_nickname = ? and user_status = 1";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$user]);
            $info = $stm->fetch();

            if(password_verify($pass, $info->user_password)){
                $result = true;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }

    public function save($model){
        try {
            if(empty($model->id_menu)){
                $sql = 'insert into menu(
                    menu_name, menu_icon, menu_controller, menu_order, menu_status, menu_show
                    ) values(?,?,?,?,?,?)';
                $stm = $this->pdo->prepare($sql);
                $stm->execute([
                    $model->menu_name,
                    $model->menu_icon,
                    $model->menu_controller,
                    $model->menu_order,
                    $model->menu_status,
                    $model->menu_show
                ]);
            } else {
                $sql = "update menu
                set
                menu_name = ?,
                menu_icon = ?,
                menu_controller = ?,
                menu_order = ?,
                menu_status = ?,
                menu_show = ?
                where id_menu = ?";

                $stm = $this->pdo->prepare($sql);
                $stm->execute([
                    $model->menu_name,
                    $model->menu_icon,
                    $model->menu_controller,
                    $model->menu_order,
                    $model->menu_status,
                    $model->menu_show,
                    $model->id_menu
                ]);
            }
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    public function listMenu($id){
        try{
            $sql = "select * from menu where id_menu = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;

    }

    public function deleteMenu($id_menu){
        try{
            $sql = "update menu set menu_status = 0 where id_menu = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_menu]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;

    }

    public function listMenuRole($id){
        try{
            $sql = "select r2.id_role, r2.role_name, r2.role_description from rolemenu r inner join menu m on r.id_menu = m.id_menu inner join role r2 on r.id_role = r2.id_role where r.id_menu = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;

    }

    public function listRole(){
        try{
            $sql = "select * from role";
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }

    public function listMenuName($id){
        try{
            $sql = "select menu_name from menu where id_menu = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
            $name = $result->menu_name;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $name = "";
        }
        return $name;

    }

    public function listOptionName($id){
        try{
            $sql = "select optionm_name from optionm where id_optionm = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
            $name = $result->optionm_name;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $name = "";
        }
        return $name;

    }

    public function listOptionIdMenu($id){
        try{
            $sql = "select id_menu from optionm where id_optionm = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
            $name = $result->id_menu;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $name = "";
        }
        return $name;

    }

    public function insertRole($id_menu, $id_role){
        try{
            $sql = "insert into rolemenu (id_role, id_menu) values(?,?)";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_role, $id_menu]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;

    }

    public function deleteRole($id_menu, $id_role){
        try{
            $sql = "delete from rolemenu where id_role = ? and id_menu = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_role, $id_menu]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;

    }

    public function listOptionsPerMenu($id){
        try{
            $sql = "select * from optionm where id_menu = ? order by optionm_order desc";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }

    public function listOption($id){
        try{
            $sql = "select * from optionm where id_optionm = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }

    public function saveOption($model){
        try {
            if(empty($model->id_optionm)){
                $sql = 'insert into optionm(
                    id_menu, optionm_name, optionm_function, optionm_show, optionm_status, optionm_order
                    ) values(?,?,?,?,?,?)';
                $stm = $this->pdo->prepare($sql);
                $stm->execute([
                    $model->id_menu,
                    $model->optionm_name,
                    $model->optionm_function,
                    $model->optionm_show,
                    $model->optionm_status,
                    $model->optionm_order
                ]);
                unset($_SESSION['id_menuee']);
            } else {
                $sql = "update optionm
                set
                optionm_name = ?,
                optionm_function = ?,
                optionm_show = ?,
                optionm_status = ?,
                optionm_order = ?
                where id_optionm = ?";

                $stm = $this->pdo->prepare($sql);
                $stm->execute([
                    $model->optionm_name,
                    $model->optionm_function,
                    $model->optionm_show,
                    $model->optionm_status,
                    $model->optionm_order,
                    $model->id_optionm
                ]);
                unset($_SESSION['id_optionme']);
            }
            $result = 1;

        } catch (Exception $e){
            //throw new Exception($e->getMessage());
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    public function deleteOption($id_optionm){
        try{
            $sql = "update optionm set optionm_status = 0 where id_optionm = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_optionm]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;

    }

    public function listPermitPerOption($id){
        try{
            $sql = "select * from permit where id_optionm = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }

    public function listPermit($id){
        try{
            $sql = "select * from permit where id_permit = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }

    public function savePermit($model){
        try {
            if(empty($model->id_permit)){
                $sql = 'insert into permit(
                    id_optionm, permit_action, permit_status
                    ) values(?,?,?)';
                $stm = $this->pdo->prepare($sql);
                $stm->execute([
                    $model->id_optionm,
                    $model->permit_action,
                    $model->permit_status
                ]);
                unset($_SESSION['id_optionmee']);
            } else {
                $sql = "update permit
                set
                permit_action = ?,
                permit_status = ?
                where id_permit = ?";

                $stm = $this->pdo->prepare($sql);
                $stm->execute([
                    $model->permit_action,
                    $model->permit_status,
                    $model->id_permit
                ]);
                unset($_SESSION['id_permite']);
            }
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    public function deletePermit($id_permit){
        try{
            $sql = "delete from permit where id_permit = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_permit]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
}