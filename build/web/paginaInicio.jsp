<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Página de inicio</title>
    <link href="css/Estilos.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div id="container">
        <h1>Mi Patito</h1>
        <%
            // Recuperar el correo de la sesión
            String correo = (String) request.getSession().getAttribute("correo");

            // Realizar la conexión a la base de datos y consultar los datos del usuario
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_patito", "root", "root");

                String consulta = "SELECT * FROM usuarios WHERE Correo = ?";
                preparedStatement = connection.prepareStatement(consulta);
                preparedStatement.setString(1, correo);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String nombre = resultSet.getString("Nombre");
                    String nomUsuario = resultSet.getString("Nom_Usuario");
                    String contraseña = resultSet.getString("Contraseña");
        %>
            <h4>Nombre: <%= nombre %></h4>
            <h4>Nombre de Usuario: <%= nomUsuario %></h4>
            <h4>Correo: <%= correo %></h4>
            <h4>Contraseña: <%= contraseña %></h4>
            <button type="button" onclick="editarUsuario('<%= nombre %>', '<%= nomUsuario %>', '<%= correo %>', '<%= contraseña %>')">Editar Usuario</button>
            <br>
            <a href="index.html">Volver al Inicio</a>
        <%
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        %>
    </div>
    <script>
        function editarUsuario(nombre, nomUsuario, correo, contraseña) {
            // Redirige al usuario a la página de edición de usuario y pasa los datos del usuario
            window.location = 'editarUsuario.jsp?nombre=' + nombre + '&nomUsuario=' + nomUsuario + '&correo=' + correo + '&contraseña=' + contraseña;
        }
    </script>
</body>
</html>
