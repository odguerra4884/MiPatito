import Clases.ConexionBaseDeDatos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/EditarUsuarioServlet")
public class EditarUsuarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtén los datos enviados desde el formulario de edición
        String nombre = request.getParameter("nombre");
        String nomUsuario = request.getParameter("nom-usuario");
        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("contraseña");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establece la conexión a la base de datos utilizando la clase ConexionBaseDeDatos
            ConexionBaseDeDatos conexionBD = new ConexionBaseDeDatos();
            connection = conexionBD.conectar();

            // Crea la consulta SQL para actualizar los datos del usuario
            String consulta = "UPDATE usuarios SET Nombre = ?, Nom_Usuario = ?, Contraseña = ? WHERE Correo = ?";
            preparedStatement = connection.prepareStatement(consulta);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, nomUsuario);
            preparedStatement.setString(3, contraseña);
            preparedStatement.setString(4, correo);

            // Ejecuta la consulta para actualizar los datos en la base de datos
            preparedStatement.executeUpdate();

            // Redirige a la página de inicio
            response.sendRedirect("paginaInicio.jsp");
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Maneja errores, muestra un mensaje de error o redirige a una página de error
            response.sendRedirect("paginaError.jsp");
        } finally {
            try {
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
    }
}
