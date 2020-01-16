<?php
/**
 * Created by PhpStorm.
 * User: CesarJose39
 * Date: 13/11/2018
 * Time: 21:50
 */
?>
<div class="row">
    <div class="col-xs-12">
        <h4 class="header-title">Lista de Personas Registradas</h4>
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
                ?>
                <tr>
                    <td><?php echo $a;?></td>
                    <td><?php echo $m->person_name;?></td>
                    <td><?php echo $m->person_surname;?></td>
                    <td><?php echo $m->person_dni;?></td>
                    <td><?php echo $m->person_birth;?></td>
                    <td><?php echo $m->person_number_phone;?></td>
                    <td><?php echo $m->person_genre;?></td>
                    <td><?php echo $m->person_address;?></td>
                    <td><a type="button" class="btn btn-xs btn-warning btne" href="<?php echo _SERVER_ . 'Person/edit/' . $m->id_person;?>" >Editar</a><a type="button" class="btn btn-xs btn-danger" onclick="preguntarSiNo(<?php echo $m->id_person;?>)">Eliminar</a></td>
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
<script src="<?php echo _SERVER_ . _JS_;?>person.js"></script>
<script>
    $(document).ready( function () {
        $('#example2').DataTable();
    } );
</script>
</body>
</html>
