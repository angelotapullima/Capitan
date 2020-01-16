<?php
class Login_Auth{
    private $pdo;
    private $log;
    public function __construct(){
        $this->log = new Log();
        $this->pdo = Database_Auth::getConnection();
    }
    public function login($nickname){
        try{
            $sql = 'select * from user u inner join person p on u.id_person = p.id_person where u.user_nickname = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $nickname
            ]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        if($result == 2){
            //If result = 2, a error has ocurred, so catch was used
            return $result;
        } else if(empty($result)) {
            //If result is empty, the sql_query does not found a user with the nickname required, so the code status is 3
            return 3;
        } else {
            //If all OK, the code status is 1
            return $result;
        }
    }
    public function getUserbyemail($email){
        try{
            $sql = 'select * from user u where u.user_email = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $email
            ]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        if($result == 2){
            //If result = 2, a error has ocurred, so catch was used
            return $result;
        } else if(empty($result)) {
            //If result is empty, the sql_query does not found a user with the nickname required, so the code status is 3
            return 3;
        } else {
            //If all OK, the code status is 1
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

    public function save_token_recovery_pass($token,$id){
        try{
            $day = date('d') + 1;
            $date = date('Y-m') . "-".$day." ".date('H:i:s');
            $sql = 'update user set user_recovery_pass = ?,user_recovery_pass_date=? where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $token,
                $date,
                $id
            ]);
            $result = true;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }
    public function verify_recovery_pass($id,$token){
        try{
            $sql = 'select * from user where id_user=? and user_recovery_pass = ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $id,$token
            ]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        if($result == 2){
            //If result = 2, a error has ocurred, so catch was used
            return $result;
        } else if(empty($result)) {
            //If result is empty, the sql_query does not found a user with the nickname required, so the code status is 3
            return 3;
        } else {
            //If all OK, the code status is 1
            return $result;
        }
    }
    public function create_person($model){
        $date_now = date("Y-m-d H:i:s");
        try {
            $sql = 'insert into person (person_name, person_surname, person_dni, person_birth, person_number_phone, person_genre, person_address, person_city, person_country, person_created_at, person_modified_at) values (?,?,?,?,?,?,?,?,?,?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->person_name,
                $model->person_surname,
                $model->person_dni,
                $model->person_birth,
                $model->person_number_phone,
                $model->person_genre,
                $model->person_address,
                $model->person_city,
                $model->person_country,
                $date_now,
                $date_now
            ]);
            $result = true;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }
    public function list_by_dni($dni){
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
    public function create_user($model)
    {
        $date_now = date("Y-m-d H:i:s");
        try {
            $sql = 'insert into user (id_person, user_nickname, user_password, user_email, user_image, user_status, user_created_at, user_last_login, user_modified_at,user_email_validate_status) values (?,?,?,?,?,?,?,?,?,0)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->id_person,
                $model->user_nickname,
                $model->user_password,
                $model->user_email,
                $model->user_image,
                1,
                $date_now,
                $date_now,
                $date_now
            ]);
            $result = true;
        } catch (Exception $e) {
            $this->log->insert($e->getMessage(), get_class($this) . '|' . __FUNCTION__);
            $result = false;
        }
        return $result;
    }
    public function list_last_id(){
        try{
            $sql = 'select id_user from user order by id_user desc limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function delete_person_by_dni($id){
        try{
            $sql = 'delete from person where person_dni = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = true;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }
    public function save_email_validate_code($token,$id)
    {
        try {
            $sql = 'update user set user_email_validate_code = ? where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $token,
                $id
            ]);
            $result = true;
        } catch (Exception $e) {
            $this->log->insert($e->getMessage(), get_class($this) . '|' . __FUNCTION__);
            $result = false;
        }
        return $result;
    }
}
