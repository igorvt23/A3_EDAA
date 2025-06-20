<h1 align="center">Sistema de Gestão e Busca de Documentos Compressos com Índice Dinâmico</h1>
<p align="center">Avaliação 03 da UC: <small>Estruturas de Dados e análise de algoritmos - UniRitter</small></p>

<p align="center">
  <a href="#-objetivos">Objetivos</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-implementacoes">Implementações</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-tecnologias">Tecnologias</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-testes">Rodando Testes</a>&nbsp;&nbsp;&nbsp;
</p>
<br>

<p align="center">
  <img src="./img/menu_principal.png" alt="Menu Principal do nosso sistema" width="600">
</p>
<br>


## 🚩 Objetivos:

Desenvolva um sistema capaz de gerenciar, indexar, ordenar e buscar documentos de
texto armazenados em disco. O sistema deve suportar a inserção, remoção, ordenação
e compressão de documentos, além de oferecer buscas eficientes por palavras-chave.
Os documentos devem ser armazenados de forma persistente em arquivos e indexados
por múltiplas estruturas de dados, como árvores, tabelas hash e estruturas auxiliares
para compressão e ordenação.
<br><br>


## ✔ Implementações:
<ul style="list-style-type: circle">
  <li>Estruturas dinâmicas de dados para gerenciar os documentos e seus índices.</li>
  <li>Algoritmos de ordenação eficientes e análise de complexidade dos mesmos.</li>
  <li>Compressão de documentos usando codificação de Huffman.</li>
  <li>Índice reverso persistente com B+Tree ou Hashing para busca por palavras.</li>
</ul>
<br>


## 🚀 Tecnologias

Este projeto foi desenvolvido com as seguintes tecnologias e estruturas de dados:

- **Java** – linguagem principal do sistema.
- **JUnit** – para testes unitários.
- **Codificação de Huffman** – compressão de arquivos de texto.
- **AVL Tree** – estrutura de árvore balanceada para organização eficiente.
- **B+Tree** – utilizada para índices reversos e buscas otimizadas.
- **Listas Encadeadas** – controle e manipulação da coleção de documentos.
- **Java IO e Serialização** – para persistência de dados em disco.

<br>

## ▶ Rodando Testes:

### ⚠ Compilando os teste JUnit na raíz do projeto
- javac -d classes -cp "lib\junit-platform-console-standalone-1.10.0.jar" Lista\Node.java Lista\List.java Lista\ListService.java test\ListServiceTest.java

### ⚠ Rodando testes JUnit
- java -jar lib\junit-platform-console-standalone-1.10.0.jar -cp "classes" --scan-classpath
<br>
<p align="center">
  <img src="./img/testes_junit.png" alt="Foto do resumo dos teste JUnit" height="400">
</p>
