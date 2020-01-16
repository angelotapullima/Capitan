function preguntarSiNoU(id){
    alertify.confirm('Eliminar Datos', '¿Esta seguro de eliminar este registro?',
        function(){ deleter(id) }
        , function(){ alertify.error('Operacion Cancelada')});
}

function deleter(id){
    var cadena = "id=" + id;
    $.ajax({
        type:"POST",
        url: urlweb + "api/User/delete",
        data : cadena,
        success:function (r) {
            if(r==1){
                alertify.success('Registro Eliminado');
                location.reload();
            } else {
                alertify.error('No se pudo realizar');
            }
        }
    });
}

function save() {
    var valor = "correcto";
    var user_nickname = $('#user_nickname').val();
    var user_password1 = $('#user_password1').val();
    var user_password2 = $('#user_password2').val();
    var user_email = $('#user_email').val();
    var id_role = $('#id_role').val();
    var id_person = $('#id_person').val();

    if(user_nickname == ""){
        alertify.error('El campo Nombre está vacío');
        $('#user_nickname').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#user_nickname').css('border','');
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

    if(id_person == ""){
        alertify.error('No se ha seleccionado una persona para el usuario');
        $('#id_person').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#id_person').css('border','');
    }

    if (valor == "correcto"){
        var cadena = "user_nickname=" + user_nickname +
            "&user_password=" + user_password1 +
            "&user_email=" + user_email +
            "&id_role=" + id_role +
            "&id_person=" + id_person;
        $.ajax({
            type:"POST",
            url: urlweb + "api/User/save",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'User/all';
                        break;
                    case "2":
                        alertify.error("Fallo el envio");
                        break;
                    case "3":
                        alertify.warning("Este NOMBRE DE USUARIO ya se encuentra en uso");
                        $('#user_nickname').css('border','solid red');
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}

function savec() {
    var valor = "correcto";

    var user_password1 = $('#user_password1').val();
    var user_password2 = $('#user_password2').val();


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

    if (valor == "correcto"){
        var cadena = "user_password=" + user_password1;
        $.ajax({
            type:"POST",
            url: urlweb + "api/User/changepass",
            data: cadena,
            success:function (r) {
                if(r==1){
                    alertify.success("¡Guardado!");
                    location.href = urlweb +  'User/all';
                } else {
                    alertify.error("Fallo el envio");
                }
            }
        });
    }

}

function savee() {
    var valor = "correcto";
    var user_nickname = $('#user_nickname').val();

    var user_email = $('#user_email').val();
    var id_role = $('#id_role').val();
    var id_person = $('#id_person').val();
    var user_status = $('#user_status').val();

    if(user_nickname == ""){
        alertify.error('El campo Nombre está vacío');
        $('#user_nickname').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#user_nickname').css('border','');
    }

    if(user_email == ""){
        alertify.error('El campo Correo está vacío');
        $('#user_email').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#user_email').css('border','');
    }

    if (valor == "correcto"){
        var cadena = "user_nickname=" + user_nickname +
            "&user_email=" + user_email +
            "&user_status=" + user_status +
            "&id_role=" + id_role +
            "&id_person=" + id_person;
        $.ajax({
            type:"POST",
            url: urlweb + "api/User/save",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'User/all';
                        break;
                    case "2":
                        alertify.error("Fallo el envio");
                        break;
                    case "3":
                        alertify.warning("Este NOMBRE DE USUARIO ya se encuentra en uso");
                        $('#user_nickname').css('border','solid red');
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}