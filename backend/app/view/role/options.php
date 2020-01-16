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
    <!-- Dark table start -->
    <div class="col-lg-6">
        <div class="card">
            <div class="card-body">
                <h4 class="header-title">Menús con Permiso Para el Rol: <strong><?php echo $role->role_name;?></strong></h4>
            </div>
        </div>
    </div>
    <div class="col-lg-6">
        <div class="card">
            <div class="card-body">
                <input class="form-control" type="password" id="password"  placeholder="Ingrese su Contraseña AQUÍ para Permitir Cambios...">
            </div>
        </div>
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
                <th>Con Permiso</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <?php
            foreach ($menu as $m){
                $acceso = "<a class=\"btn btn-xs btn-outline-danger\">NO</a>";
                $opcion = "<a type=\"button\" class=\"btn btn-xs btn-success\" onclick=\"agregarRelacion(". $id_role .",". $m->id_menu .")\">Agregar Relacion</a>";
                if($this->role->SearchRelationship($id_role, $m->id_menu)){
                    $acceso = "<a class=\"btn btn-xs btn-outline-success\">SI</a>";
                    $opcion = "<a type=\"button\" class=\"btn btn-xs btn-danger\" onclick=\"eliminarRelacion(". $id_role .",". $m->id_menu .")\">Eliminar Relacion</a>";
                } else {
                    $acceso = "<a class=\"btn btn-xs btn-outline-danger\">NO</a>";
                    $opcion = "<a type=\"button\" class=\"btn btn-xs btn-success\" onclick=\"agregarRelacion(". $id_role .",". $m->id_menu .")\">Agregar Relacion</a>";
                }
                ?>
                <tr>
                    <td><?php echo $m->id_menu?></td>
                    <td><?php echo $m->menu_name?></td>
                    <td><?php echo $acceso?></td>
                    <td><?php echo $opcion?></td>
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


