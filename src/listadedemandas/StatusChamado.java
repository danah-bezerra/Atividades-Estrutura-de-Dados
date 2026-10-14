package listadedemandas;

public final class StatusChamado {
    public static final String A_FAZER = "A fazer";
    public static final String EM_ANDAMENTO = "Em andamento";
    public static final String EM_REVISAO = "Em revisao";
    public static final String CONCLUIDO = "Concluido";

    private StatusChamado() { }

    public static boolean valido(String status) {
        return A_FAZER.equals(status) || EM_ANDAMENTO.equals(status)
                || EM_REVISAO.equals(status) || CONCLUIDO.equals(status);
    }

    public static String avancar(String status) {
        if (A_FAZER.equals(status)) return EM_ANDAMENTO;
        if (EM_ANDAMENTO.equals(status)) return EM_REVISAO;
        if (EM_REVISAO.equals(status)) return CONCLUIDO;
        return status;
    }

    public static String voltar(String status) {
        if (CONCLUIDO.equals(status)) return EM_REVISAO;
        if (EM_REVISAO.equals(status)) return EM_ANDAMENTO;
        if (EM_ANDAMENTO.equals(status)) return A_FAZER;
        return status;
    }
}
