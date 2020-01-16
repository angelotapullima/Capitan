<?php
/**
 * Created by PhpStorm.
 * User: CesarJose39
 * Date: 15/10/2018
 * Time: 17:49
 */

require 'app/models/Login.php';
//require 'app/models/Login_Auth.php';
require 'app/models/Userg.php';
require 'app/models/Person.php';
require 'app/models/User.php';
class LoginController{
    private $log;
    private $pdo;
    private $login;
    //private $login_auth;
    private $crypt;
    private $clean;
    private $userg;
    private $person;
    private $user;
    private $validate;
    public function __construct()
    {
        $this->log = new Log();
        $this->pdo = Database::getConnection();
        $this->login = new Login();
        //$this->login_auth = new Login_Auth();
        $this->crypt = new Crypt();
        $this->clean = new Clean();
        $this->userg = new Userg();
        $this->user = new User();
        $this->person = new Person();
        $this->validate = new Validate();
    }

    public function index(){
        require _VIEW_PATH_ . 'login/inicio_sesion.php';
    }
    public function registrarme(){
        require _VIEW_PATH_ . 'login/registrarme.php';
    }
    public function recuperar_clave(){
        if(isset($_GET['id']) && isset($_GET['criterio'])){
            require _VIEW_PATH_ . 'login/restaurar_clave.php';
        }else{
            require _VIEW_PATH_ . 'login/recuperar_clave.php';
        }

    }

    public function validar_usuario(){
        try{
            //If All OK, the message does not change
            $message = "THE END";
            $result = 0;
            $user = [];
            $model = new Login();
            $model->user_nickname = $_POST['user'];
            $password = $_POST['pass'];
            $singin = $this->login->singIn($model);
            if($singin == 2 || $singin == 3){
                switch ($singin){
                    case 2:
                        //Code 2: General Error
                        $result = 2;
                        $message = "Code 2: General Error";
                        break;
                    case 3:
                        //Code 3: Wrong Credentials
                        $result = 3;
                        $message = "Code 3: Wrong Credentials";
                        break;
                }
            } else {
                if(password_verify($password, $singin[0]->user_password)){
                    $this->login->last_login($singin[0]->id_user);
                    //Generacion de json si la solicitud es desde app
                    $permisos = [];
                    if(isset($_POST['app']) && $_POST['app'] == true){
                        $user = array(
                            //token firebase, num, hab ,posicion ubigeo,
                            "id_user" => $singin[0]->id_user,
                            "id_person" => $singin[0]->id_person,
                            "user_nickname" => $singin[0]->user_nickname,
                            "user_email" => $singin[0]->user_email,
                            "user_image" => _SERVER_ . $singin[0]->user_image,
                            "person_name" => $singin[0]->person_name,
                            "person_surname" => $singin[0]->person_surname,
                            "person_dni" => $singin[0]->person_dni,
                            "person_birth" => $singin[0]->person_birth,
                            "person_number_phone" => $singin[0]->person_number_phone,
                            "person_genre" => $singin[0]->person_genre,
                            "person_address" => $singin[0]->person_address,
                            "user_num" => $singin[0]->user_num,
                            "user_posicion" => $singin[0]->user_posicion,
                            "user_habilidad" => $singin[0]->user_habilidad,
                            "ubigeo_id" => $singin[0]->ubigeo_id,
                            "token" => $this->crypt->tripleencrypt($singin[0]->user_password, $singin[0]->id_user, $singin[0]->user_created_at),
                            "token_firebase" => $singin[0]->user_token
                        );
                        $permisos = $this->login->role_per_user($singin[0]->id_role);
                    } else {
                        //Generacion de datos de usuario si la solicitud es desde la web
                        if(isset($_POST['remember'])){
                            if($_POST['remember'] == "true"){
                                setcookie('id_user', $this->crypt->encrypt($singin[0]->id_user, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                setcookie('id_person', $this->crypt->encrypt($singin[0]->id_user, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                setcookie('user_nickname', $this->crypt->encrypt($singin[0]->user_nickname, _FULL_KEY_), time() + 365 * 24 * 60 * 60, "/");
                                setcookie('user_image', $this->crypt->encrypt($singin[0]->user_image, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                setcookie('person_name', $this->crypt->encrypt($singin[0]->person_name, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                setcookie('person_surname', $this->crypt->encrypt($singin[0]->person_surname, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                setcookie('person_dni', $this->crypt->encrypt($singin[0]->person_dni, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                setcookie("role", $this->crypt->encrypt($singin[0]->id_role, _FULL_KEY_), time() + 30* 24 * 60 * 60,"/");
                                setcookie("role_name", $this->crypt->encrypt($singin[0]->role_name, _FULL_KEY_), time() + 30* 24 * 60 * 60, "/");
                            }
                        }
                        $_SESSION['id_user'] = $this->crypt->encrypt($singin[0]->id_user, _FULL_KEY_);
                        $_SESSION['id_person'] = $this->crypt->encrypt($singin[0]->id_user, _FULL_KEY_);
                        $_SESSION['user_nickname'] = $this->crypt->encrypt($singin[0]->user_nickname, _FULL_KEY_);
                        $_SESSION['user_image'] = $this->crypt->encrypt($singin[0]->user_image, _FULL_KEY_);
                        $_SESSION['person_name'] = $this->crypt->encrypt($singin[0]->person_name, _FULL_KEY_);
                        $_SESSION['person_surname'] = $this->crypt->encrypt($singin[0]->person_surname, _FULL_KEY_);
                        $_SESSION['person_dni'] = $this->crypt->encrypt($singin[0]->person_surname, _FULL_KEY_);
                        $_SESSION['role'] = $this->crypt->encrypt($singin[0]->id_role, _FULL_KEY_);
                        $_SESSION['role_name'] = $this->crypt->encrypt($singin[0]->role_name, _FULL_KEY_);
                        //$_SESSION['validate_email'] = $this->crypt->encrypt($_POST['email_validate'], _FULL_KEY_);
                        //$_SESSION['id_auth'] = $this->crypt->encrypt($_POST['id_auth'], _FULL_KEY_);
                    }
                    $message = "We did it. Your awesome... and beatiful";
                    $result = 1;
                } else {
                    //Code 3: Wrong Credentials
                    $result = 3;
                    $message = "Code 3: Wrong Credentials";
                }
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            //Code 2: General Error
            $result = 2;
            $message = "Code 2: General Error";
        }

        if(isset($_POST['app']) && $_POST['app'] == true){
            $response = array("code" => $result,"message" => $message);
            $data = array("result" => $response, "data" => $user, "role" => $permisos);
            echo json_encode($data);
        } else {
            $response = array("code" => $result,"message" => $message);
            $data = array("result" => $response);
            echo json_encode($data);
        }
    }
    public function validar_usuario_doble(){
        try{
            $message = "We did it. Your awesome... and beatiful";
            //Start Evaluation Of Data Integrity
            $ok_data = true;
            if(isset($_POST['user']) && isset($_POST['pass'])){
                //Clean Data
                $_POST['user'] = $this->clean->clean_post_str($_POST['user']);
                $_POST['pass'] = $this->clean->clean_post_str($_POST['pass']);
                //Evaluation If All Data is Ok
                $ok_data = $this->clean->validate_post_str($_POST['user'], true, $ok_data, 40);
                $ok_data = $this->clean->validate_post_str($_POST['pass'], true, $ok_data, 64);
            } else {
                $ok_data = false;
            }
            if($ok_data){
                $usuario = $_POST['user'];
                $contrasenha = $_POST['pass'];
                $model = $this->login_auth->login($usuario);
                if(isset($model[0]->id_user)){
                    if($model[0]->user_status == 1){
                        if(password_verify($contrasenha, $model[0]->user_password)){
                            $this->login->last_login($model[0]->id_user);
                            $modelu = new User();
                            $modelp = new Person();
                            $info=$this->user->selectuser_id_auth($model[0]->id_user);
                            $modelu->id_role = 4;
                            if($info->id_user!=null){
                                $modelp->id_person=$info->id_person;
                                $modelu->id_person=$info->id_person;
                                $modelu->id_user=$info->id_user;
                                $modelu->id_role = $info->id_role;
                            }
                            $modelp->person_name= $model[0]->person_name;
                            $modelp->person_surname = $model[0]->person_surname;
                            $modelp->person_dni = $model[0]->person_dni;
                            $modelp->person_birth = $model[0]->person_birth;
                            $modelp->person_number_phone = $model[0]->person_number_phone;
                            $modelp->person_genre = $model[0]->person_genre;
                            $modelp->person_address = $model[0]->person_address;
                            $resultp = $this->person->save($modelp);
                            if($resultp == 1){
                                $modelu->user_nickname= $model[0]->user_nickname;
                                $modelu->id_auth= $model[0]->id_user;
                                $modelu->user_password =  password_hash($contrasenha, PASSWORD_BCRYPT);
                                $modelu->user_email = $model[0]->user_email;
                                $modelu->user_status = 1;
                                $modelu->user_auth_exists = 1;
                                $result = $this->user->save($modelu);
                                if($result != 1){
                                    $this->person->deletedni($model[0]->person_dni);
                                    $result = 2;
                                }else{
                                    $result = 0;
                                    $user = [];
                                    $modelL = new Login();
                                    $modelL->user_nickname = $usuario;
                                    $singin = $this->login->singIn($modelL);
                                    if($singin == 2 || $singin == 3){
                                        switch ($singin){
                                            case 2:
                                                //Code 2: General Error
                                                $result = 2;
                                                $message = "Code 2: General Error";
                                                break;
                                            case 3:
                                                //Code 3: Wrong Credentials
                                                $result = 3;
                                                $message = "Code 3: Wrong Credentials";
                                                break;
                                        }
                                    } else {
                                        if(password_verify($contrasenha, $singin[0]->user_password)){
                                            $this->login->last_login($singin[0]->id_user);
                                            //Generacion de json si la solicitud es desde app
                                            $permisos = [];
                                            if(isset($_POST['app']) && $_POST['app'] == true){
                                                $token_u = $this->crypt->tripleencrypt($model[0]->user_password, $model[0]->id_user, $model[0]->user_created_at);
                                                $user = array(
                                                    "id_auth" => $model[0]->id_user,
                                                    "id_user" => $model[0]->id_user,
                                                    "id_role" => $info->id_role,
                                                    "id_person" => $model[0]->id_person,
                                                    "user_nickname" => $model[0]->user_nickname,
                                                    "user_password" => $contrasenha,
                                                    "user_email" => $model[0]->user_email,
                                                    "user_image" => _SERVER_ . $model[0]->user_image,
                                                    "person_name" => $model[0]->person_name,
                                                    "person_surname" => $model[0]->person_surname,
                                                    "person_dni" => $model[0]->person_dni,
                                                    "person_birth" => $model[0]->person_birth,
                                                    "person_number_phone" => $model[0]->person_number_phone,
                                                    "person_genre" => $model[0]->person_genre,
                                                    "person_address" => $model[0]->person_address,
                                                    "person_city" => $model[0]->person_city,
                                                    "person_country" => $model[0]->person_country,
                                                    "validate_email" => $model[0]->user_email_validate_status,
                                                    "token" => $token_u
                                                );
                                                $permisos = $this->login->role_per_user($singin[0]->id_role);
                                            } else {
                                                //Generacion de datos de usuario si la solicitud es desde la web
                                                if(isset($_POST['remember'])){
                                                    if($_POST['remember'] == "true"){
                                                        setcookie('id_user', $this->crypt->encrypt($singin[0]->id_user, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                                        setcookie('id_person', $this->crypt->encrypt($singin[0]->id_user, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                                        setcookie('user_nickname', $this->crypt->encrypt($singin[0]->user_nickname, _FULL_KEY_), time() + 365 * 24 * 60 * 60, "/");
                                                        setcookie('user_image', $this->crypt->encrypt($singin[0]->user_image, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                                        setcookie('person_name', $this->crypt->encrypt($singin[0]->person_name, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                                        setcookie('person_surname', $this->crypt->encrypt($singin[0]->person_surname, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                                        setcookie('person_dni', $this->crypt->encrypt($singin[0]->person_dni, _FULL_KEY_), time() + 30 * 24 * 60 * 60, "/");
                                                        setcookie("role", $this->crypt->encrypt($singin[0]->id_role, _FULL_KEY_), time() + 30* 24 * 60 * 60,"/");
                                                        setcookie("role_name", $this->crypt->encrypt($singin[0]->role_name, _FULL_KEY_), time() + 30* 24 * 60 * 60, "/");
                                                    }
                                                }
                                                $_SESSION['id_user'] = $this->crypt->encrypt($singin[0]->id_user, _FULL_KEY_);
                                                $_SESSION['id_person'] = $this->crypt->encrypt($singin[0]->id_user, _FULL_KEY_);
                                                $_SESSION['user_nickname'] = $this->crypt->encrypt($singin[0]->user_nickname, _FULL_KEY_);
                                                $_SESSION['user_image'] = $this->crypt->encrypt($singin[0]->user_image, _FULL_KEY_);
                                                $_SESSION['person_name'] = $this->crypt->encrypt($singin[0]->person_name, _FULL_KEY_);
                                                $_SESSION['person_surname'] = $this->crypt->encrypt($singin[0]->person_surname, _FULL_KEY_);
                                                $_SESSION['person_dni'] = $this->crypt->encrypt($singin[0]->person_surname, _FULL_KEY_);
                                                $_SESSION['role'] = $this->crypt->encrypt($singin[0]->id_role, _FULL_KEY_);
                                                $_SESSION['role_name'] = $this->crypt->encrypt($singin[0]->role_name, _FULL_KEY_);
                                                $_SESSION['validate_email'] = $this->crypt->encrypt($model[0]->user_email_validate_status, _FULL_KEY_);
                                                $_SESSION['id_auth'] = $this->crypt->encrypt($model[0]->id_user, _FULL_KEY_);
                                            }
                                            $message = "We did it. Your awesome... and beatiful";
                                            $result = 1;
                                        } else {
                                            //Code 3: Wrong Credentials
                                            $result = 3;
                                            $message = "Code 3: Wrong Credentials";
                                        }
                                    }
                                }
                            } else {
                                $this->person->deletedni($model[0]->person_dni);
                                $result = 2;
                            }
                        } else {
                            $user = [];
                            $result = 3;
                            $message = "Code 3: Wrong Credentials";
                        }
                    } else {
                        $user = [];
                        $result = 4;
                        $message = "Code 4: Inhabilite User";
                    }
                } else {
                    //If enter here, the nickname do not have a user
                    if($model == 2){
                        //Code 2: General Error
                        $user = [];
                        $result = 2;
                        $message = "Code 2: General Error";
                    } else {
                        //Code 3: Wrong Credentials
                        $user = [];
                        $result = 3;
                        $message = "Code 3: Wrong Credentials";
                    }
                }
            } else {
                //Code 6: False Data Integrity
                $user= [];
                $result = 6;
                $message = "Code 6: Fail Data Integrity";
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            //Code 2: General Error
            $result = 2;
            $message = "Code 2: General Error";
        }
        if(isset($_POST['app']) && $_POST['app'] == true){
            $response = array("code" => $result,"message" => $message);
            $data = array("result" => $response, "data" => $user, "role" => $permisos);
            echo json_encode($data);
        } else {
            $response = array("code" => $result,"message" => $message);
            $data = array("result" => $response);
            echo json_encode($data);
        }
    }

    public function singOut(){
        try{
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
            unset($_SESSION['validate_email']);
            unset($_SESSION['id_auth']);
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
            $okey = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $okey = 2;
        }
        if($okey ==1){
            header('Location: ' . _SERVER_ );
        }
    }
    public function save(){
        try{
            $message = "We did it. Your awesome... and beatiful";
            $last_id = 0;
            $model = new User();
            $modelp = new Person();
            $ok_data = true;
            if(isset($_SESSION['id_usered'])){
                $result = 6;
            } else {
                if(isset($_POST['person_name']) && isset($_POST['person_surname']) && isset($_POST['person_dni']) && isset($_POST['person_birth']) && isset($_POST['person_number_phone']) && isset($_POST['person_genre']) && isset($_POST['person_address']) && isset($_POST['person_city']) && isset($_POST['person_country']) && isset($_POST['user_email']) && isset($_POST['user_password']) && isset($_POST['user_nickname'])){
                    //Clean Data
                    $_POST['person_name'] = $this->clean->clean_post_str($_POST['person_name']);
                    $_POST['person_surname'] = $this->clean->clean_post_str($_POST['person_surname']);
                    //$_POST['person_dni'] = $this->clean->clean_post_int($_POST['person_dni']);
                    $_POST['person_birth'] = $this->clean->clean_post_str($_POST['person_birth']);
                    $_POST['person_number_phone'] = $this->clean->clean_post_int($_POST['person_number_phone']);
                    $_POST['person_genre'] = $this->clean->clean_post_str($_POST['person_genre']);
                    //$_POST['person_address'] = $this->clean->clean_post_str($_POST['person_address']);
                    $_POST['person_city'] = $this->clean->clean_post_str($_POST['person_city']);
                    $_POST['person_country'] = $this->clean->clean_post_str($_POST['person_country']);
                    $_POST['user_nickname'] = $this->clean->clean_post_str($_POST['user_nickname']);
                    $_POST['user_email'] = $this->clean->clean_post_str($_POST['user_email']);
                    //Evaluation If All Data is Ok
                    $ok_data = $this->clean->validate_post_just_str($_POST['person_name'], true, $ok_data, 200);
                    $ok_data = $this->clean->validate_post_just_str($_POST['person_surname'], true, $ok_data,200);
                    // $ok_data = $this->clean->validate_post_int($_POST['person_dni'], true, $ok_data,8);
                    $ok_data = $this->clean->validate_post_date($_POST['person_birth'], true, $ok_data, 10,1);
                    $ok_data = $this->clean->validate_post_int($_POST['person_number_phone'], true, $ok_data, 32);
                    $ok_data = $this->clean->validate_post_just_str($_POST['person_genre'], true, $ok_data,1);
                    //$ok_data = $this->clean->validate_post_str($_POST['person_address'], true, $ok_data, 200);
                    $ok_data = $this->clean->validate_post_just_str($_POST['person_city'], true, $ok_data, 30);
                    $ok_data = $this->clean->validate_post_just_str($_POST['person_country'], true, $ok_data, 20);
                    $ok_data = $this->clean->validate_post_str($_POST['user_nickname'], true, $ok_data, 40);
                    $ok_data = $this->clean->validate_post_str($_POST['user_password'], true, $ok_data, 64);
                    $ok_data = $this->clean->validate_post_email($_POST['user_email'], true, $ok_data, 200);
                } else {
                    $ok_data = false;
                }
                if($ok_data){
                    if($this->userg->validateUser($_POST['user_nickname'])){
                        $result = 3;
                    } else {
                        if($this->validate->email($_POST['user_email'])){
                            $result = 4;
                        } else {

                            $modelp->person_name= $_POST['person_name'];
                            $modelp->person_surname = $_POST['person_surname'];
                            $modelp->person_dni = $_POST['person_dni'];
                            $modelp->person_birth = $_POST['person_birth'];
                            $modelp->person_number_phone = $_POST['person_number_phone'];
                            $modelp->person_genre = $_POST['person_genre'];
                            $modelp->person_address = $_POST['person_address'];
                            $modelp->person_city = $_POST['person_city'];
                            $modelp->person_country = $_POST['person_country'];
                            $resultp = $this->login_auth->create_person($modelp);
                            if($resultp == 1){
                                //If person->create is ok, $model receives all about user
                                $model->user_nickname= $_POST['user_nickname'];
                                $model->user_password =  password_hash($_POST['user_password'], PASSWORD_BCRYPT);
                                $model->user_email = $_POST['user_email'];
                                $model->user_image = "media/user/user.jpg";
                                $model->id_person = $this->login_auth->list_by_dni($_POST['person_dni']);
                                //Create the user
                                $result = $this->login_auth->create_user($model);
                                $last_id = $this->login_auth->list_last_id();
                                if($result != 1){
                                    //If a error happened, delete all and send error message
                                    $this->login_auth->delete_person_by_dni($_POST['person_dni']);
                                    //Code 2: General Error
                                    $result = 2;
                                    $message = "Code 2: General Error";
                                }else{
                                    $destino= $_POST['user_email'];
                                    $headers = "From: Bufeo Tec Team <bufeotec@gmail.com>\n";
                                    $headers .= "MIME-Version: 1.0\n";
                                    $headers .= "Content-type: text/html; charset=utf-8\r\n";
                                    $longitud = 6;
                                    $token = '';
                                    $pattern = '1234567890';
                                    $max = strlen($pattern)-1;
                                    for($i=0;$i < $longitud;$i++) $token .= $pattern{mt_rand(0,$max)};
                                    $save_email_validate = $this->login_auth->save_email_validate_code($token,$last_id->id_user);
                                    if($save_email_validate==true){
                                        $mensajeF = "<h1>Bienvenido a Bufeo Tec</h1><p>Ingrese el siguiente código para validar su email:</p><h2 style='color: red; font-weight: bold;'>$token</h2>";
                                        mail($destino,"Bienvenido a Bufeo Tec",$mensajeF,$headers);
                                    }
                                }

                            } else {
                                //If a error happened, delete all and send error message
                                $this->login_auth->delete_person_by_dni($_POST['person_dni']);
                                //Code 2: General Error
                                $result = 2;
                                $message = "Code 2: General Error";
                            }
                            $resultp2 = $this->person->save($modelp);
                            if($resultp2 == 1){
                                $model->id_auth= $last_id->id_user;
                                $model->id_role = $_POST['id_role'];
                                $result2 = $this->user->save($model);
                                if($result2 != 1){
                                    $this->person->deletedni($_POST['person_dni']);
                                    $result = 2;
                                }
                            } else {
                                $this->person->deletedni($_POST['person_dni']);
                                $result = 2;
                            }
                        }
                    }
                }else {
                    //Code 6: False Data Integrity
                    $result = 6;
                    $message = "Code 6: Fail Data Integrity";
                }
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $response = array("code" => $result,"message" => $message);
        $data = array("result" => $response);
        echo json_encode($data);
    }
    public function auth_exists(){
        try{
            $model = new User();
            $modelp = new Person();
            $passpp="";
            $id_auth = $_POST['id_auth'];
            $info=$this->user->selectuser_id_auth($id_auth);
            $model->id_role = 4;
            if($info->id_user!=null){
             $modelp->id_person=$info->id_person;
             $model->id_person=$info->id_person;
             $model->id_user=$info->id_user;
             $model->id_role = $info->id_role;
            }
            $modelp->person_name= $_POST['person_name'];
            $modelp->person_surname = $_POST['person_surname'];
            $modelp->person_dni = $_POST['person_dni'];
            $modelp->person_birth = $_POST['person_birth'];
            $modelp->person_number_phone = $_POST['person_number_phone'];
            $modelp->person_genre = $_POST['person_genre'];
            $modelp->person_address = '';
            $resultp = $this->person->save($modelp);
            if($resultp == 1){
                $model->user_nickname= $_POST['user_nickname'];
                $model->id_auth= $_POST['id_auth'];
                $model->user_password =  password_hash($_POST['user_password'], PASSWORD_BCRYPT);
                $model->user_email = $_POST['user_email'];
                $model->user_status = 1;
                $model->user_auth_exists = 1;
                $result = $this->user->save($model);
                if($result != 1){
                    $this->person->deletedni($_POST['person_dni']);
                    $result = 2;
                }
            } else {
                $this->person->deletedni($_POST['person_dni']);
                $result = 2;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $response = array("code" => $result);
        $data = array("result" => $response);
        echo json_encode($data);
    }
}