package listadedemandas;

public class Cliente {
    private long id;
    private String nome;
    private String email;
    private String senha;
    private Cliente proximo;

    public Cliente(long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.proximo = null;
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Cliente getProximo() { return proximo; }
    public void setProximo(Cliente proximo) { this.proximo = proximo; }

    public boolean conferirSenha(String tentativa) {
        return senha != null && senha.equals(tentativa);
    }

    public void alterarNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) this.nome = nome.trim();
    }

    public void alterarEmail(String email) {
        if (email != null && !email.trim().isEmpty()) this.email = email.trim();
    }

    public void alterarSenha(String senha) {
        if (senha != null && !senha.isEmpty()) this.senha = senha;
    }

    public String getSenhaParaArquivo() { return senha; }

    @Override
    public String toString() {
        return nome + " (ID " + id + ", e-mail: " + email + ")";
    }
}
