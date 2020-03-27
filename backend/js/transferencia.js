function buscar_cuenta() {
    var valor = "correcto";
    var codigo = $('#codigo').val();
    if(codigo == "" || codigo.length!==12){
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
            url: urlweb + "api/Transferencia/buscar_cuenta_por_codigo",
            data: cadena,
            dataType: 'json',
            success:function (r) {
                switch (r.code) {
                    case 1:
                        alertify.success("¡Código encontrado!");
                        var contenido = "<h4>Cuenta: </h4>" +
                            "<h4><b>"+r.result.cuenta_codigo+"</b></h4>" +
                            "<h4>Persona: </h4>" +
                            "<h4><b>"+r.result.person_name+" "+ r.result.person_surname+"</b></h4>";
                        $('#pagar_ya').show();
                        $('#bull').val(r.result.id_cuenta);
                        $('#datos').html(contenido);
                        break;
                    case 2:
                        alertify.error("No se encontró el nro de cuenta");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
}
function save_transferencia() {
    var valor = "correcto";
    var id_cuenta = $('#bull').val();
    var saldo = $('#saldo_actual').val() * 1;
    var monto = $('#monto').val() * 1;
    if(saldo < monto){
        alertify.error('Saldo insuficiente');
        valor = "incorrecto";
    }
    if(id_cuenta == ""){
        alertify.error('Cuenta inválida');
        valor = "incorrecto";
    }
    if (valor == "correcto"){
        var cadena = "id_cuenta=" + id_cuenta+"&monto="+monto;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Transferencia/save_transferencia",
            data: cadena,
            dataType: 'json',
            success:function (r) {
                switch (r) {
                    case 1:
                        alertify.success("¡Transferencia realizada!");
                        location.href = urlweb +  'Admin/index';
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    case 3:
                        alertify.error("El monto no puede ser mayor que el saldo");
                        break;
                    case 6:
                        alertify.error("Fallo en la integridad de los datos");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}
