    <div class="row">
        <div class="col-md-6 col-md-offset-1">
            <div class="panel panel-container border-right" style="padding-left: 10px">
                <form enctype="multipart/form-data" id="fupFormForo">
                <h3>Agregar Publicación</h3>
                <div class="row">
                    <div class="col-md-8">
                        <div class="row">
                            <div class="col-md-2">
                                <img alt="user" class="img-thumbnail img-circle img-responsive" src="<?= _SERVER_ . $this->crypt->decrypt($_SESSION['user_image'], _FULL_KEY_); ?>">
                            </div>
                            <div class="col-md-10">
                                <p><?= $this->crypt->decrypt($_SESSION['user_nickname'], _FULL_KEY_); ?></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-11">
                                <input type="text" class="form-control" autocomplete="off" placeholder="Título" id="titulo" name="titulo" style="border: 1px dashed green;font-weight: bold">
                                <input type="hidden" name="usuario_id" value="<?= $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_); ?>">
                                <input type="hidden" name="concepto" value="publicacion">
                                <input type="hidden" name="id_torneo" value="0">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-11">
                                <br><textarea name="descripcion" id="descripcion" class="form-control" placeholder="¿Qué deseas publicar?" style="border: 1px dashed green" rows="4"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <img id="imagenPrevisualizacion" src="<?= _SERVER_?>media/uploadimage.png" height="100" width="120"><br>
                        <label id="lbl_imagen" for="imagen"><i class="fa fa-image"></i> Subir Foto</label>
                        <input type="file" id="imagen" name="imagen" accept="image/*">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-offset-4 col-md-2">
                        <br>
                        <button type="submit" name="submit" class="btn btn-success submitBtn" style="background: green;"><i class='fa fa-check'></i> Publicar</button>
                    </div>
                </div>
                </form>
            </div>
            <?php
            if(count($foros)>0){
                foreach ($foros as $e){
                    ?>
            <div class="panel panel-container border-right" style="padding-left: 10px">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="row">
                            <div class="col-xs-1 col-md-1">
                                <img src="<?= _SERVER_ . $e->user_image; ?>" class="img-circle img-responsive" style="max-width: 50px">
                            </div>
                            <div class="col-xs-8 col-md-8">
                                <a href="#"><?= $e->user_nickname; ?></a>
                                <p style="font-size: 9pt;">Hace <?= $this->validate->time_between_dates($e->publicaciones_fecha,"hoy"); ?></p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12" style="margin-top: 0;">
                        <div class="panel panel-widget border-right">
                            <div class="row">
                                <div class="col-md-12">
                                    <h3 style="color: green;margin: 0"><?= $e->publicaciones_titulo; ?></h3>
                                    <p><?= $e->publicaciones_descripcion; ?></p>
                                    <div class="row no-padding"><img alt="cancha" class="img-thumbnail img-responsive" src="<?= _SERVER_.$e->publicaciones_foto; ?>"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-md-offset-2">
                                    <?php
                                    if(isset($e->dio_like->publicaciones_id)){
                                        ?>
                                        <button id="btn_like_<?= $e->publicaciones_id; ?>" style="background: #30a5ff;color:#fff" onclick="quitar_like('<?= $e->publicaciones_id; ?>')" class="form-control"><i class="fa fa-thumbs-up"></i> <span id="span_like_<?=$e->publicaciones_id; ?>"><?= $e->cant_likes->conteo; ?></span> Me gusta</button>
                                    <?php
                                    }else{
                                        ?>
                                        <button id="btn_like_<?= $e->publicaciones_id; ?>" onclick="dar_like('<?= $e->publicaciones_id; ?>')" class="form-control"><i class="fa fa-thumbs-up"></i> <span id="span_like_<?=$e->publicaciones_id; ?>"><?= $e->cant_likes->conteo; ?></span> Me gusta</button>
                                        <?php
                                    }
                                    ?>
                                </div>
                                <div class="col-md-4">
                                    <a onclick="btn_comentar('<?= $e->publicaciones_id; ?>')" href="#section_comment_<?= $e->publicaciones_id; ?>" class="form-control"><i class="fa fa-comment-o"></i> <span id="span_comment_<?=$e->publicaciones_id; ?>"><?= count($e->comentarios); ?></span> Comentar</a>
                                </div>
                            </div><hr>
                            <div id="comments_<?= $e->publicaciones_id; ?>">
                            <?php
                            if(count($e->comentarios)>0){
                                foreach ($e->comentarios as $c){
                                    ?>
                                <div class="row">
                                    <div class="col-md-2">
                                        <img alt="<?= $c->user_image; ?>" style="max-width: 50px" class="img-thumbnail img-circle img-responsive" src="<?= _SERVER_ . $c->user_image; ?>">
                                    </div>
                                    <div class="col-md-9">
                                        <p style="text-align: left; color: green"><?= $c->user_nickname; ?> </p>
                                        <p style="text-align: left; word-break: break-all;background: lightgray; padding:10pt;border-radius: 10px"><?= $c->comentario_coment; ?></p>
                                        <p style="text-align: right; font-size: 8pt;"><?= $this->validate->time_between_dates($c->comentario_fecha,"hoy"); ?></p>
                                    </div>
                                </div>
                            <?php
                                }
                            }
                            ?>
                            </div>
                            <div class="row" id="section_comment_<?= $e->publicaciones_id; ?>">
                                <div class="col-md-2">
                                    <img alt="user" class="img-thumbnail img-circle img-responsive" src="<?= _SERVER_ . $this->crypt->decrypt($_SESSION['user_image'], _FULL_KEY_); ?>">
                                </div>
                                <div class="col-md-9">
                                    <div id="contenedor">
                                        <div class="comentando" onkeydown="verificar('<?= $e->publicaciones_id; ?>')" id="comment_<?= $e->publicaciones_id; ?>" onblur="clean_text('<?= $e->publicaciones_id; ?>')" onclick="clean_text('<?= $e->publicaciones_id; ?>')" style="border-radius: 10px; border: 1px solid; text-align: left; width: 100%; padding: 5px; margin-top: 20px;margin-left: 0" contenteditable="true">Escribe un comentario</div>
                                    </div>
                                    <small style="text-align: left">Pulse "Intro" para publicar</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <?php
                }
            }else{
                echo "<div class='col-xs-6 col-md-3 col-lg-3'><p>Ninguna publicación encontrada</p></div>";
            }
            ?>
            <input type="hidden" id="id_comment">
        </div>
        <div class="col-md-3">
            <div class="panel panel-container border-right" style="padding-left: 10px">
                <h3 style="text-align: center">Mis equipos</h3>

                <?php
                foreach ($mis_equipos as $m){
                    ?>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-9">
                        <a href="#" style="text-decoration: none">
                        <img src="<?= _SERVER_ . $m->equipo_foto; ?>" class="img-thumbnail img-circle img-responsive">
                        <h3 style="color: green; text-align: center;"><?= $m->equipo_nombre; ?></h3>
                        </a>
                    </div>
                </div>
                <?php
                }
                ?>

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
</script>
</body>
</html>
