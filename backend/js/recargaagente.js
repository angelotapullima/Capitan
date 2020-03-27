function recargar_agente() {
    var valor = "correcto";
    var id_emisor = $('#id_emisor').val();
    var id_agente = $('#id_agente').val();
    var monto = $('#monto').val();
    var nro_operacion = $('#nro_operacion').val();
    var concepto = $('#concepto').val();

    if(id_emisor == ""){
        alertify.error('Elige una cuenta');
        $('#id_emisor').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#id_emisor').css('border','');
    }
    if(id_agente == ""){
        alertify.error('Elige un agente');
        $('#id_agente').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#id_agente').css('border','');
    }
    if(monto == ""){
        alertify.error('Ingrese el monto');
        $('#monto').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#monto').css('border','');
    }
    if(nro_operacion == ""){
        alertify.error('Ingrese el nro de operacion');
        $('#nro_operacion').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#nro_operacion').css('border','');
    }
    if(concepto == ""){
        alertify.error('Ingrese el concepto');
        $('#concepto').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#concepto').css('border','');
    }

    if (valor == "correcto"){
        var cadena = "id_emisor=" + id_emisor+
            "&id_agente="+id_agente+
            "&monto="+monto+
            "&nro_operacion="+nro_operacion+
            "&concepto="+concepto;
        $.ajax({
            type:"POST",
            url: urlweb + "api/RecargaAgente/save_recarga_agente",
            data: cadena,
            success:function (r) {
                switch (r) {
                    case "1":
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'RecargaAgente/ver_recargas_agente';
                        break;
                    case "2":
                        alertify.error("Fallo el envio");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}