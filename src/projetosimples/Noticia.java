package projetosimples;

/** Uma notícia possui sua própria lista circular duplamente encadeada de imagens. */
public class Noticia {
    private String titulo;
    private NoImagem primeira;
    private NoImagem ultima;
    private NoImagem selecionada;
    private int quantidadeImagens;

    public Noticia(String titulo) {
        this.titulo = titulo == null ? "" : titulo.trim();
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean temImagens() {
        return primeira != null;
    }

    public int quantidadeImagens() {
        return quantidadeImagens;
    }

    public void adicionarImagem(String referencia) {
        if (referencia == null || referencia.trim().isEmpty()) {
            throw new IllegalArgumentException("A referência da imagem não pode ficar vazia.");
        }

        NoImagem novo = new NoImagem(new Imagem(referencia));
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
        quantidadeImagens++;
    }

    public Imagem proximaImagem() {
        if (primeira == null) return null;
        selecionada = selecionada == null ? primeira : selecionada.proximo;
        return selecionada.imagem;
    }

    public Imagem imagemAnterior() {
        if (primeira == null) return null;
        selecionada = selecionada == null ? ultima : selecionada.anterior;
        return selecionada.imagem;
    }

    public Imagem imagemSelecionada() {
        return selecionada == null ? null : selecionada.imagem;
    }

    public Imagem removerImagemSelecionada() {
        if (selecionada == null) return null;

        Imagem removida = selecionada.imagem;
        if (quantidadeImagens == 1) {
            primeira = null;
            ultima = null;
            selecionada = null;
        } else {
            NoImagem seguinte = selecionada.proximo;
            selecionada.anterior.proximo = selecionada.proximo;
            selecionada.proximo.anterior = selecionada.anterior;
            if (selecionada == primeira) primeira = selecionada.proximo;
            if (selecionada == ultima) ultima = selecionada.anterior;
            selecionada.proximo = null;
            selecionada.anterior = null;
            selecionada = seguinte;
        }
        quantidadeImagens--;
        return removida;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
