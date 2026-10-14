package listadedemandas;

public class ListaCircularClientes {
    private Cliente inicio;
    private Cliente fim;
    private int quantidade;

    public ListaCircularClientes() {
        inicio = null;
        fim = null;
        quantidade = 0;
    }

    public boolean estaVazia() { return inicio == null; }
    public int tamanho() { return quantidade; }
    public Cliente primeiro() { return inicio; }

    public boolean inserir(Cliente cliente) {
        if (cliente == null || buscarPorEmail(cliente.getEmail()) != null) return false;
        if (inicio == null) {
            inicio = cliente;
            fim = cliente;
            cliente.setProximo(cliente);
        } else {
            fim.setProximo(cliente);
            cliente.setProximo(inicio);
            fim = cliente;
        }
        quantidade++;
        return true;
    }

    public Cliente buscarPorId(long id) {
        if (inicio == null) return null;
        Cliente atual = inicio;
        do {
            if (atual.getId() == id) return atual;
            atual = atual.getProximo();
        } while (atual != inicio);
        return null;
    }

    public Cliente buscarPorEmail(String email) {
        if (inicio == null || email == null) return null;
        Cliente atual = inicio;
        do {
            if (atual.getEmail().equalsIgnoreCase(email.trim())) return atual;
            atual = atual.getProximo();
        } while (atual != inicio);
        return null;
    }

    public Cliente autenticar(String email, String senha) {
        Cliente cliente = buscarPorEmail(email);
        return cliente != null && cliente.conferirSenha(senha) ? cliente : null;
    }

    public boolean remover(long id) {
        if (inicio == null) return false;
        Cliente anterior = fim;
        Cliente atual = inicio;
        do {
            if (atual.getId() == id) {
                if (quantidade == 1) {
                    inicio = null;
                    fim = null;
                } else {
                    anterior.setProximo(atual.getProximo());
                    if (atual == inicio) inicio = atual.getProximo();
                    if (atual == fim) fim = anterior;
                    fim.setProximo(inicio);
                }
                atual.setProximo(null);
                quantidade--;
                return true;
            }
            anterior = atual;
            atual = atual.getProximo();
        } while (atual != inicio);
        return false;
    }
}
