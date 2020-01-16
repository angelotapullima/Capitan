<?php
/**
 * Created by PhpStorm.
 * User: Cesar Jose Ruiz
 * Date: 11/04/2019
 * Time: 12:31
 */
?>
<div class="row">
    <!-- left column -->
    <div class="col-md-6">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Editar Contraseña Usuario</h4>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <div>
                <div class="box-body">
                    <div class="form-group">
                        <label class="col-form-label">Nueva Contraseña</label>
                        <input type="password" class="form-control" id="user_password1" placeholder="Ingresar Contraseña...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Repetir Contraseña</label>
                        <input type="password" class="form-control" id="user_password2" placeholder="Vuelva a Ingresar Contraseña...">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success" onclick="savec()"> Editar Contraseña</button>
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
<script src="<?php echo _SERVER_ . _JS_;?>edit.js"></script>
<script>
    $(document).ready( function () {
        $('#example2').DataTable();
    } );
</script>
</body>
</html>