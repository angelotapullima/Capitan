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
                            $i__=0;
                            foreach ($torneos as $t) {
                                if($i__%4==0){
                                    echo "</div><div class='row'>";
                                }
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
                                $i__++;
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
                            $i__=0;
                            foreach ($mis_torneos as $t) {
                                if($i__%4==0){
                                    echo "</div><div class='row'>";
                                }
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
                                $i__++;
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
                            $i__ = 0;
                            foreach ($mis_equipos as $t) {
                                $url = _SERVER_ . "Torneo/retar/".$t->equipo_id;
                                ($t->usuario_id == $usuario_id)? $retar="":$retar = "<a href='$url' class='btn btn-sm btn-success'>Retar</a href=''>";
                                if($i__%4==0){
                                    echo "</div><div class='row'>";
                                }
                                ?>
                                <div class="col-md-3" style="word-break: break-all;">
                                    <a href="<?= _SERVER_ ?>Torneo/ver_equipo/<?= $t->equipo_id; ?>" style="text-decoration: none">
                                    <img src="<?= _SERVER_ . $t->equipo_foto; ?>" alt="Equipo" style="max-height: 150px; border-radius: 25px;" class=" img-thumbnail img-responsive">
                                    <h4><?= $t->equipo_nombre; ?></h4>
                                    <i class="fa fa-star"></i> <?= $t->equipo_valoracion . "    ".$retar; ?>
                                    </a>
                                </div>
                                <?php
                                $i__++;
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
                                <h3>Otros equipos</h3>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <?php
                        if(count($equipos2)>0){
                            $i__ = 0;
                            foreach ($equipos2 as $t) {
                                $url = _SERVER_ . "Torneo/retar/".$t->equipo_id;
                                ($t->usuario_id == $usuario_id)? $retar="":$retar = "<a href='$url' class='btn btn-sm btn-success'>Retar</a>";
                                if($i__%4==0){
                                    echo "</div><div class='row'>";
                                }
                                ?>
                                <div class="col-md-3" style="word-break: break-all;">
                                    <a href="<?= _SERVER_ ?>Torneo/ver_equipo/<?= $t->equipo_id; ?>" style="text-decoration: none">
                                    <img src="<?= _SERVER_ . $t->equipo_foto; ?>" alt="Equipo" style="max-height: 150px; border-radius: 25px;" class=" img-thumbnail img-responsive">
                                    <h4><?= $t->equipo_nombre; ?></h4>
                                    <i class="fa fa-star"></i> <?= $t->equipo_valoracion. "    ".$retar; ?>
                                    </a>
                                </div>
                                <?php
                                $i__++;
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
        <div class="row">
            <div class="col-md-6">
                <h3>Es necesario descargar la app para poder chatear.</h3>
                <?php
                /*for ($i=0;$i<count($chats);$i++) {
                    $mensaje = $this->user->listar_ultimo_mensaje_de_chat($chats[$i]->chat_id);
                    if($id_usuario==$chats[$i]->id_usuario_1){
                        $datos_u = $this->user->list($chats[$i]->id_usuario_1);
                        $datos_ = $this->user->list($chats[$i]->id_usuario_2);
                    }else{
                        $datos_u = $this->user->list($chats[$i]->id_usuario_2);
                        $datos_ = $this->user->list($chats[$i]->id_usuario_1);
                    }
                    $fecha = explode(' ',$mensaje->detalle_chat_fecha);*/
                    ?>
                    <!--<div class="row" style="background: #fff;border-bottom: 1px solid lightgrey;padding-top: 5px;">
                        <a style="cursor: pointer" onclick="mostrar_chat(<?= $chats[$i]->chat_id; ?>,'<?= $chats[$i]->usuario_2; ?>','<?= _SERVER_ . $datos_->user_image; ?>')">
                        <div class="col-md-2">
                            <img alt="user" src="<?= _SERVER_ . $datos_->user_image; ?>" class="img-responsive img-circle" width="50">
                        </div>
                        <div class="col-md-10">
                            <p><b style="font-size: large"><?= $chats[$i]->usuario_2; ?></b><span style="float: right;"><i class="fa fa-clock-o"></i> <?= $this->validate->time_between_dates($fecha[0]." ".$fecha[1],"hoy"); ?></span></p>
                            <p><?= $mensaje->detalle_chat_mensaje; ?></p>
                        </div>
                        </a>
                    </div>-->
                <?php
                //}
                ?>
            </div>
        </div>
    </div>
    <div id="estadisticas" class="tab-pane fade">
        <div class="row">
            <div class="col-md-10">
                <table class="table table-responsive table-bordered">
                    <thead>
                        <th>#</th>
                        <th>Equipo</th>
                        <th>Puntaje Acumulado</th>
                        <th>Puntaje Semanal</th>
                        <th>Retos Enviados</th>
                        <th>Retos Recibidos</th>
                        <th>Retos Ganados</th>
                        <th>Retos Empatados</th>
                        <th>Retos Perdidos</th>
                        <th>Torneos</th>
                    </thead>
                    <tbody>
                    <?php
                    $iii=1;
                    foreach ($estadisticas as $e){
                        ?>
                        <tr>
                            <td><?= $iii; ?></td>
                            <td><img src="<?= _SERVER_.$e->equipo_foto ?>" class="img-circle img-responsive" width="70"> <?= $e->equipo_nombre; ?></td>
                            <td><?= $e->puntaje_acumulado ?></td>
                            <td><?= $e->puntaje_semanal ?></td>
                            <td><?= $e->retos_enviados ?></td>
                            <td><?= $e->retos_recibidos ?></td>
                            <td><?= $e->retos_ganados ?></td>
                            <td><?= $e->retos_empatados ?></td>
                            <td><?= $e->retos_perdidos ?></td>
                            <td><?= $e->torneos ?></td>
                        </tr>
                    <?php
                        $iii++;
                    }
                    ?>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script>
  /*  function mostrar_chat(id,nombre,foto) {
        $("#detalle_chat").html('');
        $("#ci").val(id);
        $("#cn").val(nombre);
        $("#detalle_chat_img").html("<img src='"+foto+"' width='50' class='img-responsive img-circle' '>");
        $("#detalle_chat_us").html("<b>"+nombre+"</b>");
        $("#cf").val(foto);
        $.ajax({
            type:"POST",
            url: urlweb + "api/User/listar_mensajes_por_chat",
            dataType: 'json',
            data: "id_chat="+id,
            success:function (r) {
                var lista = "";
                var id_u = <?= $this->crypt->decrypt($_SESSION['id_user'],_PASS_);?>;
                for(var i=0;i<r.results.length;i++){
                    if(r.results[i].id_usuario==id_u){
                        lista+="<div class='row' style='margin-bottom: 10px'><div class='col-md-4'></div><div class='col-md-8' style='padding: 10px;background: #fff;border-radius: 10px;'> "+r.results[i].mensaje+"<br><span style='font-size: 8pt;'>"+r.results[i].fecha+" "+r.results[i].hora+"</span></div></div>";
                    }else{
                        lista+="<div class='row' style='margin-bottom: 10px'><div class='col-md-8'  style='padding: 10px;background: #fff;border-radius: 10px;'> "+r.results[i].mensaje+"<br><span style='font-size: 8pt;'>"+r.results[i].fecha+" "+r.results[i].hora+"</span></div><div class='col-md-4'></div></div>";
                    }
                }
                $("#detalle_chat").html(lista);
            }
        });
    }
    function clean_text() {
        var text = $("#comment_").html().trim();
        if(text=="Escribe un mensaje"){
            $("#comment_").html('');
        }else if(text==""){
            $("#comment_").html('Escribe un mensaje');
        }
    }
        $('.comentando').keypress(function(e){
            if(e.which === 13){
                e.preventDefault();
                var id = $("#ci").val();
                var comentario = $("#comment_").html();
                var cadena = "id_usuario= "+<?= $usuario_id; ?> +"&id_chat="+id+"&mensaje="+comentario;
                console.log('carajo');
                $.ajax({
                    type:"POST",
                    url: urlweb + "api/User/enviar_mensaje",
                    dataType: 'json',
                    data: cadena,
                    success:function (re) {
                        console.log(re);
                        //mostrar_chat(iid,inn,iff);
                        /*alert(re.results[0].valor);
                        if (re.results[0].valor==1) {
                            var iid=$("#ci").val();
                            var inn=$("#cn").val();
                            var iff=$("#cf").val();
                            mostrar_chat(iid,inn,iff);
                        }
                    }
                });
            }
        });

        */
</script>
</body>
</html>
