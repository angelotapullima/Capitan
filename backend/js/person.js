function preguntarSiNo(id){
    alertify.confirm('Eliminar Datos', '¿Esta seguro de eliminar este registro?',
        function(){ deleter(id) }
        , function(){ alertify.error('Operacion Cancelada')});
}

function deleter(id){
    var cadena = "id=" + id;
    $.ajax({
        type:"POST",
        url: urlweb + "api/Person/delete",
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
            url: urlweb + "api/Person/save",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Person/all';
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