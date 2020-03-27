$("#fupForm").on('submit', function(e){
    e.preventDefault();
    var valor = "correcto";
    var usuario_id = $('#usuario_id').val();
    var ubigeo_id = $('#ubigeo_id').val();
    var empresa_name = $('#empresa_name').val();
    var descripcion = $('#descripcion').val();
    var telefono_1 = $('#telefono_1').val();
    var telefono_2 = $('#telefono_2').val();
    var horario_1 = $('#horario_1').val();
    var horario_2 = $('#horario_2').val();
    var direccion = $('#direccion').val();
    var coord_x = $('#coord_x').val();
    var coord_y = $('#coord_y').val();
    if(empresa_name == ""){
        alertify.error('El campo Nombre de Empresa está vacío');
        $('#empresa_name').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#empresa_name').css('border','');
    }if(usuario_id == ""){
        alertify.error('Elige un usuario responsable');
        $('#usuario_id').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#usuario_id').css('border','');
    }if(ubigeo_id == ""){
        alertify.error('Elige una sede');
        $('#ubigeo_id').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#ubigeo_id').css('border','');
    }
    if(descripcion == ""){
        alertify.error('Escribe una descripción');
        $('#descripcion').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#descripcion').css('border','');
    }
    if(telefono_1 == ""){
        alertify.error('Escribe un teléfono');
        $('#telefono_1').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#telefono_1').css('border','');
    }
    if(horario_1 == ""){
        $('#horario_1').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#horario_1').css('border','');
    }if(horario_2 == ""){
        $('#horario_2').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#horario_2').css('border','');
    }if(direccion == ""){
        alertify.error('Elige un punto en el mapa');
        $('#direccion').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#direccion').css('border','');
    }if(coord_x == "" || coord_y == ""){
        alertify.error('Elige un punto en el mapa');
        $('#coord_x').css('border','solid red');
        $('#coord_y').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#coord_x').css('border','');
        $('#coord_y').css('border','');
    }

    if (valor == "correcto"){
        $.ajax({
            type:"POST",
            url: urlweb + "api/Empresa/registrar",
            dataType: 'json',
            data: new FormData(this),
            contentType: false,
            cache: false,
            processData:false,
            beforeSend: function(){
                $('.submitBtn').attr("disabled","disabled");
                $('#fupForm').css("opacity",".5");
            },
            success:function (r) {
                switch (r) {
                    case 1:
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Empresa/index';
                        break;
                    case 2:
                        alertify.error("Fallo el envio");
                        break;
                    case 6:
                        alertify.error("Fallo en la integridad de los datos enviados");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
                $('#fupForm').css("opacity","");
                $(".submitBtn").removeAttr("disabled");
            }
        });
    }

});
$("#fupFormCancha").on('submit', function(e){
    e.preventDefault();
    var valor = "correcto";
    var id_empresa = $('#id_empresa').val();
    var nombre = $('#nombre').val();
    var dimensiones = $('#dimensiones').val();
    var precioD = $('#precioD').val();
    var precioN = $('#precioN').val();
    if(nombre == ""){
        alertify.error('El campo Nombre de Cancha está vacío');
        $('#nombre').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#nombre').css('border','');
    }if(dimensiones == ""){
        alertify.error('Elige unas dimensiones');
        $('#dimensiones').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#dimensiones').css('border','');
    }if(precioD == ""){
        alertify.error('Elige una precio de Dia');
        $('#precioD').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#precioD').css('border','');
    }
    if(precioN == ""){
        alertify.error('Escribe un precio de Noche');
        $('#precioN').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#precioN').css('border','');
    }
    if (valor == "correcto"){
        $.ajax({
            type:"POST",
            url: urlweb + "api/Empresa/registrar_cancha",
            dataType: 'json',
            data: new FormData(this),
            contentType: false,
            cache: false,
            processData:false,
            beforeSend: function(){
                $('.submitBtn').attr("disabled","disabled");
                $('#fupFormCancha').css("opacity",".5");
            },
            success:function (r) {
                switch (r) {
                    case 1:
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Empresa/ver/'+id_empresa;
                        break;
                    case 2:
                        alertify.error("Fallo el envio");
                        break;
                    case 6:
                        alertify.error("Fallo en la integridad de los datos enviados");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
                $('#fupFormCancha').css("opacity","");
                $(".submitBtn").removeAttr("disabled");
            }
        });
    }
});
function form_reserva(j,k,l,m,o) {
$("#lbl_fecha").html("<i class='fa fa-calendar'></i> "+j);
$("#reserva_fecha").val(o);
$("#reserva_hora").val(k);
$("#lbl_hora").html("<i class='fa fa-clock-o'></i> "+k);
var n = k.split(':');
if(n[0]<=17){
    $("#lbl_costo").html("<b> S/. "+l+"</b>");
    $("#pago1").val(l);
}else{
    $("#lbl_costo").html("<b> S/. "+m+"</b>");
    $("#pago1").val(m);
}
}
function form_reserva1(j,k,l,m,o,p,q,i) {
$("#lbl_fecha1").html("<i class='fa fa-calendar'></i> "+j);
$("#lbl_hora1").html("<i class='fa fa-clock-o'></i> "+k);
$("#lbl_nombre").html(o);
var n = k.split(':');
var aumentar = "";
if(n[0]<=17){
    if(q==2){
        var resta = l*1 - p*1;
        var butt = "<button onclick='pagar_restante("+i+","+resta+")'>Pagar lo restante</button>";
        aumentar = " (Falta pagar S/. "+ resta +") "+butt;
    }
    $("#lbl_costo1").html("<b> S/. "+l+"</b>");
    $("#lbl_monto1").html("S/. "+p+aumentar);
}else{
    if(q==2){
        var resta = m*1 - p*1;
        var butt = "<button onclick='pagar_restante("+i+","+resta+")'>Pagar lo restante</button>";
        aumentar = " (Falta pagar S/. "+ resta +") "+butt;
    }
    $("#lbl_costo1").html("<b> S/. "+m+"</b>");
    $("#lbl_monto1").html("S/. "+p+aumentar);
}
}
function intlRound (numero, decimales = 2, usarComa = false) {
    //Esta respuesta
    var opciones = {
        maximumFractionDigits: decimales,
        useGrouping: false
    };
    return new Intl.NumberFormat((usarComa ? "es" : "en"), opciones).format(numero);
}
function pagar_restante(i,j) {
    var cadena = "pago2="+j+"&id="+i;
    var cancha_id = $('#cancha_id').val();
    $.ajax({
        type:"POST",
        url: urlweb + "api/Empresa/pagar_restante",
        dataType: 'json',
        data: cadena,
        success:function (r) {
            switch (r) {
                case 1:
                    alertify.success("¡Guardado!");
                    location.href = urlweb +  'Empresa/ver_cancha/'+cancha_id;
                    break;
                case 2:
                    alertify.error("Fallo el envio");
                    break;
                case 6:
                    alertify.error("Fallo en la integridad de los datos enviados");
                    break;
                default:
                    alertify.error("ERROR DESCONOCIDO");
            }
        }
    });
}
function reservar() {
    var valor = "correcto";
    var cancha_id = $('#cancha_id').val();
    var reserva_nombre = $('#reserva_nombre').val();
    var reserva_monto = $('#reserva_monto').val();
    var pago1 = $('#pago1').val() * 1;
    var reserva_fecha = $('#reserva_fecha').val();
    var reserva_hora = $('#reserva_hora').val();
    var estado;
    if(reserva_nombre == ""){
        alertify.error('Ingrese un nombre de reserva');
        $('#reserva_nombre').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#reserva_nombre').css('border','');
    }if(reserva_monto == ""){
        alertify.error('Ingrese el monto de reserva');
        $('#reserva_monto').css('border','solid red');
        valor = "incorrecto";
    } else {
        $('#reserva_monto').css('border','');
    }
    if(reserva_monto>pago1){
        alertify.error('El monto pagado no puede ser mayor al costo de la reserva.');
        $('#reserva_monto').css('border','solid red');
        valor = "incorrecto";
    }else if(reserva_monto==pago1) {
        estado = 1;
    }else{
        estado = 2;
    }

    if (valor == "correcto"){
        var cadena = "id_cancha=" + cancha_id+
                    "&nombre="+reserva_nombre+
                    "&hora="+reserva_hora+
                    "&pago1="+reserva_monto+
                    "&tipopago=0"+
                    "&estado="+estado+
                    "&fecha="+reserva_fecha;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Empresa/registrar_reserva",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r) {
                    case 1:
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Empresa/ver_cancha/'+cancha_id;
                        break;
                    case 2:
                        alertify.error("Fallo el envio");
                        break;
                    case 6:
                        alertify.error("Fallo en la integridad de los datos enviados");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }

}