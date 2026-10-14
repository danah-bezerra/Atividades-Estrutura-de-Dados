package projetosimples;

final class NoCategoria {
    Categoria categoria;
    NoCategoria proximo;
    NoCategoria anterior;

    NoCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
