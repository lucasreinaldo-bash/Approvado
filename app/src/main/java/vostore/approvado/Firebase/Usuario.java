package vostore.approvado.Firebase;

public class Usuario {

    private String nome;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String contrasenha;
    private String fotoPerfilChat;

    public String getFotoPerfilChat() {
        return fotoPerfilChat;
    }

    public Usuario() {

    }

    public Usuario(String nome,String nomeCompleto, String email, String senha, String contrasenha, String fotoPerfilChat) {
        this.nome = nome;
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.senha = senha;
        this.contrasenha = contrasenha;
        this.fotoPerfilChat = fotoPerfilChat;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setFotoPerfilChat(String fotoPerfilChat) {
        this.fotoPerfilChat = fotoPerfilChat;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}