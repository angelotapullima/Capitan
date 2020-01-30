<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 27/08/2019
 * Time: 21:45
 */
class Person{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }

    public function listwithout(){
        try {
            $sql = 'select * from person p where p.id_person <> 2 and not exists (select null from user u where u.id_person = p.id_person)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    public function listperson($id_person){
        try {
            $sql = 'select * from person where id_person = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_person]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    public function deletePerson($id_person){
        try {
            $sql = 'delete from person where id_person = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $id_person
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    //Listar Toda La Info Sobre Personas
    public function listAll(){
        try{
            $sql = 'select * from person';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
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

    public function listAllFree(){
        try{
            $sql = 'select * from person p where not exists (select null from user u where u.id_person = p.id_person)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }

    //Listar Una Unica Persona por ID
    public function list($id){
        try{
            $sql = 'select * from person where id_person = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }

    public function listByDni($dni){
        try{
            $sql = 'select * from person where person_dni = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$dni]);
            $resultado = $stm->fetch();
            $result = $resultado->id_person;

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 0;
        }
        return $result;
    }
    public function listByMicrotime($dni){
        try{
            $sql = 'select * from person where person_microtime = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$dni]);
            $resultado = $stm->fetch();
            $result = $resultado->id_person;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 0;
        }
        return $result;
    }

    //Guardar o Editar Informacion de Role
    public function save($model){
        try {
            $fecha = date('Y-m-d H:i:s');
            if(empty($model->id_person)){
                $sql = 'insert into person(
                    person_name, person_surname, person_dni, person_birth, person_number_phone, person_genre, person_address,person_created_at,person_modified_at,person_microtime
                    ) values(?,?,?,?,?,?,?,?,?,?)';
                $stm = $this->pdo->prepare($sql);
                $stm->execute([
                    $model->person_name,
                    $model->person_surname,
                    $model->person_dni,
                    $model->person_birth,
                    $model->person_number_phone,
                    $model->person_genre,
                    $model->person_address,
                    $fecha,
                    $fecha,
                    $model->microtime
                ]);

            } else {
                $sql = "update person
                set
                person_name = ?,
                person_surname = ?,
                person_birth = ?,
                person_number_phone = ?,
                person_genre = ?,
                person_address = ?
                where id_person = ?";

                $stm = $this->pdo->prepare($sql);
                $stm->execute([
                    $model->person_name,
                    $model->person_surname,
                    $model->person_birth,
                    $model->person_number_phone,
                    $model->person_genre,
                    $model->person_address,
                    $model->id_person,
                ]);
                unset($_SESSION['id_persone']);
                unset($_SESSION['id_personeinfo']);
            }
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    //Borrar un Registro
    public function delete($id){
        try{
            $sql = 'delete from person where id_person = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

    public function deletedni($id){
        try{
            $sql = 'delete from person where person_dni = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function deletemicrotime($microtime){
        try{
            $sql = 'delete from person where person_microtime = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$microtime]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function set_usuario_direccion($direccion,$id){
        try {
            $sql = 'update person set person_address = ? where id_person = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $direccion,$id
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
}