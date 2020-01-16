<div class="panel panel-container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel border-right">
                <div class="row">
                    <div class="col-md-5">
                        <img alt="Torneo" class="img-thumbnail img-responsive" src="<?= _SERVER_ . $torneo->torneo_imagen; ?>">
                    </div>
                    <div class="col-md-7">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="large" style="padding-left: 20px;margin-bottom: 20px; text-align: center"><h3><b><?= $torneo->torneo_nombre; ?> </b></h3></div>
                            </div>
                            <div class="col-md-12">
                                <h4><i style="color: green;" class="fa fa-soccer-ball-o"></i> <?= $torneo->torneo_descripcion; ?></h4>
                                <h4><i style="color: green;" class="fa fa-user"></i> <?= $torneo->torneo_organizador; ?></h4>
                                <h4><i style="color: green;" class="fa fa-map-marker"></i> <?= $torneo->torneo_lugar; ?> </h4>
                                <h4><i style="color: green;" class="fa fa-calendar"></i> <?= $torneo->torneo_fecha; ?></h4>
                                <h4> <i style="color: green;" class="fa fa-clock-o"></i> <?= $torneo->torneo_hora; ?></h4>
                                <h4><span style="color: green">S/. </span><?= $torneo->torneo_costo; ?></h4>
                                <h4><span style="color:green;"># Equipos</span> <?= count($equipos2); ?></h4>
                                <h4> <i style="color: green;" class="fa fa-tag"></i> <?= $torneo->torneo_tipo; ?></h4>
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
        <div class="col-md-12">
            <ul class="nav nav-tabs nav-justified">
                <li class="active"><a data-toggle="tab" href="#publicaciones"><i class="fa fa-pencil"></i> Publicaciones</a></li>
                <li><a data-toggle="tab" href="#equipos"><i class="fa fa-users"></i> Grupos y Equipos</a></li>
                <li><a data-toggle="tab" href="#partidos"><i class="fa fa-trophy"></i> Instancias y Partidos</a></li>
                <li><a data-toggle="tab" href="#posiciones"><i class="fa fa-table"></i> Posiciones</a></li>
                <li><a data-toggle="tab" href="#estadisticas"><i class="fa fa-bar-chart"></i> Estadísticas</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="tab-content">
    <div id="publicaciones" class="tab-pane fade in active">
        <div class="col-md-8 col-md-offset-1" style="margin-top: 0;">
        <div class="panel panel-container border-right" style="padding-left: 10px">
            <form enctype="multipart/form-data" id="fupFormForo">
                <h3>Agregar Publicación</h3>
                <div class="row">
                    <div class="col-md-8">
                        <div class="row">
                            <div class="col-md-2">
                                <img alt="user" class="img-thumbnail img-circle img-responsive" src="<?= _SERVER_ . $this->crypt->decrypt($_SESSION['user_image'], _FULL_KEY_); ?>">
                            </div>
                            <div class="col-md-10">
                                <p><?= $this->crypt->decrypt($_SESSION['user_nickname'], _FULL_KEY_); ?></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-11">
                                <input type="text" class="form-control" autocomplete="off" placeholder="Título" id="titulo" name="titulo" style="border: 1px dashed green;font-weight: bold">
                                <input type="hidden" name="usuario_id" value="<?= $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_); ?>">
                                <input type="hidden" name="concepto" value="publicacion">
                                <input type="hidden" name="id_torneo" value="<?= $torneo->torneo_id; ?>">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-11">
                                <br><textarea name="descripcion" id="descripcion" class="form-control" placeholder="¿Qué deseas publicar?" style="border: 1px dashed green" rows="4"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <img id="imagenPrevisualizacion" src="<?= _SERVER_?>media/uploadimage.png" height="100" width="120"><br>
                        <label id="lbl_imagen" for="imagen"><i class="fa fa-image"></i> Subir Foto</label>
                        <input type="file" id="imagen" name="imagen" accept="image/*">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-offset-4 col-md-2">
                        <br>
                        <button type="submit" name="submit" class="btn btn-success submitBtn" style="background: green;"><i class='fa fa-check'></i> Publicar</button>
                    </div>
                </div>
            </form>
        </div>
        </div>
        <?php
        if(count($publicaciones)>0){
            foreach ($publicaciones as $e){
                $comentarios = $this->foro->listar_comentarios($e->publicaciones_id);
                $cant_likes = $this->foro->conteo_likes($e->publicaciones_id);
                $dio_like = $this->foro->dio_like($e->publicaciones_id,$this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_));
                ?>
                <div class="panel panel-container border-right" style="padding-left: 10px">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="row">
                                <div class="col-xs-1 col-md-1">
                                    <img src="<?= _SERVER_ . $e->user_image; ?>" class="img-circle img-responsive" style="max-width: 50px">
                                </div>
                                <div class="col-xs-8 col-md-8">
                                    <a href="#"><?= $e->user_nickname; ?></a>
                                    <p style="font-size: 9pt;">Hace <?= $this->validate->time_between_dates($e->publicaciones_fecha,"hoy"); ?></p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8 col-md-offset-1" style="margin-top: 0;">
                            <div class="panel panel-widget border-right">
                                <div class="row">
                                    <div class="col-md-12">
                                        <h3 style="color: green;margin: 0"><?= $e->publicaciones_titulo; ?></h3>
                                        <p><?= $e->publicaciones_descripcion; ?></p>
                                        <div class="row no-padding"><img alt="cancha" class="img-thumbnail img-responsive" src="<?= _SERVER_.$e->publicaciones_foto; ?>"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 col-md-offset-2">
                                        <?php
                                        if(isset($dio_like->publicaciones_id)){
                                            ?>
                                            <button id="btn_like_<?= $e->publicaciones_id; ?>" style="background: #30a5ff;color:#fff" onclick="quitar_like('<?= $e->publicaciones_id; ?>')" class="form-control"><i class="fa fa-thumbs-up"></i> <span id="span_like_<?=$e->publicaciones_id; ?>"><?= $cant_likes->conteo; ?></span> Me gusta</button>
                                            <?php
                                        }else{
                                            ?>
                                            <button id="btn_like_<?= $e->publicaciones_id; ?>" onclick="dar_like('<?= $e->publicaciones_id; ?>')" class="form-control"><i class="fa fa-thumbs-up"></i> <span id="span_like_<?=$e->publicaciones_id; ?>"><?= $cant_likes->conteo; ?></span> Me gusta</button>
                                            <?php
                                        }
                                        ?>
                                    </div>
                                    <div class="col-md-4">
                                        <a onclick="btn_comentar('<?= $e->publicaciones_id; ?>')" href="#section_comment_<?= $e->publicaciones_id; ?>" class="form-control"><i class="fa fa-comment-o"></i> <span id="span_comment_<?=$e->publicaciones_id; ?>"><?= count($comentarios); ?></span> Comentar</a>
                                    </div>
                                </div><hr>
                                <div id="comments_<?= $e->publicaciones_id; ?>">
                                    <?php
                                    if(count($comentarios)>0){
                                        foreach ($comentarios as $c){
                                            ?>
                                            <div class="row">
                                                <div class="col-md-2">
                                                    <img alt="<?= $c->user_image; ?>" style="max-width: 50px" class="img-thumbnail img-circle img-responsive" src="<?= _SERVER_ . $c->user_image; ?>">
                                                </div>
                                                <div class="col-md-9">
                                                    <p style="text-align: left; color: green"><?= $c->user_nickname; ?> </p>
                                                    <p style="text-align: left; word-break: break-all;background: lightgray; padding:10pt;border-radius: 10px"><?= $c->comentario_coment; ?></p>
                                                    <p style="text-align: right; font-size: 8pt;"><?= $this->validate->time_between_dates($c->comentario_fecha,"hoy"); ?></p>
                                                </div>
                                            </div>
                                            <?php
                                        }
                                    }
                                    ?>
                                </div>
                                <div class="row" id="section_comment_<?= $e->publicaciones_id; ?>">
                                    <div class="col-md-2">
                                        <img alt="user" class="img-thumbnail img-circle img-responsive" src="<?= _SERVER_ . $this->crypt->decrypt($_SESSION['user_image'], _FULL_KEY_); ?>">
                                    </div>
                                    <div class="col-md-9">
                                        <div id="contenedor">
                                            <div class="comentando" onkeydown="verificar('<?= $e->publicaciones_id; ?>')" id="comment_<?= $e->publicaciones_id; ?>" onblur="clean_text('<?= $e->publicaciones_id; ?>')" onclick="clean_text('<?= $e->publicaciones_id; ?>')" style="border-radius: 10px; border: 1px solid; text-align: left; width: 100%; padding: 5px; margin-top: 20px;margin-left: 0" contenteditable="true">Escribe un comentario</div>
                                        </div>
                                        <small style="text-align: left">Pulse "Intro" para publicar</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <?php
            }
        }else{
            ?>
            <p>Ninguna publicación realizada.</p>
        <?php
        }
        ?>
        <input type="hidden" id="id_comment">
    </div>
    <div id="equipos" class="tab-pane fade">
        <div class="row">
            <?php
            if($torneo->torneo_tipo == 1){
                ?>
                <div class="col-md-10">
                    <p>Listado de los grupos y sus respectivos equipos</p>
                </div>
                <div class="col-md-2">
                    <button class="d-none d-sm-inline-block btn btn-sm btn-danger shadow-sm" title="Agregar Grupo" data-toggle="modal" data-target="#agregar_grupo"><i class="fa fa-plus fa-sm"></i> Agregar Grupo</button>
                </div>
                <?php
            }else{
                ?>
                <div class="col-md-10">
                    <p>El tipo de campeonato elegido permite solo un grupo general. </p>
                </div>
                <?php
            }
            ?>
        </div>
        <div class="row" style="word-break: break-all;">
            <?php
            if(count($resources)>0){
                $iii = 0;
                foreach ($resources as $t) {
                    if($iii % 4 == 0){
                        echo "</div><div class='row'>";
                    }
                    ?>
                    <div class="col-md-3">
                        <table class="table table-bordered">
                            <tr><td style="font-weight: bold"><?= $t["nombre_grupo"]; ?></td></tr>
                            <?php
                            foreach($t["equipos"] as $e){
                                ?>
                                <tr><td><img src="<?= _SERVER_ . $e["equipo_foto"] ?>" class="img-responsive img-circle img-thumbnail" style="max-width: 50px"><?= $e["equipo_nombre"] ?></td></tr>
                                <?php
                            }
                            ?>
                            <tr><td><button class="d-none d-sm-inline-block btn btn-sm btn-info shadow-sm" title="Agregar Equipo" onclick="add_equipo(<?= $t["id_torneo_grupo"]; ?>)" data-toggle="modal" data-target="#agregar_equipo"><i class="fa fa-plus fa-sm"></i> Agregar Equipo</button></td></tr>
                        </table>
                    </div>
                    <?php
                    $iii++;
                }
            }else{
                ?>
                <p>Aún no hay otros equipos registrados</p>
                <?php
            }
            ?>

        </div>
    </div>
    <div id="partidos" class="tab-pane fade">
        <div class="row">
            <div class="col-md-10">
                <p>Listado de las instancias y sus respectivos partidos</p>
            </div>
            <div class="col-md-2">
                <button class="d-none d-sm-inline-block btn btn-sm btn-danger shadow-sm" title="Agregar Instancia" data-toggle="modal" data-target="#agregar_instancia"><i class="fa fa-plus fa-sm"></i> Agregar Instancia</button>
            </div>
            <?php
            //print_r($i_p);
            if(count($i_p)>0){
                foreach ($i_p as $ii){
                 ?>
                    <div class="row">
                        <div class="col-md-11">
                            <table class="table table-bordered">
                                <tr><td colspan="5" style="font-weight: bold"><?= $ii["nombre_instancia"]; ?></td></tr>
                                <tr><td>Fecha</td><td>Local</td><td>Visita</td><td colspan="2">Resultado</td></tr>
                                <?php
                                foreach($ii["partidos"] as $e){
                                    ?>
                                    <tr>
                                        <td style="width: 15%"><?= $e["partido_fecha"]." ".$e["partido_hora"]; ?></td>
                                        <td style="width: 35%"><img src="<?= _SERVER_ . $e["foto_equipo_local"] ?>" class="img-responsive img-circle img-thumbnail" style="max-width: 50px"><?= $e["nombre_equipo_local"] ?></td>
                                        <td style="width: 34%"><img src="<?= _SERVER_ . $e["foto_equipo_visita"] ?>" class="img-responsive img-circle img-thumbnail" style="max-width: 50px"><?= $e["nombre_equipo_visita"] ?></td>
                                        <?php
                                        if($e["partido_estado"] == 0){
                                            ?>
                                            <td style="width: 15%" colspan="2"><button data-toggle="modal" data-target="#dar_resultado" onclick="dar_result_info(<?= $e['id_torneo_partido'] ?>,'<?= _SERVER_ . $e["foto_equipo_local"] ?>','<?= $e["nombre_equipo_local"] ?>','<?= _SERVER_ . $e["foto_equipo_visita"] ?>','<?= $e["nombre_equipo_visita"] ?>','<?= $e["id_equipo_local"] ?>','<?= $e["id_equipo_visita"] ?>')" class="btn btn-warning">Dar resultado</button></td>
                                        <?php
                                        }else{
                                            ?>
                                            <td style="width: 8%; font-weight: bold;font-size: 20pt;text-align: center;"><p data-toggle="tooltip" data-html="true" title="<?= $e["goleadores_local"]; ?>"><?= $e["marcador_local"]; ?></p></td>
                                            <td style="width: 8%;font-weight: bold;font-size: 20pt;text-align: center;"><p data-toggle="tooltip" data-html="true" title="<?= $e["goleadores_visita"]; ?>"><?= $e["marcador_visita"]; ?></p></td>
                                            <?php
                                        }
                                        ?>
                                    </tr>
                                    <?php
                                }
                                ?>
                                <tr><td colspan="5"><button data-toggle="modal" onclick="add_id_torneo_instancia(<?= $ii["id_instancia"]; ?>)" data-target="#agregar_partido" class="btn btn-success" href="#"><i class="fa fa-plus"></i> Agregar Partido</button></td></tr>
                            </table>
                        </div>
                    </div>
                    <?php
                }
            }else{
                ?>
                <p>Ninguna instancia registrada.</p>
            <?php
            }
            ?>
        </div>
    </div>

    <div id="posiciones" class="tab-pane fade">
        <div class="row">
            <?php
            if($torneo->torneo_tipo != 1){
                ?>
                <div class="col-md-10">
                    <p>El tipo de campeonato elegido permite solo un grupo general. </p>
                </div>
                <?php
            }
            ?>
        </div>
        <div class="row" style="word-break: break-all;">
            <?php
            if(count($posiciones)>0){
                foreach ($posiciones as $t) {
                    ?>
                    <div class="col-md-10">
                        <table class="table table-bordered">
                            <tr><td colspan="8" style="font-weight: bold"><?= $t["nombre_grupo"]; ?></td></tr>
                            <tr style="font-weight: bold"><td>Equipos</td><td>PJ</td><td>PG</td><td>PE</td><td>PP</td><td>GF</td><td>GC</td><td>TOTAL</td></tr>
                            <?php
                            foreach($t["equipos"] as $e){
                                ?>
                                <tr>
                                    <td style="width: 60%"><img src="<?= _SERVER_ . $e["equipo_foto"] ?>" class="img-responsive img-circle img-thumbnail" style="max-width: 50px"><?= $e["equipo_nombre"]; ?></td>
                                    <td style="width: 5%"><?= $e["part_j"]; ?></td>
                                    <td style="width: 5%"><?= $e["part_g"]; ?></td>
                                    <td style="width: 5%"><?= $e["part_e"]; ?></td>
                                    <td style="width: 5%"><?= $e["part_p"]; ?></td>
                                    <td style="width: 5%"><?= $e["gf"]; ?></td>
                                    <td style="width: 5%"><?= $e["gc"]; ?></td>
                                    <td style="width: 10%; font-weight: bold"><?= $e["total"]; ?></td>
                                </tr>
                                <?php
                            }
                            ?>
                        </table>
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
    <div id="estadisticas" class="tab-pane fade">
        <?php
        if(count($goleadores)>0){
            ?>
            <div class="col-md-1"></div>
            <div class="col-md-10">
                <table class="table table-bordered">
                    <tr><td colspan="8" style="font-weight: bold">Goleadores</td></tr>
                    <tr style="font-weight: bold"><td>Jugador</td><td>Equipo</td><td>Goles</td></tr>
                    <?php
                    foreach ($goleadores as $t) {
                        ?>
                        <tr>
                            <td style="width: 50%"><img src="<?= _SERVER_ . $t->user_image; ?>" class="img-responsive img-circle img-thumbnail" style="max-width: 50px"><?= $t->user_nickname;?></td>
                            <td style="width: 40%"><img src="<?= _SERVER_ . $t->equipo_foto; ?>" class="img-responsive img-circle img-thumbnail" style="max-width: 50px"><?= $t->equipo_nombre;?></td>
                            <td style="width: 10%; font-weight: bold;font-size: 20pt;text-align: center"><?= $t->conteo; ?></td>
                        </tr>
                    <?php
                    }
                    ?>
                </table>
            </div>
                <?php
        }else{
            ?>
            <p>Aún no hay goleadores.</p>
            <?php
        }
        ?>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="agregar_equipo" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="max-width: fit-content">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" style="text-align: center; color: green;font-weight: bold">Agregar Equipo</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" id="id_torneo_grupo">
                <table id="example2">
                    <thead>
                    <th>Foto</th>
                    <th>Equipo</th>
                    <th>Capitán</th>
                    <th>Valoración</th>
                    <th>Agregar</th>
                    </thead>
                    <tbody>
                    <?php
                    foreach($equipos_en_torneo_not as $en){
                        ?>
                        <tr>
                            <td><img src="<?= _SERVER_ . $en->equipo_foto; ?>" class="img-thumbnail img-circle img-responsive" style="max-width: 200px;"></td>
                            <td><?= $en->equipo_nombre; ?></td>
                            <td><?= $en->user_nickname; ?></td>
                            <td><?= $en->equipo_valoracion; ?></td>
                            <td><button class="btn btn-success" onclick="add_team(<?= $en->equipo_id; ?>)" style="border-radius: 50%"><i class="fa fa-check fa-2x"></i></button></td>
                        </tr>
                    <?php
                    }
                    ?>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="agregar_grupo" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" style="text-align: center; color: green;font-weight: bold">Agregar Grupo</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="box box-primary">
                    <div class="box-body">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label for="nombre_grupo">Nombre del Grupo</label>
                                    <input type="text" id="nombre_grupo" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <button class="btn btn-success" onclick="add_group()"><i class="fa fa-save"></i> Guardar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="agregar_instancia" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" style="text-align: center; color: green;font-weight: bold">Agregar Instancia</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="box box-primary">
                    <div class="box-body">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="instancia_tipo">Tipo de Instancia</label>
                                    <select id="instancia_tipo" class="form-control">
                                        <option value="1">Fase de Grupos</option>
                                        <option value="2">Eliminatoria</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="instancia_nombre">Nombre de la Instancia</label>
                                    <input type="text" id="instancia_nombre" class="form-control" placeholder="Ejemplo: Cuartos de Final, Fecha 01">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <button class="btn btn-success" onclick="add_instance()"><i class="fa fa-save"></i> Guardar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="agregar_partido" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" style="text-align: center; color: green;font-weight: bold">Agregar Partido</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="box box-primary">
                    <div class="box-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="id_equipo_local">Elige Equipo Local</label>
                                    <input type="hidden" id="id_torneo_instancia">
                                    <input type="hidden" id="id_equipo_local">
                                    <div id="equipo_local"></div>
                                    <button id="btn-equipo-local" data-toggle="modal" data-target="#select_team">Seleccione</button>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="id_equipo_visita">Elige Equipo Visita</label>
                                    <input type="hidden" id="id_equipo_visita">
                                    <div id="equipo_visita"></div>
                                    <button id="btn-equipo-visita" data-toggle="modal" data-target="#select_team2">Seleccione</button>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="partido_fecha">Fecha del partido</label>
                                    <input type="date" min="<?= date('Y-m-d'); ?>" id="partido_fecha" class="form-control">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="partido_hora">Hora del partido</label>
                                    <input type="time" id="partido_hora" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <button class="btn btn-success" onclick="add_match()"><i class="fa fa-save"></i> Guardar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="select_team" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="max-width: fit-content">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" style="text-align: center; color: green;font-weight: bold">Seleccione Equipo</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" id="id_torneo_grupo">
                <table id="example3">
                    <thead>
                    <th>Foto</th>
                    <th>Equipo</th>
                    <th>Capitán</th>
                    <th>Valoración</th>
                    <th>Agregar</th>
                    </thead>
                    <tbody>
                    <?php
                    foreach($equipos2 as $en){
                        ?>
                        <tr>
                            <td><img src="<?= _SERVER_ . $en->equipo_foto; ?>" class="img-thumbnail img-circle img-responsive" style="max-width: 200px;"></td>
                            <td><?= $en->equipo_nombre; ?></td>
                            <td><?= $en->user_nickname; ?></td>
                            <td><?= $en->equipo_valoracion; ?></td>
                            <td><button class="btn btn-success" data-dismiss="modal" onclick="add_local_team(<?= $en->equipo_id; ?>,'<?= _SERVER_ . $en->equipo_foto; ?>','<?= $en->equipo_nombre; ?>')" style="border-radius: 50%"><i class="fa fa-check fa-2x"></i></button></td>
                        </tr>
                        <?php
                    }
                    ?>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="select_team2" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="max-width: fit-content">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" style="text-align: center; color: green;font-weight: bold">Seleccione Equipo</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" id="id_torneo_grupo">
                <table id="example3">
                    <thead>
                    <th>Foto</th>
                    <th>Equipo</th>
                    <th>Capitán</th>
                    <th>Valoración</th>
                    <th>Agregar</th>
                    </thead>
                    <tbody>
                    <?php
                    foreach($equipos2 as $en){
                        ?>
                        <tr>
                            <td><img src="<?= _SERVER_ . $en->equipo_foto; ?>" class="img-thumbnail img-circle img-responsive" style="max-width: 200px;"></td>
                            <td><?= $en->equipo_nombre; ?></td>
                            <td><?= $en->user_nickname; ?></td>
                            <td><?= $en->equipo_valoracion; ?></td>
                            <td><button class="btn btn-success" data-dismiss="modal" onclick="add_visit_team(<?= $en->equipo_id; ?>,'<?= _SERVER_ . $en->equipo_foto; ?>','<?= $en->equipo_nombre; ?>')" style="border-radius: 50%"><i class="fa fa-check fa-2x"></i></button></td>
                        </tr>
                        <?php
                    }
                    ?>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="dar_resultado" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" style="text-align: center; color: green;font-weight: bold">Dar Resultado</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="box box-primary">
                    <div class="box-body">
                        <div class="row">
                            <div class="col-md-2"></div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <input type="hidden" id="id_torneo_partido">
                                    <div id="data_local"></div>
                                    <center><input type="number" onblur="add_goleador('l')" value="0" min="0" max="99" id="marcador_local" style="text-align: center;max-width: 75px; font-weight: bold;font-size: 22pt;" class="form-control"></center>
                                    <br><div id="data_gol_local"></div>
                                </div>
                            </div>
                            <div class="col-md-1"><br><br><br><h2>VS</h2></div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <div id="data_visita"></div>
                                    <center><input type="number" onblur="add_goleador('v')" id="marcador_visita" min="0" max="99" value="0" style="text-align: center;max-width: 75px; font-weight: bold;font-size: 22pt;" class="form-control"></center>
                                    <br><div id="data_gol_visita"></div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <button class="btn btn-success" onclick="add_result()"><i class="fa fa-save"></i> Guardar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="add_goleador" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="max-width: max-content">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" style="text-align: center; color: green;font-weight: bold">Elegir autor del gol</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="box box-primary">
                    <div class="box-body">
                        <div class="row" id="jugadores_">

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script>
    function add_equipo(id) {
        $("#id_torneo_grupo").val(id);
    }
    function add_goleador(ind) {
        var lista = "";
        var mar = 0;
        if(ind=="l"){
            mar = $("#marcador_local").val();
            if(!isNaN(mar) && mar>0){
                for(var i=1;i<=mar;i++){
                    lista += "<div id='goleadorL_"+i+"'></div><button data-toggle='modal' id='btn_goleadorL_"+i+"' data-target='#add_goleador' onclick='buscar_jugadores(0,"+i+")' class='btn-info'>Seleccione autor del gol N° "+i+"</button>";
                }
            }
            $("#data_gol_local").html(lista);
        }else{
            mar = $("#marcador_visita").val();
            if(!isNaN(mar) && mar>0){
                for(var i=1;i<=mar;i++){
                    lista += "<div id='goleadorV_"+i+"'></div><button id='btn_goleadorV_"+i+"' data-toggle='modal' data-target='#add_goleador' onclick='buscar_jugadores(1,"+i+")' class='btn-info'>Seleccione autor del gol N° "+i+"</button>";
                }
            }
            $("#data_gol_visita").html(lista);
        }
    }
    function add_id_torneo_instancia(id) {
        $("#id_torneo_instancia").val(id);
    }
    function dar_result_info(id,il,nl,iv,nv,id1,id2){
        $("#id_torneo_partido").val(id);
        $("#data_local").html("<img src='"+il+"' class='img-circle img-responsive img-thumbnail'><p style='text-align: center;font-weight: bold'> "+nl+"</p><input type='hidden' id='id_equipo_local1' value='"+id1+"'>");
        $("#data_visita").html("<img src='"+iv+"' class='img-circle img-responsive img-thumbnail'><p style='text-align: center;font-weight: bold'> "+nv+"</p><input type='hidden' id='id_equipo_visita1' value='"+id2+"'>");

    }
    function add_local_team(id,foto,nombre) {
       if($("#id_equipo_visita").val() == id){
           alertify.error('No puedes seleccionar el mismo equipo');
       }else{
           $("#id_equipo_local").val(id);
           $("#equipo_local").html("<img src='"+foto+"' class='img-circle img-responsive img-thumbnail'><p style='text-align: center;font-weight: bold'> "+nombre+"</p>");
           $("#btn-equipo-local").html('Cambiar selección');
       }
    }
    function add_visit_team(id,foto,nombre) {
        if($("#id_equipo_local").val() == id){
            alertify.error('No puedes seleccionar el mismo equipo');
        }else {
            $("#id_equipo_visita").val(id);
            $("#equipo_visita").html("<img src='" + foto + "' class='img-circle img-responsive img-thumbnail'><p style='text-align: center;font-weight: bold'>  " + nombre + "</p>");
            $("#btn-equipo-visita").html('Cambiar selección');
        }
    }
    const $seleccionArchivos = document.querySelector("#imagen"), $imagenPrevisualizacion = document.querySelector("#imagenPrevisualizacion");
    $('input[type=file]').change(function(){
        var filename = $(this).val().split('\\').pop();
        if(filename!=""){
            $("#lbl_imagen").html(filename);
        }else{
            $("#lbl_imagen").html("<i class=\"fa fa-image\"></i> Subir Foto");
        }
        const archivos = $seleccionArchivos.files;
        if (!archivos || !archivos.length) {
            $imagenPrevisualizacion.src = "<?= _SERVER_?>media/uploadimage.png";
            return;
        }
        $imagenPrevisualizacion.src = URL.createObjectURL(archivos[0]);
    });
    function clean_text(id) {
        var text = $("#comment_"+id).html().trim();
        if(text=="Escribe un comentario"){
            $("#comment_"+id).html('');
        }else if(text==""){
            $("#comment_"+id).html('Escribe un comentario');
        }
    }
    function dar_like(id) {
        cadena = "usuario_id= "+<?= $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_); ?> +"&publicacion_id="+id;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Foro/dar_like",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].resultado) {
                    case 1:
                        $("#btn_like_"+id).css("background","#30a5ff");
                        $("#btn_like_"+id).css("color","#fff");
                        $("#btn_like_"+id).css("border-color","#30a5ff");
                        $("#btn_like_"+id).attr("onclick","quitar_like("+id+")");
                        var span_like = $("#span_like_"+id).html() * 1 + 1;
                        $("#span_like_"+id).html(span_like);
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    function quitar_like(id) {
        cadena = "usuario_id= "+<?= $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_); ?> +"&publicacion_id="+id;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Foro/quitar_like",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].resultado) {
                    case 1:
                        $("#btn_like_"+id).css("background","#fff");
                        $("#btn_like_"+id).css("color","#000");
                        $("#btn_like_"+id).css("border-color","#fff");
                        $("#btn_like_"+id).attr("onclick","dar_like("+id+")");
                        var span_like = $("#span_like_"+id).html() * 1 - 1;
                        $("#span_like_"+id).html(span_like);
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    function buscar_jugadores(ind,i) {
        var id = $("#id_torneo_partido").val();
        cadena = "id= "+id+"&ind="+ind+"&i="+i;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Torneo/buscar_jugadores_por_id_partido",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                $("#jugadores_").html(r);
                setDataTable('example4');
            }
        });
    }
    function add_result() {
        var id_torneo_partido = $("#id_torneo_partido").val();
        var marcador_local = $("#marcador_local").val();
        var marcador_visita = $("#marcador_visita").val();
        var cadena = "id_torneo_partido= "+id_torneo_partido +"&marcador_local="+marcador_local+"&marcador_visita="+marcador_visita;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Torneo/dar_resultado_partido_torneo",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].valor) {
                    case 1:
                        if(marcador_local>0){
                            var id_equipo = $("#id_equipo_local1").val();
                            for(var j =1;j<=marcador_local;j++){
                                var id_usuario = $("#hid_golL_"+j).val();
                                var cadena2 = "id_torneo_partido= "+id_torneo_partido +"&id_usuario="+id_usuario+"&id_equipo="+id_equipo;
                                $.ajax({
                                    type:"POST",
                                    url: urlweb + "api/Torneo/registrar_goleador_partido_torneo",
                                    dataType: 'json',
                                    data: cadena2,
                                    success:function (r) {

                                    }
                                });
                            }
                        }
                        if(marcador_visita>0){
                            var id_equipo2 = $("#id_equipo_visita1").val();
                            for(var j2 =1;j2<=marcador_visita;j2++){
                                var id_usuario2 = $("#hid_golV_"+j2).val();
                                var cadena22 = "id_torneo_partido= "+id_torneo_partido +"&id_usuario="+id_usuario2+"&id_equipo="+id_equipo2;
                                $.ajax({
                                    type:"POST",
                                    url: urlweb + "api/Torneo/registrar_goleador_partido_torneo",
                                    dataType: 'json',
                                    data: cadena22,
                                    success:function (r) {

                                    }
                                });
                            }
                        }
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Torneo/ver_torneo/'+ <?= $torneo->torneo_id; ?>;
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    function save_goleador(id_user,n,i,ind) {
        if(ind==0){
            $("#goleadorL_"+i).html("<input type='hidden' id='hid_golL_"+i+"' value='"+id_user+"'>Gol N° "+i+": <b>"+n+"</b>");
            $("#btn_goleadorL_"+i).html("<i class='fa fa-pencil'></i>");
        }else{
            $("#goleadorV_"+i).html("<input type='hidden' id='hid_golV_"+i+"' value='"+id_user+"'>Gol N° "+i+": <b>"+n+"</b>");
            $("#btn_goleadorV_"+i).html("<i class='fa fa-pencil'></i>");
        }
    }
    function add_team(id) {
        var id_torneo_grupo = $("#id_torneo_grupo").val();
        cadena = "id_equipo= "+ id +"&id_torneo_grupo="+id_torneo_grupo;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Torneo/registrar_equipo_en_torneo",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].valor) {
                    case 1:
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Torneo/ver_torneo/'+ <?= $torneo->torneo_id; ?>;
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    function add_group() {
        var nombre_grupo = $("#nombre_grupo").val();
        cadena = "grupo_nombre= "+ nombre_grupo +"&id_torneo="+<?= $torneo->torneo_id; ?>;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Torneo/registrar_grupo",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].valor) {
                    case 1:
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Torneo/ver_torneo/'+ <?= $torneo->torneo_id; ?>;
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    function add_instance() {
        var instancia_nombre = $("#instancia_nombre").val();
        var instancia_tipo = $("#instancia_tipo").val();
        cadena = "instancia_nombre= "+ instancia_nombre +"&instancia_tipo="+instancia_tipo+"&id_torneo="+<?= $torneo->torneo_id; ?>;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Torneo/registrar_instancia",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].valor) {
                    case 1:
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Torneo/ver_torneo/'+ <?= $torneo->torneo_id; ?>;
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    function add_match() {
        var id_torneo_instancia = $("#id_torneo_instancia").val();
        var id_equipo_local = $("#id_equipo_local").val();
        var id_equipo_visita = $("#id_equipo_visita").val();
        var partido_fecha = $("#partido_fecha").val();
        var partido_hora = $("#partido_hora").val();
        cadena = "id_equipo_local= "+ id_equipo_local +"&id_equipo_visita="+id_equipo_visita+"&fecha="+partido_fecha+"&hora="+partido_hora+"&id_torneo_instancia="+id_torneo_instancia;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Torneo/registrar_partido",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].valor) {
                    case 1:
                        alertify.success("¡Guardado!");
                        location.href = urlweb +  'Torneo/ver_torneo/'+ <?= $torneo->torneo_id; ?>;
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    function comentar() {
        var id = $("#id_comment").val();
        var comentario = $("#comment_"+id).html();
        var cadena = "usuario_id= "+<?= $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_); ?> +"&publicacion_id="+id+"&comentario="+comentario;
        $.ajax({
            type:"POST",
            url: urlweb + "api/Foro/registrar_comentario",
            dataType: 'json',
            data: cadena,
            success:function (r) {
                switch (r.results[0].resultado) {
                    case 1:
                        $("#comments_"+id).html(r.results[0].lista);
                        $("#comment_"+id).html('');

                        var span_comment = $("#span_comment_"+id).html() * 1 + 1;
                        $("#span_comment_"+id).html(span_comment);
                        break;
                    case 2:
                        alertify.error("Ha ocurrido un error");
                        break;
                    default:
                        alertify.error("ERROR DESCONOCIDO");
                }
            }
        });
    }
    $('a[href*="#"]').on('click', function(e) {
        e.preventDefault();
        var el = $($(this).attr('href'));
        var elOffset = el.offset().top;
        var elHeight = el.height();
        var windowHeight = $(window).height();
        var offset;
        if (elHeight < windowHeight) {
            offset = elOffset - ((windowHeight / 2) - (elHeight / 2));
        }
        else {
            offset = elOffset;
        }
        $('html, body').animate(
            {
                scrollTop: offset,
            },
            500,
            'linear'
        );
    });
    function btn_comentar(id) {
        $("#comment_"+id).focus();
        clean_text(id);
    }
    function verificar(id){
        $("#id_comment").val(id);
    }
    $('.comentando').keypress(function(e){
        if(e.which === 13){
            e.preventDefault();
            comentar();
        }
    });
    $("#fupFormForo").on('submit', function(e){
        e.preventDefault();
        var valor = "correcto";
        var titulo = $('#titulo').val();
        var descripcion = $('#descripcion').val();
        if(titulo == ""){
            alertify.error('Ingrese un título');
            $('#titulo').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#titulo').css('border','');
        }if(descripcion == ""){
            $('#descripcion').css('border','solid red');
            valor = "incorrecto";
        } else {
            $('#descripcion').css('border','');
        }
        if (valor == "correcto"){
            $.ajax({
                type:"POST",
                url: urlweb + "api/Foro/registrar",
                dataType: 'json',
                data: new FormData(this),
                contentType: false,
                cache: false,
                processData:false,
                beforeSend: function(){
                    $('.submitBtn').attr("disabled","disabled");
                    $('#fupFormForo').css("opacity",".5");
                },
                success:function (r) {
                    switch (r) {
                        case 1:
                            alertify.success("¡Guardado!");
                            location.href = urlweb +  'Torneo/ver_torneo/'+ <?= $torneo->torneo_id; ?>;
                            break;
                        case 2:
                            alertify.error("Ha ocurrido un error");
                            break;
                        case 6:
                            alertify.error("Fallo en la integridad de los datos enviados");
                            break;
                        default:
                            alertify.error("ERROR DESCONOCIDO");
                    }
                    $('#fupFormForo').css("opacity","");
                    $(".submitBtn").removeAttr("disabled");
                }
            });
        }
    });
</script>
</body>
</html>