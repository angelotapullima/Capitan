<?php
?>
<div class="row">
    <!-- left column -->
    <div class="col-md-1"></div>
    <div class="col-md-6">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Recargar mi cuenta</h4>
            </div>
            <?php
            if(isset($recarga_pendiente->id_pagocip)){
                ?>
                <div class="box-body">
                    <div class="form-group">
                        <label class="col-form-label">Usted ya tiene una recarga pendiente de pago con el código: </label>
                        <label style="color: red"><?= $recarga_pendiente->pagocip_codigo; ?></label>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success" id="btn_recargar_mi_cuenta" onclick="javascript:history.back()"> Volver</button>
                    </div>
                </div>
                <?php
            }else{
                ?>
            <div class="box-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-form-label">Nro de cuenta</label>
                            <input class="form-control" type="text" readonly value="<?= $datos_cuenta->cuenta_codigo ;?>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-form-label">Usuario</label>
                            <input class="form-control" type="text" readonly value="<?= $datos_cuenta->person_name.' '.$datos_cuenta->person_surname;?>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-form-label">Saldo Actual</label>
                            <input class="form-control" type="text" readonly value="<?= $datos_cuenta->cuenta_saldo;?>">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-form-label">Monto de recarga</label>
                            <input class="form-control" type="text" id="monto"  placeholder="(min 5, max 1000)">
                        </div>
                    </div>
                    <div class="col-md-12">
                        <label>Método de pago</label><br>
                        <input type="radio" name="tipo" value="Agente" id="agente">
                        <label for="agente">Pagar a través de un Agente <img src="<?= _SERVER_ ?>media/pelota.jpg" width="50"></label><br>
                        <input type="radio" name="tipo" value="BCP" id="bcp">
                        <label for="bcp">Pagar por banca móvil BCP <img src="<?= _SERVER_ ?>media/pelota.jpg" width="50"></label><br>
                        <input type="radio" name="tipo" value="Interbank" id="interbank">
                        <label for="interbank">Pagar por banca móvil Interbank <img src="<?= _SERVER_ ?>media/pelota.jpg" width="50"></label><br>
                        <input type="radio" name="tipo" value="Bbva" id="bbva">
                        <label for="bbva">Pagar por banca móvil BBVA <img src="<?= _SERVER_ ?>media/pelota.jpg" width="50"></label>
                    </div>
                </div>
                <div class="form-group">
                    <button class="btn btn-success" id="btn_recargar_mi_cuenta" onclick="recargar_mi_cuenta()"> Recargar mi cuenta</button>
                </div>
            </div>
        </div>
                <?php
            }
            ?>
        </div>
        <div id="codigocip" class="col-md-5" style="background: white; border-radius: 10px;">

        </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>cuenta.js"></script>
<script>
    $(document).ready( function () {
        $('#codigocip').hide();
    } );
</script>
</body>
</html>
