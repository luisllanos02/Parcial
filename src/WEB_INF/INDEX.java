package WEB_INF;

<!DOCTYPE html>
<html>
    <head>
        <title>Mi app web</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body onload="consultar();">
        <div style="color: red">Registro de Papeletas</div>
        <br>
        <input type="text" id="dni" name="dni" placeholder="DNI">
        <select name="multa" id="multa" onchange="traermulta(this.value)">
            <option value="Multa">Multa</option> 
            <option value="Alta Velocidad">Alta Velocidad</option>
            <option value="Pasa luz roja">Pasa luz roja</option>
            <option value="Estacionar zona pro">Estacionar en zona prohibida</option>
            <option value="Pico placa">Pico placa</option>
        </select>
        <br><br>
        <input type="text" id="monto" name="monto" placeholder="Monto" readonly="">
            <button onclick="agregar();">Agregar</button>
        <br>
        <br><br>
        <table id="myTable" cellpadding="1" border="1" STYLE="border-collapse:collapse;width:100%">
            <tr>
                <th>DNI</th>
                <th>Multa</th>
                <th>Monto</th>            
                <th colspan="3">Actions</th>
            </tr>
        </table>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
        <script type="text/javascript">
        
            function borrar(btn, dni) {
                console.log(dni);
                var respuesta = prompt(`Escribe OK para eliminar`);
                if(respuesta == 'OK') {
                    var fila = btn.closest('tr');
                    $.ajax({
                        method : 'POST',
                        url    : 'EliminarServlet',
                        data   : {
                            dni : dni
                        },
                        success: function(msg) {
                            console.log(msg);
                            fila.remove();
                            alert(msg.mensaje);
                        },
                        error: function(msg) {
                            console.error(msg);
                        }
                    });
                }
            }
            
            function agregar() {
                
                var dni= document.getElementById("dni").value;
                var multa= document.getElementById("multa").value;
                var monto= document.getElementById("monto").value;
                $.ajax({
                    method : 'POST',
                    url    : 'AgregarServlet',
                    data   : {
                        
                        dni : dni,
                        multa   : multa,
                        monto : monto
                    },
                    success: function(msg) {
                        console.log(msg);
                        location.reload();
                    },
                    error: function(msg) {
                        console.error(msg);
                    }
                });         
            }
                                   
            function consultar() {
                $.ajax({
                    method : 'POST',
                    url    : 'Consulta',
                    success: function(msg) {
                        var tabla = document.getElementById("myTable");
                        for(var i = 0; i < msg.length; i++) {
                            //CREO UNA FILA
                            var fila = tabla.insertRow(1);
                            //CREAR CELDAS
                            var celda0 = fila.insertCell(0);
                            var celda1 = fila.insertCell(1);
                            var celda2 = fila.insertCell(2);
                            var celda3 = fila.insertCell(3);
                            //PONER UN VALOR EN CADA CELDA
                            celda0.innerHTML = msg[i].dni;
                            celda1.innerHTML = msg[i].multa;
                            celda2.innerHTML = msg[i].monto;
                            celda3.innerHTML = '<button onclick="borrar(this, '+msg[i].dni+')" >Borrar</button>';              
                        }
                    },
                    error: function(msg) {
                        console.error(msg);
                    }
                });
            }
            function traermulta(multa) {
                console.log(multa);
                    $.ajax({
                        method : 'POST',
                        url    : 'Multa',
                        data   : {
                            multa : multa
                        },
                        success: function(msg) {
                            console.log(msg.monto2);
                            document.getElementById("monto").value=msg.monto2;
                        },
                        error: function(msg) {
                            console.error(msg);
                        }
                    });       
            }
        </script>
    </body>
</html>