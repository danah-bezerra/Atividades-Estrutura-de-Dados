package projetosimples;

import java.util.Scanner;

/** Camada de interação: apresenta o menu e chama as operações do sistema. */
public class AplicacaoNoticias {
    private final Scanner entrada = new Scanner(System.in);
    private final SistemaNoticias sistema = new SistemaNoticias();
    private Categoria categoriaAtual;
    private Noticia noticiaAtual;
    private Imagem imagemAtual;

    public void executar() {
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Opção: ");
            switch (opcao) {
                case 1: cadastrarCategoria(); break;
                case 2: navegarCategoria(true); break;
                case 3: navegarCategoria(false); break;
                case 4: cadastrarNoticia(); break;
                case 5: navegarNoticia(true); break;
                case 6: navegarNoticia(false); break;
                case 7: cadastrarImagem(); break;
                case 8: navegarImagem(true); break;
                case 9: navegarImagem(false); break;
                case 10: mostrarResumo(); break;
                case 11: apagarCategoria(); break;
                case 12: apagarNoticia(); break;
                case 13: apagarImagem(); break;
                case 0: System.out.println("Sistema encerrado."); break;
                default: System.out.println("Opção inválida. Escolha um número do menu.");
            }
        } while (opcao != 0);
    }

    private void cadastrarCategoria() {
        String nome = lerTexto("Nome da categoria: ");
        try {
            sistema.adicionarCategoria(nome);
            categoriaAtual = sistema.categoriaSelecionada();
            noticiaAtual = null;
            imagemAtual = null;
            System.out.println("Categoria cadastrada e selecionada.");
        } catch (IllegalArgumentException erro) {
            System.out.println(erro.getMessage());
        }
    }

    private void navegarCategoria(boolean avancar) {
        Categoria resultado = avancar ? sistema.proximaCategoria() : sistema.categoriaAnterior();
        if (resultado == null) {
            System.out.println("Cadastre uma categoria antes de navegar.");
            return;
        }
        categoriaAtual = resultado;
        noticiaAtual = null;
        imagemAtual = null;
        System.out.println("Categoria selecionada: " + categoriaAtual);
    }

    private void cadastrarNoticia() {
        if (categoriaAtual == null) {
            System.out.println("Selecione ou cadastre uma categoria primeiro.");
            return;
        }
        try {
            categoriaAtual.adicionarNoticia(lerTexto("Título da notícia: "));
            noticiaAtual = categoriaAtual.noticiaSelecionada();
            imagemAtual = null;
            System.out.println("Notícia adicionada em " + categoriaAtual + ".");
        } catch (IllegalArgumentException erro) {
            System.out.println(erro.getMessage());
        }
    }

    private void navegarNoticia(boolean avancar) {
        if (categoriaAtual == null) {
            System.out.println("Selecione uma categoria primeiro.");
            return;
        }
        Noticia resultado = avancar ? categoriaAtual.proximaNoticia() : categoriaAtual.noticiaAnterior();
        if (resultado == null) {
            System.out.println("Essa categoria ainda não possui notícias.");
            return;
        }
        noticiaAtual = resultado;
        imagemAtual = null;
        System.out.println("Notícia selecionada: " + noticiaAtual);
    }

    private void cadastrarImagem() {
        if (noticiaAtual == null) {
            System.out.println("Selecione uma notícia primeiro.");
            return;
        }
        try {
            noticiaAtual.adicionarImagem(lerTexto("Referência da imagem (nome ou URL): "));
            imagemAtual = noticiaAtual.imagemSelecionada();
            System.out.println("Imagem adicionada à notícia " + noticiaAtual + ".");
        } catch (IllegalArgumentException erro) {
            System.out.println(erro.getMessage());
        }
    }

    private void navegarImagem(boolean avancar) {
        if (noticiaAtual == null) {
            System.out.println("Selecione uma notícia primeiro.");
            return;
        }
        Imagem resultado = avancar ? noticiaAtual.proximaImagem() : noticiaAtual.imagemAnterior();
        if (resultado == null) {
            System.out.println("Essa notícia ainda não possui imagens.");
            return;
        }
        imagemAtual = resultado;
        System.out.println("Imagem selecionada: " + imagemAtual);
    }

    private void apagarCategoria() {
        Categoria removida = sistema.removerCategoriaSelecionada();
        if (removida == null) {
            System.out.println("Não há categoria selecionada para remover.");
            return;
        }
        categoriaAtual = sistema.categoriaSelecionada();
        noticiaAtual = null;
        imagemAtual = null;
        System.out.println("Categoria removida: " + removida);
    }

    private void apagarNoticia() {
        if (categoriaAtual == null) {
            System.out.println("Selecione uma categoria primeiro.");
            return;
        }
        Noticia removida = categoriaAtual.removerNoticiaSelecionada();
        if (removida == null) {
            System.out.println("Não há notícia selecionada para remover.");
            return;
        }
        noticiaAtual = categoriaAtual.noticiaSelecionada();
        imagemAtual = null;
        System.out.println("Notícia removida: " + removida);
    }

    private void apagarImagem() {
        if (noticiaAtual == null) {
            System.out.println("Selecione uma notícia primeiro.");
            return;
        }
        Imagem removida = noticiaAtual.removerImagemSelecionada();
        if (removida == null) {
            System.out.println("Não há imagem selecionada para remover.");
            return;
        }
        imagemAtual = noticiaAtual.imagemSelecionada();
        System.out.println("Imagem removida: " + removida);
    }

    private void mostrarResumo() {
        System.out.println("\n--- RESUMO DO SISTEMA ---");
        System.out.println("Total de categorias: " + sistema.quantidadeCategorias());
        System.out.println("Categoria atual: " + (categoriaAtual == null ? "nenhuma" : categoriaAtual));
        if (categoriaAtual != null) {
            System.out.println("Notícias na categoria: " + categoriaAtual.quantidadeNoticias());
        }
        System.out.println("Notícia atual: " + (noticiaAtual == null ? "nenhuma" : noticiaAtual));
        if (noticiaAtual != null) {
            System.out.println("Imagens na notícia: " + noticiaAtual.quantidadeImagens());
        }
        System.out.println("Imagem atual: " + (imagemAtual == null ? "nenhuma" : imagemAtual));
        System.out.println("------------------------\n");
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        String linha = entrada.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException erro) {
            return -1;
        }
    }

    private void mostrarMenu() {
        System.out.println("\n========== SISTEMA DE NOTÍCIAS ==========");
        System.out.println("CATEGORIAS");
        System.out.println(" 1 - Cadastrar categoria");
        System.out.println(" 2 - Próxima categoria");
        System.out.println(" 3 - Categoria anterior");
        System.out.println("NOTÍCIAS");
        System.out.println(" 4 - Cadastrar notícia na categoria atual");
        System.out.println(" 5 - Próxima notícia");
        System.out.println(" 6 - Notícia anterior");
        System.out.println("IMAGENS");
        System.out.println(" 7 - Cadastrar imagem na notícia atual");
        System.out.println(" 8 - Próxima imagem");
        System.out.println(" 9 - Imagem anterior");
        System.out.println("SISTEMA");
        System.out.println("10 - Mostrar resumo");
        System.out.println("11 - Remover categoria selecionada");
        System.out.println("12 - Remover notícia selecionada");
        System.out.println("13 - Remover imagem selecionada");
        System.out.println(" 0 - Sair");
    }
}
