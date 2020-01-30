<div class="row">
    <div class="col-md-10">
        <div class="panel panel-container">
            <div class="row">
                <div class="col-md-9">
                    <div class="panel border-right">
                        <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4><b>Canchas disponibles el día de hoy</b></h4></div>
                    </div>
                </div>
                <div class="col-md-2">
                    <a href="<?= _SERVER_ ?>Admin/busqueda_avanzada" class="btn btn-success"><i class="fa fa-search"></i> Búsqueda Avanzada</a>
                </div>
            </div>
        </div>

            <?php
            for($i=0;$i<count($resources);$i++ ){
                $hor = $hora + $i;
                $j = $hor + 1;
                ?>
        <div class="panel panel-container" style="padding-left: 20px;">
                <div class="row">
                    <div class="col-md-10">
                        <h3><i style="color: green" class="fa fa-clock-o"></i> <?= $hor.":00 - ".$j.":00";?></h3>
                    </div>
                </div>
                    <div class="row">
                <?php
                $iii = 0;
                $dia_Actual = date('D');
                $hora_Actual = date('H');
                for ($k=0;$k<count($resources[$i][$hor]);$k++){
                    ($dia_Actual=="Sun")? $horario = $resources[$i][$hor][$k]->empresa_horario_d:$horario = $resources[$i][$hor][$k]->empresa_horario_ls;
                    $hi = explode(':',$horario);
                    $hs_ = explode('-',$horario);
                    $hs = explode(':',$hs_[1]);
                    ($hora_Actual>= $hi[0] && $hora_Actual<=$hs[0])?$abierto = true:$abierto=false;
                    if($abierto) {
                        if($iii%4==0){
                            echo "</div><div class='row'>";
                        }
                        ?>
                        <div class="col-xs-3 col-md-3 col-lg-3 no-padding">
                            <a href="<?= _SERVER_ ?>Empresa/ver/<?= $resources[$i][$hor][$k]->empresa_id; ?>"
                                   style="text-decoration: none">
                                <div class="panel panel-teal panel-widget border-right">
                                    <div class="row no-padding">
                                        <img class="img-responsive img-thumbnail"
                                             src="<?= _SERVER_ . $resources[$i][$hor][$k]->empresa_foto; ?>">
                                    </div>
                                    <div class="large"><h4><?= $resources[$i][$hor][$k]->empresa_nombre; ?></h4></div>
                                    <p style="color: green"><i class="fa fa-sun-o"></i>
                                        S/. <?= $resources[$i][$hor][$k]->cancha_precioD; ?></p>
                                    <p style="color: green"><i class="fa fa-moon-o"></i>
                                        S/. <?= $resources[$i][$hor][$k]->cancha_precioN; ?></p>
                                </div>
                            </a>
                            <form method="post" action="<?= _SERVER_ ?>Empresa/reservar_cancha">
                                <input type="hidden" name="fecha" value="<?= date('Y-m-d'); ?>">
                                <input type="hidden" name="hora" value="<?= $hor.":00 - ".$j.":00";?>">
                                <input type="hidden" name="empresa" value="<?= $resources[$i][$hor][$k]->empresa_id; ?>">
                                <center><input type="submit" class="btn btn-danger" value="Reservar"></center>
                            </form>
                        </div>
                        <?php

                        $iii++;
                    }
                }
                echo "</div></div>";
            }
            ?>

    </div>
</div>
<script src="<?php echo _SERVER_ . _STYLES_ADMIN_;?>js/jquery-1.11.1.min.js"></script>
<script src="<?php echo  _SERVER_ . _STYLES_ALL_;?>charts/Chart.min.js"></script>

