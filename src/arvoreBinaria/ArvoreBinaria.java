package arvoreBinaria;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Árvore Binária de Busca (BST) com inteiros.
 * Operações: inserir, buscar, remover, altura, mínimo, máximo e percursos.
 */
public class ArvoreBinaria {

    // ---------- Nó da árvore ----------
    private static class No {
        int valor;
        No esquerda;
        No direita;

        No(int valor) {
            this.valor = valor;
        }
    }

    private No raiz;
    private int tamanho;

    // ---------- Inserção ----------
    public void inserir(int valor) {
        raiz = inserir(raiz, valor);
    }

    private No inserir(No no, int valor) {
        if (no == null) {
            tamanho++;
            return new No(valor);
        }
        if (valor < no.valor) {
            no.esquerda = inserir(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = inserir(no.direita, valor);
        }
        // valores duplicados são ignorados
        return no;
    }

    // ---------- Busca ----------
    public boolean buscar(int valor) {
        No atual = raiz;
        while (atual != null) {
            if (valor == atual.valor) return true;
            atual = (valor < atual.valor) ? atual.esquerda : atual.direita;
        }
        return false;
    }

    // ---------- Remoção ----------
    public void remover(int valor) {
        raiz = remover(raiz, valor);
    }

    private No remover(No no, int valor) {
        if (no == null) return null;

        if (valor < no.valor) {
            no.esquerda = remover(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = remover(no.direita, valor);
        } else {
            tamanho--;
            // Caso 1 e 2: zero ou um filho
            if (no.esquerda == null) return no.direita;
            if (no.direita == null) return no.esquerda;

            // Caso 3: dois filhos -> substitui pelo sucessor (menor da direita)
            No sucessor = minimo(no.direita);
            no.valor = sucessor.valor;
            tamanho++; // compensa o decremento da remoção recursiva abaixo
            no.direita = remover(no.direita, sucessor.valor);
        }
        return no;
    }

    // ---------- Mínimo, máximo, altura, tamanho ----------
    public int minimo() {
        if (raiz == null) throw new IllegalStateException("Árvore vazia");
        return minimo(raiz).valor;
    }

    private No minimo(No no) {
        while (no.esquerda != null) no = no.esquerda;
        return no;
    }

    public int maximo() {
        if (raiz == null) throw new IllegalStateException("Árvore vazia");
        No no = raiz;
        while (no.direita != null) no = no.direita;
        return no.valor;
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(No no) {
        if (no == null) return -1; // altura de árvore vazia = -1; folha = 0
        return 1 + Math.max(altura(no.esquerda), altura(no.direita));
    }

    public int tamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return raiz == null;
    }

    // ---------- Percursos ----------
    public void emOrdem() {
        emOrdem(raiz);
        System.out.println();
    }

    private void emOrdem(No no) {
        if (no == null) return;
        emOrdem(no.esquerda);
        System.out.print(no.valor + " ");
        emOrdem(no.direita);
    }

    public void preOrdem() {
        preOrdem(raiz);
        System.out.println();
    }

    private void preOrdem(No no) {
        if (no == null) return;
        System.out.print(no.valor + " ");
        preOrdem(no.esquerda);
        preOrdem(no.direita);
    }

    public void posOrdem() {
        posOrdem(raiz);
        System.out.println();
    }

    private void posOrdem(No no) {
        if (no == null) return;
        posOrdem(no.esquerda);
        posOrdem(no.direita);
        System.out.print(no.valor + " ");
    }

    // Percurso em largura (por nível)
    public void emNivel() {
        if (raiz == null) {
            System.out.println();
            return;
        }
        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);
        while (!fila.isEmpty()) {
            No no = fila.poll();
            System.out.print(no.valor + " ");
            if (no.esquerda != null) fila.add(no.esquerda);
            if (no.direita != null) fila.add(no.direita);
        }
        System.out.println();
    }

    // ---------- Programa de teste ----------
    public static void main(String[] args) {
        ArvoreBinaria arvore = new ArvoreBinaria();

        int[] valores = {50, 30, 70, 20, 40, 60, 80, 35, 45};
        for (int v : valores) arvore.inserir(v);

        System.out.print("Em ordem:  ");
        arvore.emOrdem();
        System.out.print("Pré-ordem: ");
        arvore.preOrdem();
        System.out.print("Pós-ordem: ");
        arvore.posOrdem();
        System.out.print("Por nível: ");
        arvore.emNivel();

        System.out.println("\nTamanho: " + arvore.tamanho());
        System.out.println("Altura:  " + arvore.altura());
        System.out.println("Mínimo:  " + arvore.minimo());
        System.out.println("Máximo:  " + arvore.maximo());

        System.out.println("\nBuscar 40: " + arvore.buscar(40));
        System.out.println("Buscar 99: " + arvore.buscar(99));

        System.out.println("\nRemovendo 20 (folha), 30 (dois filhos) e 50 (raiz)...");
        arvore.remover(20);
        arvore.remover(30);
        arvore.remover(50);

        System.out.print("Em ordem:  ");
        arvore.emOrdem();
        System.out.print("Por nível: ");
        arvore.emNivel();
        System.out.println("Tamanho: " + arvore.tamanho());
    }
}
