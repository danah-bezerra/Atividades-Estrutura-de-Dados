package projetosimples;

final class NoNoticia {
    Noticia noticia;
    NoNoticia proximo;
    NoNoticia anterior;

    NoNoticia(Noticia noticia) {
        this.noticia = noticia;
    }
}
