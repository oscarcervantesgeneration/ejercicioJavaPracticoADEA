import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_name", "root", "password");
            usuarioDAO = new UsuarioDAO(connection);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            switch (action) {
                case "alta":
                    String loginAlta = request.getParameter("login");
                    String passwordAlta = request.getParameter("password");
                    Date fechaVigenciaAlta = sdf.parse(request.getParameter("fechaVigencia"));
                    Usuario nuevoUsuario = new Usuario(loginAlta, passwordAlta, fechaVigenciaAlta);
                    usuarioDAO.agregarUsuario(nuevoUsuario);
                    break;
                case "baja":
                    String loginBaja = request.getParameter("login");
                    usuarioDAO.eliminarUsuario(loginBaja);
                    break;
                case "modificar":
                    String loginModificar = request.getParameter("login");
                    String passwordModificar = request.getParameter("password");
                    Date fechaVigenciaModificar = sdf.parse(request.getParameter("fechaVigencia"));
                    Usuario usuarioModificado = new Usuario(loginModificar, passwordModificar, fechaVigenciaModificar);
                    usuarioDAO.modificarUsuario(usuarioModificado);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

        response.sendRedirect("index.jsp");
    }
}