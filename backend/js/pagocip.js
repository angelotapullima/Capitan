function buscar_codigo() {
    var valor = "correcto";
    var codigo = $('#codigo').val();
    if(codigo == "" || isNaN(codigo) || codigo.length!==6){
        alertify.error('Ingrese un código válido');
        $('#codigo').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#codigo').css('border','');
    }
    if (valor == "correcto"){
        var cadena = "codigo=" + codigo;
        $.ajax({
            type:"POST",
            url: urlweb + "api/PagoCIP/buscar_pagocip_pendiente_por_codigo",
            data: cadena,
            dataType: 'json',
            success:function (r) {
                switch (r.code) {
                    case 1:
                        alertify.success("¡Código encontrado!");
                        var contenido = "<h4>Código: </h4>" +
                            "<h4><b>"+r.result.pagocip_codigo+"</b></h4>" +
                            "<h4>Persona: </h4>" +
                            "<h4><b>"+r.result.person_name+" "+ r.result.person_surname+"</b></h4>" +
                            "<h4>Monto a pagar:</h4>" +
                            "<h4><b style=\"color: red\">"+r.result.pagocip_monto+"</b></h4>" +
                            "<h4>Concepto: </h4>" +
                            "<h4><b>"+r.result.pagocip_concepto+"</b></h4>";
                        $('#pagar_ya').show();
                        $('#bull').val(r.result.id_pagocip);
                        $('#bull2').val(r.result.pagocip_monto);
                        $('#datos').html(contenido);
                        break;
                    case 2:
                        alertify.error("No se encontró el código");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
}
function buscar_codigo_retiro() {
    var valor = "correcto";
    var codigo = $('#codigo').val();
    if(codigo == "" || isNaN(codigo) || codigo.length!==6){
        alertify.error('Ingrese un código válido');
        $('#codigo').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#codigo').css('border','');
    }
    if (valor == "correcto"){
        var cadena = "codigo=" + codigo;
        $.ajax({
            type:"POST",
            url: urlweb + "api/PagoCIP/buscar_pagocip_pendiente_por_codigo_retirar",
            data: cadena,
            dataType: 'json',
            success:function (r) {
                switch (r.code) {
                    case 1:
                        alertify.success("¡Código encontrado!");
                        var contenido = "<h4>Código: </h4>" +
                            "<h4><b>"+r.result.pagocip_codigo+"</b></h4>" +
                            "<h4>Persona: </h4>" +
                            "<h4><b>"+r.result.person_name+" "+ r.result.person_surname+"</b></h4>" +
                            "<h4>Monto a retirar:</h4>" +
                            "<h4><b style=\"color: red\">"+r.result.pagocip_monto+"</b></h4>" +
                            "<h4>Concepto: </h4>" +
                            "<h4><b>"+r.result.pagocip_concepto+"</b></h4>";
                        $('#pagar_ya').show();
                        $('#bull').val(r.result.id_pagocip);
                        $('#bull2').val(r.result.pagocip_monto);
                        $('#datos').html(contenido);
                        break;
                    case 2:
                        alertify.error("No se encontró el código");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
}
function datos_agente() {
    var valor = "correcto";
    var id_agente = $('#id_agente').val();
    if(id_agente == ""){
        valor = "incorrecto";
    }
    if (valor == "correcto"){
        var cadena = "id_agente=" + id_agente;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Agente/listar_agente_por_id",
            data: cadena,
            dataType: 'json',
            success:function (r) {
                switch (r.code) {
                    case 1:
                        $('#saldo_agente').val(r.result.cuentae_saldo);
                        var saldo = $('#saldo_agente').val() * 1;
                        var monto = $('#bull2').val() * 1;
                        if(saldo < monto){
                            $('#i-times').show();
                            $('#i-check').hide();
                            $('#btn-pagar').attr("disabled",true);
                        }else{
                            $('#i-check').show();
                            $('#i-times').hide();
                            $('#btn-pagar').attr("disabled",false);
                        }
                        break;
                    case 2:
                        alertify.error("No se encontró el agente");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
}
function save_detalle_pagocip() {
    var valor = "correcto";
    var id_pagocip = $('#bull').val();
    var id_agente = $('#id_agente').val();

    if(id_pagocip == "" || isNaN(id_pagocip)){
        alertify.error('Codigo Invalido');
        valor = "incorrecto";
    }if(id_agente == "" || isNaN(id_agente)){
        alertify.error('Codigo Invalido');
        valor = "incorrecto";
    }
    if (valor == "correcto"){
        var cadena = "id_pagocip=" + id_pagocip+"&id_agente="+id_agente;
        $.ajax({
            type:"POST",
            url: urlweb + "api/PagoCIP/save_detalle_pagocip",
            data: cadena,
            dataType: 'json',
            success:function (r) {
                switch (r) {
                    case 1:
                        alertify.success("¡Pagado correctamente!");
                        location.href = urlweb +  'PagoCIP/ver_mis_pagos_cip';
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    case 3:
                        alertify.error("Código CIP Invalido");
                        break;
                    case 4:
                        alertify.error("Código Agente Invalido");
                        break;
                    case 4:
                        alertify.error("Fallo en la integridad de los datos");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}
