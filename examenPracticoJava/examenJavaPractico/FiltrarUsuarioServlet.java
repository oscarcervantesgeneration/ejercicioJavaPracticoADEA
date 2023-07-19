import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/filtrar")
public class FiltrarUsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    public void init() {
        usuarioDAO = new UsuarioDAO((Connection) getServletContext().getAttribute("CONNECTION"));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        List<Usuario> usuarios = usuarioDAO.obtenerUsuariosPorStatus(status);
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/views/tablero.jsp").forward(request, response);
    }
}