<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 28/08/2019
 * Time: 16:14
 */
class Userg{
    private $pdo;
    private $log;
    public function __construct()
    {
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }

    //Listar Toda La Info Sobre Usuarios
    public function listAll(){
        try{
            $sql = 'select * from user u inner join person p on u.id_person = p.id_person inner join role r on u.id_role = r.id_role where r.id_role <> 2';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }

    //Listar Toda La Info Sobre Usuarios
    public function listAll_wsu(){
        try{
            $sql = 'select * from user u inner join person p on u.id_person = p.id_person inner join role r on u.id_role = r.id_role';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    //Listar Toda La Info Sobre Un Usuario
    public function listAllbyUser($id){
        try{
            $sql = 'select * from user u inner join person p on u.id_person = p.id_person where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }

    public function listEmpresaUser($id){
        try{
            $sql = 'select * from empresa_usuario where id_user = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function validateUser($nickname){
        try{
            $sql = 'select * from user u where user_nickname = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$nickname]);
            $re = $stm->fetchAll();
            if(count($re) > 0){
                $result = true;
            } else {
                $result = false;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }

    public function validateDNI($dni){
        try{
            $sql = 'select * from person where person_dni = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$dni]);
            $re = $stm->fetchAll();
            if(count($re) > 0){
                $result = true;
            } else {
                $result = false;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }

    public function selectNickname($id){
        try{
            $sql = 'select user_nickname from user u where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $re = $stm->fetch();
            $result = $re->user_nickname;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = "";
        }
        return $result;
    }

    //Listar Un Unico Usuario por ID
    public function list($id){
        try{
            $sql = 'select * from user where id_user = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
}