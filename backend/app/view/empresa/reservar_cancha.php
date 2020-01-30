<div class="row">
    <div class="col-md-12">
        <h4 class="header-title">Resevar Cancha</h4>
        <div class="row">
            <div class="col-md-2">
                <div class="form-group">
                    <label for="fecha" class="col-form-label"><i class="fa fa-calendar"></i> Fecha de reserva</label>
                    <input class="form-control" type="text" readonly id="fecha" value="<?= $this->validate->get_date_nominal($fecha,'Date','Date','es'); ?>">
                    <input type="hidden" id="reserva_fecha" value="<?= $fecha; ?>">
                </div>
                <div class="form-group">
                    <label for="hora" class="col-form-label"><i class="fa fa-clock-o"></i> Hora de reserva</label>
                    <input class="form-control" type="text" readonly id="hora" value="<?= $hora; ?>">
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label><i class="fa fa-building"></i> Lugar de la Reserva</label>
                    <img alt="imagen" src="<?= _SERVER_ . $datos_empresa->empresa_foto; ?>" id="imagen" class=" img-responsive img-thumbnail">
                </div>
            </div>
            <div class="col-md-3" style="word-break: break-all">
                <h4><b><?= $datos_empresa->empresa_nombre; ?></b></h4>
                <small><i class="fa fa-map-marker"></i> <?= $datos_empresa->empresa_direccion; ?></small>
                <div class="form-group">
                    <label for="cancha" class="col-form-label"><i class="fa fa-soccer-ball-o"></i> Cancha seleccionada</label>
                    <select onchange="show_cr()" class="form-control" id="cancha">
                        <option value="">--Seleccione--</option>
                        <?php
                        foreach ($canchas as $c){
                            if($c->cancha_id==$cancha){
                                echo "<option value='".$c->cancha_id."' selected>".$c->cancha_nombre."</option>";
                            }else{
                                echo "<option value='".$c->cancha_id."'>".$c->cancha_nombre."</option>";
                            }
                        }
                        ?>
                    </select>
                </div>
            </div>
            <div class="col-md-4" id="costo_reserva" style="<?= $class; ?>">
                <table class="table table-bordered table-responsive">
                    <tbody style="font-size: 15pt;">
                        <tr>
                            <td><b>Reserva</b></td>
                            <td><img src="<?= _SERVER_ ?>media/bufi.png" width="30" alt="bufi"> <span id="cr_cr" style="font-weight: bold;color: green;"><?= $precioCancha ?></span></td>
                        </tr>
                        <tr>
                            <td><b>Comisión</b></td>
                            <td><img src="<?= _SERVER_ ?>media/bufi.png" width="30" alt="bufi"> <span id="cr_cm" style="font-weight: bold;color: green;"><?= $comision; ?></span></td>
                        </tr>
                        <tr style="border-top: 2px solid green;">
                            <td><b>TOTAL</b></td>
                            <td style="font-weight: bold;color: green;"><img src="<?= _SERVER_ ?>media/bufi.png" width="30" alt="bufi"> <span id="cr_t"><?= number_format(($comision * 1 + $precioCancha * 1), 2, '.', ''); ?></span></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <label for="reserva_nombre">Nombre de la Reserva</label>
                <input type="text" class="form-control" id="reserva_nombre">
                <div id="equipos" style="display: none;">
                    <label for="equipo_pagoyo">Seleccione Equipo</label>
                    <select class="form-control" id="equipo_pagoyo">
                        <option value="">--Seleccione--</option>
                        <?php
                        foreach ($mis_equipos as $m){
                            ?>
                            <option value="<?= $m->equipo_id; ?>"><?= $m->equipo_nombre; ?></option>
                        <?php
                        }
                        ?>
                    </select>
                </div>
            </div>
            <div class="col-md-3">
                <label>Forma de Pago</label>
                <div class="form-group">
                    <label for="pago_yo">Yo pago todo</label>
                    <input type="radio" onchange="buscar_saldo()" name="pago" id="pago_yo" value="yo">
                    <label for="pago_colaboracion">Pagar con una chancha</label>
                    <input type="radio" name="pago" onchange="buscar_saldo()" id="pago_colaboracion" value="colaboracion">
                </div>
            </div>
            <div class="col-md-4" id="forma_pago">

            </div>
        </div>
        <div class="row" id="forma2">

        </div>
        <input type="hidden" id="id_equipo">
        <input type="hidden" id="id_colaboracion">
    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>empresa.js"></script>
<script>
    function buscar_saldo() {
        var pago = $("input:radio[name=pago]:checked").val();
        var url_ =urlweb +"";
        var cadena="";
        if(pago=="yo"){
            url_+="api/Empresa/obtener_saldo_actual";
        }else{
            url_+="api/Empresa/obtener_chanchas_disponibles";
        }
        $.ajax({
            type:"POST",
            url: url_,
            dataType: 'json',
            data: cadena,
            success:function (r) {
                if($("#cancha").val()!=""){
                    if(pago=="yo"){
                        var saldo = r.cuenta_saldo;
                        var total = $("#cr_t").html();
                        if(saldo>total){
                            $("#forma_pago").html("<table class='table table-bordered table-responsive'><tr><td>Saldo Actual</td><td>"+saldo+"</td></tr><tr><td colspan='2'>El saldo es insuficiente.Puedes intentar comprar más Bufis o pagar con una chancha.</td></tr></table>");
                        }else{
                            $("#forma_pago").html("<table class='table table-bordered table-responsive'><tr><td>Saldo Actual</td><td>"+saldo+"</td></tr></table><button onclick='confirmar_reserva()' class='btn btn-sm btn-danger'>Confirmar Reserva</button>");
                        }
                        $("#forma2").html("");
                        $("#equipos").show();
                    }else{
                        $("#equipos").hide();
                        var lista="";
                        for(var i=0;i<r.results.length;i++){
                            lista+="<div class='row'><div class='col-md-4'> <table class='table table-bordered table-responsive'><tbody>" +
                                "<tr>" +
                                "<td>Equipo</td>" +
                                "<td>"+r.results[i].equipo+"</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td>Nombre</td>" +
                                "<td>"+r.results[i].nombre+"</td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td>Fecha</td>" +
                                "<td>"+r.results[i].fecha+"</td>" +
                                "</tr><tr><td colspan='2'>Colaboraciones</td></tr>";
                            var total=0;
                            for(var j=0;j<r.results[i].detalle.length;j++){
                                total=total+(r.results[i].detalle[j].detalle_colaboracion_monto * 1);
                                lista+="<tr>" +
                                    "<td>"+r.results[i].detalle[j].user_nickname+"</td>" +
                                    "<td>"+r.results[i].detalle[j].detalle_colaboracion_monto+"</td>" +
                                    "</tr>";
                            }
                            lista+= "<tr><td>Total</td><td>"+total+"</td></tr></tbody></table>";
                            if($("#cr_t").html()!=total){
                                lista+="<button class='btn btn-sm btn-primary'>El total de la chancha debe ser igual al total a pagar.</button></div></div><br><br>";
                            }else{
                                lista+="<button onclick='elegir_chancha("+r.results[i].id+",\""+r.results[i].nombre+"\","+r.results[i].id_equipo+")' class='btn btn-sm btn-primary'>Elegir esta chancha</button></div></div><br><br>";
                            }
                        }
                        $("#forma_pago").html("");
                        $("#forma2").html(lista);
                    }
                }
            }
        });
    }
    function elegir_chancha(id,nombre,id_equipo) {
        $("#forma_pago").html("<table class='table table-bordered table-responsive'><tr><td>Chancha elegida</td><td>"+nombre+"</td></tr></table><button onclick='confirmar_reserva()' class='btn btn-sm btn-danger'>Confirmar Reserva</button>");
        $("#id_equipo").val(id_equipo);
        $("#id_colaboracion").val(id);
    }
    function show_cr() {
        var id = $("#cancha").val();
        if(id!=""){
            var hora__ = $("#hora").val();
            var hora_=hora__.split(':');
            var hora = hora_[0];
            var pre = 0;
            $.ajax({
                type:"POST",
                url: urlweb+"api/Empresa/listar_cancha_por_id",
                dataType: 'json',
                data: "id="+id,
                success:function (r) {
                    $("#costo_reserva").show();
                    if(hora>=18){
                        $("#cr_cr").html(r.cancha_precioN);
                        pre=r.cancha_precioN;
                    }else{
                        $("#cr_cr").html(r.cancha_precioD);
                        pre=r.cancha_precioD;
                    }
                    var tt=(pre * 1 + 3.00);
                    var tot = parseFloat(intlRound(tt,2,false));
                    $("#cr_cm").html("3.00");
                    $("#cr_t").html(tot);
                }
            });
        }else{
            $("#costo_reserva").hide();
        }
    }
    function confirmar_reserva() {
        var valor = "correcto";
        var cancha_id = $('#cancha').val();
        var reserva_nombre = $('#reserva_nombre').val();
        var pago1 = $("#cr_cr").html();
        var reserva_fecha = $('#reserva_fecha').val();
        var reserva_hora = $('#hora').val();
        var id_equipo;
        var pago = $("input:radio[name=pago]:checked").val();
        var pago_tipo;
        var id_colaboracion;
        var comision=$("#cr_cm").html();
        if(pago=="yo"){
            pago_tipo=1;
            id_colaboracion=0;
            id_equipo = $("#equipo_pagoyo").val();
        }else{
            pago_tipo=2;
            id_equipo = $('#id_equipo').val();
            id_colaboracion=$("#id_colaboracion").val();
        }
        var estado;
        if(reserva_nombre == ""){
            alertify.error('Ingrese un nombre de reserva');
            $('#reserva_nombre').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#reserva_nombre').css('border','');
        }
        if(id_equipo == ""){
            alertify.error('Seleccione un equipo');
            valor = "incorrecto";
        }
        if (valor == "correcto"){
            var cadena = "id_cancha=" + cancha_id+
                "&pago_id_user="+<?= $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_); ?>+
                "&pago_equipo_id="+id_equipo+
                "&nombre="+reserva_nombre+
                "&hora="+reserva_hora+
                "&pago1="+pago1+
                "&pago_tipo="+pago_tipo+
                "&id_colaboracion="+id_colaboracion+
                "&pago_comision="+comision+
                "&tipopago=1"+
                "&estado=1"+
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
</script>
</body>
</html>
