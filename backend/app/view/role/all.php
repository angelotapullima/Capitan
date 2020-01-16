<?php
/**
 * Created by PhpStorm.
 * User: Cesar Jose Ruiz
 * Date: 10/04/2019
 * Time: 22:06
 */
?>

<!-- Main row -->
<div class="row">
    <div class="col-xs-12">
        <h4 class="header-title">Lista de Roles Registrados</h4>
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
                <th>Nombre</th>
                <th>Descripcion</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <?php
            foreach ($role as $m){
                ?>
                <tr>
                    <td><?php echo $m->id_role?></td>
                    <td><?php echo $m->role_name?></td>
                    <td><?php echo $m->role_description?></td>
                    <td><a type="button" class="btn btn-xs btn-warning btne" title="Editar" href="<?php echo _SERVER_ . 'Role/editar_rol/' . $m->id_role;?>" ><i class="fa fa-pencil"></i></a><a type="button" class="btn btn-xs btn-danger btne" title="Eliminar" onclick="preguntarSiNo(<?php echo $m->id_role;?>)"><i class="fa fa-times"></i></a><a type="button" class="btn btn-xs btn-success btne" title="Ver Accesos" href="<?php echo _SERVER_ . 'Role/options/' . $m->id_role;?>" ><i class="fa fa-eye"></i></a></td>
                </tr>
                <?php
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
<script src="<?php echo _SERVER_ . _JS_;?>role.js"></script>
<script>
    $(document).ready( function () {
        $('#example2').DataTable();
    } );
</script>
</body>
</html>

