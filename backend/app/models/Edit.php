<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 28/08/2019
 * Time: 17:59
 */
class Edit{
    private $pdo;
    private $log;
    public function __construct()
    {
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }

    public function save($model){
        $result = 2;
        try {
            $fecha = date("Y-m-d H:i:s");
            if(empty($model->id_user)){
                $result = 2;
            } else {
                $sql = "update user 
                set
                user_nickname = ?,
                user_modified_at = ?
                where id_user = ?";
                $stm = $this->pdo->prepare($sql);
                $stm->execute([
                    $model->user_nickname,
                    $fecha,
                    $model->id_user
                ]);
                unset($_SESSION['id_userededit']);
                $result = 1;
            }
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function edit_nickname($model){
        try {
            $fecha = date("Y-m-d H:i:s");
            $sql = "update user set user_nickname = ?, user_modified_at = ? where id_user = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->user_nickname,
                $fecha,
                $model->id_user
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
}