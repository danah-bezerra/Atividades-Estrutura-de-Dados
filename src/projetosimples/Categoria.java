package projetosimples;

/** Categoria mantém notícias em uma lista circular duplamente encadeada. */
public class Categoria {
    private String nome;
    private NoNoticia primeira;
    private NoNoticia ultima;
    private NoNoticia selecionada;
    private int quantidadeNoticias;

    public Categoria(String nome) {
        this.nome = nome == null ? "" : nome.trim();
    }

    public String getNome() {
        return nome;
    }

    public int quantidadeNoticias() {
        return quantidadeNoticias;
    }

    public boolean temNoticias() {
        return primeira != null;
    }

    public void adicionarNoticia(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título da notícia não pode ficar vazio.");
        }
        NoNoticia novo = new NoNoticia(new Noticia(titulo));
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
        quantidadeNoticias++;
    }

    public Noticia proximaNoticia() {
        if (primeira == null) return null;
        selecionada = selecionada == null ? primeira : selecionada.proximo;
        return selecionada.noticia;
    }

    public Noticia noticiaAnterior() {
        if (primeira == null) return null;
        selecionada = selecionada == null ? ultima : selecionada.anterior;
        return selecionada.noticia;
    }

    public Noticia noticiaSelecionada() {
        return selecionada == null ? null : selecionada.noticia;
    }

    public Noticia removerNoticiaSelecionada() {
        if (selecionada == null) return null;

        Noticia removida = selecionada.noticia;
        if (quantidadeNoticias == 1) {
            primeira = null;
            ultima = null;
            selecionada = null;
        } else {
            NoNoticia seguinte = selecionada.proximo;
            selecionada.anterior.proximo = selecionada.proximo;
            selecionada.proximo.anterior = selecionada.anterior;
            if (selecionada == primeira) primeira = selecionada.proximo;
            if (selecionada == ultima) ultima = selecionada.anterior;
            selecionada.proximo = null;
            selecionada.anterior = null;
            selecionada = seguinte;
        }
        quantidadeNoticias--;
        return removida;
    }

    @Override
    public String toString() {
        return nome;
    }
}
