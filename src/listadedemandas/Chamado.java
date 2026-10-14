package listadedemandas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Chamado {
    private long id;
    private Cliente cliente;
    private String titulo;
    private String descricao;
    private String responsavel;
    private String prioridade;
    private String status;
    private LocalDate prazo;
    private String comentarios;
    private String historicoUrgencia;
    private int nivelUrgencia;
    private Chamado proximo;

    protected Chamado(long id, Cliente cliente, String titulo) {
        this.id = id;
        this.cliente = cliente;
        this.titulo = titulo;
        this.descricao = "";
        this.responsavel = "Nao definido";
        this.prioridade = "Normal";
        this.status = StatusChamado.A_FAZER;
        this.prazo = null;
        this.comentarios = "";
        this.historicoUrgencia = "";
        this.nivelUrgencia = 1;
        this.proximo = null;
    }

    public abstract String origem();
    public abstract boolean registrarUrgencia(String texto);

    protected boolean atualizarUrgencia(String texto, boolean impedirReducao) {
        int novoNivel = Urgencia.converterNivel(texto);
        if (novoNivel < 0) return false;
        if (impedirReducao && novoNivel < nivelUrgencia) return false;

        nivelUrgencia = novoNivel;
        prioridade = Urgencia.nomeDoNivel(novoNivel);
        if (!historicoUrgencia.isEmpty()) historicoUrgencia += " ; ";
        historicoUrgencia += prioridade;
        return true;
    }

    public long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getResponsavel() { return responsavel; }
    public String getPrioridade() { return prioridade; }
    public int getNivelUrgencia() { return nivelUrgencia; }
    public String getStatus() { return status; }
    public Chamado getProximo() { return proximo; }
    public String getComentarios() { return comentarios; }
    public String getHistoricoUrgencia() { return historicoUrgencia; }
    public LocalDate getPrazo() { return prazo; }

    public void setProximo(Chamado proximo) { this.proximo = proximo; }
    public void setDescricao(String descricao) { this.descricao = descricao == null ? "" : descricao; }
    public void setResponsavel(String responsavel) {
        if (responsavel != null && !responsavel.trim().isEmpty()) this.responsavel = responsavel.trim();
    }
    public void setPrioridade(String prioridade) {
        int nivel = Urgencia.converterNivel(prioridade);
        if (nivel >= 0) {
            this.nivelUrgencia = nivel;
            this.prioridade = Urgencia.nomeDoNivel(nivel);
        }
    }
    public boolean setStatus(String status) {
        if (!StatusChamado.valido(status)) return false;
        this.status = status;
        return true;
    }
    public void definirPrazo(String data) {
        try {
            this.prazo = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            this.prazo = null;
        }
    }
    public void definirPrazo(LocalDate data) { this.prazo = data; }
    public String prazoFormatado() {
        return prazo == null ? "Sem prazo" : prazo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public boolean estaAtrasado() {
        return prazo != null && prazo.isBefore(LocalDate.now()) && !StatusChamado.CONCLUIDO.equals(status);
    }
    public void adicionarComentario(String comentario) {
        if (comentario == null || comentario.trim().isEmpty()) return;
        if (!comentarios.isEmpty()) comentarios += " || ";
        comentarios += comentario.trim();
    }
    public int quantidadeComentarios() {
        if (comentarios.isEmpty()) return 0;
        int total = 1;
        for (int i = 0; i < comentarios.length() - 3; i++) {
            if (comentarios.charAt(i) == '|' && comentarios.charAt(i + 1) == '|') total++;
        }
        return total;
    }
    public int quantidadeHistoricoUrgencia() {
        if (historicoUrgencia.isEmpty()) return 0;
        int total = 1;
        for (int i = 0; i < historicoUrgencia.length() - 2; i++) {
            if (historicoUrgencia.charAt(i) == ';') total++;
        }
        return total;
    }
    public String resumo() {
        return "#" + id + " | " + titulo + " | " + origem() + " | "
                + status + " | urgencia: " + prioridade + " | prazo: "
                + prazoFormatado() + " | responsavel: " + responsavel;
    }

    // Usado pela classe de persistencia para restaurar os dados sem perder o historico.
    public void restaurarCampos(String descricao, String responsavel, String status,
            String prioridade, String dataPrazo, String comentarios, String historico) {
        this.descricao = descricao == null ? "" : descricao;
        setResponsavel(responsavel);
        if (StatusChamado.valido(status)) this.status = status;
        setPrioridade(prioridade);
        definirPrazo(dataPrazo);
        this.comentarios = comentarios == null ? "" : comentarios;
        this.historicoUrgencia = historico == null ? "" : historico;
    }
}
