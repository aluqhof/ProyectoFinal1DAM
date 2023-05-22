document.getElementById("addReservaFecha").addEventListener("blur", validarFecha);
document.getElementById("addReservaHora").addEventListener("blur", validarHora);
document.getElementById("addReservaIDSocio").addEventListener("blur", validarIDSocio);
document.getElementById("addReservaIDPista").addEventListener("blur", validarPista);
let error = document.querySelectorAll(".errorForm");
error.forEach(p => p.hidden = true);

function validarFormulario(event) {
    let resultado = false;

    resultado = validarFecha() &&
        validarHora() &&
        validarIDSocio() &&
        validarPista();

    if (!resultado) {
        event.preventDefault();
    }
    return resultado;
}

let fechaReserva = document.getElementById("addReservaFecha");
let horaReserva = document.getElementById("addReservaHora");
function validarFecha() {
    let hoy = new Date();
    hoy.setHours(0, 0, 0, 0);
    let fecha = new Date(fechaReserva.value);

    if (fecha < hoy) {
        fechaReserva.focus();
        fechaReserva.nextElementSibling.hidden = false;
        return false;
    }

    if (fecha.getDay() === 0) {
        fechaReserva.focus();
        fechaReserva.nextElementSibling.hidden = false;
        return false;
    }

    fechaReserva.nextElementSibling.hidden = true;
    return true;
}

function validarHora() {
    let hora = horaReserva.value;
    let fecha = new Date(fechaReserva.value);
    let ahora = new Date();

    if (!hora) {
        horaReserva.focus();
        horaReserva.nextElementSibling.hidden = false;
        return false;
    }

    if (hora < "07:00" || hora > "21:00") {
        horaReserva.focus();
        horaReserva.nextElementSibling.hidden = false;
        return false;
    }

    if (
        fecha.getDate() === ahora.getDate() &&
        fecha.getMonth() === ahora.getMonth() &&
        fecha.getFullYear() === ahora.getFullYear() &&
        hora < ahora.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })//He tenido que buscar como hacerlo porque no me funcionaba cogiendo la hora de la fecha
    ) {
        horaReserva.focus();
        horaReserva.nextElementSibling.hidden = false;
        return false;
    }

    horaReserva.nextElementSibling.hidden = true;
    return true;
}

function validarIDSocio() {
    let idSocio = document.getElementById("addReservaIDSocio");
    if (!idSocio.value) {
        idSocio.focus();
        idSocio.nextElementSibling.hidden = false;
        return false;
    }

    idSocio.nextElementSibling.hidden = true;
    return true;
}

function validarPista() {
    let idPista = document.getElementById("addReservaIDPista");
    if (!idPista.value) {
        idPista.focus();
        idPista.nextElementSibling.hidden = false;
        return false;
    }

    idPista.nextElementSibling.hidden = true;
    return true;
}

let formulario = document.getElementById("formularioReservaAdmin");
formulario.addEventListener("submit", validarFormulario);