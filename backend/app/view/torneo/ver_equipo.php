<div class="panel panel-container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel border-right">
                <div class="row">
                    <div class="col-md-5">
                        <img alt="Torneo" class="img-thumbnail img-responsive" src="<?= _SERVER_ . $equipo->equipo_foto; ?>">
                    </div>
                    <div class="col-md-7">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="large" style="padding-left: 20px;margin-bottom: 20px; text-align: center"><h3><b><?= $equipo->equipo_nombre; ?> </b></h3></div>
                            </div>
                            <div class="col-md-12">
                                <h4><i style="color: green;" class="fa fa-user"></i> <?= $equipo->person_name." ".$equipo->person_surname; ?></h4>
                                <h4><i style="color: green;" class="fa fa-star"></i> <?= $equipo->equipo_valoracion; ?></h4>
                                <h4><i style="color: green;" class="fa fa-clock-o"></i> <?= $equipo->equipo_join; ?> </h4>
                                <h4><span style="color:green;"># Jugadores</span> <?= count($jugadores); ?></h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel panel-container">
    <div class="row">
        <div class="col-md-12">
            <ul class="nav nav-tabs nav-justified">
                <li class="active"><a data-toggle="tab" href="#jugadores"><i class="fa fa-pencil"></i> Jugadores en el equipo</a></li>
                <li><a data-toggle="tab" href="#chanchas"><i class="fa fa-soccer-ball-o"></i> Chanchas disponibles</a></li>
                <li><a data-toggle="tab" href="#torneos"><i class="fa fa-users"></i> Torneos en los que participa</a></li>
                <li><a data-toggle="tab" href="#retos"><i class="fa fa-trophy"></i> Retos disputados</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="tab-content">
    <div id="jugadores" class="tab-pane fade in active">
        <div class="row">
            <div class="col-md-8"></div>
            <div class="col-md-2">
                <button class="btn btn-success"><i class="fa fa-plus"></i> Agregar Jugador</button>
            </div>
        </div>
        <div class="row">
            <?php
            foreach ($jugadores as $j){
                ?>
                <div class="col-md-2">
                    <img src="<?= _SERVER_ . $j->user_image; ?>" class="img-responsive img-circle img-thumbnail">
                    <p style="text-align: center"><?= $j->user_nickname; ?></p>
                </div>
            <?php
            }
            ?>
        </div>
    </div>
    <div id="chanchas" class="tab-pane fade">
        <div class="row">
            <div class="col-md-8">
                <h3>Chanchas disponibles</h3>
            </div>
            <div class="col-md-2">
                <button class="btn btn-success" data-toggle="modal" data-target="#agregar_chancha"><i class="fa fa-plus"></i> Agregar Chancha</button>
            </div>
        </div>
        <div class="row">
            <?php
            foreach ($chanchas as $j){
                $detalle=$this->empresa->obtener_detalle_chancha($j->colaboracion_id);
                ?>
                <div class="col-md-3" style="border-radius: 10px;border: 1px solid green;">
                    <h4 style="text-align: center;color: green;"><?= $j->colaboracion_nombre; ?></h4>
                    <p><b>Monto deseado:</b> <img src="<?= _SERVER_ ?>media/bufi.png" width="15" alt="bufi"> <?= $j->colaboracion_monto; ?></p>
                    <p><b>Creado el: </b><?= $this->validate->get_date_nominal($j->colaboracion_date,"DateTime","DateTime","es");?></p>
                    <table class="table table-bordered table-responsive">
                        <thead><th>Fecha</th><th>Jugador</th><th>Monto colaborado</th></thead>
                        <tbody>
                        <?php
                        $total = 0;
                        foreach ($detalle as $d){
                            ?>
                            <tr>
                                <td><?= $d->detalle_colaboracion_date; ?></td>
                                <td><?= $d->user_nickname; ?></td>
                                <td><?= $d->detalle_colaboracion_monto; ?></td>
                            </tr>
                        <?php
                            $total=$total+$d->detalle_colaboracion_monto;
                        }
                        ?>
                        <tr><td colspan="2">Total</td><td><?= $total; ?></td></tr>
                        <tr><td colspan="3"><button data-toggle="modal" data-target="#colaborar" onclick="id_colab(<?= $j->colaboracion_id; ?>)">Colaborar</button></td></tr>
                        </tbody>
                    </table>
                </div>
                <?php
            }
            ?>
        </div>
    </div>
    <div id="torneos" class="tab-pane fade">

    </div>
    <div id="retos" class="tab-pane fade">
        <div class="row">
            <div class="col-md-10">
                <p>Listado de las instancias y sus respectivos partidos</p>
            </div>
            <div class="col-md-2">
                <button class="btn btn-primary"><i class="fa fa-plus"></i> Agregar Instancia</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="agregar_chancha" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel1" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background: green;">
                <h3 class="modal-title" style="color:#fff; text-align: center" id="exampleModalLabel">Crear Chancha</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-7">
                        <label for="co_n">Nombre referencial</label>
                        <input type="text" id="co_n" class="form-control">
                    </div>
                    <div class="col-md-5">
                        <label for="co_m">Monto deseado de alcanzar</label>
                        <input type="text" id="co_m" class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-success" onclick="add_chancha()"><i></i> Guardar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="colaborar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel1" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background: green;">
                <h3 class="modal-title" style="color:#fff; text-align: center" id="exampleModalLabel">Colaborar para Chancha</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <label>Saldo actual: <?= $saldo_Actual->cuenta_saldo; ?></label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <label for="ch_m">Monto a colaborar</label>
                        <input type="text" id="ch_m" class="form-control">
                        <input type="hidden" id="ch_i">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-success" onclick="add_colab()"><i></i> Guardar</button>
            </div>
        </div>
    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script>
    const $seleccionArchivos = document.querySelector("#imagen"), $imagenPrevisualizacion = document.querySelector("#imagenPrevisualizacion");
    $('input[type=file]').change(function(){
        var filename = $(this).val().split('\\').pop();
        if(filename!=""){
            $("#lbl_imagen").html(filename);
        }else{
            $("#lbl_imagen").html("<i class=\"fa fa-image\"></i> Subir Foto");
        }
        const archivos = $seleccionArchivos.files;
        if (!archivos || !archivos.length) {
            $imagenPrevisualizacion.src = "<?= _SERVER_?>media/uploadimage.png";
            return;
        }
        $imagenPrevisualizacion.src = URL.createObjectURL(archivos[0]);
    });
    function clean_text(id) {
        var text = $("#comment_"+id).html().trim();
        if(text=="Escribe un comentario"){
            $("#comment_"+id).html('');
        }else if(text==""){
            $("#comment_"+id).html('Escribe un comentario');
        }
    }
    function dar_like(id) {
        cadena = "usuario_id= "+<?= $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_); ?> +"&publicacion_id="+id;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Foro/dar_like",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].resultado) {
                    case 1:
                        $("#btn_like_"+id).css("background","#30a5ff");
                        $("#btn_like_"+id).css("color","#fff");
                        $("#btn_like_"+id).css("border-color","#30a5ff");
                        $("#btn_like_"+id).attr("onclick","quitar_like("+id+")");
                        var span_like = $("#span_like_"+id).html() * 1 + 1;
                        $("#span_like_"+id).html(span_like);
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    function quitar_like(id) {
        cadena = "usuario_id= "+<?= $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_); ?> +"&publicacion_id="+id;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Foro/quitar_like",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].resultado) {
                    case 1:
                        $("#btn_like_"+id).css("background","#fff");
                        $("#btn_like_"+id).css("color","#000");
                        $("#btn_like_"+id).css("border-color","#fff");
                        $("#btn_like_"+id).attr("onclick","dar_like("+id+")");
                        var span_like = $("#span_like_"+id).html() * 1 - 1;
                        $("#span_like_"+id).html(span_like);
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    function comentar() {
        var id = $("#id_comment").val();
        var comentario = $("#comment_"+id).html();
        var cadena = "usuario_id= "+<?= $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_); ?> +"&publicacion_id="+id+"&comentario="+comentario;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Foro/registrar_comentario",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].resultado) {
                    case 1:
                        $("#comments_"+id).html(r.results[0].lista);
                        $("#comment_"+id).html('');

                        var span_comment = $("#span_comment_"+id).html() * 1 + 1;
                        $("#span_comment_"+id).html(span_comment);
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    $('a[href*="#"]').on('click', function(e) {
        e.preventDefault();
        var el = $($(this).attr('href'));
        var elOffset = el.offset().top;
        var elHeight = el.height();
        var windowHeight = $(window).height();
        var offset;
        if (elHeight < windowHeight) {
            offset = elOffset - ((windowHeight / 2) - (elHeight / 2));
        }
        else {
            offset = elOffset;
        }
        $('html, body').animate(
            {
                scrollTop: offset,
            },
            500,
            'linear'
        );
    });
    function btn_comentar(id) {
        $("#comment_"+id).focus();
        clean_text(id);
    }
    function verificar(id){
        $("#id_comment").val(id);
    }
    $('.comentando').keypress(function(e){
        if(e.which === 13){
            e.preventDefault();
            comentar();
        }
    });
    $("#fupFormForo").on('submit', function(e){
        e.preventDefault();
        var valor = "correcto";
        var titulo = $('#titulo').val();
        var descripcion = $('#descripcion').val();
        if(titulo == ""){
            alertify.error('Ingrese un título');
            $('#titulo').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#titulo').css('border','');
        }if(descripcion == ""){
            $('#descripcion').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#descripcion').css('border','');
        }
        if (valor == "correcto"){
            $.ajax({
                type:"POST",
                url: urlweb + "api/Foro/registrar",
                dataType: 'json',
                data: new FormData(this),
                contentType: false,
                cache: false,
                processData:false,
                beforeSend: function(){
                    $('.submitBtn').attr("disabled","disabled");
                    $('#fupFormForo').css("opacity",".5");
                },
                success:function (r) {
                    switch (r) {
                        case 1:
                            alertify.success("¡Guardado!");
                            location.href = urlweb +  'Foro/ver_publicaciones';
                            break;
                        case 2:
                            alertify.error("Ha ocurrido un error");
                            break;
                        case 6:
                            alertify.error("Fallo en la integridad de los datos enviados");
                            break;
                        default:
                            alertify.error("ERROR DESCONOCIDO");
                    }
                    $('#fupFormForo').css("opacity","");
                    $(".submitBtn").removeAttr("disabled");
                }
            });
        }
    });
    function id_colab(id) {
        $("#ch_i").val(id);
    }
    function add_colab() {
        var saldo = <?= $saldo_Actual->cuenta_saldo; ?>;
        var id_colab = $("#ch_i").val();
        var monto = $("#ch_m").val();
        if(monto>saldo){
            alertify.error("El monto a colaborar no puede ser superior al saldo actual.");
        }else{
            var cadena = "id_colab="+id_colab+"&monto="+monto;
            $.ajax({
                type:"POST",
                url: urlweb + "api/Torneo/agregar_colaboracion",
                dataType: 'json',
                data: cadena,
                success:function (r) {
                    if(r==1){
                        location.href = urlweb +  'Torneo/ver_equipo/'+<?= $id; ?>;
                    }else{
                        if(r==6){
                        alertify.error("Datos ingresados incorrectos.");
                        }else{
                            alertify.error("Ha ocurrido un error.");
                        }
                    }
                }
            });
        }
    }
    function add_chancha() {
        var nombre = $("#co_n").val();
        var monto = $("#co_m").val();
        var cadena = "nombre="+nombre+"&monto="+monto+"&id_equipo="+<?= $id; ?>;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Torneo/agregar_chancha",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                if(r==1){
                    location.href = urlweb +  'Torneo/ver_equipo/'+<?= $id; ?>;
                }else{
                    if(r==6){
                    alertify.error("Datos ingresados incorrectos.");
                    }else{
                        alertify.error("Ha ocurrido un error.");
                    }
                }
            }
        });
    }
</script>
</body>
</html>