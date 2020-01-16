<form enctype="multipart/form-data" method="post" id="fupFormTorneo">
    <div class="row">
        <div class="col-md-6">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h4 class="header-title">Crear Torneo</h4>
                </div>
                <div class="box-body">
                    <div class="form-group">
                        <label for="nombre" class="col-form-label">Nombre del Torneo</label>
                        <input class="form-control" type="text" name="nombre" id="nombre">
                        <input type="hidden" name="usuario_id" id="usuario_id" value="<?= $this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_); ?>">
                    </div>
                    <div class="form-group">
                        <label for="descripcion" class="col-form-label">Descripción</label>
                        <textarea class="form-control" name="descripcion" id="descripcion" rows="4"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="organizador" class="col-form-label">Organizador</label>
                        <input class="form-control" type="text" name="organizador" id="organizador">
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="fecha" class="col-form-label">Fecha</label>
                                <input class="form-control" type="date" name="fecha" id="fecha" min="<?= date('Y-m-d');?>">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="hora" class="col-form-label">Hora</label>
                                <input class="form-control" type="time" name="hora" id="hora">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="lugar" class="col-form-label">Lugar</label>
                                <input class="form-control" type="text" id="lugar" name="lugar">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="costo" class="col-form-label">Costo de Inscripción</label>
                                <input class="form-control" type="text" id="costo" name="costo">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="tipo" class="col-form-label"> Tipo de Campeonato</label>
                                <select class="form-control" id="tipo" name="tipo">
                                    <option value="">--Seleccione--</option>
                                    <option value="1">Con Fase de Grupos y Eliminatorias</option>
                                    <option value="2">Sólo Eliminatorias (Relámpago)</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="imagen" class="col-form-label"><i class="fa fa-image"></i> Imagen de Referencia</label>
                                <input class="form-control" type="file" name="imagen" id="imagen" accept="image/*">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="submit" name="submit" class="btn btn-success submitBtn" value="Crear Torneo"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>torneo.js"></script>
<script>
    $("#fupFormTorneo").on('submit', function(e){
        e.preventDefault();
        var valor = "correcto";
        var nombre = $('#nombre').val();
        var descripcion = $('#descripcion').val();
        var organizador = $('#organizador').val();
        var tipo = $('#tipo').val();
        var costo = $('#costo').val();
        var fecha = $('#fecha').val();
        var hora = $('#hora').val();
        var lugar = $('#lugar').val();

        if(nombre == ""){
            $('#nombre').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#nombre').css('border','');
        }if(organizador == ""){
            $('#organizador').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#organizador').css('border','');
        }
        if(descripcion == ""){
            $('#descripcion').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#descripcion').css('border','');
        }
        if(tipo == ""){
            $('#tipo').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#tipo').css('border','');
        }
        if(costo == ""){
            $('#costo').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#costo').css('border','');
        }if(fecha == ""){
            $('#fecha').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#fecha').css('border','');
        }if(hora == ""){
            $('#hora').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#hora').css('border','');
        }if(lugar == ""){
            $('#lugar').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#lugar').css('border','');
        }
        if (valor == "correcto"){
            $.ajax({
                type:"POST",
                url: urlweb + "api/Torneo/registrar_torneo",
                dataType: 'json',
                data: new FormData(this),
                contentType: false,
                cache: false,
                processData:false,
                beforeSend: function(){
                    $('.submitBtn').attr("disabled","disabled");
                    $('#fupFormTorneo').css("opacity",".5");
                },
                success:function (r){
                    switch (r.results[0].valor) {
                        case 1:
                            alertify.success("¡Guardado!");
                            location.href = urlweb +  'Torneo/ver_torneo/'+r.results[0].id_torneo;
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
                    $('#fupFormTorneo').css("opacity","");
                    $(".submitBtn").removeAttr("disabled");
                }
            });
        }

    });
</script>
</body>
</html>
