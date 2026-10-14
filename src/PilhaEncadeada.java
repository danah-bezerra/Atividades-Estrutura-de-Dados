import java.util.EmptyStackException;
import java.util.Scanner;

public class PilhaEncadeada<T> {

    private static class No<T> {
        T valor;
        No<T> proximo;

        No(T valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }

    private No<T> topo;
    private int tamanho;
    private int capacidade;

    public PilhaEncadeada(int capacidade) {
        this.capacidade = capacidade;
        this.topo = null;
        this.tamanho = 0;
    }

    public void empilhar(T valor) {
        if (estaCheia()) {
            throw new IllegalStateException("Pilha cheia! Capacidade máxima: " + capacidade);
        }
        No<T> novo = new No<>(valor);
        novo.proximo = topo;
        topo = novo;
        tamanho++;
    }

    public T desempilhar() {
        if (estaVazia()) {
            throw new EmptyStackException();
        }
        T valor = topo.valor;
        topo = topo.proximo;
        tamanho--;
        return valor;
    }

    public T topo() {
        if (estaVazia()) {
            throw new EmptyStackException();
        }
        return topo.valor;
    }

    public boolean estaVazia() {
        return topo == null;
    }

    public boolean estaCheia() {
        return capacidade > 0 && tamanho == capacidade;
    }

    public int tamanho() {
        return tamanho;
    }

    public void imprimir() {
        if (estaVazia()) {
            System.out.println("[ pilha vazia ]");
            return;
        }
        StringBuilder sb = new StringBuilder("Topo -> ");
        No<T> atual = topo;
        while (atual != null) {
            sb.append(atual.valor);
            if (atual.proximo != null) sb.append(" | ");
            atual = atual.proximo;
        }
        sb.append(" <- Base");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a capacidade máxima da pilha (0 para sem limite): ");
        int capacidade = lerInteiro(scanner);
        PilhaEncadeada<Integer> pilha = new PilhaEncadeada<>(capacidade);

        int opcao;
        do {
            System.out.println("\n===== MENU PILHA =====");
            System.out.println("1 - Empilhar (push)");
            System.out.println("2 - Desempilhar (pop)");
            System.out.println("3 - Ver topo (peek)");
            System.out.println("4 - Mostrar pilha");
            System.out.println("5 - Verificar se está vazia");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor a empilhar: ");
                    int valor = lerInteiro(scanner);
                    try {
                        pilha.empilhar(valor);
                        System.out.println("Valor " + valor + " empilhado com sucesso!");
                    } catch (IllegalStateException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        int removido = pilha.desempilhar();
                        System.out.println("Valor removido: " + removido);
                    } catch (EmptyStackException e) {
                        System.out.println("Erro: a pilha está vazia!");
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Elemento no topo: " + pilha.topo());
                    } catch (EmptyStackException e) {
                        System.out.println("Erro: a pilha está vazia!");
                    }
                    break;

                case 4:
                    pilha.imprimir();
                    break;

                case 5:
                    System.out.println(pilha.estaVazia() ? "A pilha está vazia." : "A pilha NÃO está vazia.");
                    break;

                case 0:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static int lerInteiro(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida! Digite um número inteiro: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}