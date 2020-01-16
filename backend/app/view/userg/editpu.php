<?php
/**
 * Created by PhpStorm
 * User: Franz
 * Date: 18/04/2019
 * Time: 19:59
 */
?>
<div class="row">
    <!-- left column -->
    <div class="col-md-6">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Editar Persona</h4>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <div>
                <div class="box-body">
                    <div class="form-group">
                        <label class="col-form-label">Nombres</label>
                        <input class="form-control" type="text" id="person_name" value="<?php echo $person->person_name;?>" placeholder="Ingrese Nombres...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Apellidos</label>
                        <input class="form-control" type="text" id="person_surname" value="<?php echo $person->person_surname;?>" placeholder="Ingrese Apellidos...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">DNI</label>
                        <input class="form-control" type="text" id="person_dni" value="<?php echo $person->person_dni;?>" readonly>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Fecha de Nacimiento</label>
                        <input class="form-control" type="date" id="person_birth" value="<?php echo $person->person_birth;?>" placeholder="Ingrese Apellido...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Número de Teléfono</label>
                        <input class="form-control" type="text" id="person_number_phone" value="<?php echo $person->person_number_phone;?>" onkeypress="return valida(event)" placeholder="Ingrese Teléfono...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Género</label>
                        <select class="form-control" id="person_genre">
                            <option <?php echo ($person->person_genre == 'M') ? 'selected' : '';?> value="M">M</option>
                            <option <?php echo ($person->person_genre == 'F') ? 'selected' : '';?> value="F">F</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Dirección Casa</label>
                        <input class="form-control" type="text" id="person_address" value="<?php echo $person->person_address;?>" placeholder="Ingrese Dirección...">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success" onclick="savep()"> Editar Persona</button>
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
<script src="<?php echo _SERVER_ . _JS_;?>userg.js"></script>
<script>
    $(document).ready( function () {
        $('#example2').DataTable();
    } );
</script>
</body>
</html>
