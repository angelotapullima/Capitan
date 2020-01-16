function save() {
    var valor = "correcto";
    var person_name = $('#person_name').val();
    var person_surname = $('#person_surname').val();
    var person_dni = $('#person_dni').val();
    var person_birth = $('#person_birth').val();
    var person_number_phone = $('#person_number_phone').val();
    var person_genre = $('#person_genre').val();
    var person_address = $('#person_address').val();
    var id_role = $('#id_role').val();
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

    if(person_address == ""){
        alertify.error('El campo Dirección está vacío');
        $('#person_address').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#person_address').css('border','');
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
            "&user_email=" + user_email;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Userg/new",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Userg/all';
                        break;
                    case "2":
                        alertify.error("Fallo el envio");
                        break;
                    case "3":
                        alertify.warning("Este usuario ya está siendo usado");
                        $('#user_nickname').css('border','solid red');
                        break;
                    case "4":
                        alertify.warning("Este DNI ya se encuentra en uso");
                        $('#person_dni').css('border','solid red');
                        break;
                    case "6":
                        alertify.error("FALLO MORTAL :(");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}

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


function savep() {
    var valor = "correcto";
    var person_name = $('#person_name').val();
    var person_surname = $('#person_surname').val();
    var person_dni = $('#person_dni').val();
    var person_birth = $('#person_birth').val();
    var person_number_phone = $('#person_number_phone').val();
    var person_genre = $('#person_genre').val();
    var person_address = $('#person_address').val();

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

    if(person_address == ""){
        alertify.error('El campo Dirección está vacío');
        $('#person_address').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#person_address').css('border','');
    }


    if (valor == "correcto"){
        var cadena = "person_name=" + person_name +
            "&person_surname=" + person_surname +
            "&person_dni=" + person_dni +
            "&person_birth=" + person_birth +
            "&person_number_phone=" + person_number_phone +
            "&person_genre=" + person_genre +
            "&person_address=" + person_address;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Userg/save_edit_personu",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Userg/all';
                        break;
                    case "2":
                        alertify.error("Fallo el envio");
                        break;
                    case "3":
                        alertify.warning("Este DNI ya se encuentra en uso");
                        $('#person_dni').css('border','solid red');
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}

function saveeu() {
    var valor = "correcto";
    var user_nickname = $('#user_nickname').val();

    var user_email = $('#user_email').val();
    var id_role = $('#id_role').val();

    var user_status = $('#user_status').val();

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
            "&id_role=" + id_role +
            "&user_status=" + user_status;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Userg/save_edit_useru",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Userg/all';
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

function preguntarSiNoRC(id){
    alertify.confirm('Resetear Contraseña', '¿Esta seguro que desea reiniciar esta contraseña?',
        function(){ reset(id) }
        , function(){ alertify.error('Operación Cancelada')});
}

function reset(id){
    var cadena = "id=" + id;
    $.ajax({
        type:"POST",
        url: urlweb + "api/Userg/reset_pass",
        data : cadena,
        success:function (r) {
            if(r==1){
                alertify.success('Contraseña Reseteada');
                location.reload();
            } else {
                alertify.error('No se pudo realizar');
            }
        }
    });
}

function preguntarSiNoI(id,statusu){
    alertify.confirm('Cambiar Estado', '¿Esta seguro que desea cambiar el estado de este usuario?',
        function(){ status(id,statusu) }
        , function(){ alertify.error('Operación Cancelada')});
}

function status(id, status){
    var cadena = "id=" + id +
        "&user_status=" + status;
    $.ajax({
        type:"POST",
        url: urlweb + "api/Userg/change_status",
        data : cadena,
        success:function (r) {
            if(r==1){
                alertify.success('Estado Cambiado');
                location.reload();
            } else {
                alertify.error('No se pudo realizar');
            }
        }
    });
}