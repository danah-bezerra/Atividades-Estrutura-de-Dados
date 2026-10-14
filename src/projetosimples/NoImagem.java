package projetosimples;

final class NoImagem {
    Imagem imagem;
    NoImagem proximo;
    NoImagem anterior;

    NoImagem(Imagem imagem) {
        this.imagem = imagem;
    }
}
