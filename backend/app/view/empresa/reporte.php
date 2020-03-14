<div class="panel panel-container">
    <div class="row">
       <!-- <div class="col-md-2">
            <div class="form-group">
            <label class="custom-control custom-checkbox">
                <input name="tiempo" onchange="check_rango_()" type="radio" checked value="diario" class="custom-control-input">
                <span class="custom-control-indicator"></span>
                <span class="custom-control-description"><b> Diario</b></span>
            </label>
            </div>
        </div>
        <div class="col-md-2">
            <label class="custom-control custom-checkbox">
                <input name="tiempo" onchange="check_rango_()" type="radio" value="mensual" class="custom-control-input">
                <span class="custom-control-indicator"></span>
                <span class="custom-control-description"><b> Mensual</b></span>
            </label>
        </div>
        <div class="col-md-2">
            <label class="custom-control custom-checkbox">
                <input name="tiempo" onchange="check_rango_()" type="radio" value="rango" class="custom-control-input">
                <span class="custom-control-indicator"></span>
                <span class="custom-control-description"><b> Rango de Fechas</b></span>
            </label>
        </div>-->
        <div class="col-md-6">
            <div id="rango_">
                <div class="row">
                    <div class="col-md-6">
                        <label for="desde" class="col-form-label">Desde</label>
                        <input id="desde" type="date" class="form-control" value="<?= date('Y-m-d'); ?>">
                    </div>
                    <div class="col-md-6">
                        <label for="hasta" class="col-form-label">Hasta</label>
                        <input id="hasta" type="date" class="form-control" value="<?= date('Y-m-d'); ?>">
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-2">
            <button class="btn btn-primary" onclick="buscar_reporte()"><i class="fa fa-search"></i> Buscar</button>
        </div>
    </div>
</div>
<div class="panel panel-container">
    <div class="row" id="resultados">

    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script>
    /*function check_rango_() {
        if($('input:radio[name=tiempo]:checked').val() == "rango"){
            $("#rango_").show();
        }else{
            $("#rango_").hide();
        }
    }*/
    function buscar_reporte() {
        var valor = "correcto";
        var desde = $('#desde').val();
        var hasta = $('#hasta').val();
        var id_empresa=<?= $id; ?>;
        if(desde == ""){
            alertify.error('Seleccione fecha de inicio');
            $('#desde').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#desde').css('border','');
        }
        if(hasta == ""){
            alertify.error('Seleccione fecha_final');
            valor = "incorrecto";
        } else {
            $('#hasta').css('border','');
        }
        if (valor == "correcto"){
            var cadena = "id_empresa=" + id_empresa+
                "&fecha_i="+desde+
                "&fecha_f="+hasta;
            $.ajax({
                type:"POST",
                url: urlweb + "api/Empresa/estadisticas_por_empresa",
                dataType: 'json',
                data: cadena,
                success:function (r) {
                    var lista = "";
                    var total_reporte =0;
                    var canchas = [];
                    for (var j=0;j<r.results.length;j++){
                        for(var key in r.results[j]){
                            var keyName2 = key;
                        }
                        for (var jj=0;jj<r.results[j][keyName2].length;jj++){
                            var cccc=r.results[j][keyName2][jj].cancha_id;
                            if(!canchas.includes(cccc)){
                                canchas.push(cccc);
                            }
                        }
                    }
                    for (var i=0;i<r.results.length;i++){
                        for(var key in r.results[i]){
                            var keyName = key;
                        }
                        var total_dia = 0;
                        var total_dia_app = 0;
                        var total_dia_local = 0;
                        var cant_reservas = 0;
                        var cant_reservas_app = 0;
                        var cant_reservas_local = 0;
                        lista+="<div class=\"col-md-10\"><h3>"+keyName+"</h3>" +
                            "<table class=\"table table-responsive table-bordered\"><thead><tr><th>Hora</th><th>Reserva</th><th>Procedencia</th><th>Monto</th></tr></thead><tbody>";
                        for (var k=0;k<canchas.length;k++){
                            var nombre_cancha ="";
                            var total_cancha = 0;
                            var entro = 0;
                            for (var kk=0;kk<r.results[i][keyName].length;kk++){
                                if(canchas[k] == r.results[i][keyName][kk].cancha_id && entro ==0){
                                    entro = 1;
                                    nombre_cancha = r.results[i][keyName][kk].cancha_nombre;
                                    lista+="<tr style=\"background: lightgrey\"><td colspan=\"4\">"+nombre_cancha+"</td></tr>";
                                }
                            }
                            for (var kk=0;kk<r.results[i][keyName].length;kk++){
                                if(canchas[k] == r.results[i][keyName][kk].cancha_id){
                                    var pago = r.results[i][keyName][kk].reserva_pago1 * 1 + r.results[i][keyName][kk].reserva_pago2 * 1;
                                    total_cancha += pago;
                                    var proc = "";
                                    if(r.results[i][keyName][kk].reserva_tipopago==1){
                                        proc = "App";
                                        total_dia_app+=pago;
                                        cant_reservas_app++;
                                    }else{
                                        proc = "Local";
                                        total_dia_local+=pago;
                                        cant_reservas_local++;
                                    }
                                    lista+="<tr><td>"+r.results[i][keyName][kk].reserva_hora+"</td><td>"+r.results[i][keyName][kk].reserva_nombre+"</td><td>"+proc+"<td>"+pago+"</td></tr>";
                                    cant_reservas++;
                                }
                            }
                            total_dia+=total_cancha;
                            lista+="<tr style=\"background: #ebccd1;font-weight: bold\"><td colspan=\"3\">Total "+nombre_cancha+"</td><td>"+total_cancha+"</td></tr>";
                        }
                        total_reporte+=total_dia;
                        lista+="<tr style=\"background: #edde34;font-weight: bold;\"><td colspan=\"3\">Total App ( "+cant_reservas_app+" reservas)</td><td>"+total_dia_app+"</td></tr>";
                        lista+="<tr style=\"background: #edde34;font-weight: bold;\"><td colspan=\"3\">Total Local ( "+cant_reservas_local+" reservas)</td><td>"+total_dia_local+"</td></tr>";
                        lista+="<tr style=\"background: #edde34;font-weight: bold;\"><td colspan=\"3\">Total "+keyName+" ( "+cant_reservas+" reservas)</td><td>"+total_dia+"</td></tr>";
                    }
                    lista+="<tr style='background: #5cb85c; color: #fff;font-weight: bold'><td colspan='3'>Total del reporte</td><td> "+total_reporte+"</td></tr></tbody></table></div>";
                    $("#resultados").html(lista);
                }
            });
        }
    }
</script>
</body>
</html>
