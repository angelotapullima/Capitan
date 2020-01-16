<form enctype="multipart/form-data" id="fupFormCancha">
    <div class="row">
        <div class="col-md-6">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h4 class="header-title">Agregar Cancha a empresa: <b><?= $empresa->empresa_nombre; ?></b></h4>
                </div>
                <div class="box-body">
                    <div class="form-group">
                        <label for="nombre" class="col-form-label">Nombre de la cancha</label>
                        <input class="form-control" type="text" name="nombre" id="nombre" placeholder="Ingrese Nombre de la Cancha">
                        <input type="hidden" name="empresa_id" id="id_empresa" value="<?= $empresa->empresa_id; ?>">
                    </div>
                    <div class="form-group">
                        <label for="dimensiones" class="col-form-label">Dimensiones</label>
                        <input class="form-control" type="text" name="dimensiones" id="dimensiones">
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="precioD" class="col-form-label">Precio de Día</label>
                                <input class="form-control" type="text" name="precioD" id="precioD" placeholder="S/. 0.00">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="precioN" class="col-form-label">Precio de Noche</label>
                                <input class="form-control" type="text" name="precioN" id="precioN" placeholder="S/. 0.00">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="imagen" class="col-form-label">Imagen de Referencia</label>
                                <input class="form-control" type="file" name="imagen" id="imagen" accept="image/*">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="submit" name="submit" class="btn btn-success submitBtn" value="Guardar Cancha"/>
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
<script src="<?php echo _SERVER_ . _JS_;?>empresa.js"></script>
</body>
</html>
