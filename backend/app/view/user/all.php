<?php
/**
 * Created by PhpStorm.
 * User: CesarJose39
 * Date: 16/11/2018
 * Time: 8:37
 */
?>
<!-- Main row -->
<div class="row">
    <div class="col-xs-10">
        <center><h2>Gestion de Usuario</h2></center>
    </div>
    <div class="col-xs-2">
        <center><a class="btn btn-block btn-success btn-sm" href="<?php echo _SERVER_;?>User/add" >Agregar Nuevo</a></center>
    </div>
</div>
<br>
<!-- /.row (main row) -->
<div class="row">
    <div class="col-lg-12">
        <table id="example2" class="table table-bordered table-hover">
            <thead class="text-capitalize">
            <tr>
                <th>ID</th>
                <th>Usuario</th>
                <th>Rol</th>
                <th>Correo</th>
                <th>Estado</th>
                <th>Fecha Creación</th>
                <th>Última Inicio Sesión</th>
                <th>Última Modificación</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <?php
            $a = 1;
            foreach ($user as $m){
                ?>
                <tr>
                    <td><?php echo $a;?></td>
                    <td><?php echo $m->user_nickname;?></td>
                    <td><?php echo $m->role_name;?></td>
                    <td><?php echo $m->user_email;?></td>
                    <td><?php echo ($m->user_status == 1) ? 'HABILITADO' : 'INABILITADO';?></td>
                    <td><?php echo $m->user_created_at;?></td>
                    <td><?php echo $m->user_last_login;?></td>
                    <td><?php echo $m->user_modified_at;?></td>
                    <td><a type="button" class="btn btn-xs btn-warning btne" href="<?php echo _SERVER_ . 'User/edit/' . $m->id_user;?>" >Editar</a><a type="button" class="btn btn-xs btn-info btne" href="<?php echo _SERVER_ . 'User/changep/' . $m->id_user;?>" >Cambiar Contraseña</a><a type="button" class="btn btn-xs btn-danger" onclick="preguntarSiNoU(<?php echo $m->id_user;?>)">Eliminar</a></td>
                </tr>
                <?php
                $a++;
            }
            ?>
            </tbody>
        </table>
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