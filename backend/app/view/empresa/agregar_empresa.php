<form enctype="multipart/form-data" id="fupForm">
<div class="row">
    <div class="col-md-6">
        <div class="box box-primary">
            <div class="box-header with-border">
                <h4 class="header-title">Agregar Empresa</h4>
            </div>
            <div class="box-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="usuario_id" class="col-form-label">Responsable</label>
                            <select id="usuario_id" name="usuario_id" class="form-control">
                                <option value="">--Seleccione--</option>
                                <?php
                                foreach ($usuarios as $u){
                                    echo "<option value='".$u->id_user."'>".$u->person_name." ".$u->person_surname."</option>";
                                }
                                ?>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="ubigeo_id" class="col-form-label">Ciudad</label>
                            <select id="ubigeo_id" name="ubigeo_id" class="form-control">
                                <option value="1">Iquitos</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="empresa_name" class="col-form-label">Nombre Empresa</label>
                    <input class="form-control" type="text" name="empresa_name" id="empresa_name" placeholder="Ingrese Nombre de la Empresa">
                </div>
                <div class="form-group">
                    <label for="descripcion" class="col-form-label">Descripción</label>
                    <textarea class="form-control" name="descripcion" id="descripcion" rows="4"></textarea>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="telefono_1" class="col-form-label">Teléfono 01</label>
                            <input class="form-control" type="text" name="telefono_1" id="telefono_1" placeholder="Ingrese el teléfono N° 1">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="telefono_2" class="col-form-label">Teléfono 02</label>
                            <input class="form-control" type="text" name="telefono_2" id="telefono_2" placeholder="Ingrese el teléfono N° 2">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="horario_1" class="col-form-label">Horario de Atención (Lunes a Sábado)</label>
                            <input class="form-control" type="text" id="horario_1" name="horario_1" placeholder="10:00 - 23:59">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="horario_2" class="col-form-label">Horario de Atención (Domingo)</label>
                            <input class="form-control" type="text" id="horario_2" name="horario_2" placeholder="10:00 - 23:59">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="imagen" class="col-form-label"><i class="fa fa-image"></i> Imagen de Referencia</label>
                            <input class="form-control" type="file" name="imagen" id="imagen" accept="image/*">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <input type="submit" name="submit" class="btn btn-success submitBtn" value="Guardar Empresa"/>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <label style="background-color: #dca7a7; padding: 10px;">Click iquierdo para seleccionar la dirección exacta</label>
        <div id="map_canvas" style="height: 60vh;"></div>
        <div class="box-body">
            <div class="form-group">
                <label class="col-form-label">Ubicación del Punto</label>
                <input class="form-control" type="text" name="direccion" id="direccion" placeholder="Ingrese Ubicación del Punto">
            </div>
            <div class="form-group">
                <div class="col-md-6">
                    <input readonly class="form-control" type="text" name="coord_x" id="coord_x" placeholder="Latitud">
                </div>
                <div class="col-md-6">
                    <input readonly class="form-control" type="text" name="coord_y" id="coord_y" placeholder="Longitud">
                </div>
            </div>
        </div>
    </div>
</div>
</form>
<?php
require _VIEW_PATH_ . 'footer.php';
?>
<script src="<?php echo _SERVER_ . _JS_;?>domain.js"></script>
<script src="<?php echo _SERVER_ . _JS_;?>empresa.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA68xOsLic_QKxD4EcnwZDrtv-iE09-95M&callback=initMap" type="text/javascript"></script>
<script type="text/javascript">
    var map;
    var geocoder;
    var infoWindow;
    var marker;
    window.onload = function () {
        var latLng = new google.maps.LatLng(-3.7440734,-73.2588325);
        var opciones = {
            center: latLng,
            zoom: 15,
        };
        var map = new google.maps.Map(document.getElementById('map_canvas'), opciones);
        geocoder = new google.maps.Geocoder();
        infowindow = new google.maps.InfoWindow();

        google.maps.event.addListener(map, 'click', function(event) {
            geocoder.geocode(
                {'latLng': event.latLng},
                function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        if (results[0]) {
                            document.getElementById('direccion').value = results[0].formatted_address;
                            document.getElementById('coord_x').value = results[0].geometry.location.lat();
                            document.getElementById('coord_y').value = results[0].geometry.location.lng();
                            if (marker) {
                                marker.setPosition(event.latLng);
                            } else {
                                marker = new google.maps.Marker({
                                    position: event.latLng,
                                    map: map});
                            }
                            infowindow.setContent(results[0].formatted_address+'<br/> Coordenadas: '+results[0].geometry.location);
                            infowindow.open(map, marker);
                        } else {
                            document.getElementById('geocoding').innerHTML =
                                'No se encontraron resultados';
                        }
                    } else {
                        document.getElementById('geocoding').innerHTML =
                            'Geocodificación  ha fallado debido a: ' + status;
                    }
                });
        });
    }
    // -->
</script>
</body>
</html>
