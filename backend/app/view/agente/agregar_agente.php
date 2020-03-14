<?php
?>
<div class="row">
    <!-- left column -->
    <div class="col-md-6">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Agregar Agente</h4>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <div>
                <div class="box-body">
                    <div class="form-group">
                        <label class="col-form-label">Seleccione Cuenta</label>
                        <select id="agente_cuentae" class="form-control">
                            <option value="">--Seleccione--</option>
                            <?php
                            foreach ($cuentaes as $c){
                                ?>
                                <option value="<?= $c->id_cuenta_empresa; ?>"><?= $c->empresa_nombre." ".$c->cuentae_codigo; ?></option>
                            <?php
                            }
                            ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Nombre Agente</label>
                        <input class="form-control" type="text" id="agente_name"  placeholder="Ingrese Nombre del Agente...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Direccion</label>
                        <input class="form-control" type="text" id="agente_direccion"  placeholder="Ingrese Direccion del Agente...">
                    </div>
                    <div class="form-group">
                        <label class="col-form-label">Coordenadas</label>
                        <input class="form-control" type="text" id="agente_coord_x">
                        <input class="form-control" type="text" id="agente_coord_y">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success" onclick="save_agente()"> Guardar Agente</button>
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
<script src="<?php echo _SERVER_ . _JS_;?>agente.js"></script>
</body>
</html>

