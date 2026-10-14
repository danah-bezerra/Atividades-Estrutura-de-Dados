package listadedemandas;

public class ChamadoInterno extends Chamado {
    public ChamadoInterno(long id, Cliente cliente, String titulo) {
        super(id, cliente, titulo);
    }

    @Override
    public String origem() { return "Interno"; }

    @Override
    public boolean registrarUrgencia(String texto) {
        return atualizarUrgencia(texto, true);
    }
}
