<?php ?>
<div class="panel panel-container">
    <div class="row">
        <div class="col-md-4">
            <div class="panel border-right">
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Tipo de cuenta:</b> Cuenta Empresarial</h4></div>
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Número de cuenta:</b> <?= $datos_cuenta->cuentae_codigo; ?></h4></div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel border-right">
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Empresa: </b><?= $datos_cuenta->empresa_nombre; ?></h4></div>
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Saldo Actual:</b> <b style="color:red;"><?= $datos_cuenta->cuentae_moneda." ".$datos_cuenta->cuentae_saldo; ?></b></h4></div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel border-right">
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Último acceso:</b> <?= $empresas[0]->user_last_login; ?></h4></div>
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Usuario:</b> <?=$empresas[0]->person_name." " .$empresas[0]->person_surname; ?></h4></div>
            </div>
        </div>
    </div>
</div>
<div class="panel panel-container">
    <div class="row">
        <div class="col-xs-6 col-md-4 col-lg-4 no-padding">
            <div class="panel panel-teal panel-widget border-right">
                <div class="large"><h4>Ultimas recargas</h4></div>
                <table class="table table-bordered table-condensed" >
                    <thead>
                    <th>Fecha</th>
                    <th>Monto</th>
                    <th>Nro Operacion</th>
                    </thead>
                    <tbody>
                    <?php
                    foreach ($ultimas_recargas as $ur){
                        $fecha = $this->validate->get_date_nominal($ur->transferencia_e_e_date,'DateTime','DateTime','es');
                        ?>
                        <tr>
                            <td><?= $fecha; ?></td>
                            <td><?= $ur->transferencia_e_e_monto; ?></td>
                            <td><?= $ur->transferencia_e_e_nro_operacion; ?></td>
                        </tr>
                        <?php
                    }
                    ?>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col-xs-6 col-md-4 col-lg-4 no-padding">
            <div class="panel panel-teal panel-widget border-right">
                <div class="large"><h4>Ultimos movimientos</h4></div>
            </div>
        </div>
        <div class="col-xs-6 col-md-4 col-lg-4 no-padding">
            <div class="panel panel-teal panel-widget border-right">
                <div class="large"><h4>Ultimas compras</h4></div>
            </div>
        </div>
    </div>
</div>

<script src="<?php echo _SERVER_ . _STYLES_ADMIN_;?>js/jquery-1.11.1.min.js"></script>
<script src="<?php echo  _SERVER_ . _STYLES_ALL_;?>charts/Chart.min.js"></script>
<script>

</script>



