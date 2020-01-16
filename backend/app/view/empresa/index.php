<div class="panel panel-container">
    <div class="row">
        <div class="col-md-4">
            <div class="panel border-right">
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Sede: </b>Iquitos</h4></div>
            </div>
        </div>
    </div>
</div>
<div class="panel panel-container">
    <div class="row">
        <?php
        if(count($empresas)>0){
            $dia_Actual = date('D');
            $hora_Actual = date('H');
            foreach ($empresas as $e){
                $canchas = $this->empresa->listar_canchas_por_id_empresa($e->empresa_id);
                ($dia_Actual=="Sun")? $horario = $e->empresa_horario_d:$horario = $e->empresa_horario_ls;
                $hi = explode(':',$horario);
                $hs_ = explode('-',$horario);
                $hs = explode(':',$hs_[1]);
                ($hora_Actual>= $hi[0] && $hora_Actual<=$hs[0])?$abierto = "<p style='padding: 5px;border-radius: 5px;border: 1px solid green;color: green'>Abierto</p>":$abierto="<p style='padding: 5px;border-radius: 5px;border: 1px solid red;color: red'>Cerrado</p>";
                ?>
                <div class="col-xs-6 col-md-3 col-lg-3">
                    <a href="<?= _SERVER_?>Empresa/ver/<?= $e->empresa_id; ?>" style="text-decoration: none">
                        <div class="panel panel-widget border-right">
                            <div class="row no-padding"><img alt="empresa" class="img-thumbnail img-responsive" src="<?= _SERVER_ . $e->empresa_foto; ?>"></div>
                            <h3 style="color: green"><?= $e->empresa_nombre; ?></h3>
                            <p><i class="fa fa-phone-square"></i> <?= $e->empresa_telefono_1; ?></p>
                            <p><i class="fa fa-map-marker"></i> <?= $e->empresa_direccion; ?></p>
                            <p># de canchas: <b><?= count($canchas); ?></b></p>
                            <?= $abierto; ?>
                        </div>
                    </a>
                </div>
                <?php
            }
        }else{
            echo "<div class='col-xs-6 col-md-3 col-lg-3'><p>Ninguna empresa registrada</p></div>";
        }
        ?>
    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
</body>
</html>


