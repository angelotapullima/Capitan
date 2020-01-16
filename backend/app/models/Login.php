<?php
/**
 * Created by PhpStorm.
 * User: CesarJose39
 * Date: 06/10/2018
 * Time: 0:17
 */

class Login{
    private $pdo;
    private $log;
    public function __construct(){
        $this->log = new Log();
        $this->pdo = Database::getConnection();
    }
    //Validar Datos
    public function singIn($model){
        try{
            $sql = 'Select * from user u inner join person p on u.id_person = p.id_person inner join role r on r.id_role = u.id_role where u.user_nickname = ? and u.user_status = 1 and u.id_role <> 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->user_nickname
            ]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), 'Login|singIn');
            $result = 2;
        }
        if($result == 2){
            return $result;
        } else if((empty($result))) {
            return 3;
        } else {
            return $result;
        }

    }
    //Update Last Login
    public function last_login($id){
        try{
            $date_now = date("Y-m-d H:i:s");
            $sql = 'update user set user_last_login = ? where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $date_now,
                $id
            ]);
            $result = true;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }
    //Roles por Usuario
    public function role_per_user($id){
        try{
            $sql = 'select m.id_menu, m.menu_name, m.menu_controller from menu m inner join rolemenu r on m.id_menu = r.id_menu inner join role r2 on r.id_role = r2.id_role where r.id_role = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
}