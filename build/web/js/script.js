
    // Función para obtener el valor de un parámetro de consulta en la URL
    function getQueryParam(param) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
    }

    // Función para mostrar el mensaje emergente y aplicar estilos de error si es necesario
    function showPopup() {
        const success = getQueryParam("success");
        const error = getQueryParam("error");
        const popup = document.getElementById("popup");
        const message = document.getElementById("message");

        if (success === "true") {
            message.textContent = "Registro exitoso";
            popup.style.display = "block";
        } else if (error === "true") {
            message.textContent = "Nombre de usuario o contraseña inválidos";
            popup.style.display = "block";
            popup.classList.add("error"); // Agregar clase de error al mensaje emergente

            // Aplicar clase de error a campos de entrada específicos (si es necesario)
            const inputFields = document.querySelectorAll("input");
            inputFields.forEach(function (input) {
                input.classList.add("error");
            });
        }
    }

    // Llama a la función showPopup cuando la página se carga
    window.addEventListener("load", showPopup);
