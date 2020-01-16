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
                            <input class="form-control" placeholder="Ingrese su nueva contraseña" name="pass" type="password" id="pass" autofocus="">
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Vuelva a escribir su nueva contraseña" name="pass2" type="password" id="pass2" autofocus="">
                        </div>
                        <div style="text-align: center">
                            <a id="btn-recuperar-clave" class="btn btn-primary" onclick="restaurar_clave()">Guardar nueva contraseña</a>
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
        $('#pass2').keypress(function(e){
            if(e.which === 13){
                restaurar_clave();
            }
        });
        $('#pass').keypress(function(e){
            if(e.which === 13){
                restaurar_clave();
            }
        });
    });
    function restaurar_clave() {
        var valor = "correcto";
        var pass = $('#pass').val();
        var pass2 = $('#pass2').val();
        if(pass==""){
            alertify.error('Ingrese la nueva contraseña.');
            $('#pass').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#pass').css('border','');
        }
        if(pass2==""){
            alertify.error('Vuelve a escribir la contraseña.');
            $('#pass2').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#pass2').css('border','');
        }
        if(pass!=pass2){
            alertify.error('Las contraseñas no coinciden');
            $('#pass').css('border','solid red');
            $('#pass2').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#pass').css('border','');
            $('#pass2').css('border','');
        }
        if(valor=="correcto"){
            var id = <?= $_GET['id']; ?>;
            var criterio = '<?= $_GET['criterio']; ?>';
            var cadena = "id=" + id+ "&param=" + criterio + "&pass=" + pass;
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
                            alertify.success('Su contraseña se ha guardado correctamente');
                            break;
                        default:
                            alertify.error(r.result.message);
                            break;
                    }
                    $("#btn-recuperar-clave").html("Guardar nueva contraseña");
                    $("#btn-recuperar-clave").attr("disabled", false);
                }
            });
        }
    }
</script>
</body>
</html>