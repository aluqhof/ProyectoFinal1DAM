document.getElementById("addNombreDeporte").addEventListener("blur", validarNombreDeporte);
let error = document.querySelectorAll(".errorForm");
error.forEach(p => p.hidden = true);

function validarFormulario(event) {
    let resultado = true;
    resultado = validarNombreDeporte();
    if (!resultado) {
        event.preventDefault();
    }
    return resultado;
}

function validarNombreDeporte() {
    let nombreDeporte = document.getElementById("addNombreDeporte").value;
    if (!nombreDeporte || nombreDeporte.length < 3 || nombreDeporte.length > 50 || !nombreDeporte.match(/^[a-zA-Z\u00f1\u00d1áéíóúÁÉÍÓÚ\s]*$/)) {
        document.getElementById("addNombreDeporte").focus();
        document.getElementById("addNombreDeporte").nextElementSibling.hidden = false;
        return false;
    }
    document.getElementById("addNombreDeporte").nextElementSibling.hidden = true;
    return true;
}

let formulario = document.getElementById("formularioDeporteAdmin");
formulario.addEventListener("submit", validarFormulario);