function save() {
    var valor = "correcto";
    var person_name = $('#person_name').val();
    var person_surname = $('#person_surname').val();
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
            "&person_birth=" + person_birth +
            "&person_number_phone=" + person_number_phone +
            "&person_genre=" + person_genre +
            "&person_address=" + person_address;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Edit/save",
            data: cadena,
            success:function (r) {
                if(r==1){
                    alertify.success("¡Guardado!");
                    location.reload();
                } else {
                    alertify.error("Fallo el envio");
                }
            }
        });
    }

}

function savenick() {
    var valor = "correcto";
    var user_nickname = $('#user_nickname').val();


    if(user_nickname == ""){
        alertify.error('El campo Nombre está vacío');
        $('#user_nickname').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#user_nickname').css('border','');
    }


    if (valor == "correcto"){
        var cadena = "user_nickname=" + user_nickname;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Edit/saveNewNick",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.reload();
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
            url: urlweb + "api/Edit/chgpass",
            data: cadena,
            success:function (r) {
                if(r==1){
                    alertify.success("¡Guardado!");
                    location.reload();
                } else {
                    alertify.error("Fallo el envio");
                }
            }
        });
    }

}