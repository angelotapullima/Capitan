function save_agente() {
    var valor = "correcto";
    var agente_cuentae = $('#agente_cuentae').val();
    var agente_name = $('#agente_name').val();
    var agente_direccion = $('#agente_direccion').val();
    var agente_coord_x = $('#agente_coord_x').val();
    var agente_coord_y = $('#agente_coord_y').val();

    if(agente_cuentae == ""){
        alertify.error('Elige una cuenta');
        $('#agente_cuentae').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#agente_cuentae').css('border','');
    }
    if(agente_name == ""){
        alertify.error('Ingrese el nombre del agente');
        $('#agente_name').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#agente_name').css('border','');
    }
    if(agente_direccion == ""){
        alertify.error('Ingrese la direccion del agente');
        $('#agente_direccion').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#agente_direccion').css('border','');
    }

    if (valor == "correcto"){
        var cadena = "agente_cuentae=" + agente_cuentae+
                    "&agente_name="+agente_name+
                    "&agente_coord_x="+agente_coord_x+
                    "&agente_coord_y="+agente_coord_y+
            "&agente_direccion="+agente_direccion;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Agente/save_agente",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Agente/ver_agentes';
                        break;
                    case "2":
                        alertify.error("Fallo el envio");
                        break;
                    case "6":
                        alertify.error("Fallo en la integridad de los datos ingresados");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}
