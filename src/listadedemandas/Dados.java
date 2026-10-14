package listadedemandas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class Dados {
    private Dados() { }

    private static String limpar(String texto) {
        if (texto == null) return "";
        return texto.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }

    private static final class Campos {
        private final String linha;
        private int posicao;

        Campos(String linha) {
            this.linha = linha;
            this.posicao = 0;
        }

        String proximo() {
            if (posicao > linha.length()) return "";
            int separador = linha.indexOf('|', posicao);
            if (separador < 0) {
                String valor = linha.substring(posicao);
                posicao = linha.length() + 1;
                return valor;
            }
            String valor = linha.substring(posicao, separador);
            posicao = separador + 1;
            return valor;
        }
    }

    public static boolean salvar(SistemaChamados sistema, String caminho) {
        try (BufferedWriter saida = new BufferedWriter(new FileWriter(caminho))) {
            Cliente c = sistema.getClientes().primeiro();
            if (c != null) {
                do {
                    saida.write("CLIENTE|" + c.getId() + "|" + limpar(c.getNome()) + "|"
                            + limpar(c.getEmail()) + "|" + limpar(c.getSenhaParaArquivo()));
                    saida.newLine();
                    c = c.getProximo();
                } while (c != sistema.getClientes().primeiro());
            }

            Chamado ch = sistema.getChamados().primeiro();
            if (ch != null) {
                do {
                    saida.write("CHAMADO|" + ch.getId() + "|" + ch.getCliente().getId() + "|"
                            + limpar(ch.origem()) + "|" + limpar(ch.getTitulo()) + "|"
                            + limpar(ch.getDescricao()) + "|" + limpar(ch.getResponsavel()) + "|"
                            + limpar(ch.getStatus()) + "|" + limpar(ch.getPrioridade()) + "|"
                            + limpar(ch.prazoFormatado()) + "|" + limpar(ch.getComentarios()) + "|"
                            + limpar(ch.getHistoricoUrgencia()));
                    saida.newLine();
                    ch = ch.getProximo();
                } while (ch != sistema.getChamados().primeiro());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Falha ao salvar: " + e.getMessage());
            return false;
        }
    }

    public static boolean carregar(SistemaChamados sistema, String caminho) {
        long maiorCliente = 0;
        long maiorChamado = 0;
        try (BufferedReader entrada = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = entrada.readLine()) != null) {
                Campos campos = new Campos(linha);
                String tipo = campos.proximo();
                if ("CLIENTE".equals(tipo)) {
                    long id = Long.parseLong(campos.proximo());
                    String nome = campos.proximo();
                    String email = campos.proximo();
                    String senha = campos.proximo();
                    if (sistema.getClientes().buscarPorId(id) == null
                            && sistema.getClientes().buscarPorEmail(email) == null) {
                        Cliente novo = new Cliente(id, nome, email, senha);
                        sistema.getClientes().inserir(novo);
                    }
                    if (id > maiorCliente) maiorCliente = id;
                } else if ("CHAMADO".equals(tipo)) {
                    long id = Long.parseLong(campos.proximo());
                    long idCliente = Long.parseLong(campos.proximo());
                    String origem = campos.proximo();
                    String titulo = campos.proximo();
                    String descricao = campos.proximo();
                    String responsavel = campos.proximo();
                    String status = campos.proximo();
                    String prioridade = campos.proximo();
                    String prazo = campos.proximo();
                    String comentarios = campos.proximo();
                    String historico = campos.proximo();

                    if (sistema.getChamados().buscarPorId(id) == null) {
                        Cliente cliente = sistema.getClientes().buscarPorId(idCliente);
                        if (cliente != null) {
                            Chamado novo = "Externo".equals(origem)
                                    ? new ChamadoExterno(id, cliente, titulo)
                                    : new ChamadoInterno(id, cliente, titulo);
                            novo.restaurarCampos(descricao, responsavel, status, prioridade,
                                    "Sem prazo".equals(prazo) ? "" : prazo, comentarios, historico);
                            sistema.getChamados().inserir(novo);
                        }
                    }
                    if (id > maiorChamado) maiorChamado = id;
                }
            }
            sistema.ajustarContadores(maiorCliente, maiorChamado);
            return true;
        } catch (IOException | RuntimeException e) {
            System.out.println("Falha ao carregar dados: " + e.getMessage());
            return false;
        }
    }
}
