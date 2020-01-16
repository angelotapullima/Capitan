<?php
/**
 * Created by PhpStorm.
 * User: Cesar Jose Ruiz
 * Date: 13/02/2019
 * Time: 1:27
 */
?>
<!-- Main row -->
<div class="row">
    <!-- Dark table start -->
    <div class="col-lg-5">
        <div class="card">
            <div class="card-body">
                <h5>Permisos de la Opción: <?php echo $optionname;?></h5>
            </div>
        </div>
    </div>
    <div class="col-lg-3">
        <div class="card">
            <div class="card-body">
                <a type="button" class="btn btn-xs btn-success btne" href="<?php echo _SERVER_ . 'Menu/addp/' . $id_optionm;?>" >Agregar Permiso</a>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="card">
            <div class="card-body">
                <input class="form-control" type="password" id="password"  placeholder="Contraseña Para Cambios AQUÍ">
            </div>
        </div>
    </div>
    <!-- Dark table end -->
</div>
<br>
<!-- /.row (main row) -->
<div class="row">
    <div class="col-lg-12">
        <table id="example2" class="table table-bordered table-hover">
            <thead class="text-capitalize">
            <tr>
                <th>ID</th>
                <th>Nombre Acción</th>
                <th>Estado</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <?php
            foreach ($permits as $m){

                $status = "<a class=\"btn btn-xs btn-outline-danger\">DESHABILITADO</a>";

                if($m->permit_status == 1){
                    $status = "<a class=\"btn btn-xs btn-outline-success\">HABILITADO</a>";
                }
                ?>
                <tr>
                    <td><?php echo $m->id_permit?></td>
                    <td><?php echo $m->permit_action?></td>
                    <td><?php echo $status?></td>
                    <td><a type="button" class="btn btn-xs btn-warning btne" href="<?php echo _SERVER_ . 'Menu/editp/' . $m->id_permit;?>">Editar</a><a type="button" class="btn btn-xs btn-danger btne" onclick="preguntarSiNoDP(<?php echo $m->id_permit;?>)">Eliminar</a></td>
                </tr>
                <?php
            }
            ?>
            </tbody>
        </table>
        <a type="button" class="btn btn-xs btn-info btne" href="<?php echo _SERVER_ . 'Menu/functions/' . $id_menu;?>" >Volver Al Menú Anterior</a>
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
