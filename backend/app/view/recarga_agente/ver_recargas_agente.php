<?php
?>
<div class="row">
    <div class="col-xs-10">
        <center><h2>Lista de Recargas realizadas</h2></center>
    </div>
    <div class="col-xs-2">
        <center><a class="btn btn-block btn-success btn-sm" href="<?php echo _SERVER_;?>RecargaAgente/recargar_agente" >Agregar Nuevo</a></center>
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
                <th>Emisor</th>
                <th>Agente</th>
                <th>Monto recargado</th>
                <th>Saldo Actual</th>
                <th>Nro Operacion</th>
                <th>Concepto</th>
                <th>Fecha</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <?php
            foreach ($recargas_agente as $m){
                $cuentae=$this->cuentaempresa->listar_cuentae_por_id($m->id_empresa_emisor);
                $cuentar=$this->cuentaempresa->listar_cuentae_por_id($m->id_empresa_receptor);
                ?>
                <tr>
                    <td><?= $m->id_transferencia_e_e;?></td>
                    <td><?= $cuentae->empresa_nombre;?></td>
                    <td><?= $cuentar->empresa_nombre;?></td>
                    <td><?= $cuentar->cuentae_moneda." ".$m->transferencia_e_e_monto;?></td>
                    <td><?= $cuentar->cuentae_moneda." ".$cuentar->cuentae_saldo;?></td>
                    <td><?= $m->transferencia_e_e_nro_operacion;?></td>
                    <td><?= $m->transferencia_e_e_concepto;?></td>
                    <td><?= $m->transferencia_e_e_date;?></td>
                    <td><a type="button" class="btn btn-xs btn-warning btne" href="<?= _SERVER_ . 'RecargaAgente/edit/' . $m->id_transferencia_e_e;?>" > <i class="fa fa-pencil"></i></a><a type="button" class="btn btn-xs btn-info btne" href="<?= _SERVER_ . 'RecargaAgente/eliminar/' . $m->id_transferencia_e_e;?>"> <i class="fa fa-close"></i></a></td>
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
