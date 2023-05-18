document.getElementById("addPistaDeporte").addEventListener("blur", validarDeporte);
document.getElementById("addPistaPrecio").addEventListener("blur", validarPrecio);
document.getElementById("addPistaAumento").addEventListener("blur", validarAumento);
document.getElementById("addPistaHoraAumento").addEventListener("blur", validarHoraAumento);

let error = document.querySelectorAll(".errorForm");
error.forEach(p => p.hidden = true);

function validarFormulario(event) {
    let resultado = true;

    resultado = validarDeporte() &&
        validarPrecio() &&
        validarAumento() &&
        validarHoraAumento();

    if (!resultado) {
        event.preventDefault();
    }
    return resultado;
}

function validarDeporte() {
    let deporte = document.getElementById("addPistaDeporte").value;
    if (!deporte) {
        document.getElementById("addPistaDeporte").focus();
        document.getElementById("addPistaDeporte").nextElementSibling.hidden = false;
        return false;
    }
    document.getElementById("addPistaDeporte").nextElementSibling.hidden = true;
    return true;
}

function validarPrecio() {
    let precio = parseFloat(document.getElementById("addPistaPrecio").value);
    if (isNaN(precio) || precio < 0.01 || precio > 999.99 || !precio) {
        document.getElementById("addPistaPrecio").focus();
        document.getElementById("addPistaPrecio").nextElementSibling.hidden = false;
        return false;
    }
    document.getElementById("addPistaPrecio").nextElementSibling.hidden = true;
    return true;
}

function validarAumento() {
    let aumento = document.getElementById("addPistaAumento").value;
    if (!/^(?:100|[1-9]\d|\d)$/.test(aumento)) {
        document.getElementById("addPistaAumento").focus();
        document.getElementById("addPistaAumento").nextElementSibling.hidden = false;
        return false;
    }
    document.getElementById("addPistaAumento").nextElementSibling.hidden = true;
    return true;
}

function validarHoraAumento() {
    let hora = document.getElementById("addPistaHoraAumento").value;
    if (hora < "09:00" || hora > "21:00") {
        horaReserva.focus();
        horaReserva.nextElementSibling.hidden = false;
        return false;
    }

    horaReserva.nextElementSibling.hidden = true;
    return true;
}

let formulario = document.getElementById("formularioPistaAdmin");
formulario.addEventListener("submit", validarFormulario);