<?php
?>
<div class="row">
    <div class="col-xs-10">
        <center><h2>Lista de transferencias</h2></center>
    </div>
    <div class="col-xs-2">
        <center><a class="btn btn-block btn-success btn-sm" href="<?php echo _SERVER_;?>Transferencia/transferir">Transferir</a></center>
    </div>
</div>
<br>
<!-- /.row (main row) -->
<div class="row">
    <div class="col-lg-6">
        <h3><b>Transferencias Realizadas</b></h3>
        <table id="example2" class="table table-bordered table-hover">
            <thead class="text-capitalize">
            <tr>
                <th>Fecha</th>
                <th>Nro Operacion</th>
                <th>Cuenta de destino</th>
                <th>Destinatario</th>
                <th>Monto</th>
                <th>Concepto</th>
            </tr>
            </thead>
            <tbody>
            <?php
            foreach ($transferencias as $m){
                $fecha = $this->validate->get_date_nominal($m->transferencia_u_u_date,'DateTime','DateTime','es');
                $cuenta=$this->cuenta->listar_cuenta_por_id($m->id_usuario_receptor);
                ?>
                <tr>
                    <td><?= $fecha;?></td>
                    <td><?= $m->transferencia_u_u_nro_operacion;?></td>
                    <td><?= $cuenta->cuenta_codigo;?></td>
                    <td><?= $cuenta->person_name." ".$cuenta->person_surname;?></td>
                    <td><?= $cuenta->cuenta_moneda." ".$m->transferencia_u_u_monto;?></td>
                    <td><?= $m->transferencia_u_u_concepto;?></td>
                </tr>
                <?php
            }
            ?>
            </tbody>
        </table>
    </div>
    <div class="col-lg-6">
        <h3><b>Transferencias Recibidas</b></h3>
        <table id="example" class="table table-bordered table-hover">
            <thead class="text-capitalize">
            <tr>
                <th>Fecha</th>
                <th>Nro Operacion</th>
                <th>Cuenta que emitió</th>
                <th>Emisor</th>
                <th>Monto</th>
                <th>Concepto</th>
            </tr>
            </thead>
            <tbody>
            <?php
            foreach ($transferencias_recibidas as $m){
                $fecha = $this->validate->get_date_nominal($m->transferencia_u_u_date,'DateTime','DateTime','es');
                $cuenta=$this->cuenta->listar_cuenta_por_id($m->id_usuario_emisor);
                ?>
                <tr>
                    <td><?= $fecha;?></td>
                    <td><?= $m->transferencia_u_u_nro_operacion;?></td>
                    <td><?= $cuenta->cuenta_codigo;?></td>
                    <td><?= $cuenta->person_name." ".$cuenta->person_surname;?></td>
                    <td><?= $cuenta->cuenta_moneda." ".$m->transferencia_u_u_monto;?></td>
                    <td><?= $m->transferencia_u_u_concepto;?></td>
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
        $('#example').DataTable();
    } );
</script>
</body>
</html>
