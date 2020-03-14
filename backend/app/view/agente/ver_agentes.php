<?php
?>
<div class="row">
    <div class="col-xs-10">
        <center><h2>Lista de Agentes Registradas</h2></center>
    </div>
    <div class="col-xs-2">
        <center><a class="btn btn-block btn-success btn-sm" href="<?php echo _SERVER_;?>Agente/agregar_agente" >Agregar Agente</a></center>
    </div>
</div>
<br>
<!-- /.row (main row) -->
<div class="row">
    <div class="col-lg-2"></div>
    <div class="col-lg-8">
        <table id="example2" class="table table-bordered table-hover">
            <thead class="text-capitalize">
            <tr>
                <th>ID</th>
                <th>Nombre Agente</th>
                <th>Numero Cuenta Bufeo</th>
                <th>Direccion</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <?php
            foreach ($agentes as $m){
                ?>
                <tr>
                    <td><?= $m->id_agente;?></td>
                    <td><?= $m->agente_nombre;?></td>
                    <td><?= $m->cuentae_codigo;?></td>
                    <td><?= $m->agente_direccion;?></td>
                    <td><a type="button" class="btn btn-xs btn-warning btne" href="<?= _SERVER_ . 'Agente/edit/' . $m->id_agente;?>" > <i class="fa fa-pencil"></i></a><a type="button" class="btn btn-xs btn-info btne" href="<?= _SERVER_ . 'Agente/eliminar/' . $m->id_agente;?>"> <i class="fa fa-close"></i></a></td>
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
