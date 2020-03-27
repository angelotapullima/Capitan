function recargar_mi_cuenta() {
    var valor = "correcto";
    var monto = $('#monto').val();
    var tipo = $('input:radio[name=tipo]:checked').val();

    if(monto == "" || isNaN(monto) || monto<=0 || monto>1000){
        alertify.error('Ingrese un monto válido');
        $('#monto').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#monto').css('border','');
    }
    if(tipo == ""){
        alertify.error('Seleccione un método de pago');
        valor = "incorrecto";
    }
    if (valor == "correcto"){
        var cadena = "monto=" + monto+"&tipo="+tipo;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Cuenta/save_recargar_mi_cuenta",
            data: cadena,
            dataType: 'json',
            success:function (r) {
                switch (r.result.code) {
                    case 1:
                        alertify.success("¡Guardado!");
                        var contenido = "<h4 style=\"text-align: center\"><b>Muchas gracias por recargar</b></h4>" +
                            "<h4>Su solicitud de recarga fue realizada con éxito. Acércate a cualquier agente autorizado y brinda el siguiente código:</h4>" +
                            "<h4 style=\"color: red; text-align: center; background: #ecd8db\"><b>"+r.result.codigo+"</b></h4>" +
                            "<h4><b>Monto de recarga:</b> S/. "+ r.result.monto +"</h4>" +
                            "<h4><b>Fecha de expiración:</b> "+r.result.date_expiracion+"</h4>" +
                            "<h5>*Se le hizo llegar un email con esta misma información</h5>" +
                            "<button class=\"btn btn-success\">Ver agentes autorizados</button>" +
                            "<a href='"+ urlweb +"Admin/index' class=\"btn btn-primary\">Continuar</a>";
                        $('#codigocip').show();
                        $('#btn_recargar_mi_cuenta').hide();
                        $('#monto').val(" ");
                        $('#codigocip').html(contenido);
                        break;
                    case 2:
                        alertify.error("Fallo el envio");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}
function retirar() {
    var valor = "correcto";
    var monto = $('#monto').val();

    if(monto == "" || isNaN(monto) || monto<=0 || monto>1000){
        alertify.error('Ingrese un monto válido');
        $('#monto').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#monto').css('border','');
    }
    if (valor == "correcto"){
        var cadena = "monto=" + monto;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Cuenta/save_retirar",
            data: cadena,
            dataType: 'json',
            success:function (r) {
                switch (r.result.code) {
                    case 1:
                        alertify.success("¡Guardado!");
                        var contenido = "<h4 style=\"text-align: center\"><b>Muchas gracias por retirar</b></h4>" +
                            "<h4>Su solicitud de retiro fue realizada con éxito. Acércate a cualquier agente autorizado y brinda el siguiente código:</h4>" +
                            "<h4 style=\"color: red; text-align: center; background: #ecd8db\"><b>"+r.result.codigo+"</b></h4>" +
                            "<h4><b>Monto de retiro:</b> S/. "+ r.result.monto +"</h4>" +
                            "<h4><b>Fecha de expiración:</b> "+r.result.date_expiracion+"</h4>" +
                            "<h5>*Se le hizo llegar un email con esta misma información</h5>" +
                            "<button class=\"btn btn-success\">Ver agentes autorizados</button>" +
                            "<a href='"+ urlweb +"Admin/index' class=\"btn btn-primary\">Continuar</a>";
                        $('#codigocip').show();
                        $('#btn_retirar').hide();
                        $('#monto').val(" ");
                        $('#codigocip').html(contenido);
                        break;
                    case 2:
                        alertify.error("Fallo el envio");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}
