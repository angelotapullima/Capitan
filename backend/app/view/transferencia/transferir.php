<?php
?>
<div class="row">
    <!-- left column -->
    <div class="col-md-1"></div>
    <div class="col-md-4">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Realizar Transferencia</h4>
            </div>
            <div class="box-body">
                <div class="form-group">
                    <label class="col-form-label">Número de Cuenta destinatario</label>
                    <input class="form-control" id="codigo" type="text" placeholder="Ingrese el número de Cuenta">
                </div>
                <div class="form-group">
                    <button class="btn btn-success" id="btn_buscar_codigo" onclick="buscar_cuenta()"> Buscar Cuenta</button>
                </div>
            </div>
        </div>
    </div>
    <div id="pagar_ya" class="col-md-5" style="background: white; border-radius: 10px;">
        <div id="datos">

        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="col-form-label">Saldo actual</label>
                    <div class="row">
                        <div class="col-md-10">
                            <input class="form-control" type="text" id="saldo_actual" readonly value="<?= $datos_cuenta->cuenta_saldo; ?>">
                        </div>
                    </div>
                    <input class="form-control" type="hidden" value="" id="bull">
                </div>
                <div class="form-group">
                    <label class="col-form-label">Monto de la transferencia</label>
                    <div class="row">
                        <div class="col-md-10">
                            <input class="form-control" type="text" id="monto">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-1"></div>
            <div class="col-md-4">
                <div class="form-group">
                    <br><button id="btn-pagar" class='btn btn-lg btn-primary' onclick="save_transferencia()">Transferir</button>
                </div>
            </div>
        </div>
    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>transferencia.js"></script>
<script>
    $(document).ready( function () {
        $('#pagar_ya').hide();
    } );
</script>
</body>
</html>
