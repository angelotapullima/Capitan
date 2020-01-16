//var urlweb = "http://localhost:8080/capitan/";
//var urlweb_auth = "http://localhost:8080/auth/";
var urlweb = "https://www.guabba.com/capitan2/";
var urlweb_auth = "https://www.bufeotec.com/auth/";
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function registrarusuario() {
    alert('Usuario Registrado. Ahora inicie sesión.');
    location.href = urlweb + 'admin';
}

function valida(e){
    tecla = (document.all) ? e.keyCode : e.which;

    //Tecla de retroceso para borrar, siempre la permite
    if (tecla==8 || tecla==46){
        return true;
    }
    // Patron de entrada, en este caso solo acepta numeros
    patron =/[0-9]/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}

$(function () {
    $('#example2').DataTable({
        responsive: true,
        "language": {
            sEmptyTable: "No existen datos en esta tabla",
            sInfo: "Mostrando _START_ de _END_ de _TOTAL_ Entradas",
            sInfoEmpty: "0 de 0 de 0 Entradas",
            sInfoFiltered: "(Filtrado de un total de _MAX_ resultados)",
            sInfoPostFix: "",
            sInfoThousands: ".",
            sLengthMenu: "Mostrar _MENU_ Resultados",
            sLoadingRecords: "Cargando resultados...",
            sProcessing: "Espere por favor..",
            sSearch: "Buscar:",
            sZeroRecords: "No se han encontrado resultados.",
            oPaginate: {
                sFirst: "Primero",
                sPrevious: "Anterior",
                sNext: "Siguiente",
                sLast: "Último"
            },
            oAria: {
                sSortAscending: ":Habilitar para ordenar de forma ascendente",
                sSortDescending: ":Habilitar para ordenar de forma descendente"
            }
        }
    });
    $('#example3').DataTable({
        responsive: true,
        "language": {
            sEmptyTable: "No existen datos en esta tabla",
            sInfo: "Mostrando _START_ de _END_ de _TOTAL_ Entradas",
            sInfoEmpty: "0 de 0 de 0 Entradas",
            sInfoFiltered: "(Filtrado de un total de _MAX_ resultados)",
            sInfoPostFix: "",
            sInfoThousands: ".",
            sLengthMenu: "Mostrar _MENU_ Resultados",
            sLoadingRecords: "Cargando resultados...",
            sProcessing: "Espere por favor..",
            sSearch: "Buscar:",
            sZeroRecords: "No se han encontrado resultados.",
            oPaginate: {
                sFirst: "Primero",
                sPrevious: "Anterior",
                sNext: "Siguiente",
                sLast: "Último"
            },
            oAria: {
                sSortAscending: ":Habilitar para ordenar de forma ascendente",
                sSortDescending: ":Habilitar para ordenar de forma descendente"
            }
        }
    });

    $('#example4').DataTable({
        responsive: true,
        "language": {
            sEmptyTable: "No existen datos en esta tabla",
            sInfo: "Mostrando _START_ de _END_ de _TOTAL_ Entradas",
            sInfoEmpty: "0 de 0 de 0 Entradas",
            sInfoFiltered: "(Filtrado de un total de _MAX_ resultados)",
            sInfoPostFix: "",
            sInfoThousands: ".",
            sLengthMenu: "Mostrar _MENU_ Resultados",
            sLoadingRecords: "Cargando resultados...",
            sProcessing: "Espere por favor..",
            sSearch: "Buscar:",
            sZeroRecords: "No se han encontrado resultados.",
            oPaginate: {
                sFirst: "Primero",
                sPrevious: "Anterior",
                sNext: "Siguiente",
                sLast: "Último"
            },
            oAria: {
                sSortAscending: ":Habilitar para ordenar de forma ascendente",
                sSortDescending: ":Habilitar para ordenar de forma descendente"
            }
        }
    });

    $('#example5').DataTable({
        responsive: true,
        "language": {
            sEmptyTable: "No existen datos en esta tabla",
            sInfo: "Mostrando _START_ de _END_ de _TOTAL_ Entradas",
            sInfoEmpty: "0 de 0 de 0 Entradas",
            sInfoFiltered: "(Filtrado de un total de _MAX_ resultados)",
            sInfoPostFix: "",
            sInfoThousands: ".",
            sLengthMenu: "Mostrar _MENU_ Resultados",
            sLoadingRecords: "Cargando resultados...",
            sProcessing: "Espere por favor..",
            sSearch: "Buscar:",
            sZeroRecords: "No se han encontrado resultados.",
            oPaginate: {
                sFirst: "Primero",
                sPrevious: "Anterior",
                sNext: "Siguiente",
                sLast: "Último"
            },
            oAria: {
                sSortAscending: ":Habilitar para ordenar de forma ascendente",
                sSortDescending: ":Habilitar para ordenar de forma descendente"
            }
        }
    });
});
$.ajaxSetup({
    beforeSend: function () {
        $("<div class='loader' id='loading'></div>").appendTo("body");
    },complete:function() {
        $("#loading").remove();
    }
});
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});
function setDataTable(id) {
    $('#'+id).DataTable({
        responsive: true,
        "language": {
            sEmptyTable: "No existen datos en esta tabla",
            sInfo: "Mostrando _START_ de _END_ de _TOTAL_ Entradas",
            sInfoEmpty: "0 de 0 de 0 Entradas",
            sInfoFiltered: "(Filtrado de un total de _MAX_ resultados)",
            sInfoPostFix: "",
            sInfoThousands: ".",
            sLengthMenu: "Mostrar _MENU_ Resultados",
            sLoadingRecords: "Cargando resultados...",
            sProcessing: "Espere por favor..",
            sSearch: "Buscar:",
            sZeroRecords: "No se han encontrado resultados.",
            oPaginate: {
                sFirst: "Primero",
                sPrevious: "Anterior",
                sNext: "Siguiente",
                sLast: "Último"
            },
            oAria: {
                sSortAscending: ":Habilitar para ordenar de forma ascendente",
                sSortDescending: ":Habilitar para ordenar de forma descendente"
            }
        }
    });
}