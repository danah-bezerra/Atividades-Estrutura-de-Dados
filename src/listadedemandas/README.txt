SISTEMA DE DEMANDAS - LISTAS CIRCULARES
=======================================

Requisitos:
- JDK instalado (Java 8 ou superior).
- Terminal/Prompt de Comando.

Estrutura:
src/listadedemandas/
  Cliente.java
  Urgencia.java
  StatusChamado.java
  Chamado.java
  ChamadoInterno.java
  ChamadoExterno.java
  ListaCircularClientes.java
  ListaCircularChamados.java
  SistemaChamados.java
  Dados.java
  Principal.java

Como compilar:
1. Abra o terminal na pasta SistemaDemandasCircular.
2. Crie a pasta de saída e compile os fontes:

   javac -d out src/listadedemandas/*.java

Como executar:
   java -cp out listadedemandas.Principal

O sistema cria/usa o arquivo demandas.txt na pasta de execução para salvar dados.
Use a opção 17 para salvar manualmente; a opção 0 também tenta salvar antes de sair.

Decisões de implementação:
- Não usa arrays como estruturas de dados e não usa Generics.
- A assinatura main(String[] args) permanece porque é exigida pela linguagem Java.
- Os clientes e os chamados são guardados em listas circulares simplesmente encadeadas.
- Cada nó guarda uma referência para o próximo; o último aponta para o primeiro.
- Chamados internos não aceitam reduzir a urgência; chamados externos permitem alterar o nível livremente.
- As opções de navegação percorrem a lista circular de chamados.

Observação:
Este é um projeto didático. A senha é gravada no arquivo de dados para permitir a persistência
do exemplo; em um sistema real, senhas devem ser armazenadas usando hash seguro, nunca em texto puro.
