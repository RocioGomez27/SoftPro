function setChecked(id,liId){
    var checkbox = document.getElementById(id);
    var buttonLi = document.getElementById(liId);
    if(checkbox.checked){
        checkbox.checked = false;
        buttonLi.style = "background-color: rgba(152,63,246,0.3);"
    }else{
        checkbox.checked = true;
        buttonLi.style = "background-color: rgba(152,63,246,0.5);"
    }
}



function aplicarFiltros(){
    var buttonRestaurantes = document.getElementById("Restaurantes");
    var buttonAL = document.getElementById("AireLibre");
    var buttonOcio = document.getElementById("Ocio");
    var buttonTienda = document.getElementById('Tienda');
    var buttonCultura = document.getElementById('Cultura');
    var buttonTransporte = document.getElementById('Transporte');

    var form = document.getElementById('aplicar')

    var filters = "";

    if(buttonRestaurantes.checked) filters+="Restaurante&";
    if(buttonAL.checked) filters+="Naturaleza&";
    if(buttonOcio.checked) filters+="Ocio&";
    if(buttonTienda.checked) filters+="Tienda&";
    if(buttonCultura.checked) filters+="Cultura&";
    if(buttonTransporte.checked) filters+="Transporte&";

    filters = filters.substring(0,filters.length - 1);

    form.action = form.action + filters;
    
    form.submit();
}