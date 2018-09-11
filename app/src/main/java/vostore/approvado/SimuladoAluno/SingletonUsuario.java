package vostore.approvado.SimuladoAluno;

public class SingletonUsuario {
    private static SingletonUsuario instance = null;
    private static User usuario = null;

    public static SingletonUsuario getInstance() {
        if (instance == null) {
            usuario = new User();
            return instance = new SingletonUsuario();
        } else {
            return instance;
        }
    }

    public void setUsuario(User usuario) {
        SingletonUsuario.usuario = usuario;
    }

    public User getUsuario() {
        return SingletonUsuario.usuario;
    }
}

