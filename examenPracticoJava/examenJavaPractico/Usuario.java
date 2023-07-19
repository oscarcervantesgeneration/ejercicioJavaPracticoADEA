import java.util.Date;
public class Usuario {
    private String login;
    private String password;
    private Date fechaVigencia;

    public Usuario(String login, String password, Date fechaVigencia) {
        this.login = login;
        this.password = password;
        this.fechaVigencia = fechaVigencia;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
}
