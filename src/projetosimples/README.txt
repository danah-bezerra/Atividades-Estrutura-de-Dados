SISTEMA DE NOTÍCIAS — IMPLEMENTAÇÃO COM LISTAS CIRCULARES

Requisitos:
- Java 8 ou superior
- Sem arrays na lógica do programa (o único array é String[] args exigido pelo main)
- Sem Generics
- Listas circulares duplamente encadeadas

Estrutura:
- SistemaNoticias.java: mantém categorias.
- Categoria.java: mantém notícias.
- Noticia.java: mantém imagens.
- NoCategoria.java, NoNoticia.java e NoImagem.java: nós específicos de cada lista.
- Imagem.java: representa uma referência a imagem.
- AplicacaoNoticias.java: menu e interação com o usuário.
- Main.java: ponto de entrada.

Compilação pelo terminal, a partir da pasta do projeto:
  javac -d out src/projetosimples/*.java
  java -cp out projetosimples.Main

Decisão de implementação:
Cada lista possui nós específicos e mantém referências para primeiro, último e selecionado. Ao chegar ao fim, a navegação volta ao início; ao voltar antes do início, vai para o fim. A implementação não usa índices para selecionar itens nem uma lista genérica com Object.

Teste manual sugerido:
1. Cadastre duas categorias.
2. Navegue para frente e para trás e observe que a lista é circular.
3. Cadastre notícias na categoria selecionada.
4. Cadastre imagens na notícia selecionada.
5. Remova um elemento selecionado e confira o resumo.
6. Repita as remoções até a lista ficar vazia e verifique que o programa continua funcionando.

Este código é uma nova implementação de referência. Revise-o, teste-o e adapte-o ao enunciado do professor; esteja preparado para explicar como os ponteiros anterior/proximo são atualizados.
