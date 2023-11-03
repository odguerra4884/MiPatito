<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link href="css/Estilos.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css">
</head>
<body>
    <div id="container">
        <h1>Editar Usuario</h1>
        <form action="EditarUsuarioServlet" method="post">
            <input type="text" name="nombre" placeholder="Nombre" value="<%= request.getParameter("nombre") %>" required>
            <input type="text" name="nom-usuario" placeholder="Nombre de Usuario" value="<%= request.getParameter("nomUsuario") %>" required>
            <input type="text" name="correo" placeholder="Correo electrónico" value="<%= request.getParameter("correo") %>" required>
            <div class="password-container">
            <input type="password" name="contraseña" placeholder="Contraseña" value="<%= request.getParameter("contraseña") %>" required>
            <i class="icon-eye far fa-eye" id="mostrar-contraseña"></i>
            </div>
            <button type="submit">Guardar Cambios</button>
            <br>
            <button type="button" onclick="cancelarEdicion()">Cancelar</button>
        </form>
    </div>
    <script>
        function cancelarEdicion() {
            // Regresa a la página de inicio
            window.location = 'paginaInicio.jsp';
        }
        
document.getElementById("mostrar-contraseña").addEventListener("click", function() {
    var contraseñaInput = document.querySelector("input[name='contraseña']"); // Cambiado el selector aquí
    if (contraseñaInput.type === "password") {
        contraseñaInput.type = "text"; // Cambiar el tipo a "text"
        this.classList.remove("far", "fa-eye"); // Cambiar el ícono a "fa-eye-slash"
        this.classList.add("far", "fa-eye-slash");
    } else {
        contraseñaInput.type = "password"; // Cambiar el tipo a "password"
        this.classList.remove("far", "fa-eye-slash"); // Cambiar el ícono a "fa-eye"
        this.classList.add("far", "fa-eye");
    }
});

    </script>
</body>
</html>
