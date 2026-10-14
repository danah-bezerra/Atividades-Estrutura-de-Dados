package listadedemandas;

import java.util.Scanner;

public class Principal {
    private static final Scanner TECLADO = new Scanner(System.in);
    private static final SistemaChamados SISTEMA = new SistemaChamados();
    private static Cliente clienteLogado;

    public static void main(String[] args) {
        Dados.carregar(SISTEMA, "demandas.txt");
        int opcao;
        do {
            System.out.println("\n=== SISTEMA DE DEMANDAS ===");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Entrar");
            System.out.println("3 - Listar clientes");
            System.out.println("4 - Listar chamados");
            System.out.println("5 - Criar chamado (usa o cliente selecionado)");
            System.out.println("6 - Buscar chamado por ID");
            System.out.println("7 - Buscar chamado por titulo");
            System.out.println("8 - Avancar status");
            System.out.println("9 - Alterar urgencia");
            System.out.println("10 - Adicionar comentario");
            System.out.println("11 - Definir prazo");
            System.out.println("12 - Definir responsavel");
            System.out.println("13 - Remover chamado");
            System.out.println("14 - Navegar para proximo chamado");
            System.out.println("15 - Navegar para chamado anterior");
            System.out.println("16 - Estatisticas");
            System.out.println("17 - Salvar dados");
            System.out.println("0 - Sair");
            opcao = lerInteiro("Opcao: ");

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: entrar(); break;
                case 3: listarClientes(); break;
                case 4: listarChamados(); break;
                case 5: criarChamado(); break;
                case 6: buscarPorId(); break;
                case 7: buscarPorTitulo(); break;
                case 8: avancarStatus(); break;
                case 9: alterarUrgencia(); break;
                case 10: comentario(); break;
                case 11: prazo(); break;
                case 12: responsavel(); break;
                case 13: remover(); break;
                case 14: navegar(true); break;
                case 15: navegar(false); break;
                case 16: estatisticas(); break;
                case 17: System.out.println(Dados.salvar(SISTEMA, "demandas.txt") ? "Dados salvos." : "Nao foi possivel salvar."); break;
                case 0: Dados.salvar(SISTEMA, "demandas.txt"); System.out.println("Sistema encerrado."); break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static String ler(String mensagem) {
        System.out.print(mensagem);
        return TECLADO.nextLine().trim();
    }

    private static int lerInteiro(String mensagem) {
        try { return Integer.parseInt(ler(mensagem)); }
        catch (NumberFormatException e) { return -1; }
    }

    private static long lerLong(String mensagem) {
        try { return Long.parseLong(ler(mensagem)); }
        catch (NumberFormatException e) { return -1; }
    }

    private static void cadastrar() {
        String nome = ler("Nome: ");
        String email = ler("E-mail: ");
        String senha = ler("Senha: ");
        Cliente c = SISTEMA.cadastrarCliente(nome, email, senha);
        System.out.println(c == null ? "Cadastro recusado. Verifique os dados ou se o e-mail ja existe." : "Cadastrado: " + c);
    }

    private static void entrar() {
        String email = ler("E-mail: ");
        String senha = ler("Senha: ");
        clienteLogado = SISTEMA.autenticar(email, senha);
        System.out.println(clienteLogado == null ? "Login invalido." : "Bem-vindo(a), " + clienteLogado.getNome());
    }

    private static void listarClientes() {
        Cliente p = SISTEMA.getClientes().primeiro();
        if (p == null) { System.out.println("Nenhum cliente cadastrado."); return; }
        do {
            System.out.println(p);
            p = p.getProximo();
        } while (p != SISTEMA.getClientes().primeiro());
    }

    private static void listarChamados() {
        Chamado p = SISTEMA.getChamados().primeiro();
        if (p == null) { System.out.println("Nenhum chamado cadastrado."); return; }
        do {
            System.out.println(p.resumo());
            p = p.getProximo();
        } while (p != SISTEMA.getChamados().primeiro());
    }

    private static void criarChamado() {
        if (clienteLogado == null) { System.out.println("Entre primeiro usando a opcao 2."); return; }
        String titulo = ler("Titulo: ");
        String tipo = ler("Origem (I = interno, E = externo): ");
        Chamado ch = SISTEMA.criarChamado(clienteLogado, titulo, tipo.equalsIgnoreCase("E"));
        if (ch == null) { System.out.println("Nao foi possivel criar o chamado."); return; }
        String urgencia = ler("Urgencia (Baixa, Normal, Alta, Urgente): ");
        if (!ch.registrarUrgencia(urgencia)) System.out.println("Urgencia invalida.");
        System.out.println("Chamado criado: " + ch.resumo());
    }

    private static Chamado pedirChamado() {
        long id = lerLong("ID do chamado: ");
        Chamado ch = SISTEMA.buscarChamado(id);
        if (ch == null) System.out.println("Chamado nao encontrado.");
        return ch;
    }

    private static void buscarPorId() {
        Chamado ch = pedirChamado();
        if (ch != null) {
            System.out.println(ch.resumo());
            System.out.println("Descricao: " + ch.getDescricao());
            System.out.println("Historico de urgencia: " + ch.getHistoricoUrgencia());
            System.out.println("Comentarios: " + ch.getComentarios());
        }
    }

    private static void buscarPorTitulo() {
        String termo = ler("Parte do titulo: ");
        Chamado ch = SISTEMA.buscarTitulo(termo);
        System.out.println(ch == null ? "Nenhum chamado encontrado." : ch.resumo());
    }

    private static void avancarStatus() {
        Chamado ch = pedirChamado();
        if (ch != null) {
            String antes = ch.getStatus();
            ch.setStatus(StatusChamado.avancar(antes));
            System.out.println(antes + " -> " + ch.getStatus());
        }
    }

    private static void alterarUrgencia() {
        Chamado ch = pedirChamado();
        if (ch != null) {
            String antes = ch.getPrioridade();
            boolean ok = ch.registrarUrgencia(ler("Nova urgencia: "));
            if (!ok) System.out.println("Urgencia invalida ou, em chamado interno, a nova urgencia e inferior.");
            else System.out.println(antes + " -> " + ch.getPrioridade());
        }
    }

    private static void comentario() {
        Chamado ch = pedirChamado();
        if (ch != null) {
            ch.adicionarComentario(ler("Comentario: "));
            System.out.println("Comentario adicionado.");
        }
    }

    private static void prazo() {
        Chamado ch = pedirChamado();
        if (ch != null) {
            ch.definirPrazo(ler("Prazo (dd/MM/yyyy): "));
            System.out.println("Prazo: " + ch.prazoFormatado());
        }
    }

    private static void responsavel() {
        Chamado ch = pedirChamado();
        if (ch != null) {
            ch.setResponsavel(ler("Nome do responsavel: "));
            System.out.println("Responsavel atualizado.");
        }
    }

    private static void remover() {
        Chamado ch = pedirChamado();
        if (ch != null) {
            SISTEMA.getChamados().selecionar(ch.getId());
            Chamado removido = SISTEMA.getChamados().removerAtual();
            System.out.println("Removido: " + removido.getTitulo());
        }
    }

    private static void navegar(boolean avancar) {
        Chamado ch = avancar ? SISTEMA.getChamados().proximo() : SISTEMA.getChamados().anterior();
        System.out.println(ch == null ? "Nao ha chamados para navegar." : ch.resumo());
    }

    private static void estatisticas() {
        System.out.println("Total de chamados: " + SISTEMA.getChamados().tamanho());
        System.out.println("A fazer: " + SISTEMA.contarPorStatus(StatusChamado.A_FAZER));
        System.out.println("Em andamento: " + SISTEMA.contarPorStatus(StatusChamado.EM_ANDAMENTO));
        System.out.println("Em revisao: " + SISTEMA.contarPorStatus(StatusChamado.EM_REVISAO));
        System.out.println("Concluidos: " + SISTEMA.contarPorStatus(StatusChamado.CONCLUIDO));
        System.out.println("Atrasados: " + SISTEMA.contarAtrasados());
        System.out.println("Chamados do cliente logado: " + (clienteLogado == null ? 0 : SISTEMA.contarDoCliente(clienteLogado.getId())));
    }
}
