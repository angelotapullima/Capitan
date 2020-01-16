<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Saneamiento</title>
    <link rel="shortcut icon" href="<?php echo _SERVER_ . _ICON_;?>">
    <link href="<?php echo _SERVER_ . _STYLES_ADMIN_;?>css/bootstrap.min.css" rel="stylesheet">
    <link href="<?php echo _SERVER_ . _STYLES_ADMIN_;?>css/datepicker3.css" rel="stylesheet">
    <link href="<?php echo _SERVER_ . _STYLES_ADMIN_;?>css/styles.css" rel="stylesheet">

    <!-- Alertify -->
    <script src="<?php echo  _SERVER_ . _STYLES_ALL_;?>alertifyjs/alertify.js"></script>
    <link rel="stylesheet" type="text/css" href="<?php echo _SERVER_ . _STYLES_ALL_;?>alertifyjs/css/alertify.css">
    <link rel="stylesheet" type="text/css" href="<?php echo _SERVER_ . _STYLES_ALL_;?>alertifyjs/css/themes/default.css">

</head>
<body style="background-image: url('<?php echo _SERVER_;?>media/fondo1.jpg'); background-repeat: no-repeat; background-size: cover;">
<div class="row">
    <!-- left column -->
    <div class="col-md-6">
        <center><h1>Crea tu cuenta en Bufeo</h1></center>
        <h4>Recuerda que al crear tu cuenta en Bufeo, podrás acceder con un solo usuario a cualquiera de las aplicaciones de Bufeo.</h4>
    </div>
    <div class="col-md-6">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">¡Regístrate completamente gratis!</h4>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <div>
                <div class="box-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-5">
                            <div class="form-group">
                                <label class="col-form-label">Nombres</label>
                                <input class="form-control" type="text" id="person_name" placeholder="Ingrese Nombres...">
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-5">
                            <div class="form-group">
                                <label class="col-form-label">Apellidos</label>
                                <input class="form-control" type="text" id="person_surname" placeholder="Ingrese Apellidos...">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 col-md-5">
                            <div class="form-group">
                                <label class="col-form-label">DNI</label>
                                <input class="form-control" type="text" id="person_dni" placeholder="Ingrese DNI..." maxlength="8" onkeypress="return valida(event)">
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-5">
                            <div class="form-group">
                                <label class="col-form-label">Fecha de Nacimiento</label>
                                <input class="form-control" type="date" id="person_birth">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 col-md-5">
                            <div class="form-group">
                                <label class="col-form-label">Número de Teléfono</label>
                                <input class="form-control" type="text" id="person_number_phone" onkeypress="return valida(event)" placeholder="Ingrese Teléfono...">
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-5">
                            <div class="form-group">
                                <label class="col-form-label">Género</label>
                                <select class="form-control" id="person_genre">
                                    <option value="M">Masculino</option>
                                    <option value="F">Femenino</option>
                                    <option value="P">Orgullo</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 col-md-5">
                            <div class="form-group">
                                <label class="col-form-label">Email</label>
                                <input type="text" class="form-control" id="user_email" placeholder="Ingresar Correo Usuario...">
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-5">
                            <div class="form-group">
                                <label class="col-form-label">Nickname</label>
                                <input type="text" class="form-control" id="user_nickname" placeholder="Ingresar Nombre Usuario...">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 col-md-10">
                            <div class="form-group">
                                <label class="col-form-label">Contraseña</label>
                                <input type="password" class="form-control" id="user_password1" placeholder="Ingresar Contraseña...">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 col-md-10">
                            <div class="form-group">
                                <label class="col-form-label">Repetir Contraseña</label>
                                <input type="password" class="form-control" id="user_password2" placeholder="Vuelva a Ingresar Contraseña...">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success" onclick="save()"> Empezar en Bufeo</button>
                    </div>
                </div>
                <!-- /.box-body -->

            </div>
        </div>
        <!-- /.box -->
    </div>
</div>
<script src="<?php echo _SERVER_ . _STYLES_ADMIN_;?>js/jquery-1.11.1.min.js"></script>
<script src="<?php echo _SERVER_ . _STYLES_ADMIN_;?>js/bootstrap.min.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script>
    function save() {
        var valor = "correcto";
        var person_name = $('#person_name').val();
        var person_surname = $('#person_surname').val();
        var person_dni = $('#person_dni').val();
        var person_birth = $('#person_birth').val();
        var person_number_phone = $('#person_number_phone').val();
        var person_genre = $('#person_genre').val();
        var person_address = ' ';
        var id_role = 4;
        var user_nickname = $('#user_nickname').val();
        var user_password1 = $('#user_password1').val();
        var user_password2 = $('#user_password2').val();
        var user_email = $('#user_email').val();

        if(user_nickname == ""){
            alertify.error('El campo Nombre está vacío');
            $('#user_nickname').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#user_nickname').css('border','');
        }

        if(id_role == ""){
            alertify.error('El campo Rol está vacío');
            $('#id_role').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#id_role').css('border','');
        }

        if(user_password1 !== user_password2){
            alertify.error('Las Contraseñas no coinciden');
            $('#user_password1').css('border','solid red');
            $('#user_password2').css('border','solid red');
            valor = "incorrecto";
        } else {
            if(user_password1 == ""){
                alertify.error('El campo Contraseña está vacío');
                $('#user_password1').css('border','solid red');
                $('#user_password2').css('border','solid red');
                valor = "incorrecto";
            } else {
                $('#user_password1').css('border','');
                $('#user_password2').css('border','');
            }
        }

        if(user_email == ""){
            alertify.error('El campo Correo está vacío');
            $('#user_email').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#user_email').css('border','');
        }

        if(person_name == ""){
            alertify.error('El campo Nombre está vacío');
            $('#person_name').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#person_name').css('border','');
        }

        if(person_surname == ""){
            alertify.error('El campo Apellidos está vacío');
            $('#person_surname').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#person_surname').css('border','');
        }

        if(person_dni == ""){
            alertify.error('El campo DNI está vacío');
            $('#person_dni').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#person_dni').css('border','');
        }

        if(person_birth == ""){
            alertify.error('El campo Fecha de Nacimiento está vacío');
            $('#person_birth').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#person_birth').css('border','');
        }

        if(person_number_phone == ""){
            alertify.error('El campo Teléfono está vacío');
            $('#person_number_phone').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#person_number_phone').css('border','');
        }

        if(person_genre == ""){
            alertify.error('El campo Género está vacío');
            $('#person_genre').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#person_genre').css('border','');
        }
        if (valor == "correcto"){
            var cadena = "person_name=" + person_name +
                "&person_surname=" + person_surname +
                "&id_role=" + id_role +
                "&person_dni=" + person_dni +
                "&person_birth=" + person_birth +
                "&person_number_phone=" + person_number_phone +
                "&person_genre=" + person_genre +
                "&person_address=" + person_address +
                "&user_nickname=" + user_nickname +
                "&user_password=" + user_password1 +
                "&person_city=Iquitos" +
                "&person_country=Peru" +
                "&user_email=" + user_email;
            $.ajax({
                type:"POST",
                url: urlweb + "api/Login/save",
                data: cadena,
                dataType: 'json',
                success:function (r) {
                    switch (r.result.code) {
                    case 1:
                        alertify.success("¡Guardado!");
                        location.href = urlweb;
                        break;
                    case 2:
                        alertify.error("Fallo el envio");
                        break;
                    case 3:
                        alertify.warning("Este usuario ya está siendo usado");
                        $('#user_nickname').css('border','solid red');
                        break;
                    case 4:
                        alertify.warning("Este Email ya se encuentra en uso");
                        $('#person_dni').css('border','solid red');
                        break;
                    case 6:
                        alertify.error("FALLO MORTAL :(");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                    }
                }
            });
        }

    }
</script>
</body>
</html>


