<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Capitán - Bufeo Tec</title>
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
<div>
    <div class="row" style="margin-left: 0; margin-right: 0;">
        <div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default" >
                <div class="panel-heading" style="text-align: center;">Capitán</div>
                <div class="panel-body">
                    <fieldset>
                        <div class="form-group">
                            <input class="form-control" placeholder="Ingrese su Usuario" name="user" type="text" id="user" autofocus="">
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Ingrese su Contraseña" name="pass" type="password" id="pass" value="">
                        </div>
                        <div style="text-align: center">
                            <a id="btn-iniciar-sesion" class="btn btn-primary" onclick="loginsistema()">Iniciar Sesión</a>
                        </div><br>
                    </fieldset>
                </div>
            </div>
        </div><!-- /.col-->
    </div><!-- /.row -->
</div>


<script src="<?php echo _SERVER_ . _STYLES_ADMIN_;?>js/jquery-1.11.1.min.js"></script>
<script src="<?php echo _SERVER_ . _STYLES_ADMIN_;?>js/bootstrap.min.js"></script>
<script>
    $(document).ready(function(){
        $('#pass').keypress(function(e){
            if(e.which === 13){
                loginsistema();
            }
        });
        $('#user').keypress(function(e){
            if(e.which === 13){
                loginsistema();
            }
        });
    });
    function loginsistema() {
        var valor = "correcto";
        var usuario = $('#user').val();
        var contrasenha = $('#pass').val();
        if(usuario==""){
            alertify.error('Ingrese un usuario.');
            $('#user').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#user').css('border','');
        }
        if(contrasenha==""){
            alertify.error('Ingrese la contraseña.');
            $('#pass').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#pass').css('border','');
        }
        if(valor=="correcto"){
            var cadena = "user=" + usuario +
                "&pass=" + contrasenha;
            $.ajax({
                type: "POST",
                url: "<?php echo _SERVER_;?>api/Login/validar_usuario",
                data: cadena,
                dataType: 'json',
                beforeSend: function () {
                    $("#btn-iniciar-sesion").html("Cargando");
                    $("#btn-iniciar-sesion").attr("disabled", true);
                },
                success:function (r) {
                    switch (r.result.code) {
                        case 1:
                            alertify.success('Datos correctos');
                            location.href = "<?php echo _SERVER_;?>";
                            break;
                        case 2:
                            alertify.error('ERROR :(--');
                            break;
                        case 3:
                            alertify.error('Usuario y/o Contraseña Incorrectos');
                            break;
                        default:
                            alertify.error('ERROR :(');
                            break;
                    }
                    $("#btn-iniciar-sesion").attr("disabled", false);
                    $("#btn-iniciar-sesion").html("Iniciar Sesión");
                }
            });
        }
    }
</script>
</body>
</html>