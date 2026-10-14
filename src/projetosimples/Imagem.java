package projetosimples;

/** Guarda a informação associada a uma imagem (por exemplo, URL ou nome do arquivo). */
public class Imagem {
    private String referencia;

    public Imagem(String referencia) {
        this.referencia = referencia == null ? "" : referencia.trim();
    }

    public String getReferencia() {
        return referencia;
    }

    @Override
    public String toString() {
        return referencia;
    }
}
