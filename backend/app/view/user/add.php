<?php
/**
 * Created by PhpStorm.
 * User: CesarJose39
 * Date: 16/11/2018
 * Time: 9:25
 */
?>
<div class="row">
    <!-- left column -->
    <div class="col-md-6">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Agregar Nuevo Usuario</h4>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <div>
                <div class="box-body">
                    <div class="form-group">
                        <label class="col-form-label">Nombre Usuario</label>
                        <input type="text" class="form-control" id="user_nickname" placeholder="Ingresar Nombre Usuario...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Contraseña</label>
                        <input type="password" class="form-control" id="user_password1" placeholder="Ingresar Contraseña...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Repetir Contraseña</label>
                        <input type="password" class="form-control" id="user_password2" placeholder="Vuelva a Ingresar Contraseña...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Correo Usuario</label>
                        <input type="text" class="form-control" id="user_email" placeholder="Ingresar Correo Usuario...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label"> Rol Usuario</label>
                        <select id="id_role" class="form-control">
                            <?php
                            foreach ($roles as $r){
                                ?>
                                <option value="<?php echo $r->id_role;?>"><?php echo $r->role_name;?></option>
                                <?php
                            }
                            ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Persona a Designar Usuario</label>
                        <select class="form-control" id="id_person" >
                            <option value="">Selecciona una persona...</option>
                            <?php
                            foreach ($person as $p){
                                ?>
                                <option value="<?php echo $p->id_person;?>"><?php echo $p->person_name . ' '. $p->person_surname;?></option><?php
                            }
                            ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success" onclick="save()"> Agregar Usuario</button>
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
<script src="<?php echo _SERVER_ . _JS_;?>user.js"></script>
<script>
    $(document).ready( function () {
        $('#example2').DataTable();
    } );
</script>
</body>
</html>