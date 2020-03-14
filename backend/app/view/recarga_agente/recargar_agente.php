<?php
?>
<div class="row">
    <!-- left column -->
    <div class="col-md-6">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Recargar Agente</h4>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <div>
                <div class="box-body">
                    <div class="form-group">
                        <label class="col-form-label">Seleccione Cuenta</label>
                        <select id="id_emisor" class="form-control">
                            <option value="">--Seleccione--</option>
                            <?php
                            foreach ($cuentaes as $c){
                                if($c->id_user==$this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_)){
                                    ?>
                                    <option value="<?= $c->id_cuenta_empresa; ?>"><?= $c->empresa_nombre." ".$c->cuentae_codigo; ?></option>
                                    <?php
                                }
                            }
                            ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Seleccione Agente</label>
                        <select id="id_agente" class="form-control">
                            <option value="">--Seleccione--</option>
                            <?php
                            foreach ($agentes as $c){
                                ?>
                                <option value="<?= $c->id_cuenta_empresa; ?>"><?= $c->agente_nombre; ?></option>
                                <?php
                            }
                            ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Monto </label>
                        <input class="form-control" type="text" id="monto"  placeholder="0.00">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Nro de Operacion </label>
                        <input class="form-control" type="text" id="nro_operacion"  placeholder="Ingrese el nro de voucher">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Concepto </label>
                        <input class="form-control" type="text" id="concepto"  placeholder="Ingrese el concepto">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success" onclick="recargar_agente()"> Recargar agente</button>
                    </div>
                </div>
                <!-- /.box-body -->
            </div>
        </div>
        <!-- /.box -->
    </div>
</div>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>recargaagente.js"></script>
</body>
</html>

