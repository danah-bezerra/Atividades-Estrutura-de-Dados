package projetosimples;

/** Raiz do sistema: organiza categorias em uma lista circular duplamente encadeada. */
public class SistemaNoticias {
    private NoCategoria primeira;
    private NoCategoria ultima;
    private NoCategoria selecionada;
    private int quantidadeCategorias;

    public int quantidadeCategorias() {
        return quantidadeCategorias;
    }

    public boolean temCategorias() {
        return primeira != null;
    }

    public void adicionarCategoria(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria não pode ficar vazio.");
        }
        NoCategoria novo = new NoCategoria(new Categoria(nome));
        if (primeira == null) {
            primeira = novo;
            ultima = novo;
            novo.proximo = novo;
            novo.anterior = novo;
        } else {
            novo.anterior = ultima;
            novo.proximo = primeira;
            ultima.proximo = novo;
            primeira.anterior = novo;
            ultima = novo;
        }
        selecionada = novo;
        quantidadeCategorias++;
    }

    public Categoria proximaCategoria() {
        if (primeira == null) return null;
        selecionada = selecionada == null ? primeira : selecionada.proximo;
        return selecionada.categoria;
    }

    public Categoria categoriaAnterior() {
        if (primeira == null) return null;
        selecionada = selecionada == null ? ultima : selecionada.anterior;
        return selecionada.categoria;
    }

    public Categoria categoriaSelecionada() {
        return selecionada == null ? null : selecionada.categoria;
    }

    public Categoria removerCategoriaSelecionada() {
        if (selecionada == null) return null;
        Categoria removida = selecionada.categoria;

        if (quantidadeCategorias == 1) {
            primeira = null;
            ultima = null;
            selecionada = null;
        } else {
            NoCategoria seguinte = selecionada.proximo;
            selecionada.anterior.proximo = selecionada.proximo;
            selecionada.proximo.anterior = selecionada.anterior;
            if (selecionada == primeira) primeira = selecionada.proximo;
            if (selecionada == ultima) ultima = selecionada.anterior;
            selecionada.proximo = null;
            selecionada.anterior = null;
            selecionada = seguinte;
        }
        quantidadeCategorias--;
        return removida;
    }
}
