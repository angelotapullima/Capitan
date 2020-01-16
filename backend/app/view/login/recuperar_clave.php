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
<div>
    <div class="row" style="margin-left: 0; margin-right: 0;">
        <div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default" >
                <div class="panel-heading" style="text-align: center;"><img src="<?= _SERVER_;?>media/logosaneamiento.png" width="40" height="40"> Saneamiento Maynas <img src="<?= _SERVER_;?>media/em.png" width="40" height="40"></div>
                <div class="panel-body">
                    <fieldset>
                        <div class="form-group">
                            <input class="form-control" placeholder="Ingrese su email" name="email" type="email" id="email" autofocus="">
                        </div>
                        <div style="text-align: center">
                            <a id="btn-recuperar-clave" class="btn btn-primary" onclick="recuperar_clave()">Recuperar Clave</a>
                        </div><br>
                        <div class="col-xs-8">
                            <a href="<?= _SERVER_; ?>Login/index">Ir a Iniciar Sesión</a>
                        </div>
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
        $('#email').keypress(function(e){
            if(e.which === 13){
                recuperar_clave();
            }
        });
    });
    function recuperar_clave() {
        var valor = "correcto";
        var email = $('#email').val();
        if(email==""){
            alertify.error('Ingrese su email.');
            $('#email').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#email').css('border','');
        }
        if(valor=="correcto"){
            var cadena = "email=" + email;
            $.ajax({
                type: "POST",
                url: "<?php echo _SERVER_AUTH;?>api/Login/recuperar_clave",
                data: cadena,
                dataType: 'json',
                beforeSend: function () {
                    $("#btn-recuperar-clave").html("Cargando");
                    $("#btn-recuperar-clave").attr("disabled", true);
                },
                success:function (r) {
                    switch (r.result.code) {
                        case 1:
                            alertify.success('Se ha enviado un enlace al email ingresado');
                            break;
                        case 2:
                            alertify.error('ERROR :(');
                            break;
                        case 3:
                            alertify.error('El email ingresado no existe');
                            break;
                        default:
                            alertify.error('ERROR :(--');
                            break;
                    }
                    $("#btn-recuperar-clave").html("Recuperar Clave");
                    $("#btn-recuperar-clave").attr("disabled", false);
                }
            });
        }
    }
</script>
</body>
</html>