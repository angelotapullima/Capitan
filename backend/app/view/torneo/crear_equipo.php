<form enctype="multipart/form-data" method="post" id="fupFormEquipo">
    <div class="row">
        <div class="col-md-6">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h4 class="header-title">Crear Equipo</h4>
                </div>
                <div class="box-body">
                    <div class="form-group">
                        <label for="capitan" class="col-form-label">Capitán</label>
                        <input class="form-control" type="text" readonly value="<?= $this->crypt->decrypt($_SESSION['person_name'],_FULL_KEY_) . " " . $this->crypt->decrypt($_SESSION['person_surname'],_FULL_KEY_); ?>" name="capitan" id="capitan">
                    </div>
                    <div class="form-group">
                        <label for="nombre" class="col-form-label">Nombre del Equipo</label>
                        <input class="form-control" type="text" name="nombre" id="nombre">
                        <input type="hidden" name="usuario_id" id="usuario_id" value="<?= $this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_); ?>">
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <img id="imagenPrevisualizacion" src="<?= _SERVER_?>media/uploadimage.png" height="100" width="120"><br>
                                <label id="lbl_imagen" for="imagen" class="col-form-label"><i class="fa fa-image"></i> Imagen de Referencia</label>
                                <input class="form-control" type="file" name="imagen" id="imagen" accept="image/*">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="submit" name="submit" class="btn btn-success submitBtn" value="Crear Equipo"/>
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
    $("#fupFormEquipo").on('submit', function(e){
        e.preventDefault();
        var valor = "correcto";
        var nombre = $('#nombre').val();
        if(nombre == ""){
            $('#nombre').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#nombre').css('border','');
        }
        if (valor == "correcto"){
            $.ajax({
                type:"POST",
                url: urlweb + "api/Torneo/registrar_equipo",
                dataType: 'json',
                data: new FormData(this),
                contentType: false,
                cache: false,
                processData:false,
                beforeSend: function(){
                    $('.submitBtn').attr("disabled","disabled");
                    $('#fupFormEquipo').css("opacity",".5");
                },
                success:function (r){
                    switch (r.results[0].valor) {
                        case 1:
                            alertify.success("¡Guardado!");
                            location.href = urlweb +  'Torneo/ver_equipo/'+r.results[0].id_equipo;
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
                    $('#fupFormEquipo').css("opacity","");
                    $(".submitBtn").removeAttr("disabled");
                }
            });
        }

    });
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
</script>
</body>
</html>
