<div class="panel panel-container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel border-right">
                <div class="row">
                    <div class="col-md-5">
                        <img alt="empresa" class="img-thumbnail img-responsive" src="<?= _SERVER_ . $empresa->empresa_foto; ?>">
                    </div>
                    <div class="col-md-7">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="large" style="padding-left: 20px;margin-bottom: 20px; text-align: center"><h3><b><?= $empresa->empresa_nombre; ?> </b></h3></div>
                            </div>
                            <div class="col-md-12">
                                <div class="col-md-6">
                                    <h3><i style="color: green;" class="fa fa-soccer-ball-o"></i> <?= $empresa->empresa_descripcion; ?></h3>
                                </div>
                                <div class="col-md-6">
                                    <h4><i style="color: green;" class="fa fa-map-marker"></i> <?= $empresa->empresa_direccion; ?> </h4>
                                    <small><a href="#">(Ver en el mapa)</a></small>
                                    <h4><i style="color: green;" class="fa fa-phone-square"></i> <?= $empresa->empresa_telefono_1; ?></h4>
                                    <h4> <i style="color: green;" class="fa fa-phone-square"></i> <?= $empresa->empresa_telefono_2; ?></h4>
                                    <h4><i style="color: green;" class="fa fa-clock-o"></i> <?= $empresa->empresa_horario_ls; ?> (Lunes a Sábado)</h4>
                                    <h4><i style="color: green;" class="fa fa-clock-o"></i> <?= $empresa->empresa_horario_d; ?> (Domingo)</h4>
                                    <h4><i style="color: green;" class="fa fa-star"></i> <?= $empresa->empresa_valoracion; ?></h4>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel panel-container">
    <div class="row">
        <div class="col-md-offset-10">
            <a href="<?= _SERVER_ ?>Empresa/agregar_cancha/<?= $empresa->empresa_id; ?>" class="btn btn-success"><i class="fa fa-plus"></i> Agregar Cancha</a>
        </div>
    </div>
    <div class="row">
        <?php
        if(count($canchas)>0){
            foreach ($canchas as $e){
                ?>
                <div class="col-xs-6 col-md-3 col-lg-3">
                    <a href="<?= _SERVER_?>Empresa/ver_cancha/<?= $e->cancha_id; ?>" style="text-decoration: none">
                        <div class="panel panel-widget border-right">
                            <div class="row no-padding"><img alt="cancha" class="img-thumbnail img-responsive" src="<?= _SERVER_ . $e->cancha_foto; ?>"></div>
                            <h3 style="color: green"><?= $e->cancha_nombre; ?></h3>
                            <p><i class="fa fa-sun-o"></i> <span><b>S/. <?= $e->cancha_precioD; ?></b></span></p>
                            <p><i class="fa fa-moon-o"></i> <span><b>S/. <?= $e->cancha_precioN; ?></b></span></p>
                            <p><i class="fa fa-times-rectangle-o"></i> <span><b><?= $e->cancha_dimensiones; ?></b></span></p>
                        </div>
                    </a>
                </div>
                <?php
            }
        }else{
            echo "<div class='col-xs-6 col-md-3 col-lg-3'><p>Ninguna cancha registrada</p></div>";
        }
        ?>
    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
</body>
</html>
