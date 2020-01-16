<?php
/**
 * Created by PhpStorm.
 * User: Cesar Jose Ruiz
 * Date: 10/04/2019
 * Time: 22:06
 */
?>

<div class="row">
    <!-- left column -->
    <div class="col-md-6">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Editar Rol</h4>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <div>
                <div class="box-body">
                    <div class="form-group">
                        <label class="col-form-label">Nombre Rol</label>
                        <input class="form-control" type="text" id="role_name" value="<?php echo $rol_e->role_name;?>" placeholder="Ingrese Nombre del Rol...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Descripción Rol</label>
                        <input class="form-control" type="text" id="role_description" value="<?php echo $rol_e->role_description;?>" placeholder="Ingrese Descripción del Rol...">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success" onclick="save()"> Editar Rol</button>
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
<script src="<?php echo _SERVER_ . _JS_;?>role.js"></script>

</body>
</html>

