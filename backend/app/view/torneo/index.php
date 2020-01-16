<ul class="nav nav-tabs nav-justified">
    <li class="active"><a data-toggle="tab" href="#torneos"><i class="fa fa-trophy"></i> Torneos</a></li>
    <li><a data-toggle="tab" href="#equipos"><i class="fa fa-users"></i> Equipos</a></li>
    <li><a data-toggle="tab" href="#retos"><i class="fa fa-arrows-h"></i> Retos</a></li>
    <li><a data-toggle="tab" href="#chats"><i class="fa fa-smile-o"></i> Chats</a></li>
    <li><a data-toggle="tab" href="#estadisticas"><i class="fa fa-soccer-ball-o"></i> Estadísticas</a></li>
</ul>

<div class="tab-content">
    <div id="torneos" class="tab-pane fade in active">
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-container">
                    <div class="row">
                        <div class="col-md-9">
                            <div class="panel border-right">
                                <h3>Torneos de la Comunidad</h3>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <?php
                        if(count($torneos)>0){
                            foreach ($torneos as $t) {
                                ?>
                                <div class="col-md-3" style="word-break: break-all;">
                                    <a href="<?= _SERVER_ ?>Torneo/ver_torneo/<?= $t->torneo_id ?>" style="text-decoration: none;">
                                    <img src="<?= _SERVER_ . $t->torneo_imagen; ?>" alt="Torneo" style="max-height: 150px; border-radius: 25px;" class=" img-thumbnail img-responsive">
                                    <h4><?= $t->torneo_nombre; ?></h4>
                                    <p><i class="fa fa-user"></i> <?= $t->torneo_organizador ?></p>
                                    <p><i class="fa fa-map-marker"></i> <?= $t->torneo_lugar ?></p>
                                    <p><i class="fa fa-calendar"></i> <?= $t->torneo_fecha. " ".$t->torneo_hora ?></p>
                                    <p>S/. <?= $t->torneo_costo ?></p>
                                    <p># Equipos <?= $t->equipos ?></p>
                                    </a>
                                </div>
                                <?php
                            }
                        }else{
                            ?>
                            <p>Aún no hay torneos registrados</p>
                            <?php
                        }
                        ?>

                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-container">
                    <div class="row">
                        <div class="col-md-9">
                            <div class="panel border-right">
                                <h3>Mis Torneos</h3>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <a href="<?= _SERVER_ ?>Torneo/crear_torneo" class="btn btn-success"><i class="fa fa-plus"></i> Agregar Torneo</a>
                        </div>
                    </div>
                    <div class="row">
                        <?php
                        if(count($mis_torneos)>0){
                            foreach ($mis_torneos as $t) {
                                ?>
                                <div class="col-md-3" style="word-break: break-all;">
                                    <a href="<?= _SERVER_ ?>Torneo/ver_torneo/<?= $t["id_torneo"] ?>" style="text-decoration: none;">
                                    <img src="<?= _SERVER_ . $t["imagen"]; ?>" alt="Torneo" style="max-height: 150px; border-radius: 25px;" class=" img-thumbnail img-responsive">
                                    <h4><?= $t["nombre"]; ?></h4>
                                    <p><i class="fa fa-user"></i> <?= $t["organizador"] ?></p>
                                    <p><i class="fa fa-map-marker"></i> <?= $t["lugar"] ?></p>
                                    <p><i class="fa fa-calendar"></i> <?= $t["fecha"]. " ".$t["hora"] ?></p>
                                    <p>S/. <?= $t["costo"] ?></p>
                                    <p># Equipos <?= $t["equipos"] ?></p>
                                    </a>
                                </div>
                                <?php
                            }
                        }else{
                            ?>
                            <p>Aún no hay torneos registrados</p>
                            <?php
                        }
                        ?>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="equipos" class="tab-pane fade">
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-container">
                    <div class="row">
                        <div class="col-md-9">
                            <div class="panel border-right">
                                <h3>Mis Equipos</h3>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <a href="<?= _SERVER_ ?>Torneo/crear_equipo" class="btn btn-success"><i class="fa fa-plus"></i> Agregar Equipo</a>
                        </div>
                    </div>
                    <div class="row">
                        <?php
                        if(count($mis_equipos)>0){
                            foreach ($mis_equipos as $t) {
                                ?>
                                <div class="col-md-3" style="word-break: break-all;">
                                    <a href="<?= _SERVER_ ?>Torneo/ver_equipo/<?= $t->equipo_id; ?>" style="text-decoration: none">
                                    <img src="<?= _SERVER_ . $t->equipo_foto; ?>" alt="Equipo" style="max-height: 150px; border-radius: 25px;" class=" img-thumbnail img-responsive">
                                    <h4><?= $t->equipo_nombre; ?></h4>
                                    <p><i class="fa fa-star"></i> <?= $t->equipo_valoracion ?></p>
                                    </a>
                                </div>
                                <?php
                            }
                        }else{
                            ?>
                            <p>Aún no hay equipos registrados</p>
                            <?php
                        }
                        ?>

                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-container">
                    <div class="row">
                        <div class="col-md-9">
                            <div class="panel border-right">
                                <h3>Equipos Populares</h3>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <?php
                        if(count($equipos)>0){
                            foreach ($equipos as $t) {
                                ?>
                                <div class="col-md-3" style="word-break: break-all;">
                                    <img src="<?= _SERVER_ . $t->equipo_foto; ?>" alt="Equipo" style="max-height: 150px; border-radius: 25px;" class=" img-thumbnail img-responsive">
                                    <h4><?= $t->equipo_nombre; ?></h4>
                                    <p><i class="fa fa-star"></i> <?= $t->equipo_valoracion ?></p>
                                </div>
                                <?php
                            }
                        }else{
                            ?>
                            <p>Aún no hay otros equipos registrados</p>
                            <?php
                        }
                        ?>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="retos" class="tab-pane fade">
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-container">
                    <div class="row">
                        <div class="col-md-9">
                            <div class="panel border-right">
                                <h3>Mis retos</h3>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <?php
                        if(count($mis_retos)>0){
                            foreach ($mis_retos as $t) {
                                ?>
                                <div class="col-md-4" style="word-break: break-all">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <img src="<?= _SERVER_ . $t->foto_1; ?>" alt="Equipo" style="max-height: 150px; border-radius: 25px;" class=" img-thumbnail img-responsive">
                                            <h4><?= $t->nombre_1; ?></h4>
                                        </div>
                                        <div class="col-md-1">
                                            <h3>VS</h3>
                                        </div>
                                        <div class="col-md-5">
                                            <img src="<?= _SERVER_ . $t->foto_2; ?>" alt="Equipo" style="max-height: 150px; border-radius: 25px;" class=" img-thumbnail img-responsive">
                                            <h4><?= $t->nombre_2; ?></h4>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p><i class="fa fa-calendar"></i> <?= $t->reto_fecha;?></p>
                                        </div>
                                        <div class="col-md-4">
                                            <p><i class="fa fa-clock-o"></i> <?= $t->reto_hora;?></p>
                                        </div>
                                        <div class="col-md-4">
                                            <p><i class="fa fa-map-marker"></i> <?= $t->reto_lugar;?></p>
                                        </div>
                                    </div>
                                </div>
                                <?php
                            }
                        }else{
                            ?>
                            <p>Aún no hay otros equipos registrados</p>
                            <?php
                        }
                        ?>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="chats" class="tab-pane fade">
        <h3>Menu 2</h3>
        <p>Some content in menu 2.</p>
    </div>
    <div id="estadisticas" class="tab-pane fade">
        <h3>Menu 2</h3>
        <p>Some content in menu 2.</p>
    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
</body>
</html>
