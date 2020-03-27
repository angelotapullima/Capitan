<div class="row">
    <div class="col-md-9">
        <div class="row">
            <div class="col-md-7">
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h2>Canchas disponibles el día de hoy</h2></div>
                <div class="large" style="padding-left: 20px;margin-bottom: 20px;"><h4>Escoge el horario que desees y ¡Reserva!</h4></div>
            </div>
            <div class="col-md-4">
                <h4>Si deseas reservar para otras fechas, haz click aquí <a href="<?= _SERVER_ ?>Admin/busqueda_avanzada" class="btn btn-success"><i class="fa fa-search"></i> Búsqueda Avanzada</a>
            </div>
        </div>
            <?php
            for($i=0;$i<count($resources);$i++ ){
                $hor = $hora + $i;
                $j = $hor + 1;
                ?>
            <div class="card shadow" style="margin-left: 10px;">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between" style="cursor: pointer">
                    <h3 data-toggle="collapse" href="#cont_hora_<?= $hor; ?>"><i style="color: green;" class="fa fa-clock-o"></i> <?= $hor.":00 - ".$j.":00";?></h3>
                </div>
                <div class="card-body collapse" id="cont_hora_<?= $hor; ?>" style="background: #e1e1e1 !important;">
                <div class="row" >
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
                        <div class="col-xs-6 col-sm-6 col-md-3 col-lg-3 no-padding" >
                            <a style="text-decoration: none" href="<?= _SERVER_ ?>Empresa/ver/<?= $resources[$i][$hor][$k]->empresa_id; ?>"
                               class="cuadro">
                                <div class="panel panel-teal panel-widget">
                                    <div class="row no-padding">
                                        <img class="img-responsive img-thumbnail"
                                             src="<?= _SERVER_ . $resources[$i][$hor][$k]->empresa_foto; ?>">
                                    </div>
                                    <div class="large"><h4 style="color: green;font-weight: bold"><?= $resources[$i][$hor][$k]->empresa_nombre; ?></h4></div>
                                    <p style="color: green"><i class="fa fa-sun-o"></i>
                                        S/. <?= $resources[$i][$hor][$k]->cancha_precioD; ?> | <i class="fa fa-moon-o"></i>
                                        S/. <?= $resources[$i][$hor][$k]->cancha_precioN; ?></p>
                                    <p style="color:#000;"><i class="fa fa-map-marker"></i> <?= $resources[$i][$hor][$k]->empresa_direccion; ?></p>
                                    <?php
                                    $fecha_actual=date('Y-m-d H:i:s');
                                    if($resources[$i][$hor][$k]->cancha_promo_estado==1){
                                        if($fecha_actual>=$resources[$i][$hor][$k]->cancha_promo_inicio && $fecha_actual<$resources[$i][$hor][$k]->cancha_promo_fin){
                                            $fff=$this->validate->get_date_nominal($resources[$i][$hor][$k]->cancha_promo_fin,'DateTime','DateTime','es');
                                            echo "<span style='color:red;'>Promoción: S/. ".$resources[$i][$hor][$k]->cancha_promo_precio."</span><br>";
                                            echo "<span style='color:red;'>Válido hasta: ".$fff."</span>";
                                        }else{
                                            $this->empresa->registrar_promo($resources[$i][$hor][$k]->cancha_id,0,'00-00-0000 00:00:00','00-00-0000 00:00:00',0);
                                        }
                                    }
                                    ?>
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
                    ?>
                </div>
                </div>
            </div>
                    <?php
            }
            ?>
        </div>
    <div class="col-md-3">

    </div>
    </div>
<script src="<?php echo _SERVER_ . _STYLES_ADMIN_;?>js/jquery-1.11.1.min.js"></script>
<script src="<?php echo  _SERVER_ . _STYLES_ALL_;?>charts/Chart.min.js"></script>

