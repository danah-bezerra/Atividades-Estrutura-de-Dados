import java.util.NoSuchElementException;
import java.util.Scanner;

public class FilaEncadeada<T> {

    private static class No<T> {
        T valor;
        No<T> proximo;

        No(T valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }

    private No<T> inicio;
    private No<T> fim;
    private int tamanho;
    private int capacidade;

    public FilaEncadeada(int capacidade) {
        this.capacidade = capacidade;
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    public void enfileirar(T valor) {
        if (estaCheia()) {
            throw new IllegalStateException("Fila cheia! Capacidade máxima: " + capacidade);
        }
        No<T> novo = new No<>(valor);
        if (estaVazia()) {
            inicio = novo;
        } else {
            fim.proximo = novo;
        }
        fim = novo;
        tamanho++;
    }

    public T desenfileirar() {
        if (estaVazia()) {
            throw new NoSuchElementException("Fila vazia!");
        }
        T valor = inicio.valor;
        inicio = inicio.proximo;
        if (inicio == null) {
            fim = null;
        }
        tamanho--;
        return valor;
    }

    public T frente() {
        if (estaVazia()) {
            throw new NoSuchElementException("Fila vazia!");
        }
        return inicio.valor;
    }

    public boolean estaVazia() {
        return inicio == null;
    }

    public boolean estaCheia() {
        return capacidade > 0 && tamanho == capacidade;
    }

    public int tamanho() {
        return tamanho;
    }

    public void imprimir() {
        if (estaVazia()) {
            System.out.println("[ fila vazia ]");
            return;
        }
        StringBuilder sb = new StringBuilder("Início -> ");
        No<T> atual = inicio;
        while (atual != null) {
            sb.append(atual.valor);
            if (atual.proximo != null) sb.append(" | ");
            atual = atual.proximo;
        }
        sb.append(" <- Fim");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a capacidade máxima da fila (0 para sem limite): ");
        int capacidade = lerInteiro(scanner);
        FilaEncadeada<Integer> fila = new FilaEncadeada<>(capacidade);

        int opcao;
        do {
            System.out.println("\n===== MENU FILA =====");
            System.out.println("1 - Enfileirar");
            System.out.println("2 - Desenfileirar");
            System.out.println("3 - Ver frente");
            System.out.println("4 - Mostrar fila");
            System.out.println("5 - Verificar se está vazia");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor a enfileirar: ");
                    int valor = lerInteiro(scanner);
                    try {
                        fila.enfileirar(valor);
                        System.out.println("Valor " + valor + " enfileirado com sucesso!");
                    } catch (IllegalStateException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        int removido = fila.desenfileirar();
                        System.out.println("Valor removido: " + removido);
                    } catch (NoSuchElementException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Elemento na frente: " + fila.frente());
                    } catch (NoSuchElementException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 4:
                    fila.imprimir();
                    break;

                case 5:
                    System.out.println(fila.estaVazia() ? "A fila está vazia." : "A fila NÃO está vazia.");
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