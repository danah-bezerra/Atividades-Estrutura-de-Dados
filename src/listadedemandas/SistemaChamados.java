package listadedemandas;

public class SistemaChamados {
    private ListaCircularClientes clientes;
    private ListaCircularChamados chamados;
    private long proximoIdCliente;
    private long proximoIdChamado;

    public SistemaChamados() {
        clientes = new ListaCircularClientes();
        chamados = new ListaCircularChamados();
        proximoIdCliente = 1;
        proximoIdChamado = 1;
    }

    public ListaCircularClientes getClientes() { return clientes; }
    public ListaCircularChamados getChamados() { return chamados; }

    public Cliente cadastrarCliente(String nome, String email, String senha) {
        if (nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty()
                || senha == null || senha.isEmpty()) return null;
        Cliente novo = new Cliente(proximoIdCliente, nome.trim(), email.trim(), senha);
        if (!clientes.inserir(novo)) return null;
        proximoIdCliente++;
        return novo;
    }

    public Cliente autenticar(String email, String senha) {
        return clientes.autenticar(email, senha);
    }

    public Chamado criarChamado(Cliente cliente, String titulo, boolean externo) {
        if (cliente == null || titulo == null || titulo.trim().isEmpty()) return null;
        Chamado novo;
        if (externo) novo = new ChamadoExterno(proximoIdChamado, cliente, titulo.trim());
        else novo = new ChamadoInterno(proximoIdChamado, cliente, titulo.trim());
        proximoIdChamado++;
        chamados.inserir(novo);
        return novo;
    }

    public Chamado buscarChamado(long id) { return chamados.buscarPorId(id); }

    public int contarPorStatus(String status) {
        int total = 0;
        Chamado p = chamados.primeiro();
        if (p == null) return 0;
        do {
            if (p.getStatus().equals(status)) total++;
            p = p.getProximo();
        } while (p != chamados.primeiro());
        return total;
    }

    public int contarAtrasados() {
        int total = 0;
        Chamado p = chamados.primeiro();
        if (p == null) return 0;
        do {
            if (p.estaAtrasado()) total++;
            p = p.getProximo();
        } while (p != chamados.primeiro());
        return total;
    }

    public int contarDoCliente(long idCliente) {
        int total = 0;
        Chamado p = chamados.primeiro();
        if (p == null) return 0;
        do {
            if (p.getCliente().getId() == idCliente) total++;
            p = p.getProximo();
        } while (p != chamados.primeiro());
        return total;
    }

    public Chamado buscarTitulo(String termo) {
        if (termo == null || chamados.primeiro() == null) return null;
        Chamado p = chamados.primeiro();
        do {
            if (p.getTitulo().toLowerCase().contains(termo.toLowerCase())) return p;
            p = p.getProximo();
        } while (p != chamados.primeiro());
        return null;
    }

    public Chamado porPosicao(int posicao) {
        if (posicao < 0 || posicao >= chamados.tamanho()) return null;
        Chamado p = chamados.primeiro();
        int i = 0;
        while (i < posicao) {
            p = p.getProximo();
            i++;
        }
        return p;
    }

    public void ajustarContadores(long maiorCliente, long maiorChamado) {
        if (maiorCliente >= proximoIdCliente) proximoIdCliente = maiorCliente + 1;
        if (maiorChamado >= proximoIdChamado) proximoIdChamado = maiorChamado + 1;
    }
}
