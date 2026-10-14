package listadedemandas;

public final class Urgencia {
    private Urgencia() { }

    public static int converterNivel(String texto) {
        if (texto == null) return -1;
        String valor = texto.trim().toLowerCase();

        if (valor.equals("baixa") || valor.equals("0") || valor.startsWith("baix")) return 0;
        if (valor.equals("normal") || valor.equals("1") || valor.startsWith("norm")) return 1;
        if (valor.equals("alta") || valor.equals("2") || valor.startsWith("alt")) return 2;
        if (valor.equals("urgente") || valor.equals("3") || valor.startsWith("urg") || valor.startsWith("crit")) return 3;

        return -1;
    }

    public static String nomeDoNivel(int nivel) {
        if (nivel == 0) return "Baixa";
        if (nivel == 1) return "Normal";
        if (nivel == 2) return "Alta";
        if (nivel == 3) return "Urgente";
        return "Nao classificada";
    }

    public static String normalizar(String texto) {
        return nomeDoNivel(converterNivel(texto));
    }
}
