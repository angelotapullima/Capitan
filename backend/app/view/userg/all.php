<?php
/**
 * Created by PhpStorm
 * User: Franz
 * Date: 18/04/2019
 * Time: 18:35
 */
?>
<!-- Main row -->
<div class="row">
    <div class="col-xs-10">
        <center><h2>Lista de Usuarios Registrados</h2></center>
    </div>
    <div class="col-xs-2">
        <center><a class="btn btn-block btn-success btn-sm" href="<?php echo _SERVER_;?>Userg/addu" >Agregar Nuevo</a></center>
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
                <th>Estado</th>
                <th>Correo</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>DNI</th>
                <th>Fecha Nacimiento</th>
                <th>N° Celular</th>
                <th>Sexo</th>
                <th>Dirección Casa</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <?php
            $a = 1;
            foreach ($person as $m){
                $status = "<a class=\"btn btn-xs btn-outline-danger\">DESHABILITADO</a>";
                $new_status = 1;
                if($m->user_status == 1){
                    $status = "<a class=\"btn btn-xs btn-outline-success\">HABILITADO</a>";
                    $new_status = 0;
                }
                ?>
                <tr>
                    <td><?php echo $a;?></td>
                    <td><?php echo $m->user_nickname;?></td>
                    <td><?php echo $m->role_name;?></td>
                    <td><?php echo $status;?></td>
                    <td><?php echo $m->user_email;?></td>
                    <td><?php echo $m->person_name;?></td>
                    <td><?php echo $m->person_surname;?></td>
                    <td><?php echo $m->person_dni;?></td>
                    <td><?php echo $m->person_birth;?></td>
                    <td><?php echo $m->person_number_phone;?></td>
                    <td><?php echo $m->person_genre;?></td>
                    <td><?php echo $m->person_address;?></td>
                    <td><a type="button" class="btn btn-xs btn-primary btne" href="<?php echo _SERVER_ . 'Userg/editpu/' . $m->id_person;?>" >Editar Persona</a><a type="button" class="btn btn-xs btn-info btne" href="<?php echo _SERVER_ . 'Userg/edituu/' . $m->id_user;?>" >Editar Usuario</a><a type="button" class="btn btn-xs btn-secondary btne" onclick="preguntarSiNoRC(<?php echo $m->id_user;?>)">Resetear Contraseña</a><a type="button" class="btn btn-xs btn-danger btne" onclick="preguntarSiNoI(<?php echo $m->id_user;?>,<?php echo $new_status;?>)">Cambiar Estado</a></td>
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
<script src="<?php echo _SERVER_ . _JS_;?>userg.js"></script>
<script>
    $(document).ready( function () {
        $('#example2').DataTable();
    } );
</script>
</body>
</html>