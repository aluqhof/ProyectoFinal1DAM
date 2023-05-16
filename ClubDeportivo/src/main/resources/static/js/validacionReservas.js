let deportesSelect = document.getElementById("addDeporte");
let pistasSelect = document.getElementById("addPista");
let pistaContainer = document.getElementById("pistaContainer");

deportesSelect.addEventListener("change", function () {

    let deporteId = deportesSelect.value;

    if (!deporteId) {
        pistaContainer.style.display = "none";
        return;
    }
    // Filtrar las opciones del select de pistas y mostrar solo las que pertenecen al deporte seleccionado
    for (let i = 0; i < pistasSelect.options.length; i++) {
        let option = pistasSelect.options[i];
        if (option.dataset.deporteId === deporteId || !option.dataset.deporteId) {
            option.style.display = "block";
        } else {
            option.style.display = "none";
        }
    }

    // Mostrar el segundo select de pistas
    pistaContainer.style.display = "block";
});

let fechaReserva = document.getElementById("addFechaReserva");
let horaReserva = document.getElementById("addHoraReserva");
let error = document.querySelectorAll(".errorForm");
error.forEach(p => p.hidden = true);

function validarFormulario(event) {
    let resultado = false;

    resultado = validarFecha() &&
        validarHora();
    if (!resultado) {
        event.preventDefault();
    }


    return resultado;
}

fechaReserva.addEventListener("blur", validarFecha);
horaReserva.addEventListener("blur", validarHora);

function validarFecha() {
    let fecha = new Date(`${fechaReserva.value}T${horaReserva.value}:00`);
    let horaLimite = new Date();
    horaLimite.setHours(horaLimite.getHours() + 1);
    let hoy = new Date();


    if (fecha < horaLimite || fecha.getDay() === 0 || fecha > new Date(hoy.getTime() + 14 * 24 * 60 * 60 * 1000)) {
        fechaReserva.focus();
        fechaReserva.nextElementSibling.hidden = false;
        return false;
    }

    fechaReserva.nextElementSibling.hidden = true;
    return true;
}

function validarHora() {

    let fecha = new Date(fechaReserva.value);
    let horaLimite = new Date(fecha.getFullYear(), fecha.getMonth(), fecha.getDate(), new Date().getHours(), new Date().getMinutes() + 10);
    let hora = horaReserva.value;

    if (!hora || hora < horaLimite || hora < "09:00" || hora > "22:00") {
        horaReserva.focus();
        horaReserva.nextElementSibling.hidden = false;
        return false;
    }

    horaReserva.nextElementSibling.hidden = true;
    return true;
}

let formulario = document.getElementById("formularioReserva");
formulario.addEventListener("submit", validarFormulario);

pistasSelect.addEventListener("change", actualizarPrecio);
horaReserva.addEventListener("change", actualizarPrecio);

function actualizarPrecio() {
    let opcion = pistasSelect.options[pistasSelect.selectedIndex];
    let precio = opcion.dataset.precio;
    let horaLimite = opcion.dataset.hora_aumento_precio;
    let aumento = opcion.dataset.aumento_precio;
    let horaSeleccionada = horaReserva.value;
  
    let precioContainer = document.getElementById("precioContainer");
    let precioPista = document.getElementById("precioPista");
  
    if (precio) {
      precioPista.textContent = precio;
      let precioFormateado = parseFloat(precio).toFixed(2);
      precioPista.textContent = precioFormateado + " €";
  
      if (horaSeleccionada >= horaLimite) {
        let nuevoPrecio = parseFloat(precio) + (parseFloat(precio) * aumento / 100);
        let nuevoPrecioFormateado = nuevoPrecio.toFixed(2);
        precioPista.textContent = nuevoPrecioFormateado + " €";
      }
  
      precioContainer.style.display = "block";
    } else {
      precioContainer.style.display = "none";
    }
  }
  
