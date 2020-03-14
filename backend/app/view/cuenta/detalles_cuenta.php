<?php ?>
<div class="panel panel-container">
    <div class="row">
        <div class="col-md-4">
            <div class="panel border-right">
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Tipo de cuenta:</b> Cuenta Simple</h4></div>
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Número de cuenta:</b> <?= $datos_cuenta->cuenta_codigo; ?></h4></div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel border-right">
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Saldo Actual:</b> <b style="color:red;"><img src="<?= _SERVER_?>media/bufi.png" width="30"> <?= $datos_cuenta->cuenta_saldo; ?></b></h4></div>
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Usuario:</b> <?= $datos_cuenta->person_name." " .$datos_cuenta->person_surname; ?></h4></div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel border-right">
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Último acceso:</b> <?= $this->validate->get_date_nominal($datos_cuenta->user_last_login,'DateTime','DateTime','es'); ?></h4></div>
            </div>
        </div>
    </div>
</div>
<div class="panel panel-container">
    <div class="row">
        <div class="col-xs-6 col-md-4 col-lg-4 no-padding">
            <div class="panel panel-teal panel-widget border-right">
                <div class="row no-padding"><i class="fa fa-money fa-4x color-orange"></i></div>
                <div class="large"><a href="<?= _SERVER_ ?>Cuenta/historial_recargas_cuenta" class="btn btn-primary">Ultimas recargas</a></div>
                <!--<div class="large"><h4>Ultimas recargas</h4></div>
                <table class="table table-bordered table-condensed" >
                    <thead>
                    <th>Fecha</th>
                    <th>Monto</th>
                    <th>Estado</th>
                    </thead>
                    <tbody>
                <?php
                foreach ($ultimas_recargas as $ur){
                    $fecha = $this->validate->get_date_nominal($ur->pagocip_date,'DateTime','DateTime','es');
                    ?>
                    <tr>
                        <td><?= $fecha; ?></td>
                        <td><?= $ur->pagocip_monto; ?></td>
                        <?php $style=''; if($ur->pagocip_estado==1){
                            $estado="Pagado";
                        }elseif ($ur->pagocip_estado==2){
                            $estado="Pendiente de Pago";
                            $style = "style='background: yellow;'";
                        }elseif ($ur->pagocip_estado==3){
                            $estado="Cancelado";
                        }elseif ($ur->pagocip_estado==4){
                            $estado="Expirado";
                        } ?>
                        <td <?= $style; ?>><?= $estado; ?></td>
                    </tr>
                <?php
                }
                ?>
                    </tbody>
                </table>-->
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