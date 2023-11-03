import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Clases.ConexionBaseDeDatos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String nombre = request.getParameter("nombre");
            String nomUsuario = request.getParameter("nom-usuario");
            String correo = request.getParameter("correo");
            String contraseña = request.getParameter("contraseña");

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                // Realiza la conexión a la base de datos
                ConexionBaseDeDatos conexion = new ConexionBaseDeDatos();
                conn = conexion.conectar();

                // Verifica si el correo electrónico ya está registrado
                String checkEmailQuery = "SELECT * FROM usuarios WHERE Correo = ?";
                PreparedStatement checkEmailStmt = conn.prepareStatement(checkEmailQuery);
                checkEmailStmt.setString(1, correo);
                ResultSet emailResultSet = checkEmailStmt.executeQuery();

                if (emailResultSet.next()) {
                    // Correo electrónico ya está registrado, muestra un mensaje de error
                    out.println("<script>alert('El correo electrónico ya está registrado.'); window.location = 'registro.html';</script>");
                } else {
                    // Inserta los datos en la tabla de usuarios
                    String insertUserQuery = "INSERT INTO usuarios (Nombre, Nom_Usuario, Correo, Contraseña) VALUES (?, ?, ?, ?)";
                    stmt = conn.prepareStatement(insertUserQuery);
                    stmt.setString(1, nombre);
                    stmt.setString(2, nomUsuario);
                    stmt.setString(3, correo);
                    stmt.setString(4, contraseña);

                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // Registro exitoso
                        out.println("<script>alert('Registro exitoso'); window.location = 'registro.html';</script>");
                    } else {
                        // Error en el registro
                        out.println("<script>alert('Error en el registro'); window.location = 'registro.html';</script>");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
