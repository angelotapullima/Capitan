<div class="col-md-10">
    <div class="panel panel-container">
        <div class="row">
            <div class="col-md-9">
                <div class="panel border-right">
                    <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Búsqueda avanzada de Canchas</b></h4></div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-11 col-md-offset-1">
                <div class="panel border-right">
                    <div class="box box-primary">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="fecha" class="col-form-label">Fecha</label>
                                    <input type="date" class="form-control" id="fecha" min="<?= date('Y-m-d'); ?>">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="hora" class="col-form-label">Hora</label>
                                    <select class="form-control" id="hora">
                                        <option value="Todos">Cualquier hora</option>
                                        <?php
                                        for($i=9;$i<24;$i++){
                                            $j = $i + 1;$hora =$i.":00-".$j.":00";
                                            echo "<option value='$hora'>$hora</option>";
                                        }
                                        ?>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="negocio" class="col-form-label">Lugar</label>
                                    <select class="form-control" id="negocio">
                                        <option value="Todos">Cualquier lugar</option>
                                        <?php
                                        foreach($empresas as $e){
                                            echo "<option value='".$e->empresa_id."'>".$e->empresa_nombre."</option>";
                                        }
                                        ?>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <button onclick="busqueda_avanzada();" type="submit" name="submit" class="btn btn-success submitBtn"><i class="fa fa-search"></i> Buscar Cancha</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-1 col-md-11" id="resultados">

            </div>
        </div>
    </div>
</div>
    <script src="<?php echo _SERVER_ . _STYLES_ADMIN_;?>js/jquery-1.11.1.min.js"></script>
    <script src="<?php echo  _SERVER_ . _STYLES_ALL_;?>charts/Chart.min.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script>
    function busqueda_avanzada() {
        var valor = "correcto";
        var fecha = $("#fecha").val();
        if(fecha == ""){
            alertify.error('Seleccione una fecha');
            $('#fecha').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#fecha').css('border','');
        }
        var hora = $("#hora").val();
        var negocio = $("#negocio").val();
        var fecha_cc_ = fecha.split('-');
        var fecha_cc = new Date(''+fecha);
        var dias=["Sun", "lun", "mar", "mie", "jue", "vie", "sab"];
        var hi;
        var hs_;
        var hs;
        var keyname_;
        if(valor=="correcto"){
            cadena = "fecha= "+ fecha +"&hora="+hora+"&negocio="+negocio;
            $.ajax({
                type:"POST",
                url: urlweb + "api/Empresa/busqueda_avanzada",
                dataType: 'json',
                data: cadena,
                success:function (r) {
                    var dia_actual = dias[fecha_cc.getUTCDay()];
                    var lista ="";
                    if(hora!="Todos"){
                        lista +=
                            "<div class=\"panel panel-container\" style=\"padding-left: 20px;\">"+
                            "<div class=\"row\">"+
                            "                           <div class=\"col-md-10\">"+
                            "                               <h3><i style=\"color: green\" class=\"fa fa-clock-o\"></i> "+hora+"</h3>"+
                            "                           </div>"+
                            "                       </div>" +
                            "</div>";
                        lista+="<div class='row'>";
                        var iii = 0;
                        for(var y= 0;y<r.results.length;y++ ){

                            if(dia_actual=="Sun"){
                                var horario = r.results[y].empresa_horario_d;
                            }else{
                                var horario = r.results[y].empresa_horario_ls;
                            }
                            hi = horario.split(':');
                            hs_ = horario.split('-');
                            hs = hs_[1].split(':');
                            var hora_ab_ = hora.split(':');
                            var hora_ab = hora_ab_[0] * 1;
                            if(hora_ab >= hi[0] && hora_ab<=hs[0]){
                                var abierto = true;
                            }else{
                                var abierto = false;
                            }
                            if(abierto==true){
                                if(iii % 4==0){
                                    lista+="</div><div class='row'>";
                                }
                            lista+="<div class=\"col-xs-3 col-md-3 col-lg-3 no-padding\">" +
                                    "                    <a href='"+urlweb+"Empresa/ver/"+ r.results[y].empresa_id +"' style=\"text-decoration: none\">" +
                                    "                        <div class=\"panel panel-teal panel-widget border-right\">" +
                                    "                            <div class=\"row no-padding\">" +
                                    "                                <img class=\"img-responsive img-thumbnail\" src='"+urlweb+ r.results[y].empresa_foto+"'>" +
                                    "                            </div>" +
                                    "                            <div class=\"large\"><h4>"+ r.results[y].empresa_nombre+"</h4></div>" +
                                    "                            <p style=\"color: green\"><i class=\"fa fa-sun-o\"></i> S/. "+ r.results[y].cancha_precioD+"</p>" +
                                    "                            <p style=\"color: green\"><i class=\"fa fa-moon-o\"></i> S/. "+ r.results[y].cancha_precioN+"</p>" +
                                    "                        </div>" +
                                    "                    </a></div>";
                                iii++;
                            }
                        }
                        lista+="</div>";
                    }else{
                        for(var i= 0;i<r.results.length - 1;i++ ){
                            for(var key in r.results[i]){
                                var keyName = key;
                            }
                            keyname_ = keyName * 1;
                            var jj = keyName * 1 + 1;
                            var horaa = keyName+":00-"+jj+":00";
                            lista +=
                                "<div class='panel panel-container' style='padding-left: 20px;'>"+
                                "<div class='row'>"+
                                "<div class='col-md-10'>"+
                                "<h3><i style='color: green' class='fa fa-clock-o'></i> "+horaa+"</h3>"+
                                "</div>"+
                                "</div>" +
                                "</div>";
                            lista+="<div class='row'>";
                            var iiii = 0;
                            for(var l =0;l<r.results[i][keyName].length;l++){
                                if(dia_actual=="Sun"){
                                    var horario = r.results[i][keyName][l].empresa_horario_d;
                                }else{
                                    var horario = r.results[i][keyName][l].empresa_horario_ls;
                                }
                                hi = horario.split(':');
                                hs_ = horario.split('-');
                                hs = hs_[1].split(':');
                                if(keyname_ >= hi[0] && keyname_<=hs[0]){
                                    var abierto = true;
                                }else{
                                    var abierto = false;
                                }
                                if(abierto==true){
                                    if(iiii%4==0){
                                        lista+="</div><div class='row'>";
                                    }
                                lista+="<div class=\"col-xs-6 col-md-3 col-lg-3 no-padding\">" +
                                    "                    <a href='"+urlweb+"Empresa/ver/"+ r.results[i][keyName][l].empresa_id +"' style=\"text-decoration: none\">" +
                                    "                        <div class=\"panel panel-teal panel-widget border-right\">" +
                                    "                            <div class=\"row no-padding\">" +
                                    "                                <img class=\"img-responsive img-thumbnail\" src='"+urlweb+ r.results[i][keyName][l].empresa_foto+"'>" +
                                    "                            </div>" +
                                    "                            <div class=\"large\"><h4>"+ r.results[i][keyName][l].empresa_nombre+"</h4></div>" +
                                    "                            <p style=\"color: green\"><i class=\"fa fa-sun-o\"></i> S/. "+ r.results[i][keyName][l].cancha_precioD+"</p>" +
                                    "                            <p style=\"color: green\"><i class=\"fa fa-moon-o\"></i> S/. "+ r.results[i][keyName][l].cancha_precioN+"</p>" +
                                    "                        </div>" +
                                    "                    </a></div>";
                                    iiii++;
                                }
                            }
                            lista+="</div>";
                        }
                    }
                    $("#resultados").html(lista);
                }
            });
        }
    }
</script>