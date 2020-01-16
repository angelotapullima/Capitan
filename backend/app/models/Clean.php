<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 29/08/2019
 * Time: 13:39
 */
class Clean{
    public function __construct()
    {}
    //Clean POST String Values
    public function clean_post_str($value){
        try{
            //Clean Values To Avoid XSS Atack
            $value = filter_var($value, FILTER_SANITIZE_SPECIAL_CHARS);
            $result = $value;
        } catch (Exception $e){
            $result = "";
        }
        return $result;
    }
    //Clean POST String Values
    public function clean_post_date($value){
        try{
            //Clean Values To Avoid XSS Atack
            $value = filter_var($value, FILTER_SANITIZE_SPECIAL_CHARS);
            $result = $value;
        } catch (Exception $e){
            $result = "";
        }
        return $result;
    }
    //Clean POST Int Values
    public function clean_post_int($value){
        try{
            //Clean Values To Avoid XSS Atack
            $value = filter_var($value, FILTER_SANITIZE_SPECIAL_CHARS);
            //Clean Letters
            $value = filter_var($value, FILTER_SANITIZE_NUMBER_INT);
            $result = $value;
        } catch (Exception $e){
            $result = "";
        }
        return $result;
    }
    //Clean POST Float Values
    public function clean_post_float($value){
        try{
            //Clean Values To Avoid XSS Atack
            $value = filter_var($value, FILTER_SANITIZE_SPECIAL_CHARS);
            //Clean Letters
            $value = filter_var($value, FILTER_SANITIZE_NUMBER_FLOAT);
            $result = $value;
        } catch (Exception $e){
            $result = "";
        }
        return $result;
    }
    //Validate POST String Values
    public function validate_post_str($value, $empty, $ok, $strlenght){
        try{
            //If $ok is false, this do nothing
            if($ok){
                if($empty){
                    //If $empty is true, the function will validate if $value is not empty
                    if($value !== null && $value != ""){
                        //This validate if $value have the correct lenght
                        if(strlen($value) <= $strlenght){
                            $result = true;
                        } else {
                            $result = false;
                        }
                    } else {
                        $result = false;
                    }
                } else {
                    //This validate if $value have the correct lenght
                    if(strlen($value) <= $strlenght){
                        $result = true;
                    } else {
                        $result = false;
                    }
                }
            } else {
                $result = false;
            }
        } catch (Exception $e){
            $result = false;
        }
        return $result;
    }
    //Validate POST String Values (Just Letters)
    public function validate_post_just_str($value, $empty, $ok, $strlenght){
        try{
            //If $ok is false, this do nothing
            if($ok){
                if($empty){
                    //If $empty is true, the function will validate if $value is not empty
                    if($value !== null && $value != ""){
                        //This validate if $value have the correct lenght
                        if(strlen($value) <= $strlenght){
                            //$pattern = '/^[a-zA-ZÀ-ÿ\u00f1\u00d1]+(\s*[a-zA-ZÀ-ÿ\u00f1\u00d1]*)*[a-zA-ZÀ-ÿ\u00f1\u00d1]+$/';
                            $pattern = '/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]*$/';
                            $validate_str = preg_match($pattern,$value);
                            if($validate_str){
                                $result = true;
                            } else {
                                $result = false;
                            }
                        } else {
                            $result = false;
                        }
                    } else {
                        $result = false;
                    }
                } else {
                    //This validate if $value have the correct lenght
                    if(strlen($value) <= $strlenght){
                        $result = true;
                    } else {
                        $result = false;
                    }
                }
            } else {
                $result = false;
            }
        } catch (Exception $e){
            $result = false;
        }
        return $result;
    }
    //Validate POST String Values (Date)
    public function validate_post_date($value, $empty, $ok, $strlenght, $type){
        try{
            //If $ok is false, this do nothing
            if($ok){
                if($empty){
                    //If $empty is true, the function will validate if $value is not empty
                    if($value !== null && $value != ""){
                        //This validate if $value have the correct lenght
                        //yyyy-mm-dd = 10 caracters
                        //yyyy-mm-dd hh:mm:ss = 19 caracters
                        if(strlen($value) <= $strlenght){
                            //$pattern = '/^[a-zA-ZÀ-ÿ\u00f1\u00d1]+(\s*[a-zA-ZÀ-ÿ\u00f1\u00d1]*)*[a-zA-ZÀ-ÿ\u00f1\u00d1]+$/';
                            switch ($type){
                                case 1:
                                    //Date: dd/mm/yyyy o dd-mm-yyyy
                                    //$pattern = '/^([0-2][0-9]|3[0-1])(\/|-)(0[1-9]|1[0-2])\2(\d{4})$/';
                                    $pattern = '/^(19|20)\d\d[\-\/.](0[1-9]|1[012])[\-\/.](0[1-9]|[12][0-9]|3[01])$/';
                                    break;
                                case 2:
                                    //Date, hour, minute and second: dd/mm/yyyy hh:mm:ss o dd-mm-yyyy hh:mm:ss
                                    //$pattern = '/^([0-2][0-9]|3[0-1])(\/|-)(0[1-9]|1[0-2])\2(\d{4})(\s)([0-1][0-9]|2[0-3])(:)([0-5][0-9])(:)([0-5][0-9])$/';
                                    $pattern = '/^(19|20)\d\d[\-\/.](0[1-9]|1[012])[\-\/.](0[1-9]|[12][0-9]|3[01])(\s)([0-1][0-9]|2[0-3])(:)([0-5][0-9])(:)([0-5][0-9])$/';
                                    break;
                                default:
                                    break;
                            }
                            $validate_str = preg_match($pattern,$value);
                            if($validate_str){
                                $result = true;
                            } else {
                                $result = false;
                            }
                        } else {
                            $result = false;
                        }
                    } else {
                        $result = false;
                    }
                } else {
                    //This validate if $value have the correct lenght
                    if(strlen($value) <= $strlenght){
                        $result = true;
                    } else {
                        $result = false;
                    }
                }
            } else {
                $result = false;
            }
        } catch (Exception $e){
            $result = false;
        }
        return $result;
    }
    //Validate POST Int Values
    public function validate_post_int($value, $empty, $ok, $strlenght){
        try{
            //If $ok is false, this do nothing
            if($ok){
                if($empty){
                    //If $empty is true, the function will validate if $value is not empty
                    if($value !== null && $value != ""){
                        //This validate if $value have the correct lenght
                        if(strlen($value) <= $strlenght){
                            //This validate if $value have just numbers
                            $result = is_numeric($value);
                        } else {
                            $result = false;
                        }
                    } else {
                        $result = false;
                    }
                } else {
                    //This validate if $value have the correct lenght
                    if(strlen($value) <= $strlenght){
                        //This validate if $value have just numbers
                        $result = is_numeric($value);
                    } else {
                        $result = false;
                    }
                }
            } else {
                $result = false;
            }
        } catch (Exception $e){
            $result = false;
        }
        return $result;
    }
    //Validate POST Email Values
    public function validate_post_email($value, $empty, $ok, $strlenght){
        try{
            //If $ok is false, this do nothing
            if($ok){
                //This is to validate is value is empty. True = validate, False = do not validate
                if($empty){
                    //If $empty is true, the function will validate if $value is not empty
                    if($value !== null && $value != ""){
                        //This validate if $value have the correct lenght
                        if(strlen($value) <= $strlenght){
                            //This validate if $value have the correct format for email
                            $result = filter_var($value, FILTER_VALIDATE_EMAIL);
                            if($result !== false){
                                $result = true;
                            } else {
                                $result = false;
                            }
                        } else {
                            $result = false;
                        }
                    } else {
                        $result = false;
                    }
                } else {
                    //This validate if $value have the correct lenght
                    if(strlen($value) <= $strlenght){
                        //This validate if $value have the correct format for email
                        $result = filter_var($value, FILTER_VALIDATE_EMAIL);
                        if($result !== false){
                            $result = true;
                        } else {
                            $result = false;
                        }
                    } else {
                        $result = false;
                    }
                }
            } else {
                $result = false;
            }
        } catch (Exception $e){
            $result = false;
        }
        return $result;
    }
    //Validate POST Image
    public function validate_post_image($value, $ok){
        try{
            //If $ok is false, this do nothing
            if($ok){
                //This validate that the image is not null
                if($value['size'] > 0){
                    //This validate that the image have the correct type
                    if($value['type'] == 'image/jpeg' || $value['type'] == 'image/png'){
                        $result = true;
                    } else {
                        $result = false;
                    }
                } else {
                    $result = false;
                }
            } else {
                $result = false;
            }
        } catch (Exception $e){
            $result = false;
        }
        return $result;
    }

}