function save() {
    var valor = "correcto";
    var menu_name = $('#menu_name').val();
    var menu_icon = $('#menu_icon').val();
    var menu_controller = $('#menu_controller').val();
    var menu_order = $('#menu_order').val();
    var menu_status = $('#menu_status').val();
    var menu_show = $('#menu_show').val();
    var password = $('#password').val();

    if(menu_name == ""){
        alertify.error('El campo Nombre de Menú está vacío');
        $('#menu_name').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#menu_name').css('border','');
    }

    if(menu_icon == ""){
        alertify.error('El campo Icono del Menú está vacío');
        $('#menu_icon').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#menu_icon').css('border','');
    }

    if(menu_controller == ""){
        alertify.error('El campo Nombre Controlador está vacío');
        $('#menu_controller').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#menu_controller').css('border','');
    }

    if(menu_order == ""){
        alertify.error('El campo Orden Aparación está vacío');
        $('#menu_order').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#menu_order').css('border','');
    }

    if(password == ""){
        alertify.error('El campo Contraseña de Usuario está vacío');
        $('#password').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#password').css('border','');
    }


    if (valor == "correcto"){
        var cadena = "menu_name=" + menu_name +
            "&menu_icon=" + menu_icon +
            "&menu_controller=" + menu_controller +
            "&menu_order=" + menu_order +
            "&menu_status=" + menu_status +
            "&menu_show=" + menu_show +
            "&password=" + password;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Menu/save",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Menu/list';
                        break;
                    case "2":
                        alertify.error("Fallo el envio");
                        break;
                    case "3":
                        alertify.warning("La Contraseña de Usuario no es Correcta");
                        $('#password').css('border','solid red');
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}

function savef(idr) {
    var valor = "correcto";
    var optionm_name = $('#optionm_name').val();
    var optionm_function = $('#optionm_function').val();
    var optionm_show = $('#optionm_show').val();
    var optionm_status = $('#optionm_status').val();
    var optionm_order = $('#optionm_order').val();
    var password = $('#password').val();

    if(optionm_name == ""){
        alertify.error('El campo Nombre Opción está vacío');
        $('#optionm_name').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#optionm_name').css('border','');
    }

    if(optionm_function == ""){
        alertify.error('El campo Nombre Función está vacío');
        $('#optionm_function').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#optionm_function').css('border','');
    }

    if(optionm_order == ""){
        alertify.error('El campo Orden está vacío');
        $('#optionm_order').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#optionm_order').css('border','');
    }

    if(password == ""){
        alertify.error('El campo Contraseña de Usuario está vacío');
        $('#password').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#password').css('border','');
    }


    if (valor == "correcto"){
        var cadena = "optionm_name=" + optionm_name +
            "&optionm_function=" + optionm_function +
            "&optionm_show=" + optionm_show +
            "&optionm_status=" + optionm_status +
            "&optionm_order=" + optionm_order +
            "&password=" + password;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Menu/saveOption",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Menu/functions/' + idr;
                        break;
                    case "2":
                        alertify.error("Fallo el envio");
                        break;
                    case "3":
                        alertify.warning("La Contraseña de Usuario no es Correcta");
                        $('#password').css('border','solid red');
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}

function savep(idr) {
    var valor = "correcto";
    var permit_action = $('#permit_action').val();
    var permit_status = $('#permit_status').val();
    var password = $('#password').val();

    if(permit_action == ""){
        alertify.error('El campo Acción está vacío');
        $('#permit_action').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#optionm_name').css('border','');
    }
    if(password == ""){
        alertify.error('El campo Contraseña de Usuario está vacío');
        $('#password').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#password').css('border','');
    }


    if (valor == "correcto"){
        var cadena = "permit_action=" + permit_action +
            "&permit_status=" + permit_status +
            "&password=" + password;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Menu/savePermit",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Menu/listp/' + idr;
                        break;
                    case "2":
                        alertify.error("Fallo el envio");
                        break;
                    case "3":
                        alertify.warning("La Contraseña de Usuario no es Correcta");
                        $('#password').css('border','solid red');
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}

function volverf(id){
    location.href = urlweb +  'Menu/functions/' + id;
}

function volverp(id){
    location.href = urlweb +  'Menu/listp/' + id;
}

function preguntarSiNoAR(id_menu, id_role){
    alertify.confirm('Agregar Relación', '¿Esta seguro que desea crear está relación?',
        function(){ addRole(id_menu, id_role) }
        , function(){ alertify.error('Operacion Cancelada')});
}

function addRole(id_menu, id_role){
    var valor = "correcto";
    var password = $('#password').val();

    if(password == ""){
        alertify.error('El campo Contraseña de Usuario está vacío');
        $('#password').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#password').css('border','');
    }

    if(valor == "correcto"){
        var cadena = "id_menu=" + id_menu +
            "&id_role=" + id_role +
            "&password=" + password;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Menu/insertRole",
            data : cadena,
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
                        alertify.warning("La Contraseña de Usuario no es Correcta");
                        $('#password').css('border','solid red');
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}

function preguntarSiNoDR(id_menu, id_role){
    alertify.confirm('Eliminar Relación', '¿Esta seguro que desea eliminar está relación?',
        function(){ deleteRole(id_menu, id_role) }
        , function(){ alertify.error('Operacion Cancelada')});
}

function deleteRole(id_menu, id_role){
    var valor = "correcto";
    var password = $('#password').val();

    if(password == ""){
        alertify.error('El campo Contraseña de Usuario está vacío');
        $('#password').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#password').css('border','');
    }

    if(valor == "correcto"){
        var cadena = "id_menu=" + id_menu +
            "&id_role=" + id_role +
            "&password=" + password;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Menu/deleteRole",
            data : cadena,
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
                        alertify.warning("La Contraseña de Usuario no es Correcta");
                        $('#password').css('border','solid red');
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
}


function preguntarSiNoDP(id_permit){
    alertify.confirm('Eliminar Permiso', '¿Esta seguro que desea eliminar este permiso?',
        function(){ deletePermit(id_permit) }
        , function(){ alertify.error('Operacion Cancelada')});
}

function deletePermit(id_permit){
    var valor = "correcto";
    var password = $('#password').val();

    if(password == ""){
        alertify.error('El campo Contraseña de Usuario está vacío');
        $('#password').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#password').css('border','');
    }

    if(valor == "correcto"){
        var cadena = "id_permit=" + id_permit +
            "&password=" + password;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Menu/deletePermit",
            data : cadena,
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
                        alertify.warning("La Contraseña de Usuario no es Correcta");
                        $('#password').css('border','solid red');
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
}