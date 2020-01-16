<div class="panel panel-container">
    <div class="row">
        <div class="col-xs-6 col-md-5 col-lg-5">
            <div class="panel panel-widget border-right">
                <div class="row no-padding"><img alt="cancha" class="img-thumbnail img-responsive" src="<?= _SERVER_ . $cancha->cancha_foto; ?>"></div>
                <h3 style="color: green"><b><?= $cancha->empresa_nombre; ?></b></h3>
                <h4 style="color: green"><?= $cancha->cancha_nombre; ?></h4>
                <?php
                $precioD = $cancha->cancha_precioD;
                $precioN = $cancha->cancha_precioN;
                ?>
                <p><i class="fa fa-sun-o"></i> <span><b>S/. <?= $precioD; ?></b></span></p>
                <p><i class="fa fa-moon-o"></i> <span><b>S/. <?= $precioN; ?></b></span></p>
                <p><i class="fa fa-times-rectangle-o"></i> <span><b><?= $cancha->cancha_dimensiones; ?></b></span></p>
            </div>
        </div>
        <div class="col-xs-6 col-md-2 col-lg-2" style="border-right: 1px solid grey">
            <?php
            $ff = $this->validate->get_date_nominal($fecha,"Date","Date","es");
            $fecha_ll = $this->validate->get_day_nominal($fecha_l);
            ?>
            <h3><?= $fecha_ll."<br> ".$ff ?></h3>
            <?php
                if($fecha_l==7){
                    $horario_ = explode(":",$cancha->empresa_horario_d);
                }else{
                    $horario_ = explode(":",$cancha->empresa_horario_ls);
                }
                $horario_final = explode('-',$horario_[1]);
                for ($i = $horario_[0];$i<$horario_final[1];$i++){
                    $j = $i + 1;
                    $hora = $i . ":00 - " . $j .":00";
                    if(count($resumen_hoy)>0){
                        foreach($resumen_hoy as $r){
                            if($r->reserva_hora == $hora){
                                $nombre_ = $r->reserva_nombre;
                                $monto_ = $r->reserva_costo;
                                ?>
                                <button type="button" onclick="form_reserva1('<?= $ff ?>','<?= $hora ?>','<?= $precioD ?>','<?= $precioN ?>','<?= $nombre_; ?>','<?= $monto_; ?>')"  data-toggle="modal" data-target="#exampleModal1" class="btn btn-sm btn-danger" style="margin-bottom: 5px; font-size: 9pt;font-weight: bold; padding-bottom: 0; color: #fff">
                                    <?= $hora; ?>
                                </button>
                                <?php
                            }else{
                                ?>
                                <button type="button" data-toggle="modal" onclick="form_reserva('<?= $ff ?>','<?= $hora ?>','<?= $precioD ?>','<?= $precioN ?>','<?= $fecha ?>')" data-target="#exampleModal" class="btn btn-sm btn-success" style="margin-bottom: 5px; font-size: 9pt;font-weight: bold; padding-bottom: 0">
                                    <?= $hora; ?>
                                </button>
                                <?php
                            }
                        }
                    }else{
                        ?>
                        <button type="button" data-toggle="modal" onclick="form_reserva('<?= $ff ?>','<?= $hora ?>','<?= $precioD ?>','<?= $precioN ?>','<?= $fecha ?>')" data-target="#exampleModal" class="btn btn-sm btn-success" style="margin-bottom: 5px; font-size: 9pt;font-weight: bold; padding-bottom: 0">
                            <?= $hora; ?>
                        </button>
                        <?php
                    }
                }
            ?>
        </div>
        <div class="col-xs-6 col-md-2 col-lg-2" style="border-right: 1px solid grey">
            <?php $ff2 = $this->validate->get_date_nominal($manhana,"Date","Date","es"); $manhana_ll = $this->validate->get_day_nominal($manhana_l);?>
            <h3><?= $manhana_ll."<br>".$ff2 ?></h3>
            <?php
            if($fecha_l==7){
                $horario2_ = explode(":",$cancha->empresa_horario_d);
            }else{
                $horario2_ = explode(":",$cancha->empresa_horario_ls);
            }
            $horario_final2 = explode('-',$horario2_[1]);
            for ($i = $horario2_[0];$i<$horario_final2[1];$i++){
                $j = $i + 1;
                $hora = $i . ":00 - " . $j .":00";
                if(count($resumen_m)>0){
                    foreach($resumen_m as $r){
                        if($r->reserva_hora == $hora){
                            $nombre_ = $r->reserva_nombre;
                            $monto_ = $r->reserva_costo;
                            ?>
                            <button type="button" onclick="form_reserva1('<?= $ff2 ?>','<?= $hora ?>','<?= $precioD ?>','<?= $precioN ?>','<?= $nombre_; ?>','<?= $monto_; ?>')"  data-toggle="modal" data-target="#exampleModal1" class="btn btn-sm btn-danger" style="margin-bottom: 5px; font-size: 9pt;font-weight: bold; padding-bottom: 0; color: #fff">
                                <?= $hora; ?>
                            </button>
                            <?php
                        }else{
                            ?>
                            <button type="button" data-toggle="modal" onclick="form_reserva('<?= $ff2 ?>','<?= $hora ?>','<?= $precioD ?>','<?= $precioN ?>','<?= $manhana ?>')" data-target="#exampleModal" class="btn btn-sm btn-success" style="margin-bottom: 5px; font-size: 9pt;font-weight: bold; padding-bottom: 0">
                                <?= $hora; ?>
                            </button>
                            <?php
                        }
                    }
                }else{
                    ?>
                    <button type="button" data-toggle="modal" onclick="form_reserva('<?= $ff2 ?>','<?= $hora ?>','<?= $precioD ?>','<?= $precioN ?>','<?= $manhana ?>')" data-target="#exampleModal" class="btn btn-sm btn-success" style="margin-bottom: 5px; font-size: 9pt;font-weight: bold; padding-bottom: 0">
                        <?= $hora; ?>
                    </button>
                    <?php
                }
            }
            ?>
        </div>
        <div class="col-xs-6 col-md-2 col-lg-2">
            <?php $ff3 = $this->validate->get_date_nominal($pasado,"Date","Date","es");$pasado_ll = $this->validate->get_day_nominal($pasado_l); ?>
            <h3><?= $pasado_ll."<br>".$ff3 ?></h3>
            <?php
            if($pasado_l==7){
                $horario3_ = explode(":",$cancha->empresa_horario_d);
            }else{
                $horario3_ = explode(":",$cancha->empresa_horario_ls);
            }
            $horario_final3 = explode('-',$horario3_[1]);
            for ($i = $horario3_[0];$i<$horario_final3[1];$i++){
                $j = $i + 1;
                $hora = $i . ":00-" . $j .":00";
                if(count($resumen_p)>0){
                    foreach($resumen_p as $r){
                        if($r->reserva_hora == $hora){
                            $nombre_ = $r->reserva_nombre;
                            $monto_ = $r->reserva_costo;
                            ?>
                            <button type="button" onclick="form_reserva1('<?= $ff3 ?>','<?= $hora ?>','<?= $precioD ?>','<?= $precioN ?>','<?= $nombre_; ?>','<?= $monto_; ?>')"  data-toggle="modal" data-target="#exampleModal1" class="btn btn-sm btn-danger" style="margin-bottom: 5px; font-size: 9pt;font-weight: bold; padding-bottom: 0; color: #fff">
                                <?= $hora; ?>
                            </button>
                            <?php
                        }else{
                            ?>
                            <button type="button" data-toggle="modal" onclick="form_reserva('<?= $ff3 ?>','<?= $hora ?>','<?= $precioD ?>','<?= $precioN ?>','<?= $pasado ?>')" data-target="#exampleModal" class="btn btn-sm btn-success" style="margin-bottom: 5px; font-size: 9pt;font-weight: bold; padding-bottom: 0">
                                <?= $hora; ?>
                            </button>
                            <?php
                        }
                    }
                }else{
                    ?>
                    <button type="button" data-toggle="modal" onclick="form_reserva('<?= $ff3 ?>','<?= $hora ?>','<?= $precioD ?>','<?= $precioN ?>','<?= $pasado ?>')" data-target="#exampleModal" class="btn btn-sm btn-success" style="margin-bottom: 5px; font-size: 9pt;font-weight: bold; padding-bottom: 0">
                        <?= $hora; ?>
                    </button>
                    <?php
                }
            }
            ?>
        </div>
    </div>
</div>
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background: green;">
                <h3 class="modal-title" style="color:#fff; text-align: center" id="exampleModalLabel">Reservar Cancha</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <h3 style="color: green; text-align: center;"><b><?= $cancha->empresa_nombre; ?></b></h3>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <h4 style="color: green"><i class="fa fa-soccer-ball-o"></i> <?= $cancha->cancha_nombre; ?></h4>
                    </div>
                    <div class="col-md-4">
                        <h4 style="color: green" id="lbl_fecha"></h4>
                        <input type="hidden" id="reserva_fecha">
                        <input type="hidden" id="cancha_id" value="<?= $cancha->cancha_id; ?>">
                    </div>
                    <div class="col-md-4">
                        <h4 style="color: green" id="lbl_hora"></h4>
                        <input type="hidden" id="reserva_hora">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label for="reserva_nombre">Nombre de la reserva</label>
                        <input type="text" class="form-control" id="reserva_nombre" placeholder="Ingrese el nombre">
                    </div>
                    <div class="col-md-6">
                        <label for="reserva_monto">Costo de la cancha</label>
                        <h4 style="color: green" id="lbl_costo"></h4>
                    </div>
                    <div class="col-md-6">
                        <label for="reserva_monto">Monto a pagar</label>
                        <input type="text" class="form-control" id="reserva_monto" placeholder="Ingrese el monto a pagar">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-success" onclick="reservar()">Reservar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="exampleModal1" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel1" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background: green;">
                <h3 class="modal-title" style="color:#fff; text-align: center" id="exampleModalLabel">Detalle de Reserva</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <h3 style="color: green; text-align: center;"><b><?= $cancha->empresa_nombre; ?></b></h3>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <h4 style="color: green"><i class="fa fa-soccer-ball-o"></i> <?= $cancha->cancha_nombre; ?></h4>
                    </div>
                    <div class="col-md-4">
                        <h4 style="color: green" id="lbl_fecha1"></h4>
                    </div>
                    <div class="col-md-4">
                        <h4 style="color: green" id="lbl_hora1"></h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label>Nombre de la reserva</label>
                        <h4 id="lbl_nombre"></h4>
                    </div>
                    <div class="col-md-6">
                        <label>Costo de la cancha</label>
                        <h4 style="color: green" id="lbl_costo1"></h4>
                    </div>
                    <div class="col-md-6">
                        <label>Monto pagado</label>
                        <h4 id="lbl_monto1"></h4>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-success" data-dismiss="modal">Listo</button>
            </div>
        </div>
    </div>
</div>

<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>empresa.js"></script>
</body>
</html>