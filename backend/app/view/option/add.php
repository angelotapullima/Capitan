<?php
/**
 * Created by PhpStorm.
 * User: Cesar Jose Ruiz
 * Date: 13/02/2019
 * Time: 1:27
 */
?>
<div class="row">
    <!-- left column -->
    <div class="col-md-6">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Agregar Opción a Menú <?php echo $menuname;?></h4>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <div>
                <div class="box-body">
                    <div class="form-group">
                        <label class="col-form-label">Nombre Opción</label>
                        <input class="form-control" type="text" id="optionm_name"  placeholder="Ingrese Nombre de la Opción...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Nombre Función</label>
                        <input class="form-control" type="text" id="optionm_function"  placeholder="Ingrese Nombre de Función...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">¿Mostrar en Opciones?</label>
                        <select class="form-control" id="optionm_show">
                            <option value="1">SI</option>
                            <option value="0">NO</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Estado</label>
                        <select class="form-control" id="optionm_status">
                            <option value="1">HABILITADO</option>
                            <option value="0">DESHABILITADO</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Orden</label>
                        <input class="form-control" type="text" id="optionm_order"  placeholder="Ingrese Número de Orden">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Contraseña De Usuario Actual</label>
                        <input class="form-control" type="password" id="password"  placeholder="Ingrese su Contraseña...">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success" onclick="savef(<?php echo $id_menu;?>)"> Guardar Opción</button>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-info" onclick="volverf(<?php echo $id_menu;?>)"> Volver a Listar Funciones</button>
                    </div>
                </div>
                <!-- /.box-body -->

            </div>
        </div>
        <!-- /.box -->



    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>menu.js"></script>
<script>
    $(document).ready( function () {
        $('#example2').DataTable();
    } );
</script>
</body>
</html>
