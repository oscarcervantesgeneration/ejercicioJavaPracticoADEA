import java.sql.*;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public Usuario obtenerUsuario(String login) throws SQLException {
        String sql = "SELECT LOGIN, PASSWORD, FECHA_VIGENCIA FROM USUARIO WHERE LOGIN = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    java.sql.Date sqlDate = rs.getDate("FECHA_VIGENCIA");
                    java.util.Date utilDate = (sqlDate != null) ? new java.util.Date(sqlDate.getTime()) : null;
                    return new Usuario(rs.getString("LOGIN"), rs.getString("PASSWORD"), utilDate);
                } else {
                    return null;
                }
            }
        }
    }

    public boolean validarUsuario(String login, String password) throws Exception {
        Usuario usuario = obtenerUsuario(login);
        if (usuario != null) {
            String passwordHash = Base64.getEncoder().encodeToString(
                    MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8)));
            return passwordHash.equals(usuario.getPassword()) &&
                    (usuario.getFechaVigencia() == null || new java.util.Date().before(usuario.getFechaVigencia()));
        }
        return false;
    }
}