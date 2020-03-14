<?php
?>
<div class="row">
    <!-- left column -->
    <div class="col-md-1"></div>
    <div class="col-md-4">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Retirar CIP</h4>
            </div>
            <div class="box-body">
                <div class="form-group">
                    <label class="col-form-label">Código CIP</label>
                    <input class="form-control" id="codigo" type="text" placeholder="Ingresa el código CIP">
                </div>
                <div class="form-group">
                    <button class="btn btn-success" id="btn_buscar_codigo" onclick="buscar_codigo_retiro()"> Buscar Código</button>
                </div>
            </div>
        </div>
    </div>
    <div id="pagar_ya" class="col-md-5" style="background: white; border-radius: 10px;">
        <div id="datos">

        </div>
        <div class="form-group">
            <label class="col-form-label">Seleccione Agente</label>
            <select id="id_agente" onchange="datos_agente()" class="form-control">
                <option value="">--Seleccione--</option>
                <?php
                foreach ($agentes as $c){
                    ?>
                    <option value="<?= $c->id_agente; ?>"><?= $c->agente_nombre; ?></option>
                    <?php
                }
                ?>
            </select>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="col-form-label">Saldo del agente</label>
                    <div class="row">
                        <div class="col-md-10">
                            <input class="form-control" type="text" id="saldo_agente" readonly value="">
                        </div>
                    </div>
                    <input class="form-control" type="hidden" value="" id="bull">
                    <input class="form-control" type="hidden" value="" id="bull2">
                </div>
            </div>
            <div class="col-md-1"></div>
            <div class="col-md-4">
                <div class="form-group">
                    <br><button id="btn-pagar" class='btn btn-lg btn-primary' disabled onclick="save_detalle_pagocip()">Retirar</button>
                </div>
            </div>
        </div>
    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>pagocip.js"></script>
<script>
    $(document).ready( function () {
        $('#pagar_ya').hide();
    } );
</script>
</body>
</html>
