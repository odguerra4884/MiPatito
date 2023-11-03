import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String correo = request.getParameter("correo");
            String contraseña = request.getParameter("contraseña");
            boolean validacionExitosa = false;

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                // Realiza la conexión a la base de datos
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_patito", "root", "root");

                // Consulta para verificar el inicio de sesión
                String consulta = "SELECT * FROM usuarios WHERE Correo = ? AND Contraseña = ?";
                preparedStatement = connection.prepareStatement(consulta);
                preparedStatement.setString(1, correo);
                preparedStatement.setString(2, contraseña);
                resultSet = preparedStatement.executeQuery();

                // Verifica si se encontró un usuario con el correo y contraseña proporcionados
                if (resultSet.next()) {
                    validacionExitosa = true;
                    // Almacenar el correo en la sesión para recuperar los datos del usuario
                    request.getSession().setAttribute("correo", correo);
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

            if (validacionExitosa) {
                // Redirigir al usuario a la página de inicio
                response.sendRedirect("paginaInicio.jsp");
            } else {
                out.println("<script>alert('Inicio de sesión incorrecto'); window.location = 'index.html';</script>");
            }
        }
    }
}
