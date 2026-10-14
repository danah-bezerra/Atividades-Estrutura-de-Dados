package listadedemandas;

public class ChamadoExterno extends Chamado {
    public ChamadoExterno(long id, Cliente cliente, String titulo) {
        super(id, cliente, titulo);
    }

    @Override
    public String origem() { return "Externo"; }

    @Override
    public boolean registrarUrgencia(String texto) {
        return atualizarUrgencia(texto, false);
    }
}
