document.getElementById("addReservaFecha").addEventListener("blur", validarFecha);
document.getElementById("addReservaHora").addEventListener("blur", validarHora);
document.getElementById("addReservaIDSocio").addEventListener("blur", validarIDSocio);
document.getElementById("addReservaIDPista").addEventListener("blur", validarPista);
let mensajes = document.querySelectorAll(".errorForm");
mensajes.forEach(p => p.hidden = true);

function validarFormulario() {
    let resultado = false;

    resultado = validarFecha() &&
        validarHora() &&
        validarIDSocio() &&
        validarPista();

return resultado;
}

let fechaReserva = document.getElementById("addReservaFecha");
let horaReserva = document.getElementById("addReservaHora");
function validarFecha() {
    let fecha = new Date(fechaReserva.value);
    let hoy = new Date();

    if (fecha < hoy) {
        fechaReserva.focus();
        fechaReserva.nextElementSibling.hidden = false;
        return false;
    }

    if (fecha.getDay() === 0) {//No es domingo
        fechaReserva.focus();
        fechaReserva.nextElementSibling.hidden = false;
        return false;
    }

    fechaReserva.nextElementSibling.hidden = true;
    return true;
}

function validarHora() {
    let hora = horaReserva.value;

    // Verificar que se haya seleccionado una hora de reserva
    if (!hora) {
        horaReserva.focus();
        horaReserva.nextElementSibling.hidden = false;
        return false;
    }

    // Verificar que la hora de reserva esté dentro del horario de apertura del club deportivo
    if (hora < "09:00" || hora > "21:00") {
        horaReserva.focus();
        horaReserva.nextElementSibling.hidden = false;
        return false;
    }

    horaReserva.nextElementSibling.hidden = true;
    return true;
}

function validarIDSocio() {
    let idSocio = document.getElementById("addReservaIDSocio");
    // Verificar que se haya seleccionado un socio
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
    // Verificar que se haya seleccionado una pista
    if (!idPista.value) {
        idPista.focus();
        idPista.nextElementSibling.hidden = false;
        return false;
    }

    idPista.nextElementSibling.hidden = true;
    return true;
}