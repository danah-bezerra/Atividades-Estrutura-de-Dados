package listadedemandas;

public class ListaCircularChamados {
    private Chamado inicio;
    private Chamado fim;
    private Chamado atual;
    private int quantidade;

    public ListaCircularChamados() {
        inicio = null;
        fim = null;
        atual = null;
        quantidade = 0;
    }

    public boolean estaVazia() { return inicio == null; }
    public int tamanho() { return quantidade; }
    public Chamado primeiro() { return inicio; }

    public void inserir(Chamado chamado) {
        if (chamado == null) return;
        if (inicio == null) {
            inicio = chamado;
            fim = chamado;
            chamado.setProximo(chamado);
            atual = chamado;
        } else {
            fim.setProximo(chamado);
            chamado.setProximo(inicio);
            fim = chamado;
        }
        quantidade++;
    }

    public Chamado buscarPorId(long id) {
        if (inicio == null) return null;
        Chamado p = inicio;
        do {
            if (p.getId() == id) return p;
            p = p.getProximo();
        } while (p != inicio);
        return null;
    }

    public Chamado proximo() {
        if (atual == null) return null;
        atual = atual.getProximo();
        return atual;
    }

    public Chamado anterior() {
        if (atual == null) return null;
        Chamado p = atual;
        while (p.getProximo() != atual) p = p.getProximo();
        atual = p;
        return atual;
    }

    public void selecionar(long id) {
        Chamado encontrado = buscarPorId(id);
        if (encontrado != null) atual = encontrado;
    }

    public Chamado removerAtual() {
        if (inicio == null || atual == null) return null;
        Chamado removido = atual;
        if (quantidade == 1) {
            inicio = null;
            fim = null;
            atual = null;
        } else {
            Chamado anterior = atual;
            while (anterior.getProximo() != atual) anterior = anterior.getProximo();
            anterior.setProximo(atual.getProximo());
            if (atual == inicio) inicio = atual.getProximo();
            if (atual == fim) fim = anterior;
            fim.setProximo(inicio);
            atual = atual.getProximo();
        }
        removido.setProximo(null);
        quantidade--;
        return removido;
    }

    public boolean removerPorId(long id) {
        Chamado alvo = buscarPorId(id);
        if (alvo == null) return false;
        atual = alvo;
        removerAtual();
        return true;
    }
}
