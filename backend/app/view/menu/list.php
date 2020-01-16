<?php
/**
 * Created by PhpStorm.
 * User: Cesar Jose Ruiz
 * Date: 08/02/2019
 * Time: 18:47
 */
?>
<div class="row">
    <div class="col-xs-10">
        <center><h2>Lista de Menús Registrados</h2></center>
    </div>
    <div class="col-xs-2">
        <center><a class="btn btn-block btn-success btn-sm" href="<?php echo _SERVER_;?>Menu/add" >Agregar Nuevo</a></center>
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
                <th>Nombre Menu</th>
                <th>Código Icono</th>
                <th>Imagen Icono</th>
                <th>Controlador</th>
                <th>Orden de Aparación</th>
                <th>Estado</th>
                <th>Visibilidad</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <?php
            foreach ($menu as $m){
                $show = "<a class=\"btn btn-xs btn-outline-danger\">NO</a>";
                $status = "<a class=\"btn btn-xs btn-outline-danger\">DESHABILITADO</a>";
                if($m->menu_show == 1){
                    $show = "<a class=\"btn btn-xs btn-outline-success\">SI</a>";
                }
                if($m->menu_status == 1){
                    $status = "<a class=\"btn btn-xs btn-outline-success\">HABILITADO</a>";
                }
                ?>
                <tr>
                    <td><?php echo $m->id_menu?></td>
                    <td><?php echo $m->menu_name?></td>
                    <td><?php echo $m->menu_icon?></td>
                    <td><i class="<?php echo $m->menu_icon?>"></i></td>
                    <td><?php echo $m->menu_controller?></td>
                    <td><?php echo $m->menu_order?></td>
                    <td><?php echo $status;?></td>
                    <td><?php echo $show;?></td>
                    <td><a type="button" class="btn btn-xs btn-warning btne" href="<?php echo _SERVER_ . 'Menu/edit/' . $m->id_menu;?>" >Editar</a><a type="button" class="btn btn-xs btn-primary btne" href="<?php echo _SERVER_ . 'Menu/role/' . $m->id_menu;?>" >Gestionar Acceso de Roles</a><a type="button" class="btn btn-xs btn-info btne" href="<?php echo _SERVER_ . 'Menu/functions/' . $m->id_menu;?>" target="_blank">Ver Opciones</a></td>
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
<script src="<?php echo _SERVER_ . _JS_;?>menu.js"></script>
<script>
    $(document).ready( function () {
        $('#example2').DataTable();
    } );
</script>
</body>
</html>


