<?php
?>
    <div class="row">
        <div class="col-xs-10">
            <center><h2>Lista de Retiros realizados</h2></center>
        </div>
        <div class="col-xs-2">
            <center><a class="btn btn-block btn-success btn-sm" href="<?php echo _SERVER_;?>PagoCIP/retirar_cip" >Retirar CIP</a></center>
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
                    <th>Agente</th>
                    <th>Codigo</th>
                    <th>Monto retirado</th>
                    <th>Concepto</th>
                    <th>Fecha</th>
                </tr>
                </thead>
                <tbody>
                <?php
                foreach ($pagos_cip as $m){
                    ?>
                    <tr>
                        <td><?= $m->id_detalle_pagocip;?></td>
                        <td><?= $m->agente_nombre;?></td>
                        <td><?= $m->pagocip_codigo;?></td>
                        <td><?= $m->pagocip_monto;?></td>
                        <td><?= $m->pagocip_concepto;?></td>
                        <td><?= $m->detalle_pagocip_date;?></td>
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

