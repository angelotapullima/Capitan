<?php
?>
<div class="row">
    <div class="col-xs-10">
        <center><h2>Lista de Retiros realizadas</h2></center>
    </div>
    <div class="col-xs-2">
        <center><a class="btn btn-block btn-success btn-sm" href="<?php echo _SERVER_;?>Cuenta/retirar" >Retirar</a></center>
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
                <th>Fecha</th>
                <th>Código</th>
                <th>Monto</th>
                <th>Concepto</th>
                <th>Estado</th>
                <th>Agente Pagado</th>
                <th>Fecha de Pago</th>
                <th>Acción</th>
            </tr>
            </thead>
            <tbody>
            <?php
            foreach ($retiros as $m){
                $fecha = $this->validate->get_date_nominal($m->pagocip_date,'DateTime','DateTime','es');
                $agente = " - ";
                $fecha_pago = " - ";
                if($m->pagocip_estado==1){
                    $estado="Retirado";
                    $detalle_cip = $this->cuenta->listar_detalle_pagocip_por_id($m->id_pagocip);
                    $agente = $detalle_cip->agente_nombre;
                    $fecha_pago = $detalle_cip->detalle_pagocip_date;
                }elseif ($m->pagocip_estado==2){
                    $estado="Pendiente de Retiro";
                    $style = "style='background: yellow;'";
                }elseif ($m->pagocip_estado==3){
                    $estado="Cancelado";
                }elseif ($m->pagocip_estado==4){
                    $estado="Expirado";
                }
                ?>
                <tr>
                    <td><?= $fecha;?></td>
                    <td><?= $m->pagocip_codigo;?></td>
                    <td><?= $m->pagocip_monto;?></td>
                    <td><?= $m->pagocip_concepto;?></td>
                    <td <?= $style; ?>><?= $estado;?></td>
                    <td><?= $agente;?></td>
                    <td><?= $fecha_pago;?></td>
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
